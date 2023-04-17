package library.management.controllers;

import library.management.entity.GetBooks;
import library.management.entity.GetSetBooks;
import library.management.entity.IssueBook;
import library.management.repo.BooksRepositiry;
import library.management.repo.GetBooksI;
import library.management.repo.IssuedBooks;
import library.management.services.ReaderOperation;
import library.management.services.SequenceGeneratorService1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static library.management.entity.IssueBook.SEQUENCE_NAME;

@RestController
public class ReaderController {
    @Autowired
    private BooksRepositiry bookRepo1;
    @Autowired
    private ReaderOperation rOperation;

    @Autowired
    private GetBooksI getBooksI;
    @Autowired
    private IssuedBooks issuedB;
    @Autowired
    private SequenceGeneratorService1 service;

    @GetMapping("/showAllR")
    public ResponseEntity<?> showAllR() {
        List<GetBooks> getBooks = this.getBooksI.findAll();
        if (getBooks.size() == 0) {
            return new ResponseEntity("Books Not Available", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(getBooks);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> showSingle(@PathVariable("name") String Name) {
        List<IssueBook> listIssued = this.issuedB.findAll().stream().filter(c -> c.getReaderName().equals(Name)).collect(Collectors.toList());
        if (listIssued.size() == 0) {
            return ResponseEntity.ok("No Data Found For Username : " + Name);
        }
        return ResponseEntity.ok(listIssued);
    }

    @PostMapping("/issueBook/{id}")
    public ResponseEntity<?> issueBook(@Valid @RequestBody IssueBook issue, @PathVariable("id") int id) {
        int getCopies = 0;
        boolean isThere = bookRepo1.existsById(id);
        if (isThere) {
            GetSetBooks book1 = bookRepo1.findById(id).get();
            int copies = issue.getCopies();
            boolean available = rOperation.available(book1, issue);
            if (available) {
                List<IssueBook> listIssued = this.issuedB.findAll().stream().filter(c -> c.getReaderName().equals(issue.getReaderName())).collect(Collectors.toList());
                if (listIssued.size() == 1) {
                    for (IssueBook k : listIssued) {
                        getCopies = k.getCopies();
                    }
                }
                if (listIssued.size() == 0) {
                    issue.setBookName(book1.getBookName());
                    issue.setBookId(id);
                    issue.setId(service.getSequenceNumber(SEQUENCE_NAME));
                    IssueBook issuedB = this.issuedB.save(issue);
                    GetSetBooks book11 = rOperation.update(book1, copies);
                    bookRepo1.save(book11);
                    return ResponseEntity.ok(issuedB);
                }
                if (getCopies < 2 && listIssued.size() < 2) {
                    if (issue.getCopies() <= 1) {
                        issue.setBookName(book1.getBookName());
                        issue.setBookId(id);
                        issue.setId(service.getSequenceNumber(SEQUENCE_NAME));
                        IssueBook issueBook = this.issuedB.save(issue);
                        GetSetBooks books = rOperation.update(book1, copies);
                        bookRepo1.save(books);
                        return ResponseEntity.ok(issueBook);
                    }
                    return ResponseEntity.ok("You Have Already Issued 1 Book Now You Can Issue Only 1 Book ");
                }
                return ResponseEntity.ok("You Have Already Issued Two Books ");
            }
            return ResponseEntity.ok("No Of Copies Must Be Greater Than 0 And Less Or Equals 2");
        }
        return ResponseEntity.ok("This Book Id Not Available");
    }

    @PostMapping("/returnBook/{name}")
    public ResponseEntity<?> returnBook(@RequestBody GetSetBooks books, @PathVariable("name") String name) {
        int rating = books.getRating();
        if (rating < 6 && rating > 0) {
            GetSetBooks book1 = this.bookRepo1.findById(books.getId()).get();
            List<IssueBook> listIssued = this.issuedB.findAll().stream().filter(a -> a.getReaderName().equals(name)).collect(Collectors.toList());
            listIssued = listIssued.stream().filter(i -> i.getBookName().equals(book1.getBookName())).collect(Collectors.toList());
            int findIdToDeleteIssued = 0;
            long penelty = 0;
            int copies = 0;
            for (IssueBook issueBook : listIssued) {
                findIdToDeleteIssued = issueBook.getId();
                copies = issueBook.getCopies();
                penelty = rOperation.dateCals(issueBook.getDateOfIssue());
            }
            GetSetBooks book2 = rOperation.updateReturn(book1, copies);
            book2.setRating(books.getRating());
            this.bookRepo1.save(book2);
            this.issuedB.deleteById(findIdToDeleteIssued);
            if (listIssued.size() == 0) {
                return ResponseEntity.ok("Data Not Available");
            }
            return ResponseEntity.ok(" Successfully Completed... \n Your Penalty Is $ :" + penelty);
        }
        return ResponseEntity.ok("Enter A Valid Rating > 0 and <= 5 ");
    }
}
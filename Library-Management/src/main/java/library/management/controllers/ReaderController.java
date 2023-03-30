package library.management.controllers;

import library.management.entity.GetBooks;
import library.management.entity.GetSetBooks;
import library.management.entity.IssueBook;
import library.management.repo.BooksRepositiry;
import library.management.repo.GetBooksI;
import library.management.repo.IssuedBooks;
import library.management.services.ReaderOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ReaderController {
    @Autowired
    private BooksRepositiry bookRepo1;
    @Autowired
    private ReaderOperation rOperation;

    @Autowired
    private GetBooksI gg;

    @GetMapping("/showAllR")
    public ResponseEntity<?> showAllR() {
        List<GetBooks> kk = this.gg.findAll();
        if (kk.size() == 0) {
            return new ResponseEntity("Books Not Available", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(kk);
    }

    @Autowired
    private IssuedBooks issuedB;

    @PostMapping("/issueBook/{id}")
    public ResponseEntity<?> issueBook(@RequestBody IssueBook issue, @PathVariable("id") int id) {
        boolean isThere = bookRepo1.existsById(id);
        int aa = 0;
        if (isThere) {
            GetSetBooks book1 = bookRepo1.findById(id).get();
            int t = rOperation.countDigit(issue.getContactNo());
            int copies = issue.getCopies();
            if (copies < 3) {
                if (t == 0) {
                    int as = rOperation.available(book1, issue);
                    if (as == 1) {
                        boolean q = issuedB.existsById(issue.getId());
                        if (q == false) {
                            List<IssueBook> listIssued = this.issuedB.findAll().stream().filter(c -> c.getReaderName().equals(issue.getReaderName())).collect(Collectors.toList());
                            if (listIssued.size() == 1) {
                                for (IssueBook k : listIssued) {
                                    aa = k.getCopies();
                                }
                            }
                            if (listIssued.size() < 2) {
                                if (aa < 2) {
                                    if (issue.getCopies() <= 1) {
                                        GetSetBooks db = bookRepo1.findById(id).get();
                                        issue.setBookName(db.getBookName());
                                        issue.setBookId(id);
                                        issue.setStatus("Issued");
                                        IssueBook isB = this.issuedB.save(issue);
                                        GetSetBooks books = rOperation.update(db, copies);
                                        bookRepo1.save(books);
                                        return ResponseEntity.ok(isB);
                                    }
                                    return ResponseEntity.ok("You Can Issue Only One Book ");
                                }
                                return ResponseEntity.ok("You Have Already Issued Two Books ");
                            }
                            return ResponseEntity.ok("Can Not Issue More Than Two Books ");
                        }
                        return ResponseEntity.ok("This Id Is Already Exists ");
                    } else if (as == 0) {
                        return ResponseEntity.ok("No Of Copies Must Be Greater Than 0");
                    }
                    return ResponseEntity.ok("Books Are Not Available");
                } else if (t == 1) {
                    return ResponseEntity.ok("Mobile No Is Greater Than 10");
                }
                return ResponseEntity.ok("Mobile No Is Less Than 10");
            }
            return ResponseEntity.ok("No Of Copies Must Be Less Or Equals 2");
        }
        return ResponseEntity.ok("This Book Id Not Available");
    }

    @PostMapping("/returnBook/{name}")
    public ResponseEntity<?> returnBook(@RequestBody GetSetBooks books, @PathVariable("name") String name) {
        int ab = books.getRating();
        if (ab < 6) {
            GetSetBooks book1 = this.bookRepo1.findById(books.getId()).get();
            List<IssueBook> listIssued = this.issuedB.findAll().stream().filter(a -> a.getReaderName().equals(name)).collect(Collectors.toList());
            listIssued = listIssued.stream().filter(aa -> aa.getBookName().equals(book1.getBookName())).collect(Collectors.toList());
            int findIdToDeleteIssued = 0;
            long penelty = 0;
            int copies = 0;
            for (IssueBook a : listIssued) {
                findIdToDeleteIssued = a.getId();
                copies = a.getCopies();
                penelty = rOperation.dateCals(a.getDateOfIssue());
            }
            GetSetBooks book2 = rOperation.updateReturn(book1, copies);
            book2.setRating(books.getRating());
            this.bookRepo1.save(book2);
            this.issuedB.deleteById(findIdToDeleteIssued);
            if (listIssued.size() == 0) {
                return ResponseEntity.ok("No Data Available");
            }
            return ResponseEntity.ok(" Successfully Completed... \n Your Penalty Is $ :" + penelty);
        }
        return ResponseEntity.ok("Rating Should Be Must Less Or Equals 5 ");
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<?> showSingle(@PathVariable("name") String Name) {
        List<IssueBook> listIssued = this.issuedB.findAll().stream().filter(c -> c.getReaderName().equals(Name)).collect(Collectors.toList());
        if (listIssued.size() == 0) {
            return ResponseEntity.ok("No Data Found For Username : " + Name);
        }
        return ResponseEntity.ok(listIssued);
    }
}
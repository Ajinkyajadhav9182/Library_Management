package library.management.controllers;

import library.management.entity.GetSetBooks;
import library.management.entity.IssueBook;
import library.management.repo.BooksRepositiry;
import library.management.repo.IssuedBooks;
import library.management.services.ReaderOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ReaderController {
    @Autowired
    private BooksRepositiry bookRepo1;
    @Autowired
    private ReaderOperation ro;

    @GetMapping("/showAllR")
    public ResponseEntity<?> showAllR() {
        return ResponseEntity.ok(this.bookRepo1.findAll());
    }

    @Autowired
    private IssuedBooks issuedB;

    @PostMapping("/issueBook/{id}")
    public ResponseEntity<?> issueBook(@RequestBody IssueBook issue, @PathVariable("id") int id) {
        boolean isThere = bookRepo1.existsById(id);
        if (isThere) {
            List<IssueBook> listIssued = this.issuedB.findAll().stream().filter(c -> c.getReaderName().equals(issue.getReaderName())).collect(Collectors.toList());
            if (listIssued.size() <= 1) {
                GetSetBooks db = bookRepo1.findById(id).get();
                issue.setBookName(db.getBookName());
                issue.setBookId(id);
                issue.setStatus("Issued");
                IssueBook isB = this.issuedB.save(issue);
                GetSetBooks books = ro.update(db);
                bookRepo1.save(books);
                return ResponseEntity.ok(isB);
            }
            return ResponseEntity.ok("Can Not Issue More Than Two Books ");
        }
        return ResponseEntity.ok("This Book Id Not Available");
    }

    @PostMapping("/returnBook/{name}")
    public ResponseEntity<?> returnBook(@RequestBody GetSetBooks books, @PathVariable("name") String name) {
        GetSetBooks book1 = this.bookRepo1.findById(books.getId()).get();
        GetSetBooks book2 = ro.updateReturn(book1);
        book2.setRating(books.getRating());
        this.bookRepo1.save(book2);
        List<IssueBook> listIssued = this.issuedB.findAll().stream().filter(a -> a.getReaderName().equals(name)).collect(Collectors.toList());
        listIssued = listIssued.stream().filter(aa -> aa.getBookName().equals(book1.getBookName())).collect(Collectors.toList());
        int findIdToDeleteIssued = 0;
        long penelty = 0;
        for (IssueBook a : listIssued) {
            findIdToDeleteIssued = a.getId();
            penelty = ro.dateCals(a.getDateOfIssue());
        }
        this.issuedB.deleteById(findIdToDeleteIssued);
        if (listIssued.size() == 0) {
            return ResponseEntity.ok("No Data Available");
        }
        return ResponseEntity.ok("Your Penalty Is " + penelty);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> showSingle(@PathVariable("name") String Name) {
        List<IssueBook> listIssued = this.issuedB.findAll().stream().filter(c -> c.getReaderName().equals(Name)).collect(Collectors.toList());
        if (listIssued.size() == 0) {
            return ResponseEntity.ok("No Data Found For Username : " + Name);
        }
        return ResponseEntity.ok(listIssued);
    }
}
package library.management.controllers;

import library.management.entity.GetSetBooks;
import library.management.entity.IssueBook;
import library.management.repo.BooksRepositiry;
import library.management.repo.IssuedBooks;
import library.management.services.AdminOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AdminController {
    @Autowired
    private BooksRepositiry booksRepositiry;

    @PostMapping("/addBook")
    public ResponseEntity<?> addBook(@RequestBody GetSetBooks books) {
        boolean isThere = this.booksRepositiry.existsById(books.getId());
        if (isThere) {
            return ResponseEntity.ok("This BookId Already Exist");
        }
        GetSetBooks saved = this.booksRepositiry.save(books);
        return ResponseEntity.ok(saved);
    }

    @GetMapping("/showAll")
    public ResponseEntity<List<GetSetBooks>> showAll() {
        List<GetSetBooks> listBooks = this.booksRepositiry.findAll();
        if (listBooks.size() == 0) {
            return new ResponseEntity("Books Not Available", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(listBooks);
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<GetSetBooks> showSingle(@PathVariable("id") int id) {
        boolean isThere = booksRepositiry.existsById(id);
        if (isThere) {
            GetSetBooks books = null;
            books = booksRepositiry.findById(id).get();
            return ResponseEntity.ok(books);
        }
        return new ResponseEntity("Book Not Found In The Database", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteSingle(@PathVariable("id") int id) {
        boolean isThere = booksRepositiry.existsById(id);
        if (isThere) {
            booksRepositiry.deleteById(id);
            return "Book Is Deleted";
        }
        return "Book Is Not Available";
    }

    @DeleteMapping("/deleteAll")
    public String deleteAll() {
        this.booksRepositiry.deleteAll();
        return "All Books Deleted Successfully";
    }

    @Autowired
    AdminOperation adOperation;

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody GetSetBooks books, @PathVariable("id") int id) {
        boolean isThere = booksRepositiry.existsById(id);
        if (isThere) {
            GetSetBooks book1 = this.booksRepositiry.findById(id).get();
            GetSetBooks book2 = adOperation.update(book1, books);
            this.booksRepositiry.save(book2);
            return ResponseEntity.ok(book2);
        }
        return ResponseEntity.ok("This Id Is Not Present");
    }

    @Autowired
    private IssuedBooks ibs;

    @GetMapping("/viewIssued")
    public ResponseEntity<?> viewIssuedBooks() {
        List<IssueBook> listIssued = this.ibs.findAll();
        if (listIssued.size() == 0) {
            return new ResponseEntity("Data Not Available", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(listIssued);
    }
}
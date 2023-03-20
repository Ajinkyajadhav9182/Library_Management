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
    private BooksRepositiry BR;

    @PostMapping("/addBook")
    public ResponseEntity<?> addBook(@RequestBody GetSetBooks sc) {
        GetSetBooks gsb = this.BR.save(sc);
        return ResponseEntity.ok(gsb);
    }

    @GetMapping("/showAll")
    public ResponseEntity<List<GetSetBooks>> showAll() {
        List<GetSetBooks> lgsb = this.BR.findAll();
        if (lgsb.size() == 0) {
            return new ResponseEntity("Books Not Available", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(lgsb);
    }

    @GetMapping("/show/{id}")
    public ResponseEntity<GetSetBooks> showSingle(@PathVariable("id") int id) {
        boolean aaa = BR.existsById(id);
        if (aaa) {
            GetSetBooks da = null;
            da = BR.findById(id).get();
            return ResponseEntity.ok(da);
        }
        return new ResponseEntity("Book Not Found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteSingle(@PathVariable("id") int id) {
        boolean aaa = BR.existsById(id);
        if (aaa) {
            BR.deleteById(id);
            return "Book Is Deleted";
        }
        return "Book Is Not Available";
    }

    @DeleteMapping("/deleteAll")
    public String deleteAll() {
        this.BR.deleteAll();
        return "All Books Deleted Successfully";
    }

    @Autowired
    AdminOperation ad;

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody GetSetBooks books, @PathVariable("id") int id) {
        boolean aaa = BR.existsById(id);
        if (aaa) {
            GetSetBooks a = this.BR.findById(id).get();
            GetSetBooks aa = ad.update(a, books);
            this.BR.save(aa);
            return ResponseEntity.ok(aa);
        }
        return ResponseEntity.ok("This Id Is Not Present");
    }

    @Autowired
    private IssuedBooks ibs;

    @GetMapping("/viewIssued")
    public ResponseEntity<?> viewIssuedBooks() {
        List<IssueBook> lisb = this.ibs.findAll();
        if (lisb.size() == 0) {
            return new ResponseEntity("Data Not Available", HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(lisb);
    }
}
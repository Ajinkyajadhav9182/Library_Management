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
    private BooksRepositiry BR1;
    @Autowired
    private ReaderOperation ro;

    @GetMapping("/showAllR")
    public ResponseEntity<?> showAllR() {
        return ResponseEntity.ok(this.BR1.findAll());
    }

    @Autowired
    private IssuedBooks IB;

    @PostMapping("/issueBook/{id}")
    public ResponseEntity<?> issueBook(@RequestBody IssueBook issboo, @PathVariable("id") int id) {
        boolean aaa = BR1.existsById(id);
        if (aaa) {
            List<IssueBook> lsb = this.IB.findAll().stream().filter(c -> c.getReaderName().equals(issboo.getReaderName())).collect(Collectors.toList());
            if (lsb.size() <= 1) {
                GetSetBooks db = BR1.findById(id).get();
                issboo.setBookName(db.getBookName());
                issboo.setBookId(id);
                issboo.setStatus("Issued");
                IssueBook isB = this.IB.save(issboo);
                GetSetBooks GSEB = ro.update(db);
                BR1.save(GSEB);
                return ResponseEntity.ok(isB);
            }
            return ResponseEntity.ok("Can Not Issue More Than Two Books ");
        }
        return ResponseEntity.ok("This Book Id Not Available");
    }

    @PostMapping("/returnBook/{name}")
    public ResponseEntity<?> returnBook(@RequestBody GetSetBooks sa, @PathVariable("name") String name) {
        GetSetBooks aaa = this.BR1.findById(sa.getId()).get();//find book
        GetSetBooks asaw = ro.updateReturn(aaa);//find and update book copies
        asaw.setRating(sa.getRating());
        this.BR1.save(asaw);
        List<IssueBook> lisba = this.IB.findAll().stream().filter(a -> a.getReaderName().equals(name)).collect(Collectors.toList());
        lisba = lisba.stream().filter(aa -> aa.getBookName().equals(aaa.getBookName())).collect(Collectors.toList());
        int aa = 0;
        int p = 0;
        for (IssueBook a : lisba) {
            aa = a.getId();
            p = ro.dateCalsi(a.getDateOfIssue());
        }
        this.IB.deleteById(aa);
        if (lisba.size() == 0) {
            return ResponseEntity.ok("No Data Available");
        }
        return ResponseEntity.ok("Your Penalty Is " + p);
    }

    @GetMapping("/{name}")
    public ResponseEntity<?> showSingle(@PathVariable("name") String Name) {
        List<IssueBook> lsb = this.IB.findAll().stream().filter(c -> c.getReaderName().equals(Name)).collect(Collectors.toList());
        if (lsb.size() == 0) {
            return ResponseEntity.ok("No Data Found For Username : " + Name);
        }
        return ResponseEntity.ok(lsb);
    }
}
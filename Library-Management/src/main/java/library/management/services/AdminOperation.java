package library.management.services;

import library.management.entity.GetSetBooks;
import org.springframework.stereotype.Component;

@Component
public class AdminOperation {
    public GetSetBooks update(GetSetBooks c, GetSetBooks v) {
        c.setBookName(v.getBookName());
        c.setAuthorName(v.getAuthorName());
        c.setPublication(v.getPublication());
        c.setPrice(v.getPrice());
        c.setAvailableCopies(v.getAvailableCopies());
        return c;
    }
}
package library.management.services;

import library.management.entity.GetSetBooks;
import org.springframework.stereotype.Component;

@Component
public class AdminOperation {
    public GetSetBooks update(GetSetBooks book1, GetSetBooks book2) {
        book1.setBookName(book2.getBookName());
        book1.setAuthorName(book2.getAuthorName());
        book1.setPublication(book2.getPublication());
        book1.setPrice(book2.getPrice());
        book1.setAvailableCopies(book2.getAvailableCopies());
        return book1;
    }
}
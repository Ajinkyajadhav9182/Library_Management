package library.management.services;

import library.management.entity.GetSetBooks;
import library.management.repo.IssuedBooks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class ReaderOperation {
    public GetSetBooks update(GetSetBooks book) {
        book.setAvailableCopies(book.getAvailableCopies() - 1);
        return book;
    }

    public GetSetBooks updateReturn(GetSetBooks book) {
        book.setAvailableCopies(book.getAvailableCopies() + 1);
        return book;
    }

    @Autowired
    private IssuedBooks issuedBooks;

    public long dateCals(LocalDate nowDate1) {
        try {
            LocalDate nowDate = LocalDate.now();
            long countDays = ChronoUnit.DAYS.between(nowDate1, nowDate);
            if (countDays > 7) {
                countDays = (long) (countDays * 1.5);
                return countDays;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }
}

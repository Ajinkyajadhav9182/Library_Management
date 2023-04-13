package library.management.services;

import library.management.entity.GetSetBooks;
import library.management.entity.IssueBook;
import library.management.repo.IssuedBooks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class ReaderOperation {
    public GetSetBooks update(GetSetBooks book, int copies) {
        book.setAvailableCopies(book.getAvailableCopies() - copies);
        return book;
    }

    public GetSetBooks updateReturn(GetSetBooks book, int copies) {
        book.setAvailableCopies(book.getAvailableCopies() + copies);
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

    public boolean available(GetSetBooks getSetBooks, IssueBook issueBook) {
        if (issueBook.getCopies() == 0 && issueBook.getCopies() < 3) {
            return false;
        } else if (issueBook.getCopies() <= getSetBooks.getAvailableCopies()) {
            return true;
        }
        return false;
    }
}

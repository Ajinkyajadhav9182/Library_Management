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
    public GetSetBooks update(GetSetBooks book, int b) {
        book.setAvailableCopies(book.getAvailableCopies() - b);
        return book;
    }

    public GetSetBooks updateReturn(GetSetBooks book, int b) {
        book.setAvailableCopies(book.getAvailableCopies() + b);
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

    public int countDigit(long num) {
        int count = 0;
        while (num != 0) {
            num = num / 10;
            count++;
        }
        if (count == 10) {
            return 0;
        } else if (count >= 10) {
            return 1;
        } else {
            return 2;
        }
    }

    public int available(GetSetBooks a, IssueBook d) {
        if (d.getCopies() == 0) {
            return 0;
        } else if (d.getCopies() <= a.getAvailableCopies()) {
            return 1;
        }
        return 3;
    }
}

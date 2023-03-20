package library.management.services;

import library.management.entity.GetSetBooks;
import library.management.repo.IssuedBooks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class ReaderOperation {
    public GetSetBooks update(GetSetBooks c) {
        c.setAvailableCopies(c.getAvailableCopies() - 1);
        return c;
    }

    public GetSetBooks updateReturn(GetSetBooks c) {
        c.setAvailableCopies(c.getAvailableCopies() + 1);
        return c;
    }

    @Autowired
    private IssuedBooks IB;

    public int dateCalsi(LocalDate nowDate1) {
        try {
            LocalDate nowDate = LocalDate.now();
            long s = ChronoUnit.DAYS.between(nowDate1, nowDate);
            if (s > 7) {
                System.out.println(s);
                return 50;
            }
        } catch (Exception a) {
            a.printStackTrace();
        }
        return 0;
    }
}

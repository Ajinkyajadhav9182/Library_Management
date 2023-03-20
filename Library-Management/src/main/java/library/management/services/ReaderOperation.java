package library.management.services;

import library.management.entity.GetSetBooks;
import library.management.entity.IssueBook;
import library.management.repo.IssuedBooks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

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
        // List<IssueBook> lsb = this.IB.findAll().stream().filter(c -> c.getReaderName().equals(che)).collect(Collectors.toList());
        try {
            LocalDate nowDate = LocalDate.now();
            System.out.println(nowDate + "      ????     " + nowDate1);
            long s = ChronoUnit.DAYS.between(nowDate1, nowDate);
            if (s > 7) {
                System.out.println(s);
                return 50;
            }
            System.out.println(s);
        } catch (Exception a) {
            a.printStackTrace();
        }
        return 0;
    }
}

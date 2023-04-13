package library.management.entity;

import lombok.Data;
import org.hibernate.validator.constraints.Range;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Document(collection = "issued")
public class IssueBook {
    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";
    @Id
    private int id;
    private int bookId;
    private String bookName;
    @NotEmpty(message = "Reader Name Should Not Be Null")
    private String readerName;
    @NotEmpty(message = "Address Should Not Be Null")
    private String address;
    @Digits(integer = 10, fraction = 0, message = "phone_no should be exact 10 characters.")
    private Long contactNo;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDate dateOfIssue = LocalDate.now();
    @Min(value = 1, message = "Copies Should Be Greater Than 0")
    @Max(value = 2, message = "Copies Should Be Less Than 2")
    private int copies;
}
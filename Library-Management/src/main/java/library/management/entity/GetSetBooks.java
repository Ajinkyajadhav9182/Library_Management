package library.management.entity;

import lombok.*;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;


@Data
@Document(collection = "books")
public class GetSetBooks {
    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";
    private int id;
    @NotNull(message = "Book Name Should Not Be Null")
    private String bookName;
    @NotNull(message = "Author Name Should Not Be Null")
    private String authorName;
    @NotNull(message = "Publication Should Not Be Null")
    private String publication;
    @Min(value = 1, message = "Price Should Be Greater Than 0")
    private float price;
    @Min(value = 1, message = "Available Copies Should Be Greater Than 0")
    private int availableCopies;
    private int rating;
}
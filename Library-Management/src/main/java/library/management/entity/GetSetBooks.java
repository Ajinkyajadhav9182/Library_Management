package library.management.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "books")
public class GetSetBooks {
    @Transient
    public static final String SEQUENCE_NAME = "user_sequence";
    private int id;
    private String bookName;
    private String authorName;
    private String publication;
    private float price;
    private int availableCopies;
    private int rating;
}
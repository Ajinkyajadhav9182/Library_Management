package library.management.entity;

import lombok.Getter;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Document(collection = "books")
public class GetBooks {
    private int id;
    private String bookName;
    private String authorName;
    private String publication;
    private float price;
}

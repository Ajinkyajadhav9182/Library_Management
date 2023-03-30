package library.management.entity;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "books")
public class GetBooks {
    private int id;
    private String bookName;
    private String authorName;
    private String publication;
    private float price;

    public int getId() {
        return id;
    }

    public String getBookName() {
        return bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getPublication() {
        return publication;
    }

    public float getPrice() {
        return price;
    }

    public int getRating() {
        return rating;
    }

    private int rating;

    @Override
    public String toString() {
        return "GetBooks{" +
                "id=" + id +
                ", bookName='" + bookName + '\'' +
                ", authorName='" + authorName + '\'' +
                ", publication='" + publication + '\'' +
                ", price=" + price +
                ", rating=" + rating +
                '}';
    }
}

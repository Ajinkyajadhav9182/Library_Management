package library.management.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "db_sequence1")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DbSequence1 {
    @Id
    private String id;
    private int seq;
}
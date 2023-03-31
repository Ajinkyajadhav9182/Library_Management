package library.management.repo;

import library.management.entity.IssueBook;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IssuedBooks extends MongoRepository<IssueBook, Integer> {
}

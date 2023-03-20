package library.management.repo;

import library.management.entity.GetSetBooks;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BooksRepositiry extends MongoRepository<GetSetBooks,Integer> {
}

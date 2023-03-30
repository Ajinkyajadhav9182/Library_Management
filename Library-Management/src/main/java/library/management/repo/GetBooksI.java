package library.management.repo;

import library.management.entity.GetBooks;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface GetBooksI extends MongoRepository<GetBooks, Integer> {
}

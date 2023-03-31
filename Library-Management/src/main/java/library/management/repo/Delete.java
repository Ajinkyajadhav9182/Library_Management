package library.management.repo;

import library.management.entity.DbSequence;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Delete extends MongoRepository<DbSequence, Integer> {
}

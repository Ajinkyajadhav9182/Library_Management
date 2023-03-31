package library.management.services;

import org.springframework.stereotype.Service;
import library.management.entity.Dbsequence1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Objects;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;

@Service
public class SequenceGeneratorService1 {


    @Autowired
    private MongoOperations mongoOperations;


    public int getSequenceNumber(String sequenceName) {
        //get sequence no
        Query query = new Query(Criteria.where("id").is(sequenceName));
        //update the sequence no
        Update update = new Update().inc("seq", 1);
        //modify in document
        Dbsequence1 counter = mongoOperations
                .findAndModify(query,
                        update, options().returnNew(true).upsert(true),
                        Dbsequence1.class);

        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }
}
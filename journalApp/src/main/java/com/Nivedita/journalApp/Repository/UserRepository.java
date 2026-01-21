package com.Nivedita.journalApp.Repository;
import com.Nivedita.journalApp.GeneralEntry.UserEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends  MongoRepository<UserEntry, ObjectId> {
    UserEntry findByUserName(String username);
    void  deleteByName(String name);
}

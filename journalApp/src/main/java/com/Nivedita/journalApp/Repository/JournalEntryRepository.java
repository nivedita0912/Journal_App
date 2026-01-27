package com.Nivedita.journalApp.Repository;
import com.Nivedita.journalApp.GeneralEntry.journalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface  JournalEntryRepository extends  MongoRepository<journalEntry, ObjectId> {

}

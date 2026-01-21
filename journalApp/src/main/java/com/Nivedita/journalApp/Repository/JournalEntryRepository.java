package com.Nivedita.journalApp.Repository;
import com.Nivedita.journalApp.GeneralEntry.journalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface  JournalEntryRepository extends  MongoRepository<journalEntry, ObjectId> {

}

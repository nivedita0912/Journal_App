package com.Nivedita.journalApp.services;
import com.Nivedita.journalApp.GeneralEntry.UserEntry;
import com.Nivedita.journalApp.GeneralEntry.journalEntry;
import com.Nivedita.journalApp.Repository.JournalEntryRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.StackTracePrinter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
@Slf4j
@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;
    @Autowired
    private UserEntryService userEntryService;

    public List<journalEntry> delete( String userName,ObjectId id) {
        UserEntry user = userEntryService.findByUserName(userName);
        if(user != null){
            user.getUserEntryList().removeIf(x -> x.getId().equals(id));
            userEntryService.saveGeneralEntry(user);
        }
        journalEntryRepository.deleteById(id);

        return journalEntryRepository.findAll();
    }

    @Transactional
    public journalEntry saveGeneralEntry(journalEntry entry, String userName) {

        UserEntry user = userEntryService.findByUserName(userName);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        entry.setDate(LocalDateTime.now());

        journalEntry saved = journalEntryRepository.save(entry);

        user.getUserEntryList().add(saved);
        userEntryService.saveGeneralEntry(user);

        return saved;
    }

//    public ResponseEntity<?> saveGeneralEntry(journalEntry JournalEntry, String userName) {
//        try{
//            UserEntry user = userEntryService.findByUserName(userName);
//            JournalEntry.setDate(LocalDateTime.now());
//            if(user == null){
//                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//            }
//            user.getUserEntryList().add(JournalEntry);
//            userEntryService.saveGeneralEntry(user);
//        }
//        catch (Exception e){
//            System.out.println(e);
//            throw  new RuntimeException("there is an exception");
//        }
//        return new ResponseEntity<>(HttpStatus.CREATED);




        public void saveGeneralEntry(journalEntry JournalEntry) {
        journalEntryRepository.save(JournalEntry);
    }

    public List<journalEntry> getAll() {
        return journalEntryRepository.findAll();
    }


    public Optional<journalEntry> findById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }



}

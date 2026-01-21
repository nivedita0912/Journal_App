package com.Nivedita.journalApp.controller;
import com.Nivedita.journalApp.GeneralEntry.UserEntry;
import com.Nivedita.journalApp.services.UserEntryService;
import org.bson.types.ObjectId;
import com.Nivedita.journalApp.GeneralEntry.journalEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/journal")
public class entrycontroller {

    @Autowired
    private com.Nivedita.journalApp.services.JournalEntryService JournalEntryService;

   @Autowired
   private UserEntryService userEntryService;

    @GetMapping("{userName}")
    public ResponseEntity<?> getAllJournalEntriesOfUser(@PathVariable String userName) {
        UserEntry user = userEntryService.findByUserName(userName);
         List<journalEntry> all = user.getUserEntryList();
       if(all != null && !all.isEmpty()){
           return new ResponseEntity<>(all,HttpStatus.OK);
       }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{myid}")
    public ResponseEntity<journalEntry> find(@PathVariable ObjectId myid) {
        Optional<journalEntry> temp =JournalEntryService.findById(myid);
       if(temp.isPresent()){
           return new ResponseEntity<>(temp.get(), HttpStatus.OK) ;
       }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
    }

    @PostMapping("{userName}")
    public ResponseEntity<journalEntry> createEntries(@RequestBody journalEntry myentry,@PathVariable String userName) {
        try {
            JournalEntryService.saveGeneralEntry(myentry,userName);
            return new ResponseEntity<>(myentry,HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/id/{userName}/{myid}")
    public List<journalEntry>  delete(@PathVariable String userName,@PathVariable ObjectId myid) {
        return  JournalEntryService.delete(userName, myid);
    }

    @PutMapping("/id/{userName}/{myid}")
    public ResponseEntity<?> update(
            @RequestBody journalEntry a,
            @PathVariable String userName,
            @PathVariable ObjectId myid){
        journalEntry temp = JournalEntryService.findById(myid).orElse(null);
        if(temp != null) {
       temp.setTitle(a.getTitle() != null && !a.getTitle().isEmpty() ? a.getTitle() : temp.getTitle());
       temp.setContent(a.getContent() != null && !a.getContent().isEmpty() ? a.getContent() : temp.getContent());
            JournalEntryService.saveGeneralEntry(temp);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }



}

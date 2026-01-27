package com.Nivedita.journalApp.controller;
import com.Nivedita.journalApp.GeneralEntry.UserEntry;
import com.Nivedita.journalApp.services.UserEntryService;
import org.bson.types.ObjectId;
import com.Nivedita.journalApp.GeneralEntry.journalEntry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class entrycontroller {

    @Autowired
    private com.Nivedita.journalApp.services.JournalEntryService JournalEntryService;

   @Autowired
   private UserEntryService userEntryService;

    @GetMapping("/get")
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        UserEntry user = userEntryService.findByUserName(name);
         List<journalEntry> all = user.getUserEntryList();
       if(all != null && !all.isEmpty()){
           return new ResponseEntity<>(all,HttpStatus.OK);
       }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{myid}")
    public ResponseEntity<journalEntry> find(@PathVariable ObjectId myid) {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String userName  = authentication.getName();
      UserEntry userEntry =  userEntryService.findByUserName(userName);
     List<journalEntry> collect = userEntry.getUserEntryList().stream().filter(x -> x.getId().equals(myid)).collect(Collectors.toList());
     if(!collect.isEmpty()){
         Optional<journalEntry> temp =JournalEntryService.findById(myid);
         if(temp.isPresent()){
             return new ResponseEntity<>(temp.get(), HttpStatus.OK) ;
         }
     }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND) ;
    }

    @PostMapping("/post")
    public ResponseEntity<journalEntry> createEntries(@RequestBody journalEntry myentry) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String name = auth.getName();
            JournalEntryService.saveGeneralEntry(myentry,name);
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

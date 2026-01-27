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
        if (all != null && !all.isEmpty()) {
            return new ResponseEntity<>(all, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{myid}")
    public ResponseEntity<journalEntry> find(@PathVariable ObjectId myid) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        UserEntry userEntry = userEntryService.findByUserName(userName);
        List<journalEntry> collect = userEntry.getUserEntryList().stream().filter(x -> x.getId().equals(myid)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<journalEntry> temp = JournalEntryService.findById(myid);
            if (temp.isPresent()) {
                return new ResponseEntity<>(temp.get(), HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/post")
    public ResponseEntity<journalEntry> createEntries(@RequestBody journalEntry myentry) {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String name = auth.getName();
            JournalEntryService.saveGeneralEntry(myentry, name);
            return new ResponseEntity<>(myentry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/id/{myid}")
    public ResponseEntity<?> delete(@PathVariable ObjectId myid) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        boolean response = JournalEntryService.delete(name, myid);
        if (response) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/id/{myid}")
    public ResponseEntity<?> update(
            @RequestBody journalEntry a,
            @PathVariable ObjectId myid) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        UserEntry userEntry = userEntryService.findByUserName(name);
        List<journalEntry> collect = userEntry.getUserEntryList().stream().filter(x -> x.getId().equals(myid)).collect(Collectors.toList());
        if (!collect.isEmpty()) {
            Optional<journalEntry> temp = JournalEntryService.findById(myid);
            if (temp.isPresent()) {
                journalEntry old = temp.get();
                old.setTitle(a.getTitle() != null && !a.getTitle().isEmpty() ? a.getTitle() : old.getTitle());
                old.setContent(a.getContent() != null && !a.getContent().isEmpty() ? a.getContent() : old.getContent());
                JournalEntryService.saveEntry(old);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

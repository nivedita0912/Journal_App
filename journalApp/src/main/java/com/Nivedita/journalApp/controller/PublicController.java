package com.Nivedita.journalApp.controller;

import com.Nivedita.journalApp.GeneralEntry.UserEntry;
import com.Nivedita.journalApp.services.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {
    @Autowired
    private UserEntryService userEntryService;

    @PostMapping("/create")
    public Boolean addUser(@RequestBody UserEntry user){
        userEntryService.saveUser(user);
        return true;
    }
}

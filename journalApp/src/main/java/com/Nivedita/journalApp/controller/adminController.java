package com.Nivedita.journalApp.controller;


import com.Nivedita.journalApp.GeneralEntry.UserEntry;
import com.Nivedita.journalApp.services.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/Admin")
public  class adminController{
//only admin will create the admin
@Autowired
   private UserEntryService userEntryService;
@GetMapping("/allUser")
    public ResponseEntity<?> getAllUsers(){
        List<UserEntry> allUser = userEntryService.getAll();
        if(!allUser.isEmpty() && allUser!=null){
            return new ResponseEntity<>(allUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/create/admin")
    public  void  createUserAdmin(@RequestBody UserEntry userEntry){
    userEntryService.saveAdmin(userEntry);
    }
}

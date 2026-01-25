package com.Nivedita.journalApp.controller;

import com.Nivedita.journalApp.GeneralEntry.UserEntry;
import com.Nivedita.journalApp.Repository.UserRepository;
import com.Nivedita.journalApp.services.UserEntryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository repo;
    @Autowired
    private  UserEntryService userEntryService;

    @GetMapping("/get")
    public List<UserEntry> getAllUsers(){
        return userEntryService.getAll();
    }
//    @PostMapping
//    public Boolean addUser(@RequestBody UserEntry user){
//         userEntryService.saveUser(user);
//    return true;
//    }
    @PutMapping("/put")
    public ResponseEntity<?> updateUser(@RequestBody UserEntry user){
       Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName();
        UserEntry old = userEntryService.findByUserName(name);
        if(old != null){
           old.setPassword(user.getPassword());
            old.setUserName(user.getUserName());
           userEntryService.saveUser(old);
        }
        return  new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping
   public boolean deleteUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         repo.deleteByUserName(auth.getName());
        return true;
    }

}

package com.Nivedita.journalApp.services;
import com.Nivedita.journalApp.GeneralEntry.UserEntry;
import com.Nivedita.journalApp.GeneralEntry.journalEntry;
import com.Nivedita.journalApp.Repository.JournalEntryRepository;
import com.Nivedita.journalApp.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class UserEntryService {

    @Autowired
    private UserRepository userEntryRepository;
    private  static final PasswordEncoder passwordEncoder  = new BCryptPasswordEncoder();

    public void saveGeneralEntry(UserEntry userEntry) {
         userEntryRepository.save(userEntry);
    }
public  void saveAdmin(UserEntry userEntry){
    userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
userEntry.setRoles(Arrays.asList("User","ADMIN"));
    }

    public void saveUser(UserEntry userEntry) {
       userEntry.setPassword(passwordEncoder.encode(userEntry.getPassword()));
        userEntry.setRoles(Arrays.asList("User"));
       userEntryRepository.save(userEntry);
    }

    public List<UserEntry> getAll() {
        return userEntryRepository.findAll();
    }

    public Optional<UserEntry>  findById(ObjectId id) {
      return  userEntryRepository.findById(id);
    }

    public UserEntry findByUserName(String userName) {
        return  userEntryRepository.findByUserName(userName);
    }

    public void deleteById(ObjectId id) {
        userEntryRepository.deleteById(id);
    }

}

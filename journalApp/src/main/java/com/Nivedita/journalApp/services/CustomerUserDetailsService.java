package com.Nivedita.journalApp.services;
//impl in that security one

import com.Nivedita.journalApp.GeneralEntry.UserEntry;
import com.Nivedita.journalApp.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       UserEntry User = userRepo.findByUserName(username);
       if(User != null){
         UserDetails  userDetails =  org.springframework.security.core.userdetails.User.builder()
                   .username(User.getUserName())
                   .password(User.getPassword())
                   .roles(User.getRoles().toArray(new String[0]))
                   .build();
           return userDetails;
       }
        throw  new UsernameNotFoundException("No user found , wrong input " + username);
    }
}

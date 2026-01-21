package com.Nivedita.journalApp.GeneralEntry;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.mongodb.core.index.IndexOptions;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Slf4j
@NoArgsConstructor
@Document(collection = "users")
@Data
public class UserEntry {
    @Id
    private ObjectId id;
   @Indexed(unique = true)
    @NonNull
    private String userName;
    @NonNull
    private  String password;
    @DBRef
    private List<journalEntry> userEntryList = new ArrayList<>();
    private List<String> roles;
}

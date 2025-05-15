package com.ritik.journal.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ritik.journal.entity.JournalEntry;
import com.ritik.journal.entity.User;
import com.ritik.journal.services.JournalEntryService;
import com.ritik.journal.services.UserService;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAllJournalEntriesOfUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> entries = user.getJournalEntries();
        return new ResponseEntity<>(entries, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry journalEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        JournalEntry createdEntry = journalEntryService.createEntry(journalEntry, userName);
        return new ResponseEntity<>(createdEntry, HttpStatus.CREATED);
    }
    
    @GetMapping("id/{myId}")
    public ResponseEntity<JournalEntry> getJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());
        try {
            if(!collect.isEmpty()){
                JournalEntry entry = journalEntryService.getJournalEntryById(myId);
                return new ResponseEntity<>(entry, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @DeleteMapping("id/{myId}")
    public ResponseEntity<Void> deleteJournalEntryById(@PathVariable ObjectId myId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());
        try {
            if(!collect.isEmpty()){;
                boolean deleted = journalEntryService.deleteJournalEntryById(myId, userName);
                if (deleted) {
                    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PutMapping("id/{myId}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(@PathVariable ObjectId myId, @RequestBody JournalEntry newEntry) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName= authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> collect = user.getJournalEntries().stream().filter(x->x.getId().equals(myId)).collect(Collectors.toList());
        try {
            if(!collect.isEmpty()){;
                JournalEntry updatedEntry = journalEntryService.updateJournalEntryById(myId, newEntry);
                return new ResponseEntity<>(updatedEntry, HttpStatus.OK);
            }
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
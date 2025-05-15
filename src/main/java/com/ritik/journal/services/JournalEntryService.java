package com.ritik.journal.services;

import java.time.LocalDateTime;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ritik.journal.entity.JournalEntry;
import com.ritik.journal.entity.User;
import com.ritik.journal.repository.JournalEntryRepository;

@Service
public class JournalEntryService {
    

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserService userService;

    public List<JournalEntry> getAllJournalEntriesOfUser() {
        return journalEntryRepository.findAll();
    }

    // @Transactional
    public JournalEntry createEntry(JournalEntry journalEntry, String userName) {
        User user = userService.findByUserName(userName);
        
        journalEntry.setDate(LocalDateTime.now());
        JournalEntry saved =  journalEntryRepository.save(journalEntry);

        user.getJournalEntries().add(saved);
        userService.createUser(user);

        return saved;
    }
    
    public JournalEntry getJournalEntryById(ObjectId myId) {
        return journalEntryRepository.findById(myId).get();
    }
    
    public boolean deleteJournalEntryById(ObjectId myId, String userName) {
        User user = userService.findByUserName(userName);

        user.getJournalEntries().removeIf(x -> x.getId().equals(myId));
        userService.createUser(user);
        journalEntryRepository.deleteById(myId);
        return true;
    }

    public JournalEntry updateJournalEntryById(ObjectId myId,JournalEntry journalEntry) {
        JournalEntry existingEntry = journalEntryRepository.findById(myId)
        .get();
        
        if (journalEntry.getTitle() != null) {
            existingEntry.setTitle(journalEntry.getTitle());
        }
        
        if (journalEntry.getContent() != null) {
            existingEntry.setContent(journalEntry.getContent());
        }
        
        return journalEntryRepository.save(existingEntry);
    }
} 

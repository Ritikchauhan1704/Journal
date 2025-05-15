package com.ritik.journal.services;

import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ritik.journal.entity.User;
import com.ritik.journal.repository.UserRepository;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }
    public User createNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Arrays.asList("USER"));
        return userRepository.save(user);
    }

    public User getUserById(ObjectId myId) {
        return userRepository.findById(myId).get();
    }

    public boolean deleteUserById(ObjectId myId) {
        userRepository.deleteById(myId);
        return true;
    }

    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

}

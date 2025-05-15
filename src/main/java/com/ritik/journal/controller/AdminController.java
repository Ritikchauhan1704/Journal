package com.ritik.journal.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ritik.journal.entity.User;
import com.ritik.journal.services.UserService;
import org.springframework.web.bind.annotation.GetMapping;



@RestController
@RequestMapping("/admin")
public class AdminController {
    
    @Autowired
    private UserService userService;


    @GetMapping("/get-users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUser();
        if(users !=null && !users.isEmpty()) {
            return new ResponseEntity<>(users,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

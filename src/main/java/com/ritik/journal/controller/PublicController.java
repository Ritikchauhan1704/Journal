package com.ritik.journal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ritik.journal.entity.User;
import com.ritik.journal.services.UserService;



@RestController
@RequestMapping("/public")
public class PublicController {
    
    @Autowired
    private UserService userService;

    @PostMapping("/create-user")
    public User creatUser(@RequestBody User user) {
        return userService.createNewUser(user);
    }

}

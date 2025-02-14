package com.example.practice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.practice.dto.User;
import com.example.practice.repository.UserRepository;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/count")
    public Integer getUserByCount() {
        return userRepository.count();
    }
    
    @GetMapping("/allUser")
    public List<User> getUser() {
        return userRepository.findAll();
    }

    @GetMapping("/user")
    public User getUserById(@RequestParam int id) {
        return userRepository.findById(id);
    }
}

package com.example.practice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.practice.dto.User;
import com.example.practice.repository.UserRepository;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/repository_1")
    public Integer getCountRepository() {
        int getCount = userRepository.count();
        return getCount;
    }
    
    @GetMapping("/repository_2")
    public List<User> getUserRepository() {
        List<User> getUser = userRepository.findAll();
        return getUser;
    }
}

package com.example.practice.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.practice.dto.User;
import com.example.practice.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    @GetMapping("/update")
    public void getUpdateUser(
        @RequestParam int id,
        @RequestHeader String mail,
        @RequestHeader String password,
        @RequestHeader String roles,
        @RequestHeader String lastLogined,
        @RequestHeader Boolean enabled) {
            userRepository.updateLastLogined(id, mail, password, roles, LocalDateTime.parse(lastLogined), enabled);
            log.info("更新");
    }

    @GetMapping("/insert")
    public void getInsertUser(
        @RequestHeader String mail,
        @RequestHeader String password,
        @RequestHeader String time,
        @RequestHeader String roles) {
            userRepository.insert(mail, password, LocalDateTime.parse(time), roles);
            log.info("挿入");
        }
}

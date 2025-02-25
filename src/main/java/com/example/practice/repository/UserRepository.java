package com.example.practice.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.example.practice.dto.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.practice.repository.mapper.UserMapper;

@Repository
public class UserRepository {
    @Autowired
    private UserMapper userMapper;

    public int count() {
        return userMapper.count();
    }

    public List<User> findAll() {
        return userMapper.findAll();
    }

    public User findById(int id) {
        return userMapper.findById(id);
    }

    public void updateLastLogined(int id, LocalDateTime lastLogined) {
        userMapper.updateLastLogined(id, lastLogined);
    }

    public void insert(String mail, String password, LocalDateTime time, String roles) {
        userMapper.insert(mail, password, time, roles);
    }
}

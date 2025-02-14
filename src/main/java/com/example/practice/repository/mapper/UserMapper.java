package com.example.practice.repository.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.practice.dto.User;

@Mapper
public interface UserMapper {
    int count();
    List<User> findAll();
    User findById(int id);
}

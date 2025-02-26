package com.example.practice.repository.mapper;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.practice.dto.User;

@Mapper
public interface UserMapper {
    int count();
    List<User> findAll();
    User findById(int id);

    void updateLastLogined(
        @Param("id") int id,
        @Param("mail") String mail,
        @Param("password") String password,
        @Param("roles") String roles,
        @Param("time") LocalDateTime lastLogined,
        @Param("enabled") Boolean enabled
        );
    
    void insert(
        @Param("mail") String mail,
        @Param("password") String password,
        @Param("time") LocalDateTime time,
        @Param("roles") String roles
    );
}

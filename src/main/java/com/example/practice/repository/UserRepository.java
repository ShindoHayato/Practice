package com.example.practice.repository;

import java.time.LocalDateTime;
import java.util.List;

import com.example.practice.dto.User;
import com.example.practice.exception.ApplicationException;
import com.example.practice.exception.Error;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.TransientDataAccessException;
import org.springframework.stereotype.Repository;

import com.example.practice.repository.mapper.UserMapper;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Repository
public class UserRepository {
    @Autowired
    private UserMapper userMapper;

    public int count() {
        try {
            return userMapper.count();
        } catch (TransientDataAccessException e) {  //transientDataAccessException:一時的な問題を示す例外
            log.warn("count user retryable exception. " + e.getMessage());
            throw new ApplicationException(Error.DB_ERROR, e);
        } catch (DataAccessException e) {   //DataAccessException:一般的なデータアクセスの問題を示す例外
            log.error("count user exception. " + e);
            throw new ApplicationException(Error.DB_ERROR, e);
        }
    }

    public List<User> findAll() {
        try {
            return userMapper.findAll();
        } catch (TransientDataAccessException e) {
            log.warn("find all user retryable exception. " + e.getMessage());
            throw new ApplicationException(Error.DB_ERROR, e);
        } catch (DataAccessException e) {
            log.error("find all user exception. " + e);
            throw new ApplicationException(Error.DB_ERROR, e);
        }
    }

    public User findById(int id) {
        try {
            return userMapper.findById(id);
        } catch (TransientDataAccessException e) {
            log.warn("find user by id retryable exception. " + e.getMessage());
            throw new ApplicationException(Error.DB_ERROR, e);
        } catch (DataAccessException e) {
            log.error("find user by id exception. " + e);
            throw new ApplicationException(Error.DB_ERROR, e);
        }
    }

    public void updateLastLogined(int id, String mail, String password, String roles, LocalDateTime lastLogined, Boolean enabled) {
        try {
            userMapper.updateLastLogined(id, mail, password, roles, lastLogined, enabled);
        } catch (TransientDataAccessException e) {
            log.warn("update user last logined retryable exception. " + e.getMessage());
            throw new ApplicationException(Error.DB_ERROR, e);
        } catch (DataAccessException e) {
            log.error("update user last logined exception. " + e);
            throw new ApplicationException(Error.DB_ERROR, e);
        }
    }

    public void insert(String mail, String password, LocalDateTime time, String roles) {
        try {
            userMapper.insert(mail, password, time, roles);
        } catch (TransientDataAccessException e) {
            log.warn("create user retryable exception. " + e.getMessage());
            throw new ApplicationException(Error.DB_ERROR, e);
        } catch (DataAccessException e) {
            log.error("create user exception. " + e);
            throw new ApplicationException(Error.DB_ERROR, e);
        }
    }
}

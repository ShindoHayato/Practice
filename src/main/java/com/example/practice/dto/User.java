package com.example.practice.dto;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class User {
    private int id;
    private String mail;
    private String password;
    private String roles;
    private LocalDateTime created;
    private LocalDateTime last_logined;
    private boolean enabled;
}

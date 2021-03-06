package com3014.coursework.group6.model;

import org.springframework.stereotype.Component;

/**
 * Model for the login attributes when a user is logging in
 */
@Component
public class Login {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}

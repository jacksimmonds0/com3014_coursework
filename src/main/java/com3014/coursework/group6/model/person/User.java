package com3014.coursework.group6.model.person;

public class User extends Person {

    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String status;
    private String role;

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
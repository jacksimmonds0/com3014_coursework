package com3014.coursework.group6.service;

import com3014.coursework.group6.model.Login;
import com3014.coursework.group6.model.person.User;

import java.util.List;

public interface UserService {

    void register(User user);

    void assignUserRole(String username);

    boolean validateUser(Login login);

    List<User> getUserList(int userID);

    String getUserRole(String username);

    boolean userExists(String username);

    User updateDetails(User user);

    boolean correctPasswordForUser(int id, String password);

    void updatePasswordForUser(int id, String password);

    void changeUserStatus(int id, String status);

    User getUserByUsername(Login login);

    boolean userAccountActive(Login login);

    User getUserById(int id);
}
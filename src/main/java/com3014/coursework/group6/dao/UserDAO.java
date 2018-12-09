package com3014.coursework.group6.dao;

import com3014.coursework.group6.model.Login;
import com3014.coursework.group6.model.person.User;

import java.util.List;

public interface UserDAO {

    void register(User user);

    void assignUserRole(String username);

    boolean userExists(String username);

    User validateUser(Login login);

    List<User> getUserList();

    String getUserRole(String username);

    void updateDetails(User updatedUser);

    User getUserById(int id);

    void updatePasswordForUser(int id, String password);

    void deleteUser(int id);

    User getUserByUsername(Login login);
}
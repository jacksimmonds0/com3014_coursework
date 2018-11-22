package com3014.coursework.group6.dao;

import com3014.coursework.group6.model.Login;
import com3014.coursework.group6.model.person.User;

import java.util.List;

public interface UserDao {
    void register(User user);

    void assignUserRole(String username);

    boolean userExists(String username);

    User validateUser(Login login);

    List getUserRoles(String username);

}
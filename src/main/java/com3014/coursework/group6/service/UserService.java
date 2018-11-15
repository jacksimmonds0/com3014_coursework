package com3014.coursework.group6.service;

import com3014.coursework.group6.model.Login;
import com3014.coursework.group6.model.User;

import java.util.List;

public interface UserService {

    void register(User user);

    void assignUserRole(String username);

    User validateUser(Login login);

    List getUserRoles(String username);

    boolean userExists(String username);
}
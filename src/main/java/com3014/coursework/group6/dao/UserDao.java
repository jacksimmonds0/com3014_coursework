package com3014.coursework.group6.dao;

import com3014.coursework.group6.model.Login;
import com3014.coursework.group6.model.User;

public interface UserDao {
    void register(User user);

    User validateUser(Login login);
}
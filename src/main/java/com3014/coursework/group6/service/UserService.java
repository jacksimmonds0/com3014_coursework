package com3014.coursework.group6.service;

import com3014.coursework.group6.model.Login;
import com3014.coursework.group6.model.User;

public interface UserService {

    void register(User user);

    User validateUser(Login login);
}
package com3014.coursework.group6.service.impl;

import com3014.coursework.group6.dao.UserDAO;
import com3014.coursework.group6.model.Login;
import com3014.coursework.group6.model.person.User;
import com3014.coursework.group6.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDao;

    @Override
    public void register(User user) {
        userDao.register(user);
    }

    @Override
    public void assignUserRole(String username) {
        userDao.assignUserRole(username);
    }

    @Override
    public boolean validateUser(Login login) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userDao.getUserByUsername(login);

        // if the user object is null then no user exists in the database for that username
        // then compare the login password (plaintext) against the password from the database (hashed)
        return user != null && passwordEncoder.matches(login.getPassword(), user.getPassword());

    }

    @Override
    public List<User> getUserList() {
        return userDao.getUserList();
    }

    @Override
    public String getUserRole(String username) {
        return userDao.getUserRole(username);
    }

    @Override
    public boolean userExists(String username) {
        return userDao.userExists(username);
    }

    @Override
    public User updateDetails(User user) {
        userDao.updateDetails(user);

        // get new data from DAO
        return userDao.getUserById(user.getId());
    }

    @Override
    public boolean correctPasswordForUser(int id, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userDao.getUserById(id);

        // if the user object is null then no user exists in the database for that id
        // then compare the old password entered (plaintext) against the password from the database (hashed)
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

    @Override
    public void updatePasswordForUser(int id, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        userDao.updatePasswordForUser(id, hashedPassword);
    }

    @Override
    public User getUserByUsername(Login login) {
        return userDao.getUserByUsername(login);
    }

}

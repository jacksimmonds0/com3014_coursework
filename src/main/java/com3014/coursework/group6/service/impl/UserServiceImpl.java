package com3014.coursework.group6.service.impl;

import com3014.coursework.group6.dao.UserDao;
import com3014.coursework.group6.model.Login;
import com3014.coursework.group6.model.person.User;
import com3014.coursework.group6.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public void register(User user) {
        userDao.register(user);
    }

    @Override
    public void assignUserRole(String username) {
        userDao.assignUserRole(username);
    }

    @Override
    public User validateUser(Login login) {
        return userDao.validateUser(login);
    }

    @Override
    public List getUserRoles(String username) {
        return userDao.getUserRoles(username);
    }

    @Override
    public boolean userExists(String username) {
        return userDao.userExists(username);
    }

    @Override
    public User updateDetails(int id, String firstName, String lastName, String email) {
        User updatedUser = new User();

        updatedUser.setId(id);
        updatedUser.setFirstName(firstName);
        updatedUser.setLastName(lastName);
        updatedUser.setEmail(email);

        userDao.updateDetails(updatedUser);

        // ensure data has been updated correctly
        return userDao.getUserById(id);
    }

    @Override
    public boolean correctPasswordForUser(int id, String password) {
        return userDao.correctPasswordForUser(id, password);
    }

    @Override
    public void updatePasswordForUser(int id, String password) {
        userDao.updatePasswordForUser(id, password);
    }

}

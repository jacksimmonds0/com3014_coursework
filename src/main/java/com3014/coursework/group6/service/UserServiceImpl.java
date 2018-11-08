package com3014.coursework.group6.service;



import com3014.coursework.group6.dao.UserDao;
import com3014.coursework.group6.model.Login;
import com3014.coursework.group6.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UserDao userDao;

    public void register(User user) {
        userDao.register(user);
    }

    public User validateUser(Login login) {
        return userDao.validateUser(login);
    }
}

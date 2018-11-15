package com3014.coursework.group6.service;



import com3014.coursework.group6.dao.UserDao;
import com3014.coursework.group6.model.Login;
import com3014.coursework.group6.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.portlet.handler.UserRoleAuthorizationInterceptor;

import java.net.PasswordAuthentication;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    public UserDao userDao;

    public void register(User user) {
        userDao.register(user);
    }

    public void assignUserRole(String username) { userDao.assignUserRole(username);}

    public User validateUser(Login login) {
        return userDao.validateUser(login);
    }

    public List getUserRoles(String username) {
        return userDao.getUserRoles(username);
    }

    public boolean userExists(String username) {
        return userDao.userExists(username);
    }

}

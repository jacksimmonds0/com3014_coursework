package com3014.coursework.group6.service.impl;

import com3014.coursework.group6.dao.UserDAO;
import com3014.coursework.group6.model.Login;
import com3014.coursework.group6.model.person.User;
import com3014.coursework.group6.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The service for managing users, roles and sessions
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDAO userDao;

    @Override
    public void register(User user) {
        userDao.register(user);
    }

    /**
     * Assigning a user a role
     *
     * @param username
     *          the user to be assigned a role
     */
    @Override
    public void assignUserRole(String username) {
        userDao.assignUserRole(username);
    }

    /**
     * Validating a user against the database
     *
     * @param login
     *          the login details entered by the user
     * @return true/false if the user is valid or not
     */
    @Override
    public boolean validateUser(Login login) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userDao.getUserByUsername(login);

        // if the user object is null then no user exists in the database for that username
        // then compare the login password (plaintext) against the password from the database (hashed)
        return user != null && passwordEncoder.matches(login.getPassword(), user.getPassword());

    }

    /**
     * Get the user list for the admin page
     *
     * @param userID
     *          the user id to be excluded i.e. the currently signed in user
     * @return the user list
     */
    @Override
    public List<User> getUserList(int userID) {

        // only return the users for the admin page that are not logged in currently
        return userDao.getUserList().stream()
                .filter(m -> m.getId() != userID)
                .collect(Collectors.toList());
    }

    /**
     * Retrieve the user role for a specified user
     *
     * @param username
     *          the username to retrieve the role for
     * @return the role for that user
     */
    @Override
    public String getUserRole(String username) {
        return userDao.getUserRole(username);
    }

    /**
     * Determine if the user exists
     *
     * @param username
     *          the username for the user to be checked
     * @return true/false if the user exists
     */
    @Override
    public boolean userExists(String username) {
        return userDao.userExists(username);
    }

    /**
     * Update details for a user
     *
     * @param user
     *          the updated details user object
     * @return the new user object
     */
    @Override
    public User updateDetails(User user) {
        userDao.updateDetails(user);

        // get new data from DAO
        return userDao.getUserById(user.getId());
    }

    /**
     * Determine if the password is correct for a user
     *
     * @param id
     *          the user id for the password
     * @param password
     *          the password to be checked
     * @return true/false if the password is correct
     */
    @Override
    public boolean correctPasswordForUser(int id, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User user = userDao.getUserById(id);

        // if the user object is null then no user exists in the database for that id
        // then compare the old password entered (plaintext) against the password from the database (hashed)
        return user != null && passwordEncoder.matches(password, user.getPassword());
    }

    /**
     * Update password for the user
     *
     * @param id
     *          the user id to update the password
     * @param password
     *          the password to be updated to
     */
    @Override
    public void updatePasswordForUser(int id, String password) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(password);

        userDao.updatePasswordForUser(id, hashedPassword);
    }

    /**
     * Change the user status
     *
     * @param id
     *          the user id that the status should be changed
     * @param status
     *          the new status
     */
    @Override
    public void changeUserStatus(int id, String status) {
        userDao.changeUserStatus(id, status);
    }

    /**
     * Get user by username
     *
     * @param login
     *          the login containing the username to be retrieved
     * @return the user object
     */
    @Override
    public User getUserByUsername(Login login) {
        return userDao.getUserByUsername(login);
    }

    /**
     * Check if an account is active
     *
     * @param login
     *          the login containing the username
     * @return true/false if account is active or not
     */
    @Override
    public boolean userAccountActive(Login login) {
        return userDao.userAccountActive(login);
    }

    /**
     * Retrieve the user by the id
     *
     * @param id
     *          the user id
     * @return the user object from the id
     */
    @Override
    public User getUserById(int id) {
        return userDao.getUserById(id);
    }

}

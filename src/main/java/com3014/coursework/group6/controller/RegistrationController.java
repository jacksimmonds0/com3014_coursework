package com3014.coursework.group6.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com3014.coursework.group6.model.Login;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com3014.coursework.group6.model.person.User;
import com3014.coursework.group6.service.UserService;
import com3014.coursework.group6.validator.RegisterValidator;

/**
 * The controller for registering a user onto the system
 */
@Controller
public class RegistrationController {

    private static final Logger LOG = Logger.getLogger(RegistrationController.class);

    @Autowired
    UserService userService;

    @Autowired
    RegisterValidator registerValidator;

    /**
     * @return the register view with the user as a model
     */
    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView showRegister() {
        ModelAndView mav = new ModelAndView("register");
        mav.addObject("user", new User());

        return mav;
    }

    /**
     * The add user POST method to complete the registration form with validation
     *
     * @param session
     *          the {@link HttpSession} to add the user to the cookie if registration is successful
     * @param user
     *          the {@link ModelAttribute} user for validation
     * @param result
     *          the result of the validation
     * @return the {@link ModelAndView} to redirect to the home page if registration is successful
     */
    @RequestMapping(value = "/registerProcess", method = RequestMethod.POST)
    public ModelAndView addUser(HttpSession session, @ModelAttribute("user") User user, BindingResult result) {

        try {
            registerValidator.validate(user, result);

            if(result.hasErrors()) {

                return new ModelAndView("register");
            }
            else {

                // hash the password before being stored in the database
                BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                String hashedPassword = passwordEncoder.encode(user.getPassword());
                user.setPassword(hashedPassword);

                userService.register(user);
                userService.assignUserRole(user.getUsername());
                Login login = new Login();
                login.setUsername(user.getUsername());
                User actualUser = userService.getUserByUsername(login);

                // add the user to the session if registration is successful
                session.setAttribute("currentUser", actualUser);
                session.setAttribute("userRole", userService.getUserRole(user.getUsername()));

                return new ModelAndView("redirect:/", "firstName", user.getFirstName());
            }
        }
        catch(CannotGetJdbcConnectionException e) {
            // log exception to server log and show error screen
            LOG.error(e);

            return new ModelAndView("error");
        }

    }
}
package com3014.coursework.group6.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com3014.coursework.group6.model.person.User;
import com3014.coursework.group6.service.UserService;
import com3014.coursework.group6.validator.RegisterValidator;

@Controller
public class RegistrationController {

    private static final Logger LOG = Logger.getLogger(RegistrationController.class);

    @Autowired
    public UserService userService;

    @Autowired
    RegisterValidator registerValidator;

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("register");
        mav.addObject("user", new User());

        return mav;
    }
    @RequestMapping(value = "/registerProcess", method = RequestMethod.POST)
    public ModelAndView addUser(HttpServletRequest request, HttpServletResponse response,
                                @ModelAttribute("user") User user, BindingResult result) {

        try {
            registerValidator.validate(user, result);

            if(result.hasErrors()){
                return new ModelAndView("register");
            } else {

                userService.register(user);
                userService.assignUserRole(user.getUsername());

                return new ModelAndView("index", "firstName", user.getFirstName());
            }
        }
        catch(CannotGetJdbcConnectionException e) {
            // log exception to server log and show error screen
            LOG.error(e);

            return new ModelAndView("error");
        }

    }
}
package com3014.coursework.group6.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import com3014.coursework.group6.model.Login;
import com3014.coursework.group6.model.person.User;
import com3014.coursework.group6.service.UserService;


@Controller
public class LoginController {

    private static final Logger LOG = Logger.getLogger(LoginController.class);

    private static final String CURRENT_USER = "currentUser";

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLogin(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("login", new Login());
        return mav;
    }

    @RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
    public ModelAndView loginProcess(HttpServletRequest request, HttpServletResponse response,
                                     HttpSession session, @ModelAttribute("login") Login login) {
        ModelAndView mav = null;
        User user = new User();

        try {
            if(userService.validateUser(login)) {
                user = userService.getUserByUsername(login);

                // add the user to the session (as a cookie)
                session.setAttribute(CURRENT_USER, user);

                // return back to the home page
                mav = new ModelAndView("index");
            }
            else {
                mav = new ModelAndView("login");
                mav.addObject("message", "Username or Password is wrong!");
            }
        }
        catch(CannotGetJdbcConnectionException e) {
            // log exception to server log and show error screen
            LOG.error(e);

            return new ModelAndView("error");
        }

        return mav;
    }

    @RequestMapping(value = "/logout")
    public ModelAndView logout(HttpSession session) {
        ModelAndView mav = new ModelAndView("logout");
        User currentUser = (User) session.getAttribute(CURRENT_USER);

        mav.addObject("username", currentUser.getUsername());

        // remove the session from the client-side
        session.invalidate();

        return mav;
    }

}
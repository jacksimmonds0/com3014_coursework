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

/**
 * The login controller to allow users to login and logout of the system
 */
@Controller
public class LoginController {

    private static final Logger LOG = Logger.getLogger(LoginController.class);

    private static final String CURRENT_USER = "currentUser";

    @Autowired
    UserService userService;

    /**
     * @return the login page view on the /login endpoint
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView showLogin() {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("login", new Login());
        return mav;
    }

    /**
     * The login process POST method to login to the web application
     *
     * @param session
     *          the {@link HttpSession} to add the user to the cookie if login is successful
     * @param login
     *          the {@link ModelAttribute} login for validation
     * @return the {@link ModelAndView} depending on if login is successful or not
     */
    @RequestMapping(value = "/loginProcess", method = RequestMethod.POST)
    public ModelAndView loginProcess(HttpSession session, @ModelAttribute("login") Login login) {
        ModelAndView mav = null;
        User user = new User();

        try {
            if(userService.validateUser(login) && userService.userAccountActive(login)) {
                user = userService.getUserByUsername(login);

                // add the user to the session (as a cookie)
                session.setAttribute(CURRENT_USER, user);
                session.setAttribute("userRole", userService.getUserRole(user.getUsername()));

                // return back to the home page
                mav = new ModelAndView("redirect:/");
            }
            else if (!userService.validateUser(login)) {
                mav = new ModelAndView("login");
                mav.addObject("message", "Invalid username or password");
            }
            else {
                mav = new ModelAndView("login");
                mav.addObject("message", "User account has been deactivated. Please contact an admin");
            }
        }
        catch(CannotGetJdbcConnectionException e) {
            // log exception to server log and show error screen
            LOG.error(e);

            return new ModelAndView("error");
        }

        return mav;
    }

    /**
     * Method to allow the user to logout of the system
     *
     * @param session
     *          the {@link HttpSession} to be invalidated so the user is logged out of the system
     * @return the logout view if logout is successful, the home page otherwise
     */
    @RequestMapping(value = "/logout")
    public ModelAndView logout(HttpSession session) {
        ModelAndView mav = new ModelAndView("logout");
        User currentUser = (User) session.getAttribute(CURRENT_USER);

        if(currentUser == null) {
            return new ModelAndView("index");
        }

        mav.addObject("username", currentUser.getUsername());

        // remove the session from the client-side
        session.invalidate();

        return mav;
    }

}
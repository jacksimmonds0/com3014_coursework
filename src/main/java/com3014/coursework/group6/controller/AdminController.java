package com3014.coursework.group6.controller;

import com3014.coursework.group6.model.person.User;
import com3014.coursework.group6.service.UserService;
import com3014.coursework.group6.validator.DetailsUpdateValidator;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class AdminController {

    private static final String USER_ROLE = "userRole";

    @Autowired
    UserService userService;

    private DetailsUpdateValidator detailsUpdateValidator;

    private boolean requestDoesNotMatchSession(HttpSession session) {
        String userRole = (String) session.getAttribute(USER_ROLE);

        return userRole == null || !userRole.equals("ROLE_ADMIN");
    }

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    public ModelAndView getUserList(HttpSession session) {

        if (requestDoesNotMatchSession(session)) {
            ModelAndView mav = new ModelAndView("index");
            mav.addObject("message", "Error: Restricted access");
            return mav;
        } else {
            ModelAndView mav = new ModelAndView("admin");
            mav.addObject("userList", userService.getUserList());
            return mav;
        }

    }

    @RequestMapping(value = "admin/account/{id}", method = RequestMethod.GET)
    public String editAccount(@PathVariable int id, ModelMap model) {
        User user = userService.getUserById(id);

        model.addAttribute("user", user);

        return "editaccount";
    }

    @RequestMapping(value = "admin/delete/{id}", method = RequestMethod.GET)
    public ModelAndView deleteAccount(@PathVariable int id) {
        userService.deleteUser(id);
        ModelAndView mav = new ModelAndView("redirect:/admin");

        return mav;
    }


}

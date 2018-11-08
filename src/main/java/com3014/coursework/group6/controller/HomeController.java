package com3014.coursework.group6.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController {

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String navigateToLogin(ModelMap model) {
        return "login";
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String navigateToRegister(ModelMap model) {
        return "register";
    }
}

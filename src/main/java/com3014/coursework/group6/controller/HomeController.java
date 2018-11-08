package com3014.coursework.group6.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String navigateToHome() {
        return "login";
    }

    @RequestMapping(value = "/view_register.htm", method = RequestMethod.GET)
    public String navigateToRegister() {
        return "register";
    }
}

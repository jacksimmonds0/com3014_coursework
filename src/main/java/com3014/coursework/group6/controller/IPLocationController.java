package com3014.coursework.group6.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com3014.coursework.group6.model.Login;
import com3014.coursework.group6.model.User;
import com3014.coursework.group6.service.UserService;



@Controller
public class IPLocationController {

    @RequestMapping(value = "/iplocation", method = RequestMethod.GET)
    public String root() {
        return "iplocation";
    }

}
package com3014.coursework.group6.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class IPLocationController {

    @RequestMapping(value = "/nearestcinema", method = RequestMethod.GET)
    public String root() {
        return "nearestcinema";
    }
}
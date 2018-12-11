package com3014.coursework.group6.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * The controller for the nearest cinema page
 */
@Controller
public class IPLocationController {

    /**
     * @return the nearest cinema view
     */
    @RequestMapping(value = "/nearestcinema", method = RequestMethod.GET)
    public String root() {
        return "nearestcinema";
    }
}
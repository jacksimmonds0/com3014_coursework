package com3014.coursework.group6.controller;

import com3014.coursework.group6.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;

    @RequestMapping(value = "/movie", method = RequestMethod.GET)
    public String showMovie(@RequestParam("id") int movieID, ModelMap model) {

        model.addAttribute("movieID", movieID);
        model.addAttribute("movie", movieService.getMovie(movieID));

        return "movie";
    }

}

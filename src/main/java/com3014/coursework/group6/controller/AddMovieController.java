package com3014.coursework.group6.controller;
import com3014.coursework.group6.model.Movie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

public class AddMovieController {
    @RequestMapping(value = "/addmovie", method = RequestMethod.GET)
    /*public ModelAndView showForm() {
        return new ModelAndView("addmovie", "movie", new Movie());
    }*/
    public String root() {
        return "addmovie";
    }
}

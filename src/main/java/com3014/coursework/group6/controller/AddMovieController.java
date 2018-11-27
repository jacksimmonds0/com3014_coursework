package com3014.coursework.group6.controller;
import com3014.coursework.group6.model.Movie;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AddMovieController {
    @RequestMapping(value = "/addmovie", method = RequestMethod.GET)
    public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("addmovie");
        mv.addObject("movie", new Movie());

        return mv;
    }
}

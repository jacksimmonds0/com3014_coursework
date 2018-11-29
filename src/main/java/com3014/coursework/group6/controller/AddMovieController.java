package com3014.coursework.group6.controller;
import com3014.coursework.group6.model.Movie;
import com3014.coursework.group6.model.person.User;
import com3014.coursework.group6.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class AddMovieController {

    @Autowired
    private MovieService movieService;

    @RequestMapping(value = "/addmovie", method = RequestMethod.GET)
    public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("addmovie");
        mv.addObject("movie", new Movie());

        return mv;
    }

    @RequestMapping(value ="/addmovieProcess", method = RequestMethod.POST, produces={"application/json"})
    public @ResponseBody int addMovie(@RequestParam(value="year",required = true) int year, @RequestParam(value="title",required=true) String title, @RequestParam(value="description",required=true) String description, HttpSession session, HttpServletRequest request){
        int dbResult = movieService.addMovie(year, title, description);
        return dbResult;
    }
}

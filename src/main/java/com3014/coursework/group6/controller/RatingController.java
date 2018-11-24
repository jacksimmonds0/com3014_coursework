package com3014.coursework.group6.controller;

import com3014.coursework.group6.model.Movie;
import com3014.coursework.group6.model.person.User;
import com3014.coursework.group6.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class RatingController {
    @Autowired
    private MovieService movieService;


    @RequestMapping(value ="/addRating", method = RequestMethod.POST, produces={"application/json"})
    public @ResponseBody int addRating(@RequestParam(value="movieID",required = true) int movieID,@RequestParam(value="rating",required=true) double rating, HttpSession session, HttpServletRequest request){
        User currentUser = (User)session.getAttribute("currentUser");
        int dbResult = movieService.addRating(movieID, currentUser.getId(),rating);
        return dbResult;
    }

    @RequestMapping(value ="/getAvgRating", method = RequestMethod.POST, produces={"application/json"})
    public @ResponseBody double getAvgRating(@RequestParam(value="movieID",required = true) int movieID, HttpServletRequest request){
        double avgRating = movieService.getAvgRating(movieID);
        return avgRating;
    }
}
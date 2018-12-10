package com3014.coursework.group6.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com3014.coursework.group6.model.Movie;
import com3014.coursework.group6.model.person.User;
import com3014.coursework.group6.service.MovieService;
import com3014.coursework.group6.service.RecommendationService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
public class RatingController {
    @Autowired
    private MovieService movieService;

    @Autowired
    private RecommendationService recommendationService;


    @RequestMapping(value ="/addRating", method = RequestMethod.POST, produces={"application/json"})
    public @ResponseBody String addRating(@RequestParam(value="movieID",required = true) int movieID,@RequestParam(value="rating",required=true) double rating, HttpSession session, HttpServletRequest request){
        User currentUser = (User)session.getAttribute("currentUser");
        int userID = currentUser.getId();
        int dbResult = movieService.addRating(movieID, userID,rating);
        if(rating>=4){
            List<Movie> similarMovies = recommendationService.getSimilarMovies(movieID,userID);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("recommendations",similarMovies);
            return jsonObject.toString();
        }
        return "{}";
    }

    @RequestMapping(value ="/getAvgRating", method = RequestMethod.POST, produces={"application/json"})
    public @ResponseBody double getAvgRating(@RequestParam(value="movieID",required = true) int movieID, HttpServletRequest request){
        double avgRating = movieService.getAvgRating(movieID);
        return avgRating;
    }
}
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

/**
 * The REST controller for adding a rating to the movie and retrieving average ratings
 */
@RestController
public class RatingController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private RecommendationService recommendationService;

    /**
     * The POST method to add a rating to the movie
     *
     * @param movieID
     *          the id for the movie to add a rating to
     * @param rating
     *          the rating from the user to add for the movie
     * @param session
     *          the {@link HttpSession} object containing the currently logged in user
     * @return the JSON response for the rating added
     */
    @RequestMapping(value ="/addRating", method = RequestMethod.POST, produces={"application/json"})
    public @ResponseBody String addRating(@RequestParam(value="movieID",required = true) int movieID,@RequestParam(value="rating",required=true) double rating, HttpSession session){
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

    /**
     * Retrieving the average rating from the database
     *
     * @param movieID
     *          the movie we need to get the average rating for, based on ID
     * @return the average rating for the movie
     */
    @RequestMapping(value ="/getAvgRating", method = RequestMethod.POST, produces={"application/json"})
    public @ResponseBody double getAvgRating(@RequestParam(value="movieID",required = true) int movieID){
        double avgRating = movieService.getAvgRating(movieID);
        return avgRating;
    }
}
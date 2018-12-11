package com3014.coursework.group6.controller;

import com3014.coursework.group6.model.person.User;
import com3014.coursework.group6.service.MovieService;
import com3014.coursework.group6.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * The controller for the home page of the movies web application
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private RecommendationService recommendationService;

    /**
     * The method to return the {@link ModelAndView} for the home page
     *
     * @param session
     *          the {@link HttpSession} object for if a user is logged in
     * @return the {@link ModelAndView} for the home page with model objects for the recommended movies
     *          if a user is logged in
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView root(HttpSession session) {
        ModelAndView mav = new ModelAndView("index");
        User currentUser = (User) session.getAttribute("currentUser");

        if(currentUser != null) {
            System.out.println(recommendationService.getRecommendedMovies(currentUser.getId()));
            mav.addObject("recommendations", recommendationService.getRecommendedMovies(currentUser.getId()));
        }

        mav.addObject("recentMovies", movieService.getMostRecentlyAddedMovies());

        return mav;
    }
}

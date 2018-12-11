package com3014.coursework.group6.service.impl;

import com3014.coursework.group6.dao.MovieDAO;
import com3014.coursework.group6.model.Movie;
import com3014.coursework.group6.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * The service to recommended movies for users of the application
 */
@Service
public class RecommendationServiceImpl implements RecommendationService {

    @Autowired
    private MovieDAO movieDAO;

    /**
     * Get the similar movies for an individual movie that has been entered
     *
     * @param movie_id
     *          the movie id to recommend similar movies from
     * @param user_id
     *          the user id for the user who has rated the film
     * @return the list of recommended movies
     */
    @Override
    public List<Movie> getSimilarMovies(int movie_id, int user_id) {
        return movieDAO.getSimilarMovies(movie_id, user_id);
    }

    /**
     * The recommended movies for the user to be displayed on the home page
     *
     * @param user_id
     *          the user id of the user logged in
     * @return the map for key as the movie liked to values for list of recommended movies for that movie
     */
    @Override
    public Map<Movie, List<Movie>> getRecommendedMovies(int user_id) {
        return movieDAO.getRecommendedMovies(user_id);
    }
}

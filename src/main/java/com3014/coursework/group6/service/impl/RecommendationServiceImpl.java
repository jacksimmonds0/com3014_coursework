package com3014.coursework.group6.service.impl;

import com3014.coursework.group6.dao.MovieDAO;
import com3014.coursework.group6.model.Movie;
import com3014.coursework.group6.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class RecommendationServiceImpl implements RecommendationService {
    @Autowired
    private MovieDAO movieDAO;

    @Override
    public List<Movie> getSimilarMovies(int movie_id, int user_id) {
        return movieDAO.getSimilarMovies(movie_id, user_id);
    }

    @Override
    public Map<Movie, List<Movie>> getRecommendedMovies(int user_id) {
        return movieDAO.getRecommendedMovies(user_id);
    }
}

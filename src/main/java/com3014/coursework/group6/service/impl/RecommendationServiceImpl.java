package com3014.coursework.group6.service.impl;

import com3014.coursework.group6.dao.MovieDAO;
import com3014.coursework.group6.model.Movie;
import com3014.coursework.group6.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecommendationServiceImpl implements RecommendationService {
    @Autowired
    private MovieDAO movieDAO;

    @Override
    public List<Movie> getSimilarMovies(int movie_id) {
        return movieDAO.getSimilarMovies(movie_id);
    }

    @Override
    public List<Movie> getRecommendedMovies(int user_id) {
        return null;
    }
}

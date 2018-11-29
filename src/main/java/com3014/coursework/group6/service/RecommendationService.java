package com3014.coursework.group6.service;

import com3014.coursework.group6.model.Movie;

import java.util.List;

public interface RecommendationService {

    public List<Movie> getSimilarMovies(int movie_id);

    public List<Movie> getRecommendedMovies(int user_id);

}

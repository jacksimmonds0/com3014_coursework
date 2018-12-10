package com3014.coursework.group6.service;

import com3014.coursework.group6.model.Movie;

import java.util.List;
import java.util.Map;

public interface RecommendationService {

    public List<Movie> getSimilarMovies(int movie_id, int user_id);

    public Map<Movie, List<Movie>> getRecommendedMovies(int user_id);

}

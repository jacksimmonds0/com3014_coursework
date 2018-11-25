package com3014.coursework.group6.service;

import com3014.coursework.group6.model.Movie;

import java.util.List;

public interface MovieService {

    public List<Movie> getAllMovies();

    public Movie getMovie(int id);

    public int addRating(int movie_id, int user_id, double rating);

    public double getAvgRating(int movie_id);

    public double getIndivRating(int movie_id, int user_id);
}

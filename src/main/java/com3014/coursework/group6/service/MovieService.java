package com3014.coursework.group6.service;

import com3014.coursework.group6.model.Comment;
import com3014.coursework.group6.model.Genre;
import com3014.coursework.group6.model.Movie;
import com3014.coursework.group6.model.person.Director;

import java.sql.Timestamp;
import java.util.List;

public interface MovieService {

    public List<Movie> getAllMovies();

    public Movie getMovie(int id);

    public int addRating(int movie_id, int user_id, double rating);

    public int addComment(int movie_id, int user_id, String title, String comment, Timestamp timestamp);

    public double getAvgRating(int movie_id);

    public double getIndivRating(int movie_id, int user_id);

    public List<Comment> getComments(int movie_id);

    public List<Double> getAllAvgRatingsForMovies(List<Movie> movies);

    public int addMovie(int year, String title, String description, List<Integer> genres);

}

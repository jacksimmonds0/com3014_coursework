package com3014.coursework.group6.service.impl;

import com3014.coursework.group6.dao.ActorDAO;
import com3014.coursework.group6.dao.DirectorDAO;
import com3014.coursework.group6.dao.MovieDAO;
import com3014.coursework.group6.dao.GenreDAO;
import com3014.coursework.group6.dao.UserDAO;
import com3014.coursework.group6.model.Comment;
import com3014.coursework.group6.model.Genre;
import com3014.coursework.group6.model.Movie;
import com3014.coursework.group6.model.person.Actor;
import com3014.coursework.group6.model.person.Director;
import com3014.coursework.group6.model.person.User;
import com3014.coursework.group6.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovieServiceImpl implements MovieService {

    @Autowired
    private MovieDAO movieDAO;

    @Autowired
    private ActorDAO actorDAO;

    @Autowired
    private DirectorDAO directorDAO;

    @Autowired
    private GenreDAO genreDAO;

    @Autowired
    private UserDAO userDAO;

    @Override
    public List<Movie> getAllMovies() {
        List<Movie> movies = movieDAO.getAllMoviesFromDB();

        if(!movies.isEmpty()) {
            for(Movie movie : movies) {
                setMovieFields(movie);

                List<Comment> comments = movieDAO.getComments(movie.getId());
                movie.setComments(comments);
            }
        }

        return movies;
    }

    @Override
    public Movie getMovie(int id){
        Movie movie = movieDAO.getMovieFromDB(id);
        if(movie != null){
            setMovieFields(movie);
        }

        return movie;
    }

    @Override
    public int addRating(int movie_id, int user_id, double rating){
        return movieDAO.addRating(movie_id,user_id, rating);
    }

    @Override
    public int addComment(int movie_id, int user_id, String title, String comment, Timestamp timestamp) {
        return movieDAO.addComment(movie_id,user_id, title, comment, timestamp);
    }


    @Override
    public double getAvgRating(int movie_id){
        return movieDAO.getAvgRating(movie_id);
    }

    @Override
    public double getIndivRating(int movie_id, int user_id){
        return movieDAO.getIndivRating(movie_id, user_id);
    }

    @Override
    public List<Comment> getComments(int movie_id) {
        List<Comment> comments = movieDAO.getComments(movie_id);

        if(!comments.isEmpty()) {
            for(Comment c : comments) {
                User u = userDAO.getUserById(c.getUser().getId());
                c.setUser(u);
            }
        }

        return comments;
    }

    @Override
    public List<Double> getAllAvgRatingsForMovies(List<Movie> movies) {

        return movies.stream()
                .map(m -> movieDAO.getAvgRating(m.getId()))
                .collect(Collectors.toList());
    }

    public List<Integer> getNumberOfCommentsForMovies(List<Movie> movies) {

        return movies.stream()
                .map(m -> movieDAO.getComments(m.getId()).size())
                .collect(Collectors.toList());
    }

    @Override
    public int addMovie(int year, String title, String description, List<Integer> genres, String director, String actors, String posterUrl) {
        return movieDAO.addMovie(year, title, description, genres, director, actors, posterUrl);
    }

    private void setMovieFields(Movie movie) {
        Director d = directorDAO.getDirectorForMovie(movie.getDirector().getId());
        movie.setDirector(d);

        List<Actor> actors = actorDAO.getActorsForMovie(movie.getId());
        movie.setActors(actors);

        List<Genre> genres = genreDAO.getGenresForMovie(movie.getId());
        movie.setGenres(genres);
    }

}

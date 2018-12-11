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

/**
 * The service to return the all aspects for the movie pages
 */
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

    /**
     * @return all movies from the database with comments, actors, genres and directors
     */
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

    /**
     * Returning a specific movie from the database
     *
     * @param id
     *          the movie id to be retrieve
     * @return the movie object based on the id
     */
    @Override
    public Movie getMovie(int id){
        Movie movie = movieDAO.getMovieFromDB(id);
        if(movie != null){
            setMovieFields(movie);
        }

        return movie;
    }

    /**
     * Add a rating to the database
     *
     * @param movie_id
     *          the movie id for the rating to be added to
     * @param user_id
     *          the user id who added the rating
     * @param rating
     *          the rating value entered on the client-side
     * @return the database result
     */
    @Override
    public int addRating(int movie_id, int user_id, double rating){
        return movieDAO.addRating(movie_id,user_id, rating);
    }

    /**
     * Add a comment to the database
     *
     * @param movie_id
     *          the movie id for the comment to be added to
     * @param user_id
     *          the user id who added the comment
     * @param title
     *          the title of the comment
     * @param comment
     *          the comment added
     * @param timestamp
     *          the time the comment was added
     * @return the database result
     */
    @Override
    public int addComment(int movie_id, int user_id, String title, String comment, Timestamp timestamp) {
        return movieDAO.addComment(movie_id,user_id, title, comment, timestamp);
    }

    /**
     * Get the average rating for a movie
     *
     * @param movie_id
     *          the movie id
     * @return the average rating value
     */
    @Override
    public double getAvgRating(int movie_id){
        return movieDAO.getAvgRating(movie_id);
    }

    /**
     * Get the individual rating for a movie
     *
     * @param movie_id
     *          the movie id
     * @param user_id
     *          the user id who added the rating
     * @return the individual rating value
     */
    @Override
    public double getIndivRating(int movie_id, int user_id){
        return movieDAO.getIndivRating(movie_id, user_id);
    }

    /**
     * Get all comments for a movie
     *
     * @param movie_id
     *          the movie id
     * @return the comments for the movie as a list
     */
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

    /**
     * Get all the average ratings for a list of movies
     *
     * @param movies
     *          the movies to retrieve average ratings for
     * @return the list of average ratings, in the same order as the list of movies
     */
    @Override
    public List<Double> getAllAvgRatingsForMovies(List<Movie> movies) {

        return movies.stream()
                .map(m -> movieDAO.getAvgRating(m.getId()))
                .collect(Collectors.toList());
    }

    /**
     * The number of comments for movies
     *
     * @param movies
     *          the number of comments for movies
     * @return the list of number of comments, in the same order as the list of movies
     */
    public List<Integer> getNumberOfCommentsForMovies(List<Movie> movies) {

        return movies.stream()
                .map(m -> movieDAO.getComments(m.getId()).size())
                .collect(Collectors.toList());
    }

    /**
     * Adding a movie to the database
     *
     * @param year
     *          the year the movie was released
     * @param title
     *          the movie title
     * @param description
     *          the description of the movie
     * @param genres
     *          the genres associated with that movie
     * @param director
     *          the director of the movie
     * @param actors
     *          the actors inthe movie
     * @param posterUrl
     *          the URL to the poster for the movie
     * @return the database result
     */
    @Override
    public int addMovie(int year, String title, String description, List<Integer> genres, String director, String actors, String posterUrl) {
        return movieDAO.addMovie(year, title, description, genres, director, actors, posterUrl);
    }

    /**
     * @return the 6 most recently added movies from the database (based on ID)
     */
    @Override
    public List<Movie> getMostRecentlyAddedMovies() {
        return movieDAO.getMostRecentlyAddedMovies();
    }

    /**
     * Set the movie fields from the director, actor and genre DAOs due to one-to-many relationships
     *
     * @param movie
     *         the movie object to add the fields to
     */
    private void setMovieFields(Movie movie) {
        Director d = directorDAO.getDirectorForMovie(movie.getDirector().getId());
        movie.setDirector(d);

        List<Actor> actors = actorDAO.getActorsForMovie(movie.getId());
        movie.setActors(actors);

        List<Genre> genres = genreDAO.getGenresForMovie(movie.getId());
        movie.setGenres(genres);
    }

}

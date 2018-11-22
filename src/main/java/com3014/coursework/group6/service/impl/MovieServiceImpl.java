package com3014.coursework.group6.service.impl;

import com3014.coursework.group6.dao.ActorDAO;
import com3014.coursework.group6.dao.DirectorDAO;
import com3014.coursework.group6.dao.MovieDAO;
import com3014.coursework.group6.dao.GenreDAO;
import com3014.coursework.group6.model.Genre;
import com3014.coursework.group6.model.Movie;
import com3014.coursework.group6.model.person.Actor;
import com3014.coursework.group6.model.person.Director;
import com3014.coursework.group6.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<Movie> getAllMovies() {
        List<Movie> movies = movieDAO.getAllMoviesFromDB();

        if(!movies.isEmpty()) {
            for(Movie m : movies) {
                Director d = directorDAO.getDirectorForMovie(m.getDirector().getId());
                m.setDirector(d);

                List<Actor> actors = actorDAO.getActorsForMovie(m.getId());
                m.setActors(actors);

                List<Genre> genres =  genreDAO.getGenresForMovie(m.getId());
                m.setGenres(genres);
            }
        }

        return movies;
    }
}

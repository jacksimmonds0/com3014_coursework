package com3014.coursework.group6.service.impl;

import com3014.coursework.group6.dao.ActorDAO;
import com3014.coursework.group6.dao.DirectorDAO;
import com3014.coursework.group6.dao.GenreDAO;
import com3014.coursework.group6.model.Genre;
import com3014.coursework.group6.model.person.Actor;
import com3014.coursework.group6.model.person.Director;
import com3014.coursework.group6.service.MovieFieldsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * A service to retrieve all the genres, actors and directors for the add movie
 */
@Service
public class MovieFieldsServiceImpl implements MovieFieldsService {

    @Autowired
    private GenreDAO genreDAO;

    @Autowired
    private ActorDAO actorDAO;

    @Autowired
    private DirectorDAO directorDAO;

    /**
     * @return all the genres as a list from the database
     */
    @Override
    public List<Genre> getAllGenres() {
        return genreDAO.getAllGenres();
    }

    /**
     * @return all the actors as a list from the database
     */
    @Override
    public List<Actor> getAllActors() {
        return actorDAO.getAllActors();
    }

    /**
     * @return all the databases as a list from the database
     */
    @Override
    public List<Director> getAllDirectors() {
        return directorDAO.getAllDirectors();
    }
}

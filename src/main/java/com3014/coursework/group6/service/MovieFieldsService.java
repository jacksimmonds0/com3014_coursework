package com3014.coursework.group6.service;

import com3014.coursework.group6.model.Genre;
import com3014.coursework.group6.model.person.Actor;
import com3014.coursework.group6.model.person.Director;

import java.util.List;

public interface MovieFieldsService {

    public List<Genre> getAllGenres();

    public List<Actor> getAllActors();

    public List<Director> getAllDirectors();
}

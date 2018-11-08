package com3014.coursework.group6.service;

import com3014.coursework.group6.model.Movie;

import java.util.List;

public interface SearchService {

    public List<Movie> getSearchResults(String search);
}

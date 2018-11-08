package com3014.coursework.group6.service.impl;

import com3014.coursework.group6.dao.MovieDAO;
import com3014.coursework.group6.model.Movie;
import com3014.coursework.group6.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private MovieDAO movieDAO;

    @Override
    public List<Movie> getSearchResults(final String search) {

        List<Movie> movies = movieDAO.getAllMovies();

        return movies.stream()
                .filter(m -> m.getTitle().contains(search))
                .collect(Collectors.toList());
    }
}

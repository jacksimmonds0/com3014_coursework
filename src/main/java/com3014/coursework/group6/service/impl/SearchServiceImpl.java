package com3014.coursework.group6.service.impl;

import com3014.coursework.group6.dao.MovieDAO;
import com3014.coursework.group6.model.Genre;
import com3014.coursework.group6.model.Movie;
import com3014.coursework.group6.model.person.Actor;
import com3014.coursework.group6.model.person.Director;
import com3014.coursework.group6.service.MovieService;
import com3014.coursework.group6.service.SearchService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchServiceImpl implements SearchService {

    private MovieService movieService;

    @Autowired
    public SearchServiceImpl(MovieService movieService) {
        this.movieService = movieService;
    }

    @Override
    public List<Movie> getSearchResults(String search) {

        List<Movie> movies = movieService.getAllMovies();

        return movies.stream()
                .filter(m -> searchByTitle(m, search) || searchbyDirector(m, search) || searchByActors(m, search) ||
                        searchByGenre(m, search) || searchByYear(m, search) )
                .collect(Collectors.toList());
    }

    @Override
    public String getSearchboxResults(String search) {
        List<Movie> movies = movieService.getAllMovies();

        // first 5 results from a search based on the title
        List<Movie> results = movies.stream()
                .filter(m -> searchByTitle(m, search))
                .limit(5)
                .collect(Collectors.toList());

        // add the results to a JSON response
        JSONArray jsonResults = new JSONArray();
        for(Movie movie : results) {
            JSONObject json = new JSONObject();

            json.put("id", movie.getId());
            json.put("title", movie.getTitle());

            jsonResults.put(json);
        }

        return new JSONObject().put("response", jsonResults).toString();
    }

    private boolean searchByTitle(Movie movie, String search) {
        return movie.getTitle().toLowerCase().contains(search.toLowerCase());
    }

    private boolean searchByGenre(Movie movie, String search) {

        for(Genre genre : movie.getGenres()) {
            if(genre.getName().toLowerCase().contains(search.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private boolean searchByYear(Movie movie, String search) {
        int searchParsed = 0;

        try {
            searchParsed = Integer.parseInt(search.toLowerCase());
        }
        catch(NumberFormatException e) {
            return false;
        }

        return searchParsed == movie.getYear();
    }

    private boolean searchbyDirector(Movie movie, String search) {
        String directorName = movie.getDirector().getName().toLowerCase();

        return directorName.contains(search.toLowerCase());
    }

    private boolean searchByActors(Movie movie, String search) {

        for(Actor actor : movie.getActors()) {
            if(actor.getName().toLowerCase().contains(search.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

}

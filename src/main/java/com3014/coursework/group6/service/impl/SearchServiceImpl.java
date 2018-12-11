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

/**
 * A service to provide searching of results for the search page and search box dropdown
 */
@Service
public class SearchServiceImpl implements SearchService {

    private MovieService movieService;

    @Autowired
    public SearchServiceImpl(MovieService movieService) {
        this.movieService = movieService;
    }

    /**
     * Gets search results for the search page - by actor, genre, director, year and title
     *
     * @param search
     *          the search term entered by the user
     * @return the list of movies as a result of the search
     */
    @Override
    public List<Movie> getSearchResults(String search) {

        List<Movie> movies = movieService.getAllMovies();

        return movies.stream()
                .filter(m -> searchByTitle(m, search) || searchbyDirector(m, search) || searchByActors(m, search) ||
                        searchByGenre(m, search) || searchByYear(m, search) )
                .collect(Collectors.toList());
    }

    /**
     * Get 5 search results for the type-ahead search only based on title
     *
     * @param search
     *          the search term entered by the user in the UI currently
     * @return the list of movies as a result of the search as a JSON
     */
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

    /**
     * Return if the search string matches the movie by title
     *
     * @param movie
     *          the movie to be searched against
     * @param search
     *          the search string from the user
     * @return if the search is a match or not (true/false)
     */
    private boolean searchByTitle(Movie movie, String search) {
        return movie.getTitle().toLowerCase().contains(search.toLowerCase());
    }

    /**
     * Return if the search string matches the movie by genre,
     * if any of the genres match then return true
     *
     * @param movie
     *          the movie to be searched against
     * @param search
     *          the search string from the user
     * @return if the search is a match or not (true/false)
     */
    private boolean searchByGenre(Movie movie, String search) {

        for(Genre genre : movie.getGenres()) {
            if(genre.getName().toLowerCase().contains(search.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * Return if the search string matches the movie by title
     *
     * @param movie
     *          the movie to be searched against
     * @param search
     *          the search string from the user
     * @return if the search is a match or not (true/false)
     */
    private boolean searchByYear(Movie movie, String search) {
        int searchParsed = 0;

        try {
            searchParsed = Integer.parseInt(search.toLowerCase());
        }
        catch(NumberFormatException e) {
            // i.e. this is not a number so must not be a search by year
            return false;
        }

        return searchParsed == movie.getYear();
    }

    /**
     * Return if the search string matches the movie by director
     *
     * @param movie
     *          the movie to be searched against
     * @param search
     *          the search string from the user
     * @return if the search is a match or not (true/false)
     */
    private boolean searchbyDirector(Movie movie, String search) {
        String directorName = movie.getDirector().getName().toLowerCase();

        return directorName.contains(search.toLowerCase());
    }

    /**
     * Return if the search string matches the movie by actor,
     * if any of the actors match then return true
     *
     * @param movie
     *          the movie to be searched against
     * @param search
     *          the search string from the user
     * @return if the search is a match or not (true/false)
     */
    private boolean searchByActors(Movie movie, String search) {

        for(Actor actor : movie.getActors()) {
            if(actor.getName().toLowerCase().contains(search.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

}

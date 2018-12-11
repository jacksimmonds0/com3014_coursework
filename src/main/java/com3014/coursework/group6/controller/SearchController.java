package com3014.coursework.group6.controller;

import com3014.coursework.group6.model.Movie;
import com3014.coursework.group6.service.MovieService;
import com3014.coursework.group6.service.SearchService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * The controller for the search results and the type-ahead search results
 */
@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private MovieService movieService;

    /**
     * The search results for the search page based on the term(s) entered in the search box
     *
     * @param term
     *          the term entered in the navbar search box
     * @param model
     *          the {@link ModelMap} to add the results to
     * @return the search view
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String showSearchResults(@RequestParam("term") String term, ModelMap model) {
        List<Movie> results = searchService.getSearchResults(term);

        model.addAttribute("term", term);
        model.addAttribute("results", results);
        model.addAttribute("ratings", movieService.getAllAvgRatingsForMovies(results));

        return "search";
    }

    /**
     * The search box REST endpoint to retrieve movie title and id for the type-ahead search dropdown
     *
     * @param term
     *          the current input for the search box
     * @return the JSON string for the list of id-title for the movies from the type-ahead search
     */
    @RequestMapping(value = "/searchbox", method = RequestMethod.GET, produces = {"application/json"})
    @ResponseBody
    public String getSearchboxResults(@RequestParam("term") String term) {

        // if the term is empty (i.e. the search box is empty) then return an empty array as the response
        if(term.equals("")) {
            return new JSONObject().put("response", new JSONArray()).toString();
        }

        return searchService.getSearchboxResults(term);
    }
}

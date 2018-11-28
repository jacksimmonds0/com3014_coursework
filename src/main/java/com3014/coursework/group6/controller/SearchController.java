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

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @Autowired
    private MovieService movieService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String showSearchResults(@RequestParam("term") String term, ModelMap model) {
        List<Movie> results = searchService.getSearchResults(term);

        model.addAttribute("term", term);
        model.addAttribute("results", results);
        model.addAttribute("ratings", movieService.getAllAvgRatingsForMovies(results));

        return "search";
    }

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

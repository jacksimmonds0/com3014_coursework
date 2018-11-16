package com3014.coursework.group6.controller;

import com3014.coursework.group6.service.SearchService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

@Controller
public class SearchController {

    @Autowired
    private SearchService searchService;

    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public String showSearchResults(@RequestParam("term") String term, ModelMap model) {

        model.addAttribute("term", term);
        model.addAttribute("results", searchService.getSearchResults(term));

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

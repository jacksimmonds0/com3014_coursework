package com3014.coursework.group6.controller;

import com3014.coursework.group6.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

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
}

package com3014.coursework.group6.controller;
import java.util.ArrayList;
import java.util.List;
import com3014.coursework.group6.dao.GenreDAO;
import com3014.coursework.group6.model.Genre;
import com3014.coursework.group6.model.Movie;
import com3014.coursework.group6.model.person.User;
import com3014.coursework.group6.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
public class AddMovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private GenreDAO genreDAO;

    @RequestMapping(value = "/addmovie", method = RequestMethod.GET)

    public ModelAndView showRegister(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = new ModelAndView("addmovie");
        mv.addObject("movie", new Movie());

        return mv;
    }
    public String getGenreList(Model model) throws Exception {
        List<Genre> genreList = genreDAO.getAllGenres();
        model.addAttribute("genreList", genreList);
        return "addmovie";
    }

    @RequestMapping(value ="/addmovieProcess", method = RequestMethod.POST, produces={"application/json"})
    public @ResponseBody ModelAndView addMovie(@RequestParam(value="year",required = true) int year, @RequestParam(value="title",required=true) String title, @RequestParam(value="description",required=true) String description, @RequestParam(value="genres",required=true) List<Integer> genres, HttpSession session, HttpServletRequest request){
        int dbResult = movieService.addMovie(year, title, description, genres);
        return new ModelAndView("redirect:movie?id=" + dbResult);
    }
}

package com3014.coursework.group6.controller;
import java.util.List;

import com3014.coursework.group6.dao.ActorDAO;
import com3014.coursework.group6.dao.DirectorDAO;
import com3014.coursework.group6.dao.GenreDAO;
import com3014.coursework.group6.model.Genre;
import com3014.coursework.group6.model.Movie;
import com3014.coursework.group6.service.MovieFieldsService;
import com3014.coursework.group6.service.MovieService;
import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Controller to map the form to add a movie to add all the fields in the database
 * Also retrieves genres, directors and actors from the database to populate drop-downs
 * that the user can select from when adding a movie
 */
@Controller
public class AddMovieController {

    @Autowired
    private MovieService movieService;

    @Autowired
    private MovieFieldsService movieFieldsService;

    /**
     * The controller for the mapping to the /addmovie endpoint which contains the form for adding
     * a movie to the database
     *
     * @return the {@link ModelAndView} for the addmovie jsp view with the model objects for the
     *         genres, actors and directors from the database
     */
    @RequestMapping(value = "/addmovie", method = RequestMethod.GET)
    public ModelAndView showAddMoviePage() {
        ModelAndView mv = new ModelAndView("addmovie");
        mv.addObject("movie", new Movie());

        mv.addObject("genreList", movieFieldsService.getAllGenres());
        mv.addObject("actorsList", movieFieldsService.getAllActors());
        mv.addObject("directorsList", movieFieldsService.getAllDirectors());

        return mv;
    }

    /**
     * The POST method called when submitting the form for adding a movie
     *
     * @param year
     *          the year the movie was released
     * @param title
     *          the movie title
     * @param description
     *          the description of the movie
     * @param genres
     *          the genres associated with that movie
     * @param director
     *          the director of the movie
     * @param actors
     *          the actors inthe movie
     * @param posterUrl
     *          the URL to the poster for the movie
     * @param session
     *          the session object containing the logged in user
     *
     * @return the model and view redirecting to the movie page that has just been created
     */
    @RequestMapping(value ="/addmovieProcess", method = RequestMethod.POST, produces={"application/json"})
    public @ResponseBody ModelAndView addMovie(@RequestParam(value="year",required = true) int year,
                                               @RequestParam(value="title",required=true) String title,
                                               @RequestParam(value="description",required=true) String description,
                                               @RequestParam(value="genres",required=true) List<Integer> genres,
                                               @RequestParam(value="director",required=true) String director,
                                               @RequestParam(value="actors",required=true) String actors,
                                               @RequestParam(value="posterUrl", required=false) String posterUrl,
                                               HttpSession session){

        // prevent XSS attacks on the poster URL
        posterUrl = StringEscapeUtils.escapeHtml(posterUrl);

        int dbResult = movieService.addMovie(year, title, description, genres, director, actors, posterUrl);
        return new ModelAndView("redirect:movie?id=" + dbResult);
    }

}

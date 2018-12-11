package com3014.coursework.group6.controller;

import com3014.coursework.group6.model.Comment;
import com3014.coursework.group6.model.person.User;
import com3014.coursework.group6.service.MovieService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * The controller for the individual movie page, allowing for comments and ratings to be added
 * for logged in user
 */
@Controller
public class MovieController {

    @Autowired
    private MovieService movieService;

    /**
     * The show movie page as a GET request on the /movie endpoint
     * of the form /movie?id=1 for the individual movie page
     *
     * @param movieID
     *          the id as a {@link RequestParam} for the movie to display
     * @param model
     *          the {@link ModelMap} for the models to be added to the page
     * @param session
     *          the {@link HttpSession} object containing the currently logged in user
     * @return the movie page for the movie based on the id
     */
    @RequestMapping(value = "/movie", method = RequestMethod.GET)
    public String showMovie(@RequestParam("id") int movieID, ModelMap model, HttpSession session) {
        User currentUser = (User)session.getAttribute("currentUser");
        if(currentUser != null){
            double individualRating = movieService.getIndivRating(movieID, currentUser.getId());
            model.addAttribute("individualRating",individualRating);
        }
        List<Comment> commentList = movieService.getComments(movieID);
        model.addAttribute("movieID", movieID);
        model.addAttribute("movie", movieService.getMovie(movieID));
        model.addAttribute("comments",commentList);
        return "movie";
    }

    /**
     * The POST method to add a comment to the movie
     *
     * @param movieID
     *          the id for the movie to add a comment to
     * @param title
     *          the title for the comment
     * @param comment
     *          the comment string itself
     * @param session
     *          the {@link HttpSession} object containing the currently logged in user
     * @return the JSON response for the comment added
     */
    @RequestMapping(value = "/movie/{id}/addcomment",
            method = RequestMethod.POST,
            produces = {"application/json"})
    @ResponseBody
    public String addComment(@PathVariable("id") int movieID, @RequestParam("title") String title, @RequestParam("comment") String comment, HttpSession session) {

        if(title.isEmpty()) {
            return "No title";
        }
        else if(comment.isEmpty()) {
            return "No comment";
        }
        User currentUser = (User)session.getAttribute("currentUser");
        Calendar calendar = Calendar.getInstance();
        java.util.Date now = calendar.getTime();
        java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

        int dbResult = movieService.addComment(movieID, currentUser.getId(),title,comment,currentTimestamp);
        String json = "{}";
        if(dbResult==1) {
            String comment_time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(currentTimestamp);

            json = new JSONObject()
                    .put("username", currentUser.getUsername())
                    .put("title", title)
                    .put("comment", comment)
                    .put("comment_time",comment_time).toString();
        }
        return json;
    }

}

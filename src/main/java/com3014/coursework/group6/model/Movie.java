package com3014.coursework.group6.model;

import com3014.coursework.group6.model.person.Actor;
import com3014.coursework.group6.model.person.Director;

import java.util.List;
import java.util.stream.Collectors;

public class Movie {

    private int id;
    private int year;
    private String title;
    private String description;
    private Director director;
    private List<Actor> actors;
    private List<Genre> genres;
    private List<Comment> comments;
    private List<Rating> ratings;
    private String posterUrl;

    public Movie() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public void setActors(List<Actor> actors) {
        this.actors = actors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    public String getActorsForSearchResult() {
        return actors.stream()
                .map(Actor::getName)
                .collect(Collectors.joining(", "));
    }

    public String getGenresForSearchResults() {
        return genres.stream()
                .map(Genre::getName)
                .collect(Collectors.joining(" | "));
    }


}

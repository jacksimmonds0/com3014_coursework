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

    private Movie() {
        // private constructor to prevent instantiation
    }

    public int getId() {
        return id;
    }

    public int getYear() {
        return year;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Director getDirector() {
        return this.director;
    }

    public List<Actor> getActors() {
        return actors;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public List<Rating> getRatings() {
        return ratings;
    }

    public String getActorsForSearchResult() {
        return actors.stream()
                .map(Actor::getName)
                .collect(Collectors.joining(", "));
    }

    public String getGenresForSearchResults() {
        return genres.stream()
                .map(Genre::getGenre)
                .collect(Collectors.joining(" | "));
    }

    /**
     * Builder pattern used due to large amount of fields
     */
    public static class Builder {
        private int id;
        private int year;
        private String title;
        private String description;
        private Director director;
        private List<Actor> actors;
        private List<Genre> genres;
        private List<Comment> comments;
        private List<Rating> ratings;

        public Builder(int id) {
            this.id = id;
        }

        public Builder year(int year) {
            this.year = year;

            return this;
        }

        public Builder title(String title) {
            this.title = title;

            return this;
        }

        public Builder description(String description) {
            this.description = description;

            return this;
        }

        public Builder director(Director director) {
            this.director = director;

            return this;
        }

        public Builder actors(List<Actor> actors) {
            this.actors = actors;

            return this;
        }

        public Builder genres(List<Genre> genres) {
            this.genres = genres;

            return this;
        }

        public Builder comments(List<Comment> comments) {
            this.comments = comments;

            return this;
        }

        public Builder ratings(List<Rating> ratings) {
            this.ratings = ratings;

            return this;
        }

        public Movie build() {
            Movie movie = new Movie();

            movie.id = this.id;
            movie.year = this.year;
            movie.title = this.title;
            movie.description = this.description;
            movie.director = this.director;
            movie.actors = this.actors;
            movie.genres = this.genres;
            movie.comments = this.comments;
            movie.ratings = this.ratings;

            return movie;
        }


    }


}

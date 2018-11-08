package com3014.coursework.group6.model;

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

public class Movie {

    private static final DecimalFormat RATING_FORMAT = new DecimalFormat("0.00");
    private int id;
    private int year;
    private String title;
    private String description;
    private List<Genre> genres;
    private List<String> comments;
    private List<Double> ratings;

    private Movie() {
        // private constructor to prevent instantiation
    }

    public String getTitle() {
        return this.title;
    }


    public String getAverageRating() {
        double total = 0.0;

        for(Double rating : ratings) {
            total += rating;
        }

        return RATING_FORMAT.format(total / ratings.size());
    }

    public JSONObject toJSONObject() {
        JSONObject json = new JSONObject();

        json.append("title", title);
        json.append("year", year);
        json.append("rating", this.getAverageRating());

        return json;
    }


    /**
     * Builder pattern used due to large amount of fields
     */
    public static class Builder {
        private int id;
        private int year;
        private String title;
        private String description;
        private List<Genre> genres;
        private List<String> comments;
        private List<Double> ratings;

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

        public Builder genres(List<Genre> genres) {
            this.genres = genres;

            return this;
        }

        public Builder comments(List<String> comments) {
            this.comments = comments;

            return this;
        }

        public Builder ratings(List<Double> ratings) {
            this.ratings = ratings;

            return this;
        }

        public Movie build() {
            Movie movie = new Movie();

            movie.id = this.id;
            movie.year = this.year;
            movie.title = this.title;
            movie.description = this.description;
            movie.comments = this.comments;
            movie.ratings = this.ratings;

            return movie;
        }


    }


}

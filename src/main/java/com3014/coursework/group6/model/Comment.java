package com3014.coursework.group6.model;

import com3014.coursework.group6.model.person.User;

import java.sql.Timestamp;

/**
 * The is an object to store the comment fields for a movie
 */
public class Comment {

    private int id;
    private int movie_id;
    private User user;
    private String title;
    private String comment;
    private String timestamp;

    public Comment() {
    }

    public int getId() {
        return id;
    }

    public int getMovie_id() {
        return movie_id;
    }

    public User getUser() {
        return user;
    }

    public String getComment() {
        return comment;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getTitle() {
        return title;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

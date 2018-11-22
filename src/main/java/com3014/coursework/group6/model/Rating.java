package com3014.coursework.group6.model;

import com3014.coursework.group6.model.person.User;

public class Rating {

    private User user;
    private double ratingValue;

    public Rating() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getRatingValue() {
        return ratingValue;
    }

    public void setRatingValue(double ratingValue) {
        this.ratingValue = ratingValue;
    }
}

package com3014.coursework.group6.model;

public enum Genre {

    COMEDY("Comedy"), SCI_FI("Sci-Fi"), HORROR("Horror"), ROMANCE("Romance"), ACTION("Action"), THRILLER("Thriller"),
        DRAMA("Drama"), MYSTERY("Mystery"), CRIME("Crime"), ANIMATION("Animation"), ADVENTURE("Adventure"),
        FANTASY("Fantasy"), COMEDY_ROMANCE("Comedy Romance"), ACTION_COMEDY("Action-Comedy"), SUPERHERO("Superhero");

    private String genre;

    private Genre(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return this.genre;
    }

}

package com3014.coursework.group6.model;

public class Genre {

    private int id;
    private String name;

    public Genre() {
    }

    public Genre(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

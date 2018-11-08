package com3014.coursework.group6.dao;

import com3014.coursework.group6.model.Movie;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class MovieDAO {

    public List<Movie> getAllMovies() {

        Movie m1 = new Movie.Builder(1)
                .title("The Film 1")
                .build();


        Movie m2 = new Movie.Builder(2)
                .title("The Film 2")
                .build();


        Movie m3 = new Movie.Builder(3)
                .title("The Film 3")
                .build();


        Movie m4 = new Movie.Builder(4)
                .title("The Film 4")
                .build();



        return Arrays.asList(m1, m2, m3, m4);
    }

}

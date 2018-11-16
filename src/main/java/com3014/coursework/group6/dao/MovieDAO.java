package com3014.coursework.group6.dao;

import com3014.coursework.group6.model.Genre;
import com3014.coursework.group6.model.Movie;
import com3014.coursework.group6.model.person.Actor;
import com3014.coursework.group6.model.person.Director;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class MovieDAO {

    public List<Movie> getAllMovies() {
        List<Genre> genres1 = Arrays.asList(Genre.ACTION, Genre.COMEDY);
        List<Genre> genres2 = Arrays.asList(Genre.DRAMA, Genre.MYSTERY);

        List<Actor> actors1 = Arrays.asList(
                new Actor("Person", "One", ""),
                new Actor("Person", "Two", "")
        );

        List<Actor> actors2 = Arrays.asList(
                new Actor("Person", "Three", ""),
                new Actor("Person", "Four", "")
        );

        Movie m1 = new Movie.Builder(1)
                .title("The Film 1")
                .year(2018)
                .genres(genres1)
                .actors(actors1)
                .director(new Director("This", "Director", ""))
                .description("This is a movie")
                .build();


        Movie m2 = new Movie.Builder(2)
                .title("The Film 2")
                .year(2010)
                .genres(genres2)
                .actors(actors1)
                .director(new Director("That", "Director", ""))
                .description("This is another movie")
                .build();


        Movie m3 = new Movie.Builder(3)
                .title("The Film 3")
                .year(1999)
                .genres(genres1)
                .actors(actors2)
                .director(new Director("Another", "Director", ""))
                .description("An additional movie")
                .build();


        Movie m4 = new Movie.Builder(4)
                .title("The Film 4")
                .year(1950)
                .genres(genres2)
                .actors(actors2)
                .director(new Director("Final", "Director", ""))
                .description("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi dapibus laoreet nunc vitae " +
                        "venenatis. Quisque ligula orci, dignissim eu lacus ac, eleifend pulvinar eros. Aliquam sit amet " +
                        "purus a lectus porta placerat a et justo. Ut consequat metus suscipit, porttitor magna in, vestibulum " +
                        "magna. Phasellus at mauris nec metus condimentum consectetur id at orci. Cras purus justo, semper " +
                        "aliquam elit sit amet, consectetur dictum ex. Nunc pretium nisl at dolor ultricies pellentesque. " +
                        "Cras sagittis urna est, ut convallis lorem porttitor.")
                .build();


        return Arrays.asList(m1, m2, m3, m4);
    }

}

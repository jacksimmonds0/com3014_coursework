package com3014.coursework.group6.dao;

import com3014.coursework.group6.dao.mapper.MovieMapper;
import com3014.coursework.group6.model.Genre;
import com3014.coursework.group6.model.Movie;
import com3014.coursework.group6.model.person.Actor;
import com3014.coursework.group6.model.person.Director;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieDAO {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<Movie> getAllMoviesFromDB() {
        String sql = "SELECT * FROM movies";

        return jdbcTemplate.query(sql, new MovieMapper());
    }

}

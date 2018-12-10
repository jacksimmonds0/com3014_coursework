package com3014.coursework.group6.dao.mapper;

import com3014.coursework.group6.model.Movie;
import com3014.coursework.group6.model.person.Director;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MovieMapper implements RowMapper<Movie> {

    @Override
    public Movie mapRow(ResultSet rs, int rowNum) throws SQLException {
        Movie movie = new Movie();

        movie.setId(rs.getInt("id"));
        movie.setTitle(rs.getString("title"));
        movie.setDescription(rs.getString("description"));
        movie.setYear(rs.getInt("year"));
        movie.setPosterUrl(rs.getString("poster_url"));

        Director director = new Director();
        director.setId(rs.getInt("director_id"));
        movie.setDirector(director);

        return movie;
    }
}

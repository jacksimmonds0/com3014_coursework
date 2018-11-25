package com3014.coursework.group6.dao;

import com3014.coursework.group6.dao.mapper.GenreMapper;
import com3014.coursework.group6.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenreDAO {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<Genre> getGenresForMovie(int movieId) {
        String sql = "SELECT * FROM genre LEFT JOIN movie_genre g on genre.id = g.genre_id WHERE movie_id=:id";

        MapSqlParameterSource namedParameter = new MapSqlParameterSource();
        namedParameter.addValue("id", movieId);

        return jdbcTemplate.query(sql, namedParameter, new GenreMapper());
    }

    public List<Genre> getAllGenres() {
        String sql = "SELECT * From genre";

        return jdbcTemplate.query(sql, new GenreMapper());
    }
}

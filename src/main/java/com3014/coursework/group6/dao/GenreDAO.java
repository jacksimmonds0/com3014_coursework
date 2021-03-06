package com3014.coursework.group6.dao;

import com3014.coursework.group6.dao.mapper.GenreMapper;
import com3014.coursework.group6.model.Genre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Data Access Object for the genre objects.
 * Contains database interactions for any queries relating to genres.
 */
@Repository
public class GenreDAO {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     *
     * @param movieId
     * @return list of genres associated with specific movie
     */
    public List<Genre> getGenresForMovie(int movieId) {
        String sql = "SELECT * FROM genre LEFT JOIN movie_genre g on genre.id = g.genre_id WHERE movie_id=:id";

        MapSqlParameterSource namedParameter = new MapSqlParameterSource();
        namedParameter.addValue("id", movieId);

        return jdbcTemplate.query(sql, namedParameter, new GenreMapper());
    }

    /**
     *
     * @return list of all genres
     */
    public List<Genre> getAllGenres() {
        String sql = "SELECT * From genre";

        return jdbcTemplate.query(sql, new GenreMapper());
    }
}

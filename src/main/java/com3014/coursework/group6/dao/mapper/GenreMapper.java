package com3014.coursework.group6.dao.mapper;

import com3014.coursework.group6.model.Genre;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GenreMapper implements RowMapper<Genre> {

    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        Genre genre = new Genre();

        genre.setId(rs.getInt("id"));
        genre.setName(rs.getString("name"));

        return genre;
    }
}

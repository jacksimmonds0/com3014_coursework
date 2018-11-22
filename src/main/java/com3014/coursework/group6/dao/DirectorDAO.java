package com3014.coursework.group6.dao;

import com3014.coursework.group6.dao.mapper.DirectorMapper;
import com3014.coursework.group6.model.person.Director;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DirectorDAO {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public Director getDirectorForMovie(int id) {
        String sql = "SELECT * FROM directors WHERE id=:id";

        MapSqlParameterSource namedParameter = new MapSqlParameterSource();
        namedParameter.addValue("id", id);

        List<Director> directors = jdbcTemplate.query(sql, namedParameter, new DirectorMapper());

        return directors.size() == 1 ? directors.get(0) : null;
    }
}

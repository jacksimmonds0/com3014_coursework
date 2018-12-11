package com3014.coursework.group6.dao;

import com3014.coursework.group6.dao.mapper.ActorMapper;
import com3014.coursework.group6.model.person.Actor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ActorDAO {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     *
     * @param movieId
     * @return list of actors corresponding to parameterized movie id.
     */
    public List<Actor> getActorsForMovie(int movieId) {
        String sql = "SELECT * FROM actors LEFT JOIN movie_actor a on actors.id = a.actor_id WHERE movie_id=:id";

        MapSqlParameterSource namedParameter = new MapSqlParameterSource();
        namedParameter.addValue("id", movieId);

        return jdbcTemplate.query(sql, namedParameter, new ActorMapper());
    }

    /**
     *
     * @return list of all actors
     */
    public List<Actor> getAllActors() {
        String sql = "SELECT * FROM actors";

        return jdbcTemplate.query(sql, new ActorMapper());
    }

}

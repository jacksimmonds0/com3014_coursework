package com3014.coursework.group6.dao.mapper;

import com3014.coursework.group6.model.person.Actor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActorMapper implements RowMapper<Actor> {

    @Override
    public Actor mapRow(ResultSet rs, int numRow) throws SQLException {
        Actor actor = new Actor();

        actor.setId(rs.getInt("id"));
        actor.setFirstName(rs.getString("first_name"));
        actor.setLastName(rs.getString("last_name"));

        return actor;
    }
}

package com3014.coursework.group6.dao.mapper;

import com3014.coursework.group6.model.person.Actor;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ActorMapper implements RowMapper<Actor> {

    /**
     * Maps an SQL query resultset row to an actor object
     * @param rs
     * @param numRow
     * @return
     * @throws SQLException
     */
    @Override
    public Actor mapRow(ResultSet rs, int numRow) throws SQLException {
        Actor actor = new Actor();

        actor.setId(rs.getInt("id"));
        actor.setFirstName(rs.getString("first_name"));
        actor.setLastName(rs.getString("last_name"));

        return actor;
    }
}

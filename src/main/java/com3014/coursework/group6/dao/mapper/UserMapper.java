package com3014.coursework.group6.dao.mapper;

import com3014.coursework.group6.model.person.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper implements RowMapper<User> {

    /**
     * Maps an SQL query resultset row to an user object
     * @param rs
     * @param arg1
     * @return
     * @throws SQLException
     */
    @Override
    public User mapRow(ResultSet rs, int arg1) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setFirstName(rs.getString("first_name"));
        user.setLastName(rs.getString("last_name"));
        user.setEmail(rs.getString("email_address"));
        user.setStatus(rs.getString("status"));
        try {
            user.setRole(rs.getString("role"));
        } catch (java.sql.SQLException e) {

        }
        return user;
    }
}
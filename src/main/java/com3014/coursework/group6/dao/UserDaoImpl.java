package com3014.coursework.group6.dao;

import java.util.List;
import javax.sql.DataSource;

import com3014.coursework.group6.dao.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import com3014.coursework.group6.model.Login;
import com3014.coursework.group6.model.person.User;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

public class UserDaoImpl implements UserDao {

    @Autowired
    DataSource dataSource;

    @Autowired
    NamedParameterJdbcTemplate jdbcTemplate;

    public void register(User user) {

        String sql = "INSERT INTO users (username, password, first_name, last_name, email_address) VALUES(:username, :password, :firstName, :lastName, :email)";
        jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(user));
    }

    public void assignUserRole(String username) {
        String sql = "INSERT INTO user_roles (username, role) VALUES(:username, 'ROLE_USER')";
        MapSqlParameterSource namedParameter = new MapSqlParameterSource();
        namedParameter.addValue("username", username);
        jdbcTemplate.update(sql, namedParameter);
    }

    public boolean userExists(String username) {
        String sql = "SELECT * FROM users WHERE username=':username'";
        MapSqlParameterSource namedParameter = new MapSqlParameterSource();
        namedParameter.addValue("username", username);

        List<User> user = jdbcTemplate
                .query(sql, namedParameter, new UserMapper());

        return (user.size() > 0);
    }

    public User validateUser(Login login) {
        String sql = "SELECT * FROM users WHERE username='" + login.getUsername() + "' AND password='" + login.getPassword()
                + "'";
        List<User> users = jdbcTemplate.query(sql, new UserMapper());

        return users.size() > 0 ? users.get(0) : null;
    }

    public User getUserByID(int id){
        String sql = "SELECT * FROM users WHERE id = :id";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);

        User user = jdbcTemplate.queryForObject(sql, namedParameters, new UserMapper());

        return user;
    }

    public List getUserRoles(String username) {
        String sql = "SELECT role FROM user_roles WHERE username = :username";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("username", username);

        List roles = jdbcTemplate
                .queryForList(sql, namedParameters, String.class);

        return roles;
    }

}

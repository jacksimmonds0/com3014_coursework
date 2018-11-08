package com3014.coursework.group6.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import com3014.coursework.group6.model.Login;
import com3014.coursework.group6.model.User;

public class UserDaoImpl implements UserDao {

    @Autowired
    DataSource datasource;

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void register(User user) {

        String sql = "INSERT INTO USERS (username, password, first_name, last_name, email_address) VALUES(?,?,?,?,?)";
        jdbcTemplate.update(sql, new Object[]{user.getUsername(), user.getPassword(), user.getFirstname(),
                user.getLastname(), user.getEmail()});
    }

    public User validateUser(Login login) {
        String sql = "SELECT * FROM users WHERE username='" + login.getUsername() + "' AND password='" + login.getPassword()
                + "'";
        List<User> users = jdbcTemplate.query(sql, new UserMapper());

        return users.size() > 0 ? users.get(0) : null;
    }
}

class UserMapper implements RowMapper<User> {
    public User mapRow(ResultSet rs, int arg1) throws SQLException {
        User user = new User();
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password"));
        user.setFirstname(rs.getString("first_name"));
        user.setLastname(rs.getString("last_name"));
        user.setEmail(rs.getString("email_address"));
        return user;
    }
}
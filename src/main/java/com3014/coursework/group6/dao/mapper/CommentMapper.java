package com3014.coursework.group6.dao.mapper;

import com3014.coursework.group6.model.Comment;
import com3014.coursework.group6.model.person.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

public class CommentMapper implements RowMapper<Comment> {

    /**
     * Maps an SQL query resultset row to an comment object
     * @param rs
     * @param rowNum
     * @return
     * @throws SQLException
     */
    @Override
    public Comment mapRow(ResultSet rs, int rowNum) throws SQLException {
        Comment comment = new Comment();

        comment.setId(rs.getInt("id"));
        User user = new User();
        user.setId(rs.getInt("user_id"));
        comment.setUser(user);
        comment.setMovie_id(rs.getInt("movie_id"));
        comment.setTitle(rs.getString("title"));
        comment.setComment(rs.getString("comment"));
        String comment_time = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(rs.getTimestamp("comment_time"));
        comment.setTimestamp(comment_time);

        return comment;
    }
}

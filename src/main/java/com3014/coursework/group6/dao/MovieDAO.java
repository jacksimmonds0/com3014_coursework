package com3014.coursework.group6.dao;

import com3014.coursework.group6.dao.mapper.MovieMapper;
import com3014.coursework.group6.model.Genre;
import com3014.coursework.group6.model.Movie;
import com3014.coursework.group6.model.person.Actor;
import com3014.coursework.group6.model.person.Director;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MovieDAO {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public List<Movie> getAllMoviesFromDB() {
        String sql = "SELECT * FROM movies";

        return jdbcTemplate.query(sql, new MovieMapper());
    }

    public Movie getMovieFromDB(int id){
        String sql = "SELECT * FROM movies WHERE id = :id";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);

        Movie movie = jdbcTemplate
                .queryForObject(sql, namedParameters,new MovieMapper());

        return movie;
    }


    public int addRating(int movie_id, int user_id, double rating) {
        String sql = "UPDATE ratings set rating = :rating WHERE movie_id = :movie_id AND user_id = :user_id";
        MapSqlParameterSource namedParameter = new MapSqlParameterSource();
        namedParameter.addValue("rating", rating);
        namedParameter.addValue("movie_id",movie_id);
        namedParameter.addValue("user_id",user_id);
        int result = jdbcTemplate.update(sql, namedParameter);
        if(result == 0){
            sql = "INSERT INTO ratings (movie_id, user_id, rating) VALUES(:movie_id, :user_id, :rating)";
            namedParameter = new MapSqlParameterSource();
            namedParameter.addValue("movie_id",movie_id);
            namedParameter.addValue("user_id",user_id);
            namedParameter.addValue("rating", rating);
            result = jdbcTemplate.update(sql, namedParameter);
        }
        return result;
    }

    public double getAvgRating(int movie_id){
        String sql = "SELECT rating FROM ratings WHERE movie_id = :movie_id";
        MapSqlParameterSource namedParameter = new MapSqlParameterSource();
        namedParameter.addValue("movie_id",movie_id);
        List<Double> ratings = jdbcTemplate.queryForList(sql,namedParameter,Double.class);
        double total = 0.0;
        for (double r : ratings) {
            total+=r;
        }
        return (total/ratings.size());
    }

    public double getIndivRating(int movie_id, int user_id){
        double rating = 2.5;
        String sql = "SELECT COUNT(rating) FROM ratings WHERE movie_id = :movie_id AND user_id = :user_id";
        MapSqlParameterSource namedParameter = new MapSqlParameterSource();
        namedParameter.addValue("movie_id",movie_id);
        namedParameter.addValue("user_id",user_id);
        int count = jdbcTemplate.queryForObject(sql, namedParameter, Integer.class);
        if(count==1){
            sql = "SELECT rating FROM ratings WHERE movie_id = :movie_id AND user_id = :user_id";
            namedParameter = new MapSqlParameterSource();
            namedParameter.addValue("movie_id",movie_id);
            namedParameter.addValue("user_id",user_id);
            rating = jdbcTemplate.queryForObject(sql, namedParameter, Double.class);
        }


        return rating;
    }

}

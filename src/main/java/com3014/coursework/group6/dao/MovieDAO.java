package com3014.coursework.group6.dao;

import com3014.coursework.group6.dao.mapper.CommentMapper;
import com3014.coursework.group6.dao.mapper.MovieMapper;
import com3014.coursework.group6.model.Comment;
import com3014.coursework.group6.model.Genre;
import com3014.coursework.group6.model.Movie;
import com3014.coursework.group6.model.person.Actor;
import com3014.coursework.group6.model.person.Director;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public int addComment(int movie_id, int user_id, String title, String comment, Timestamp timestamp) {
            String sql = "INSERT INTO comments (movie_id, user_id, title, comment, comment_time) VALUES(:movie_id, :user_id, :title, :comment, :comment_time)";
            MapSqlParameterSource namedParameter = new MapSqlParameterSource();
            namedParameter.addValue("movie_id",movie_id);
            namedParameter.addValue("user_id",user_id);
            namedParameter.addValue("title", title);
            namedParameter.addValue("comment",comment);
            namedParameter.addValue("comment_time",timestamp);
            int result = jdbcTemplate.update(sql, namedParameter);
        return result;
    }

    public int addMovie(int year, String title, String description, List<Integer> genres, String director, String actors, String posterUrl) {
        String sql = "INSERT INTO movies (id, year, title, description, director_id, poster_url) VALUES(null, :year, :title, :description, 1, :poster_url)";
        MapSqlParameterSource namedParameter = new MapSqlParameterSource();
        namedParameter.addValue("id",null);
        namedParameter.addValue("year",year);
        namedParameter.addValue("title", title);
        namedParameter.addValue("description",description);
        namedParameter.addValue("director_id",1);

        if(posterUrl != null) {
            namedParameter.addValue("poster_url", posterUrl);
        }

        jdbcTemplate.update(sql, namedParameter);
        String sql2 = "SELECT MAX(id) FROM movies";
        int result2 = jdbcTemplate.queryForObject(sql2,namedParameter,Integer.class);
        for (int i = 0; i < genres.size(); i++) {
            String sql4 = "INSERT INTO movie_genre (id, movie_id, genre_id) VALUES(null, " + result2 + ", " + genres.get(i) + ")";
            MapSqlParameterSource namedParameter2 = new MapSqlParameterSource();
            namedParameter2.addValue("id",null);
            namedParameter2.addValue("movie_id",result2);
            namedParameter2.addValue("genre_id", genres.get(i));
            jdbcTemplate.update(sql4, namedParameter2);
        }
        String[] splitting = director.split(" ");

        int new_director_id = addDirector(splitting[0], splitting[1]);

        updateDirector(new_director_id, result2);

        String[] splitting2 = actors.split(",");

        for (int i = 0; i < splitting2.length; i++) {
            String[] splitting3 = splitting2[i].split(" ");

            int new_actor_id = addActor(splitting3[0], splitting3[1]);

            updateActor(result2, new_actor_id);
        }

        return result2;
    }

    public int addDirector(String first_name, String last_name){

        String checksql = "SELECT COUNT(*) FROM directors WHERE first_name = :first_name AND last_name = :last_name";
        MapSqlParameterSource namedParameter = new MapSqlParameterSource();
        namedParameter.addValue("first_name",first_name);
        namedParameter.addValue("last_name",last_name);
        int exists = jdbcTemplate.queryForObject(checksql,namedParameter,Integer.class);

        int result = 0;

        if(exists == 0){
            String sql = "INSERT INTO directors (id, first_name, last_name) VALUES(null, :first_name, :last_name)";
            MapSqlParameterSource namedParameter2 = new MapSqlParameterSource();
            namedParameter2.addValue("id",null);
            namedParameter2.addValue("first_name",first_name);
            namedParameter2.addValue("last_name",last_name);
            jdbcTemplate.update(sql, namedParameter2);

            String sql2 = "SELECT MAX(id) FROM directors";
            result = jdbcTemplate.queryForObject(sql2,namedParameter2,Integer.class);
        }

        else{
            String sql = "SELECT MAX(id) FROM directors WHERE first_name = :first_name AND last_name = :last_name";
            MapSqlParameterSource namedParameter2 = new MapSqlParameterSource();
            namedParameter2.addValue("first_name",first_name);
            namedParameter2.addValue("last_name",last_name);
            result = jdbcTemplate.queryForObject(sql,namedParameter2,Integer.class);
        }

        return result;
    }

    public int updateDirector(int director_id, int id){

        String sql = "UPDATE movies SET director_id = :director_id WHERE id = :id";
        MapSqlParameterSource namedParameter = new MapSqlParameterSource();
        namedParameter.addValue("director_id",director_id);
        namedParameter.addValue("id",id);
        jdbcTemplate.update(sql, namedParameter);

        return 1;
    }

    public int addActor(String first_name, String last_name){

        String checksql = "SELECT COUNT(*) FROM actors WHERE first_name = :first_name AND last_name = :last_name";
        MapSqlParameterSource namedParameter = new MapSqlParameterSource();
        namedParameter.addValue("first_name",first_name);
        namedParameter.addValue("last_name",last_name);
        int exists = jdbcTemplate.queryForObject(checksql,namedParameter,Integer.class);

        int result = 0;

        if(exists == 0) {
            String sql = "INSERT INTO actors (id, first_name, last_name) VALUES(null, :first_name, :last_name)";
            MapSqlParameterSource namedParameter2 = new MapSqlParameterSource();
            namedParameter2.addValue("id", null);
            namedParameter2.addValue("first_name", first_name);
            namedParameter2.addValue("last_name", last_name);
            jdbcTemplate.update(sql, namedParameter2);

            String sql2 = "SELECT MAX(id) FROM actors";
            result = jdbcTemplate.queryForObject(sql2, namedParameter2, Integer.class);
        }

        else{
            String sql = "SELECT MAX(id) FROM actors WHERE first_name = :first_name AND last_name = :last_name";
            MapSqlParameterSource namedParameter2 = new MapSqlParameterSource();
            namedParameter2.addValue("first_name",first_name);
            namedParameter2.addValue("last_name",last_name);
            result = jdbcTemplate.queryForObject(sql,namedParameter2,Integer.class);
        }

        return result;
    }

    public int updateActor(int movie_id, int actor_id){

        String sql = "INSERT INTO movie_actor (id, movie_id, actor_id) VALUES(null, :movie_id, :actor_id)";
        MapSqlParameterSource namedParameter = new MapSqlParameterSource();
        namedParameter.addValue("id",null);
        namedParameter.addValue("movie_id",movie_id);
        namedParameter.addValue("actor_id", actor_id);
        jdbcTemplate.update(sql, namedParameter);

        return 1;
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

        double avgRating = 0.0;
        if(ratings.size() != 0) {
            avgRating = total/ratings.size();
        }

        return avgRating;
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

    public List<Comment> getComments(int movie_id){
        String sql = "SELECT * FROM comments WHERE movie_id = :movie_id";
        MapSqlParameterSource namedParameter = new MapSqlParameterSource();
        namedParameter.addValue("movie_id",movie_id);
        return jdbcTemplate.query(sql,namedParameter, new CommentMapper());
    }

    public List<Movie> getSimilarMovies(int movie_id){
        Movie m = getMovieFromDB(movie_id);
        String sql = "SELECT * FROM movies WHERE director_id = :director_id AND id <> :movie_id";
        MapSqlParameterSource namedParameter = new MapSqlParameterSource();
        namedParameter.addValue("movie_id",movie_id);
        namedParameter.addValue("director_id",m.getDirector().getId());
        List<Movie> movies = jdbcTemplate.query(sql,namedParameter, new MovieMapper());
        sql = "SELECT genre_id FROM movie_genre WHERE movie_id = :movie_id";
        List<Integer> genres = jdbcTemplate.queryForList(sql,namedParameter, Integer.class);
        sql = "SELECT movie_id FROM movie_genre WHERE genre_id IN (:genres) AND movie_id <> :movie_id";
        namedParameter.addValue("genres",genres);
        List<Integer> movie_ids1 = jdbcTemplate.queryForList(sql,namedParameter,Integer.class);
        if(movie_ids1.size()>=1){
            sql = "SELECT * FROM movies WHERE id IN (:movie_ids1)";
            namedParameter.addValue("movie_ids1",movie_ids1);
            movies.addAll(jdbcTemplate.query(sql,namedParameter, new MovieMapper()));
        }
        sql = "SELECT actor_id FROM movie_actor WHERE movie_id = :movie_id";
        List<Integer> actors = jdbcTemplate.queryForList(sql,namedParameter,Integer.class);
        if(actors.size()==1){
            sql = "SELECT movie_id FROM movie_actor WHERE actor_id = :actor AND movie_id <> :movie_id";
            int actor = actors.get(0);
            namedParameter.addValue("actor",actor);
        }else{
            sql = "SELECT movie_id FROM movie_actor WHERE actor_id IN (:actors) AND movie_id <> :movie_id";
            namedParameter.addValue("actors",actors);
        }
        List<Integer> movie_ids2 = jdbcTemplate.queryForList(sql,namedParameter,Integer.class);
        if(movie_ids2.size()>=1) {
            sql = "SELECT * FROM movies WHERE id IN (:movie_ids2)";
            namedParameter.addValue("movie_ids2", movie_ids2);
            movies.addAll(jdbcTemplate.query(sql, namedParameter, new MovieMapper()));
        }

        return movies;
    }

    public Map<Movie, List<Movie>> getRecommendedMovies(int user_id){
        String sql = "SELECT movie_id FROM ratings WHERE user_id = :user_id AND rating >= 4";
        MapSqlParameterSource namedParameter = new MapSqlParameterSource();
        namedParameter.addValue("user_id",user_id);
        List<Integer> movie_ids = jdbcTemplate.queryForList(sql,namedParameter,Integer.class);
        Map<Movie, List<Movie>> movieRecs = new HashMap<>();
        for (int movie_id : movie_ids) {
            Movie m = getMovieFromDB(movie_id);
            List<Movie> recommendations = getSimilarMovies(movie_id);
            movieRecs.put(m,recommendations);
        }
        return movieRecs;
    }

    public List<Movie> getMostRecentlyAddedMovies() {
        String sql = "SELECT * FROM movies ORDER BY id DESC LIMIT 6";

        return jdbcTemplate.query(sql, new MovieMapper());
    }

}

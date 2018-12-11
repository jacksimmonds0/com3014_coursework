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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Data Access Object for the movie objects.
 * Contains database interactions for any queries relating to movies.
 */
@Repository
public class MovieDAO {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    /**
     *
     * @return list of all movies from the database
     */
    public List<Movie> getAllMoviesFromDB() {
        String sql = "SELECT * FROM movies";

        return jdbcTemplate.query(sql, new MovieMapper());
    }


    /**
     *
     * @param id
     * @return specific movie with the corresponding parameterized id.
     */
    public Movie getMovieFromDB(int id){
        String sql = "SELECT * FROM movies WHERE id = :id";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("id", id);

        Movie movie = jdbcTemplate
                .queryForObject(sql, namedParameters,new MovieMapper());

        return movie;
    }

    /**
     *
     * @param movie_id
     * @param user_id
     * @param rating
     *
     * The following attempts to update the rating of the specified movie for the specified user
     * If no previous rating exists for this user and this movie, then the database query returns 0
     * This will, in turn, cause the insert statement to be executed.
     *
     * @return the database result
     */
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

    /**
     *
     * @param movie_id
     * @param user_id
     * @param title
     * @param comment
     * @param timestamp
     *
     * Inserts a comment into the database with the parameters provided
     *
     * @return the database result
     */
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

    /**
     *
     * @param year
     * @param title
     * @param description
     * @param genres
     * @param director
     * @param actors
     * @param posterUrl
     *
     * Since there is a foreign key constraint on the director_id field, the director is first added into the database.
     * A director consists of a first name and a last name, and is therefore split using a " " (space) delimiter.
     *
     * The director_id is then retrieved by executing the addDirector function.
     * The movie is now inserted into the database.
     *
     * The movie_genre table has a foreign key constraint on the movie_id and therefore the genres are
     * added to this table after inserting the movie first.
     *
     * Actors need to be added to the actor table before referenced in movie_actor.
     * This is achieved by running addActor before updateActor.
     *
     * @return the movie_id of the newly inserted movie.
     */
    public int addMovie(int year, String title, String description, List<Integer> genres, String director, String actors, String posterUrl) {
        String[] splitting = director.split(" ");

        int new_director_id = addDirector(splitting[0], splitting[1]);

        String sql = "INSERT INTO movies (id, year, title, description, director_id, poster_url) VALUES(null, :year, :title, :description, :director_id, :poster_url)";
        MapSqlParameterSource namedParameter = new MapSqlParameterSource();
        namedParameter.addValue("id",null);
        namedParameter.addValue("year",year);
        namedParameter.addValue("title", title);
        namedParameter.addValue("description",description);
        namedParameter.addValue("director_id",new_director_id);

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

        String[] splitting2 = actors.split(",");

        for (int i = 0; i < splitting2.length; i++) {
            String[] splitting3 = splitting2[i].split(" ");

            int new_actor_id = addActor(splitting3[0], splitting3[1]);

            updateActor(result2, new_actor_id);
        }

        return result2;
    }

    /**
     *
     * @param first_name
     * @param last_name
     *
     * If a director corresponding to the parameters exists then the id of said director is returned.
     * else, a new record is created for the director and the id of that record is returned.
     * @return director id
     */
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

    /**
     *
     * @param first_name
     * @param last_name
     *
     * If a actor corresponding to the parameters exists then the id of said actor is returned.
     * else, a new record is created for the actor and the id of that record is returned.
     * @return actor id
     */
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

    /**
     *
     * @param movie_id
     * @param actor_id
     *
     * Creates an associative row that connects the parameterized movie to the parameterized actor
     *
     */
    public int updateActor(int movie_id, int actor_id){

        String sql = "INSERT INTO movie_actor (id, movie_id, actor_id) VALUES(null, :movie_id, :actor_id)";
        MapSqlParameterSource namedParameter = new MapSqlParameterSource();
        namedParameter.addValue("id",null);
        namedParameter.addValue("movie_id",movie_id);
        namedParameter.addValue("actor_id", actor_id);
        jdbcTemplate.update(sql, namedParameter);

        return 1;
    }

    /**
     *
     * @param movie_id
     *
     * Gets all the ratings associated with a movie_id
     * Calculates the mean average (total / amount)
     *
     * @return mean average rating of specified movie.
     */
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
        }else{
            avgRating = 3;
        }

        return avgRating;
    }


    /**
     *
     * @param movie_id
     * @param user_id
     *
     * Gets individual rating for specified movie from specified user
     * If no rating has been specified, then set default of 3.
     *
     * @return rating for user and movie
     */
    public double getIndivRating(int movie_id, int user_id){
        double rating = 3;
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

    /**
     *
     * @param movie_id
     *
     * Gets all comments records for a movie
     * @return list of comment objects, achieved by using CommentMapper
     */
    public List<Comment> getComments(int movie_id){
        String sql = "SELECT * FROM comments WHERE movie_id = :movie_id";
        MapSqlParameterSource namedParameter = new MapSqlParameterSource();
        namedParameter.addValue("movie_id",movie_id);
        return jdbcTemplate.query(sql,namedParameter, new CommentMapper());
    }

    /**
     *
     * @param movie_id
     * @param user_id
     *
     * First, a movie object is retrieved corresponding to the movie_id provided.
     * The first SQL script gets a list of movie objects with the same director as the movie in question.
     * The second SQL script gets all of the genres associated with the movie in question.
     * The third SQL script gets a list of movie_ids with at least one of the same genres as the movie in question.
     * The fourth SQL script retrieves a list of movie objects associated with the above movie_ids
     * The fifth SQL script retrieves all of the actors associated with the movie in question.
     * The sixth SQL script gets a list of movie_ids with at least one of the same actors as the movie in question.
     * The seventh SQL script gets a list of movie_ids with the same actor (if only one) as the movie in question.
     * The eighth SQL script retrieves a list of movie objects associated with the above movie_ids
     *
     * All of the movie object lists are combined, and the SQL scripts are formatted so that
     * no duplications occur, and only the movies that the user has not yet rated are selected.
     *
     * If the movie object list is larger than 6 objects, then it is truncated.
     *
     * @return list of similar movies
     */
    public List<Movie> getSimilarMovies(int movie_id, int user_id){
        Movie m = getMovieFromDB(movie_id);

        String sql = "SELECT m.* \n" +
                "                FROM movies m\n" +
                "        LEFT JOIN ratings r ON r.movie_id = m.id AND r.user_id = :user_id\n" +
                "        WHERE director_id = :director_id\n" +
                "        AND m.id <> :movie_id\n" +
                "        AND r.rating IS NULL";
        MapSqlParameterSource namedParameter = new MapSqlParameterSource();
        namedParameter.addValue("movie_id",movie_id);
        namedParameter.addValue("user_id",user_id);
        namedParameter.addValue("director_id",m.getDirector().getId());
        List<Movie> movies = jdbcTemplate.query(sql,namedParameter, new MovieMapper());
        List<Integer> movie_ids = new ArrayList<>();
        movie_ids.add(movie_id);
        for (Movie movie : movies) {
            movie_ids.add(movie.getId());
        }
        namedParameter.addValue("movie_ids",movie_ids);
        sql = "SELECT genre_id FROM movie_genre WHERE movie_id = :movie_id";
        List<Integer> genres = jdbcTemplate.queryForList(sql,namedParameter, Integer.class);
        sql = "SELECT movie_id FROM movie_genre WHERE genre_id IN (:genres) AND NOT movie_id IN (:movie_ids)";
        namedParameter.addValue("genres",genres);
        List<Integer> movie_ids1 = jdbcTemplate.queryForList(sql,namedParameter,Integer.class);
        movie_ids.addAll(movie_ids1);
        if(movie_ids1.size()>=1){
            sql = "SELECT m.* FROM movies m LEFT JOIN ratings r ON r.movie_id = m.id AND r.user_id = :user_id WHERE id IN (:movie_ids1) AND r.rating IS NULL";
            namedParameter.addValue("movie_ids1",movie_ids1);
            movies.addAll(jdbcTemplate.query(sql,namedParameter, new MovieMapper()));
        }
        namedParameter.addValue("movie_ids",movie_ids);
        sql = "SELECT actor_id FROM movie_actor WHERE movie_id = :movie_id";
        List<Integer> actors = jdbcTemplate.queryForList(sql,namedParameter,Integer.class);
        if(actors.size()==1){
            sql = "SELECT movie_id FROM movie_actor WHERE actor_id = :actor AND NOT movie_id IN (:movie_ids)";
            int actor = actors.get(0);
            namedParameter.addValue("actor",actor);
        }else{
            sql = "SELECT movie_id FROM movie_actor WHERE actor_id IN (:actors) AND NOT movie_id IN (:movie_ids)";
            namedParameter.addValue("actors",actors);
        }
        List<Integer> movie_ids2 = jdbcTemplate.queryForList(sql,namedParameter,Integer.class);
        if(movie_ids2.size()>=1) {
            sql = "SELECT m.* FROM movies m LEFT JOIN ratings r ON r.movie_id = m.id AND r.user_id = :user_id WHERE id IN (:movie_ids2) AND r.rating IS NULL";
            namedParameter.addValue("movie_ids2", movie_ids2);
            movies.addAll(jdbcTemplate.query(sql, namedParameter, new MovieMapper()));
        }
        if(movies.size()>6){
            return movies.subList(0,5);
        }else{
            return movies;
        }

    }

    /**
     *
     * @param user_id
     *
     * The SQL statement retrieves a list of movie_id integers that the user has rated 4 or higher.
     * A for statement finds similar movies for each movie_id identified above.
     * If at least one recommendation is found, then the movie object is mapped to the list of its recommendations.
     *
     * @return the map of liked movies to movie recommendations.
     */
    public Map<Movie, List<Movie>> getRecommendedMovies(int user_id){
        String sql = "SELECT movie_id FROM ratings WHERE user_id = :user_id AND rating >= 4";
        MapSqlParameterSource namedParameter = new MapSqlParameterSource();
        namedParameter.addValue("user_id",user_id);
        List<Integer> movie_ids = jdbcTemplate.queryForList(sql,namedParameter,Integer.class);
        Map<Movie, List<Movie>> movieRecs = new HashMap<>();
        for (int movie_id : movie_ids) {
            Movie m = getMovieFromDB(movie_id);
            List<Movie> recommendations = getSimilarMovies(movie_id, user_id);
            if(recommendations.size()>0){
                movieRecs.put(m,recommendations);
            }
        }
        return movieRecs;
    }

    /**
     *
     * @return the 6 most recently added movies to the database.
     */
    public List<Movie> getMostRecentlyAddedMovies() {
        String sql = "SELECT * FROM movies ORDER BY id DESC LIMIT 6";

        return jdbcTemplate.query(sql, new MovieMapper());
    }

}

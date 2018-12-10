package com3014.coursework.group6.service.impl;

import com3014.coursework.group6.model.Genre;
import com3014.coursework.group6.model.Movie;
import com3014.coursework.group6.model.person.Actor;
import com3014.coursework.group6.model.person.Director;
import com3014.coursework.group6.service.MovieService;
import com3014.coursework.group6.service.SearchService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SearchServiceImplTest {

    private SearchService searchService;

    private MovieService mockMovieService;

    private Movie movie1;

    private Movie movie2;

    private Movie movie3;

    private Movie movie4;

    private Movie movie5;

    private Movie movie6;

    @Before
    public void setUp() throws Exception {

        // set-up mock movie service
        mockMovieService = mock(MovieService.class);

        // inject mocks into class under test
        searchService = new SearchServiceImpl(mockMovieService);

        // create test objects
        List<Genre> genres1 = Arrays.asList(
                new Genre("Comedy"),
                new Genre("Sci-Fi"),
                new Genre("Thriller")
        );

        List<Genre> genres2 = Arrays.asList(
                new Genre("Comdey"),
                new Genre("Mystery"),
                new Genre("Sci-Fi")
        );

        Director director1 = new Director();
        director1.setFirstName("The");
        director1.setLastName("Director");

        Director director2 = new Director();
        director2.setFirstName("Other");
        director2.setLastName("Director");

        Actor actor1 = new Actor();
        actor1.setFirstName("First");
        actor1.setLastName("Actor");

        Actor actor2 = new Actor();
        actor2.setFirstName("Second");
        actor2.setLastName("Actor");

        Actor actor3 = new Actor();
        actor3.setFirstName("Third");
        actor3.setLastName("Actor");

        List<Actor> actors1 = Arrays.asList(actor1, actor2);
        List<Actor> actors2 = Arrays.asList(actor2, actor3);

        movie1 = new Movie();
        movie1.setId(1);
        movie1.setTitle("The Movie");
        movie1.setGenres(genres1);
        movie1.setDirector(director1);
        movie1.setActors(actors1);
        movie1.setYear(2016);

        movie2 = new Movie();
        movie2.setId(2);
        movie2.setTitle("The Movie 2");
        movie2.setGenres(genres2);
        movie2.setDirector(director1);
        movie2.setActors(actors1);
        movie2.setYear(2018);

        movie3 = new Movie();
        movie3.setId(3);
        movie3.setTitle("The Film");
        movie3.setGenres(genres1);
        movie3.setDirector(director2);
        movie3.setActors(actors2);
        movie3.setYear(2002);

        movie4 = new Movie();
        movie4.setId(4);
        movie4.setTitle("The Film 2");
        movie4.setGenres(genres2);
        movie4.setDirector(director2);
        movie4.setActors(actors2);
        movie4.setYear(2016);

        movie5 = new Movie();
        movie5.setId(5);
        movie5.setTitle("The Motion Picture");
        movie5.setGenres(genres1);
        movie5.setDirector(director1);
        movie5.setActors(actors1);
        movie5.setYear(1981);

        movie6 = new Movie();
        movie6.setId(6);
        movie6.setTitle("The Motion Picture 2");
        movie6.setGenres(genres2);
        movie6.setDirector(director1);
        movie6.setActors(actors1);
        movie6.setYear(1984);

        List<Movie> allMovies = Arrays.asList(movie1, movie2, movie3, movie4, movie5, movie6);
        when(mockMovieService.getAllMovies()).thenReturn(allMovies);
    }

    @Test
    public void test_search_by_title() {
        List<Movie> expectedForSearchThe = Arrays.asList(movie1, movie2, movie3, movie4, movie5, movie6);
        List<Movie> actualForSearchThe = searchService.getSearchResults("The");

        List<Movie> expectedForSearchTheFilm = Arrays.asList(movie3, movie4);
        List<Movie> actualForSearchTheFilm = searchService.getSearchResults("The Film");

        assertNotNull(actualForSearchThe);
        assertNotNull(actualForSearchTheFilm);
        assertEquals(expectedForSearchThe, actualForSearchThe);
        assertEquals(expectedForSearchTheFilm, actualForSearchTheFilm);
    }

    @Test
    public void test_search_by_genre() {
        List<Movie> expected = Arrays.asList(movie1, movie3, movie5);
        List<Movie> actual = searchService.getSearchResults("Thriller");

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void test_search_by_director() {
        List<Movie> expected = Arrays.asList(movie3, movie4);
        List<Movie> actual = searchService.getSearchResults("Other Director");

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void test_search_by_actor() {
        List<Movie> expected = Arrays.asList(movie1, movie2, movie5, movie6);
        List<Movie> actual = searchService.getSearchResults("First Actor");

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void test_search_by_year() {
        List<Movie> expected = Arrays.asList(movie1, movie4);
        List<Movie> actual = searchService.getSearchResults("2016");

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    public void test_search_box_only_returns_5_results() {
        // empty search of 6 movies should only return 5
        String results = searchService.getSearchboxResults("");
        JSONObject actual = new JSONObject(results);
        JSONArray response = (JSONArray) actual.get("response");

        assertEquals(5, response.length());
    }

    @Test
    public void test_search_box_results() {
        // all movies whose title start with "The Mo"
        // would be returned for type-ahead search in the UI on the client-side
        List<Movie> expectedMovies = Arrays.asList(movie1, movie2, movie5, movie6);

        JSONArray response = createJSONArrayFromMovieArray(expectedMovies);
        String expected = new JSONObject()
                .put("response", response)
                .toString();

        String actual = searchService.getSearchboxResults("The Mo");

        assertNotNull(actual);
        assertEquals(expected, actual);
    }


    private JSONArray createJSONArrayFromMovieArray(List<Movie> movies) {
        JSONArray result = new JSONArray();

        for(Movie movie : movies) {

            JSONObject obj = new JSONObject()
                    .put("id", movie.getId())
                    .put("title", movie.getTitle());

            result.put(obj);
        }

        return result;
    }
}
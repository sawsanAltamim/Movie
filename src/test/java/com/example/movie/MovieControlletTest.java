package com.example.movie;

import com.example.movie.Controller.MovieController;
import com.example.movie.Controller.TicketController;
import com.example.movie.Service.MovieService;
import com.example.movie.Table.Movie;
import com.example.movie.Table.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
@WebMvcTest(value = MovieController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
public class MovieControlletTest {

    @MockBean
    MovieService movieService;

    @Autowired
    MockMvc mockMvc;

    Movie movie1, movie2, movie3;

    User userAdmin;

    List<Movie> movies, movieList;

    @BeforeEach
    void setUp() {
        userAdmin = new User(1, "admin", "s123", "ADMIN", null);
        movie1 = new Movie(1, "Movie 1", "Details 1", "Director 1", "2h", "Action", 8.5, null);
        movie2 = new Movie(2, "Movie 2", "Details 2", "Director 2", "1h 30m", "Comedy", 7.0, null);

        movies = Arrays.asList(movie1);
    }

    @Test
    public void testAddMovie() throws Exception {
        Mockito.doNothing().when(movieService).addMovie(userAdmin.getId(), movie1);

        mockMvc.perform(post("/api/v1/movie/add")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(movie1)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateMovie() throws Exception {
        Mockito.doNothing().when(movieService).updateMovie(userAdmin.getId(), movie1.getId(), movie1);

        mockMvc.perform(put("/api/v1/movie/update/{movie_id}", movie1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(movie1)))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteMovie() throws Exception {
        Mockito.doNothing().when(movieService).deleteMovie(userAdmin.getId(), movie1.getId());
        mockMvc.perform(delete("/api/v1/movie/delete/{movie_id}", movie1.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
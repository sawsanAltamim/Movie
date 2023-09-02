package com.example.movie;

import com.example.movie.Repostory.MovieRepostory;
import com.example.movie.Repostory.UserRepostory;
import com.example.movie.Service.MovieService;
import com.example.movie.Table.Movie;
import com.example.movie.Table.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
public class MovieServiceTest {

    @InjectMocks
    MovieService movieService;

    @Mock
    MovieRepostory movieRepository;

    @Mock
    UserRepostory userRepostory;

    Movie movie1;
    Movie movie2;
    List<Movie> movies;

    User user;

    User adminUser;

    @BeforeEach
    void setUp() {
        movie1 = new Movie(1, "Movie 1", "Details 1", "Director 1", "2h", "Action", 8.5, null);
        movie2 = new Movie(2, "Movie 2", "Details 2", "Director 2", "1h 30m", "Comedy", 7.0, null);

        movies = new ArrayList<>();
        movies.add(movie1);
        movies.add(movie2);

        adminUser = new User(1, "admin", "s123", "ADMIN", null);

    }

    @Test
    public void testGetAllMovies() {
        when(movieRepository.findAll()).thenReturn(movies);

        List<Movie> movieList = movieService.getAllMovies(1);

        Assertions.assertEquals(movieList, movies);

        verify(movieRepository, times(1)).findAll();
    }

    @Test
    public void GetMoviesByDirectorTest(){
        when(userRepostory.findUserById(adminUser.getId())).thenReturn(adminUser);
        when(movieRepository.findByDirector("Director 1")).thenReturn(List.of(movie1));

        List<Movie> result = movieService.getMoviesByDirector(adminUser.getId(), "Director 1");

        Assertions.assertEquals(result, List.of(movie1));

        verify(userRepostory, times(1)).findUserById(adminUser.getId());
        verify(movieRepository, times(1)).findByDirector("Director 1");
    }

   /* @Test
    public void addMovieTest(){
        when(userRepostory.findUserById(user.getId())).thenReturn(user);

        movieService.addMovie(1,movie1);

        verify(userRepostory,times(1)).findUserById(user.getId());
        verify(movieService,times(1)).save(movie1);
    }*/
}

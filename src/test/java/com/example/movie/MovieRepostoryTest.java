package com.example.movie;

import com.example.movie.Repostory.MovieRepostory;
import com.example.movie.Table.Movie;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MovieRepostoryTest {

    @Autowired
    MovieRepostory movieRepostory;

    Movie movie1;
    Movie movie2;

    @BeforeEach
    void setUp() {
        movie1 = new Movie(1, "Movie 1", "Details 1", "Director 1", "2h", "Action", 8.5, null);
        movie2 = new Movie(2, "Movie 2", "Details 2", "Director 2", "1h 30m", "Comedy", 7.0, null);
        movieRepostory.saveAll(List.of(movie1, movie2));
    }

    @Test
    public void findByDirectorTest() {
        List<Movie> movies = movieRepostory.findByDirector("Director 1");
        Assertions.assertThat(movies).containsExactly(movie1);
    }

    @Test
    public void findMovieByIdTest() {
        Movie movie = movieRepostory.findMovieById(movie1.getId());
        Assertions.assertThat(movie).isEqualTo(movie1);
    }
}

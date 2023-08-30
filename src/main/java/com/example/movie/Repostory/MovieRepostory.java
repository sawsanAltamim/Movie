package com.example.movie.Repostory;

import com.example.movie.Table.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepostory extends JpaRepository<Movie, Integer> {
    Movie findMovieById(Integer id);
}

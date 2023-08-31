package com.example.movie.Controller;

import com.example.movie.Api.ApiResponse;
import com.example.movie.Service.MovieService;
import com.example.movie.Table.Movie;
import com.example.movie.Table.User;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/movie")
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/get")
    public ResponseEntity getAllMovies(@AuthenticationPrincipal User user){
        return ResponseEntity.status(HttpStatus.OK).body(movieService.getAllMovies(user.getId()));
    }

    @PostMapping("/add")
    public ResponseEntity addMovie(@AuthenticationPrincipal User user, @RequestBody Movie movie){
        movieService.addMovie(user.getId(), movie);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Movie added"));
    }

    @PutMapping("/update/{movie_id}")
    public ResponseEntity updateMovie(@AuthenticationPrincipal User user, @PathVariable Integer movie_id, @RequestBody Movie movie){
        movieService.updateMovie(user.getId(), movie_id, movie);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Movie update"));
    }

    @DeleteMapping("/delete/{movie_id}")
    public ResponseEntity deleteMovie(@AuthenticationPrincipal User user, @PathVariable Integer movie_id){
        movieService.deleteMovie(user.getId(),movie_id);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("Movie delete"));
    }

    @GetMapping("/get-by-director/{directorName}")
    public ResponseEntity getMoviesByDirector(@AuthenticationPrincipal User user, @PathVariable String directorName) {
        List<Movie> movies = movieService.getMoviesByDirector(user.getId(), directorName);
        return ResponseEntity.status(HttpStatus.OK).body(movies);
    }
}

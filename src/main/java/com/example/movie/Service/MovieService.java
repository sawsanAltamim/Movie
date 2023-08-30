package com.example.movie.Service;

import com.example.movie.Api.ApiException;
import com.example.movie.Repostory.MovieRepostory;
import com.example.movie.Repostory.UserRepostory;
import com.example.movie.Table.Movie;
import com.example.movie.Table.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepostory movieRepository;
    private final UserRepostory userRepostory;

    public List<Movie> getAllMovies(Integer admin_id) {
        return movieRepository.findAll();
    }

    public void addMovie(Integer admin_id,Movie movie){
        User user = userRepostory.findUserById(admin_id);
        if(user == null){
            throw new ApiException("Admin not found");
        }
        if (!user.getRole().equals("ADMIN")) {
            throw new ApiException("iMovie cannot be added");
        }
        movieRepository.save(movie);
    }

    public void updateMovie(Integer admin_id,Integer movie_id, Movie movie) {
        Movie movie1 = movieRepository.findMovieById(movie_id);
        if (movie1 == null) {
            throw new ApiException("Movie not found");
        }
        User user = userRepostory.findUserById(admin_id);
        if (user == null) {
            throw new ApiException("Admin not found");
        }
        if (!user.getRole().equals("ADMIN")) {
            throw new ApiException("Movie cannot be update");
        }

        movie1.setMovieName(movie.getMovieName());
        movie1.setMovieDetails(movie.getMovieDetails());
        movie1.setMovieHours(movie.getMovieHours());
        movie1.setMovieEvaluation(movie.getMovieEvaluation());
        movie1.setDirector(movie.getDirector());
        movie1.setMovieType(movie.getMovieType());
        movieRepository.save(movie1);
    }

    public void deleteMovie(Integer admin_id,Integer movie_id){
        Movie movie = movieRepository.findMovieById(movie_id);
        if (movie == null) {
            throw new ApiException("Movie not found");
        }
        User user = userRepostory.findUserById(admin_id);
        if (user == null) {
            throw new ApiException("Admin not found");
        }
        if (!user.getRole().equals("ADMIN")) {
            throw new ApiException("Movie cannot be delete");
        }
        movieRepository.delete(movie);
    }
}
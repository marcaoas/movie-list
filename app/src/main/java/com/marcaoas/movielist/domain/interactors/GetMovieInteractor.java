package com.marcaoas.movielist.domain.interactors;

import com.marcaoas.movielist.domain.models.Movie;
import com.marcaoas.movielist.domain.repositories.MoviesRepository;

import io.reactivex.Single;

/**
 * Created by marco on 02/03/17.
 */

public class GetMovieInteractor {

    private final MoviesRepository moviesRepository;
    private final String movieId;

    public GetMovieInteractor(MoviesRepository repository, String movieId) {
        this.moviesRepository = repository;
        this.movieId = movieId;
    }

    public Single<Movie> getMovie() {
        return moviesRepository.getMovie(movieId);
    }
}

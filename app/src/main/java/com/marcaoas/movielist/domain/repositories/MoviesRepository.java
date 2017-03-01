package com.marcaoas.movielist.domain.repositories;

import com.marcaoas.movielist.domain.models.Movie;
import com.marcaoas.movielist.domain.models.MovieList;

import io.reactivex.Single;

/**
 * Created by marco on 28/02/17.
 */

public interface MoviesRepository {

    Single<MovieList> getMovieList(int page);
    Single<Movie> getMovieDetails(String movieId);


}

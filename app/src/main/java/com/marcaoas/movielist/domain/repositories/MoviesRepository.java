package com.marcaoas.movielist.domain.repositories;

import com.marcaoas.movielist.domain.models.Movie;
import com.marcaoas.movielist.domain.models.MovieList;

import java.util.Date;

import io.reactivex.Single;

/**
 * Created by marco on 28/02/17.
 */

public interface MoviesRepository {

    Single<MovieList> getMovieListWithReleaseDateLTESortedByReleaseDate(int page, Date date);
    Single<Movie> getMovie(String movieId);


}

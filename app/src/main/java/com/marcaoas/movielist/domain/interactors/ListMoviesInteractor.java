package com.marcaoas.movielist.domain.interactors;

import com.marcaoas.movielist.domain.models.MovieList;
import com.marcaoas.movielist.domain.repositories.MoviesRepository;

import java.util.Date;

import io.reactivex.Single;

/**
 * Created by marco on 01/03/17.
 */

public class ListMoviesInteractor {

    private final MoviesRepository moviesRepository;

    public ListMoviesInteractor(MoviesRepository moviesRepository) {
        this.moviesRepository = moviesRepository;
    }

    public Single<MovieList> listAllMovies(int page) {
        if(page < 1){
            page = 1;
        }
        return moviesRepository.getMovieListWithReleaseDateLTESortedByReleaseDate(page, new Date());
    }

}

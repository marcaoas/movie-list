package com.marcaoas.movielist.data.repositories;

import com.marcaoas.movielist.data.store.TMDBApiClient;
import com.marcaoas.movielist.data.store.TMDBApiServices;
import com.marcaoas.movielist.domain.models.Movie;
import com.marcaoas.movielist.domain.models.MovieList;
import com.marcaoas.movielist.domain.repositories.MovieRepository;

import io.reactivex.Single;

/**
 * Created by marco on 28/02/17.
 */

public class TMDBMovieRepository implements MovieRepository {

    private final TMDBApiServices api;

    public TMDBMovieRepository(TMDBApiServices api) {
        this.api = api;
    }

    @Override
    public Single<MovieList> getMovieList() {
        api.getMovieList(TMDBApiClient.getApiKey());
        return null;
    }

    @Override
    public Single<Movie> getMovieDetails(String movieId) {
        return null;
    }
}

package com.marcaoas.movielist.data.repositories;

import com.marcaoas.movielist.data.mappers.MovieListMapper;
import com.marcaoas.movielist.data.mappers.MovieMapper;
import com.marcaoas.movielist.data.store.TMDBApiClient;
import com.marcaoas.movielist.domain.models.Movie;
import com.marcaoas.movielist.domain.models.MovieList;
import com.marcaoas.movielist.domain.repositories.MoviesRepository;

import io.reactivex.Single;

/**
 * Created by marco on 28/02/17.
 */

public class TMDBMovieRepository implements MoviesRepository {

    private final TMDBApiClient api;
    private final MovieListMapper movieListMapper;
    private final MovieMapper movieMapper;

    public TMDBMovieRepository(TMDBApiClient api, MovieListMapper movieListMapper, MovieMapper movieMapper) {
        this.api = api;
        this.movieListMapper = movieListMapper;
        this.movieMapper = movieMapper;
    }

    @Override
    public Single<MovieList> getMovieList(int page) {
        return api.getMovieList(page)
                .map(movieListMapper::map);
    }

    @Override
    public Single<Movie> getMovie(String movieId) {
        return api.getMovie(movieId)
                .map(movieMapper::map);
    }
}

package com.marcaoas.movielist.data.mappers;

import com.marcaoas.movielist.data.entities.tmdb.TMDBMovieListEntity;
import com.marcaoas.movielist.domain.models.MovieList;

/**
 * Created by marco on 01/03/17.
 */

public class MovieListMapper {

    private final MovieMapper movieMapper;

    public MovieListMapper(MovieMapper movieMapper){
        this.movieMapper = movieMapper;
    }

    public MovieList map(TMDBMovieListEntity movieListEntity ) {
        if(movieListEntity == null){
            return null;
        }
        MovieList movieList = new MovieList();
        movieList.setCurrentPage(movieListEntity.getCurrentPage());
        movieList.setTotalPages(movieListEntity.getTotalPage());
        movieList.setTotalResults(movieListEntity.getTotalResults());
        movieList.setMovies(movieMapper.map(movieListEntity.getMovies()));
        return movieList;
    }
}

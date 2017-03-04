package com.marcaoas.movielist.data.mappers;

import com.marcaoas.movielist.data.entities.tmdb.TMDBMovieEntity;
import com.marcaoas.movielist.domain.models.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 01/03/17.
 */

public class MovieMapper {

    private final String imageEndpointTMDB;
    private final GenreMapper genreMapper;

    public MovieMapper(String imageEndpointTMDB, GenreMapper genreMapper) {
        this.imageEndpointTMDB = imageEndpointTMDB;
        this.genreMapper = genreMapper;
    }

    public Movie map(TMDBMovieEntity movieEntity) {
        if(movieEntity == null){
            return null;
        }
        Movie movie = new Movie();
        movie.setBackdropUrl(getImageUrl(movieEntity.getBackdropPath()));
        movie.setPosterUrl(getImageUrl(movieEntity.getPosterPath()));
        movie.setId(movieEntity.getId());
        movie.setOriginalTitle(movieEntity.getOriginalTitle());
        movie.setTitle(movieEntity.getTitle());
        movie.setOverview(movieEntity.getOverview());
        movie.setRelaseDate(movieEntity.getReleaseDate());
        movie.setStatus(movieEntity.getStatus());
        movie.setVoteAverage(movieEntity.getVoteAverage());
        movie.setVoteCount(movieEntity.getVoteCount());
        movie.setPopularity(movieEntity.getPopularity());
        movie.setOriginalLanguage(movieEntity.getOriginalLanguage());
        movie.setRuntime(movieEntity.getRuntime());
        movie.setGenreList(genreMapper.map(movieEntity.getGenreList()));
        return movie;
    }

    public List<Movie> map(List<TMDBMovieEntity> movieEntityList) {
        if(movieEntityList == null) {
            return null;
        }
        ArrayList<Movie> movieList = new ArrayList<>();
        for(TMDBMovieEntity movieEntity : movieEntityList) {
            movieList.add(map(movieEntity));
        }
        return movieList;
    }

    public String getImageUrl(String imagePath) {
        if(imagePath == null){
            return null;
        }
        return String.format("%s%s", imageEndpointTMDB, imagePath);
    }

}

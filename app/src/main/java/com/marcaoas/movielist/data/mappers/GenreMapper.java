package com.marcaoas.movielist.data.mappers;

import com.marcaoas.movielist.data.entities.tmdb.TMDBGenreEntity;
import com.marcaoas.movielist.domain.models.Genre;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marco on 04/03/17.
 */

public class GenreMapper {

    public Genre map(TMDBGenreEntity genreEntity) {
        if(genreEntity == null){
            return null;
        }
        Genre genre = new Genre();
        genre.setId(genreEntity.getId());
        genre.setName(genreEntity.getName());
        return genre;
    }

    public List<Genre> map(List<TMDBGenreEntity> genreEntityList) {
        if(genreEntityList == null){
            return null;
        }
        List<Genre> genreList = new ArrayList<>();
        for(TMDBGenreEntity genreEntity : genreEntityList){
            genreList.add(map(genreEntity));
        }
        return genreList;
    }

}

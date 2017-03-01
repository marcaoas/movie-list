package com.marcaoas.movielist.data.store;

import com.marcaoas.movielist.data.entities.tmdb.TMDBMovieListEntity;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by marco on 28/02/17.
 */

public interface TMDBApiServices {

    @GET("/discover/movie")
    Single<Response<TMDBMovieListEntity>> getMovieList(@Query("api_key") String apiKey);

}

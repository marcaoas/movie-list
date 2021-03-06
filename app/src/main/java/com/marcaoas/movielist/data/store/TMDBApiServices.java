package com.marcaoas.movielist.data.store;

import com.marcaoas.movielist.data.entities.tmdb.TMDBMovieEntity;
import com.marcaoas.movielist.data.entities.tmdb.TMDBMovieListEntity;

import java.util.Date;

import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by marco on 28/02/17.
 */

public interface TMDBApiServices {

    @GET("discover/movie")
    Single<Response<TMDBMovieListEntity>> getMovieList(@Query("api_key") String apiKey, @Query("page") int page, @Query("sort_by") String sortBy, @Query("primary_release_date.lte") String releaseDateLessThan);

    @GET("movie/{movie_id}")
    Single<Response<TMDBMovieEntity>> getMovie(@Path("movie_id") String movieId, @Query("api_key") String apiKey);
}

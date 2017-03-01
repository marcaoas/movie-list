package com.marcaoas.movielist.presentation.di;

import android.content.Context;

import com.marcaoas.movielist.data.mappers.MovieListMapper;
import com.marcaoas.movielist.data.mappers.MovieMapper;
import com.marcaoas.movielist.data.repositories.TMDBMovieRepository;
import com.marcaoas.movielist.data.store.TMDBApiClient;
import com.marcaoas.movielist.domain.repositories.MoviesRepository;
import com.marcaoas.movielist.presentation.MovieListApplication;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by marco on 28/02/17.
 */

@Module
public class ApplicationModule {

    private final MovieListApplication application;

    public ApplicationModule(MovieListApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Context providesApplicationContext() {
        return application;
    }

    @Provides
    @Singleton
    @Named("apiKey")
    String provideApiKey() {
        return TMDBApiClient.API_KEY;
    }

    @Provides
    @Singleton
    @Named("apiEndpoint")
    String provideApiEndpoint() {
        return TMDBApiClient.API_ENDPOINT;
    }

    @Provides
    @Singleton
    @Named("imageEndpoint")
    String provideImageEndpoint() {
        return TMDBApiClient.IMAGE_ENDPOINT;
    }


    @Provides
    @Singleton
    TMDBApiClient provideApiServices(@Named("apiEndpoint") String apiEndpoint,
                                     @Named("apiKey") String apiKey) {
        return new TMDBApiClient(apiKey, apiEndpoint);
    }

    @Provides
    @Singleton
    MovieMapper provideMovieMapper(@Named("imageEndpoint") String imageEndpoint) {
        return new MovieMapper(imageEndpoint);
    }

    @Provides
    @Singleton
    MovieListMapper provideMovieListMapper(MovieMapper movieMapper) {
        return new MovieListMapper(movieMapper);
    }

    @Provides
    @Singleton
    MoviesRepository provideMoviesRepository(TMDBApiClient apiClient,
                                             MovieListMapper movieListMapper,
                                             MovieMapper movieMapper) {
        return new TMDBMovieRepository(apiClient, movieListMapper, movieMapper);
    }

}

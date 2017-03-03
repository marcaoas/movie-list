package com.marcaoas.movielist.presentation.details.di;

import com.marcaoas.movielist.domain.interactors.GetMovieInteractor;
import com.marcaoas.movielist.domain.repositories.MoviesRepository;
import com.marcaoas.movielist.presentation.details.MovieDetailsContract;
import com.marcaoas.movielist.presentation.details.MovieDetailsPresenter;
import com.marcaoas.movielist.presentation.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by marco on 02/03/17.
 */
@Module
public class MovieDetailsModule {

    private final String movieId;

    public MovieDetailsModule(String movieId) {
        this.movieId = movieId;
    }

    @PerActivity
    @Provides
    GetMovieInteractor provideInteractor(MoviesRepository moviesRepository) {
        return new GetMovieInteractor(moviesRepository, movieId);
    }

    @PerActivity
    @Provides
    MovieDetailsContract.Presenter providePresenter(GetMovieInteractor getMovieInteractor) {
        return new MovieDetailsPresenter(getMovieInteractor);
    }

}

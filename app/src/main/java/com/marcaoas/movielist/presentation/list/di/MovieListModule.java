package com.marcaoas.movielist.presentation.list.di;

import com.marcaoas.movielist.presentation.di.PerActivity;
import com.marcaoas.movielist.presentation.list.MovieListContract;
import com.marcaoas.movielist.presentation.list.MovieListPresenter;
import com.marcaoas.movielist.presentation.list.MoviesAdapter;

import dagger.Module;
import dagger.Provides;

/**
 * Created by marco on 28/02/17.
 */
@Module
public class MovieListModule {

    @PerActivity
    @Provides
    MoviesAdapter provideMoviesAdapter() {
        return new MoviesAdapter();
    }

    @PerActivity
    @Provides
    MovieListContract.Presenter presenterProvider() {
        return new MovieListPresenter();
    }
}

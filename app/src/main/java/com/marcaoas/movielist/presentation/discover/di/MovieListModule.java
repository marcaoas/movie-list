package com.marcaoas.movielist.presentation.discover.di;

import com.marcaoas.movielist.domain.interactors.ListMoviesInteractor;
import com.marcaoas.movielist.domain.repositories.MoviesRepository;
import com.marcaoas.movielist.presentation.di.PerActivity;
import com.marcaoas.movielist.presentation.discover.MovieListContract;
import com.marcaoas.movielist.presentation.discover.MovieListPresenter;
import com.marcaoas.movielist.presentation.discover.MoviesAdapter;

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
    ListMoviesInteractor provideInteractor(MoviesRepository moviesRepository) {
        return new ListMoviesInteractor(moviesRepository);
    }

    @PerActivity
    @Provides
    MovieListContract.Presenter presenterProvider(ListMoviesInteractor interactor) {
        return new MovieListPresenter(interactor);
    }
}

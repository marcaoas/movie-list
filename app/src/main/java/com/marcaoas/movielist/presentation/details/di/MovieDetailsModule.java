package com.marcaoas.movielist.presentation.details.di;

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

    @PerActivity
    @Provides
    MovieDetailsContract.Presenter providePresenter() {
        return new MovieDetailsPresenter();
    }

}

package com.marcaoas.movielist.presentation.di;

import android.content.Context;

import com.marcaoas.movielist.presentation.MovieListApplication;

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

}

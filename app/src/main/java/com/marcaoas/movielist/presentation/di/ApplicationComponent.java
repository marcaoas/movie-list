package com.marcaoas.movielist.presentation.di;

import com.marcaoas.movielist.presentation.MovieListApplication;
import com.marcaoas.movielist.presentation.details.di.MovieDetailsComponent;
import com.marcaoas.movielist.presentation.details.di.MovieDetailsModule;
import com.marcaoas.movielist.presentation.discover.di.MovieListComponent;
import com.marcaoas.movielist.presentation.discover.di.MovieListModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by marco on 28/02/17.
 */
@Component(modules = { ApplicationModule.class })
@Singleton
public interface ApplicationComponent {

    void inject(MovieListApplication app);

    MovieListComponent plus( MovieListModule movieListModule );
    MovieDetailsComponent plus(MovieDetailsModule movieDetailsModule );


}

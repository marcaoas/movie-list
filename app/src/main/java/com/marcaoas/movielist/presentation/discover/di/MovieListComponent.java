package com.marcaoas.movielist.presentation.discover.di;

import com.marcaoas.movielist.presentation.di.PerActivity;
import com.marcaoas.movielist.presentation.discover.MovieListActivity;

import dagger.Subcomponent;

/**
 * Created by marco on 28/02/17.
 */
@PerActivity
@Subcomponent(modules = { MovieListModule.class })
public interface MovieListComponent {
    void inject(MovieListActivity activity);
}

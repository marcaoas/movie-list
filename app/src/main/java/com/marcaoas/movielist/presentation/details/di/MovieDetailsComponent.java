package com.marcaoas.movielist.presentation.details.di;

import com.marcaoas.movielist.presentation.details.MovieDetailsActivity;
import com.marcaoas.movielist.presentation.di.PerActivity;

import dagger.Subcomponent;

/**
 * Created by marco on 02/03/17.
 */
@PerActivity
@Subcomponent(modules = { MovieDetailsModule.class })
public interface MovieDetailsComponent {

    void inject(MovieDetailsActivity movieDetailsActivity);

}

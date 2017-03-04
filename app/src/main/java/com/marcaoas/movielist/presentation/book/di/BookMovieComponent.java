package com.marcaoas.movielist.presentation.book.di;

import com.marcaoas.movielist.presentation.book.BookMovieActivity;
import com.marcaoas.movielist.presentation.di.PerActivity;

import dagger.Subcomponent;

/**
 * Created by marco on 04/03/17.
 */
@PerActivity
@Subcomponent(modules = { BookMovieModule.class })
public interface BookMovieComponent {

    void inject(BookMovieActivity activity);

}

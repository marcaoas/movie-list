package com.marcaoas.movielist.presentation.book.di;

import com.marcaoas.movielist.presentation.book.BookMovieContract;
import com.marcaoas.movielist.presentation.book.BookMoviePresenter;
import com.marcaoas.movielist.presentation.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by marco on 04/03/17.
 */
@Module
public class BookMovieModule {

    @PerActivity
    @Provides
    BookMovieContract.Presenter providePresenter(@Named("movieBookingUrl") String bookMovieUrl) {
        return new BookMoviePresenter(bookMovieUrl);
    }

}

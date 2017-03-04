package com.marcaoas.movielist.presentation;

import android.content.Context;

import com.marcaoas.movielist.presentation.book.BookMovieActivity;
import com.marcaoas.movielist.presentation.details.MovieDetailsActivity;
/**
 * Created by marco on 01/03/17.
 */

public class Navigator {

    public void navigateToMovieDetails(Context context, String movieId) {
        context.startActivity(MovieDetailsActivity.getCallingIntent(context, movieId));
    }

    public void navigateToBookMovie(Context context) {
        context.startActivity(BookMovieActivity.getCallingIntent(context));
    }
}

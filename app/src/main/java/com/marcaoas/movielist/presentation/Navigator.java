package com.marcaoas.movielist.presentation;

import android.content.Context;

import com.marcaoas.movielist.presentation.details.MovieDetailsActivity;
/**
 * Created by marco on 01/03/17.
 */

public class Navigator {

    public void navigateToMovieDetailsActivity(Context context, String movieId) {
        context.startActivity(MovieDetailsActivity.getCallingIntent(context, movieId));
    }
}

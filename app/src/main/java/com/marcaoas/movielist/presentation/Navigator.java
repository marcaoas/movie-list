package com.marcaoas.movielist.presentation;

import android.content.Context;

import com.marcaoas.movielist.presentation.utils.Logger;

/**
 * Created by marco on 01/03/17.
 */

public class Navigator {
    private final Context context;

    public Navigator(Context context) {
        this.context = context;
    }

    public void navigateToMovieDetailsActivity(String movieId) {
        //TODO
        Logger.d("navigating to movie screen : "+ movieId);
    }
}

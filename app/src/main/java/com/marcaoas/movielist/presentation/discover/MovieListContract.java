package com.marcaoas.movielist.presentation.discover;

import com.marcaoas.movielist.domain.models.Movie;

import java.util.List;

/**
 * Created by marco on 28/02/17.
 */

public interface MovieListContract {

    interface Presenter {

        void bindView(View view);
        void unbindView();
        void startingScreen();

    }

    interface View {

        void hideLoading();
        void showLoading();
        void showInternetError();
        void showDefaultError();
        void showList();
        void addMovies(List<Movie> movies);

    }

}

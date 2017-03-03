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
        void movieClicked(Movie movie);
        void onRefreshMovies();
        void onScrollBottom();
        void onTryAgainClicked();
    }

    interface View {

        void hideLoading();
        void showLoading();
        void showInternetError();
        void showDefaultError();
        void showList();
        void hideList();
        void clearMovies();
        void addMovies(List<Movie> movies);
        void goToMovieDetails(String movieId);

    }

}

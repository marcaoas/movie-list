package com.marcaoas.movielist.presentation.details;

import com.marcaoas.movielist.domain.models.Genre;

import java.util.List;

/**
 * Created by marco on 02/03/17.
 */

public interface MovieDetailsContract {

    interface View {
        void showLoading();
        void hideLoading();
        void showInternetError();
        void showDefaultError();
        void setBackdropVisible(boolean visible);
        void goToMovieBookingScreen();

        void setBackdropImageUrl(String backdropUrl);
        void setMovieTitle(String title);
        void setMovieOverview(String overview);
        void setMovieGenres(List<Genre> genres);
        void setPostImageUrl(String posterUrl);
        void setRuntimeAndLanguage(int hours, int minutes, String language);
        void finishScreen();
    }

    interface Presenter {
        void bindView(MovieDetailsContract.View view);
        void unbindView();
        void startScreen();
        void bookMovieClicked();
        void onBackPressed();
        void retryLoadMovie();
    }

}

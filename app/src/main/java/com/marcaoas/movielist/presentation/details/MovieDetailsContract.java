package com.marcaoas.movielist.presentation.details;

/**
 * Created by marco on 02/03/17.
 */

public interface MovieDetailsContract {

    interface View {
        void showLoading();
        void hideoading();
        void showInternetError();
        void showDefaultError();
        void setBackdropVisible(boolean visible);
        void goToMovieBookingScreen();
    }

    interface Presenter {
        void bindView();
        void unbindView();
        void startScreen();
        void bookMovieClicked();
    }

}

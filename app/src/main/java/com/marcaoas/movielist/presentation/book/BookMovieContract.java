package com.marcaoas.movielist.presentation.book;

/**
 * Created by marco on 04/03/17.
 */

public interface BookMovieContract {

    interface Presenter {
        void bindView(View view);
        void unbindView();
        void startScreen();
        void onBackPressed();
        void onRetryPressed();
        void onWebViewLoaded();
        void onErrorReceived();
    }

    interface View {
        void showLoading();
        void showDefaultError();
        void loadUrl(String url);
        void finishScreen();
        void showWebViewContent();
    }

}

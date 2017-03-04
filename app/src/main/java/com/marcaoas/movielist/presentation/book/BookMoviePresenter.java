package com.marcaoas.movielist.presentation.book;

/**
 * Created by marco on 04/03/17.
 */

public class BookMoviePresenter implements BookMovieContract.Presenter {

    private BookMovieContract.View view;
    private final String movieBookUrl;
    private boolean loadedWithError;

    public BookMoviePresenter(String movieBookUrl) {
        this.movieBookUrl = movieBookUrl;
    }

    @Override
    public void bindView(BookMovieContract.View view) {
        this.view = view;
    }

    @Override
    public void unbindView() {
        view = null;
    }

    @Override
    public void startScreen() {
        loadedWithError = false;
        view.showLoading();
        view.loadUrl(movieBookUrl);
    }

    @Override
    public void onBackPressed() {
        view.finishScreen();
    }

    @Override
    public void onRetryPressed() {
        startScreen();
    }

    @Override
    public void onWebViewLoaded() {
        if(!loadedWithError){
            view.showWebViewContent();
        }
    }

    @Override
    public void onErrorReceived() {
        view.showDefaultError();
        loadedWithError = true;
    }
}

package com.marcaoas.movielist.presentation.book;

import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by marco on 04/03/17.
 */

public class BookMoviePresenterTest {
    private static final String BOOK_MOVIE_URL = "http://someurl.com.br";

    private BookMovieContract.View view;
    private BookMoviePresenter presenter;

    @Before
    public void createAndBindPresenterToView() {
        view = mock(BookMovieContract.View.class);
        presenter = new BookMoviePresenter(BOOK_MOVIE_URL);
        presenter.bindView(view);
    }

    @Test
    public void onBackPressed_finshesView() {
        presenter.onBackPressed();
        verify(view, times(1)).finishScreen();
    }

    @Test
    public void startScreen_showsLoadingScreen() {
        presenter.startScreen();
        verify(view, times(1)).showLoading();
    }

    @Test
    public void startScreen_sendsUrlToLoad() {
        presenter.startScreen();
        verify(view, times(1)).loadUrl(eq(BOOK_MOVIE_URL));
    }

    @Test
    public void onWebViewLoaded_showsTheContent(){
        presenter.onWebViewLoaded();
        verify(view, times(1)).showWebViewContent();
    }

    @Test
    public void onErrorReceived_showsError(){
        presenter.onErrorReceived();
        verify(view, times(1)).showDefaultError();
    }

    @Test
    public void onWebViewLoaded_afterAnError_doesntShowTheContent(){
        presenter.onErrorReceived();
        presenter.onWebViewLoaded();
        verify(view, times(0)).showWebViewContent();
    }

    @Test
    public void onRetryPressed_showLoadingScreen() {
        presenter.onRetryPressed();
        verify(view, times(1)).showLoading();
    }

    @Test
    public void onRetryPressed_sendsUrlToLoad() {
        presenter.onRetryPressed();
        verify(view, times(1)).loadUrl(eq(BOOK_MOVIE_URL));
    }

    @Test
    public void onRetryPressed_afterFirstTryEndedInError_sendsUrlToLoadForTheSecondTime() {
        presenter.startScreen();
        presenter.onErrorReceived();
        presenter.onRetryPressed();
        verify(view, times(2)).loadUrl(eq(BOOK_MOVIE_URL));
    }

    @Test
    public void onRetryPressed_afterFirstTryEndedInError_showLoadingForTheSecondTime() {
        presenter.startScreen();
        presenter.onErrorReceived();
        presenter.onRetryPressed();
        verify(view, times(2)).showLoading();
    }

    @Test
    public void onWebViewLoaded_afterFirstTryEndedInErrorThenGotSuccess_showsContent() {
        presenter.startScreen();
        presenter.onErrorReceived();
        presenter.onWebViewLoaded();
        presenter.onRetryPressed();
        presenter.onWebViewLoaded();
        verify(view, times(1)).showWebViewContent();
    }

    @Test
    public void onWebViewLoaded_afterFirstTryEndedInErrorThenGotError_doesntShowsContent() {
        presenter.startScreen();
        presenter.onErrorReceived();
        presenter.onWebViewLoaded();
        presenter.onRetryPressed();
        presenter.onErrorReceived();
        presenter.onWebViewLoaded();
        verify(view, times(0)).showWebViewContent();
    }

    @Test
    public void onWebViewLoaded_afterFirstTryEndedInErrorThenGotError_showsError() {
        presenter.startScreen();
        presenter.onErrorReceived();
        presenter.onWebViewLoaded();
        presenter.onRetryPressed();
        presenter.onErrorReceived();
        verify(view, times(2)).showDefaultError();
    }

}

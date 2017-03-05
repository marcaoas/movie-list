package com.marcaoas.movielist.presentation.details;

import com.marcaoas.movielist.domain.interactors.GetMovieInteractor;
import com.marcaoas.movielist.domain.models.Movie;

import org.junit.Before;
import org.junit.Test;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by marco on 04/03/17.
 */

public class MovieDetailsPresenterTest {

    private MovieDetailsContract.View view;
    private GetMovieInteractor interactor;
    private MovieDetailsPresenter presenter;

    @Before
    public void createAndBindPresenterToView(){
        interactor = mock(GetMovieInteractor.class);
        view = mock(MovieDetailsContract.View.class);
        presenter = new MovieDetailsPresenter(interactor);
        presenter.bindView(view);
    }

    @Test
    public void startScreen_callsMovieDetails() {
        presenter.startScreen();
        verify(interactor, times(0)).getMovie();
    }

}

package com.marcaoas.movielist.presentation.discover;

import com.marcaoas.movielist.domain.interactors.ListMoviesInteractor;
import com.marcaoas.movielist.domain.models.Movie;
import com.marcaoas.movielist.presentation.utils.Logger;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by marco on 28/02/17.
 */

public class MovieListPresenter implements MovieListContract.Presenter {

    private final ListMoviesInteractor interactor;
    private MovieListContract.View view;

    public MovieListPresenter(ListMoviesInteractor interactor) {
        this.interactor = interactor;
    }

    @Override
    public void bindView(MovieListContract.View view) {
        this.view = view;
        Logger.d("BIND VIEW");

    }

    @Override
    public void unbindView() {
        this.view = null;
        Logger.d("UNBIND VIEW");
    }

    @Override
    public void startingScreen() {
        view.showLoading();
        interactor.listAllMovies(1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieList -> {
                    view.hideLoading();
                    view.showList();
                    view.addMovies(movieList.getMovies());
                }, throwable -> {
                    Logger.d("ERROR");
                    view.hideLoading();
                    throwable.printStackTrace();
                });
    }

    @Override
    public void movieClicked(Movie movie) {
        view.goToMovieDetails(movie.getId());
    }
}

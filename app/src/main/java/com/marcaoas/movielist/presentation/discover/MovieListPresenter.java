package com.marcaoas.movielist.presentation.discover;

import com.marcaoas.movielist.domain.interactors.ListMoviesInteractor;
import com.marcaoas.movielist.domain.models.Movie;
import com.marcaoas.movielist.presentation.utils.Logger;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by marco on 28/02/17.
 */

public class MovieListPresenter implements MovieListContract.Presenter {
    private static final int FIRST_PAGE = 1;

    private final ListMoviesInteractor interactor;
    private MovieListContract.View view;
    private CompositeDisposable disposableBag = new CompositeDisposable();
    private int currentPage = FIRST_PAGE;

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
        if(!disposableBag.isDisposed()) {
            disposableBag.dispose();
        }
        this.view = null;
        Logger.d("UNBIND VIEW");
    }

    @Override
    public void startingScreen() {
        loadMovies();
    }

    @Override
    public void movieClicked(Movie movie) {
        view.goToMovieDetails(movie.getId());
    }

    @Override
    public void onRefreshMovies() {
        view.clearMovies();
        currentPage = FIRST_PAGE;
        loadMovies();
    }

    @Override
    public void onScrollBottom() {
        currentPage++;
        loadMovies();
    }

    @Override
    public void onTryAgainClicked() {
        loadMovies();
    }

    private void loadMovies() {
        view.showLoading();
        Disposable disposable = interactor.listAllMovies(currentPage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movieList -> {
                    view.hideLoading();
                    view.showList();
                    view.addMovies(movieList.getMovies());
                    currentPage = movieList.getCurrentPage();
                }, throwable -> {
                    Logger.d("ERROR");
                    view.hideLoading();
                    throwable.printStackTrace();
                });
        disposableBag.add(disposable);
    }
}

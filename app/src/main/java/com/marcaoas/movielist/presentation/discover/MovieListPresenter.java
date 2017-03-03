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

    private final ListMoviesInteractor interactor;
    private MovieListContract.View view;
    private CompositeDisposable disposableBag = new CompositeDisposable();

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
        view.showLoading();
        Disposable disposable = interactor.listAllMovies(1)
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
        disposableBag.add(disposable);
    }

    @Override
    public void movieClicked(Movie movie) {
        view.goToMovieDetails(movie.getId());
    }
}

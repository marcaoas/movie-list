package com.marcaoas.movielist.presentation.discover;

import com.marcaoas.movielist.data.mappers.utils.RequestException;
import com.marcaoas.movielist.domain.interactors.ListMoviesInteractor;
import com.marcaoas.movielist.domain.models.Movie;
import com.marcaoas.movielist.domain.models.MovieList;
import com.marcaoas.movielist.presentation.utils.Logger;

import io.reactivex.Single;
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
    private int nextPage = FIRST_PAGE;
    private boolean hasEndedPagination;
    private boolean isLoading;

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
        isLoading = true;
        view.showLoading();
        executeRequest(movieRequest(FIRST_PAGE).subscribe(movieList -> {
            view.addMovies(movieList.getMovies());
        }, throwable -> {
            throwable.printStackTrace();
        }));
    }

    @Override
    public void movieClicked(Movie movie) {
        view.goToMovieDetails(movie.getId());
    }

    @Override
    public void onRefreshMovies() {
        if(!isLoading){
            isLoading = true;
            executeRequest(movieRequest(FIRST_PAGE).subscribe(movieList -> {
                view.clearMovies();
                view.addMovies(movieList.getMovies());
            }, throwable -> {
                throwable.printStackTrace();
            }));
        }
    }

    @Override
    public void onScrollBottom() {
        if(!hasEndedPagination && nextPage != FIRST_PAGE && !isLoading) {
            isLoading = true;
            view.showLoading();
            executeRequest(movieRequest(nextPage).subscribe( movieList -> {
                view.addMovies(movieList.getMovies());
            }, throwable -> {
                throwable.printStackTrace();
            }));
        }
    }

    @Override
    public void onTryAgainClicked() {
        if(nextPage == FIRST_PAGE){
            startingScreen();
        } else {
            onScrollBottom();
        }
    }

    private Single<MovieList> movieRequest(int page) {
        return interactor.listAllMovies(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess((movieList) -> {
                    nextPage = movieList.getCurrentPage() + 1;
                    hasEndedPagination = movieList.paginationHasEnded();
                    view.hideLoading();
                    isLoading = false;
                })
                .doOnError(throwable -> {
                    showViewError(throwable);
                    isLoading = false;
                });
    }

    public void showViewError(Throwable throwable) {
        if(throwable instanceof RequestException){
            RequestException exception = (RequestException) throwable;
            if(exception.isNetworkError()) {
                view.showInternetError();
            } else {
                view.showDefaultError();
            }
        } else {
            view.showDefaultError();
        }
    }


    private void executeRequest(Disposable disposable) {
        disposableBag.add(disposable);
    }

}

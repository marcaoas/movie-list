package com.marcaoas.movielist.presentation.details;

import android.text.TextUtils;

import com.marcaoas.movielist.data.mappers.utils.RequestException;
import com.marcaoas.movielist.domain.interactors.GetMovieInteractor;
import com.marcaoas.movielist.domain.models.Movie;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by marco on 02/03/17.
 */

public class MovieDetailsPresenter implements MovieDetailsContract.Presenter {

    private final GetMovieInteractor getMovieInteractor;
    private MovieDetailsContract.View view;
    private CompositeDisposable disposableBag = new CompositeDisposable();

    public MovieDetailsPresenter(GetMovieInteractor getMovieInteractor) {
        this.getMovieInteractor = getMovieInteractor;
    }

    @Override
    public void bindView(MovieDetailsContract.View view) {
        this.view = view;
    }

    @Override
    public void unbindView() {
        if(!disposableBag.isDisposed()){
            disposableBag.dispose();
        }
        this.view = null;
    }

    @Override
    public void startScreen() {
        requestMovie();
    }

    public void requestMovie() {
        view.showLoading();
        Disposable disposable = getMovieInteractor.getMovie()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(movie -> {
                    setMovie(movie);
                    view.hideLoading();
                }, throwable -> {
                    throwable.printStackTrace();
                    showError(throwable);
                });
        disposableBag.add(disposable);
    }

    private void showError(Throwable throwable) {
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

    @Override
    public void bookMovieClicked() {
        view.goToMovieBookingScreen();
    }

    @Override
    public void onBackPressed() {
        view.finishScreen();
    }

    @Override
    public void retryLoadMovie(){
        requestMovie();
    }

    public void setMovie(Movie movie) {
        if(!TextUtils.isEmpty(movie.getBackdropUrl())){
           view.setBackdropVisible(true);
            view.setBackdropImageUrl(movie.getBackdropUrl());
        } else {
            view.setBackdropVisible(false);
        }
        view.setMovieGenres(movie.getGenreList());
        view.setMovieTitle(movie.getTitle());
        view.setMovieOverview(movie.getOverview());
        view.setPostImageUrl(movie.getPosterUrl());
        view.setRuntimeAndLanguage(movie.getRuntimeInHours(), movie.getRuntimeInMinutes(), movie.getOriginalLanguage());
    }
}

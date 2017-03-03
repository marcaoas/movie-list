package com.marcaoas.movielist.presentation.details;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.marcaoas.movielist.R;
import com.marcaoas.movielist.presentation.Navigator;
import com.marcaoas.movielist.presentation.base.BaseActivity;
import com.marcaoas.movielist.presentation.details.di.MovieDetailsModule;
import com.marcaoas.movielist.presentation.discover.di.MovieListModule;
import com.marcaoas.movielist.presentation.utils.Logger;

import javax.inject.Inject;

/**
 * Created by marco on 02/03/17.
 */

public class MovieDetailsActivity extends BaseActivity implements MovieDetailsContract.View {
    private static final String EXTRA_MOVIE_ID = "com.marcaoas.movielist.movieId";

    @Inject
    MovieDetailsContract.Presenter presenter;
    @Inject
    Navigator navigator;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        getAppComponent().plus(new MovieDetailsModule()).inject(this);

        presenter.bindView();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unbindView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.startScreen();
    }

    public static Intent getCallingIntent(Context context, String movieId) {
        Intent intent = new Intent(context, MovieDetailsActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID, movieId);
        return intent;
    }

    @Override
    public void showLoading() {
        Logger.d("showing loading");
    }

    @Override
    public void hideoading() {
        Logger.d("hiding loading");
    }

    @Override
    public void showInternetError() {
        Logger.d("showing internet error");
    }

    @Override
    public void showDefaultError() {
        Logger.d("showing default error");
    }

    @Override
    public void setBackdropVisible(boolean visible) {
        Logger.d("setting backdrop visibility to : " + visible);
    }

    @Override
    public void goToMovieBookingScreen() {
        //TODO
    }
}

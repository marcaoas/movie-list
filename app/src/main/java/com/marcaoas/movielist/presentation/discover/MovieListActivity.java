package com.marcaoas.movielist.presentation.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.marcaoas.movielist.R;
import com.marcaoas.movielist.domain.models.Movie;
import com.marcaoas.movielist.presentation.Navigator;
import com.marcaoas.movielist.presentation.base.BaseActivity;
import com.marcaoas.movielist.presentation.discover.di.MovieListModule;
import com.marcaoas.movielist.presentation.utils.Logger;
import com.marcaoas.movielist.presentation.utils.SimpleListDivider;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by marco on 28/02/17.
 */

public class MovieListActivity extends BaseActivity implements MovieListContract.View {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Inject
    MoviesAdapter moviesAdapter;
    @Inject
    Navigator navigator;

    @Inject
    MovieListContract.Presenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        getAppComponent().plus(new MovieListModule()).inject(this);

        setupRecyclerView();
        bindPresenter();
    }

    @Override
    protected void onDestroy() {
        unbindPresenter();
        super.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.startingScreen();
    }

    public void bindPresenter() {
        presenter.bindView(this);
    }

    public void unbindPresenter() {
        presenter.unbindView();
    }

    private void setupRecyclerView() {
        mRecyclerView = (RecyclerView) findViewById(R.id.movies_recyclerView);
        moviesAdapter.getItemClick().subscribe( movie -> {
            navigator.navigateToMovieDetailsActivity(movie.getId());
        });
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(moviesAdapter);
        mRecyclerView.addItemDecoration(new SimpleListDivider(this));
    }


    @Override
    public void hideLoading() {
        Logger.d("hide Loading");
    }

    @Override
    public void showLoading() {
        Logger.d("show Loading");
    }

    @Override
    public void showInternetError() {
        Logger.d("show Internet error");
    }

    @Override
    public void showDefaultError() {
        Logger.d("show default error");
    }

    @Override
    public void showList() {
        Logger.d("show movies list");
    }

    @Override
    public void addMovies(List<Movie> movies) {
        moviesAdapter.addMovies(movies);
    }
}

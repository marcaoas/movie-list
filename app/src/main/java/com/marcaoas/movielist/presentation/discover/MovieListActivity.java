package com.marcaoas.movielist.presentation.discover;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.marcaoas.movielist.R;
import com.marcaoas.movielist.domain.models.Movie;
import com.marcaoas.movielist.presentation.Navigator;
import com.marcaoas.movielist.presentation.base.BaseActivity;
import com.marcaoas.movielist.presentation.discover.di.MovieListModule;
import com.marcaoas.movielist.presentation.utils.EndScrollListener;
import com.marcaoas.movielist.presentation.utils.Logger;
import com.marcaoas.movielist.presentation.utils.SimpleListDivider;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marco on 28/02/17.
 */

public class MovieListActivity extends BaseActivity implements MovieListContract.View {

    private RecyclerView.LayoutManager mLayoutManager;

    @Inject
    MoviesAdapter moviesAdapter;
    @Inject
    Navigator navigator;
    @Inject
    MovieListContract.Presenter presenter;

    @BindView(R.id.movies_recyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.pull_to_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);
        getAppComponent().plus(new MovieListModule()).inject(this);
        setupViews();
        bindPresenter();
        presenter.startingScreen();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindPresenter();
    }

    public void bindPresenter() {
        presenter.bindView(this);
    }

    public void unbindPresenter() {
        presenter.unbindView();
    }

    private void setupViews() {
        ButterKnife.bind(this);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            presenter.onRefreshMovies();
        });
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(moviesAdapter);
        mRecyclerView.addItemDecoration(new SimpleListDivider(this));
        mRecyclerView.addOnScrollListener(new EndScrollListener() {
            @Override
            public void onScrolledToEnd() {
                presenter.onScrollBottom();
            }
        });
        moviesAdapter.getItemClick().subscribe( movie -> {
            presenter.movieClicked(movie);
        });
        moviesAdapter.getRetryButtonClicked().subscribe(view -> { presenter.onTryAgainClicked(); });
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
        moviesAdapter.removeExtraView();
    }

    @Override
    public void showLoading() {
        Logger.d("show Loading");
        moviesAdapter.showLoading();
    }

    @Override
    public void showInternetError() {
        Logger.d("show Internet error");
        moviesAdapter.showNetworkError();
    }

    @Override
    public void showDefaultError() {
        Logger.d("show default error");
        moviesAdapter.showDefaultError();
    }

    @Override
    public void showList() {
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideList() {
        mRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void clearMovies() {
        moviesAdapter.clearMovies();
    }

    @Override
    public void addMovies(List<Movie> movies) {
        moviesAdapter.addMovies(movies);
    }

    @Override
    public void goToMovieDetails(String movieId) {
        navigator.navigateToMovieDetails(this, movieId);
    }
}

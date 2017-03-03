package com.marcaoas.movielist.presentation.details;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.marcaoas.movielist.BuildConfig;
import com.marcaoas.movielist.R;
import com.marcaoas.movielist.presentation.Navigator;
import com.marcaoas.movielist.presentation.base.BaseActivity;
import com.marcaoas.movielist.presentation.details.di.MovieDetailsModule;
import com.marcaoas.movielist.presentation.utils.Logger;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marco on 02/03/17.
 */

public class MovieDetailsActivity extends BaseActivity implements MovieDetailsContract.View {
    private static final String EXTRA_MOVIE_ID = "com.marcaoas.movielist.movieId";

    private String movieId;
    @Inject
    MovieDetailsContract.Presenter presenter;
    @Inject
    Navigator navigator;

    @BindView(R.id.backdrop_imageView)
    ImageView backdropImageView;
    @BindView(R.id.poster_imageView)
    ImageView posterImageView;
    @BindView(R.id.movie_overview_textView)
    TextView movieOverviewTextView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        handleIntent(savedInstanceState);
        getAppComponent().plus(new MovieDetailsModule(movieId)).inject(this);
        ButterKnife.bind(this);
        setupToolbar();
        presenter.bindView(this);
        presenter.startScreen();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void handleIntent(Bundle extras) {
        if(extras != null) {
            movieId = extras.getString(EXTRA_MOVIE_ID);
        } else {
            movieId = getIntent().getStringExtra(EXTRA_MOVIE_ID);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unbindView();
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public void showLoading() {
        Logger.d("showing loading");
    }

    @Override
    public void hideLoading() {
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

    @Override
    public void setBackdropImageUrl(String backdropUrl) {
        Picasso.with(this)
                .load(backdropUrl)
                .placeholder(R.drawable.ic_video_icon)
                .error(R.drawable.ic_video_icon)
                .into(backdropImageView);
    }

    @Override
    public void setMovieTitle(String title) {
        collapsingToolbarLayout.setTitle(title);
    }

    @Override
    public void setMovieOverview(String overview) {
        movieOverviewTextView.setText(overview);
    }

    @Override
    public void setMovieCategories(List<String> categories) {

    }

    @Override
    public void setPostImageUrl(String posterUrl) {
        int imageWidth = (int) getResources().getDimension(R.dimen.details_image_width);
        int imageHeight = (int) getResources().getDimension(R.dimen.details_image_height);
        Picasso.with(this)
                .load(posterUrl)
                .resize(imageWidth, imageHeight)
                .centerInside()
                .placeholder(R.drawable.ic_video_icon)
                .error(R.drawable.ic_video_icon)
                .into(posterImageView);
    }

    public static Intent getCallingIntent(Context context, String movieId) {
        Intent intent = new Intent(context, MovieDetailsActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID, movieId);
        return intent;
    }
}

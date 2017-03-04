package com.marcaoas.movielist.presentation.details;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.marcaoas.movielist.BuildConfig;
import com.marcaoas.movielist.R;
import com.marcaoas.movielist.domain.models.Genre;
import com.marcaoas.movielist.presentation.Navigator;
import com.marcaoas.movielist.presentation.base.BaseActivity;
import com.marcaoas.movielist.presentation.details.di.MovieDetailsModule;
import com.marcaoas.movielist.presentation.utils.ExtraViewHolder;
import com.marcaoas.movielist.presentation.utils.Logger;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.deanwild.flowtextview.FlowTextView;

/**
 * Created by marco on 02/03/17.
 */

public class MovieDetailsActivity extends BaseActivity implements MovieDetailsContract.View {
    private static final String EXTRA_MOVIE_ID = "com.marcaoas.movielist.movieId";

    private String movieId;
    private ExtraViewHolder extraMessagesViewHolder;
    @Inject
    MovieDetailsContract.Presenter presenter;
    @Inject
    Navigator navigator;

    @BindView(R.id.backdrop_imageView)
    ImageView backdropImageView;
    @BindView(R.id.poster_imageView)
    ImageView posterImageView;
    @BindView(R.id.movie_overview_textView)
    FlowTextView movieOverviewTextView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbarLayout;
    @BindView(R.id.book_movie_button)
    Button bookMovieButton;
    @BindView(R.id.genres_textView)
    TextView genresTextView;
    @BindView(R.id.movie_details_container)
    View movieDetailsContainerView;
    @BindView(R.id.extra_messages_view)
    View extraMessagesView;
    @BindView(R.id.app_bar_layout)
    AppBarLayout collapsingAppBarView;
    @BindView(R.id.runtime_and_lang_textView)
    TextView runtimeAndLangTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        handleIntent(savedInstanceState);
        getAppComponent().plus(new MovieDetailsModule(movieId)).inject(this);
        setupViews();
        presenter.bindView(this);
        presenter.startScreen();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(EXTRA_MOVIE_ID, movieId);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                presenter.onBackPressed();
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

    private void setupViews() {
        ButterKnife.bind(this);
        setupToolbar();
        bookMovieButton.setOnClickListener( (view) -> { presenter.bookMovieClicked(); });
        extraMessagesViewHolder = new ExtraViewHolder(extraMessagesView);
        extraMessagesViewHolder.getRetryButton().setOnClickListener((view) -> { presenter.retryLoadMovie(); });
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
        hideContent();
        extraMessagesView.setVisibility(View.VISIBLE);
        extraMessagesViewHolder.showLoading();
    }

    @Override
    public void hideLoading() {
        extraMessagesView.setVisibility(View.GONE);
        showContent();
    }

    @Override
    public void showInternetError() {
        hideContent();
        extraMessagesView.setVisibility(View.VISIBLE);
        extraMessagesViewHolder.showNetworkError();
    }

    @Override
    public void showDefaultError() {
        hideContent();
        extraMessagesView.setVisibility(View.VISIBLE);
        extraMessagesViewHolder.showDefaultError();
    }

    @Override
    public void setBackdropVisible(boolean visible) {
        Logger.d("setting backdrop visibility to : " + visible);
        collapsingAppBarView.setExpanded(visible, true);
    }

    @Override
    public void goToMovieBookingScreen() {
        navigator.navigateToBookMovie(this);
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
        if(!TextUtils.isEmpty(overview)){
            movieOverviewTextView.setText(overview);
        }
    }

    @Override
    public void setMovieGenres(List<Genre> genres) {
        if(genres == null || genres.size() <= 0) {
            genresTextView.setVisibility(View.GONE);
        } else {
            genresTextView.setVisibility(View.VISIBLE);
            StringBuilder builder = new StringBuilder();
            for(int i=0; i < genres.size(); i ++){
                Genre genre = genres.get(i);
                builder.append( genre.getName());
                if(i < genres.size() - 1){
                    builder.append(", ");
                }
            }
            genresTextView.setText(builder);
        }
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

    @Override
    public void setRuntimeAndLanguage(int hours, int minutes, String language) {
        StringBuilder text = new StringBuilder();
        if(!TextUtils.isEmpty(language)){
            text.append(getString(R.string.movie_language, language));
        }
        if(!TextUtils.isEmpty(language) && (hours > 0|| minutes > 0)){
            text.append(" | ");
        }
        if(hours > 0){
            text.append(getString(R.string.movie_runtime_in_hours, hours));
        }
        if(minutes > 0){
            text.append(getString(R.string.movie_runtime_in_minutes, hours));
        }
        runtimeAndLangTextView.setText(text.toString());

    }

    @Override
    public void finishScreen() {
        finish();
    }

    public void showContent() {
        movieOverviewTextView.setVisibility(View.VISIBLE);
        bookMovieButton.setVisibility(View.VISIBLE);
        posterImageView.setVisibility(View.VISIBLE);
    }

    public void hideContent() {
        movieOverviewTextView.setVisibility(View.GONE);
        bookMovieButton.setVisibility(View.GONE);
        posterImageView.setVisibility(View.GONE);
        collapsingAppBarView.setExpanded(false);
    }

    public static Intent getCallingIntent(Context context, String movieId) {
        Intent intent = new Intent(context, MovieDetailsActivity.class);
        intent.putExtra(EXTRA_MOVIE_ID, movieId);
        return intent;
    }
}

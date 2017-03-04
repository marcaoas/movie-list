package com.marcaoas.movielist.presentation.book;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.support.annotation.Nullable;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.marcaoas.movielist.R;
import com.marcaoas.movielist.presentation.base.BaseActivity;
import com.marcaoas.movielist.presentation.book.di.BookMovieModule;
import com.marcaoas.movielist.presentation.details.di.MovieDetailsModule;
import com.marcaoas.movielist.presentation.utils.ExtraViewHolder;
import com.marcaoas.movielist.presentation.utils.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marco on 04/03/17.
 */

public class BookMovieActivity extends BaseActivity implements BookMovieContract.View{

    private ExtraViewHolder extraMessagesViewHolder;

    @Inject
    BookMovieContract.Presenter presenter;

    @BindView(R.id.book_movie_webView)
    WebView webView;
    @BindView(R.id.extra_messages_view)
    View extraMessagesView;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_movie);
        getAppComponent().plus(new BookMovieModule()).inject(this);
        setupViews();
        presenter.bindView(this);
        presenter.startScreen();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.unbindView();
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

    private void setupViews() {
        if(getSupportActionBar()!=null){
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        ButterKnife.bind(this);
        extraMessagesViewHolder = new ExtraViewHolder(extraMessagesView);
        extraMessagesViewHolder.getRetryButton().setOnClickListener((view) -> { presenter.onRetryPressed(); });
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                presenter.onWebViewLoaded();
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                presenter.onErrorReceived();
            }
        });
    }


    @Override
    public void showLoading() {
        extraMessagesViewHolder.showLoading();
        extraMessagesView.setVisibility(View.VISIBLE);
        webView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showDefaultError() {
        extraMessagesViewHolder.showDefaultError();
        extraMessagesView.setVisibility(View.VISIBLE);
        webView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showWebViewContent() {
        extraMessagesView.setVisibility(View.GONE);
        webView.setVisibility(View.VISIBLE);
    }

    @Override
    public void loadUrl(String url) {
        webView.loadUrl(url);
    }

    @Override
    public void finishScreen() {
        finish();
    }

    public static Intent getCallingIntent(Context context) {
        Intent intent = new Intent(context, BookMovieActivity.class);
        return intent;
    }
}

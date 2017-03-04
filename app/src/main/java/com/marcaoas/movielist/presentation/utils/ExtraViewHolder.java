package com.marcaoas.movielist.presentation.utils;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.marcaoas.movielist.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by marco on 04/03/17.
 */

public class ExtraViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.extra_progressBar)
    ProgressBar extraProgressBar;
    @BindView(R.id.extra_retry_button)
    Button retryButton;
    @BindView(R.id.extra_message_icon_imageView)
    ImageView extraIconImageView;
    @BindView(R.id.extra_message_textView)
    TextView extraMessageTextView;


    public ExtraViewHolder(View view) {
        super(view);
        ButterKnife.bind(this, view);
    }

    public void showDefaultError() {
        retryButton.setVisibility(View.VISIBLE);
        extraMessageTextView.setVisibility(View.VISIBLE);
        extraIconImageView.setVisibility(View.VISIBLE);
        extraProgressBar.setVisibility(View.GONE);
        extraMessageTextView.setText(R.string.extra_default_error_message);
        extraIconImageView.setImageResource(R.drawable.ic_default_error);
        retryButton.setText(R.string.extra_retry_button);
    }

    public void showLoading() {
        retryButton.setVisibility(View.GONE);
        extraMessageTextView.setVisibility(View.VISIBLE);
        extraIconImageView.setVisibility(View.GONE);
        extraProgressBar.setVisibility(View.VISIBLE);
        extraMessageTextView.setText(R.string.extra_loading_message);
    }

    public void showNetworkError() {
        retryButton.setVisibility(View.VISIBLE);
        extraMessageTextView.setVisibility(View.VISIBLE);
        extraIconImageView.setVisibility(View.VISIBLE);
        extraProgressBar.setVisibility(View.GONE);
        extraMessageTextView.setText(R.string.extra_network_error_message);
        extraIconImageView.setImageResource(R.drawable.ic_network_error);
        retryButton.setText(R.string.extra_retry_button);
    }

    public ProgressBar getExtraProgressBar() {
        return extraProgressBar;
    }

    public void setExtraProgressBar(ProgressBar extraProgressBar) {
        this.extraProgressBar = extraProgressBar;
    }

    public Button getRetryButton() {
        return retryButton;
    }

    public void setRetryButton(Button retryButton) {
        this.retryButton = retryButton;
    }

    public ImageView getExtraIconImageView() {
        return extraIconImageView;
    }

    public void setExtraIconImageView(ImageView extraIconImageView) {
        this.extraIconImageView = extraIconImageView;
    }

    public TextView getExtraMessageTextView() {
        return extraMessageTextView;
    }

    public void setExtraMessageTextView(TextView extraMessageTextView) {
        this.extraMessageTextView = extraMessageTextView;
    }
}
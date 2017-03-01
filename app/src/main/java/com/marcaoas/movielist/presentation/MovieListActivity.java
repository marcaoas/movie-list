package com.marcaoas.movielist.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.marcaoas.movielist.R;
import com.marcaoas.movielist.presentation.base.BaseActivity;

/**
 * Created by marco on 28/02/17.
 */

public class MovieListActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_list);

    }
}

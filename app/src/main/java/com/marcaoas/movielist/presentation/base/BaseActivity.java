package com.marcaoas.movielist.presentation.base;

import android.support.v7.app.AppCompatActivity;

import com.marcaoas.movielist.presentation.MovieListApplication;
import com.marcaoas.movielist.presentation.di.ApplicationComponent;

/**
 * Created by marco on 28/02/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public ApplicationComponent getAppComponent() {
        return MovieListApplication.getInstance().getApplicationComponent();
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

}

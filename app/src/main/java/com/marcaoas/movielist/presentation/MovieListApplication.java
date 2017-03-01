package com.marcaoas.movielist.presentation;

import android.app.Application;

import com.marcaoas.movielist.presentation.di.ApplicationComponent;
import com.marcaoas.movielist.presentation.di.ApplicationModule;
import com.marcaoas.movielist.presentation.di.DaggerApplicationComponent;

/**
 * Created by marco on 28/02/17.
 */

public class MovieListApplication extends Application {

    private static MovieListApplication staticInstance;
    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        staticInstance = this;

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();

    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }

    public static MovieListApplication getInstance() {
        return staticInstance;
    }
}

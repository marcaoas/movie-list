package com.marcaoas.movielist.presentation.list;

import com.marcaoas.movielist.presentation.utils.Logger;

/**
 * Created by marco on 28/02/17.
 */

public class MovieListPresenter implements MovieListContract.Presenter {

    private MovieListContract.View view;

    @Override
    public void bindView(MovieListContract.View view) {
        this.view = view;
        Logger.d("BIND VIEW");
    }

    @Override
    public void unbindView() {
        this.view = null;
        Logger.d("UNBIND VIEW");
    }
}

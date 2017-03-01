package com.marcaoas.movielist.presentation.list;

/**
 * Created by marco on 28/02/17.
 */

public interface MovieListContract {

    interface Presenter {

        void bindView(View view);
        void unbindView();

    }

    interface View {

        void hideLoading();
        void showLoading();
        void showInternetError();
        void showDefaultError();
        void showList();
        void addMovies();

    }

}

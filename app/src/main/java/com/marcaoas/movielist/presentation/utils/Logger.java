package com.marcaoas.movielist.presentation.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by marco on 28/02/17.
 */

public class Logger {

    private static final String MOVIE_LIST_TAG = "MOVIELIST";

    public static void d(String log) {
        if(!TextUtils.isEmpty(log)) {
            Log.d(MOVIE_LIST_TAG, log);
        }
    }

    public static void w(String log) {
        if(!TextUtils.isEmpty(log)) {
            Log.w(MOVIE_LIST_TAG, log);
        }
    }

    public static void i(String log) {
        if(!TextUtils.isEmpty(log)) {
            Log.i(MOVIE_LIST_TAG, log);
        }
    }

    public static void e(String log) {
        if(!TextUtils.isEmpty(log)) {
            Log.e(MOVIE_LIST_TAG, log);
        }
    }

}

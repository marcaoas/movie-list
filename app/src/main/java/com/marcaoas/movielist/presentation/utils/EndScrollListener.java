package com.marcaoas.movielist.presentation.utils;

import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by marco on 04/03/17.
 */

public abstract class EndScrollListener extends RecyclerView.OnScrollListener {
    public static String TAG = EndScrollListener.class.getSimpleName();

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        if (canScrollVertically(recyclerView, 1)) {
            onScrolledToEnd();
        }
    }

    public boolean canScrollVertically(RecyclerView recyclerView, int direction) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            return !recyclerView.canScrollVertically(direction);
        } else {
            final int offset = recyclerView.computeVerticalScrollOffset();
            final int range = recyclerView.computeVerticalScrollRange() - recyclerView.computeVerticalScrollExtent();
            if (range == 0) return false;
            if (direction < 0) {
                return offset > 0;
            } else {
                return offset < range - 1;
            }
        }
    }

    public abstract void onScrolledToEnd();
}
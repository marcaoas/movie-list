package com.marcaoas.movielist.presentation.discover;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.marcaoas.movielist.R;
import com.marcaoas.movielist.domain.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by marco on 28/02/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int FOOTER_VIEW_TYPE = 10;
    private static final int MOVIE_VIEW_TYPE = 20;
    private ArrayList<Movie> movieList;
    private final PublishSubject<Movie> onMovieClickSubject = PublishSubject.create();
    private View footerView;

    public MoviesAdapter() {
        movieList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == MOVIE_VIEW_TYPE){
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.movie_list_item, parent, false);
            return new MovieViewHolder(view);
        } else {
            return null;
        }

    }

    @Override
    public int getItemViewType(int position) {
        if(position == getItemCount() - 1 && footerView != null){
            return FOOTER_VIEW_TYPE;
        } else {
            return MOVIE_VIEW_TYPE;
        }
    }

    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position) == MOVIE_VIEW_TYPE){
            Movie movie = getItem(position);
            if(movie != null){
                MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
                movieViewHolder.title.setText(movie.getTitle());
                int imageWidth = (int) movieViewHolder.getContext().getResources().getDimension(R.dimen.list_image_width);
                int imageHeight = (int) movieViewHolder.getContext().getResources().getDimension(R.dimen.list_image_height);
                Picasso.with(movieViewHolder.getContext())
                        .load(movie.getPosterUrl())
                        .resize(imageWidth, imageHeight)
                        .centerInside()
                        .placeholder(R.drawable.ic_video_icon)
                        .error(R.drawable.ic_video_icon)
                        .into(movieViewHolder.poster);
                movieViewHolder.container.setOnClickListener(view -> { onMovieClickSubject.onNext(movie); });
            }
        }
    }

    @Override
    public int getItemCount() {
        if(footerView!=null){
            return movieList.size() + 1;
        } else {
            return movieList.size();
        }

    }

    public Movie getItem(int position) {
        if(position >= movieList.size() || position > 0){
            return null;
        }
        return movieList.get(position);
    }

    public void addMovies(List<Movie> movies) {
        movieList.addAll(movies);
        notifyDataSetChanged();
    }

    public Observable<Movie> getItemClick() {
        return onMovieClickSubject;
    }

    public void clearMovies() {
        movieList.clear();
        notifyDataSetChanged();
    }

    public void setFooterView(View view) {
        footerView = view;
        notifyDataSetChanged();
    }

    public void hideFooterView() {
        footerView = null;
        notifyDataSetChanged();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {
        View container;
        TextView title;
        TextView popularity;
        ImageView poster;

        public MovieViewHolder(View view) {
            super(view);
            container = view.findViewById(R.id.movie_item_container);
            title = (TextView) view.findViewById(R.id.title_textView);
            popularity = (TextView) view.findViewById(R.id.popularity_textView);
            poster = (ImageView) view.findViewById(R.id.poster_imageView);
        }

        public Context getContext() {
            return container.getContext();
        }
    }
}

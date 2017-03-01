package com.marcaoas.movielist.presentation.list;

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

/**
 * Created by marco on 28/02/17.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

    private ArrayList<Movie> movieList;

    public MoviesAdapter() {
        movieList = new ArrayList<>();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_item, parent, false);
        return new MovieViewHolder(view);
    }

    public void onBindViewHolder(MovieViewHolder holder, int position) {
        Movie movie = getItem(position);
        holder.title.setText(movie.getTitle());

        int imageSize = (int) holder.getContext().getResources().getDimension(R.dimen.list_image_width);
        Picasso.with(holder.getContext())
                .load(movie.getPosterUrl())
                .resize(imageSize, imageSize)
                .centerInside()
                .placeholder(R.drawable.ic_video_icon)
                .error(R.drawable.ic_video_icon)
                .into(holder.poster);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public Movie getItem(int position) {
        return movieList.get(position);
    }

    public void addMovies(List<Movie> movies) {
        movieList.addAll(movies);
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

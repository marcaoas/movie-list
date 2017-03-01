package com.marcaoas.movielist.data.entities.tmdb;

import com.google.gson.annotations.SerializedName;

/**
 * Created by marco on 28/02/17.
 */

public class TMDBMovieListEntity {

    @SerializedName("page")
    private int currentPages;
    @SerializedName("total_pages")
    private int totalPage;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("results")
    private List<TMDBMovieEntity> movies;

    public int getCurrentPages() {
        return currentPages;
    }

    public void setCurrentPages(int currentPages) {
        this.currentPages = currentPages;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<TMDBMovieEntity> getMovies() {
        return movies;
    }

    public void setMovies(List<TMDBMovieEntity> movies) {
        this.movies = movies;
    }
}

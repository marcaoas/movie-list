package com.marcaoas.movielist.data.entities.tmdb;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by marco on 28/02/17.
 */

public class TMDBMovieListEntity {

    @SerializedName("page")
    private int currentPage;
    @SerializedName("total_pages")
    private int totalPage;
    @SerializedName("total_results")
    private int totalResults;
    @SerializedName("results")
    private List<TMDBMovieEntity> movies;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
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

package com.marcaoas.movielist.domain.interactors;

import com.marcaoas.movielist.domain.repositories.MoviesRepository;

import org.junit.Test;

import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by marco on 04/03/17.
 */

public class ListMoviesInteractorTest {

    @Test
    public void getAllMovies_callRepositoryMoviesPassingThePageAndAnyDate() {
        MoviesRepository moviesRepositoryMock = mock(MoviesRepository.class);
        ListMoviesInteractor listMovieInteractor = new ListMoviesInteractor(moviesRepositoryMock);
        listMovieInteractor.listAllMovies(1);
        verify(moviesRepositoryMock, times(1)).getMovieListWithReleaseDateLTESortedByReleaseDate(eq(1), any(Date.class));
    }

    @Test
    public void getAllMovies_withPageLowerThan1_callRepositoryMoviesPassing1AndAnyDate() {
        MoviesRepository moviesRepositoryMock = mock(MoviesRepository.class);
        ListMoviesInteractor listMovieInteractor = new ListMoviesInteractor(moviesRepositoryMock);
        listMovieInteractor.listAllMovies(0);
        verify(moviesRepositoryMock, times(1)).getMovieListWithReleaseDateLTESortedByReleaseDate(eq(1), any(Date.class));
    }

    @Test
    public void getAllMovies_withPageLowerGreater1_callRepositoryMoviesPassingTheSamePageAndAnyDate() {
        MoviesRepository moviesRepositoryMock = mock(MoviesRepository.class);
        ListMoviesInteractor listMovieInteractor = new ListMoviesInteractor(moviesRepositoryMock);
        listMovieInteractor.listAllMovies(2);
        verify(moviesRepositoryMock, times(1)).getMovieListWithReleaseDateLTESortedByReleaseDate(eq(2), any(Date.class));
    }
}

package com.marcaoas.movielist.domain.interactors;

import com.marcaoas.movielist.domain.repositories.MoviesRepository;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by marco on 04/03/17.
 */

public class GetMovieInteractorTest {
    private static final String FAKE_MOVIE_ID = "123158";

    @Test
    public void getMovie_callRepositoryMoviePassingMovieId() {
        MoviesRepository moviesRepositoryMock = mock(MoviesRepository.class);
        GetMovieInteractor getMovieInteractor = new GetMovieInteractor(moviesRepositoryMock, FAKE_MOVIE_ID);
        getMovieInteractor.getMovie();
        verify(moviesRepositoryMock, times(1)).getMovie("123158");
    }

}

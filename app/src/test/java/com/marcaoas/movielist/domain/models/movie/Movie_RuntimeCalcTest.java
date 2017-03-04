package com.marcaoas.movielist.domain.models.movie;

import com.marcaoas.movielist.domain.models.Movie;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by marco on 04/03/17.
 */

public class Movie_RuntimeCalcTest {
    @Test
    public void movieRuntimeInMinutes_59min_Return59() {
        Movie movie = new Movie();
        movie.setRuntime(59);
        assertThat(movie.getRuntimeInMinutes(), is(59));
    }

    @Test
    public void movieRuntimeInMinutes_multipleOf60_Return0() {
        Movie movie = new Movie();
        movie.setRuntime(60);
        assertThat(movie.getRuntimeInMinutes(), is(0));
        movie.setRuntime(120);
        assertThat(movie.getRuntimeInMinutes(), is(0));
    }

    @Test
    public void movieRuntimeInMinutes_61min_Return1() {
        Movie movie = new Movie();
        movie.setRuntime(61);
        assertThat(movie.getRuntimeInMinutes(), is(1));
    }

    @Test
    public void movieRuntimeInHours_60min_Return1() {
        Movie movie = new Movie();
        movie.setRuntime(60);
        assertThat(movie.getRuntimeInHours(), is(1));
    }

    @Test
    public void movieRuntimeInHours_moreThan60minAndLessThan120_Return1() {
        Movie movie = new Movie();
        movie.setRuntime(61);
        assertThat(movie.getRuntimeInHours(), is(1));
    }

    @Test
    public void movieRuntimeInHours_lessThan60min_Return0() {
        Movie movie = new Movie();
        movie.setRuntime(59);
        assertThat(movie.getRuntimeInHours(), is(0));
    }
}

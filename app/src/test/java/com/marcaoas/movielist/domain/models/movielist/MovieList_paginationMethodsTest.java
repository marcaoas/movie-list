package com.marcaoas.movielist.domain.models.movielist;

import com.marcaoas.movielist.domain.models.MovieList;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by marco on 04/03/17.
 */

public class MovieList_paginationMethodsTest {

    @Test
    public void movieListPaginationHasEnded_withPageEqualTotalPage_ReturnTrue(){
        MovieList list = new MovieList();
        list.setCurrentPage(1);
        list.setTotalPages(1);
        assertThat(list.paginationHasEnded(), is(true));

        list.setCurrentPage(2);
        list.setTotalPages(2);
        assertThat(list.paginationHasEnded(), is(true));
    }

    @Test
    public void movieListPaginationHasEnded_withPageLowerThanTotalPages_ReturnFalse(){
        MovieList list = new MovieList();
        list.setCurrentPage(1);
        list.setTotalPages(2);
        assertThat(list.paginationHasEnded(), is(false));
    }

    @Test
    public void movieListPaginationHasEnded_withPageGreaterThanTotalPages_ReturnFalse(){
        MovieList list = new MovieList();
        list.setCurrentPage(3);
        list.setTotalPages(2);
        assertThat(list.paginationHasEnded(), is(false));
    }

}

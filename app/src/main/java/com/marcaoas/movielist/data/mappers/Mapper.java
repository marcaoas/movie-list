package com.marcaoas.movielist.data.mappers;

/**
 * Created by marco on 28/02/17.
 */

public interface Mapper<T, U> {

    U transform(T t);

}

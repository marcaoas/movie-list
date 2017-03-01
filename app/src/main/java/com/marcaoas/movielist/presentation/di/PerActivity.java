package com.marcaoas.movielist.presentation.di;

/**
 * Created by marco on 28/02/17.
 */

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface PerActivity {

}

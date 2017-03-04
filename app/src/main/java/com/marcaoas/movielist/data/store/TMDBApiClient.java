package com.marcaoas.movielist.data.store;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.marcaoas.movielist.BuildConfig;
import com.marcaoas.movielist.data.entities.tmdb.TMDBMovieEntity;
import com.marcaoas.movielist.data.entities.tmdb.TMDBMovieListEntity;
import com.marcaoas.movielist.data.mappers.Mapper;
import com.marcaoas.movielist.data.mappers.MovieListMapper;
import com.marcaoas.movielist.data.mappers.utils.RequestException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by marco on 28/02/17.
 */
public class TMDBApiClient {

    //normally api credentials and endpoint are placed at build.gradle with their respectively build type
    //but as we only have one environment it will be here for now
    public static final String API_ENDPOINT = "http://api.themoviedb.org/3/";
    public static final String API_KEY = "9a2867abc5ffe4b5bc020fab46ca4f6a";

    public static final String API_DATE_FORMAT = "yyyy-MM-dd";
    public static final String IMAGE_ENDPOINT = "https://image.tmdb.org/t/p/original";
    public static final String RELEASE_SORTED_PARAM = "release_date.desc";
    private static TMDBApiServices apiServices;
    private final String apiKey;
    private final String apiEnpoint;

    public TMDBApiClient(String apiKey, String apiEndpoint) {
        this.apiKey = apiKey;
        this.apiEnpoint = apiEndpoint;
    }

    private static TMDBApiServices getApiServices(String apiEndpoint) {
        if (apiServices == null) {
            buildApiServices(apiEndpoint);
        }
        return apiServices;
    }

    private static void buildApiServices(String apiEndpoint) {
        final OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE));

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClientBuilder.build())
                .baseUrl(apiEndpoint)
                .addConverterFactory(getJsonSerializer())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiServices = retrofit.create(TMDBApiServices.class);
    }

    public String getApiKey() {
        return apiKey;
    }

    public static Converter.Factory getJsonSerializer() {
        Gson gson = new GsonBuilder().serializeNulls()
                .setDateFormat(API_DATE_FORMAT).create();
        return GsonConverterFactory.create(gson);
    }

    private static String getFormattedDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(API_DATE_FORMAT, Locale.getDefault());
        return dateFormat.format(date);
    }

    private <T> SingleTransformer<Response<T>, T> verifyResponse() {
        return mapResponse(t -> t);
    }

    private <T, U> SingleTransformer<Response<T>, U> mapResponse(Mapper<T, U> mapper) {
        return upstream -> upstream.map(response -> {
            if (response.isSuccessful()) {
                return mapper.transform(response.body());
            }
            throw new RequestException(response.code(), response.errorBody().string());
        });
    }

    private <T> SingleTransformer<T, T> verifyRequestError() {
        return upstream -> upstream.onErrorResumeNext(throwable -> {
            if (throwable instanceof RequestException) {
                return Single.error(throwable);
            }
            return Single.error(new RequestException(throwable));
        });
    }

    public Single<TMDBMovieEntity> getMovie(String movieId) {
        return getApiServices(apiEnpoint).getMovie(movieId, getApiKey())
                .compose(verifyResponse())
                .compose(verifyRequestError());
    }

    public Single<TMDBMovieListEntity> getMovieListSortedByReleaseDateWithReleaseDateLTE(int page, Date releaseDate) {
        return getApiServices(apiEnpoint).getMovieList(getApiKey(), page, RELEASE_SORTED_PARAM, getFormattedDate(releaseDate))
                .compose(verifyResponse())
                .compose(verifyRequestError());
    }
}

package com.marcaoas.movielist.data.store;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.marcaoas.movielist.BuildConfig;
import com.marcaoas.movielist.data.entities.tmdb.TMDBMovieListEntity;
import com.marcaoas.movielist.data.mappers.Mapper;

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

    public static final String API_ENDPOINT = "http://api.themoviedb.org/3/";
    public static final String API_KEY = "9a2867abc5ffe4b5bc020fab46ca4f6a";
    public static final String API_DATE_FORMAT = "yyyy-MM-dd";
    private static TMDBApiServices apiServices;

    private static TMDBApiServices getApiServices() {
        if (apiServices == null) {
            buildApiServices();
        }
        return apiServices;
    }

    private static void buildApiServices() {
        final OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(new HttpLoggingInterceptor().setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE));

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClientBuilder.build())
                .baseUrl(getApiEndpoint())
                .addConverterFactory(getJsonSerializer())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        apiServices = retrofit.create(TMDBApiServices.class);
    }

    public static String getApiEndpoint() {
        return API_ENDPOINT;
    }

    public static String getApiKey() {
        return API_KEY;
    }

    public static Converter.Factory getJsonSerializer() {
        Gson gson = new GsonBuilder().serializeNulls()
                .setDateFormat(API_DATE_FORMAT).create();
        return GsonConverterFactory.create(gson);
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

    public Single<TMDBMovieListEntity> getMovieList() {
        return getApiServices().getMovieList(getApiKey());
    }
}

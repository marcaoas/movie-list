package com.marcaoas.movielist.data.store;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.marcaoas.movielist.BuildConfig;
import com.marcaoas.movielist.data.entities.tmdb.TMDBMovieListEntity;
import com.marcaoas.movielist.data.mappers.Mapper;
import com.marcaoas.movielist.data.mappers.utils.RequestException;

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
    public static final String IMAGE_ENDPOINT = "https://image.tmdb.org/t/p/original";
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

    public Single<TMDBMovieListEntity> getMovieList(int page) {
        return getApiServices(apiEnpoint).getMovieList(getApiKey(), page)
                .compose(verifyResponse());
    }
}
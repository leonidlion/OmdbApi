package com.dev.leo.omdbapi.repository.api;

import android.support.annotation.IntRange;

import com.dev.leo.omdbapi.BuildConfig;
import com.dev.leo.omdbapi.Constants;
import com.dev.leo.omdbapi.repository.DataSource;
import com.dev.leo.omdbapi.repository.api.model.FilmResponse;
import com.dev.leo.omdbapi.repository.api.model.SearchResponse;
import com.dev.leo.omdbapi.utils.ApiUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiDataSource implements DataSource {
    private static ApiDataSource instance;
    private OmbdService service;

    private ApiDataSource(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient.Builder client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(Constants.CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Constants.READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Constants.WRITE_TIMEOUT, TimeUnit.SECONDS);

        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client.build())
                .baseUrl(BuildConfig.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        service = retrofit.create(OmbdService.class);
    }

    public static synchronized ApiDataSource getInstance(){
        if (instance == null)
            instance = new ApiDataSource();
        return instance;
    }

    public void searchFilms(String title, @IntRange(from = 1) int page, final BaseCallback<SearchResponse> callback){
        service.searchFilms(BuildConfig.API_KEY, title, page).enqueue(ApiUtils.getGenericCallback(callback));
    }

    public void getFilmById(String imdbId, final BaseCallback<FilmResponse> callback){
        service.getFilmById(BuildConfig.API_KEY, imdbId).enqueue(ApiUtils.getGenericCallback(callback));
    }
}

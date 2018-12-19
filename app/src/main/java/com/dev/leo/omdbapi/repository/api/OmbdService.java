package com.dev.leo.omdbapi.repository.api;

import android.support.annotation.IntRange;

import com.dev.leo.omdbapi.repository.api.model.FilmResponse;
import com.dev.leo.omdbapi.repository.api.model.SearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OmbdService {
    @GET(".")
    Call<SearchResponse> searchFilms(@Query("apikey") String apiKey, @Query("s") String query, @Query("page") @IntRange(from = 1) int page);

    @GET(".")
    Call<FilmResponse> getFilmById(@Query("apikey") String apiKey, @Query("i") String imdbId);
}

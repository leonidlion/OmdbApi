package com.dev.leo.omdbapi.repository;

import android.support.annotation.IntRange;

import com.dev.leo.omdbapi.repository.api.BaseCallback;
import com.dev.leo.omdbapi.repository.api.model.FilmResponse;
import com.dev.leo.omdbapi.repository.api.model.SearchResponse;

public interface DataSource {
    void searchFilms(String title, @IntRange(from = 1) int page, BaseCallback<SearchResponse> callback);
    void getFilmById(String imdbId, BaseCallback<FilmResponse> callback);
}

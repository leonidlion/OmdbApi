package com.dev.leo.omdbapi.ui.activity.detail;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.dev.leo.omdbapi.repository.LiveRepository;
import com.dev.leo.omdbapi.repository.api.model.FilmResponse;

public class FilmDetailViewModel extends ViewModel {
    private LiveRepository repository;

    public FilmDetailViewModel(){
        repository = new LiveRepository();
    }

    public LiveData<FilmResponse> getFilmLiveData(String imdbId) {
        return repository.getFilmResponse(imdbId);
    }
}

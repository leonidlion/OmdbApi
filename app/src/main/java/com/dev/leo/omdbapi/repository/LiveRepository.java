package com.dev.leo.omdbapi.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.dev.leo.omdbapi.repository.api.ApiDataSource;
import com.dev.leo.omdbapi.repository.api.BaseCallback;
import com.dev.leo.omdbapi.repository.api.model.FilmResponse;

public class LiveRepository {
    private DataSource apiDataSource;

    public LiveRepository(){
        apiDataSource = ApiDataSource.getInstance();
    }

    public LiveData<FilmResponse> getFilmResponse(String imdbId){
        MutableLiveData<FilmResponse> filmLifeData = new MutableLiveData<>();
        apiDataSource.getFilmById(imdbId, new BaseCallback<FilmResponse>() {
            @Override
            public void onSuccess(FilmResponse data) {
                filmLifeData.setValue(data);
            }

            @Override
            public void onError(int code, String message) {
                filmLifeData.setValue(null);
            }

            @Override
            public void onFailure(Throwable throwable) {
                filmLifeData.setValue(null);
            }
        });
        return filmLifeData;
    }
}

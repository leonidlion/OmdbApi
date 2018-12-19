package com.dev.leo.omdbapi.utils;

import android.support.annotation.NonNull;

import com.dev.leo.omdbapi.Constants;
import com.dev.leo.omdbapi.repository.api.BaseCallback;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ApiUtils {

    public static  <T> Callback<T> getGenericCallback(final BaseCallback<T> callback){
        return new Callback<T>() {
            @Override
            public void onResponse(@NonNull Call<T> call, @NonNull Response<T> response) {
                if (response.isSuccessful()){
                    callback.onSuccess(response.body());
                }else {
                    try {
                        callback.onError(response.code(), response.errorBody().string());
                    } catch (IOException e) {
                        callback.onFailure(e);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<T> call, @NonNull Throwable t) {
                t.printStackTrace();
                if (t instanceof ConnectException || t instanceof SocketTimeoutException || t instanceof UnknownHostException){
                    callback.onError(-1, Constants.NO_INTERNET);
                }else callback.onFailure(t);
            }
        };
    }
}

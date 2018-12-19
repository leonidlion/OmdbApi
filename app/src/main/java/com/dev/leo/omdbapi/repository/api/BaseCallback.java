package com.dev.leo.omdbapi.repository.api;

public interface BaseCallback<T> {
    void onSuccess(T data);
    void onError(int code, String message);
    void onFailure(Throwable throwable);
}
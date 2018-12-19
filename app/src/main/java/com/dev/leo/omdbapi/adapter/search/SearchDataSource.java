package com.dev.leo.omdbapi.adapter.search;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.dev.leo.omdbapi.Constants;
import com.dev.leo.omdbapi.repository.DataSource;
import com.dev.leo.omdbapi.repository.api.BaseCallback;
import com.dev.leo.omdbapi.repository.api.NetworkState;
import com.dev.leo.omdbapi.repository.api.ApiDataSource;
import com.dev.leo.omdbapi.repository.api.model.Search;
import com.dev.leo.omdbapi.repository.api.model.SearchResponse;

public class SearchDataSource extends PageKeyedDataSource<Integer, Search> {
    private static String query;
    private DataSource api;

    private MutableLiveData<NetworkState> networkState;
    private MutableLiveData<NetworkState> initialLoading;

    public SearchDataSource(){
        api = ApiDataSource.getInstance();
        networkState = new MutableLiveData<>();
        initialLoading = new MutableLiveData<>();
    }

    public static void setQuery(String q){
        query = q;
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Search> callback) {
        if (TextUtils.isEmpty(query)) return;

        initialLoading.postValue(NetworkState.LOADING);
        networkState.postValue(NetworkState.LOADING);
        api.searchFilms(query, Constants.FIRST_PAGE, new BaseCallback<SearchResponse>() {
            @Override
            public void onSuccess(SearchResponse data) {
                if (!data.getSearch().isEmpty() && TextUtils.isEmpty(data.getError())) {
                    initialLoading.postValue(NetworkState.LOADING);
                    networkState.postValue(NetworkState.LOADED);
                    callback.onResult(data.search, null, Constants.FIRST_PAGE + 1);
                }else {
                    initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, data.getError()));
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, data.getError()));
                }
            }

            @Override
            public void onError(int code, String message) {
                initialLoading.postValue(new NetworkState(NetworkState.Status.FAILED, message));
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, message));
            }

            @Override
            public void onFailure(Throwable throwable) {
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, throwable.getMessage()));
            }
        });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Search> callback) {
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Search> callback) {
        networkState.postValue(NetworkState.LOADING);
        api.searchFilms(query, params.key, new BaseCallback<SearchResponse>() {
            @Override
            public void onSuccess(SearchResponse data) {
                if (!data.getSearch().isEmpty() && TextUtils.isEmpty(data.getError())) {
                    Integer nextKey;

                    initialLoading.postValue(NetworkState.LOADING);
                    networkState.postValue(NetworkState.LOADED);

                    float pageCount = (float)Integer.parseInt(data.getTotalResults()) / (float) Constants.PAGE_SIZE;

                    nextKey = params.key == Math.ceil(pageCount) ? null : params.key + 1;

                    callback.onResult(data.getSearch(), nextKey);
                }else {
                    networkState.postValue(new NetworkState(NetworkState.Status.FAILED, data.getError()));
                }
            }

            @Override
            public void onError(int code, String message) {
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, message));
            }

            @Override
            public void onFailure(Throwable throwable) {
                networkState.postValue(new NetworkState(NetworkState.Status.FAILED, throwable.getMessage()));
            }
        });
    }

    public MutableLiveData<NetworkState> getNetworkState() {
        return networkState;
    }

    public MutableLiveData getInitialLoading() {
        return initialLoading;
    }
}

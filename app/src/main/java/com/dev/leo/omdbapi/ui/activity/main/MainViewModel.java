package com.dev.leo.omdbapi.ui.activity.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.dev.leo.omdbapi.Constants;
import com.dev.leo.omdbapi.adapter.search.SearchDataFactory;
import com.dev.leo.omdbapi.adapter.search.SearchDataSource;
import com.dev.leo.omdbapi.repository.api.NetworkState;
import com.dev.leo.omdbapi.repository.api.model.Search;

import java.util.concurrent.Executors;

public class MainViewModel extends ViewModel {
    private LiveData<PagedList<Search>> searchLiveData;
    private LiveData<NetworkState> networkStateLiveData;
    private LiveData<SearchDataSource> searchDataSourceLiveData;

    public MainViewModel(){
        SearchDataFactory factory = new SearchDataFactory();

        searchDataSourceLiveData = factory.getMutableLiveData();

        networkStateLiveData = Transformations.switchMap(factory.getMutableLiveData(), SearchDataSource::getNetworkState);

        PagedList.Config pagedListConfig = new PagedList.Config.Builder()
                .setEnablePlaceholders(true)
                .setPageSize(Constants.PAGE_SIZE)
                .setInitialLoadSizeHint(5)
                .build();

        searchLiveData = new LivePagedListBuilder<>(factory, pagedListConfig)
                .setFetchExecutor(Executors.newSingleThreadExecutor()).build();
    }

    public void resetSearch(){
        if (searchDataSourceLiveData.getValue() != null)
            searchDataSourceLiveData.getValue().invalidate();
    }

    public void changeQuery(String query){
        SearchDataSource.setQuery(query);
        if (searchDataSourceLiveData.getValue() != null)
            searchDataSourceLiveData.getValue().invalidate();
    }

    public LiveData<PagedList<Search>> getSearchLiveData(){
        return searchLiveData;
    }

    public LiveData<NetworkState> getNetworkStateLiveData(){
        return networkStateLiveData;
    }
}

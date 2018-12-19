package com.dev.leo.omdbapi.adapter.search;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.dev.leo.omdbapi.repository.api.model.Search;

public class SearchDataFactory extends DataSource.Factory<Integer, Search> {
    private MutableLiveData<SearchDataSource> mutableLiveData;
    private SearchDataSource searchDataSource;

    public SearchDataFactory(){
        mutableLiveData = new MutableLiveData<>();
    }

    @Override
    public DataSource<Integer, Search> create() {
        searchDataSource = new SearchDataSource();
        mutableLiveData.postValue(searchDataSource);
        return searchDataSource;
    }

    public MutableLiveData<SearchDataSource> getMutableLiveData() {
        return mutableLiveData;
    }
}

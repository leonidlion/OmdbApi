package com.dev.leo.omdbapi.ui.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.dev.leo.omdbapi.Constants;
import com.dev.leo.omdbapi.enums.BundleKeys;
import com.dev.leo.omdbapi.ui.activity.BaseActivity;
import com.dev.leo.omdbapi.R;
import com.dev.leo.omdbapi.adapter.FilmPagedAdapter;
import com.dev.leo.omdbapi.databinding.ActivityMainBinding;
import com.dev.leo.omdbapi.ui.activity.detail.FilmDetailActivity;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel> implements FilmPagedAdapter.ClickListener{
    private FilmPagedAdapter adapter;

    private Handler queryHandler = new Handler();
    private String searchQuery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initPagedAdapter();

        initRefreshLayout();

        viewModel.getSearchLiveData().observe(this, searchPagedList->{
            adapter.submitList(searchPagedList);
            if (binding.refreshLayout.isRefreshing()) binding.refreshLayout.setRefreshing(false);
        });

        viewModel.getNetworkStateLiveData().observe(this, networkState -> {
            adapter.setNetworkState(networkState);
        });
    }

    private void initRefreshLayout(){
        binding.refreshLayout.setColorSchemeResources(
                android.R.color.holo_orange_light,
                android.R.color.holo_orange_dark,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        binding.refreshLayout.setOnRefreshListener(() -> {
            viewModel.resetSearch();
        });
    }

    private void initPagedAdapter(){
        adapter = new FilmPagedAdapter();
        adapter.setClickListener(this);
        binding.includeContent.filmsRecycler.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem mSearch = menu.findItem(R.id.action_search);

        SearchView mSearchView = (SearchView) mSearch.getActionView();
        mSearchView.setQueryHint(getString(R.string.search));

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchQuery = newText;
                queryHandler.removeCallbacksAndMessages(null);

                queryHandler.postDelayed(()-> viewModel.changeQuery(searchQuery),
                        Constants.QUERY_DELAY_MS);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected boolean userToolbar() {
        return true;
    }

    @Override
    protected int getToolbarId() {
        return R.id.toolbar;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected Class<MainViewModel> getViewModelClass() {
        return MainViewModel.class;
    }

    @Override
    public void onItemClick(String imdbId) {
        Intent intent = new Intent(this, FilmDetailActivity.class);
        intent.putExtra(BundleKeys.ARG_IMDB_ID.name(), imdbId);

        startActivity(intent);
    }
}

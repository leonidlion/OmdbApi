package com.dev.leo.omdbapi.ui.activity.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.dev.leo.omdbapi.R;
import com.dev.leo.omdbapi.databinding.ActivityFilmDetailBinding;
import com.dev.leo.omdbapi.enums.BundleKeys;
import com.dev.leo.omdbapi.ui.activity.BaseActivity;

public class FilmDetailActivity extends BaseActivity<ActivityFilmDetailBinding, FilmDetailViewModel> {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!getIntent().hasExtra(BundleKeys.ARG_IMDB_ID.name()))
            finish();

        String imdbId = getIntent().getStringExtra(BundleKeys.ARG_IMDB_ID.name());

        viewModel.getFilmLiveData(imdbId).observe(this, filmResponse -> binding.setFilm(filmResponse));
    }

    @Override
    protected boolean useAnimation() {
        return true;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_film_detail;
    }

    @Override
    protected Class<FilmDetailViewModel> getViewModelClass() {
        return FilmDetailViewModel.class;
    }
}

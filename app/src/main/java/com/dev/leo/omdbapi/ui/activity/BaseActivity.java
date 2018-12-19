package com.dev.leo.omdbapi.ui.activity;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.AnimRes;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.dev.leo.omdbapi.R;

import butterknife.ButterKnife;

public abstract class BaseActivity<B extends ViewDataBinding, VM extends ViewModel> extends AppCompatActivity {
    @Nullable
    protected Toolbar toolbar;

    protected VM viewModel;
    protected B binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, getLayoutRes());

        viewModel = ViewModelProviders.of(this).get(getViewModelClass());

        if (useAnimation())
            overridePendingTransition(getEnterAnim(), getExitAnim());

        if (userToolbar() && getToolbarId() != 0) {
            if (findViewById(getToolbarId()) instanceof Toolbar) {
                toolbar = findViewById(getToolbarId());
                setSupportActionBar(toolbar);
            }
        }
    }

    @LayoutRes
    protected abstract int getLayoutRes();

    protected abstract Class<VM> getViewModelClass();

    protected boolean useAnimation(){
        return false;
    }

    protected boolean userToolbar(){
        return false;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (useAnimation())
            overridePendingTransition(getBackOpenAnim(), getBackCloseAnim());
    }

    @AnimRes
    protected int getBackOpenAnim(){
        return R.anim.activity_close_translate;
    }

    @AnimRes
    protected int getBackCloseAnim(){
        return R.anim.activity_close_translate;
    }

    @AnimRes
    protected int getEnterAnim(){
        return R.anim.activity_open_translate;
    }

    @AnimRes
    protected int getExitAnim(){
        return R.anim.activity_close_translate;
    }

    @IdRes
    protected int getToolbarId(){
        return 0;
    }

    protected void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    protected void showToast(@StringRes int message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

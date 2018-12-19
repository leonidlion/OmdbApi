package com.dev.leo.omdbapi.adapter;

import android.arch.paging.PagedListAdapter;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.dev.leo.omdbapi.GlideApp;
import com.dev.leo.omdbapi.R;
import com.dev.leo.omdbapi.databinding.ItemFilmBinding;
import com.dev.leo.omdbapi.repository.api.NetworkState;
import com.dev.leo.omdbapi.repository.api.model.Search;

public class FilmPagedAdapter extends PagedListAdapter<Search, RecyclerView.ViewHolder> {
    private static final int TYPE_DATA = 1;
    private static final int TYPE_LOAD = 0;

    private NetworkState networkState;
    private ClickListener clickListener;

    public interface ClickListener{
        void onItemClick(String imdbId);
    }

    public FilmPagedAdapter() {
        super(Search.DIFF_CALLBACK);
    }

    public void setClickListener(ClickListener clickListener){
        this.clickListener = clickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return isLoadingData() && position == getItemCount() -1 ? TYPE_LOAD : TYPE_DATA;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view;
        if (i == TYPE_DATA) {
            ItemFilmBinding filmBinding = ItemFilmBinding.inflate(inflater, viewGroup, false);
            return new FilmsHolder(filmBinding.getRoot());
        } else{
            view = inflater.inflate(R.layout.item_progress, viewGroup, false);
            return new ProgressViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (holder instanceof FilmsHolder){
            ((FilmsHolder) holder).onBind(getItem(i));
        }
    }

    public void setNetworkState(NetworkState networkState) {
        this.networkState = networkState;
        if (isLoadingData()) notifyItemRemoved(getItemCount());
        else notifyItemInserted(getItemCount());
    }

    private boolean isLoadingData(){
        return networkState != null && networkState != NetworkState.LOADED;
    }

    @BindingAdapter("app:imageUrlPoster")
    public static void setImage(ImageView poster, String url){
        GlideApp.with(poster.getContext())
                .load(url)
                .error(R.drawable.ic_launcher_foreground)
                .into(poster);
    }

    class FilmsHolder extends BaseBindingRecyclerHolder<ItemFilmBinding, Search> {
        FilmsHolder(View itemView) {
            super(itemView);
        }

        protected void onBind(Search data) {
            binding.setFilmItem(data);
            binding.setClickListener(clickListener);
            binding.executePendingBindings();
        }
    }

    private static class ProgressViewHolder extends RecyclerView.ViewHolder{
        ProgressViewHolder(View itemView) {
            super(itemView);
        }
    }
}

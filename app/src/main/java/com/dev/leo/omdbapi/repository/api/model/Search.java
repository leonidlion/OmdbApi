package com.dev.leo.omdbapi.repository.api.model;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Search {
    @SerializedName("Title")
    @Expose
    public String title;
    @SerializedName("Year")
    @Expose
    public String year;
    @SerializedName("imdbID")
    @Expose
    public String imdbID;
    @SerializedName("Type")
    @Expose
    public String type;
    @SerializedName("Poster")
    @Expose
    public String poster;

    public static DiffUtil.ItemCallback<Search> DIFF_CALLBACK = new DiffUtil.ItemCallback<Search>() {
        @Override
        public boolean areItemsTheSame(@NonNull Search search, @NonNull Search t1) {
            return search.getImdbID().equalsIgnoreCase(t1.getImdbID());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Search search, @NonNull Search t1) {
            return search.getImdbID().equalsIgnoreCase(t1.getImdbID());
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getImdbID() {
        return imdbID;
    }

    public void setImdbID(String imdbID) {
        this.imdbID = imdbID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}

package com.example.minh.qlthuvien.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Minh on 9/27/2018.
 */

public class Genre {

    @SerializedName("id")
    @Expose
    private int mId;
    @SerializedName("tentheloai")
    @Expose
    private String mGenreName;
    @SerializedName("hinhanh")
    @Expose
    private String mImageGenre;

    public Genre() {
    }

    public Genre(int mId, String mGenreName, String mImageGenre) {
        this.mId = mId;
        this.mGenreName = mGenreName;
        this.mImageGenre = mImageGenre;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getGenreName() {
        return mGenreName;
    }

    public void setGenreName(String genreName) {
        mGenreName = genreName;
    }

    public String getImageGenre() {
        return mImageGenre;
    }

    public void setImageGenre(String imageGenre) {
        mImageGenre = imageGenre;
    }
}

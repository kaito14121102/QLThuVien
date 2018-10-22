package com.example.minh.qlthuvien.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import static android.R.attr.id;

/**
 * Created by Minh on 9/28/2018.
 */

public class Book implements Serializable {
    @SerializedName("id")
    @Expose
    private int mId;
    @SerializedName("tensach")
    @Expose
    private String mName;
    @SerializedName("tentacgia")
    @Expose
    private String mAuthor;
    @SerializedName("idtheloai")
    @Expose
    private int mIdGenre;
    @SerializedName("tomtat")
    @Expose
    private String mSummary;
    @SerializedName("hinhanh")
    @Expose
    private String mImage;
    @SerializedName("soluong")
    @Expose
    private int mAmount;
    @SerializedName("thue")
    @Expose
    private int mHire;

    public Book() {
    }

    public Book(int id, String name, String author, int idGenre, String summary, String image, int amount, int hire) {
        mId = id;
        mName = name;
        mAuthor = author;
        mIdGenre = idGenre;
        mSummary = summary;
        mImage = image;
        mAmount = amount;
        mHire = hire;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public int getIdGenre() {
        return mIdGenre;
    }

    public void setIdGenre(int idGenre) {
        mIdGenre = idGenre;
    }

    public String getSummary() {
        return mSummary;
    }

    public void setSummary(String summary) {
        mSummary = summary;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public int getAmount() {
        return mAmount;
    }

    public void setAmount(int amount) {
        mAmount = amount;
    }

    public int getHire() {
        return mHire;
    }

    public void setHire(int hire) {
        mHire = hire;
    }
}

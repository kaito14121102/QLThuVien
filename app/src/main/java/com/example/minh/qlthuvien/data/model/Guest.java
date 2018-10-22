package com.example.minh.qlthuvien.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Minh on 10/1/2018.
 */

public class Guest {

    @SerializedName("mabandoc")
    @Expose
    private int mId;
    @SerializedName("tenbandoc")
    @Expose
    private String mName;
    @SerializedName("sdt")
    @Expose
    private int mPhone;

    public Guest(int id, String name, int phone) {
        mId = id;
        mName = name;
        mPhone = phone;
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

    public int getPhone() {
        return mPhone;
    }

    public void setPhone(int phone) {
        mPhone = phone;
    }
}

package com.example.minh.qlthuvien.data.remote.book;

import java.util.ArrayList;

/**
 * Created by Minh on 9/28/2018.
 */

public interface BookCallback<T> {
    void onSucess(ArrayList<T> data);

    void onError(Exception exception);
}

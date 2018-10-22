package com.example.minh.qlthuvien.data.remote.genre;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Minh on 9/28/2018.
 */

public interface GenreCallback<T> {
    void onSucess(ArrayList<T> data);

    void onError(Exception exception);
}

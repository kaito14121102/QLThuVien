package com.example.minh.qlthuvien.data;

import com.example.minh.qlthuvien.data.model.Genre;
import com.example.minh.qlthuvien.data.remote.genre.GenreCallback;

/**
 * Created by Minh on 9/28/2018.
 */

public interface GenreDataSource {
    interface RemoteGenreDataSource {
        void getData(GenreCallback<Genre> callback);
    }
}

package com.example.minh.qlthuvien.data.remote.genre;

import android.util.Log;

import com.example.minh.qlthuvien.data.GenreDataSource;
import com.example.minh.qlthuvien.data.model.Genre;
import com.example.minh.qlthuvien.data.remote.retrofit.APIUltils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Minh on 9/28/2018.
 */

public class GenreRemoteDataSource implements GenreDataSource.RemoteGenreDataSource {
    private static GenreRemoteDataSource sInstance;

    private GenreRemoteDataSource() {
    }

    public static GenreRemoteDataSource getInstance() {
        if (sInstance == null) {
            sInstance = new GenreRemoteDataSource();
        }
        return sInstance;
    }

    @Override
    public void getData(final GenreCallback<Genre> callback) {
        APIUltils.getSOService().getGenres().enqueue(new Callback<ArrayList<Genre>>() {
            @Override
            public void onResponse(Call<ArrayList<Genre>> call, Response<ArrayList<Genre>> response) {
                if(response.isSuccessful()){
                    callback.onSucess(response.body());
                }else {
                    Log.d("TAGG",response.toString());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Genre>> call, Throwable t) {

            }
        });
    }
}

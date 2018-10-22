package com.example.minh.qlthuvien.data.remote.book;

import android.util.Log;

import com.example.minh.qlthuvien.data.BookDataSource;
import com.example.minh.qlthuvien.data.model.Book;
import com.example.minh.qlthuvien.data.remote.retrofit.APIUltils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by Minh on 9/28/2018.
 */

public class BookRemotDataSource implements BookDataSource.RemoteBookDataSource {
    private static BookRemotDataSource mInstance;

    private BookRemotDataSource() {

    }

    public static BookRemotDataSource getInstance() {
        if (mInstance == null) {
            mInstance = new BookRemotDataSource();
        }
        return mInstance;
    }

    @Override
    public void getData(int id, final BookCallback<Book> callback) {
        APIUltils.getSOService().getBooks(id).enqueue(new Callback<ArrayList<Book>>() {
            @Override
            public void onResponse(Call<ArrayList<Book>> call, Response<ArrayList<Book>> response) {
                Log.d("TAGG","success");
                if(response.isSuccessful()){
                    callback.onSucess(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Book>> call, Throwable t) {

            }
        });
    }
}

package com.example.minh.qlthuvien.data;

import com.example.minh.qlthuvien.data.remote.book.BookCallback;
import com.example.minh.qlthuvien.data.remote.book.BookRemotDataSource;

/**
 * Created by Minh on 9/28/2018.
 */

public class BookRepository {
    private static BookRepository mInstance;
    private BookRemotDataSource mRemote;

    public BookRepository(BookRemotDataSource remote) {
        mRemote = remote;
    }

    public static BookRepository getInstance(BookRemotDataSource remotDataSource){
        if(mInstance == null){
            mInstance = new BookRepository(remotDataSource);
        }
        return mInstance;
    }

    public void getdata(int id,BookCallback callback){
        mRemote.getData(id,callback);
    }
}

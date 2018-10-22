package com.example.minh.qlthuvien.data;

import com.example.minh.qlthuvien.data.model.Book;
import com.example.minh.qlthuvien.data.remote.book.BookCallback;

/**
 * Created by Minh on 9/28/2018.
 */

public interface BookDataSource {
    interface RemoteBookDataSource {
        void getData(int id, BookCallback<Book> callback);
    }
}

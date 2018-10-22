package com.example.minh.qlthuvien.screen.bookgenre;

import com.example.minh.qlthuvien.data.model.Book;

import java.util.ArrayList;

/**
 * Created by Minh on 9/28/2018.
 */

public interface BookGenreContract {
    interface View {
        void onGetGenreSuccess(ArrayList<Book> books);
    }

    interface Presenter{
        void getBook(int id);
    }
}

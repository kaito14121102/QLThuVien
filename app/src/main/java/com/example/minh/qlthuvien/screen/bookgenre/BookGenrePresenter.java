package com.example.minh.qlthuvien.screen.bookgenre;

import com.example.minh.qlthuvien.data.BookRepository;
import com.example.minh.qlthuvien.data.remote.book.BookCallback;

import java.util.ArrayList;

/**
 * Created by Minh on 9/28/2018.
 */

public class BookGenrePresenter implements BookGenreContract.Presenter {

    private BookRepository mRepository;
    private BookGenreContract.View mView;

    public BookGenrePresenter(BookRepository repository, BookGenreContract.View view) {
        mRepository = repository;
        mView = view;
    }

    @Override
    public void getBook(int id) {
        mRepository.getdata(id,new BookCallback() {
            @Override
            public void onSucess(ArrayList data) {
                mView.onGetGenreSuccess(data);
            }

            @Override
            public void onError(Exception exception) {

            }
        });
    }
}

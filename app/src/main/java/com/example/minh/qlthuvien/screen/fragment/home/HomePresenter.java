package com.example.minh.qlthuvien.screen.fragment.home;

import com.example.minh.qlthuvien.data.GenreRepository;
import com.example.minh.qlthuvien.data.remote.genre.GenreCallback;

import java.util.ArrayList;

/**
 * Created by Minh on 9/28/2018.
 */

public class HomePresenter implements HomeContract.Presenter {
    private HomeContract.View mView;
    private GenreRepository mRepository;

    public HomePresenter(HomeContract.View view, GenreRepository repository) {
        mView = view;
        mRepository = repository;
    }

    @Override
    public void getGenre() {
        mRepository.getData(new GenreCallback() {
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

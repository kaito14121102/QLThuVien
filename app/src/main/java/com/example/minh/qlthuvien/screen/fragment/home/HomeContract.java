package com.example.minh.qlthuvien.screen.fragment.home;

import com.example.minh.qlthuvien.Ultil.BaseView;
import com.example.minh.qlthuvien.data.model.Genre;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Minh on 9/28/2018.
 */

public interface HomeContract {
    interface View {
        void onGetGenreSuccess(ArrayList<Genre> genres);
    }

    interface Presenter{
        void getGenre();
    }
}

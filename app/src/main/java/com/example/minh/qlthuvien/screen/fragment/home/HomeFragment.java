package com.example.minh.qlthuvien.screen.fragment.home;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.minh.qlthuvien.R;
import com.example.minh.qlthuvien.data.GenreRepository;
import com.example.minh.qlthuvien.data.model.Genre;
import com.example.minh.qlthuvien.data.remote.genre.GenreRemoteDataSource;
import com.example.minh.qlthuvien.screen.bookgenre.BookGenreActivity;

import java.util.ArrayList;

/**
 * Created by Minh on 9/27/2018.
 */

public class HomeFragment extends Fragment implements HomeContract.View, HomeAdapter.GenreOnClickItem {
    public static String TAG = "homefragment";
    private RecyclerView mRecycleGenre;
    private HomeAdapter mAdapter;
    private HomePresenter mPresenter;
    private GenreRemoteDataSource mRemote;
    private GenreRepository mRepository;

    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initData();
        initWidget(view);
        if (mPresenter != null) {
            mPresenter.getGenre();
        }
        return view;
    }

    private void initData() {
        mRemote = GenreRemoteDataSource.getInstance();
        mRepository = GenreRepository.getInstance(mRemote);
        mPresenter = new HomePresenter(this, mRepository);
    }

    private void initWidget(View view) {
        mAdapter = new HomeAdapter(getActivity(), this);
        mRecycleGenre = view.findViewById(R.id.reycle_view);
        mRecycleGenre.setHasFixedSize(true);
        mRecycleGenre.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecycleGenre.setAdapter(mAdapter);
    }

    @Override
    public void onGetGenreSuccess(ArrayList<Genre> genres) {
        if (genres != null) {
            mAdapter.addData(genres);
        }
    }

    @Override
    public void onClick(Genre genre) {
        startActivity(BookGenreActivity
                .getHomeIntent(getActivity(), genre.getId(), genre.getImageGenre(), genre.getGenreName()));
    }
}

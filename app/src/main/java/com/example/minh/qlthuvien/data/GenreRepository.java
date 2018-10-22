package com.example.minh.qlthuvien.data;

import com.example.minh.qlthuvien.data.remote.genre.GenreCallback;
import com.example.minh.qlthuvien.data.remote.genre.GenreRemoteDataSource;

/**
 * Created by Minh on 9/28/2018.
 */

public class GenreRepository {

    private static GenreRepository sIntance;
    private GenreRemoteDataSource mRemote;

    private GenreRepository(GenreRemoteDataSource remote) {
        mRemote = remote;
    }

    public static GenreRepository getInstance(GenreRemoteDataSource remoteDataSource){
        if(sIntance == null){
            sIntance = new GenreRepository(remoteDataSource);
        }
        return sIntance;
    }

    public void getData(GenreCallback callback){
        mRemote.getData(callback);
    }
}

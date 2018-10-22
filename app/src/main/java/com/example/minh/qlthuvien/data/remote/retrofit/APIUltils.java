package com.example.minh.qlthuvien.data.remote.retrofit;

import com.example.minh.qlthuvien.Ultil.Constant;

/**
 * Created by Minh on 9/28/2018.
 */

public class APIUltils {
    public static String BASE_URL = "http://172.20.10.2:2019/QLThuVien/";
//    public static String BASE_URL = "http://192.168.0.105:2019/QLThuVien/";

    public static SOService getSOService() {
        return RetrofitClient.getClient(BASE_URL).create(SOService.class);
    }
}

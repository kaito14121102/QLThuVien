package com.example.minh.qlthuvien.data.remote.retrofit;

import com.example.minh.qlthuvien.data.model.Book;
import com.example.minh.qlthuvien.data.model.Genre;
import com.example.minh.qlthuvien.data.model.Guest;

import java.util.ArrayList;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Minh on 9/28/2018.
 */

public interface SOService {
    @GET("gettheloai.php")
    Call<ArrayList<Genre>> getGenres();

    @GET("getguest.php")
    Call<ArrayList<Guest>> getGuests();

    @FormUrlEncoded
    @POST("getbookgenre.php")
    Call<ArrayList<Book>> getBooks(@Field("idloai") int loai);


    @GET("getallbook.php")
    Call<ArrayList<Book>> getAllBooks();

    @FormUrlEncoded
    @POST("insertbook.php")
    Call<ResponseBody> insertBook(@Field("tensach") String nameBook, @Field("tentacgia") String nameAuthor,
                                  @Field("idtheloai") int idGenre, @Field("tomtat") String summary,
                                  @Field("hinhanh") String image, @Field("soluong") int amount);

    @FormUrlEncoded
    @POST("deletebook.php")
    Call<ResponseBody> deleteBook(@Field("id") int id);

    @FormUrlEncoded
    @POST("updatebook.php")
    Call<ResponseBody> updateBook(@Field("idsach") int idsach, @Field("tensach") String nameBook,
                                  @Field("tentacgia") String nameAuthor,
                                  @Field("idtheloai") int idGenre, @Field("tomtat") String summary,
                                  @Field("soluong") int amount, @Field("thue") int hire);

    @FormUrlEncoded
    @POST("insertguest.php")
    Call<Guest> insertGuest(@Field("tenbandoc") String nameGuest, @Field("sdt") int phone);

    @FormUrlEncoded
    @POST("deleteguest.php")
    Call<ResponseBody> deleteGuest(@Field("mabandoc") int idGuest);

    @FormUrlEncoded
    @POST("insertbookhire.php")
    Call<ResponseBody> insertBookHire(@Field("mabandoc") int idGuest, @Field("masach") int idBook,
                                      @Field("ngaymuon") String dateHire, @Field("ngaytra") String date,
                                      @Field("soluong") int amount);

    @FormUrlEncoded
    @POST("getbookhiredetail.php")
    Call<ArrayList<Book>> getBookHireDetatil(@Field("mabandoc") int mabandoc);

    @FormUrlEncoded
    @POST("updatebookhire.php")
    Call<ResponseBody> updatebookhire(@Field("json") String json, @Field("mabandoc") int idGuest);

    @FormUrlEncoded
    @POST("deletebookhire.php")
    Call<ResponseBody> deletebookhire(@Field("mabandoc") int idGuest, @Field("masach") int idBook,
                                      @Field("soluong") int hire);
}

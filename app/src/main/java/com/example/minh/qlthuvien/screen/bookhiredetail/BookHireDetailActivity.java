package com.example.minh.qlthuvien.screen.bookhiredetail;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.minh.qlthuvien.R;
import com.example.minh.qlthuvien.data.model.Book;
import com.example.minh.qlthuvien.data.remote.retrofit.APIUltils;
import com.example.minh.qlthuvien.screen.bookhire.BookHireAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookHireDetailActivity extends AppCompatActivity implements BookHireDetailAdapter.OnClickButtonListen {
    private ArrayList<Book> mBooks;
    private RecyclerView mRecyclerView;
    private FloatingActionButton mButton;
    private BookHireDetailAdapter mAdapter;
    public static String ID_GUEST = "id_guest";
    private int mIdGuest;

    public static Intent getIntentBookDetailActivity(Context context, int id) {
        Intent intent = new Intent(context, BookHireDetailActivity.class);
        intent.putExtra(ID_GUEST, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_hire_detail);
        mIdGuest = getIntent().getIntExtra(ID_GUEST, 0);
        initRecycleView();
        initData();
        setEven();
    }

    private void setEven() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBooks.size() > 0) {
                    JSONArray jsonArray = new JSONArray();
                    for (int i = 0; i < mBooks.size(); i++) {
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("masach", mBooks.get(i).getId());
                            jsonObject.put("thue", mBooks.get(i).getHire());
                            jsonArray.put(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                        APIUltils.getSOService().updatebookhire(jsonArray.toString(), mIdGuest).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(BookHireDetailActivity.this, "Update Successfully", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }
                }
            });
        }

    private void initData() {
        mBooks = new ArrayList<>();
        APIUltils.getSOService().getBookHireDetatil(mIdGuest).enqueue(new Callback<ArrayList<Book>>() {
            @Override
            public void onResponse(Call<ArrayList<Book>> call, Response<ArrayList<Book>> response) {
                if (response.isSuccessful()) {
                    mBooks.addAll(response.body());
                    mAdapter.addData(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Book>> call, Throwable t) {

            }
        });
    }

    private void initRecycleView() {
        mButton = (FloatingActionButton) findViewById(R.id.button_done);
        mAdapter = new BookHireDetailAdapter(this, this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void clickButton(int position, int hire) {
        mBooks.get(position).setHire(hire);
    }

    @Override
    public void deleteBook(int hire, int idBook, final int position) {
        APIUltils.getSOService().deletebookhire(mIdGuest,idBook,hire).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    Toast.makeText(BookHireDetailActivity.this, "delete successfull", Toast.LENGTH_SHORT).show();
                    mAdapter.deleteBook(position);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(BookHireDetailActivity.this, "delete fail", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

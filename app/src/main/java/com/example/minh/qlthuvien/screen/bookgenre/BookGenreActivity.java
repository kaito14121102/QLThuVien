package com.example.minh.qlthuvien.screen.bookgenre;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.minh.qlthuvien.R;
import com.example.minh.qlthuvien.data.BookRepository;
import com.example.minh.qlthuvien.data.model.Book;
import com.example.minh.qlthuvien.data.remote.book.BookRemotDataSource;
import com.example.minh.qlthuvien.data.remote.retrofit.APIUltils;
import com.example.minh.qlthuvien.screen.bookdetail.BookDetailActivity;
import com.example.minh.qlthuvien.screen.bookdetailrepair.BookRepairActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookGenreActivity extends AppCompatActivity implements BookGenreContract.View, BookGenreAdapter.BookOnClickItem {
    public static String ID_GENRE = "id_genre";
    public static String IMAGE_GENRE = "image_genre";
    public static String NAME_GENRE = "name_genre";
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ImageView mImageGenre;
    private BookGenreAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public static Intent getHomeIntent(Context context, int id, String image, String name) {
        Intent intent = new Intent(context, BookGenreActivity.class);
        intent.putExtra(ID_GENRE, id);
        intent.putExtra(IMAGE_GENRE, image);
        intent.putExtra(NAME_GENRE, name);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_genre);
        initViews();
        initRecycleView();
        getTypeGenre();
    }

    private void initRecycleView() {
        mAdapter = new BookGenreAdapter(this, this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void initViews() {
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        mImageGenre = (ImageView) findViewById(R.id.image_genre);
    }

    private void getTypeGenre() {
        Intent intent = getIntent();
        int id = intent.getIntExtra(ID_GENRE, 0);
        String image = intent.getStringExtra(IMAGE_GENRE);
        String name = intent.getStringExtra(NAME_GENRE);
        mCollapsingToolbarLayout.setTitle(name);
        Picasso.with(this).load(image)
                .placeholder(R.drawable.bookdefault)
                .error(R.drawable.bookdefault)
                .into(mImageGenre);
        initData(id);
    }

    private void initData(int id) {
        BookRemotDataSource remotDataSource = BookRemotDataSource.getInstance();
        BookRepository bookRepository = BookRepository.getInstance(remotDataSource);
        BookGenrePresenter presenter = new BookGenrePresenter(bookRepository, this);
        presenter.getBook(id);
    }

    @Override
    public void onGetGenreSuccess(ArrayList<Book> books) {
        if (books != null) {
            mAdapter.addData(books);
        }
    }

    @Override
    public void onClick(Book book) {
        startActivity(BookDetailActivity.getIntentBook(this, book));
    }

    @Override
    public void OnMenuControl(Book book, int position, View view) {
        showMenu(view, book, position);
    }

    private void showMenu(View view, final Book book, final int position) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_control, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_repair:
                        startActivity(BookRepairActivity.getIntentBookRepair(
                                BookGenreActivity.this, book));
                        break;
                    case R.id.action_delete:
                        deleteBook(book.getId());
                        mAdapter.deleteBook(position);
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    public void deleteBook(int id) {
        APIUltils.getSOService().deleteBook(id).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(BookGenreActivity.this, "delete successfull", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
package com.example.minh.qlthuvien.screen.bookhire;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.minh.qlthuvien.R;
import com.example.minh.qlthuvien.data.model.Book;
import com.example.minh.qlthuvien.data.model.Guest;
import com.example.minh.qlthuvien.data.remote.retrofit.APIUltils;
import com.example.minh.qlthuvien.screen.bookdetailrepair.BookRepairActivity;
import com.example.minh.qlthuvien.screen.bookgenre.BookGenreActivity;
import com.example.minh.qlthuvien.screen.bookgenre.BookGenreAdapter;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.id.edit;

public class BookHireActivity extends AppCompatActivity implements BookHireAdapter.BookOnClickItem {
    public static final String GUEST_ID = "guest_id";
    private RecyclerView mRecyclerView;
    private BookHireAdapter mAdapter;
    private EditText mEditSearch;
    private ImageButton mImageSearch;
    private int mIdGuest;

    public static Intent getIntentBookHireActivity(Context context, int id) {
        Intent intent = new Intent(context, BookHireActivity.class);
        intent.putExtra(GUEST_ID, id);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_hire);
        mIdGuest = getIntent().getIntExtra(GUEST_ID, 0);
        initWidget();
        initRecycleView();
        getBooks();
        setEven();
    }

    private void setEven() {
        mEditSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mAdapter.getFilter().filter(charSequence);
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initRecycleView() {
        mAdapter = new BookHireAdapter(this, this);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
    }

    private void getBooks() {
        APIUltils.getSOService().getAllBooks().enqueue(new Callback<ArrayList<Book>>() {
            @Override
            public void onResponse(Call<ArrayList<Book>> call, Response<ArrayList<Book>> response) {
                mAdapter.addData(response.body());
            }

            @Override
            public void onFailure(Call<ArrayList<Book>> call, Throwable t) {

            }
        });
    }

    private void initWidget() {
        mRecyclerView = (RecyclerView) findViewById(R.id.reycle_view);
        mEditSearch = (EditText) findViewById(R.id.edit_search);
    }


    @Override
    public void onClick(Book book) {

    }

    @Override
    public void OnMenuControl(Book book, int position, View view) {
        showMenu(view, book, position);
    }

    private void showMenu(View view, final Book book, final int position) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.book_hire, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_save:
                        if (book.getAmount() - book.getHire() == 0) {
                            Toast.makeText(BookHireActivity.this, "Out of book", Toast.LENGTH_SHORT).show();
                        } else {
                            DialogHire(book.getId(), position, book.getAmount());
                        }
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    private void DialogHire(final int idBook, final int position, final int amount) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom_hire);
        dialog.setCanceledOnTouchOutside(false);
        // ánh xạ
        final EditText editDate = (EditText) dialog.findViewById(R.id.edit_date_return);
        final EditText editAmount = (EditText) dialog.findViewById(R.id.edit_amount);
        Button buttonAdd = (Button) dialog.findViewById(R.id.button_add_new);
        final Button buttonCancel = (Button) dialog.findViewById(R.id.button_cancel);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = editDate.getText().toString().trim();
                String amountText = editAmount.getText().toString().trim();
                String dateHire = "2018-06-10";
                if (!TextUtils.isEmpty(date) && !TextUtils.isEmpty(amountText)) {

                    final int amountHire = Integer.parseInt(amountText);
                    if (amount > amountHire) {
                        APIUltils.getSOService().insertBookHire(mIdGuest, idBook, dateHire, date,
                                amountHire).enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(BookHireActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                                    dialog.cancel();
                                    dialog.dismiss();
                                    mAdapter.updateBook(position,amountHire);
                                } else {
                                    Toast.makeText(BookHireActivity.this, "You hired this book", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {

                            }
                        });
                    }else {
                        Toast.makeText(BookHireActivity.this, "Not enough book", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BookHireActivity.this, "You need input all information", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}

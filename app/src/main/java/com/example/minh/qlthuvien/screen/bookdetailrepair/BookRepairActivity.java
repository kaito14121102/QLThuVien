package com.example.minh.qlthuvien.screen.bookdetailrepair;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.minh.qlthuvien.R;
import com.example.minh.qlthuvien.data.model.Book;
import com.example.minh.qlthuvien.data.remote.retrofit.APIUltils;
import com.example.minh.qlthuvien.screen.MainActivity;
import com.example.minh.qlthuvien.screen.bookdetail.BookDetailActivity;
import com.example.minh.qlthuvien.screen.bookgenre.BookGenreActivity;
import com.squareup.picasso.Picasso;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.minh.qlthuvien.R.drawable.book;

public class BookRepairActivity extends AppCompatActivity implements View.OnClickListener {
    private static String BOOK_DETAIL = "book_detail_repair";
    private EditText mEditAmount;
    private EditText mEditHire;
    private EditText mEditNameBook;
    private EditText mEditAuthor;
    private EditText mEditSummary;
    private ImageView mImageBook;
    private Spinner mSpinGenre;
    private Button mButtonSave;
    private Button mButtonCancel;
    private int mIdGenre;
    private int mIdBook;

    public static Intent getIntentBookRepair(Context context, Book book) {
        Intent intent = new Intent(context, BookRepairActivity.class);
        intent.putExtra(BOOK_DETAIL, book);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_repair);
        initWidget();
        initData();
    }

    private void initData() {
        Book book = (Book) getIntent().getSerializableExtra(BOOK_DETAIL);
        mEditAmount.setText(book.getAmount() + "");
        mEditHire.setText(book.getHire() + "");
        mEditNameBook.setText(book.getName());
        mEditAuthor.setText(book.getAuthor());
        mEditSummary.setText(book.getSummary());
        Picasso.with(this).load(book.getImage())
                .placeholder(R.drawable.book)
                .error(R.drawable.book)
                .into(mImageBook);
        setSpiner(book.getIdGenre());
        mIdBook = book.getId();
    }

    private void initWidget() {
        mEditNameBook = (EditText) findViewById(R.id.edit_name_book);
        mEditAuthor = (EditText) findViewById(R.id.edit_name_author);
        mImageBook = (ImageView) findViewById(R.id.image_book);
        mEditSummary = (EditText) findViewById(R.id.edit_summary);
        mEditAmount = (EditText) findViewById(R.id.edit_amount);
        mEditHire = (EditText) findViewById(R.id.edit_hire);
        mSpinGenre = (Spinner) findViewById(R.id.spin_genre);
        mButtonSave = (Button) findViewById(R.id.button_save);
        mButtonCancel = (Button) findViewById(R.id.button_cancel);
        mButtonSave.setOnClickListener(this);
        mButtonCancel.setOnClickListener(this);
    }

    private void setSpiner(int idGenre) {
        String[] genres = {"Comic Book", "Novel", "Science Book", "Litery"};
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, genres);
        mSpinGenre.setAdapter(arrayAdapter);
        mIdGenre = idGenre - 1;
        mSpinGenre.setSelection(mIdGenre);
        mSpinGenre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mIdGenre = i;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_save:
                updateBook();
                break;
            case R.id.button_cancel:
                finish();
                break;
            default:
                break;
        }
    }

    private void updateBook() {
        String name = mEditNameBook.getText().toString();
        String author = mEditAuthor.getText().toString();
        String amount = mEditAmount.getText().toString();
        String summary = mEditSummary.getText().toString();
        String hire = mEditHire.getText().toString();
        int checkAmount = Integer.parseInt(amount);
        int checkHire = Integer.parseInt(hire);
        APIUltils.getSOService().updateBook(mIdBook, name, author
                , mIdGenre + 1, summary, checkAmount, checkHire)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(BookRepairActivity.this, "Update sucessed", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(BookRepairActivity.this, MainActivity.class));
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(BookRepairActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

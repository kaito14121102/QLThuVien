package com.example.minh.qlthuvien.screen.bookdetail;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minh.qlthuvien.R;
import com.example.minh.qlthuvien.data.model.Book;
import com.example.minh.qlthuvien.screen.bookdetailrepair.BookRepairActivity;
import com.squareup.picasso.Picasso;

public class BookDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private static String BOOK_DETAIL = "book_detail";
    private TextView mNameBook;
    private TextView mAuthorBook;
    private TextView mSummaryBook;
    private TextView mAmountBook;
    private ImageView mImageBook;
    private Button mButtonHire;
    private Button mButtonRepair;
    private Book book;

    public static Intent getIntentBook(Context context, Book book) {
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra(BOOK_DETAIL, book);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);
        initWidget();
        getBookIntent();
    }

    private void initWidget() {
        mNameBook = (TextView) findViewById(R.id.text_name_book);
        mAuthorBook = (TextView) findViewById(R.id.text_author_book);
        mAmountBook = (TextView) findViewById(R.id.text_amount_book);
        mSummaryBook = (TextView) findViewById(R.id.text_summary_book);
        mImageBook = (ImageView) findViewById(R.id.image_book);
        mButtonHire = (Button) findViewById(R.id.button_hire);
        mButtonRepair = (Button) findViewById(R.id.button_repair);
        mButtonHire.setOnClickListener(this);
        mButtonRepair.setOnClickListener(this);
    }

    private void getBookIntent() {
        Intent intent = getIntent();
        book = (Book) intent.getSerializableExtra(BOOK_DETAIL);
        showDetailBook(book);
    }

    private void showDetailBook(Book book) {
        mNameBook.setText(book.getName());
        mAuthorBook.setText("By: " + book.getAuthor());
        mAmountBook.setText("Total: " + book.getAmount() + "  Hiring: " + book.getHire());
        mSummaryBook.setText(book.getSummary());
        Picasso.with(this).load(book.getImage())
                .placeholder(R.drawable.book)
                .error(R.drawable.book)
                .into(mImageBook);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button_repair:
                startActivity(BookRepairActivity.getIntentBookRepair(BookDetailActivity.this, book));
                break;
        }
    }
}

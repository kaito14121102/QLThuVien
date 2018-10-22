package com.example.minh.qlthuvien.screen.fragment.bookaddnew;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.minh.qlthuvien.R;
import com.example.minh.qlthuvien.data.model.Book;
import com.example.minh.qlthuvien.data.remote.retrofit.APIUltils;
import com.example.minh.qlthuvien.data.remote.retrofit.RetrofitClient;

import java.util.ArrayList;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Minh on 9/27/2018.
 */

public class BookFragment extends Fragment implements View.OnClickListener {
    public static String TAG = "bookfragment";
    private EditText mEditNameBook;
    private EditText mEditNameAuthor;
    private EditText mEditSummary;
    private EditText mEditImage;
    private EditText mEditAmount;
    private Spinner mSpinGenre;
    private Button mButtonAdd;
    private int mIdGenre;

    public static BookFragment newInstance() {
        BookFragment bookFragment = new BookFragment();
        return bookFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book, container, false);
        initWidget(view);
        return view;
    }

    private void initWidget(View view) {
        mEditNameBook = view.findViewById(R.id.edit_name_book);
        mEditNameAuthor = view.findViewById(R.id.edit_name_author);
        mEditImage = view.findViewById(R.id.edit_image);
        mEditSummary = view.findViewById(R.id.edit_summary);
        mEditAmount = view.findViewById(R.id.edit_amount);
        mSpinGenre = view.findViewById(R.id.spin_genre);
        mButtonAdd = view.findViewById(R.id.button_add);
        mButtonAdd.setOnClickListener(this);
        setSpiner();
    }

    private void setSpiner() {
        String[] genres = {"Comic Book", "Novel", "Science Book", "Litery"};
        ArrayAdapter<String> arrayAdapter =
                new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, genres);
        mSpinGenre.setAdapter(arrayAdapter);
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
        String name = mEditNameBook.getText().toString();
        String author = mEditNameAuthor.getText().toString();
        String amount = mEditAmount.getText().toString();
        String summary = mEditSummary.getText().toString();
        String image = mEditImage.getText().toString();
        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(author) && !TextUtils.isEmpty(amount)) {
            int checkamount = Integer.parseInt(amount);
            APIUltils.getSOService().insertBook(name, author, mIdGenre + 1, summary, image, checkamount)
                    .enqueue(new Callback<ResponseBody>() {
                        @Override
                        public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getActivity(), "Add new Successed", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseBody> call, Throwable t) {
                            Toast.makeText(getActivity(), "Add new Failed", Toast.LENGTH_SHORT).show();
                        }
                    });

        } else {
            Toast.makeText(getActivity(), "you need input name - author - amount", Toast.LENGTH_SHORT).show();
        }
    }
}

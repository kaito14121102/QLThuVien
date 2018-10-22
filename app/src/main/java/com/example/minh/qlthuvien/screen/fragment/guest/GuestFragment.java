package com.example.minh.qlthuvien.screen.fragment.guest;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.minh.qlthuvien.R;
import com.example.minh.qlthuvien.data.model.Book;
import com.example.minh.qlthuvien.data.model.Guest;
import com.example.minh.qlthuvien.data.remote.retrofit.APIUltils;
import com.example.minh.qlthuvien.screen.MainActivity;
import com.example.minh.qlthuvien.screen.bookdetailrepair.BookRepairActivity;
import com.example.minh.qlthuvien.screen.bookgenre.BookGenreActivity;
import com.example.minh.qlthuvien.screen.bookhire.BookHireActivity;
import com.example.minh.qlthuvien.screen.bookhiredetail.BookHireDetailActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.R.attr.id;
import static android.R.id.edit;
import static com.example.minh.qlthuvien.R.drawable.book;

/**
 * Created by Minh on 9/27/2018.
 */

public class GuestFragment extends Fragment implements GuestAdapter.OnClickItem {
    public static String TAG = "guestfragment";
    private FloatingActionButton mButtonAdd;
    private GuestAdapter mAdapter;
    private RecyclerView mRecyclerView;

    public static GuestFragment newInstance() {
        GuestFragment guestFragment = new GuestFragment();
        return guestFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_guest, container, false);
        initWidget(view);
        initRecyleView(view);
        return view;
    }

    private void initRecyleView(View view) {
        mAdapter = new GuestAdapter(getActivity(), this);
        mRecyclerView = view.findViewById(R.id.recycle_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setAdapter(mAdapter);
        initData();
    }

    private void initData() {
        APIUltils.getSOService().getGuests().enqueue(new Callback<ArrayList<Guest>>() {
            @Override
            public void onResponse(Call<ArrayList<Guest>> call, Response<ArrayList<Guest>> response) {
                if (response.isSuccessful()) {
                    mAdapter.addData(response.body());
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Guest>> call, Throwable t) {

            }
        });
    }

    private void initWidget(View view) {
        mButtonAdd = view.findViewById(R.id.button_add_guest);
        mButtonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogAddNewGuest();
            }
        });
    }

    private void DialogAddNewGuest() {
        final Dialog dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom_guest);
        dialog.setCanceledOnTouchOutside(false);
        // ánh xạ
        final EditText editName = (EditText) dialog.findViewById(R.id.edit_name_guest);
        final EditText editPhone = (EditText) dialog.findViewById(R.id.edit_phone_guest);
        Button buttonAdd = (Button) dialog.findViewById(R.id.button_add_new);
        Button buttonCancel = (Button) dialog.findViewById(R.id.button_cancel_guest);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString().trim();
                String phone = editPhone.getText().toString().trim();
                if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(phone)) {
                    APIUltils.getSOService().insertGuest(name, Integer.parseInt(phone)).enqueue(new Callback<Guest>() {
                        @Override
                        public void onResponse(Call<Guest> call, Response<Guest> response) {
                            mAdapter.addData1(response.body());
                            dialog.cancel();
                            dialog.dismiss();
                        }

                        @Override
                        public void onFailure(Call<Guest> call, Throwable t) {

                        }
                    });
                } else {
                    Toast.makeText(getActivity(), "fail", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void showMenu(View view, final int position, final Guest guest) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_control1, popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.action_hire:
                        startActivity(BookHireActivity.getIntentBookHireActivity(getActivity(), guest.getId()));
                        break;
                    case R.id.action_delete:
                        final ArrayList<Book> mBooks = new ArrayList<>();
                        APIUltils.getSOService().getBookHireDetatil(guest.getId()).enqueue(new Callback<ArrayList<Book>>() {
                            @Override
                            public void onResponse(Call<ArrayList<Book>> call, Response<ArrayList<Book>> response) {
                                if (response.isSuccessful()) {
                                    mBooks.addAll(response.body());
                                    deleteGuest(mBooks,guest.getId(),position);
                                }
                            }

                            @Override
                            public void onFailure(Call<ArrayList<Book>> call, Throwable t) {

                            }
                        });
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    @Override
    public void OnMenuControl(Guest guest, int positon, View view) {
        showMenu(view, positon, guest);
    }

    @Override
    public void OnClickItem(int idGuest) {
        startActivity(BookHireDetailActivity.getIntentBookDetailActivity(getActivity(), idGuest));
    }

    public void deleteGuest(ArrayList<Book> books, int id, final int position){
        if (books.size() == 0) {
                            APIUltils.getSOService().deleteGuest(id).enqueue(new Callback<ResponseBody>() {
                                @Override
                                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                    if (response.isSuccessful()) {
                                        mAdapter.deleteGuest(position);
                                    }
                                }

                                @Override
                                public void onFailure(Call<ResponseBody> call, Throwable t) {

                                }
                            });
        } else {
            Toast.makeText(getActivity(), "Can't delete guest", Toast.LENGTH_SHORT).show();
        }
    }
}



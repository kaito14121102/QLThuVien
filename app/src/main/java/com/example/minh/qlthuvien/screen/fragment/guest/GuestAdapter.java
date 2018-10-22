package com.example.minh.qlthuvien.screen.fragment.guest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minh.qlthuvien.R;
import com.example.minh.qlthuvien.data.model.Guest;

import java.util.ArrayList;

/**
 * Created by Minh on 10/1/2018.
 */

public class GuestAdapter extends RecyclerView.Adapter<GuestAdapter.ItemHolder> {
    private Context mContext;
    private ArrayList<Guest> mGuests;
    private OnClickItem mListener;

    public GuestAdapter(Context mContext, OnClickItem listener) {
        this.mContext = mContext;
        mGuests = new ArrayList<>();
        mListener = listener;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_guest, null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        Guest guest = mGuests.get(position);
        holder.mTextId.setText(guest.getId() + "");
        holder.mTextName.setText(guest.getName());
    }

    @Override
    public int getItemCount() {
        return mGuests.size();
    }

    public void addData(ArrayList<Guest> guests) {
        mGuests.addAll(guests);
        notifyDataSetChanged();
    }


    public void addData1(Guest guests) {
        mGuests.add(guests);
        notifyDataSetChanged();
    }

    public void deleteGuest(int position) {
        mGuests.remove(position);
        notifyDataSetChanged();
    }

    interface OnClickItem {
        void OnMenuControl(Guest guest, int position, View view);

        void OnClickItem(int idGuest);
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextId;
        private TextView mTextName;
        private ImageView mImageDot;
        private ImageView mImageAva;

        public ItemHolder(View itemView) {
            super(itemView);
            mTextId = itemView.findViewById(R.id.text_id_guest);
            mTextName = itemView.findViewById(R.id.text_name_guest);
            mImageDot = itemView.findViewById(R.id.image_dot);
            mImageAva = itemView.findViewById(R.id.image_book);
            mImageDot.setOnClickListener(this);
            mImageAva.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.image_dot:
                    mListener.OnMenuControl(mGuests.get(getAdapterPosition()), getAdapterPosition(), mImageDot);
                    break;
                case R.id.image_book:
                    mListener.OnClickItem(mGuests.get(getAdapterPosition()).getId());
                    break;
            }
        }
    }
}

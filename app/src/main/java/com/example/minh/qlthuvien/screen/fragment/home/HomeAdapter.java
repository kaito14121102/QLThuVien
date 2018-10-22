package com.example.minh.qlthuvien.screen.fragment.home;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minh.qlthuvien.R;
import com.example.minh.qlthuvien.data.model.Genre;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Minh on 9/27/2018.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ItemHolder> {
    private Context mContext;
    private ArrayList<Genre> mGenres;
    private GenreOnClickItem mListener;

    public HomeAdapter(Context mContext, GenreOnClickItem listener) {
        this.mContext = mContext;
        mGenres = new ArrayList<>();
        mListener = listener;
    }

    public void addData(ArrayList<Genre> genres) {
        mGenres.addAll(genres);
        notifyDataSetChanged();
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_genre, null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        Genre genre = mGenres.get(position);
        holder.textGenre.setText(genre.getGenreName());
        Picasso.with(mContext).load(genre.getImageGenre())
                .placeholder(R.drawable.bookdefault)
                .error(R.drawable.bookdefault)
                .into(holder.imageGenre);
    }

    @Override
    public int getItemCount() {
        return mGenres.size();
    }

    public interface GenreOnClickItem {
        void onClick(Genre genre);
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textGenre;
        public ImageView imageGenre;

        public ItemHolder(View itemView) {
            super(itemView);
            textGenre = itemView.findViewById(R.id.text_genre);
            imageGenre = itemView.findViewById(R.id.image_genre);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mListener.onClick(mGenres.get(getAdapterPosition()));
        }
    }
}

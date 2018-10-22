package com.example.minh.qlthuvien.screen.bookgenre;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minh.qlthuvien.R;
import com.example.minh.qlthuvien.data.model.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Minh on 9/28/2018.
 */

public class BookGenreAdapter extends RecyclerView.Adapter<BookGenreAdapter.ItemHolder> {
    private ArrayList<Book> mBooks;
    private Context mContext;
    private BookOnClickItem mListener;

    public BookGenreAdapter(Context context, BookOnClickItem listener) {
        mContext = mContext;
        mBooks = new ArrayList<>();
        mListener = listener;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_genre, null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        Book book = mBooks.get(position);
        holder.mNameBook.setText(book.getName());
        holder.mAuthorBook.setText(book.getAuthor());
        Picasso.with(mContext).load(book.getImage())
                .placeholder(R.drawable.book)
                .error(R.drawable.book)
                .into(holder.mImageBook);
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public void addData(ArrayList<Book> books) {
        mBooks.addAll(books);
        notifyDataSetChanged();
    }

    public void deleteBook(int position) {
        mBooks.remove(position);
        notifyDataSetChanged();
    }

    public interface BookOnClickItem {
        public void onClick(Book book);

        public void OnMenuControl(Book book, int position, View view);
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageBook;
        private TextView mNameBook, mAuthorBook;
        private ImageView mImageDot;

        public ItemHolder(View itemView) {
            super(itemView);
            mImageBook = itemView.findViewById(R.id.image_book);
            mNameBook = itemView.findViewById(R.id.text_name_book);
            mAuthorBook = itemView.findViewById(R.id.text_author_book);
            mImageDot = itemView.findViewById(R.id.image_dot);
            mImageDot.setOnClickListener(this);
            mImageBook.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.image_book:
                    mListener.onClick(mBooks.get(getAdapterPosition()));
                    break;
                case R.id.image_dot:
                    mListener.OnMenuControl(mBooks.get(getAdapterPosition()),
                            getAdapterPosition(), mImageDot);
                    break;
            }
        }
    }
}

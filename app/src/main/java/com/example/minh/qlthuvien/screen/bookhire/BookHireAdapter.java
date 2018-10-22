package com.example.minh.qlthuvien.screen.bookhire;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minh.qlthuvien.R;
import com.example.minh.qlthuvien.data.model.Book;
import com.example.minh.qlthuvien.screen.bookgenre.BookGenreAdapter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Minh on 9/28/2018.
 */

public class BookHireAdapter extends RecyclerView.Adapter<BookHireAdapter.ItemHolder> implements Filterable {
    private ArrayList<Book> mBooks;
    private Context mContext;
    private BookOnClickItem mListener;
    private ArrayList<Book> backupdata;

    public BookHireAdapter(Context context, BookOnClickItem listener) {
        mContext = context;
        mBooks = new ArrayList<>();
        mListener = listener;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_hire, null);
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
        holder.mAmount.setText("exist: " + (book.getAmount() - book.getHire()));
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

    public void updateBook(int position,int amountHire){
        int oldHire = mBooks.get(position).getHire();
        mBooks.get(position).setHire(oldHire+amountHire);
        notifyDataSetChanged();
    }
    @Override
    public Filter getFilter() {
        Filter f = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                FilterResults fr = new FilterResults();
                if (backupdata == null) backupdata = new ArrayList<>(mBooks);
                if (charSequence == null) backupdata = new ArrayList<>(mBooks);
                if (charSequence == null || charSequence.length() == 0) {
                    fr.values = backupdata;
                    fr.count = backupdata.size();
                } else {

                    ArrayList<Book> newdata = new ArrayList<>();
                    for (Book c : mBooks)
                        if (c.getName().toLowerCase().contains(charSequence.toString().toLowerCase()))
                            newdata.add(c);
                    fr.values = newdata;
                    fr.count = mBooks.size();
                }
                return fr;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mBooks = (ArrayList<Book>) filterResults.values;
                notifyDataSetChanged();
            }
        };
        return f;
    }

    public interface BookOnClickItem {
        public void onClick(Book book);

        public void OnMenuControl(Book book, int position, View view);
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageBook;
        private CheckBox mCheckBox;
        private TextView mNameBook, mAuthorBook, mAmount;
        private ImageView mImageDot;

        public ItemHolder(View itemView) {
            super(itemView);
            mImageBook = itemView.findViewById(R.id.image_book);
            mNameBook = itemView.findViewById(R.id.text_name_book);
            mAuthorBook = itemView.findViewById(R.id.text_author_book);
            mImageDot = itemView.findViewById(R.id.image_dot);
            mCheckBox = itemView.findViewById(R.id.checkbox);
            mAmount = itemView.findViewById(R.id.text_amount);
            mImageDot.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.image_dot:
                    mListener.OnMenuControl(mBooks.get(getAdapterPosition()),
                            getAdapterPosition(), mImageDot);
                    break;
            }
        }
    }
}

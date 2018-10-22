package com.example.minh.qlthuvien.screen.bookhiredetail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.minh.qlthuvien.R;
import com.example.minh.qlthuvien.data.model.Book;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Minh on 10/6/2018.
 */

public class BookHireDetailAdapter extends RecyclerView.Adapter<BookHireDetailAdapter.ItemHolder> {
    private Context mContext;
    private ArrayList<Book> mBooks;
    private OnClickButtonListen mListener;

    public BookHireDetailAdapter(Context context, OnClickButtonListen listener) {
        mBooks = new ArrayList<>();
        mContext = context;
        mListener = listener;
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_hire_detail, null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        Book book = mBooks.get(position);
        Picasso.with(mContext).load(book.getImage())
                .placeholder(R.drawable.book)
                .error(R.drawable.book)
                .into(holder.mImageBook);
        holder.mTextName.setHint(book.getName());
        holder.mTextAuthor.setHint(book.getAuthor());
        holder.mTextHire.setText(book.getHire() + "");
        holder.mTextExist.setText("exist: " + book.getAmount());
    }

    @Override
    public int getItemCount() {
        return mBooks.size();
    }

    public void addData(ArrayList<Book> books) {
        mBooks.addAll(books);
        notifyDataSetChanged();
    }

    public void deleteBook(int position){
        mBooks.remove(position);
        notifyDataSetChanged();
    }
    public interface OnClickButtonListen {
        void clickButton(int position, int hire);

        void deleteBook(int hire, int idBook, int position);
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageBook, mImageDelete;
        private TextView mTextName, mTextAuthor, mTextHire, mTextExist;
        private ImageButton mButtonMinus, mButtonPlus;

        public ItemHolder(View itemView) {
            super(itemView);
            mImageBook = itemView.findViewById(R.id.image_book);
            mImageDelete = itemView.findViewById(R.id.image_delete);
            mTextName = itemView.findViewById(R.id.text_name_book);
            mTextAuthor = itemView.findViewById(R.id.text_author_book);
            mTextHire = itemView.findViewById(R.id.text_hire);
            mTextExist = itemView.findViewById(R.id.text_exist);
            mButtonMinus = itemView.findViewById(R.id.button_minus);
            mButtonPlus = itemView.findViewById(R.id.button_plus);
            mButtonMinus.setOnClickListener(this);
            mButtonPlus.setOnClickListener(this);
            mImageDelete.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.button_minus:
                    int oldHire = Integer.parseInt(mTextHire.getText().toString().trim());
                    if (oldHire == 0) {
                        mButtonMinus.setEnabled(false);
                    } else {
                        oldHire--;
                        mTextHire.setText(oldHire + "");
                        mButtonPlus.setEnabled(true);
                        mListener.clickButton(getAdapterPosition(), oldHire);
                    }
                    break;
                case R.id.button_plus:
                    int oldHire1 = Integer.parseInt(mTextHire.getText().toString().trim());
                    if (oldHire1 == mBooks.get(getAdapterPosition()).getAmount()) {
                        mButtonPlus.setEnabled(false);
                    } else {
                        oldHire1++;
                        mTextHire.setText(oldHire1 + "");
                        mButtonMinus.setEnabled(true);
                        mListener.clickButton(getAdapterPosition(), oldHire1);
                    }
                    break;
                case R.id.image_delete:
                    mListener.deleteBook(mBooks.get(getAdapterPosition()).getHire(),
                            mBooks.get(getAdapterPosition()).getId(), getAdapterPosition());
                    break;
            }
        }
    }
}

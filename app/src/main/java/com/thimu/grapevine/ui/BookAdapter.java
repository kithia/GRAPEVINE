package com.thimu.grapevine.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thimu.grapevine.R;

import java.util.ArrayList;
import java.util.List;

/**
 * The book adapter
 *
 * @author Obed Ngigi
 * @version 04.07.2020
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {

    //
    private List<Book> books = new ArrayList<>();

    /**
     * Create the adapter
     * @param parent
     * @param viewType
     * @return the adapter view
     */
    @NonNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.book_item, parent, false);
        return new BookHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {
        Book currentBook = books.get(position);
        holder.textViewPublisher.setText(currentBook.getPublisher());
        holder.textViewTitle.setText(currentBook.getTitle());
        holder.textViewAuthor.setText(currentBook.getAuthor());
        holder.textViewGenre.setText(currentBook.getGenre());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public void setBooks(List<Book> books) {
        this.books = books;
        notifyDataSetChanged();
    }

    class BookHolder extends RecyclerView.ViewHolder {
        private TextView textViewPublisher;
        private TextView textViewTitle;
        private TextView textViewAuthor;
        private TextView textViewGenre;

        public BookHolder(@NonNull View itemView) {
            super(itemView);
            textViewPublisher = itemView.findViewById(R.id.bookPublisher);
            textViewTitle = itemView.findViewById(R.id.bookTitle);
            textViewAuthor = itemView.findViewById(R.id.bookAuthor);
            textViewGenre = itemView.findViewById(R.id.bookGenre);

        }
    }
}

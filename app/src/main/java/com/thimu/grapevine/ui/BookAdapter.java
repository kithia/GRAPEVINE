package com.thimu.grapevine.ui;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.textview.MaterialTextView;
import com.thimu.grapevine.BookDetailActivity;
import com.thimu.grapevine.R;
import com.thimu.grapevine.ui.reads.ReadsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * The book adapter
 *
 * @author Kĩthia Ngigĩ
 * @version 30.07.2020
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> implements Filterable {

    //
    private List<Book> books = new ArrayList<>();
    private List<Book> allBooks;

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
                .inflate(R.layout.fragment_reads_book_item, parent, false);
        return new BookHolder(itemView); }

    @Override
    public void onBindViewHolder(@NonNull BookHolder holder, int position) {
        Book currentBook = books.get(position);

        holder.imageViewCover.setImageResource(currentBook.getCover());
        holder.textViewPublisher.setText(currentBook.getPublisher());
        holder.textViewPublishDate.setText(String.valueOf(currentBook.getPublishDate()));
        holder.textViewTitle.setText(currentBook.getTitle());
        holder.textViewAuthor.setText(currentBook.getAuthors());
        holder.textViewGenre.setText(currentBook.getGenre());
        if (currentBook.getPages() == 0 && !currentBook.getRead()) {
            holder.progressBar.setMax(1000);
            holder.progressBar.setProgress(0, true); }
        else if (currentBook.getPages() == 0 && currentBook.getRead()) {
            holder.progressBar.setMax(1000);
            holder.progressBar.setProgress(1000, true); }
        else {
            holder.progressBar.setMax(currentBook.getPages());
            holder.progressBar.setProgress(currentBook.getPagesRead(), true); } }

    @Override
    public int getItemCount() {
        return books.size(); }

    public void setBooks(List<Book> books) {
        this.books = books;
        allBooks = new ArrayList<>(books);
        notifyDataSetChanged(); }

    public Book getBookAt(int position) {
        return books.get(position); }

    @Override
    public Filter getFilter() {
        return bookFilter; }

    private Filter bookFilter = new Filter() {
        // Runs in background thread
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            List<Book> filteredList = new ArrayList<>();

            if (charSequence.toString().trim().isEmpty()) {
                filteredList.addAll(allBooks); }
            else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Book book : allBooks) {
                    if (book.getTitle().toLowerCase().contains(filterPattern) ||
                            book.getAuthors().toLowerCase().contains(filterPattern)) {
                        filteredList.add(book); } } }

            // The result of this filtering operation is passed to the UI thread below
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results; }

        // Runs in UI thread
        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            books.clear();
            books.addAll((List) filterResults.values);
            notifyDataSetChanged(); } };

    class BookHolder extends RecyclerView.ViewHolder {

        //
        private final Context context;

        private MaterialCardView cardViewBook;

        private ImageView imageViewCover;
        private MaterialTextView textViewPublisher;
        private MaterialTextView textViewPublishDate;
        private MaterialTextView textViewTitle;
        private MaterialTextView textViewAuthor;
        private MaterialTextView textViewGenre;
        private ProgressBar progressBar;

        public BookHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();

            cardViewBook = itemView.findViewById(R.id.readsCardView);

            imageViewCover = itemView.findViewById(R.id.bookCover);
            textViewPublisher = itemView.findViewById(R.id.bookPublisher);
            textViewPublishDate = itemView.findViewById(R.id.bookPublishDate);
            textViewTitle = itemView.findViewById(R.id.bookTitle);
            textViewAuthor = itemView.findViewById(R.id.bookAuthor);
            textViewGenre = itemView.findViewById(R.id.bookGenre);
            progressBar = itemView.findViewById(R.id.bookProgress);

            cardViewBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (cardViewBook.isChecked() || progressBar.isIndeterminate()) {
                        cardViewBook.setChecked(false);
                        progressBar.setIndeterminate(false); }
                    else {
                        Intent intent = new Intent(context, BookDetailActivity.class);
                        intent.putExtra(ReadsFragment.EXTRA_BOOK, getBookAt(getAdapterPosition()));
                        context.startActivity(intent); } } });

            cardViewBook.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    cardViewBook.setChecked(!cardViewBook.isChecked());
                    progressBar.setIndeterminate(!progressBar.isIndeterminate());
                    return true; } }); }
    }
}

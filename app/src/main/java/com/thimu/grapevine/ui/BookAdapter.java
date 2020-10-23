package com.thimu.grapevine.ui;

import android.app.Activity;
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
import com.thimu.grapevine.MainActivity;
import com.thimu.grapevine.ManualAddEditBookActivity;
import com.thimu.grapevine.R;
import com.thimu.grapevine.ui.reads.ReadsFragment;

import java.util.ArrayList;
import java.util.List;

import static com.thimu.grapevine.ui.reads.ReadsFragment.EDIT_BOOK_REQUEST;

/**
 * The book adapter
 *
 * @author Kĩthia Ngigĩ
 * @version 05.08.2020
 */
public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> implements Filterable {

    //
    private List<Book> books = new ArrayList<>();
    private List<Book> allBooks;

    // private OnItemClickListener listener;

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
        holder.textViewPublishDate.setText(currentBook.getPublishDate());
        holder.textViewTitle.setText(currentBook.getTitle());
        holder.textViewAuthor.setText(currentBook.getAuthors());
        holder.textViewGenre.setText(currentBook.getGenre());
        if (currentBook.getPages() == 0) {
            holder.progressbar.setMax(1000);
            if (currentBook.getRead()) { holder.progressbar.setProgress(1000, true); }
            else { holder.progressbar.setProgress(0, true); } }
        else {
            holder.progressbar.setMax(currentBook.getPages());
            holder.progressbar.setProgress(currentBook.getPagesRead(), true); } }

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

        private ImageView imageViewCover;
        private MaterialTextView textViewPublisher;
        private MaterialTextView textViewPublishDate;
        private MaterialTextView textViewTitle;
        private MaterialTextView textViewAuthor;
        private MaterialTextView textViewGenre;
        private ProgressBar progressbar;

        public BookHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();

            MaterialCardView cardViewBook = itemView.findViewById(R.id.readsCardView);

            imageViewCover = itemView.findViewById(R.id.bookCover);
            textViewPublisher = itemView.findViewById(R.id.bookPublisher);
            textViewPublishDate = itemView.findViewById(R.id.bookPublishDate);
            textViewTitle = itemView.findViewById(R.id.bookTitle);
            textViewAuthor = itemView.findViewById(R.id.bookAuthor);
            textViewGenre = itemView.findViewById(R.id.bookGenre);
            progressbar = itemView.findViewById(R.id.bookProgress);

            /* itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(books.get(position));
                    }
                }
            }); */

            cardViewBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (getAdapterPosition() != RecyclerView.NO_POSITION) {
                        if (progressbar.isIndeterminate()) { progressbar.setIndeterminate(false); }
                        else {
                            Intent intent = new Intent(context, BookDetailActivity.class);
                            intent.putExtra(ReadsFragment.EXTRA_BOOK, getBookAt(getAdapterPosition()));
                            context.startActivity(intent); } } } });

            cardViewBook.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    progressbar.setIndeterminate(!progressbar.isIndeterminate());

                    /* Intent intent = new Intent(context, ManualAddEditBookActivity.class);
                    Book book = getBookAt(getAdapterPosition());
                    intent.putExtra(ManualAddEditBookActivity.EXTRA_IDENTIFICATION, book.getIdentification());
                    intent.putExtra(ManualAddEditBookActivity.EXTRA_ISBN, book.getISBN());
                    intent.putExtra(ManualAddEditBookActivity.EXTRA_COVER, book.getCover());
                    intent.putExtra(ManualAddEditBookActivity.EXTRA_PUBLISHER, book.getPublisher());
                    intent.putExtra(ManualAddEditBookActivity.EXTRA_PUBLISH_DATE, book.getPublishDate());
                    intent.putExtra(ManualAddEditBookActivity.EXTRA_TITLE, book.getTitle());
                    intent.putExtra(ManualAddEditBookActivity.EXTRA_AUTHORS, book.getAuthors());
                    intent.putExtra(ManualAddEditBookActivity.EXTRA_GENRE, book.getGenre());
                    intent.putExtra(ManualAddEditBookActivity.EXTRA_SUMMARY, book.getSummary());
                    intent.putExtra(ManualAddEditBookActivity.EXTRA_LANGUAGE, book.getLanguage());
                    intent.putExtra(ManualAddEditBookActivity.EXTRA_FORMAT, book.getFormat());
                    intent.putExtra(ManualAddEditBookActivity.EXTRA_PAGES, book.getPages());
                    intent.putExtra(ManualAddEditBookActivity.EXTRA_PAGES_READ, book.getPagesRead());
                    intent.putExtra(ManualAddEditBookActivity.EXTRA_READ, book.getRead());
                    ((Activity) context).startActivityForResult(intent, EDIT_BOOK_REQUEST); */
                    return true; } }); }
    }

    /* public interface OnItemClickListener {
        void onItemClick(Book book);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    } */
}

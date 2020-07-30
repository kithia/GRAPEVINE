package com.thimu.grapevine;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.thimu.grapevine.ui.Book;
import com.thimu.grapevine.ui.reads.ReadsFragment;

import java.util.Objects;

/**
 * An activity to display the details of a book
 *
 * @author Kĩthia Ngigĩ
 * @version 30.07.2020
 */
public class BookDetailActivity extends AppCompatActivity {

    /**
     * Create the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        Intent intent = getIntent();
        Book book = intent.getParcelableExtra(ReadsFragment.EXTRA_BOOK);

        int bookDetailCover = Objects.requireNonNull(book).getCover();
        String bookDetailGenre = book.getGenre();
        String bookDetailTitle = book.getTitle();
        String bookDetailAuthors = book.getAuthors();

        int bookDetailPages = book.getPages();
        int bookDetailFormat = book.getFormat();
        String bookDetailLanguage = book.getLanguage();
        String bookDetailPublishDate = book.getPublishDate();

        // Elements of the activity
        Toolbar toolbar = findViewById(R.id.bookDetailToolbar);
        setSupportActionBar(toolbar);

        ImageView imageViewCoverLarge = findViewById(R.id.bookDetailCoverLarge);
        ImageView imageViewCover = findViewById(R.id.bookDetailCover);
        TextView textViewGenre = findViewById(R.id.bookDetailGenre);
        TextView textViewTitle = findViewById(R.id.bookDetailTitle);
        TextView textViewAuthors = findViewById(R.id.bookDetailAuthors);

        TextView textViewPages = findViewById(R.id.bookDetailPages);
        TextView textViewFormat = findViewById(R.id.bookDetailFormat);
        TextView textViewLanguage = findViewById(R.id.bookDetailLanguage);
        TextView textViewPublishDate = findViewById(R.id.bookDetailPublishDate);

        /* TextView textViewPagesTitle = findViewById(R.id.bookDetailPagesTitle);
        TextView textViewFormatTitle = findViewById(R.id.bookDetailFormatTitle);
        TextView textViewLanguageTitle = findViewById(R.id.bookDetailLanguageTitle);
        TextView textViewPublishDateTitle = findViewById(R.id.bookDetailPublishDateTitle); */

        imageViewCoverLarge.setImageResource(bookDetailCover);
        imageViewCover.setImageResource(bookDetailCover);
        textViewGenre.setText(bookDetailGenre);
        textViewTitle.setText(bookDetailTitle);
        textViewAuthors.setText(bookDetailAuthors);

        if (bookDetailPages == 0) { textViewPages.setText(getString(R.string.uc_n_a)); }
        else { textViewPages.setText(String.valueOf(bookDetailPages)); }

        if (bookDetailFormat == R.string.paperback) {
            textViewFormat.setText(getString(R.string.paperback)); }
        else if (bookDetailFormat == R.string.hardcover) {
            textViewFormat.setText(getString(R.string.hardcover)); }
        else if (bookDetailFormat == R.string.ebook) {
            textViewFormat.setText(getString(R.string.ebook)); }
        else if (bookDetailFormat == R.string.audiobook) {
            textViewFormat.setText(getString(R.string.audiobook)); }
        else if (bookDetailFormat == R.string.compact_disk) {
            textViewFormat.setText(getString(R.string.compact_disk)); }
        else if (bookDetailFormat == R.string.non_traditional) {
            textViewFormat.setText(getString(R.string.non_traditional)); }
        else { textViewFormat.setText(getString(R.string.uc_n_a)); }

        if (bookDetailLanguage.isEmpty()) { textViewLanguage.setText(getString(R.string.uc_n_a)); }
        else { textViewLanguage.setText(bookDetailLanguage); }

        if (bookDetailPublishDate == null) { textViewPublishDate.setText(getString(R.string.uc_n_a)); }
        else { textViewPublishDate.setText(bookDetailPublishDate); }
    }

}
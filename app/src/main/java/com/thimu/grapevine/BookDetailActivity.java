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
 * @version 24.07.2020
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
        TextView textViewLanguage = findViewById(R.id.bookDetailLanguage);
        TextView textViewPublishDate = findViewById(R.id.bookDetailPublishDate);

        /* TextView textViewPagesTitle = findViewById(R.id.bookDetailPagesTitle);
        TextView textViewLanguageTitle = findViewById(R.id.bookDetailLanguageTitle);
        TextView textViewPublishDateTitle = findViewById(R.id.bookDetailPublishDateTitle); */

        imageViewCoverLarge.setImageResource(bookDetailCover);
        imageViewCover.setImageResource(bookDetailCover);
        textViewGenre.setText(bookDetailGenre);
        textViewTitle.setText(bookDetailTitle);
        textViewAuthors.setText(bookDetailAuthors);

        if (bookDetailPages == 0) { textViewPages.setText(getString(R.string.uc_n_a)); }
        else { textViewPages.setText(String.valueOf(bookDetailPages)); }

        if (bookDetailLanguage.isEmpty()) { textViewLanguage.setText(getString(R.string.uc_n_a)); }
        else { textViewLanguage.setText(bookDetailLanguage); }

        if (bookDetailPublishDate == null) { textViewPublishDate.setText(getString(R.string.uc_n_a)); }
        else { textViewPublishDate.setText(bookDetailPublishDate); }
    }

}
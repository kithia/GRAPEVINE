package com.thimu.grapevine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SeekBar;
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
        String bookDetailPublisher = book.getPublisher();

        final int bookDetailPages = book.getPages();
        final int bookDetailPagesRead = book.getPagesRead();
        final boolean bookDetailRead = book.getRead();
        int bookDetailFormat = book.getFormat();
        String bookDetailLanguage = book.getLanguage();
        String bookDetailPublishDate = book.getPublishDate();

        String bookDetailSummary = book.getSummary();

        // Elements of the activity
        Toolbar toolbar = findViewById(R.id.bookDetailToolbar);
        setSupportActionBar(toolbar);

        ImageView imageViewCoverLarge = findViewById(R.id.bookDetailCoverLarge);
        ImageView imageViewCover = findViewById(R.id.bookDetailCover);
        TextView textViewGenre = findViewById(R.id.bookDetailGenre);
        TextView textViewTitle = findViewById(R.id.bookDetailTitle);
        TextView textViewAuthors = findViewById(R.id.bookDetailAuthors);
        TextView textViewPublisher = findViewById(R.id.bookDetailPublisher);

        TextView textViewPages = findViewById(R.id.bookDetailPages);
        final SeekBar seekBarProgress = findViewById(R.id.bookDetailSeekBar);
        final TextView textViewPagesReadProgress = findViewById(R.id.bookDetailPagesReadSeekBar);
        final TextView textViewPagesProgress = findViewById(R.id.bookDetailPagesSeekBar);
        ImageView imageViewFormat = findViewById(R.id.bookDetailFormat);
        TextView textViewLanguage = findViewById(R.id.bookDetailLanguage);
        TextView textViewPublishDate = findViewById(R.id.bookDetailPublishDate);

        TextView textViewSummary = findViewById(R.id.bookDetailSummary);

        // TextView textViewPagesTitle = findViewById(R.id.bookDetailPagesTitle);
        TextView textViewFormatTitle = findViewById(R.id.bookDetailFormatTitle);
        /* TextView textViewLanguageTitle = findViewById(R.id.bookDetailLanguageTitle);
        TextView textViewPublishDateTitle = findViewById(R.id.bookDetailPublishDateTitle); */

        imageViewCoverLarge.setImageResource(bookDetailCover);
        imageViewCover.setImageResource(bookDetailCover);
        textViewGenre.setText(bookDetailGenre);
        textViewTitle.setText(bookDetailTitle);
        textViewAuthors.setText(bookDetailAuthors);
        textViewPublisher.setText(bookDetailPublisher);


        if (bookDetailPages == 0) { textViewPages.setText(getString(R.string.uc_n_a)); }
        else { textViewPages.setText(String.valueOf(bookDetailPages)); }

        if (bookDetailFormat == R.string.paperback) {
            imageViewFormat.setImageResource(R.drawable.ic_outline_book);
            textViewFormatTitle.setText(getString(R.string.paperback)); }
        else if (bookDetailFormat == R.string.hardcover) {
            imageViewFormat.setImageResource(R.drawable.ic_outline_book);
            textViewFormatTitle.setText(getString(R.string.hardcover)); }
        else if (bookDetailFormat == R.string.ebook) {
            imageViewFormat.setImageResource(R.drawable.ic_outline_book);
            textViewFormatTitle.setText(getString(R.string.ebook)); }
        else if (bookDetailFormat == R.string.audiobook) {
            imageViewFormat.setImageResource(R.drawable.ic_outline_headset);
            textViewFormatTitle.setText(getString(R.string.audiobook)); }
        else if (bookDetailFormat == R.string.compact_disk) {
            imageViewFormat.setImageResource(R.drawable.ic_outline_album);
            textViewFormatTitle.setText(getString(R.string.compact_disk)); }
        else if (bookDetailFormat == R.string.non_traditional) {
            imageViewFormat.setImageResource(R.drawable.ic_outline_menu_book);
            textViewFormatTitle.setText(getString(R.string.non_traditional)); }
        else { textViewFormatTitle.setText(getString(R.string.uc_n_a)); }

        if (bookDetailLanguage.isEmpty()) { textViewLanguage.setText(getString(R.string.uc_n_a)); }
        else { textViewLanguage.setText(bookDetailLanguage); }

        if (bookDetailPublishDate == null) { textViewPublishDate.setText(getString(R.string.uc_n_a)); }
        else { textViewPublishDate.setText(bookDetailPublishDate); }


        if (bookDetailPages == 0) {
            seekBarProgress.setMax(1000);
            seekBarProgress.setProgress(0, true);
            textViewPagesProgress.setTextColor(getColor(android.R.color.tertiary_text_light));
            if (bookDetailRead) { seekBarProgress.setProgress(1000, true); } }
        else {
            seekBarProgress.setMax(bookDetailPages);
            seekBarProgress.setProgress(bookDetailPagesRead, true); }
        if (bookDetailPagesRead == 0) { textViewPagesReadProgress.setTextColor(getColor(android.R.color.tertiary_text_light)); }

        textViewPagesReadProgress.setText(String.valueOf(bookDetailPagesRead));
        String pagesProgressString = " /" + bookDetailPages;
        textViewPagesProgress.setText(pagesProgressString);
        seekBarProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (!(bookDetailPages == 0)) {
                    textViewPagesReadProgress.setText(String.valueOf(seekBar.getProgress()));
                    if (seekBar.getProgress() > 0) {
                        textViewPagesReadProgress.setTextColor(getColor(R.color.colorPrimary)); }
                    else { textViewPagesReadProgress.setTextColor(getColor(android.R.color.tertiary_text_light)); } } }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if ((bookDetailPages == 0)) {
                    if (bookDetailRead) {
                        seekBarProgress.setProgress(1000, true); }
                    else { seekBarProgress.setProgress(0, true); } } } });


        textViewSummary.setText(bookDetailSummary);
    }

}
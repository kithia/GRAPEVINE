package com.thimu.grapevine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.appbar.AppBarLayout;
import com.thimu.grapevine.ui.Book;
import com.thimu.grapevine.ui.reads.ReadsFragment;

import java.util.Objects;

/**
 * An activity to display the details of a book
 *
 * @author Kĩthia Ngigĩ
 * @version 03.08.2020
 */
public class BookDetailActivity extends AppCompatActivity {

    //
    private Menu bookDetailMenu;

    // Attributes of book
    private int bookDetailPages;
    private int bookDetailPagesRead;
    private boolean bookDetailRead;

    // Elements of activity
    private AppBarLayout appbarLayout;
    private Toolbar toolbar;
    private String bookDetailTitle;
    private NestedScrollView nestedScrollView;

    private ImageView imageViewCoverLarge;
    private SeekBar seekbarProgress;
    private TextView textViewPagesReadProgress;
    private TextView textViewPagesProgress;

    /**
     * Create the activity
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        Intent intent = getIntent();
        Book book = intent.getParcelableExtra(ReadsFragment.EXTRA_BOOK);

        bookDetailMenu = findViewById(R.id.edit_book_detail);

        int bookDetailCover = Objects.requireNonNull(book).getCover();
        String bookDetailGenre = book.getGenre();
        bookDetailTitle = book.getTitle();
        String bookDetailAuthors = book.getAuthors();
        String bookDetailPublisher = book.getPublisher();

        bookDetailPages = book.getPages();
        bookDetailPagesRead = book.getPagesRead();
        bookDetailRead = book.getRead();
        int bookDetailFormat = book.getFormat();
        String bookDetailLanguage = book.getLanguage();
        String bookDetailPublishDate = book.getPublishDate();

        String bookDetailSummary = book.getSummary();

        appbarLayout = findViewById(R.id.bookDetailAppBarLayout);
        toolbar = findViewById(R.id.bookDetailToolbar);
        setSupportActionBar(toolbar);
        nestedScrollView = findViewById(R.id.bookDetailNestedScrollView);

        imageViewCoverLarge = findViewById(R.id.bookDetailCoverLarge);
        ImageView imageViewCover = findViewById(R.id.bookDetailCover);
        TextView textViewGenre = findViewById(R.id.bookDetailGenre);
        TextView textViewTitle = findViewById(R.id.bookDetailTitle);
        TextView textViewAuthors = findViewById(R.id.bookDetailAuthors);
        TextView textViewPublisher = findViewById(R.id.bookDetailPublisher);

        TextView textViewPages = findViewById(R.id.bookDetailPages);
        seekbarProgress = findViewById(R.id.bookDetailSeekBar);
        textViewPagesReadProgress = findViewById(R.id.bookDetailPagesReadSeekBar);
        textViewPagesProgress = findViewById(R.id.bookDetailPagesSeekBar);
        ImageView imageViewFormat = findViewById(R.id.bookDetailFormat);
        TextView textViewLanguage = findViewById(R.id.bookDetailLanguage);
        TextView textViewPublishDate = findViewById(R.id.bookDetailPublishDate);

        TextView textViewSummary = findViewById(R.id.bookDetailSummary);

        // TextView textViewPagesTitle = findViewById(R.id.bookDetailPagesTitle);
        TextView textViewFormatTitle = findViewById(R.id.bookDetailFormatTitle);
        /* TextView textViewLanguageTitle = findViewById(R.id.bookDetailLanguageTitle);
        TextView textViewPublishDateTitle = findViewById(R.id.bookDetailPublishDateTitle); */

        nestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                if (nestedScrollView.getScrollY() >= (imageViewCoverLarge.getHeight() - toolbar.getHeight())) {
                    ViewCompat.setElevation(appbarLayout, floatValueOf(4));
                    toolbar.setNavigationIcon(R.drawable.ic_outline_arrow_back_primary);
                    toolbar.setTitle(bookDetailTitle);
                    toolbar.setBackgroundColor(getColor(android.R.color.white)); }
                else {
                    ViewCompat.setElevation(appbarLayout, 0);
                    toolbar.setNavigationIcon(R.drawable.ic_outline_arrow_back_white);
                    toolbar.setTitle("");
                    toolbar.setBackgroundColor(getColor(android.R.color.transparent)); } } });

        /* ImageColour imageColour = null;
        try { imageColour = new ImageColour(getBitmap(bookDetailCover)); }
        catch (Exception e) { e.printStackTrace(); }
        String mostCommonColour = imageColour.getColourHex();
        if (mostCommonColour.isEmpty()) { imageViewCoverLarge.setImageResource(bookDetailCover); }
        else {
            int color = Color.parseColor(mostCommonColour);
            imageViewCoverLarge.setColorFilter(color); } */
        imageViewCoverLarge.setImageResource(bookDetailCover);
        imageViewCover.setImageResource(bookDetailCover);
        textViewGenre.setText(bookDetailGenre);
        textViewTitle.setText(bookDetailTitle);
        textViewAuthors.setText(bookDetailAuthors);
        textViewPublisher.setText(bookDetailPublisher);

        buildBookDetailInfo(bookDetailFormat, bookDetailLanguage, bookDetailPublishDate, textViewPages, imageViewFormat, textViewLanguage, textViewPublishDate, textViewFormatTitle);

        buildBookDetailProgress();

        textViewSummary.setText(bookDetailSummary);
    }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.book_detail_menu, menu);
        return true; }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.edit_book_detail) { return true; }
        else { return false; } }

    /**
     *
     * @param image
     * @return
     */
    public String getDominantColour(int image) {
        Drawable drawable = ContextCompat.getDrawable(this, image);
        Bitmap bitmap = Bitmap.createBitmap(Objects.requireNonNull(drawable).getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Bitmap newBitmap = Bitmap.createScaledBitmap(bitmap, 1, 1, true);
        final int color = newBitmap.getPixel(0, 0);
        newBitmap.recycle();
        return "#" + color; }

    /**
     *
     * @param image
     * @return
     */
    private Bitmap getBitmap(int image) {
        Drawable drawable = ContextCompat.getDrawable(this, image);
        Bitmap bitmap = Bitmap.createBitmap(Objects.requireNonNull(drawable).getIntrinsicWidth(),
                drawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap; }

    /**
     *
     */
    private void buildBookDetailProgress() {
        textViewPagesReadProgress.setText(String.valueOf(bookDetailPagesRead));
        String pagesProgressString = " /" + bookDetailPages;
        textViewPagesProgress.setText(pagesProgressString);
        seekbarProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (!(bookDetailPages == 0)) {
                    textViewPagesReadProgress.setText(String.valueOf(seekBar.getProgress()));
                    if (seekBar.getProgress() > 0) {
                        textViewPagesReadProgress.setTextColor(getColor(R.color.colorPrimary));
                    } else {
                        textViewPagesReadProgress.setTextColor(getColor(android.R.color.tertiary_text_light));
                    }
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                if ((bookDetailPages == 0)) {
                    if (bookDetailRead) {
                        seekbarProgress.setProgress(1000, true);
                    } else {
                        seekbarProgress.setProgress(0, true);
                    }
                }
            }
        });
    }

    /**
     * @param bookDetailFormat
     * @param bookDetailLanguage
     * @param bookDetailPublishDate
     * @param textViewPages
     * @param imageViewFormat
     * @param textViewLanguage
     * @param textViewPublishDate
     * @param textViewFormatTitle
     */
    private void buildBookDetailInfo(int bookDetailFormat, String bookDetailLanguage, String bookDetailPublishDate, TextView textViewPages, ImageView imageViewFormat, TextView textViewLanguage, TextView textViewPublishDate, TextView textViewFormatTitle) {
        if (bookDetailPages == 0) {
            textViewPages.setText(getString(R.string.uc_n_a));
        } else {
            textViewPages.setText(String.valueOf(bookDetailPages));
        }

        if (bookDetailFormat == R.string.paperback) {
            imageViewFormat.setImageResource(R.drawable.ic_outline_book);
            textViewFormatTitle.setText(getString(R.string.paperback));
        } else if (bookDetailFormat == R.string.hardcover) {
            imageViewFormat.setImageResource(R.drawable.ic_outline_book);
            textViewFormatTitle.setText(getString(R.string.hardcover));
        } else if (bookDetailFormat == R.string.leather_bound) {
            imageViewFormat.setImageResource(R.drawable.ic_outline_book);
            textViewFormatTitle.setText(getString(R.string.leather_bound));
        } else if (bookDetailFormat == R.string.ebook) {
            imageViewFormat.setImageResource(R.drawable.ic_outline_book);
            textViewFormatTitle.setText(getString(R.string.ebook));
        } else if (bookDetailFormat == R.string.audiobook) {
            imageViewFormat.setImageResource(R.drawable.ic_outline_headset);
            textViewFormatTitle.setText(getString(R.string.audiobook));
        } else if (bookDetailFormat == R.string.compact_disk) {
            imageViewFormat.setImageResource(R.drawable.ic_outline_album);
            textViewFormatTitle.setText(getString(R.string.compact_disk));
        } else if (bookDetailFormat == R.string.non_traditional) {
            imageViewFormat.setImageResource(R.drawable.ic_outline_menu_book);
            textViewFormatTitle.setText(getString(R.string.non_traditional));
        } else {
            textViewFormatTitle.setText(getString(R.string.uc_n_a));
        }

        if (bookDetailLanguage.isEmpty()) {
            textViewLanguage.setText(getString(R.string.uc_n_a));
        } else {
            textViewLanguage.setText(bookDetailLanguage);
        }

        if (bookDetailPublishDate == null) {
            textViewPublishDate.setText(getString(R.string.uc_n_a));
        } else {
            textViewPublishDate.setText(bookDetailPublishDate);
        }


        if (bookDetailPages == 0) {
            seekbarProgress.setMax(1000);
            seekbarProgress.setProgress(0, true);
            textViewPagesProgress.setTextColor(getColor(android.R.color.tertiary_text_light));
            if (bookDetailRead) {
                seekbarProgress.setProgress(1000, true);
            }
        } else {
            seekbarProgress.setMax(bookDetailPages);
            seekbarProgress.setProgress(bookDetailPagesRead, true);
        }
        if (bookDetailPagesRead == 0) {
            textViewPagesReadProgress.setTextColor(getColor(android.R.color.tertiary_text_light));
        }
    }

    /**
     * Convert a dp value to its float equivalent
     *
     * @param dp the dp value to be converted
     */
    public float floatValueOf(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                this.getResources().getDisplayMetrics()); }
}
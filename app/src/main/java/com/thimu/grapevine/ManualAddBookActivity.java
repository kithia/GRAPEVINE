package com.thimu.grapevine;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

/**
 *
 *
 * @author Obed Ngigi
 * @version 06.07.2020
 */
public class ManualAddBookActivity extends AppCompatActivity {

    // Intent keys
    public static final String EXTRA_ISBN =
            "com.thimu.grapevine.EXTRA_ISBN";
    public static final String EXTRA_PUBLISHER =
            "com.thimu.grapevine.EXTRA_PUBLISHER";
    public static final String EXTRA_PUBLISH_DATE =
            "com.thimu.grapevine.EXTRA_PUBLISH_YEAR";
    public static final String EXTRA_TITLE =
            "com.thimu.grapevine.EXTRA_TITLE";
    public static final String EXTRA_AUTHORS =
            "com.thimu.grapevine.EXTRA_AUTHORS";
    public static final String EXTRA_GENRE =
            "com.thimu.grapevine.EXTRA_GENRE";
    public static final String EXTRA_LANGUAGE =
            "com.thimu.grapevine.EXTRA_LANGUAGE";
    public static final String EXTRA_PAGES =
            "com.thimu.grapevine.EXTRA_PAGES";

    // Elements of the activity
    private ScrollView scrollView;

    private TextInputLayout textInputLayoutISBN;
    private TextInputLayout textInputLayoutTitle;

    private TextInputEditText textInputISBN;
    private TextInputEditText textInputTitle;
    private TextInputEditText textInputAuthors;
    private TextInputEditText textInputGenre;
    private TextInputEditText textInputPublisher;
    private TextInputEditText textInputPublishDate;
    private TextInputEditText textInputLanguage;
    private TextInputEditText textInputPages;

    /**
     * Create the activity
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_add_book);

        Objects.requireNonNull(getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic_outline_close);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0);

        scrollView = findViewById(R.id.manualAddBookScrollView);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if(scrollView.canScrollVertically(-1)) {
                    // Show elevation
                    setActionBarElevation(4); }
                else {
                    // Remove elevation
                    setActionBarElevation(0); } } });

        textInputLayoutISBN = findViewById(R.id.textInputLayoutISBN);
        textInputLayoutTitle = findViewById(R.id.textInputLayoutTitle);

        textInputISBN = findViewById(R.id.textInputEnterISBN);
        textInputTitle = findViewById(R.id.textInputEnterTitle);
        textInputAuthors = findViewById(R.id.textInputEnterAuthors);
        textInputGenre = findViewById(R.id.textInputEnterGenre);
        textInputPublisher = findViewById(R.id.textInputEnterPublisher);
        textInputPublishDate = findViewById(R.id.textInputEnterPublishDate);
        textInputLanguage = findViewById(R.id.textInputEnterLanguage);
        textInputPages = findViewById(R.id.textInputEnterPages);

    }

    /**
     * Set the elevation of the actionbar
     * @param elevation the dp value of the elevation
     */
    public void setActionBarElevation (int elevation) {
        float floatElevation = TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, elevation,
                this.getResources().getDisplayMetrics() );
        Objects.requireNonNull(getSupportActionBar()).setElevation(floatElevation); }

    /**
     *
     */
    private void saveBook() {
        String ISBN = Objects.requireNonNull(textInputISBN.getText()).toString().trim();
        final String title = Objects.requireNonNull(textInputTitle.getText()).toString();
        String authors = Objects.requireNonNull(textInputAuthors.getText()).toString();
        String genre = Objects.requireNonNull(textInputGenre.getText()).toString();
        String publisher = Objects.requireNonNull(textInputPublisher.getText()).toString();
        String publishDate = Objects.requireNonNull(textInputPublishDate.getText()).toString();
        String language = Objects.requireNonNull(textInputLanguage.getText()).toString();
        String pages = Objects.requireNonNull(textInputPages.getText()).toString();

        if (!ISBN.isEmpty()) {
            switch (ISBN.length()) {
                case 10:
                case 13:
                    break;
                default:
                    textInputLayoutISBN.setError(getString(R.string.please_enter_valid_ISBN));
                    textInputISBN.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            textInputLayoutISBN.setErrorEnabled(false);
                            textInputLayoutISBN.setErrorEnabled(true); }

                        @Override
                        public void afterTextChanged(Editable editable) { } });

                    textInputISBN.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                        @Override
                        public void onFocusChange(View view, boolean b) {
                            textInputLayoutISBN.setErrorEnabled(false);
                            textInputLayoutISBN.setErrorEnabled(true); } });
                    break; } }
        else { textInputLayoutISBN.setError(null); }

        if (title.trim().isEmpty()) {
            textInputLayoutTitle.setError(getString(R.string.please_enter_title));
            textInputTitle.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    textInputLayoutTitle.setErrorEnabled(false);
                    textInputLayoutTitle.setErrorEnabled(true); }

                @Override
                public void afterTextChanged(Editable editable) { } }); }
        else {
            textInputLayoutTitle.setError(null);

            Intent data = new Intent();
            data.putExtra(EXTRA_ISBN, ISBN);
            data.putExtra(EXTRA_PUBLISHER, publisher);
            data.putExtra(EXTRA_PUBLISH_DATE, publishDate);
            data.putExtra(EXTRA_TITLE, publisher);
            data.putExtra(EXTRA_AUTHORS, authors);
            data.putExtra(EXTRA_GENRE, genre);
            data.putExtra(EXTRA_LANGUAGE, language);
            data.putExtra(EXTRA_PAGES, pages);

            // Indicates if whether the input was successful (save button was selected)
            setResult(RESULT_OK);
            // Close the activity
            finish(); } }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_book_menu, menu);
        return true; }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_book) {
            saveBook();
            return true; }
        return super.onOptionsItemSelected(item); }
}
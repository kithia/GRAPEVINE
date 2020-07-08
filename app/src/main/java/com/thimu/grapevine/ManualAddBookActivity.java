package com.thimu.grapevine;

import android.app.Activity;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

/**
 * A fragment for the user to manually add a book to their library
 *
 * @author Obed Ngigi
 * @version 07.07.2020
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

        // Configure actionbar
        ActionBar toolbar = Objects.requireNonNull(getSupportActionBar());
        toolbar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        toolbar.setHomeAsUpIndicator(R.drawable.ic_outline_close);
        toolbar.setElevation(0);

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
     *  Add book to library
     */
    private void saveBook() {
        String ISBN = Objects.requireNonNull(textInputISBN.getText()).toString();
        String title = Objects.requireNonNull(textInputTitle.getText()).toString();
        String authors = Objects.requireNonNull(textInputAuthors.getText()).toString();
        String genre = Objects.requireNonNull(textInputGenre.getText()).toString();
        String publisher = Objects.requireNonNull(textInputPublisher.getText()).toString();
        String publishDate = Objects.requireNonNull(textInputPublishDate.getText()).toString();
        String language = Objects.requireNonNull(textInputLanguage.getText()).toString();
        String pages = Objects.requireNonNull(textInputPages.getText()).toString();

        boolean isAcceptableISBN = true;
        if (!ISBN.isEmpty()) {
            // Permit ISBN-10 and ISBN-13 only
            String regex = getString(R.string.regex_ISBN);
            if (ISBN.matches(regex)) isAcceptableISBN = true;
            else {
                 textInputLayoutISBN.setError(getString(R.string.please_enter_valid_ISBN));
                 textInputISBN.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        textInputLayoutISBN.setErrorEnabled(false); }

                    @Override
                    public void afterTextChanged(Editable editable) { } });

                 textInputISBN.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {
                        textInputLayoutISBN.setErrorEnabled(false); } });

                 textInputLayoutISBN.setErrorEnabled(true);
                 isAcceptableISBN = false; }
        }

        boolean isAcceptableTitle = false;
        if (!title.trim().isEmpty()) {
                textInputLayoutTitle.setError(null);
                isAcceptableTitle = true; }
        else {
                    textInputLayoutTitle.setError(getString(R.string.please_enter_title));
                    textInputTitle.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }

                        @Override
                        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                            textInputLayoutTitle.setErrorEnabled(false); }

                        @Override
                        public void afterTextChanged(Editable editable) { } });
                    textInputLayoutTitle.setErrorEnabled(true); }

        if (isAcceptableTitle && isAcceptableISBN) {
            Intent bookData = new Intent();
            bookData.putExtra(EXTRA_ISBN, ISBN.trim());
            bookData.putExtra(EXTRA_PUBLISHER, publisher);
            bookData.putExtra(EXTRA_PUBLISH_DATE, publishDate);
            bookData.putExtra(EXTRA_TITLE, title);
            bookData.putExtra(EXTRA_AUTHORS, authors);
            bookData.putExtra(EXTRA_GENRE, genre);
            bookData.putExtra(EXTRA_LANGUAGE, language);
            bookData.putExtra(EXTRA_PAGES, pages);

            // Indicates whether the input was successful (save button was selected)
            setResult(RESULT_OK, bookData);
            // Close the activity
            hideVirtualKeyboard(this);
            finish(); }
    }

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
        else { return super.onOptionsItemSelected(item); } }

    /**
     * Hide the onscreen keyboard
     * @param activity
     */
    public static void hideVirtualKeyboard(Activity activity) {
        InputMethodManager IMM = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        // Find the currently focused view so the correct window token can be taken from it
        View view = activity.getCurrentFocus();
        // If no view currently has focus, create a new one; the window token can be taken from it
        if (view == null) {
            view = new View(activity); }
        IMM.hideSoftInputFromWindow(view.getWindowToken(), 0); }
}
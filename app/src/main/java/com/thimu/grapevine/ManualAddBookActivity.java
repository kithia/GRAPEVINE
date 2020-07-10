package com.thimu.grapevine;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.os.ConfigurationCompat;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

/**
 * A fragment for the user to manually add a book to their library
 *
 * @author Obed Ngigi
 * @version 10.07.2020
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
    private ActionBar toolbar;
    private ScrollView scrollView;

    private TextInputLayout textInputLayoutISBN;
    private TextInputLayout textInputLayoutTitle;
    private TextInputLayout textInputLayoutPublishDate;

    private TextInputEditText textInputISBN;
    private TextInputEditText textInputTitle;
    private TextInputEditText textInputAuthors;
    private TextInputEditText textInputGenre;
    private TextInputEditText textInputPublisher;
    private TextInputEditText textInputPublishDate;
    private TextInputEditText textInputPublishDateSQL;
    private TextInputEditText textInputLanguage;
    private TextInputEditText textInputPages;

    /**
     * Create the activity
     * @param savedInstanceState the last saved state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_add_book);

        // Configure actionbar
        toolbar = Objects.requireNonNull(getSupportActionBar());
        toolbar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        toolbar.setHomeAsUpIndicator(R.drawable.ic_outline_close);
        toolbar.setElevation(0);

        scrollView = findViewById(R.id.manualAddBookScrollView);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if(scrollView.canScrollVertically(-1)) { setActionBarElevation(4); }
                else { setActionBarElevation(0); } } });

        textInputLayoutISBN = findViewById(R.id.textInputLayoutISBN);
        textInputLayoutTitle = findViewById(R.id.textInputLayoutTitle);
        textInputLayoutPublishDate = findViewById(R.id.textInputLayoutPublishDate);

        textInputISBN = findViewById(R.id.textInputEnterISBN);
        textInputTitle = findViewById(R.id.textInputEnterTitle);
        textInputAuthors = findViewById(R.id.textInputEnterAuthors);
        textInputGenre = findViewById(R.id.textInputEnterGenre);
        textInputPublisher = findViewById(R.id.textInputEnterPublisher);
        textInputPublishDate = findViewById(R.id.textInputEnterPublishDate);
        textInputPublishDateSQL = textInputPublishDate;
        textInputLanguage = findViewById(R.id.textInputEnterLanguage);
        textInputPages = findViewById(R.id.textInputEnterPages);

        // Hide soft keyboard
        textInputPublishDate.setInputType(InputType.TYPE_NULL);

        final Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        final DatePickerDialog publishDatePickerDialog = new DatePickerDialog(
                ManualAddBookActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, day);

                Date publishDate = calendar.getTime();
                // SQL date format for input into Book database
                @SuppressLint("SimpleDateFormat") DateFormat SQLDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String publishDateSQLString = SQLDateFormat.format(publishDate);
                textInputPublishDateSQL.setText(publishDateSQLString);

                // Locale date format for UI output
                Locale locale = ConfigurationCompat.getLocales(Resources.getSystem()
                        .getConfiguration()).get(0);
                DateFormat localeDateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, locale);
                String publishDateString = localeDateFormat.format(publishDate);
                textInputPublishDate.setText(publishDateString); } }, year, month, day);

        textInputPublishDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (textInputPublishDate.hasFocus()) {
                    publishDatePickerDialog.show(); } } });

        textInputPublishDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                publishDatePickerDialog.show(); } });
    }

    /**
     * Set the elevation of the actionbar
     * @param elevation the dp value of the elevation
     */
    public void setActionBarElevation (int elevation) {
        float floatElevation = TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, elevation,
                this.getResources().getDisplayMetrics() );
        toolbar.setElevation(floatElevation); }

    /**
     *  Add book to library
     */
    private void saveBook() {
        String ISBN = Objects.requireNonNull(textInputISBN.getText()).toString();
        String title = Objects.requireNonNull(textInputTitle.getText()).toString();
        String authors = Objects.requireNonNull(textInputAuthors.getText()).toString();
        String genre = Objects.requireNonNull(textInputGenre.getText()).toString();
        String publisher = Objects.requireNonNull(textInputPublisher.getText()).toString();
        String publishDate = Objects.requireNonNull(textInputPublishDateSQL.getText()).toString();
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

        if (!isAcceptableISBN || !isAcceptableTitle) {
            scrollView.fullScroll(ScrollView.FOCUS_UP); }
        else {
            Intent bookData = new Intent();
            bookData.putExtra(EXTRA_ISBN, ISBN);
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
            hideSoftKeyboard(ManualAddBookActivity.this);
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
     * Hide soft keyboard
     * @param activity this activity
     */
    public void hideSoftKeyboard(Activity activity) {
        InputMethodManager IMM = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        View view = activity.getCurrentFocus();
        if (view == null) {
            view = new View(activity); }
        IMM.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY); }
}
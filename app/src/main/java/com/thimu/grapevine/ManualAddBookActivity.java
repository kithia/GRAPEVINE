package com.thimu.grapevine;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;

/**
 * An activity for the user to manually add a book to their library
 *
 * @author Obed Ngigi
 * @version 11.07.2020
 */
public class ManualAddBookActivity extends AppCompatActivity {

    //
    public static final int ADD_BOOK_SUMMARY_REQUEST = 0;

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
    public static final String EXTRA_SUMMARY =
            "com.thimu.grapevine.EXTRA_SUMMARY";
    public static final String EXTRA_LANGUAGE =
            "com.thimu.grapevine.EXTRA_LANGUAGE";
    public static final String EXTRA_PAGES =
            "com.thimu.grapevine.EXTRA_PAGES";
    public static final String EXTRA_BOOK_DISCARD =
            "com.thimu.grapevine.EXTRA_BOOK_DISCARD";

    // Elements of the activity
    private ActionBar toolbar;
    private ScrollView scrollView;

    private TextInputLayout textInputLayoutISBN;
    private TextInputLayout textInputLayoutTitle;

    private TextInputEditText textInputISBN;
    private TextInputEditText textInputTitle;
    private TextInputEditText textInputAuthors;
    private TextInputEditText textInputGenre;
    private TextInputEditText textInputPublisher;
    private TextInputEditText textInputPublishDate;
    private String publishDateSQL;
    private TextInputEditText textInputSummary;
    private TextInputEditText textInputLanguage;
    private TextInputEditText textInputPages;

    private View currentFocus;

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
        toolbar.setHomeButtonEnabled(true);
        toolbar.setElevation(0);

        scrollView = findViewById(R.id.manualAddBookScrollView);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if(scrollView.canScrollVertically(-1)) { setActionBarElevation(4); }
                else { setActionBarElevation(0); } } });

        textInputLayoutISBN = findViewById(R.id.textInputLayoutISBN);
        textInputLayoutTitle = findViewById(R.id.textInputLayoutTitle);

        textInputISBN = findViewById(R.id.textInputEnterISBN);
        textInputTitle = findViewById(R.id.textInputEnterTitle);
        textInputAuthors = findViewById(R.id.textInputEnterAuthors);
        textInputGenre = findViewById(R.id.textInputEnterGenre);
        textInputPublisher = findViewById(R.id.textInputEnterPublisher);
        textInputPublishDate = findViewById(R.id.textInputEnterPublishDate);
        textInputSummary = findViewById(R.id.textInputEnterSummary);
        textInputLanguage = findViewById(R.id.textInputEnterLanguage);
        textInputPages = findViewById(R.id.textInputEnterPages);

        // Hide soft keyboard
        textInputPublishDate.setInputType(InputType.TYPE_NULL);
        textInputSummary.setInputType(InputType.TYPE_NULL);

        long currentDate = System.currentTimeMillis();

        CalendarConstraints.Builder publishDateConstraintsBuilder = new CalendarConstraints.Builder();
        publishDateConstraintsBuilder.setOpenAt(currentDate);
        publishDateConstraintsBuilder.setEnd(currentDate);

        MaterialDatePicker.Builder publishDatePickerBuilder = MaterialDatePicker.Builder.datePicker();
        publishDatePickerBuilder.setTitleText(getString(R.string.select_publish_date));
        publishDatePickerBuilder.setCalendarConstraints(publishDateConstraintsBuilder.build());
        publishDatePickerBuilder.setSelection(MaterialDatePicker.todayInUtcMilliseconds());
        final MaterialDatePicker publishDatePicker = publishDatePickerBuilder.build();

        publishDatePicker.addOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                currentFocus.requestFocus(); } });

        publishDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                // SQL date format for input into Book database
                Date publishDate = new Date((Long) publishDatePicker.getSelection());
                publishDateSQL = publishDate.toString();
                textInputPublishDate.setText(publishDatePicker.getHeaderText());

                textInputLanguage.requestFocus(); } });

        textInputPublishDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFocus = getCurrentFocus();
                if (currentFocus == null) { currentFocus = textInputISBN; }
                // publishDatePickerDialog.show();
                publishDatePicker.show(getSupportFragmentManager(), getString(R.string.enter_publish_date)); } });

        textInputSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFocus = getCurrentFocus();
                if (currentFocus == null) { currentFocus = textInputISBN; }
                Intent bookSummaryIntent = new Intent(ManualAddBookActivity.this, ManualAddBookSummaryActivity.class);
                String bookSummary = Objects.requireNonNull(textInputSummary.getText()).toString();
                bookSummaryIntent.putExtra(EXTRA_SUMMARY, bookSummary);
                startActivityForResult(bookSummaryIntent, ADD_BOOK_SUMMARY_REQUEST); } });
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
     * Pass the book in n intent
     * Add the book to library
     */
    private void saveBook() {
        String ISBN = Objects.requireNonNull(textInputISBN.getText()).toString();
        String title = Objects.requireNonNull(textInputTitle.getText()).toString();
        String authors = Objects.requireNonNull(textInputAuthors.getText()).toString();
        String genre = Objects.requireNonNull(textInputGenre.getText()).toString();
        String publisher = Objects.requireNonNull(textInputPublisher.getText()).toString();
        String publishDate = publishDateSQL;
        String summary = Objects.requireNonNull(textInputSummary.getText()).toString();
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
                 textInputLayoutISBN.setErrorEnabled(true);
                 isAcceptableISBN = false; } }

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

        if (!isAcceptableTitle) {
            scrollView.smoothScrollTo(textInputTitle.getScrollX(), textInputTitle.getScrollY());
            textInputTitle.requestFocus(); }
        else if (!isAcceptableISBN) {
            scrollView.smoothScrollTo(textInputISBN.getScrollX(), textInputISBN.getScrollY());
            textInputISBN.requestFocus(); }
        else {
            Intent bookData = new Intent();
            bookData.putExtra(EXTRA_ISBN, ISBN);
            bookData.putExtra(EXTRA_PUBLISHER, publisher);
            bookData.putExtra(EXTRA_PUBLISH_DATE, publishDate);
            bookData.putExtra(EXTRA_TITLE, title);
            bookData.putExtra(EXTRA_AUTHORS, authors);
            bookData.putExtra(EXTRA_GENRE, genre);
            bookData.putExtra(EXTRA_SUMMARY, summary);
            bookData.putExtra(EXTRA_LANGUAGE, language);
            bookData.putExtra(EXTRA_PAGES, pages);

            // Indicates whether the input was successful (save button was selected)
            setResult(RESULT_OK, bookData);
            // Hide the soft keyboard and close the activity
            hideSoftKeyboard(ManualAddBookActivity.this);
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
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_BOOK_SUMMARY_REQUEST) {
            if (resultCode == RESULT_OK) {
                String writeSummary = Objects.requireNonNull(data).getStringExtra(ManualAddBookSummaryActivity.EXTRA_WRITE_SUMMARY);
                textInputSummary.setText(writeSummary);
                textInputLanguage.requestFocus(); }
            else { currentFocus.requestFocus(); } } }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_book) { saveBook(); }
        else { onExit(); }
        return true; }

        /**
     * Decide what to do when back button is pressed
     */
    @Override
    public void onBackPressed() {
        onExit(); }

    /**
     * Decide what to do when the user tires to exit
     */
    private void onExit() {
        currentFocus = getCurrentFocus();
        if(!checkAddBookEmpty()) {
            final MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(ManualAddBookActivity.this);
            alertDialogBuilder.setMessage(getString(R.string.discard_book_question));
            alertDialogBuilder.setIcon(R.drawable.ic_outline_delete);
            alertDialogBuilder.setPositiveButton(getString(R.string.discard), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    hideSoftKeyboard(ManualAddBookActivity.this);
                    finish(); } });

            alertDialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    currentFocus.requestFocus(); } });

            alertDialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    currentFocus.requestFocus(); } });

            alertDialogBuilder.show(); }
        else { finish(); }
    }

    /**
     * Check if there is any entry into the activity
     * @return whether there is any entry into the activity
     */
    public boolean checkAddBookEmpty() {
        ArrayList<TextInputEditText> textInputEditTexts = new ArrayList<>();
        textInputEditTexts.add(textInputISBN);
        textInputEditTexts.add(textInputTitle);
        textInputEditTexts.add(textInputAuthors);
        textInputEditTexts.add(textInputGenre);
        textInputEditTexts.add(textInputPublisher);
        textInputEditTexts.add(textInputPublishDate);
        textInputEditTexts.add(textInputSummary);
        textInputEditTexts.add(textInputLanguage);
        textInputEditTexts.add(textInputPages);

        boolean isAddBookEmpty = true;
            for(TextInputEditText bookAttribute : textInputEditTexts) {
                if (!Objects.requireNonNull(bookAttribute.getText()).toString().isEmpty()) {
                    isAddBookEmpty = false; } }
            return isAddBookEmpty; }

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
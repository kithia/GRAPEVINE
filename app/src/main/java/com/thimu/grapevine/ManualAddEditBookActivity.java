package com.thimu.grapevine;

import android.annotation.SuppressLint;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.thimu.grapevine.ui.ISBNRegularExpression;
import com.thimu.grapevine.ui.reads.ReadsFragment;

import org.jetbrains.annotations.NotNull;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

/**
 * An activity for the user to manually add a book to their library
 *
 * @author Kĩthia Ngigĩ
 * @version 05.08.2020
 */
public class ManualAddEditBookActivity extends AppCompatActivity {

    //
    public ISBNRegularExpression ISBNRegularExpression
            = new ISBNRegularExpression();

    //
    public static final int ADD_BOOK_SUMMARY_REQUEST = 0;

    // Intent keys
    public static final String EXTRA_IDENTIFICATION =
            "com.thimu.grapevine.EXTRA_IDENTIFICATION";
    public static final String EXTRA_ISBN =
            "com.thimu.grapevine.EXTRA_ISBN";
    public static final String EXTRA_COVER =
            "com.thimu.grapevine.EXTRA_COVER";
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
    public static final String EXTRA_FORMAT =
            "com.thimu.grapevine.EXTRA_FORMAT";
    public static final String EXTRA_PAGES =
            "com.thimu.grapevine.EXTRA_PAGES";
    public static final String EXTRA_PAGES_READ =
            "com.thimu.grapevine.EXTRA_PAGES_READ";
    public static final String EXTRA_READ =
            "com.thimu.grapevine.EXTRA_READ";
    public static final String EXTRA_BOOK_DISCARD =
            "com.thimu.grapevine.EXTRA_BOOK_DISCARD";

    // Elements of the activity
    private ActionBar toolbar;
    private ScrollView scrollView;

    private TextInputLayout textInputLayoutISBN;
    private TextInputLayout textInputLayoutTitle;

    private TextInputEditText textInputISBN;
    private int cover = 0;
    private TextInputEditText textInputTitle;
    private TextInputEditText textInputAuthors;
    private AutoCompleteTextView textInputGenre;
    private TextInputEditText textInputPublisher;
    private TextInputEditText textInputPublishDate;
    private String publishDateSQL;
    private TextInputEditText textInputSummary;
    private TextInputEditText textInputLanguage;
    private AutoCompleteTextView textInputFormat;
    private TextInputEditText textInputPages;
    private SwitchMaterial readSwitch;

    private View currentFocus;

    /**
     * Create the activity
     * @param savedInstanceState the last saved state of the activity
     */
    @SuppressLint("CutPasteId")
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

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_IDENTIFICATION)) {
            setTitle(getString(R.string.edit_book));
            textInputISBN.setText(intent.getStringExtra(EXTRA_ISBN));
            cover = intent.getIntExtra(EXTRA_COVER, 0);
            textInputTitle.setText(intent.getStringExtra(EXTRA_TITLE));
            textInputAuthors.setText(intent.getStringExtra(EXTRA_AUTHORS));
            textInputGenre.setText(intent.getStringExtra(EXTRA_GENRE));
            textInputPublisher.setText(intent.getStringExtra(EXTRA_PUBLISHER));
            textInputPublishDate.setText(intent.getStringExtra(EXTRA_PUBLISH_DATE));
            textInputSummary.setText(intent.getStringExtra(EXTRA_SUMMARY));
            textInputLanguage.setText(intent.getStringExtra(EXTRA_LANGUAGE));
            textInputFormat.setText(intent.getIntExtra(EXTRA_FORMAT, R.string.paperback));
            textInputPages.setText(intent.getIntExtra(EXTRA_PAGES, 0));
            readSwitch.setChecked(intent.getBooleanExtra(EXTRA_READ, false));

            readSwitch.setEnabled(false); }
        else { setTitle(getString(R.string.add_book)); }

        scrollView = findViewById(R.id.manualAddBookScrollView);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if(scrollView.canScrollVertically(-1)) { toolbar.setElevation(floatValueOf(4)); }
                else { toolbar.setElevation(0); } } });

        textInputLayoutISBN = findViewById(R.id.textInputLayoutISBN);
        textInputLayoutTitle = findViewById(R.id.textInputLayoutTitle);

        textInputISBN = findViewById(R.id.textInputEnterISBN);
        cover = getRandomFlag();
        textInputTitle = findViewById(R.id.textInputEnterTitle);
        textInputAuthors = findViewById(R.id.textInputEnterAuthors);
        textInputGenre = findViewById(R.id.textInputEnterGenre);
        textInputPublisher = findViewById(R.id.textInputEnterPublisher);
        textInputPublishDate = findViewById(R.id.textInputEnterPublishDate);
        textInputSummary = findViewById(R.id.textInputEnterSummary);
        textInputLanguage = findViewById(R.id.textInputEnterLanguage);
        textInputFormat = findViewById(R.id.textInputEnterFormat);
        textInputPages = findViewById(R.id.textInputEnterPages);
        readSwitch = findViewById(R.id.switchRead);

        textInputISBN.requestFocus();
        currentFocus = getCurrentFocus();

        ArrayList<String> dropdownGenre = setDropdownGenreList();
        ArrayList<String> dropdownFormat = setDropdownFormatList();
        ArrayAdapter<String> genreStringAdapter = new ArrayAdapter<>(ManualAddEditBookActivity.this,
                R.layout.dropdown_menu_item, dropdownGenre);
        ArrayAdapter<String> formatStringAdapter = new ArrayAdapter<>(ManualAddEditBookActivity.this,
                R.layout.dropdown_menu_item, dropdownFormat);

        AutoCompleteTextView editTextEnterGenreDropdown = findViewById(R.id.textInputEnterGenre);
        AutoCompleteTextView editTextEnterFormatDropdown = findViewById(R.id.textInputEnterFormat);
        editTextEnterGenreDropdown.setAdapter(genreStringAdapter);
        editTextEnterFormatDropdown.setAdapter(formatStringAdapter);

        // Hide soft keyboard
        textInputPublishDate.setInputType(InputType.TYPE_NULL);
        textInputSummary.setInputType(InputType.TYPE_NULL);
        textInputFormat.setInputType(InputType.TYPE_NULL);

        // Bug: UTC time, not local-time
        buildPublishDatePicker();

        textInputSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFocus = getCurrentFocus();
                Intent bookSummaryIntent = new Intent(ManualAddEditBookActivity.this, ManualAddBookSummaryActivity.class);
                String bookSummary = Objects.requireNonNull(textInputSummary.getText()).toString();
                bookSummaryIntent.putExtra(EXTRA_SUMMARY, bookSummary);
                startActivityForResult(bookSummaryIntent, ADD_BOOK_SUMMARY_REQUEST); } });
    }

    /**
     *
     * @return
     */
    @NotNull
    private ArrayList<String> setDropdownGenreList() {
        ArrayList<String> dropdownGenre = new ArrayList<>();
        dropdownGenre.add(getString(R.string.action_and_adventure));
        dropdownGenre.add(getString(R.string.autobiography));
        dropdownGenre.add(getString(R.string.biography));
        dropdownGenre.add(getString(R.string.classic));
        dropdownGenre.add(getString(R.string.comic_book));
        dropdownGenre.add(getString(R.string.cookbook));
        dropdownGenre.add(getString(R.string.crime_fiction));
        dropdownGenre.add(getString(R.string.detective_and_mystery));
        dropdownGenre.add(getString(R.string.essay));
        dropdownGenre.add(getString(R.string.fantasy));
        dropdownGenre.add(getString(R.string.graphic_novel));
        dropdownGenre.add(getString(R.string.historical_fiction));
        dropdownGenre.add(getString(R.string.history));
        dropdownGenre.add(getString(R.string.horror));
        dropdownGenre.add(getString(R.string.journalism));
        dropdownGenre.add(getString(R.string.literary_fiction));
        dropdownGenre.add(getString(R.string.memoir));
        dropdownGenre.add(getString(R.string.poetry));
        dropdownGenre.add(getString(R.string.prayer));
        dropdownGenre.add(getString(R.string.religion));
        dropdownGenre.add(getString(R.string.romance));
        dropdownGenre.add(getString(R.string.science_fiction));
        dropdownGenre.add(getString(R.string.self_help));
        dropdownGenre.add(getString(R.string.short_story));
        dropdownGenre.add(getString(R.string.suspense_and_thriller));
        dropdownGenre.add(getString(R.string.textbook));
        dropdownGenre.add(getString(R.string.travel));
        dropdownGenre.add(getString(R.string.true_crime));
        dropdownGenre.add(getString(R.string.womens_fiction));
        return dropdownGenre; }

    /**
     *
     * @return
     */
    private ArrayList<String> setDropdownFormatList() {
        ArrayList<String> dropdownFormat = new ArrayList<>();
        dropdownFormat.add(getString(R.string.paperback));
        dropdownFormat.add(getString(R.string.hardcover));
        dropdownFormat.add(getString(R.string.leather_bound));
        dropdownFormat.add(getString(R.string.ebook));
        dropdownFormat.add(getString(R.string.audiobook));
        dropdownFormat.add(getString(R.string.compact_disk));
        dropdownFormat.add(getString(R.string.non_traditional));
        return dropdownFormat; }

    /**
     *
     */
    private void buildPublishDatePicker() {
        long currentDate = System.currentTimeMillis();

        CalendarConstraints.Builder publishDateConstraintsBuilder = new CalendarConstraints.Builder();
        publishDateConstraintsBuilder.setOpenAt(currentDate);
        publishDateConstraintsBuilder.setEnd(currentDate);

        MaterialDatePicker.Builder publishDatePickerBuilder = MaterialDatePicker.Builder.datePicker();
        publishDatePickerBuilder.setTitleText(getString(R.string.select_publish_date));
        publishDatePickerBuilder.setCalendarConstraints(publishDateConstraintsBuilder.build());
        publishDatePickerBuilder.setSelection(MaterialDatePicker.todayInUtcMilliseconds());
        final MaterialDatePicker publishDatePicker = publishDatePickerBuilder.build();

        publishDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                // SQL date format for input into Book database
                Date publishDate = new Date((Long) publishDatePicker.getSelection());
                publishDateSQL = publishDate.toString();
                textInputPublishDate.setText(publishDatePicker.getHeaderText());

                textInputLanguage.requestFocus(); } });

        publishDatePicker.addOnNegativeButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFocus.requestFocus(); } });

        publishDatePicker.addOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                currentFocus.requestFocus(); } });

        textInputPublishDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFocus = getCurrentFocus();
                publishDatePicker.show(getSupportFragmentManager(), getString(R.string.enter_publish_date)); } }); }

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
        if (item.getItemId() == R.id.save_book) { saveBook(); }
        else { onExit(); }
        return true; }

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

        String format = Objects.requireNonNull(textInputFormat.getText()).toString();
        int intFormat = 0;
        if (format.equals(getString(R.string.paperback))) { intFormat = R.string.paperback; }
        else if (format.equals(getString(R.string.hardcover))) { intFormat = R.string.hardcover; }
        else if (format.equals(getString(R.string.ebook))) { intFormat = R.string.ebook; }
        else if (format.equals(getString(R.string.audiobook))) { intFormat = R.string.audiobook; }
        else if (format.equals(getString(R.string.compact_disk))) { intFormat = R.string.compact_disk; }
        else if (format.equals(getString(R.string.non_traditional))) { intFormat = R.string.non_traditional; }

        int pages = 0;
        if (!Objects.requireNonNull(textInputPages.getText()).toString().isEmpty()) {
            pages = Integer.parseInt(String.valueOf(textInputPages.getText())); }

        int pagesRead = 0;
        if (readSwitch.isChecked()) { pagesRead = pages; }

        boolean isAcceptableISBN = isAcceptableISBN(ISBN);
        boolean isAcceptableTitle = isAcceptableTitle(title);

        if (!isAcceptableTitle) {
            scrollView.smoothScrollTo(textInputTitle.getScrollX(), textInputTitle.getScrollY());
            textInputTitle.requestFocus(); }
        else if (!isAcceptableISBN) {
            scrollView.smoothScrollTo(textInputISBN.getScrollX(), textInputISBN.getScrollY());
            textInputISBN.requestFocus(); }
        else {
            Intent bookData = new Intent();
            int identification = getIntent().getIntExtra(EXTRA_IDENTIFICATION, -1);
            if (identification != 1) { bookData.putExtra(EXTRA_IDENTIFICATION, identification); }

            bookData.putExtra(EXTRA_ISBN, ISBN);
            bookData.putExtra(EXTRA_COVER, cover);
            bookData.putExtra(EXTRA_PUBLISHER, publisher);
            bookData.putExtra(EXTRA_PUBLISH_DATE, publishDate);
            bookData.putExtra(EXTRA_TITLE, title);
            bookData.putExtra(EXTRA_AUTHORS, authors);
            bookData.putExtra(EXTRA_GENRE, genre);
            bookData.putExtra(EXTRA_SUMMARY, summary);
            bookData.putExtra(EXTRA_LANGUAGE, language);
            bookData.putExtra(EXTRA_FORMAT, intFormat);
            bookData.putExtra(EXTRA_PAGES, pages);
            bookData.putExtra(EXTRA_PAGES_READ, pagesRead);
            bookData.putExtra(EXTRA_READ, readSwitch.isChecked());

            // Indicates whether the input was successful (save button was selected)
            setResult(RESULT_OK, bookData);
            // Hide the soft keyboard and close the activity
            hideSoftKeyboard(ManualAddEditBookActivity.this);
            finish(); } }

    /**
     *
     * @param title
     * @return
     */
    private boolean isAcceptableTitle(String title) {
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
        return isAcceptableTitle;
    }

    /**
     *
     * @param ISBN
     * @return
     */
    private boolean isAcceptableISBN(String ISBN) {
        boolean isAcceptableISBN = true;
        if (!ISBN.isEmpty()) {
            // Permit ISBN-10 and ISBN-13 only
            if (!ISBN.matches(ISBNRegularExpression.getRegex())) {
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
        return isAcceptableISBN; }

    /**
     * Decide what to do when the user tires to exit
     */
    private void onExit() {
        currentFocus = getCurrentFocus();
        if(!checkAddBookEmpty()) {
            final MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(ManualAddEditBookActivity.this);
            alertDialogBuilder.setMessage(getString(R.string.discard_book) + getString(R.string.question_mark));
            alertDialogBuilder.setPositiveButton(getString(R.string.discard), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    /* Bundle bookDiscardBundle = new Bundle();
                    bookDiscardBundle.putBoolean(EXTRA_BOOK_DISCARD, true);
                    ReadsFragment readsFragmentObject = new ReadsFragment();
                    readsFragmentObject.setArguments(bookDiscardBundle); */
                    hideSoftKeyboard(ManualAddEditBookActivity.this);
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
     * Decide what to do when back button is pressed
     */
    @Override
    public void onBackPressed() { onExit(); }

    /**
     * Check if there is any entry into the activity
     * @return whether there is any entry into the activity
     */
    public boolean checkAddBookEmpty() {
        ArrayList<EditText> editTexts = new ArrayList<>();
        editTexts.add(textInputISBN);
        editTexts.add(textInputTitle);
        editTexts.add(textInputAuthors);
        editTexts.add(textInputGenre);
        editTexts.add(textInputPublisher);
        editTexts.add(textInputPublishDate);
        editTexts.add(textInputSummary);
        editTexts.add(textInputLanguage);
        // editTexts.add(textInputFormat);
        editTexts.add(textInputPages);

        boolean isAddBookEmpty = true;
        for(EditText bookAttribute : editTexts) {
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

    /**
     * Convert a dp value to its float equivalent
     * @param dp the dp value to be converted
     */
    public float floatValueOf(int dp) {
        return TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, dp,
                this.getResources().getDisplayMetrics() ); }

    /**
     * Get a random flag drawable
     * All countries are apart of Africa or the Diaspora
     * @return the drawable reference of the flag
     */
    public int getRandomFlag() {
        ArrayList<Integer> countries = new ArrayList<Integer>();
        countries.add(R.drawable.algeria_square);
        countries.add(R.drawable.angola_square);
        countries.add(R.drawable.anguilla_square);
        countries.add(R.drawable.antigua_and_barbuda_square);
        countries.add(R.drawable.aruba_square);
        countries.add(R.drawable.bahamas_square);
        countries.add(R.drawable.barbados_square);
        countries.add(R.drawable.benin_square);
        countries.add(R.drawable.bonaire_square);
        countries.add(R.drawable.botswana_square);
        countries.add(R.drawable.brazil_square);
        countries.add(R.drawable.british_virgin_islands_square);
        countries.add(R.drawable.burkina_faso_square);
        countries.add(R.drawable.burundi_square);
        countries.add(R.drawable.cabo_verde_square);
        countries.add(R.drawable.cameroon_square);
        countries.add(R.drawable.cayman_islands_square);
        countries.add(R.drawable.central_african_republic_square);
        countries.add(R.drawable.chad_square);
        countries.add(R.drawable.colombia_square);
        countries.add(R.drawable.comoros_square);
        countries.add(R.drawable.cuba_square);
        countries.add(R.drawable.curacao_square);
        countries.add(R.drawable.democratic_republic_of_congo_square);
        countries.add(R.drawable.djibouti_square);
        countries.add(R.drawable.dominica_square);
        countries.add(R.drawable.dominican_republic_square);
        countries.add(R.drawable.egypt_square);
        countries.add(R.drawable.equatorial_guinea_square);
        countries.add(R.drawable.eritrea_square);
        countries.add(R.drawable.ethiopia_square);
        countries.add(R.drawable.france_square);
        countries.add(R.drawable.gabon_square);
        countries.add(R.drawable.gambia_square);
        countries.add(R.drawable.ghana_square);
        countries.add(R.drawable.grenada_square);
        countries.add(R.drawable.guinea_bissau_square);
        countries.add(R.drawable.guinea_square);
        countries.add(R.drawable.haiti_square);
        countries.add(R.drawable.ivory_coast_square);
        countries.add(R.drawable.jamaica_square);
        countries.add(R.drawable.kenya_square);
        countries.add(R.drawable.lesotho_square);
        countries.add(R.drawable.liberia_square);
        countries.add(R.drawable.libya_square);
        countries.add(R.drawable.madagascar_square);
        countries.add(R.drawable.malawi_square);
        // countries.add(R.drawable.malaysia_square);
        countries.add(R.drawable.mali_square);
        countries.add(R.drawable.martinique_square);
        countries.add(R.drawable.mauritania_square);
        countries.add(R.drawable.mauritius_square);
        countries.add(R.drawable.mexico_square);
        countries.add(R.drawable.montserrat_square);
        countries.add(R.drawable.morocco_square);
        countries.add(R.drawable.mozambique_square);
        countries.add(R.drawable.namibia_square);
        countries.add(R.drawable.niger_square);
        countries.add(R.drawable.nigeria_square);
        countries.add(R.drawable.puerto_rico_square);
        countries.add(R.drawable.republic_of_the_congo_square);
        countries.add(R.drawable.rwanda_square);
        countries.add(R.drawable.saba_island_square);
        countries.add(R.drawable.sahrawi_arab_democratic_republic_square);
        countries.add(R.drawable.saint_kitts_and_nevis_square);
        countries.add(R.drawable.sao_tome_and_principe_square);
        countries.add(R.drawable.senegal_square);
        countries.add(R.drawable.seychelles_square);
        countries.add(R.drawable.sierra_leone_square);
        countries.add(R.drawable.sint_eustatius_square);
        countries.add(R.drawable.sint_maarten_square);
        countries.add(R.drawable.somalia_square);
        countries.add(R.drawable.somaliland_square);
        countries.add(R.drawable.south_africa_sqaure);
        countries.add(R.drawable.south_sudan_square);
        countries.add(R.drawable.st_barts_square);
        countries.add(R.drawable.st_lucia_square);
        countries.add(R.drawable.st_vincent_and_the_grenadines_square);
        countries.add(R.drawable.sudan_square);
        // countries.add(R.drawable.taiwan_square);
        countries.add(R.drawable.tanzania_square);
        countries.add(R.drawable.togo_square);
        countries.add(R.drawable.trinidad_and_tobago_square);
        countries.add(R.drawable.tunisia_square);
        countries.add(R.drawable.turks_and_caicos_square);
        countries.add(R.drawable.uganda_square);
        countries.add(R.drawable.united_kingdom_square);
        countries.add(R.drawable.united_states_of_america_square);
        countries.add(R.drawable.venezuela_square);
        // countries.add(R.drawable.vietnam_square);
        countries.add(R.drawable.virgin_islands_square);
        countries.add(R.drawable.zambia_square);
        countries.add(R.drawable.zimbabwe_square);

        Random random = new Random();
        int countryIndex = random.nextInt(countries.size());
        return countries.get(countryIndex); }
}
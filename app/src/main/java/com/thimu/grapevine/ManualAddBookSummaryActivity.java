package com.thimu.grapevine;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.Objects;

/**
 * An activity for the user to attribute the summary of a book
 *
 * @author Obed Ngigi
 * @version 11.07.2020
 */
public class ManualAddBookSummaryActivity extends AppCompatActivity {

    // Intent key
    public static final String EXTRA_WRITE_SUMMARY =
            "com.thimu.grapevine.EXTRA_WRITE_SUMMARY";

    // Elements of the activity
    private ActionBar toolbar;
    private ScrollView scrollView;

    private TextInputEditText textInputWriteSummary;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_add_book_summary);

        // Configure actionbar
        toolbar = Objects.requireNonNull(getSupportActionBar());
        toolbar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        toolbar.setElevation(0);

        scrollView = findViewById(R.id.manualAddBookSummaryScrollView);
        scrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if(scrollView.canScrollVertically(-1)) { setActionBarElevation(4); }
                else { setActionBarElevation(0); } } });

        Intent bookSummaryIntent = getIntent();
        String summary = bookSummaryIntent.getStringExtra(ManualAddBookActivity.EXTRA_SUMMARY);
        textInputWriteSummary = findViewById(R.id.textInputEnterWriteSummary);
        textInputWriteSummary.setText(summary);
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
     * Add summary to book
     */
    public void saveBookSummary() {
        String summary = Objects.requireNonNull(textInputWriteSummary.getText()).toString();

        Intent bookSummaryResultIntent = new Intent();
        bookSummaryResultIntent.putExtra(EXTRA_WRITE_SUMMARY, summary);

        // Indicates whether the input was successful (save button was selected)
        setResult(RESULT_OK, bookSummaryResultIntent);
        // Close the activity
        finish(); }

    /**
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_book_summary_menu, menu);
        return true; }

    /**
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.save_book_summary) {
            saveBookSummary();
            return true; }
        else { return super.onOptionsItemSelected(item); } }
}
package com.thimu.grapevine;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.Objects;

/**
 * An activity for the user to attribute the summary of a book
 *
 * @author Kĩthia Ngigĩ
 * @version 12.07.2020
 */
public class ManualAddBookSummaryActivity extends AppCompatActivity {

    // Intent key
    public static final String EXTRA_WRITE_SUMMARY =
            "com.thimu.grapevine.EXTRA_WRITE_SUMMARY";

    // Elements of the activity
    private ActionBar toolbar;
    private NestedScrollView nestedScrollView;

    private EditText textInputWriteSummary;
    private String previousSummary;

    /**
     * Create the activity
     * @param savedInstanceState the last saved state of the activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_add_book_summary);

        // Configure actionbar
        toolbar = Objects.requireNonNull(getSupportActionBar());
        toolbar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        toolbar.setElevation(0);

        nestedScrollView = findViewById(R.id.manualAddBookSummaryNestedScrollView);
        nestedScrollView.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
            @Override
            public void onScrollChanged() {
                if (nestedScrollView.canScrollVertically(-1)) { setActionBarElevation(4); }
                else { setActionBarElevation(0); } } });

        Intent bookSummaryIntent = getIntent();
        previousSummary = bookSummaryIntent.getStringExtra(ManualAddBookActivity.EXTRA_SUMMARY);
        textInputWriteSummary = findViewById(R.id.textInputEnterWriteSummary);
        textInputWriteSummary.setText(previousSummary);
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
        if (item.getItemId() == R.id.save_book_summary) { saveBookSummary(); }
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
        if(!previousSummary.contentEquals(textInputWriteSummary.getText().toString())) {
            final MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(ManualAddBookSummaryActivity.this);
            alertDialogBuilder.setMessage(getString(R.string.discard_changes) + getString(R.string.question_mark));
            alertDialogBuilder.setPositiveButton(getString(R.string.discard), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish(); } });

            alertDialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {} });

            alertDialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {} });

            alertDialogBuilder.show(); }
        else { finish(); } }
}
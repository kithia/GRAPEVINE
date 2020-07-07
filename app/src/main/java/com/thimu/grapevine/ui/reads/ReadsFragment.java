package com.thimu.grapevine.ui.reads;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.thimu.grapevine.ManualAddBookActivity;
import com.thimu.grapevine.R;
import com.thimu.grapevine.ui.Book;
import com.thimu.grapevine.ui.BookAdapter;
import com.thimu.grapevine.ui.BookViewModel;

import java.util.List;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

/**
 * A fragment to display the user's book library
 *
 * @author Obed Ngigi
 * @version 07.07.2020
 */
public class ReadsFragment extends Fragment {

    //
    public static final int ADD_BOOK_REQUEST = 0;

    // Elements of the fragment
    private Toolbar toolbar;
    private SearchView searchView;
    private ChipGroup chipBar;
    private Chip chipSort;
    private Chip chipGroup;
    private FloatingActionButton floatingActionButton;

    //
    private BookViewModel bookViewModel;

    /**
     * Create the fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return the fragment view
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Window window = requireActivity().getWindow();
        window.setStatusBarColor(Color.TRANSPARENT);

        View view = inflater.inflate(R.layout.fragment_reads, container, false);
        toolbar = view.findViewById(R.id.readsToolbar);
        searchView = view.findViewById(R.id.readsSearchView);
        chipBar = view.findViewById(R.id.readsChipBar);
        chipSort = view.findViewById(R.id.readsChipSort);
        chipGroup = view.findViewById(R.id.readsChipGroup);
        floatingActionButton = view.findViewById(R.id.readsFloatingActionButton);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ManualAddBookActivity.class);
                startActivityForResult(intent, ADD_BOOK_REQUEST); } });

        RecyclerView recyclerView = view.findViewById(R.id.readsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider_margin));
        recyclerView.addItemDecoration(dividerItemDecoration);

        final BookAdapter adapter = new BookAdapter();
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (recyclerView.canScrollVertically(-1)) {
                    // Show elevation
                    setAppBarElevation(4); }
                else {
                    // Remove elevation
                    setAppBarElevation(0); }

                if (dy > 0) {
                    floatingActionButton.hide(); }
                else {
                    floatingActionButton.show(); } } });

        bookViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        bookViewModel.getAllBooks().observe(getViewLifecycleOwner(), new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                adapter.setBooks(books); } });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                final Book swipedBook = adapter.getBookAt(viewHolder.getAdapterPosition());
                bookViewModel.remove(swipedBook);
                Snackbar.make(requireView(), swipedBook.getTitle() + getString(R.string.lc_was_removed_from) + getString(R.string.lc_your_library), Snackbar.LENGTH_LONG)
                        .setAction(getString(R.string.undo), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) { bookViewModel.insert(swipedBook); } })
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                        .setBackgroundTint(Color.WHITE)
                        .setTextColor(getResources().getColor(R.color.colorPrimary))
                        .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                        .show(); } }).attachToRecyclerView(recyclerView);

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_BOOK_REQUEST && resultCode == RESULT_OK) {
            String ISBN = data.getStringExtra(ManualAddBookActivity.EXTRA_ISBN);
            String publisher = data.getStringExtra(ManualAddBookActivity.EXTRA_PUBLISHER);
            String publishYear = data.getStringExtra(ManualAddBookActivity.EXTRA_PUBLISH_DATE);
            String title = data.getStringExtra(ManualAddBookActivity.EXTRA_TITLE);
            String authors = data.getStringExtra(ManualAddBookActivity.EXTRA_AUTHORS);
            String genre = data.getStringExtra(ManualAddBookActivity.EXTRA_GENRE);
            String language = data.getStringExtra(ManualAddBookActivity.EXTRA_LANGUAGE);
            String pages = data.getStringExtra(ManualAddBookActivity.EXTRA_PAGES);

            Book book = new Book(ISBN, publisher, publishYear, Objects.requireNonNull(title), authors, genre, null, language, pages);
            bookViewModel.insert(book);

            Snackbar.make(requireView(), book.getTitle() + getString(R.string.lc_was_saved_to)
                    + getString(R.string.lc_your_library), Snackbar.LENGTH_LONG)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                    .setBackgroundTint(Color.WHITE)
                    .setTextColor(getResources().getColor(R.color.colorPrimary))
                    .setActionTextColor(getResources().getColor(R.color.colorPrimary))
                    .show(); } }

    /**
     * Set the elevation of the appbar
     * @param elevation the dp value of the elevation
     */
    public void setAppBarElevation (int elevation) {
        float floatElevation = TypedValue.applyDimension( TypedValue.COMPLEX_UNIT_DIP, elevation,
                requireContext().getResources().getDisplayMetrics() );
        toolbar.setElevation(floatElevation);
        searchView.setElevation(floatElevation);
        chipBar.setElevation(floatElevation);
        chipSort.setElevation(floatElevation);
        chipGroup.setElevation(floatElevation); }
}

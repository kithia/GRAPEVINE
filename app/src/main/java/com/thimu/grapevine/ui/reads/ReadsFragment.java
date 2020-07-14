package com.thimu.grapevine.ui.reads;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
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

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static android.app.Activity.RESULT_OK;
import static androidx.core.content.ContextCompat.getColor;

/**
 * A fragment to display the user's book library
 *
 * @author Obed Ngigi
 * @version 14.07.2020
 */
public class ReadsFragment extends Fragment {

    //
    public static final int ADD_BOOK_REQUEST = 0;

    // Elements of the fragment
    private SearchView searchView;
    private View chipbar;
    private Chip chipSort;
    private Chip chipGroup;
    private FloatingActionButton floatingActionButton;

    //
    private BookAdapter adapter;
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
        window.setStatusBarColor(getColor(requireContext(), android.R.color.white));

        // Configure custom actionbar
        ActionBar toolbar = Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar());
        toolbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        toolbar.setBackgroundDrawable(new ColorDrawable(getColor(requireContext(), android.R.color.white)));
        toolbar.setDisplayShowCustomEnabled(true);
        toolbar.setCustomView(R.layout.fragment_reads_toolbar);

        View view = inflater.inflate(R.layout.fragment_reads, container, false);
        searchView = view.findViewById(R.id.readsSearchView);
        chipbar = view.findViewById(R.id.readsChipbar);
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

        adapter = new BookAdapter();
        recyclerView.setAdapter(adapter);
        recyclerViewScrollListener(recyclerView);

        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        bookViewModel.getAllBooks().observe(getViewLifecycleOwner(), new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                adapter.setBooks(books); } });

        /* onBookDiscard(); */

        /* searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false; }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false; } }); */

        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                final Book swipedBook = adapter.getBookAt(viewHolder.getAdapterPosition());
                final MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(requireContext());
                alertDialogBuilder.setTitle(R.string.remove_book);
                alertDialogBuilder.setMessage(getString(R.string.are_you_sure_you_want_to_remove)
                + getString(R.string.open_single_quotation_mark) + swipedBook.getTitle()
                + getString(R.string.close_single_quotation_mark) + getString(R.string.question_mark));
                alertDialogBuilder.setPositiveButton(getString(R.string.remove), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        bookViewModel.remove(swipedBook);
                        adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                        adapter.notifyItemRangeChanged(viewHolder.getAdapterPosition(), adapter.getItemCount());

                        Snackbar.make(requireView(), getString(R.string.open_single_quotation_mark) + swipedBook.getTitle()
                                + getString(R.string.close_single_quotation_mark) + getString(R.string.lc_was_removed_from)
                                + getString(R.string.lc_your_library), Snackbar.LENGTH_LONG)
                                .setAction(getString(R.string.undo), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        bookViewModel.insert(swipedBook);
                                        adapter.notifyItemInserted(viewHolder.getAdapterPosition());
                                        adapter.notifyItemRangeChanged(viewHolder.getAdapterPosition(), adapter.getItemCount());
                                        adapter.notifyDataSetChanged(); } })
                                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                                .setBackgroundTint(Color.WHITE)
                                .setTextColor(getColor(requireContext(), R.color.colorPrimaryDark))
                                .setActionTextColor(getColor(requireContext(), R.color.colorPrimaryDark))
                                .show(); } });

                alertDialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) { } });

                alertDialogBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) { } });

                alertDialogBuilder.show(); }

            // Configure swipe decoration
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                    .addSwipeRightBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.white))
                    .addSwipeRightActionIcon(R.drawable.ic_outline_delete)
                    .setSwipeRightActionIconTint(ContextCompat.getColor(requireContext(), R.color.colorError))
                    .addSwipeRightLabel(getString(R.string.remove))
                    .setSwipeRightLabelColor(ContextCompat.getColor(requireContext(), R.color.colorError))
                    .create()
                    .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive); } };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return view;
    }

    /**
     *
     */
    private void onBookDiscard() {
        if(getArguments() != null) {
            boolean bookDiscarded = getArguments().getBoolean(ManualAddBookActivity.EXTRA_BOOK_DISCARD);
            if (bookDiscarded) {
                Snackbar.make(requireView(), getString(R.string.book_discarded)
                    , Snackbar.LENGTH_SHORT)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                    .setBackgroundTint(Color.WHITE)
                    .setTextColor(getColor(requireContext(), R.color.colorPrimaryDark))
                    .show(); } } }

    /**
     * Set custom onScrollListener
     * @param recyclerView
     */
    private void recyclerViewScrollListener(RecyclerView recyclerView) {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            // Show & remove elevation
            if (recyclerView.canScrollVertically(-1)) { setElevation(chipbar, 4); }
            else { setElevation(chipbar,0); } } }); }

    /**
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == ADD_BOOK_REQUEST && resultCode == RESULT_OK) {
            String ISBN = data.getStringExtra(ManualAddBookActivity.EXTRA_ISBN);
            String publisher = data.getStringExtra(ManualAddBookActivity.EXTRA_PUBLISHER);
            String publishDate = data.getStringExtra(ManualAddBookActivity.EXTRA_PUBLISH_DATE);
            String title = data.getStringExtra(ManualAddBookActivity.EXTRA_TITLE);
            String authors = data.getStringExtra(ManualAddBookActivity.EXTRA_AUTHORS);
            String genre = data.getStringExtra(ManualAddBookActivity.EXTRA_GENRE);
            String summary = data.getStringExtra(ManualAddBookActivity.EXTRA_SUMMARY);
            String language = data.getStringExtra(ManualAddBookActivity.EXTRA_LANGUAGE);
            int pages = data.getIntExtra(ManualAddBookActivity.EXTRA_PAGES, 0);
            boolean read = data.getBooleanExtra(ManualAddBookActivity.EXTRA_READ, false);

            int pagesRead = 0;
            if (read) { pagesRead = pages; }

            Book book = new Book(ISBN, R.drawable.ic_kenya_square, publisher, publishDate, Objects.requireNonNull(title), authors, genre, summary, language, pages, pagesRead, read);
            bookViewModel.insert(book);
            adapter.notifyItemInserted(0);
            adapter.notifyDataSetChanged();

            Snackbar.make(requireView(), getString(R.string.open_single_quotation_mark) + book.getTitle() + getString(R.string.close_single_quotation_mark) + getString(R.string.lc_was_saved_to)
                + getString(R.string.lc_your_library), Snackbar.LENGTH_SHORT)
                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                .setBackgroundTint(Color.WHITE)
                .setTextColor(getColor(requireContext(), R.color.colorPrimaryDark))
                .show(); }

         /* onBookDiscard(); */ }

    /**
     * Set the elevation of a view
     * @param elevation the dp value of the elevation
     * @param view the view to be de/elevated
     */
    public void setElevation(View view, int elevation) {
        float floatElevation = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, elevation,
                requireContext().getResources().getDisplayMetrics());
        view.setElevation(floatElevation); }
}

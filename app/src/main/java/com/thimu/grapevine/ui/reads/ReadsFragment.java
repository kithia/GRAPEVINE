package com.thimu.grapevine.ui.reads;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.chip.Chip;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.thimu.grapevine.ManualAddEditBookActivity;
import com.thimu.grapevine.R;
import com.thimu.grapevine.ui.Book;
import com.thimu.grapevine.ui.BookAdapter;
import com.thimu.grapevine.ui.BookViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

import static android.app.Activity.RESULT_OK;
import static androidx.core.content.ContextCompat.getColor;

/**
 * The main fragment of MainActivity
 * A fragment to display the user's book library
 *
 * @author Kĩthia Ngigĩ
 * @version 05.08.2020
 */
public class ReadsFragment extends Fragment {

    //
    public static final int ADD_BOOK_REQUEST = 0;
    public static final int EDIT_BOOK_REQUEST = 1;

    // Intent key
    public static final String EXTRA_BOOK =
            "com.thimu.grapevine.EXTRA_BOOK";

    // Elements of the fragment
    private AppBarLayout appbarLayout;
    private NestedScrollView nestedScrollView;
    private SearchView searchView;

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
        /* ActionBar toolbar = Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar());
        toolbar.hide();
        toolbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        toolbar.setBackgroundDrawable(new ColorDrawable(getColor(requireContext(), android.R.color.white)));
        toolbar.setDisplayShowCustomEnabled(true);
        toolbar.setCustomView(R.layout.fragment_reads_searchbar); */

        View view = inflater.inflate(R.layout.fragment_reads, container, false);
        appbarLayout = view.findViewById(R.id.readsAppBarLayout);
        searchView = view.findViewById(R.id.readsSearchView);
        Chip chipSort = view.findViewById(R.id.readsChipSort);
        Chip chipGroup = view.findViewById(R.id.readsChipGroup);
        nestedScrollView = view.findViewById(R.id.readsNestedScrollView);
        RecyclerView recyclerView = view.findViewById(R.id.readsRecyclerView);
        FloatingActionButton floatingActionButton = view.findViewById(R.id.readsFloatingActionButton);

        buildSearchView(searchView);

        nestedScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
           @Override
           public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
               if (nestedScrollView.canScrollVertically(-1)) {
                   ViewCompat.setElevation(appbarLayout, floatValueOf(4)); }
               else { ViewCompat.setElevation(appbarLayout, 0); }

           if (searchView.hasFocus()) { searchView.clearFocus(); } } });

        buildRecyclerView(recyclerView);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ManualAddEditBookActivity.class);
                startActivityForResult(intent, ADD_BOOK_REQUEST); } });

        /* onBookDiscard(); */

        return view;
    }

    /**
     * Build the SearchView
     * @param searchView the SearchView
     */
    private void buildSearchView(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) { return false; }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                return false; } }); }

    /**
     * Build the RecyclerView
     * @param recyclerView the RecyclerView
     */
    private void buildRecyclerView(RecyclerView recyclerView) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(false);

        adapter = new BookAdapter();
        recyclerView.setAdapter(adapter);

        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);
        bookViewModel.getAllBooks().observe(getViewLifecycleOwner(), new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) { adapter.setBooks(books); } });



        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false; }

            @Override
            public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                // Swiped left
                /* if (direction == ItemTouchHelper.LEFT) {
                    adapter.notifyItemChanged(viewHolder.getAdapterPosition());
                    final Book swipedBook = adapter.getBookAt(viewHolder.getAdapterPosition());

                    Intent intent = new Intent(requireContext(), ManualAddEditBookActivity.class);
                    intent.putExtra(ManualAddEditBookActivity.EXTRA_IDENTIFICATION, swipedBook.getIdentification());
                    intent.putExtra(ManualAddEditBookActivity.EXTRA_ISBN, swipedBook.getISBN());
                    intent.putExtra(ManualAddEditBookActivity.EXTRA_COVER, swipedBook.getCover());
                    intent.putExtra(ManualAddEditBookActivity.EXTRA_PUBLISHER, swipedBook.getPublisher());
                    intent.putExtra(ManualAddEditBookActivity.EXTRA_PUBLISH_DATE, swipedBook.getPublishDate());
                    intent.putExtra(ManualAddEditBookActivity.EXTRA_TITLE, swipedBook.getTitle());
                    intent.putExtra(ManualAddEditBookActivity.EXTRA_AUTHORS, swipedBook.getAuthors());
                    intent.putExtra(ManualAddEditBookActivity.EXTRA_GENRE, swipedBook.getGenre());
                    intent.putExtra(ManualAddEditBookActivity.EXTRA_SUMMARY, swipedBook.getSummary());
                    intent.putExtra(ManualAddEditBookActivity.EXTRA_LANGUAGE, swipedBook.getLanguage());
                    intent.putExtra(ManualAddEditBookActivity.EXTRA_FORMAT, swipedBook.getFormat());
                    intent.putExtra(ManualAddEditBookActivity.EXTRA_PAGES, swipedBook.getPages());
                    intent.putExtra(ManualAddEditBookActivity.EXTRA_PAGES_READ, swipedBook.getPagesRead());
                    intent.putExtra(ManualAddEditBookActivity.EXTRA_READ, swipedBook.getRead());
                    startActivityForResult(intent, EDIT_BOOK_REQUEST); } */
                // Swiped right
                if (direction == ItemTouchHelper.RIGHT) {
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
                                    .setTextColor(getColor(requireContext(), R.color.colorPrimary))
                                    .setActionTextColor(getColor(requireContext(), R.color.colorPrimary))
                                    .show(); } });

                    alertDialogBuilder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) { } });

                    alertDialogBuilder.show(); } }

            // Configure swipe decoration
            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                // Swiped left
                /* if (dX < 0) {
                    new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                            .addSwipeLeftBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.white))
                            .addSwipeLeftActionIcon(R.drawable.ic_outline_edit)
                            .setSwipeLeftActionIconTint(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
                            .addSwipeLeftLabel(getString(R.string.edit))
                            .setSwipeLeftLabelColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
                            .create()
                            .decorate();
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                } */
                // Swiped right
                if (dX > 0) {
                    new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                            .addSwipeRightBackgroundColor(ContextCompat.getColor(requireContext(), android.R.color.white))
                            .addSwipeRightActionIcon(R.drawable.ic_outline_delete)
                            .setSwipeRightActionIconTint(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
                            .addSwipeRightLabel(getString(R.string.remove))
                            .setSwipeRightLabelColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
                            .create()
                            .decorate();
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive); } } };

        /* adapter.setOnItemClickListener(new BookAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {
                Intent intent = new Intent(requireContext(), ManualAddEditBookActivity.class);
                intent.putExtra(ManualAddEditBookActivity.EXTRA_IDENTIFICATION, book.getIdentification());
                intent.putExtra(ManualAddEditBookActivity.EXTRA_ISBN, book.getISBN());
                intent.putExtra(ManualAddEditBookActivity.EXTRA_COVER, book.getCover());
                intent.putExtra(ManualAddEditBookActivity.EXTRA_PUBLISHER, book.getPublisher());
                intent.putExtra(ManualAddEditBookActivity.EXTRA_PUBLISH_DATE, book.getPublishDate());
                intent.putExtra(ManualAddEditBookActivity.EXTRA_TITLE, book.getTitle());
                intent.putExtra(ManualAddEditBookActivity.EXTRA_AUTHORS, book.getAuthors());
                intent.putExtra(ManualAddEditBookActivity.EXTRA_GENRE, book.getGenre());
                intent.putExtra(ManualAddEditBookActivity.EXTRA_SUMMARY, book.getSummary());
                intent.putExtra(ManualAddEditBookActivity.EXTRA_LANGUAGE, book.getLanguage());
                intent.putExtra(ManualAddEditBookActivity.EXTRA_FORMAT, book.getFormat());
                intent.putExtra(ManualAddEditBookActivity.EXTRA_PAGES, book.getPages());
                intent.putExtra(ManualAddEditBookActivity.EXTRA_PAGES_READ, book.getPagesRead());
                intent.putExtra(ManualAddEditBookActivity.EXTRA_READ, book.getRead());
                startActivityForResult(intent, EDIT_BOOK_REQUEST); } }); */

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    /**
     *
     */
    /* private void onBookDiscard() {
        if(getArguments() != null) {
            boolean bookDiscarded = getArguments().getBoolean(ManualAddEditBookActivity.EXTRA_BOOK_DISCARD);
            if (bookDiscarded) {
                Snackbar.make(requireView(), getString(R.string.book_discarded)
                    , Snackbar.LENGTH_SHORT)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                    .setBackgroundTint(Color.WHITE)
                    .setTextColor(getColor(requireContext(), R.color.colorPrimary))
                    .show(); } } } */

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
            String ISBN = data.getStringExtra(ManualAddEditBookActivity.EXTRA_ISBN);
            int cover = data.getIntExtra(ManualAddEditBookActivity.EXTRA_COVER, 0);
            String publisher = data.getStringExtra(ManualAddEditBookActivity.EXTRA_PUBLISHER);
            String publishDate = data.getStringExtra(ManualAddEditBookActivity.EXTRA_PUBLISH_DATE);
            String title = data.getStringExtra(ManualAddEditBookActivity.EXTRA_TITLE);
            String authors = data.getStringExtra(ManualAddEditBookActivity.EXTRA_AUTHORS);
            String genre = data.getStringExtra(ManualAddEditBookActivity.EXTRA_GENRE);
            String summary = data.getStringExtra(ManualAddEditBookActivity.EXTRA_SUMMARY);
            String language = data.getStringExtra(ManualAddEditBookActivity.EXTRA_LANGUAGE);
            int format = data.getIntExtra(ManualAddEditBookActivity.EXTRA_FORMAT, 0);
            int pages = data.getIntExtra(ManualAddEditBookActivity.EXTRA_PAGES, 0);
            int pagesRead = data.getIntExtra(ManualAddEditBookActivity.EXTRA_PAGES_READ, 0);
            boolean read = data.getBooleanExtra(ManualAddEditBookActivity.EXTRA_READ, false);

            Book book = new Book(ISBN, cover, publisher, publishDate, Objects.requireNonNull(title), authors, genre, summary, language, format, pages, pagesRead, read);
            bookViewModel.insert(book);
            adapter.notifyItemInserted(0);
            adapter.notifyDataSetChanged();

            Snackbar.make(requireView(), getString(R.string.open_single_quotation_mark) + book.getTitle() + getString(R.string.close_single_quotation_mark) + getString(R.string.lc_was_saved_to)
                + getString(R.string.lc_your_library), Snackbar.LENGTH_SHORT)
                .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                .setBackgroundTint(Color.WHITE)
                .setTextColor(getColor(requireContext(), R.color.colorPrimary))
                .show(); }
        else if (requestCode == EDIT_BOOK_REQUEST && resultCode == RESULT_OK) {
            int identification = data.getIntExtra(ManualAddEditBookActivity.EXTRA_IDENTIFICATION, -1);
            if (identification == -1) {
                Snackbar.make(requireView(), getString(R.string.open_single_quotation_mark) + data.getStringExtra(ManualAddEditBookActivity.EXTRA_TITLE) + getString(R.string.close_single_quotation_mark) + getString(R.string.lc_could_not_be_saved)
                        , Snackbar.LENGTH_SHORT)
                        .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                        .setBackgroundTint(Color.WHITE)
                        .setTextColor(getColor(requireContext(), R.color.colorPrimary))
                        .show(); }

            String ISBN = data.getStringExtra(ManualAddEditBookActivity.EXTRA_ISBN);
            int cover = data.getIntExtra(ManualAddEditBookActivity.EXTRA_COVER, 0);
            String publisher = data.getStringExtra(ManualAddEditBookActivity.EXTRA_PUBLISHER);
            String publishDate = data.getStringExtra(ManualAddEditBookActivity.EXTRA_PUBLISH_DATE);
            String title = data.getStringExtra(ManualAddEditBookActivity.EXTRA_TITLE);
            String authors = data.getStringExtra(ManualAddEditBookActivity.EXTRA_AUTHORS);
            String genre = data.getStringExtra(ManualAddEditBookActivity.EXTRA_GENRE);
            String summary = data.getStringExtra(ManualAddEditBookActivity.EXTRA_SUMMARY);
            String language = data.getStringExtra(ManualAddEditBookActivity.EXTRA_LANGUAGE);
            int format = data.getIntExtra(ManualAddEditBookActivity.EXTRA_FORMAT, 0);
            int pages = data.getIntExtra(ManualAddEditBookActivity.EXTRA_PAGES, 0);
            int pagesRead = data.getIntExtra(ManualAddEditBookActivity.EXTRA_PAGES_READ, 0);
            boolean read = data.getBooleanExtra(ManualAddEditBookActivity.EXTRA_READ, false);

            Book book = new Book(ISBN, cover, publisher, publishDate, Objects.requireNonNull(title), authors, genre, summary, language, format, pages, pagesRead, read);
            book.setIdentification(identification);
            bookViewModel.insert(book);
            adapter.notifyItemInserted(0);
            adapter.notifyDataSetChanged();

            Snackbar.make(requireView(), getString(R.string.open_single_quotation_mark) + book.getTitle() + getString(R.string.close_single_quotation_mark) + getString(R.string.lc_was_updated)
                    , Snackbar.LENGTH_SHORT)
                    .setAnimationMode(BaseTransientBottomBar.ANIMATION_MODE_SLIDE)
                    .setBackgroundTint(Color.WHITE)
                    .setTextColor(getColor(requireContext(), R.color.colorPrimary))
                    .show(); }

         /* onBookDiscard(); */ }

    /**
     * Convert a dp value to its float equivalent
     * @param dp the dp value to be converted
     */
    public float floatValueOf(int dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp,
                requireContext().getResources().getDisplayMetrics()); }
}

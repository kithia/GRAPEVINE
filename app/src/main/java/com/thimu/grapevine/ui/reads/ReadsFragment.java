package com.thimu.grapevine.ui.reads;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thimu.grapevine.R;
import com.thimu.grapevine.ui.Book;
import com.thimu.grapevine.ui.BookAdapter;
import com.thimu.grapevine.ui.BookViewModel;

import java.util.List;

/**
 * A fragment to display the user's book library
 *
 * @author Obed Ngigi
 * @version 04.07.2020
 */
public class ReadsFragment extends Fragment {

    // Attributes of the fragment
    private Toolbar toolbar;
    private SearchView searchView;
    private ChipGroup chipBar;
    private Chip chipSort;
    private Chip chipGroup;
    private FloatingActionButton floatingActionButton;

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

        RecyclerView recyclerView = view.findViewById(R.id.readsRecyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(getResources().getDrawable(R.drawable.ic_divider_margin));
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

                if (dy > 0 && floatingActionButton.getVisibility() == View.VISIBLE) {
                    floatingActionButton.hide(); }
                else if (dy < 0 && floatingActionButton.getVisibility() != View.VISIBLE) {
                    floatingActionButton.show(); } } });

        BookViewModel bookViewModel = ViewModelProviders.of(this).get(BookViewModel.class);
        bookViewModel.getAllBooks().observe(getViewLifecycleOwner(), new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {
                adapter.setBooks(books); } });
        return view;
    }

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

package com.thimu.grapevine.ui.reads;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.thimu.grapevine.R;

/**
 * The reads fragment
 *
 * @author Obed Ngigi
 * @version 03.07.2020
 */
public class ReadsFragment extends Fragment {
    private Toolbar toolbar;
    private SearchView searchView;
    private ChipGroup chipBar;
    private Chip chipSort;
    private Chip chipGroup;
    private FloatingActionButton floatingActionButton;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Window window = requireActivity().getWindow();
        window.setStatusBarColor(Color.TRANSPARENT);

        return inflater.inflate(R.layout.fragment_reads, container, false);
    }
}

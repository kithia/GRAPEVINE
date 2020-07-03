package com.thimu.grapevine.ui.search;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.thimu.grapevine.R;

/**
 * A fragment for the user to search ISBN books
 *
 * @author Obed Ngigi
 * @version 03.07.2020
 */
public class SearchFragment extends Fragment {

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

        return inflater.inflate(R.layout.fragment_search, container, false);
    }
}
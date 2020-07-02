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

import java.util.Objects;

/**
 * The search fragment
 *
 * @author Obed Ngigi
 * @version 01.07.2020
 */
public class SearchFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Window window = Objects.requireNonNull(getActivity()).getWindow();
        window.setStatusBarColor(Color.TRANSPARENT);

        View view = inflater.inflate(R.layout.fragment_search, container, false);
        return view;
    }
}
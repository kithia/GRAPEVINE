package com.thimu.grapevine.ui.bookshops;

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
 * The bookshops fragment
 *
 * @author Obed Ngigi
 * @version 03.07.2020
 */
public class BookshopsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Window window = requireActivity().getWindow();
        window.setStatusBarColor(Color.rgb(149, 117, 205));

        return inflater.inflate(R.layout.fragment_bookshops, container, false);
    }
}

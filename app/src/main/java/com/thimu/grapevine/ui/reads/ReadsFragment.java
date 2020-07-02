package com.thimu.grapevine.ui.reads;

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
 * The reads fragment
 *
 * @author Obed Ngigi
 * @version 01.07.2020
 */
public class ReadsFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Window window = Objects.requireNonNull(getActivity()).getWindow();
        window.setStatusBarColor(Color.TRANSPARENT);

        View view = inflater.inflate(R.layout.fragment_reads, container, false);
        return view;
    }
}

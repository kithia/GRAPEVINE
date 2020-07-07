package com.thimu.grapevine.ui.conversation;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.thimu.grapevine.R;

import java.util.Objects;

import static androidx.core.content.ContextCompat.getColor;

/**
 * A fragment for the user to converse with other users
 *
 * @author Obed Ngigi
 * @version 03.07.2020
 */
public class ConversationFragment extends Fragment {

    /**
     * Create the fragment
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return the fragment view
     */
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        requireActivity().getWindow().setStatusBarColor(getColor(requireContext(), R.color.colorPrimary));

        // Configure actionbar
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar()).setBackgroundDrawable(new ColorDrawable(getColor(requireContext(), R.color.colorPrimary)));

        return inflater.inflate(R.layout.fragment_conversation, container, false);
    }
}
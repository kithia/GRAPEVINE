package com.thimu.grapevine.ui.conversation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.thimu.grapevine.R;

import java.util.Objects;

import static androidx.core.content.ContextCompat.getColor;

/**
 * A fragment to display the user's forum conversations
 *
 * @author Kĩthia Ngigĩ
 * @version 27.07.2020
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
        Window window = requireActivity().getWindow();
        window.setStatusBarColor(getColor(requireContext(), android.R.color.white));

        // Configure actionbar
        /* ActionBar toolbar = Objects.requireNonNull(((AppCompatActivity) requireActivity()).getSupportActionBar());
        toolbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_TITLE);
        toolbar.setBackgroundDrawable(new ColorDrawable(getColor(requireContext(), android.R.color.white)));
        toolbar.show(); */

        View view = inflater.inflate(R.layout.fragment_conversation, container, false);
        MaterialToolbar materialToolbar = view.findViewById(R.id.conversationToolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(materialToolbar);

        return view;
    }
}
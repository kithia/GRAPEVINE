package com.thimu.grapevine.ui.conversation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.thimu.grapevine.R;

import static androidx.core.content.ContextCompat.getColor;

/**
 * A fragment to display the user's forum conversations
 *
 * @author Kĩthia Ngigĩ
 * @version 16.07.2020
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

        return inflater.inflate(R.layout.fragment_conversation, container, false);
    }
}
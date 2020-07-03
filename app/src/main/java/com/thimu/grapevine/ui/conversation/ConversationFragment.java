package com.thimu.grapevine.ui.conversation;

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
 * The conversation fragment
 *
 * @author Obed Ngigi
 * @version 03.07.2020
 */
public class ConversationFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Window window = requireActivity().getWindow();
        window.setStatusBarColor(Color.rgb(149, 117, 205));

        return inflater.inflate(R.layout.fragment_conversation, container, false);
    }
}
package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.inbox;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MessageCenterViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.TitleScreenViewModel;

/**
 * TitleScreenFragment class
 *
 * @author TritiumGamingStudios
 */
public class InboxMessageFragment extends Fragment {

    private TitleScreenViewModel titleScreenViewModel = null;
    private MessageCenterViewModel messageInboxViewModel = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) { // OBTAIN VIEW MODEL REFERENCE
        if (titleScreenViewModel == null)
            titleScreenViewModel = new ViewModelProvider(requireActivity()).get(TitleScreenViewModel.class);
        // INITIALIZE VIEW MODEL
        if (getContext() != null)
            titleScreenViewModel.init(getContext());

        if (messageInboxViewModel == null)
            messageInboxViewModel = new ViewModelProvider(requireActivity()).get(MessageCenterViewModel.class);


        return inflater.inflate(R.layout.fragment_msginbox_message, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // INITIALIZE VIEWS
        AppCompatTextView label_title = view.findViewById(R.id.textview_title);
        AppCompatImageView button_back = view.findViewById(R.id.button_prev);

        // TEXT SIZE
        label_title.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);

        // LISTENERS
        button_back.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

    }

}
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.RSSParser;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MessageCenterViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.TitleScreenViewModel;

/**
 * TitleScreenFragment class
 *
 * @author TritiumGamingStudios
 */
public class InboxFragment extends Fragment {

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


        return inflater.inflate(R.layout.fragment_msginbox, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        if (messageInboxViewModel == null) {
            messageInboxViewModel = new ViewModelProvider(requireActivity()).get(MessageCenterViewModel.class);
        }

        // INITIALIZE VIEWS
        AppCompatTextView label_title = view.findViewById(R.id.textview_title);
        AppCompatTextView label_extranews = view.findViewById(R.id.textview_extranews);
        AppCompatTextView label_petnews = view.findViewById(R.id.textview_petupdateinbox);
        AppCompatTextView label_phasnews = view.findViewById(R.id.textview_phasupdateinbox);
        AppCompatImageView button_back = view.findViewById(R.id.button_prev);
        ConstraintLayout button_extranews = view.findViewById(R.id.constraintLayout_inboxnews_listener);
        ConstraintLayout button_petnews = view.findViewById(R.id.constraintLayout_petnews_listener);
        ConstraintLayout button_phasnews = view.findViewById(R.id.constraintLayout_phasnews_listener);

        // TEXT SIZE
        label_title.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);
        label_extranews.setAutoSizeTextTypeUniformWithConfiguration(12, 30, 1, TypedValue.COMPLEX_UNIT_SP);
        label_petnews.setAutoSizeTextTypeUniformWithConfiguration(12, 30, 1, TypedValue.COMPLEX_UNIT_SP);
        label_phasnews.setAutoSizeTextTypeUniformWithConfiguration(12, 30, 1, TypedValue.COMPLEX_UNIT_SP);

        // LISTENERS
        button_back.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
        button_extranews.setOnClickListener(this::gotoGeneralNews);
        button_petnews.setOnClickListener(this::gotoPetNews);
        button_phasnews.setOnClickListener(this::gotoPhasNews);

    }

    public void navigateToInboxFragment(View v) {
        Navigation.findNavController(v).navigate(R.id.action_inboxFragment_to_inboxMessageListFragment);
    }

    /**
     * showExtraNewsPopup method
     */
    private void gotoGeneralNews(View v) {
        messageInboxViewModel.chooseCurrentInbox(MessageCenterViewModel.InboxType.GENERAL);
        navigateToInboxFragment(v);
    }

    /**
     * showPetNewsPopup method
     */
    private void gotoPetNews(View v) {
        messageInboxViewModel.chooseCurrentInbox(MessageCenterViewModel.InboxType.PET);
        navigateToInboxFragment(v);
    }

    /**
     * showPhasNewsPopup method
     */
    public void gotoPhasNews(View v) {
        messageInboxViewModel.chooseCurrentInbox(MessageCenterViewModel.InboxType.PHASMOPHOBIA);
        navigateToInboxFragment(v);
    }



    /**
     * saveStates method
     *
     * TODO
     */
    public void saveStates() {
        if (titleScreenViewModel != null && getContext() != null)
            titleScreenViewModel.saveToFile(getContext());
    }

    /**
     * onPause method
     */
    @Override
    public void onPause() {
        // SAVE PERSISTENT DATA
        saveStates();

        super.onPause();
    }

    /**
     * onResume method
     */
    @Override
    public void onResume() {
        // STOP THREADS

        super.onResume();
    }

    /**
     * onDestroy method
     */
    @Override
    public void onDestroy() {

        super.onDestroy();
    }

}
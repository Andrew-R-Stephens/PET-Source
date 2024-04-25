package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.newsletter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.newsletter.views.NewsletterInboxView;
import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.MainMenuFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared.NewsletterViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.views.PETImageButton;

/**
 * TitleScreenFragment class
 *
 * @author TritiumGamingStudios
 */
public class NewsInboxesFragment extends MainMenuFragment {

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) { // OBTAIN VIEW MODEL REFERENCE

        super.init();

        return inflater.inflate(R.layout.fragment_news_inboxes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        PETImageButton button_back = view.findViewById(R.id.button_left);

        ViewGroup list_inboxes = view.findViewById(R.id.list_inboxes);

        if(newsLetterViewModel != null) {
            for (NewsletterViewModel.InboxType inboxType : NewsletterViewModel.InboxType.values()) {
                try {
                    NewsletterInboxView inboxView =
                            new NewsletterInboxView(requireContext(), null);
                    inboxView.setInboxTitle(inboxType.getName(requireContext()));
                    inboxView.setInboxIcon(inboxType.getIcon(requireContext()));
                    inboxView.initNotify(requireContext(), newsLetterViewModel.getInbox(inboxType));

                    switch (inboxType) {
                        case GENERAL -> inboxView.setOnClickListener(this::gotoGeneralNews);
                        case PET -> inboxView.setOnClickListener(this::gotoPetNews);
                        case PHASMOPHOBIA -> inboxView.setOnClickListener(this::gotoPhasNews);
                    }

                    if (list_inboxes instanceof GridLayout) {
                        inboxView.setLayoutParams(
                                new GridLayout.LayoutParams(
                                    GridLayout.spec(GridLayout.UNDEFINED, 1f),
                                    GridLayout.spec(GridLayout.UNDEFINED, 1f)));
                    }

                    list_inboxes.addView(inboxView);
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }
        }

        button_back.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        super.initAdView(view.findViewById(R.id.adView));
    }

    public void navigateToInboxFragment(@NonNull View v) {
        Navigation.findNavController(v).
                navigate(R.id.action_inboxFragment_to_inboxMessageListFragment);
    }

    /**
     * showExtraNewsPopup method
     */
    private void gotoGeneralNews(@NonNull View v) {
        try {
            newsLetterViewModel.setCurrentInboxType(NewsletterViewModel.InboxType.GENERAL);
            navigateToInboxFragment(v);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * showPetNewsPopup method
     */
    private void gotoPetNews(@NonNull View v) {
        try {
            newsLetterViewModel.setCurrentInboxType(NewsletterViewModel.InboxType.PET);
            navigateToInboxFragment(v);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * showPhasNewsPopup method
     */
    public void gotoPhasNews(@NonNull View v) {
        try {
            newsLetterViewModel.setCurrentInboxType(NewsletterViewModel.InboxType.PHASMOPHOBIA);
            navigateToInboxFragment(v);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initViewModels() {
        super.initViewModels();
        initMainMenuViewModel();
        initNewsletterViewModel();
    }

    @Override
    protected void saveStates() {
        super.saveStates();
    }

    /**
     * onPause method
     */
    @Override
    public void onPause() {
        saveStates();

        super.onPause();
    }

}
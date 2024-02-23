package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.newsletter;

import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.PETFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.MainMenusFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.marketplace.MarketplaceSingleThemeView;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.newsletter.data.NewsletterMessagesData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.newsletter.views.NewsletterInboxView;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.NewsletterViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

/**
 * TitleScreenFragment class
 *
 * @author TritiumGamingStudios
 */
public class NewsletterInboxesFragment extends MainMenusFragment {

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) { // OBTAIN VIEW MODEL REFERENCE

        super.init();

        return inflater.inflate(R.layout.fragment_msginbox, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        AppCompatImageView button_back =
                view.findViewById(R.id.button_prev);

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

        try {
            MobileAds.initialize(requireActivity(), initializationStatus -> {
            });
            AdView mAdView = view.findViewById(R.id.adView);
            if (!titleScreenViewModel.hasAdRequest()) {
                titleScreenViewModel.setAdRequest(new AdRequest.Builder().build());
            }
            mAdView.loadAd(titleScreenViewModel.getAdRequest());
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initViewModels() {
        super.initViewModels();
        initTitleScreenViewModel();
        initNewsletterViewModel();
    }

    public void navigateToInboxFragment(View v) {
        Navigation.findNavController(v).
                navigate(R.id.action_inboxFragment_to_inboxMessageListFragment);
    }

    /**
     * showExtraNewsPopup method
     */
    private void gotoGeneralNews(View v) {
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
    private void gotoPetNews(View v) {
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
    public void gotoPhasNews(View v) {
        try {
            newsLetterViewModel.setCurrentInboxType(NewsletterViewModel.InboxType.PHASMOPHOBIA);
            navigateToInboxFragment(v);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * saveStates method
     * <p>
     * TODO
     */
    public void saveStates() {
        if (globalPreferencesViewModel != null) {
            try {
                globalPreferencesViewModel.saveToFile(requireContext());
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
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

}
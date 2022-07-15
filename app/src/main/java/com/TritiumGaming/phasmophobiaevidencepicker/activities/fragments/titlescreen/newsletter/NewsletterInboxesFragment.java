package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.newsletter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.newsletter.data.NewsletterMessagesData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.GlobalPreferencesViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.NewsletterViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.TitlescreenViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

/**
 * TitleScreenFragment class
 *
 * @author TritiumGamingStudios
 */
public class NewsletterInboxesFragment extends Fragment {

    private GlobalPreferencesViewModel globalPreferencesViewModel = null;

    private TitlescreenViewModel titleScreenViewModel = null;
    private NewsletterViewModel newsletterViewModel = null;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) { // OBTAIN VIEW MODEL REFERENCE
        if (globalPreferencesViewModel == null) {
            globalPreferencesViewModel =
                    new ViewModelProvider(requireActivity()).get(GlobalPreferencesViewModel.class);
        }
        // INITIALIZE VIEW MODEL
        if (getContext() != null) {
            globalPreferencesViewModel.init(getContext());
        }
        if (titleScreenViewModel == null) {
            titleScreenViewModel =
                    new ViewModelProvider(requireActivity()).get(TitlescreenViewModel.class);
        }

        if (newsletterViewModel == null) {
            newsletterViewModel =
                    new ViewModelProvider(requireActivity()).get(NewsletterViewModel.class);
        }

        return inflater.inflate(R.layout.fragment_msginbox, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // INITIALIZE VIEWS
        AppCompatTextView label_title =
                view.findViewById(R.id.textview_title);
        AppCompatTextView label_extranews =
                view.findViewById(R.id.textview_extranews);
        AppCompatTextView label_petnews =
                view.findViewById(R.id.textview_petupdateinbox);
        AppCompatTextView label_phasnews =
                view.findViewById(R.id.textview_phasupdateinbox);
        AppCompatImageView button_back =
                view.findViewById(R.id.button_prev);
        ConstraintLayout button_extranews =
                view.findViewById(R.id.constraintLayout_inboxnews_listener);
        ConstraintLayout button_petnews =
                view.findViewById(R.id.constraintLayout_petnews_listener);
        ConstraintLayout button_phasnews =
                view.findViewById(R.id.constraintLayout_phasnews_listener);
        AppCompatImageView notifyIcon_1 = view.findViewById(R.id.inboxNotify_1);
        AppCompatImageView notifyIcon_2 = view.findViewById(R.id.inboxNotify_2);
        AppCompatImageView notifyIcon_3 = view.findViewById(R.id.inboxNotify_3);

        // LISTENERS
        button_back.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());
        button_extranews.setOnClickListener(this::gotoGeneralNews);
        button_petnews.setOnClickListener(this::gotoPetNews);
        button_phasnews.setOnClickListener(this::gotoPhasNews);

        if(getActivity() != null) {
            getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                    new OnBackPressedCallback(true) {
                        @Override
                        public void handleOnBackPressed() {
                            Navigation.findNavController(view).popBackStack();
                        }
                    });
        }

        label_title.setText(R.string.messagecenter_inboxestitle_label);
        if(getContext() != null) {
            label_extranews.setText(
                    newsletterViewModel.getInboxType(0).getName(view.getContext()));
            label_petnews.setText(
                    newsletterViewModel.getInboxType(1).getName(view.getContext()));
            label_phasnews.setText(
                    newsletterViewModel.getInboxType(2).getName(view.getContext()));
        }


        notifyIcon_1.setAlpha(0f);
        notifyIcon_2.setAlpha(0f);
        notifyIcon_3.setAlpha(0f);
        Animation animation = AnimationUtils.loadAnimation(getContext(), R.anim.notifyblink);

        NewsletterMessagesData inbox1 = newsletterViewModel.getInbox(
                newsletterViewModel.getInboxType(0));
        NewsletterMessagesData inbox2 = newsletterViewModel.getInbox(
                newsletterViewModel.getInboxType(1));
        NewsletterMessagesData inbox3 = newsletterViewModel.getInbox(
                newsletterViewModel.getInboxType(2));

        if(inbox1 != null && inbox1.compareDates()) {
            notifyIcon_1.setAlpha(0.9f);
            notifyIcon_1.startAnimation(animation);
        }
        if(inbox2 != null && inbox2.compareDates()) {
            notifyIcon_2.setAlpha(0.9f);
            notifyIcon_2.startAnimation(animation);
        }
        if(inbox3 != null && inbox3.compareDates()) {
            notifyIcon_3.setAlpha(0.9f);
            notifyIcon_3.startAnimation(animation);
        }

        if (getActivity() != null) {
            MobileAds.initialize(getActivity(), initializationStatus -> {
            });
            AdView mAdView = view.findViewById(R.id.adView);
            if (!titleScreenViewModel.hasAdRequest()) {
                titleScreenViewModel.setAdRequest(new AdRequest.Builder().build());
            }
            mAdView.loadAd(titleScreenViewModel.getAdRequest());
        }
    }

    public void navigateToInboxFragment(View v) {
        Navigation.findNavController(v).
                navigate(R.id.action_inboxFragment_to_inboxMessageListFragment);
    }

    /**
     * showExtraNewsPopup method
     */
    private void gotoGeneralNews(View v) {
        newsletterViewModel.setCurrentInboxType(NewsletterViewModel.InboxType.GENERAL);
        navigateToInboxFragment(v);
    }

    /**
     * showPetNewsPopup method
     */
    private void gotoPetNews(View v) {
        newsletterViewModel.setCurrentInboxType(NewsletterViewModel.InboxType.PET);
        navigateToInboxFragment(v);
    }

    /**
     * showPhasNewsPopup method
     */
    public void gotoPhasNews(View v) {
        newsletterViewModel.setCurrentInboxType(NewsletterViewModel.InboxType.PHASMOPHOBIA);
        navigateToInboxFragment(v);
    }

    /**
     * saveStates method
     * <p>
     * TODO
     */
    public void saveStates() {
        if (globalPreferencesViewModel != null && getContext() != null) {
            globalPreferencesViewModel.saveToFile(getContext());
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
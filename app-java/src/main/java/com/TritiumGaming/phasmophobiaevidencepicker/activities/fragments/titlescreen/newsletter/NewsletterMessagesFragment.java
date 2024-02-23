package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.newsletter;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.PETFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.MainMenusFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.newsletter.data.NewsletterMessagesData;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.newsletter.views.MessagesAdapterView;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.NewsletterViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

/**
 * TitleScreenFragment class
 *
 * @author TritiumGamingStudios
 */
public class NewsletterMessagesFragment extends MainMenusFragment {

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        super.init();

        return inflater.inflate(
                R.layout.fragment_msginbox_listmessages, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // INITIALIZE VIEWS
        AppCompatTextView label_title = view.findViewById(R.id.textview_title);
        AppCompatImageView button_back = view.findViewById(R.id.button_prev);
        RecyclerView recyclerViewMessages = view.findViewById(R.id.recyclerview_messageslist);

        // SCROLL BAR
        recyclerViewMessages.setScrollBarFadeDuration(-1);

        // SET VIEW TEXT
        try {
            label_title.setText(newsLetterViewModel.getCurrentInboxType().getName(requireContext()));
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        NewsletterMessagesData inbox =
                newsLetterViewModel.getInbox(newsLetterViewModel.getCurrentInboxType());
        if(inbox != null) {
            inbox.updateLastReadDate();
            try {
                newsLetterViewModel.saveToFile(requireContext(), inbox.getInboxType());
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }

        // SET CONTENT
        if (newsLetterViewModel != null && newsLetterViewModel.getCurrentInbox() != null) {
            MessagesAdapterView adapter = new MessagesAdapterView(
                    newsLetterViewModel.getCurrentInbox().getMessages(), position -> {
                newsLetterViewModel.setCurrentMessageId(position);
                Navigation.findNavController(view).
                        navigate(R.id.action_inboxMessageListFragment_to_inboxMessageFragment);
            });
            recyclerViewMessages.setAdapter(adapter);
            try {
                recyclerViewMessages.setLayoutManager(new LinearLayoutManager(requireContext()));
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } else {
            if(newsLetterViewModel != null) {
                try {
                    Log.d("InboxMessageListFrag", "Inbox does not exist!" +
                            newsLetterViewModel.getCurrentInboxType().getName(requireContext()));
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            } else {
                Log.d("InboxMessageListFrag",
                        "Either MessageInboxViewModel does not exist " +
                                "or Context does not exist!");
            }
        }

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

        // LISTENERS
        button_back.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

    }


    @Override
    protected void initViewModels() {
        super.initViewModels();
        initTitleScreenViewModel();
        initNewsletterViewModel();
    }

}
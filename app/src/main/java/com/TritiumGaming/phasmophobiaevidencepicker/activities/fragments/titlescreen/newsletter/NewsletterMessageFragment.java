package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.newsletter;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.newsletter.data.NewsletterMessageData;
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
public class NewsletterMessageFragment extends Fragment {

    private TitlescreenViewModel titleScreenViewModel = null;
    private NewsletterViewModel newsletterViewModel = null;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) { // OBTAIN VIEW MODEL REFERENCE

        if (titleScreenViewModel == null) {
            titleScreenViewModel =
                    new ViewModelProvider(requireActivity()).get(TitlescreenViewModel.class);
        }
        if (newsletterViewModel == null) {
            newsletterViewModel =
                    new ViewModelProvider(requireActivity()).get(NewsletterViewModel.class);
        }

        return inflater.inflate(R.layout.fragment_msginbox_message, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // INITIALIZE VIEWS
        AppCompatTextView label_title = view.findViewById(R.id.textview_title);
        AppCompatTextView label_date = view.findViewById(R.id.textView_messageDate);
        AppCompatTextView label_content = view.findViewById(R.id.textView_messageContent);
        AppCompatImageView button_back = view.findViewById(R.id.button_prev);

        // LISTENERS
        button_back.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        if (getActivity() != null) {
            getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                    new OnBackPressedCallback(true) {
                        @Override
                        public void handleOnBackPressed() {
                            Navigation.findNavController(view).popBackStack();
                        }
                    });
        }

        // SET CONTENT
        NewsletterMessageData message = newsletterViewModel.getCurrentMessage();
        if (message != null) {
            label_title.setText(Html.fromHtml(message.getTitle()));
            label_date.setText(Html.fromHtml(message.getDate()));
            label_content.setText(Html.fromHtml(message.getDescription()));
        } else {
            label_title.setText(Html.fromHtml("Data unavailable"));
            label_date.setText(Html.fromHtml("Data unavailable"));
            label_content.setText(Html.fromHtml("Data unavailable"));
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

}
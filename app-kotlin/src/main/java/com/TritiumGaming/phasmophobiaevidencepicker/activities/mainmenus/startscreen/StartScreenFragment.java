package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.compose.ui.platform.ComposeView;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.InvestigationActivity;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.MainMenuActivity;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.MainMenuFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.startscreen.views.StartScreenAnimationView;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.BitmapUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.views.account.AccountIconView;
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.AnimsKt;
import com.TritiumGaming.phasmophobiaevidencepicker.views.composables.PETMenusKt;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;

import java.util.Locale;

/**
 * TitleScreenFragment class
 *
 * @author TritiumGamingStudios
 */
public class StartScreenFragment extends MainMenuFragment {

    @Nullable
    private StartScreenAnimationView animationView;
    private ComposeView button_msgInbox, newsIcon;

    private final BitmapUtils bitmapUtils = new BitmapUtils();

    private boolean canRunMessageCenter = true;

    @Nullable
    private Thread thread_messageCenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        super.init();

        return inflater.inflate(R.layout.fragment_startscreen, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        // INITIALIZE VIEWS
        animationView = view.findViewById(R.id.backgroundanimationView);

        AppCompatTextView label_languageName = view.findViewById(R.id.label_languageName);
        View button_startSolo = view.findViewById(R.id.button_start_solo);
        AppCompatImageView icon_appIcon = view.findViewById(R.id.icon_appicon);
        ComposeView button_info = view.findViewById(R.id.button_info);
        ComposeView button_settings = view.findViewById(R.id.button_settings);
        AppCompatImageView button_review = view.findViewById(R.id.button_review);
        button_msgInbox = view.findViewById(R.id.button_inbox);
        View button_language = view.findViewById(R.id.listener_language);

        // LISTENERS
        button_info.setOnClickListener(this::gotoAppInfoFragment);
        button_language.setOnClickListener(this::gotoLanguagesFragment);
        button_msgInbox.setOnClickListener(this::gotoMessageCenterFragment);
        button_startSolo.setOnClickListener(v -> {
                    try {
                        Intent intent = new Intent(requireActivity(), InvestigationActivity.class);
                        intent.putExtra("lobby", 0);
                        startActivity(intent);
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                }
        );

        newsIcon = new ComposeView(requireContext());
        AnimsKt.setNewsAlertComposable(newsIcon, false);

        PETMenusKt.setIconDropdownMenu(
                button_info,
                new AccountIconView(requireContext()),
                /*newsIcon,*/
                R.navigation.titlescreen_navgraph,
                new Object[]{
                        R.drawable.ic_person
                },
                new Integer[]{
                        R.id.marketplaceFragment
                }
        );
        PETMenusKt.setIconDropdownMenu(
                button_settings,
                R.drawable.icon_ts_gear,
                R.navigation.titlescreen_navgraph,
                new Object[]{
                        R.drawable.icon_ts_gear,
                        R.drawable.icon_ts_globe
                },
                new Integer[]{
                        R.id.appSettingsFragment,
                        R.id.appLanguageFragment
                }
        );

        if(button_msgInbox != null) {
            AnimsKt.setNewsAlertComposable(button_msgInbox, false);
        }

        setBackgroundLogo(icon_appIcon);
        setLanguageName(label_languageName);

        super.initAdView(view.findViewById(R.id.adView));

        try {
            if (!((MainMenuActivity) requireActivity()).checkForAppUpdates()) {
                initReviewRequest(button_review);
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        try {
            doReviewRequest();
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void backPressedHandler() {
        closePopup();
    }

    @Override
    protected void initViewModels() {
        super.initViewModels();
        initMainMenuViewModel();
        initNewsletterViewModel();
    }

    public void loadMessageCenter() {
        if(newsLetterViewModel != null) {
            try {
                newsLetterViewModel.registerInboxes(requireContext());
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    private void setBackgroundLogo(@NonNull AppCompatImageView icon_appIcon) {
        bitmapUtils.clearResources();
        bitmapUtils.setResource(R.drawable.app_icon_sm);

        try {
            icon_appIcon.setImageBitmap(bitmapUtils.compileBitmaps(requireContext()));
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void setLanguageName(@NonNull AppCompatTextView label_languageName) {
        String chosenLanguage = Locale.getDefault().getDisplayLanguage();
        if(!chosenLanguage.isEmpty()) {
            chosenLanguage = chosenLanguage.substring(0, 1).toUpperCase() +
                            chosenLanguage.substring(1);
        }
        label_languageName.setText(chosenLanguage);
    }

    private void loadBannerAd(@NonNull View view) {
        try {
            MobileAds.initialize(requireActivity(), initializationStatus -> {
            });
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        AdView mAdView = view.findViewById(R.id.adView);

        if (mainMenuViewModel != null) {
            if (!mainMenuViewModel.hasAdRequest()) {
                mainMenuViewModel.setAdRequest(new AdRequest.Builder().build());
            }
            mAdView.loadAd(mainMenuViewModel.getAdRequest());
        }
    }

    private void gotoMessageCenterFragment(@NonNull View v) {
        Navigation.findNavController(v).navigate(R.id.action_titleScreenFragment_to_inboxFragment);
    }

    private void gotoMarketplaceFragment(@NonNull View v) {
        Navigation.findNavController(v).navigate(R.id.action_titleScreenFragment_to_marketplaceFragment);
    }

    /**
     * gotoAppInfoFragment method
     */
    private void gotoAppInfoFragment(@NonNull View v) {
        Navigation.findNavController(v).navigate(R.id.action_titleScreenFragment_to_appInfoFragment);
    }


    /**
     * gotoAppSettingsFragment method
     */
    private void gotoAppSettingsFragment(@NonNull View v) {
        Navigation.findNavController(v).navigate(R.id.action_titleScreenFragment_to_appSettingsFragment);
    }

    /**
     * gotoLanguagesFragment method
     */
    public void gotoLanguagesFragment(@NonNull View v) {
        Navigation.findNavController(v).navigate(R.id.action_titleScreenFragment_to_appLanguageFragment);
    }

    public void initReviewRequest(@NonNull AppCompatImageView button_review) {
        // REQUEST REVIEW LISTENER
        if (globalPreferencesViewModel != null &&
                globalPreferencesViewModel.getReviewRequestData().getTimesOpened() > 2) {
            button_review.setOnClickListener(v -> {
                try {
                    showReviewPopup(requireView());
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            });
        } else {
            button_review.setEnabled(false);
            button_review.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * doReviewRequest method
     */
    public void doReviewRequest() throws IntentSender.SendIntentException {

        if (globalPreferencesViewModel != null &&
                globalPreferencesViewModel.getReviewRequestData().canRequestReview()) {

            Log.d("Review", "Review Request Accepted");
            new Thread(() -> {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                globalPreferencesViewModel.getReviewRequestData().setWasRequested(true);
                try {
                    requireActivity().runOnUiThread(() -> {
                        try {
                            showReviewPopup(requireView());
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        }
                    });
                    globalPreferencesViewModel.saveToFile(requireContext());
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
            }).start();

        } else {
            Log.d("Review", "Review Request Denied");
        }

    }

    /**
     * showReviewPopup method
     */
    public void showReviewPopup(@NonNull View parentView) {

        //DESTROY PREVIOUS POPUP
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
        //INFLATE LAYOUT
        LayoutInflater inflater = (LayoutInflater) requireView().getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View customView = inflater.inflate(R.layout.popup_requestreview, null);

        // INITIALIZE POPUPWINDOW
        popupWindow = new PopupWindow(
                customView,
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        popupWindow.setOutsideTouchable(false);

        AppCompatButton acceptButton = customView.findViewById(R.id.label_accept);
        AppCompatButton declineButton = customView.findViewById(R.id.label_decline);

        // LISTENERS
        acceptButton.setOnClickListener(v -> {
            popupWindow.dismiss();
            popupWindow = null;

            startReviewLauncher();

        });

        declineButton.setOnClickListener(v -> {
            Bundle params = new Bundle();
            params.putString("event_type", "user_action");
            params.putString("event_details", "request_rejected");
            analytics.logEvent("event_review_manager", params);

            popupWindow.dismiss();
            popupWindow = null;
        });

        // FINALIZE
        popupWindow.setAnimationStyle(androidx.navigation.ui.R.anim.nav_default_enter_anim);

        boolean success = parentView.post(() -> {
            //Log.d("Review", "Is displaying");
            if(popupWindow != null) {
                popupWindow.showAtLocation(customView, Gravity.CENTER_VERTICAL, 0, 0);
            }

        });

        Log.d("ReviewRequest", (success ? "SUCCESSFUL" : "UNSUCCESSFUL"));

        Bundle params = new Bundle();
        params.putString("event_type", "review_requested");
        params.putString("event_details",
                "request_" + (success ? "successful" : "unsuccessful"));
        analytics.logEvent("event_review_manager", params);

    }

    private void startReviewLauncher() {
        //THIS IS FOR THE IN-APP REVIEW
        try {
            ReviewManager manager =
                    ReviewManagerFactory.create(requireContext());

            Task<ReviewInfo> request = manager.requestReviewFlow();
            request.addOnCompleteListener(requestTask -> {
                if (requestTask.isSuccessful()) {
                    Log.e("ReviewManager", "Task Successful");
                    // We can get the ReviewInfo object
                    ReviewInfo reviewInfo = requestTask.getResult();

                    try {
                        Task<Void> flow = manager.launchReviewFlow(requireActivity(), reviewInfo);
                        flow.addOnCompleteListener(flowTask -> {
                            // DO NOTHING
                        });
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(getResources().
                            getString(R.string.review_storelink_website)));
                    intent.setPackage("com.android.vending");

                    try {
                        requireContext().startActivity(intent);
                        Log.e("ReviewManager",
                                "SUCCEEDED intent navigation to the App Store.");
                    } catch (IllegalStateException |ActivityNotFoundException e) {
                        Log.e("ReviewManager",
                                "FAILED intent navigation to the App Store.");
                        e.printStackTrace();

                        Bundle params = new Bundle();
                        params.putString("event_type", "review_navigation");
                        params.putString("event_details", "navigation_failed");
                        analytics.logEvent("event_review_manager", params);
                    }
                } else {
                    Log.e("ReviewManager", "Task Failed");
                    if (requestTask.getException() != null) {
                        (requestTask.getException()).printStackTrace();
                    }
                }
            });
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void doMessageCenterNotification() {
        Log.d("MessageCenter", "Starting animation");

        try {
            if(button_msgInbox != null) {
                //inboxNotify.setAlpha(1f);
                //inboxNotify.startAnimation(animation);
                AnimsKt.setNewsAlertComposable(button_msgInbox, true);
                AnimsKt.setNewsAlertComposable(newsIcon, true);
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void startLoadMessageCenterThread() {

        thread_messageCenter = new Thread(() -> {

            Log.d("MessageCenter", "Attempting to load inboxes...");

            final int MAX_CYCLES = 3;
            int currentCycle = 0;

            while (canRunMessageCenter &&
                    (!newsLetterViewModel.isUpToDate() && (currentCycle < MAX_CYCLES))) {

                Log.d("MessageCenter", "Attempting to load inboxes...");

                loadMessageCenter();

                if (!newsLetterViewModel.isUpToDate()) {
                    Log.d("MessageCenter",
                            "[Attempt " + currentCycle + "/" + MAX_CYCLES + "] " +
                                    "Inboxes are not up to date yet! Retrying...\n");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                currentCycle++;
            }

            Log.d("MessageCenter", "Inboxes " +
                    (newsLetterViewModel.isUpToDate() ?
                            "are now up to date." :
                            "could not be updated in time.") +
                    " Loading completed.");

            try {
                if(!newsLetterViewModel.init(requireContext())) {
                    Log.e("MessageCenter", "Initialization failed.");
                }
                newsLetterViewModel.compareAllInboxDates();
                if (newsLetterViewModel.requiresNotify()) {
                    requireActivity().runOnUiThread(this::doMessageCenterNotification);
                }
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }

        });

        thread_messageCenter.start();

    }

    private void startInitMessageCenterThread() {

        if (checkInternetConnection()) {
            startLoadMessageCenterThread();
        }
        else {
            Log.d("MessageCenter", "Could not connect to the internet.");
            newsLetterViewModel.compareAllInboxDates();
            if (newsLetterViewModel.requiresNotify()) {
                doMessageCenterNotification();
            }
        }
    }

    private void stopLoadMessageCenterThread() {
        if (thread_messageCenter != null) {
            thread_messageCenter.interrupt();
            thread_messageCenter = null;
        }
    }

    /**
     * onPause method
     */
    @Override
    public void onPause() {

        // SAVE PERSISTENT DATA
        saveGlobalPreferencesViewModel();

        // STOP THREADS
        animationView.stopAnimThreads();
        animationView.stopAnimInitThreads();
        animationView.canAnimateBackground(false);

        stopLoadMessageCenterThread();
        if(newsLetterViewModel.isUpToDate()) {
            canRunMessageCenter = false;
            Log.d("MessageCenter", "IS up to date");
        } else {
            Log.d("MessageCenter", "IS NOT up to date");
        }

        // RECYCLE ANIMATION VIEW
        if (animationView != null) {
            animationView.recycleBitmaps();
        }
        super.onPause();
    }

    /**
     * onResume method
     */
    @Override
    public void onResume() {

        // START THREADS
        animationView.startAnimInitThreads(mainMenuViewModel, bitmapUtils);
        animationView.startAnimThreads();

        startInitMessageCenterThread();

        super.onResume();
    }

    /**
     * onDestroy method
     */
    @Override
    public void onDestroy() {

        // DESTROY AD-REQUEST
        if (mainMenuViewModel != null) {
            mainMenuViewModel.setAdRequest(null);
        }
        super.onDestroy();
    }

    /**
     * onLowMemory method
     */
    @Override
    public void onLowMemory() {

        // RECYCLE ANIMATION VIEW BITMAPS
        if (animationView != null) {
            animationView.recycleBitmaps();
        }
        super.onLowMemory();
    }

}
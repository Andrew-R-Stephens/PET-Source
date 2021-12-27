package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.titlescreen;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.InvestigationActivity;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.titlescreen.views.TitlescreenAnimationView;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.BitmapUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.NetworkUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.RSSParserUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.GlobalPreferencesViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.NewsletterViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.TitlescreenViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.play.core.review.ReviewInfo;
import com.google.android.play.core.review.ReviewManager;
import com.google.android.play.core.review.ReviewManagerFactory;
import com.google.android.play.core.tasks.Task;
import com.google.firebase.analytics.FirebaseAnalytics;

import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.util.Locale;

/**
 * TitleScreenFragment class
 *
 * @author TritiumGamingStudios
 */
public class TitlescreenFragment extends Fragment {

    private FirebaseAnalytics mFirebaseAnalytics;

    private GlobalPreferencesViewModel globalPreferencesViewModel = null;

    private TitlescreenViewModel titleScreenViewModel = null;
    private NewsletterViewModel newsLetterViewModel = null;

    private final BitmapUtils bitmapUtils = new BitmapUtils();

    private PopupWindow popup = null;

    private TitlescreenAnimationView animationView = null;

    private boolean canRunAnim = true, canRunMessageCenter = true;

    private Thread animInitThread = null, animTickThread = null, animDrawThread = null,
            initReadyThread = null, messageCenterThread = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        initFirebase();

        // OBTAIN VIEW MODEL REFERENCE
        if (globalPreferencesViewModel == null) {
            globalPreferencesViewModel = new ViewModelProvider(requireActivity()).
                    get(GlobalPreferencesViewModel.class);
        }
        // INITIALIZE VIEW MODEL
        if (getContext() != null) {
            globalPreferencesViewModel.init(getContext());
        }

        if (titleScreenViewModel == null) {
            titleScreenViewModel = new ViewModelProvider(requireActivity()).
                    get(TitlescreenViewModel.class);
        }

        if (newsLetterViewModel == null) {
            newsLetterViewModel = new ViewModelProvider(requireActivity()).
                    get(NewsletterViewModel.class);
        }

        return inflater.inflate(R.layout.fragment_titlescreen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // INITIALIZE VIEWS
        animationView = view.findViewById(R.id.titlescreen_backgroundanimation);
        AppCompatTextView label_languageName = view.findViewById(R.id.label_languageName);
        AppCompatTextView button_startSolo = view.findViewById(R.id.button_start_solo);
        AppCompatTextView button_startMult = view.findViewById(R.id.button_start_mult);
        AppCompatImageView icon_appIcon = view.findViewById(R.id.icon_appicon);
        ImageButton button_info = view.findViewById(R.id.button_info);
        ImageButton button_settings = view.findViewById(R.id.button_settings);
        ImageButton button_review = view.findViewById(R.id.button_review);
        ImageButton button_msgInbox = view.findViewById(R.id.button_inbox);
        View button_language = view.findViewById(R.id.listener_language);

        // SET APP-ICON
        bitmapUtils.clearResources();
        bitmapUtils.setResource(R.drawable.app_icon_sm);
        if (getContext() != null) {
            icon_appIcon.setImageBitmap(bitmapUtils.compileBitmaps(getContext()));
        }

        // LANGUAGE
        String appendedLanguage = Locale.getDefault().getDisplayLanguage();
        if (globalPreferencesViewModel.getLanguageName().equalsIgnoreCase("fr")) {
            appendedLanguage += " (" + getString(R.string.titlescreen_beta_label) + ")";
        }
        label_languageName.setText(appendedLanguage);

        // LISTENERS
        button_info.setOnClickListener(this::gotoAppInfoFragment);
        button_settings.setOnClickListener(this::gotoAppSettingsFragment);
        button_language.setOnClickListener(this::gotoLanguagesFragment);
        button_msgInbox.setOnClickListener(this::gotoMessageCenterFragment);
        button_startSolo.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), InvestigationActivity.class);
                intent.putExtra("lobby", 0);
                startActivity(intent);
            }
        );

        button_startMult.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), InvestigationActivity.class);
                intent.putExtra("lobby", 1);
                startActivity(intent);
            }
        );


        //TODO Create a button hyperlink to app review page

        if (getActivity() != null) {
            MobileAds.initialize(getActivity(), initializationStatus -> {
            });
            AdView mAdView = view.findViewById(R.id.adView);
            if (!titleScreenViewModel.hasAdRequest()) {
                titleScreenViewModel.setAdRequest(new AdRequest.Builder().build());
            }
            mAdView.loadAd(titleScreenViewModel.getAdRequest());
        }

        // REQUEST REVIEW LISTENER
        if (globalPreferencesViewModel != null &&
                globalPreferencesViewModel.getReviewRequestData().getTimesOpened() > 2) {
            button_review.setOnClickListener(v -> showReviewPopup(getView()));
        } else {
            button_review.setEnabled(false);
            button_review.setVisibility(View.INVISIBLE);
        }
        // REQUEST REVIEW PROMPT
        requestReview();

    }

    private void initFirebase() {
        if(getActivity() != null){
            mFirebaseAnalytics = FirebaseAnalytics.getInstance(getActivity());
        }
    }

    public void registerMessageInboxes() {

        try {
            boolean isUpToDate = true;

            if (newsLetterViewModel != null) {

                if (getContext() != null) {
                    new RSSParserUtils(XmlPullParserFactory.newInstance(),
                            getContext().getResources().
                                    getString(R.string.preference_phasmophobia_changelog_link),
                            NewsletterViewModel.InboxType.PHASMOPHOBIA, newsLetterViewModel);
                } else {
                    isUpToDate = false;
                }

                if (getContext() != null) {
                    new RSSParserUtils(XmlPullParserFactory.newInstance(),
                            getContext().getResources().
                                    getString(R.string.preference_general_news_link),
                            NewsletterViewModel.InboxType.GENERAL, newsLetterViewModel);
                } else {
                    isUpToDate = false;
                }

                if (getContext() != null) {
                    new RSSParserUtils(XmlPullParserFactory.newInstance(),
                            getContext().getResources().
                                    getString(R.string.preference_pet_changelog_link),
                            NewsletterViewModel.InboxType.PET, newsLetterViewModel);
                } else {
                    isUpToDate = false;
                }

                newsLetterViewModel.setIsUpToDate(isUpToDate);
            }

        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

    }

    /**
     * doReviewRequest method
     */
    public void requestReview() {

        if (globalPreferencesViewModel != null &&
                globalPreferencesViewModel.getReviewRequestData().canRequestReview()) {

            Log.d("Review", "Review Request Accepted");
            Thread tempThread = new Thread(() -> {
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (getActivity() != null && getContext() != null) {
                    globalPreferencesViewModel.getReviewRequestData().setWasRequested(true);
                    getActivity().runOnUiThread(() -> showReviewPopup(getView()));
                    if (getContext() != null) {
                        globalPreferencesViewModel.saveToFile(getContext());
                    }
                }
            });
            tempThread.start();

        } else {
            Log.d("Review", "Review Request Denied");
        }

    }

    private void gotoMessageCenterFragment(View v) {
        Navigation.findNavController(v).
                navigate(R.id.action_titleScreenFragment_to_inboxFragment);
    }

    /**
     * gotoAppInfoFragment method
     */
    private void gotoAppInfoFragment(View v) {
        Navigation.findNavController(v).
                navigate(R.id.action_titleScreenFragment_to_appInfoFragment);
    }


    /**
     * gotoAppSettingsFragment method
     */
    private void gotoAppSettingsFragment(View v) {

        Navigation.findNavController(v).
                navigate(R.id.action_titleScreenFragment_to_appSettingsFragment);
    }

    /**
     * gotoLanguagesFragment method
     */
    public void gotoLanguagesFragment(View v) {
        Navigation.findNavController(v).
                navigate(R.id.action_titleScreenFragment_to_appLanguageFragment);
    }

    /**
     * showReviewPopup method
     */
    public void showReviewPopup(View parentView) {

        //DESTROY PREVIOUS POPUP
        if (popup != null) {
            popup.dismiss();
        }
        //INFLATE LAYOUT
        LayoutInflater inflater = (LayoutInflater) requireView().getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View customView = inflater.inflate(R.layout.popup_requestreview, null);

        // INITIALIZE POPUPWINDOW
        popup = new PopupWindow(
                customView,
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        popup.setOutsideTouchable(false);

        AppCompatTextView acceptButton = customView.findViewById(R.id.label_accept);
        AppCompatTextView declineButton = customView.findViewById(R.id.label_decline);

        // LISTENERS
        acceptButton.setOnClickListener(v -> {
            popup.dismiss();
            popup = null;

            //THIS IS FOR THE IN-APP REVIEW
            if (getContext() != null) {
                ReviewManager manager =
                        ReviewManagerFactory.create(getContext());
                Task<ReviewInfo> request = manager.requestReviewFlow();
                request.addOnCompleteListener(requestTask -> {
                    if (requestTask.isSuccessful()) {
                        Log.e("ReviewManager", "Task Successful");
                        // We can get the ReviewInfo object
                        ReviewInfo reviewInfo = requestTask.getResult();
                        if (getActivity() != null) {
                            Task<Void> flow = manager.launchReviewFlow(getActivity(), reviewInfo);
                            flow.addOnCompleteListener(flowTask -> {
                                // The flow has finished. The API does not indicate whether the user
                                // reviewed or not, or even whether the review dialog was shown.
                                // Thus, no
                                // matter the result, we continue our app flow.
                            });
                        }
                    } else {
                        //Log.e("ReviewManager", "Task Failed");
                        if (requestTask.getException() != null) {
                            (requestTask.getException()).printStackTrace();
                        }

                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(getResources().
                                getString(R.string.review_storelink)));
                        intent.setPackage("com.android.vending");
                        try {
                            startActivity(intent);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });

        declineButton.setOnClickListener(v -> {
            popup.dismiss();
            popup = null;
        });

        // FINALIZE
        popup.setAnimationStyle(R.anim.nav_default_enter_anim);

        boolean success = parentView.post(() -> {

            //Log.d("Review", "Is displaying");
            popup.showAtLocation(customView, Gravity.CENTER_VERTICAL, 0, 0);

        });

        Log.d("Review", (success ? "SUCCESSFUL" : "UNSUCCESSFUL"));

    }

    private void startLoadMessageCenterThread() {

        messageCenterThread = new Thread(() -> {

            Log.d("MessageCenter", "Attempting to load inboxes...");

            final int MAX_CYCLES = 3;
            int currentCycle = 0;

            while (canRunMessageCenter &&
                    (!newsLetterViewModel.isUpToDate()) || (currentCycle < MAX_CYCLES)) {

                registerMessageInboxes();

                if (!newsLetterViewModel.isUpToDate()) {

                    Log.d("MessageCenter", "Load failed!");

                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                currentCycle++;
            }
            Log.d("MessageCenter", "Load completed.");

        });

        messageCenterThread.start();

    }

    private void startInitMessageCenterThread() {

        if (NetworkUtils.isNetworkAvailable(getContext(),
                globalPreferencesViewModel.getNetworkPreference())) {
            startLoadMessageCenterThread();
        }
        else {
            Log.d("MessageCenter", "Could not obtain external data");
        }
    }

    private void stopLoadMessageCenterThread() {

        if (messageCenterThread != null) {

            messageCenterThread.interrupt();
            messageCenterThread = null;

        }

    }

    /**
     * startAnimInitThreads method
     */
    private void startAnimInitThreads() {

        if (animInitThread == null) {

            animInitThread = new Thread() {

                public void run() {
                    animationView.init(titleScreenViewModel, bitmapUtils);
                    animationView.buildImages();
                    animationView.buildData();
                }

            };
            animInitThread.start();

        }
        if (initReadyThread == null) {

            initReadyThread = new Thread(() -> {

                while (!canRunAnim) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                startAnimThreads();

            });

            initReadyThread.start();

        }
    }

    /**
     * startAnimTickThread method
     */
    private void startAnimTickThread() {
        if (animTickThread == null) {
            canRunAnim = true;
            animTickThread = new Thread() {
                public void run() {
                    while (canRunAnim) {
                        try {
                            animationView.tick();

                            long now = System.nanoTime();
                            long updateTime = System.nanoTime() - now;
                            double TARGET_FPS = 30;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R &&
                                    (getContext() != null && getContext().getDisplay() != null)) {
                                TARGET_FPS = getContext().getDisplay().getRefreshRate();
                                if (TARGET_FPS > 60) {
                                    TARGET_FPS = 60;
                                }
                            }
                            //TARGET_FPS = 200;
                            long OPTIMAL_TIME = (long) (1000000000 / TARGET_FPS);
                            long wait = (long) ((OPTIMAL_TIME - updateTime) / 1000000.0);

                            if (wait < 0) {
                                wait = 1;
                            }

                            Thread.sleep(wait);

                        } catch (InterruptedException e) {
                            //Log.e("TitleScreenFragment", "InterruptedException error handled.");
                        }
                    }
                }
            };
            animTickThread.start();
        }
    }

    /**
     * startAnimDrawThread method
     */
    private void startAnimDrawThread() {
        if (animDrawThread == null) {
            animDrawThread = new Thread() {
                public void run() {
                    while (canRunAnim) {
                        try {
                            if (getActivity() != null) {
                                getActivity().runOnUiThread(() -> animationView.invalidate());
                            }
                            long now = System.nanoTime();
                            long updateTime = System.nanoTime() - now;
                            double TARGET_FPS = 30;
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R &&
                                    (getContext() != null && getContext().getDisplay() != null)) {
                                TARGET_FPS = getContext().getDisplay().getRefreshRate();
                                if (TARGET_FPS > 60) {
                                    TARGET_FPS = 60;
                                }
                            }
                            long OPTIMAL_TIME = (long) (1000000000 / TARGET_FPS);
                            long wait = (long) ((OPTIMAL_TIME - updateTime) / 1000000.0);

                            if (wait < 0) {
                                wait = 1;
                            }

                            Thread.sleep(wait);

                        } catch (InterruptedException e) {
                            //Log.e("TitleScreenFragment", "InterruptedException error handled.");
                        }
                    }
                }
            };
            animDrawThread.start();
        }
    }

    /**
     * stopAnimInitThreads method
     */
    public void stopAnimInitThreads() {
        if (animInitThread != null) {
            animInitThread.interrupt();
            animInitThread = null;
        }
        if (initReadyThread != null) {
            initReadyThread.interrupt();
            initReadyThread = null;
        }
    }

    /**
     * stopAnimTickThread method
     */
    public void stopAnimTickThread() {
        if (animTickThread != null) {
            animTickThread.interrupt();
            animTickThread = null;
        }
    }

    /**
     * stopAnimDrawThread method
     */
    public void stopAnimDrawThread() {
        if (animDrawThread != null) {
            animDrawThread.interrupt();
            animDrawThread = null;
        }
    }

    /**
     * startAnimThreads method
     */
    public void startAnimThreads() {
        startAnimTickThread();
        startAnimDrawThread();
    }

    /**
     * stopAnimThreads method
     */

    public void stopAnimThreads() {
        stopAnimDrawThread();
        stopAnimTickThread();
    }

    /**
     * saveStates method
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

        // DESTROY POPUP
        if (popup != null) {
            popup.dismiss();
            popup = null;
        }

        // SAVE PERSISTENT DATA
        saveStates();

        // STOP THREADS
        stopAnimThreads();
        stopAnimInitThreads();
        canRunAnim = false;
        stopLoadMessageCenterThread();
        canRunMessageCenter = false;

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
        startAnimInitThreads();
        startAnimThreads();
        startInitMessageCenterThread();

        super.onResume();
    }

    /**
     * onDestroy method
     */
    @Override
    public void onDestroy() {

        // DESTROY AD-REQUEST
        if (titleScreenViewModel != null) {
            titleScreenViewModel.setAdRequest(null);
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
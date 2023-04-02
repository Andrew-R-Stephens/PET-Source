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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
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

import java.util.Locale;

/**
 * TitleScreenFragment class
 *
 * @author TritiumGamingStudios
 */
public class TitlescreenFragment extends Fragment {

    private FirebaseAnalytics analytics;

    private GlobalPreferencesViewModel globalPreferencesViewModel = null;
    private TitlescreenViewModel titleScreenViewModel = null;
    private NewsletterViewModel newsLetterViewModel = null;

    private final BitmapUtils bitmapUtils = new BitmapUtils();

    private PopupWindow popup = null;
    private TitlescreenAnimationView animationView = null;
    private View inboxNotify;

    private boolean canRunAnim = true, canRunMessageCenter = true;

    private Thread thread_initAnima = null, thread_tickAnim = null, thread_renderAnim = null,
            thread_initReady = null, thread_messageCenter = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        initFirebase();
        initViewModels();

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
        AppCompatImageButton button_info = view.findViewById(R.id.button_info);
        AppCompatImageButton button_settings = view.findViewById(R.id.button_settings);
        AppCompatImageButton button_review = view.findViewById(R.id.button_review);
        AppCompatImageButton button_msgInbox = view.findViewById(R.id.button_inbox);
        View button_language = view.findViewById(R.id.listener_language);
        inboxNotify = view.findViewById(R.id.img_inboxalert);

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

        /*button_startMult.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), InvestigationActivity.class);
                intent.putExtra("lobby", 1);
                startActivity(intent);
            }
        );*/

        if(inboxNotify != null) {
            inboxNotify.setAlpha(0f);
        }

        setBackgroundLogo(icon_appIcon);
        setLanguageName(label_languageName);

        renderAd(view);

        initReviewRequest(button_review);

        doIntroductionRequest();
        doReviewRequest();

    }

    private void initFirebase() {
        if(getContext() != null){
            analytics = FirebaseAnalytics.getInstance(getContext());
        }
    }

    private void initViewModels() {
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
    }

    public void loadMessageCenter() {
        if(newsLetterViewModel != null && getContext() != null) {
            newsLetterViewModel.registerInboxes(getContext());
        }
    }

    private void setBackgroundLogo(AppCompatImageView icon_appIcon) {
        Context context = getContext();
        if (context != null) {
            bitmapUtils.clearResources();
            bitmapUtils.setResource(R.drawable.app_icon_sm);
            icon_appIcon.setImageBitmap(bitmapUtils.compileBitmaps(context));
        }
    }

    private void setLanguageName(AppCompatTextView label_languageName) {
        String chosenLanguage = Locale.getDefault().getDisplayLanguage();
        label_languageName.setText(chosenLanguage);
    }

    private void renderAd(View view) {
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

    public void initReviewRequest(AppCompatImageButton button_review) {
        // REQUEST REVIEW LISTENER
        if (globalPreferencesViewModel != null &&
                globalPreferencesViewModel.getReviewRequestData().getTimesOpened() > 2) {
            button_review.setOnClickListener(v -> showReviewPopup(getView()));
        } else {
            button_review.setEnabled(false);
            button_review.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * doReviewRequest method
     */
    public void doReviewRequest() {

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

    public void doIntroductionRequest() {
        // REQUEST REVIEW LISTENER
        if (globalPreferencesViewModel != null &&
                globalPreferencesViewModel.canShowIntroduction()) {

            Thread tempThread = new Thread(() -> {
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (getActivity() != null && getContext() != null) {
                    getActivity().runOnUiThread(() -> showIntroductionPopup(getView()));
                }
            });
            tempThread.start();
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
                                // DO NOTHING
                            });

                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setData(Uri.parse(getResources().
                                    getString(R.string.review_storelink_website)));
                            intent.setPackage("com.android.vending");

                            try {
                                Log.e("ReviewManager",
                                        "SUCCEEDED intent navigation to the App Store.");
                                getContext().startActivity(intent);
                            } catch (ActivityNotFoundException e) {
                                Log.e("ReviewManager",
                                        "FAILED intent navigation to the App Store.");
                                e.printStackTrace();

                                Bundle params = new Bundle();
                                params.putString("event_type", "review_navigation");
                                params.putString("event_details", "navigation_failed");
                                analytics.logEvent("event_review_manager", params);
                            }
                        }
                    } else {
                        Log.e("ReviewManager", "Task Failed");
                        if (requestTask.getException() != null) {
                            (requestTask.getException()).printStackTrace();
                        }
                    }
                });
            }
        });

        declineButton.setOnClickListener(v -> {
            Bundle params = new Bundle();
            params.putString("event_type", "user_action");
            params.putString("event_details", "request_rejected");
            analytics.logEvent("event_review_manager", params);

            popup.dismiss();
            popup = null;
        });

        // FINALIZE
        popup.setAnimationStyle(R.anim.nav_default_enter_anim);

        boolean success = parentView.post(() -> {
            //Log.d("Review", "Is displaying");
            if(popup != null) {
                popup.showAtLocation(customView, Gravity.CENTER_VERTICAL, 0, 0);
            }

        });

        Log.d("ReviewRequest", (success ? "SUCCESSFUL" : "UNSUCCESSFUL"));

        Bundle params = new Bundle();
        params.putString("event_type", "review_requested");
        params.putString("event_details",
                "request_" + (success ? "successful" : "unsuccessful"));
        analytics.logEvent("event_review_manager", params);

    }

    /**
     * showReviewPopup method
     */
    public void showIntroductionPopup(View parentView) {

        //DESTROY PREVIOUS POPUP
        if (popup != null) {
            popup.dismiss();
        }
        //INFLATE LAYOUT
        LayoutInflater inflater = (LayoutInflater) requireView().getContext().
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("InflateParams")
        View customView = inflater.inflate(R.layout.fragment_titlescreen_intro, null);

        // INITIALIZE POPUPWINDOW
        popup = new PopupWindow(
                customView,
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT
        );
        popup.setOutsideTouchable(false);

        AppCompatImageButton closeButton = customView.findViewById(R.id.popup_close_button);
        closeButton.setOnClickListener(view -> {
            globalPreferencesViewModel.setCanShowIntroduction(false);
            globalPreferencesViewModel.saveToFile(getContext());
            popup.dismiss();
            popup = null;
        });

        // FINALIZE
        popup.setAnimationStyle(R.anim.nav_default_enter_anim);

        boolean success = parentView.post(() -> {
            //Log.d("Review", "Is displaying");
            if(popup != null) {
                popup.showAtLocation(customView, Gravity.CENTER_VERTICAL, 0, 0);
            }
        });

        Log.d("ReviewRequest", (success ? "SUCCESSFUL" : "UNSUCCESSFUL"));

        Bundle params = new Bundle();
        params.putString("event_type", "started");
        params.putString("event_details",
                "request_" + (success ? "successful" : "unsuccessful"));
        analytics.logEvent("event_introduction_start", params);

    }

    private void doMessageCenterNotification() {
        Log.d("MessageCenter", "Starting animation");
        Context context = getContext();
        if(context != null) {
            Animation animation = AnimationUtils.loadAnimation(context,
                    R.anim.notifyblink);
            if(inboxNotify != null) {
                inboxNotify.setAlpha(1f);
                inboxNotify.startAnimation(animation);
            }
        }
    }

    private boolean checkInternetConnection() {
        return (NetworkUtils.isNetworkAvailable(getContext(),
                globalPreferencesViewModel.getNetworkPreference()));
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
                                    "Inboxes are not up to date yet! Retrying...");
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

            // INITIALIZE VIEW MODEL
            if(getContext() != null) {
                if(!newsLetterViewModel.init(getContext())) {
                    Log.e("MessageCenter",
                            "Initialization failed.");
                    Bundle params = new Bundle();
                    params.putString("error_type", "failed_initialization");
                    params.putString("error_details", "null_context");
                    analytics.logEvent("error_message_center", params);
                }
                newsLetterViewModel.compareAllInboxDates();
                if (newsLetterViewModel.requiresNotify()) {
                    if (getActivity() != null) {
                        getActivity().runOnUiThread(this::doMessageCenterNotification);
                    }
                }
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

    /**
     * startAnimThreads method
     */
    public void startAnimThreads() {
        startAnimTickThread();
        startAnimDrawThread();
    }

    /**
     * startAnimInitThreads method
     */
    private void startAnimInitThreads() {

        if (thread_initAnima == null) {
            thread_initAnima = new Thread() {

                public void run() {
                    animationView.init(titleScreenViewModel, bitmapUtils);
                    animationView.buildImages();
                    animationView.buildData();
                }

            };
            thread_initAnima.start();
        }

        if (thread_initReady == null) {
            thread_initReady = new Thread(() -> {

                while (!canRunAnim) {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                startAnimThreads();

            });

            thread_initReady.start();
        }
    }

    /**
     * startAnimTickThread method
     */
    private void startAnimTickThread() {
        if (thread_tickAnim == null) {
            canRunAnim = true;
            thread_tickAnim = new Thread() {
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
            thread_tickAnim.start();
        }
    }

    /**
     * startAnimDrawThread method
     */
    private void startAnimDrawThread() {
        if (thread_renderAnim == null) {
            thread_renderAnim = new Thread() {
                public void run() {
                while (canRunAnim) {
                    try {
                        if (getActivity() != null) {
                            getActivity().runOnUiThread(() -> animationView.invalidate());
                        }
                        long now = System.nanoTime();
                        long updateTime = System.nanoTime() - now;
                        double TARGET_FPS = 24;
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
            thread_renderAnim.start();
        }
    }

    private void stopLoadMessageCenterThread() {
        if (thread_messageCenter != null) {
            thread_messageCenter.interrupt();
            thread_messageCenter = null;
        }
    }

    /**
     * stopAnimInitThreads method
     */
    public void stopAnimInitThreads() {
        if (thread_initAnima != null) {
            thread_initAnima.interrupt();
            thread_initAnima = null;
        }
        if (thread_initReady != null) {
            thread_initReady.interrupt();
            thread_initReady = null;
        }
    }

    /**
     * stopAnimTickThread method
     */
    public void stopAnimTickThread() {
        if (thread_tickAnim != null) {
            thread_tickAnim.interrupt();
            thread_tickAnim = null;
        }
    }

    /**
     * stopAnimDrawThread method
     */
    public void stopAnimDrawThread() {
        if (thread_renderAnim != null) {
            thread_renderAnim.interrupt();
            thread_renderAnim = null;
        }
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
        if(newsLetterViewModel.isUpToDate()) {
            canRunMessageCenter = false;
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
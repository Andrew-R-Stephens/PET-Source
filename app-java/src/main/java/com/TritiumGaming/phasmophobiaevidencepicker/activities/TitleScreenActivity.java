package com.TritiumGaming.phasmophobiaevidencepicker.activities;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.GoogleMobileAdsConsentManager;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MapMenuViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.NewsletterViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.ObjectivesViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.OnboardingViewModel;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * TestActivity class
 *
 * @author TritiumGamingStudios
 */
public class TitleScreenActivity extends PETActivity {

    protected OnboardingViewModel onboardingViewModel;
    protected NewsletterViewModel newsLetterViewModel;

    private GoogleMobileAdsConsentManager googleMobileAdsConsentManager;
    private final AtomicBoolean isMobileAdsInitializeCalled = new AtomicBoolean(false);

    protected AppUpdateManager appUpdateManager;
    protected int updateType = AppUpdateType.IMMEDIATE;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_titlescreen);
        Log.d("Titlescreen", "OnCreate");

        //requestOnboardingActivity();

        requestAdsConsentInformation();
    }

    protected ViewModelProvider.AndroidViewModelFactory initViewModels() {

        ViewModelProvider.AndroidViewModelFactory factory = super.initViewModels();

        super.initViewModels();

        initOnboardingViewModel(factory);
        initNewsletterViewModel(factory);

        return factory;
    }

    private void initOnboardingViewModel(ViewModelProvider.AndroidViewModelFactory factory) {
        onboardingViewModel = factory.create(
                OnboardingViewModel.class);
        onboardingViewModel = new ViewModelProvider(this).get(
                OnboardingViewModel.class);
    }

    private void initNewsletterViewModel(ViewModelProvider.AndroidViewModelFactory factory) {
        newsLetterViewModel = factory.create(
                NewsletterViewModel.class);
        newsLetterViewModel = new ViewModelProvider(this).get(
                NewsletterViewModel.class);
        //newsLetterViewModel.init(this);
    }

    public boolean checkForAppUpdates() {
        appUpdateManager = AppUpdateManagerFactory.create(getApplicationContext());

        AtomicBoolean hasUpdate = new AtomicBoolean(false);

        appUpdateManager.getAppUpdateInfo().addOnSuccessListener(info -> {
            boolean isUpdateAvailable = info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE;
            boolean isUpdateAllowed = updateType == AppUpdateType.IMMEDIATE;
            if (isUpdateAvailable && isUpdateAllowed) {
                try {
                    appUpdateManager.startUpdateFlowForResult(info, updateType, TitleScreenActivity.this, 123);
                    hasUpdate.set(true);
                } catch (IntentSender.SendIntentException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return hasUpdate.get();
    }

    public void requestOnboardingActivity() {
        if (onboardingViewModel.getCanShowIntroduction()) {
            Log.d("Onboarding", "Starting Activity");
            startActivity(new Intent(this, OnboardingActivity.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    @Nullable @org.jetbrains.annotations.Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 123) {
            if(resultCode != RESULT_OK) {
                Log.e("UpdateResult", "Update Failed!");
            } else {
                Log.e("UpdateResult", "Update Succeeded!");
            }
        }
    }

    public void initPreferences() {
        super.initPreferences();

        globalPreferencesViewModel.incrementAppOpenCount(getApplicationContext());

        //set language
        if (setLanguage(globalPreferencesViewModel.getLanguage(getApplicationContext()))) {
            recreate();
        }
    }

    private void requestAdsConsentInformation() {

        // Set your test devices. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ABCDEF012345"))
        // to get test ads on this device."
        MobileAds.setRequestConfiguration(
                new RequestConfiguration.Builder().setTestDeviceIds(List.of("9E93747E0D90133B5298FD010482BD8F"))
                        .build());

        googleMobileAdsConsentManager = new GoogleMobileAdsConsentManager(this);

        googleMobileAdsConsentManager.gatherConsent(
                consentError -> {
                    if (consentError != null) {
                        // Consent not obtained in current session.
                        Log.w(
                                "AdConsentManager",
                                String.format(
                                        "%s: %s",
                                        consentError.getErrorCode(),
                                        consentError.getMessage()));
                    }

                    if (googleMobileAdsConsentManager.canRequestAds()) {
                        initializeMobileAdsSdk();
                    }

                    if (googleMobileAdsConsentManager.isPrivacyOptionsRequired()) {
                        // Regenerate the options menu to include a privacy setting.
                        invalidateOptionsMenu();
                    }
                }
        );

        // This sample attempts to load ads using consent obtained in the previous session.
        if (googleMobileAdsConsentManager.canRequestAds()) {
            initializeMobileAdsSdk();
        }
    }

    private void initializeMobileAdsSdk() {
        if (isMobileAdsInitializeCalled.getAndSet(true)) {
            return;
        }

        // Initialize the Google Mobile Ads SDK.
        MobileAds.initialize(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(appUpdateManager != null && updateType == AppUpdateType.IMMEDIATE) {
            appUpdateManager.getAppUpdateInfo().addOnSuccessListener(info -> {
                if (info.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    try {
                        appUpdateManager.startUpdateFlowForResult(info, updateType, TitleScreenActivity.this, 123);
                    } catch (IntentSender.SendIntentException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }


}
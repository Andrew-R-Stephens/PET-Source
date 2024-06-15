package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.onboarding.OnboardingActivity;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.pet.PETActivity;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared.NewsletterViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared.OnboardingViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.utils.GoogleMobileAdsConsentManager;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * TestActivity class
 *
 * @author TritiumGamingStudios
 */
public class MainMenuActivity extends PETActivity {

    protected OnboardingViewModel onboardingViewModel;
    protected NewsletterViewModel newsLetterViewModel;

    private GoogleMobileAdsConsentManager googleMobileAdsConsentManager;
    private final AtomicBoolean isMobileAdsInitializeCalled = new AtomicBoolean(false);

    protected AppUpdateManager appUpdateManager;
    protected int updateType = AppUpdateType.IMMEDIATE;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mainmenu);
        Log.d("MainMenuActivity", "OnCreate");

        //requestOnboardingActivity();

        //showConsentForm();
        //requestAdsConsentInformation(); Moved to PETActivity
        createConsentInformation();
    }

    @NonNull
    protected ViewModelProvider.AndroidViewModelFactory initViewModels() {

        ViewModelProvider.AndroidViewModelFactory factory = super.initViewModels();

        initOnboardingViewModel(factory);
        initNewsletterViewModel(factory);

        return factory;
    }

    private void initOnboardingViewModel(@NonNull ViewModelProvider.AndroidViewModelFactory factory) {
        onboardingViewModel = factory.create(
                OnboardingViewModel.class);
        onboardingViewModel = new ViewModelProvider(this).get(
                OnboardingViewModel.class);
    }

    private void initNewsletterViewModel(@NonNull ViewModelProvider.AndroidViewModelFactory factory) {
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
                    appUpdateManager.startUpdateFlowForResult(info, updateType, MainMenuActivity.this, 123);
                    hasUpdate.set(true);
                } catch (IntentSender.SendIntentException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        return hasUpdate.get();
    }

    private void completePendingAppUpdates() {
        if(appUpdateManager != null && updateType == AppUpdateType.IMMEDIATE) {
            appUpdateManager.getAppUpdateInfo().addOnSuccessListener(info -> {
                if (info.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS) {
                    try {
                        appUpdateManager.startUpdateFlowForResult(info, updateType, MainMenuActivity.this, 123);
                    } catch (IntentSender.SendIntentException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        }
    }

    public void requestOnboardingActivity() {
        if (onboardingViewModel != null && onboardingViewModel.getShowIntroduction()) {
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

    @Override
    protected void onResume() {
        super.onResume();

        completePendingAppUpdates();
    }

}
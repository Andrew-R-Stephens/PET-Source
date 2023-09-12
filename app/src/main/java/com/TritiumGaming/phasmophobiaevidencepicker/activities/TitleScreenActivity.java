package com.TritiumGaming.phasmophobiaevidencepicker.activities;

import android.os.Bundle;
import android.util.Log;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.GoogleMobileAdsConsentManager;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * TestActivity class
 *
 * @author TritiumGamingStudios
 */
public class TitleScreenActivity extends PETActivity {

    /*
    FirebaseAnalytics analytics;

    private GlobalPreferencesViewModel globalPreferencesViewModel;

    private TitlescreenViewModel titleScreenViewModel;
    private NewsletterViewModel newsLetterViewModel;
    */

    private GoogleMobileAdsConsentManager googleMobileAdsConsentManager;
    private final AtomicBoolean isMobileAdsInitializeCalled = new AtomicBoolean(false);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*

        initViewModels();
        initPrefs();
        */

        setContentView(R.layout.activity_titlescreen);

        requestAdsConsentInformation();
    }

    /*
    private void initViewModels() {
        analytics = FirebaseAnalytics.getInstance(this);

        ViewModelProvider.AndroidViewModelFactory factory =
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication());

        globalPreferencesViewModel = factory.create(GlobalPreferencesViewModel.class);
        globalPreferencesViewModel.init(TitleScreenActivity.this);

        titleScreenViewModel = factory.create(TitlescreenViewModel.class);

        newsLetterViewModel = factory.create(NewsletterViewModel.class);
    }
    */

    public void initPrefs() {
        super.initPrefs();

        globalPreferencesViewModel.incrementAppOpenCount(getApplicationContext());

        //set language
        if (setLanguage(globalPreferencesViewModel.getLanguage(getApplicationContext()))) {
            recreate();
        }
    }

    /*
    public int getFontStyle(int fontType) {
        switch (fontType) {
            case 1: {
                return R.style.Android;
            }
            case 2: {
                return R.style.Journal;
            }
            case 3: {
                return R.style.Brick;
            }
            case 4: {
                return R.style.Clean;
            }
            default: {
                return R.style.Fonts_Base;
            }
        }
    }

    public int getColorSpace(int colorSpace) {
        switch (colorSpace) {
            case 1: {
                return R.style.Monochromacy;
            }
            case 2: {
                return R.style.Deuteranomaly;
            }
            case 3: {
                return R.style.Protanomaly;
            }
            case 4: {
                return R.style.Tritanomaly;
            }
            case 5: {
                return R.style.Funhouse;
            }
            default: {
                return R.style.Colorblind_Base;
            }
        }
    }

    public void changeTheme(int colorSpace, int fontType) {

        int styleId = getFontStyle(fontType);
        getTheme().applyStyle(styleId, true);

        int colorSpaceId = getColorSpace(colorSpace);
        setTheme(colorSpaceId);

    }
    */

    /*
    public boolean setLanguage(String language) {
        boolean isChanged = false;

        Locale defaultLocale = Locale.getDefault();
        Locale locale = new Locale(language);
        if (!(defaultLocale.getLanguage().equalsIgnoreCase(locale.getLanguage()))) {
            isChanged = true;
        }

        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        super.setLanguage(language);

        return isChanged;
    }
    */


    private void requestAdsConsentInformation() {

        // Set your test devices. Check your logcat output for the hashed device ID to
        // get test ads on a physical device. e.g.
        // "Use RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("ABCDEF012345"))
        // to get test ads on this device."
        MobileAds.setRequestConfiguration(
                new RequestConfiguration.Builder().setTestDeviceIds(Arrays.asList("9E93747E0D90133B5298FD010482BD8F"))
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


}
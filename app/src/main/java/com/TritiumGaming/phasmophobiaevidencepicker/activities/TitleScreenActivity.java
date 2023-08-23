package com.TritiumGaming.phasmophobiaevidencepicker.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.GoogleMobileAdsConsentManager;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.GlobalPreferencesViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.NewsletterViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.TitlescreenViewModel;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.RequestConfiguration;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Arrays;
import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * TestActivity class
 *
 * @author TritiumGamingStudios
 */
public class TitleScreenActivity extends AppCompatActivity {

    FirebaseAnalytics analytics;

    private GlobalPreferencesViewModel globalPreferencesViewModel;

    private TitlescreenViewModel titleScreenViewModel;
    private NewsletterViewModel newsLetterViewModel;

    GoogleMobileAdsConsentManager googleMobileAdsConsentManager;

    // Use an atomic boolean to initialize the Google Mobile Ads SDK and load ads once.
    private final AtomicBoolean isMobileAdsInitializeCalled = new AtomicBoolean(false);

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViewModels();
        initPrefs();

        setContentView(R.layout.activity_titlescreen);

        requestAdsConsentInformation();
    }

    private void initViewModels() {
        analytics = FirebaseAnalytics.getInstance(this);

        ViewModelProvider.AndroidViewModelFactory factory =
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication());

        globalPreferencesViewModel = factory.create(GlobalPreferencesViewModel.class);
        globalPreferencesViewModel.init(TitleScreenActivity.this);

        titleScreenViewModel = factory.create(TitlescreenViewModel.class);

        newsLetterViewModel = factory.create(NewsletterViewModel.class);
    }

    /**
     * Immediately initialize Activity with Preferences
     */
    public void initPrefs() {
        globalPreferencesViewModel.incrementAppOpenCount(getApplicationContext());

        //set language
        if (setLanguage(globalPreferencesViewModel.getLanguage(getApplicationContext()))) {
            recreate();
        }

        //set isAlwaysOn
        if(globalPreferencesViewModel.getIsAlwaysOn()) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        //set colorSpace
        changeTheme(globalPreferencesViewModel.getColorSpace(), globalPreferencesViewModel.getFontType());
    }

    /**
     * changeTheme
     * <p>
     * Sets the Skin Theme based on User Preferences.
     *
     * @param colorSpace to be set
     */
    public void changeTheme(int colorSpace, int fontType) {

        switch (fontType) {
            case 0: {
                getTheme().applyStyle(R.style.Fonts_Base, true);
                break;
            }
            case 1: {
                getTheme().applyStyle(R.style.Android, true);
                break;
            }
            case 2: {
                getTheme().applyStyle(R.style.Journal, true);
                break;
            }
            case 3: {
                getTheme().applyStyle(R.style.Brick, true);
                break;
            }
            case 4: {
                getTheme().applyStyle(R.style.Clean, true);
                break;
            }
        }

        switch (colorSpace) {
            case 0: {
                setTheme(R.style.Colorblind_Base);
                break;
            }
            case 1: {
                setTheme(R.style.Monochromacy);
                break;
            }
            case 2: {
                setTheme(R.style.Deuteranomaly);
                break;
            }
            case 3: {
                setTheme(R.style.Protanomaly);
                break;
            }
            case 4: {
                setTheme(R.style.Tritanomaly);
                break;
            }
            case 5: {
                setTheme(R.style.Funhouse);
                break;
            }
        }
    }

    /**
     * setLanguage
     * <p>
     * Sets the Language of the application based on User Preferences.
     *
     * @param language
     */
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

        return isChanged;
    }

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
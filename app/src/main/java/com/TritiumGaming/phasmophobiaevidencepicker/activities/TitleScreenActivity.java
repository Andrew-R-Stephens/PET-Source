package com.TritiumGaming.phasmophobiaevidencepicker.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.GlobalPreferencesViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.NewsletterViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.TitlescreenViewModel;
import com.google.android.gms.ads.MobileAds;
import com.google.android.ump.ConsentDebugSettings;
import com.google.android.ump.ConsentInformation;
import com.google.android.ump.ConsentRequestParameters;
import com.google.android.ump.UserMessagingPlatform;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * MainActivity class
 *
 * @author TritiumGamingStudios
 */
public class TitleScreenActivity extends AppCompatActivity {

    FirebaseAnalytics analytics;

    private GlobalPreferencesViewModel globalPreferencesViewModel;

    private TitlescreenViewModel titleScreenViewModel;
    private NewsletterViewModel newsLetterViewModel;

    private ConsentInformation consentInformation;
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
                getTheme().applyStyle(R.style.Simple, true);
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
        // Set tag for under age of consent. false means users are not under age
        // of consent.
        ConsentDebugSettings debugSettings = new ConsentDebugSettings.Builder(this)
                .setDebugGeography(ConsentDebugSettings.DebugGeography.DEBUG_GEOGRAPHY_EEA)
                .addTestDeviceHashedId("TEST-DEVICE-HASHED-ID")
                .build();

        ConsentRequestParameters params = new ConsentRequestParameters
                .Builder()
                .setConsentDebugSettings(debugSettings)
                .build();


        /*
        ConsentRequestParameters params = new ConsentRequestParameters
                .Builder()
                .setTagForUnderAgeOfConsent(false)
                .build();
        */

        consentInformation = UserMessagingPlatform.getConsentInformation(this);
        consentInformation.requestConsentInfoUpdate(
                this,
                params,
                () -> {
                    // Load and show the consent form.
                    UserMessagingPlatform.loadAndShowConsentFormIfRequired(
                            this,
                            loadAndShowError -> {
                                if (loadAndShowError != null) {
                                    // Consent gathering failed.
                                    Log.w("ConsentFormRequest", String.format("%s: %s",
                                            loadAndShowError.getErrorCode(),
                                            loadAndShowError.getMessage()));
                                }

                                // Consent has been gathered.
                                if (consentInformation.canRequestAds()) {
                                    initializeMobileAdsSdk();
                                }
                            }
                    );
                },
                requestConsentError -> {
                    // Consent gathering failed.
                    Log.w("ConsentInformation", String.format("%s: %s",
                            requestConsentError.getErrorCode(),
                            requestConsentError.getMessage()));
                });

        // Check if you can initialize the Google Mobile Ads SDK in parallel
        // while checking for new consent information. Consent obtained in
        // the previous session can be used to request ads.
        if (consentInformation.canRequestAds()) {
            initializeMobileAdsSdk();
        }
    }

    private void initializeMobileAdsSdk() {
        if (isMobileAdsInitializeCalled.getAndSet(true)) {
            return;
        }

        // Initialize the Google Mobile Ads SDK.
        MobileAds.initialize(this);

        // TODO: Request an ad.
        // InterstitialAd.load(...);

    }


}
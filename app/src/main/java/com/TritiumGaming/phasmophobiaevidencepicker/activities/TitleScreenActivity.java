package com.TritiumGaming.phasmophobiaevidencepicker.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.GlobalPreferencesViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.NewsletterViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.TitlescreenViewModel;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Locale;

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

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViewModels();
        initPrefs();

        setContentView(R.layout.activity_titlescreen);
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

        fontType = 1;

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

        switch (fontType) {
            case 0: {
                getTheme().applyStyle(R.style.Fonts_Base, false);
                break;
            }
            case 1: {
                getTheme().applyStyle(R.style.Simple, false);
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



}
package com.TritiumGaming.phasmophobiaevidencepicker.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.GlobalPreferencesViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.NewsletterViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.TitlescreenViewModel;

import java.util.Locale;

/**
 * MainActivity class
 *
 * @author TritiumGamingStudios
 */
public class TitleScreenActivity extends AppCompatActivity {

    GlobalPreferencesViewModel globalPreferencesViewModel;

    TitlescreenViewModel titleScreenViewModel;
    NewsletterViewModel newsLetterViewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Create View Models
        ViewModelProvider.AndroidViewModelFactory factory =
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication());
        globalPreferencesViewModel = factory.create(GlobalPreferencesViewModel.class);
        globalPreferencesViewModel.init(TitleScreenActivity.this);
        titleScreenViewModel = factory.create(TitlescreenViewModel.class);
        newsLetterViewModel = factory.create(NewsletterViewModel.class);

        // Immediately initialize Activity with Preferences
        init();

        //Set the Parent View, Late call
        setContentView(R.layout.activity_titlescreen);

    }

    /**
     * Immediately initialize Activity with Preferences
     */
    public void init() {
        globalPreferencesViewModel.incrementAppOpenCount(getApplicationContext());

        //set language
        if (setLanguage(globalPreferencesViewModel.getLanguage(getApplicationContext())))
            recreate();

        //set isAlwaysOn
        if (getSharedPreferences(getString(R.string.preferences_globalFile_name),
                Context.MODE_PRIVATE).getBoolean(
                getString(R.string.preference_isAlwaysOn), false))
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        //set colorSpace
        int colorSpace = getSharedPreferences(getString(R.string.preferences_globalFile_name),
                Context.MODE_PRIVATE).getInt(
                getString(R.string.preference_colorSpace),
                globalPreferencesViewModel.getColorSpace());

        changeTheme(colorSpace);
    }

    /**
     * changeTheme
     * <p>
     * Sets the Skin Theme based on User Preferences.
     *
     * @param colorSpace to be set
     */
    public void changeTheme(int colorSpace) {
        switch (colorSpace) {
            case 0: {
                setTheme(R.style.Theme_PhasmophobiaEvidenceTool_Normal);
                break;
            }
            case 1: {
                setTheme(R.style.Theme_PhasmophobiaEvidenceTool_Colorblind_Monochromacy);
                break;
            }
            case 2: {
                setTheme(R.style.Theme_PhasmophobiaEvidenceTool_Colorblind_Deuteranomaly);
                break;
            }
            case 3: {
                setTheme(R.style.Theme_PhasmophobiaEvidenceTool_Colorblind_Protanomaly);
                break;
            }
            case 4: {
                setTheme(R.style.Theme_PhasmophobiaEvidenceTool_Colorblind_Tritanomaly);
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
        if (!(defaultLocale.getLanguage().equalsIgnoreCase(locale.getLanguage())))
            isChanged = true;

        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        return isChanged;
    }

}
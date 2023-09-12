package com.TritiumGaming.phasmophobiaevidencepicker.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.AppThemeData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.GlobalPreferencesViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.PermissionsViewModel;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Locale;

/**
 * InvestigationActivity class
 *
 * @author TritiumGamingStudios
 */
public abstract class PETActivity extends AppCompatActivity {

    protected FirebaseAnalytics analytics;

    protected GlobalPreferencesViewModel globalPreferencesViewModel;
    protected PermissionsViewModel permissionsViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        initFirebase();
        initViewModels();
        initPrefs();

    }

    protected void initFirebase() {
        analytics = FirebaseAnalytics.getInstance(this);
    }

    protected ViewModelProvider.AndroidViewModelFactory initViewModels() {
        ViewModelProvider.AndroidViewModelFactory factory =
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication());

        globalPreferencesViewModel = factory.create(GlobalPreferencesViewModel.class);
        globalPreferencesViewModel.init(PETActivity.this);

        permissionsViewModel = factory.create(
                PermissionsViewModel.class);
        permissionsViewModel = new ViewModelProvider(this).get(
                PermissionsViewModel.class);

        return factory;
    };

    protected void initPrefs() {

        //set colorSpace
        changeTheme(globalPreferencesViewModel.getColorSpace(), globalPreferencesViewModel.getFontType());

        if (globalPreferencesViewModel.getIsAlwaysOn()) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

    }

    public int getFontStyle(int fontType) {
        return AppThemeData.getFontResource(fontType);
    }

    public int getColorSpace(int colorSpace) {
        return AppThemeData.getColorSpace(colorSpace);
    }

    /**
     * changeTheme
     * <p>
     * Sets the Skin Theme based on User Preferences.
     *
     * @param colorSpace to be set
     */
    public void changeTheme(int colorSpace, int fontType) {

        if(fontType != -1) {
            int styleId = getFontStyle(fontType);
            getTheme().applyStyle(styleId, true);
        }

        if(colorSpace != -1) {
            int colorSpaceId = getColorSpace(colorSpace);
            setTheme(colorSpaceId);
        }
    }


    /**
     * setLanguage method
     *
     * @param language The desired new language
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

    /**
     * getAppLanguage method
     *
     * @return the abbreviation of the chosen language that's saved to file
     */
    public String getAppLanguage() {
        return globalPreferencesViewModel.getLanguageName();
    }



}

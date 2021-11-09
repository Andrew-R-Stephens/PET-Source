package com.TritiumGaming.phasmophobiaevidencepicker.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MessageCenterViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.TitlescreenViewModel;

import java.util.Locale;

/**
 * MainActivity class
 *
 * @author TritiumGamingStudios
 */
public class TitleScreenActivity extends AppCompatActivity {

    TitlescreenViewModel titleScreenViewModel;
    MessageCenterViewModel messageCenterViewModel;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Create View Models
        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication());
        titleScreenViewModel = factory.create(TitlescreenViewModel.class);
        messageCenterViewModel = factory.create(MessageCenterViewModel.class);

        titleScreenViewModel.init(TitleScreenActivity.this);

        //Set Immediately Important Review Tracking Data
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preferences_globalFile_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getResources().getString(R.string.reviewtracking_appTimesOpened), titleScreenViewModel.getReviewRequestData().getTimesOpened()+1);
        editor.apply();

        init();

        //Set the Parent View
        setContentView(R.layout.activity_titlescreen);

    }

    /**
     * Sets Immediately Important Preferences Data
     *
     */
    public void init() {
        //set language
        if(setLanguage(getLanguage()))
            recreate();
        //set isAlwaysOn
        if(getSharedPreferences(getString(R.string.preferences_globalFile_name), Context.MODE_PRIVATE).getBoolean(getString(R.string.preference_isAlwaysOn), false))
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //set colorSpace
        int colorSpace = getSharedPreferences(getString(R.string.preferences_globalFile_name), Context.MODE_PRIVATE).getInt(getString(R.string.preference_colorSpace), titleScreenViewModel.getColorSpace());
        changeTheme(colorSpace);
    }

    /**
     * changeTheme
     *
     * Sets the Skin Theme based on User Preferences.
     *
     * @param colorSpace to be set
     */
    public void changeTheme(int colorSpace) {
        switch(colorSpace){
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
     *
     * Sets the Language of the application based on User Preferences.
     *
     * @param language
     */
    public boolean setLanguage(String language){
        boolean isChanged = false;
        Locale defaultLocale = Locale.getDefault();
        Locale locale = new Locale(language);
        if(!(defaultLocale.getLanguage().equalsIgnoreCase(locale.getLanguage())))
            isChanged = true;
        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
        return isChanged;
    }

    /**
     * getLanguage
     *
     * Returns the current applications language.
     * Defaults to return 'en' if there is no previously saved preference.
     *
     * @return The language specified in the Preferences data, or otherwise English
     */
    public String getLanguage(){
        String lang = getSharedPreferences(getString(R.string.preferences_globalFile_name), Context.MODE_PRIVATE).getString("chosenLanguage", "en");
        Log.d("Current Chosen Language", lang);
        return lang;
    }

}
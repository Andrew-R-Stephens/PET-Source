package com.TritiumGaming.phasmophobiaevidencepicker.activities.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.RSSParser;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MessageCenterViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.TitleScreenViewModel;

import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.util.Locale;

/**
 * MainActivity class
 *
 * @author TritiumGamingStudios
 */
public class TitleScreenActivity extends AppCompatActivity {

    TitleScreenViewModel titleScreenViewModel;
    MessageCenterViewModel messageCenterViewModel;
    Thread messageCenterThread = null;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Create View Models
        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication());
        titleScreenViewModel = factory.create(TitleScreenViewModel.class);
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
        setLanguage(getLanguage());
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
    public void setLanguage(String language){
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
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
        return getSharedPreferences(getString(R.string.preferences_globalFile_name), Context.MODE_PRIVATE).getString("chosenLanguage", "en");
    }

}
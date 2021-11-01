package com.TritiumGaming.phasmophobiaevidencepicker.activities.activity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.data.InvestigationData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.TitleScreenViewModel;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * MainActivity class
 *
 * @author TritiumGamingStudios
 */
public class TitleScreenActivity extends AppCompatActivity {

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Create View Models
        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication());
        TitleScreenViewModel titleScreenViewModel = factory.create(TitleScreenViewModel.class);
        titleScreenViewModel.init(TitleScreenActivity.this);

        //Set Immediately Important Review Tracking Data
        SharedPreferences sharedPref = getSharedPreferences(getString(R.string.preferences_globalFile_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(getResources().getString(R.string.reviewtracking_appTimesOpened), titleScreenViewModel.getReviewRequestData().getTimesOpened()+1);
        editor.apply();

        //Set Immediately Important Preferences Data
            //change language
        setLanguage(getLanguage());
            //change preferences
                //isAlwaysOn
        if(getSharedPreferences(getString(R.string.preferences_globalFile_name), Context.MODE_PRIVATE).getBoolean(getString(R.string.preference_isAlwaysOn), false))
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                //colorSpace
        int colorSpace = getSharedPreferences(getString(R.string.preferences_globalFile_name), Context.MODE_PRIVATE).getInt(getString(R.string.preference_colorSpace), titleScreenViewModel.getColorSpace());
        changeTheme(colorSpace);

        //Set the Parent View
        setContentView(R.layout.activity_titlescreen);

        /*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            loadWebSources();
        }
        */
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

    /**
     * Creates the GhostEvidencePair.json file.
     * Initializes the file with content. Used to keep the data up-to-date.
     */
    /*
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void loadWebSources() {
        Log.d("WEB", "Loading Web Sources");
        new Thread(() -> {
            ArrayList<String> lines = new ArrayList<String>();
            try {
                // Create a URL for the desired page
                URL url = new URL(getResources().getString(R.string.preference_ghost_evidence));
                //First open the connection
                HttpURLConnection conn=(HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(10000); // times out in 10 seconds

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String str = in.readLine();
                try {
                    int version = Integer.parseInt(str);
                    if (version > titleScreenViewModel.getLatestDatabaseVersion()) {
                        Log.d("TitleScreenActivity", "loadWebSources: PET-Data requires update from " + titleScreenViewModel.getLatestDatabaseVersion() + " -> " + version);
                        while ((str = in.readLine()) != null) {
                            lines.add(str);
                        }
                        in.close();
                        conn.disconnect();

                        File file = new File(getFilesDir(), getResources().getString(R.string.localFile_ghostEvidencePair_name));
                        file.delete();
                        file.createNewFile();

                        BufferedWriter writer = null;
                        writer = new BufferedWriter(new FileWriter(file, true));
                        for (int i = 0; i < lines.size(); i++) {
                            writer.append(lines.get(i) + "\n");
                        }
                        writer.close();

                        titleScreenViewModel.setLatestDatabaseVersion(version);
                        Log.d("TitleScreenActivity", "loadWebSources: PET-Data should be updated to version " + version);
                        //titleScreenViewModel.saveToFile(getApplicationContext());

                    } else {
                        in.close();
                        conn.disconnect();
                        Log.d("TitleScreenActivity", "loadWebSources: PET-Data already up to date.");
                    }
                } catch (NumberFormatException nfe) {
                    Log.e("TitleScreenActivity", "loadWebSources: NumberFormatException on first line");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    */

}
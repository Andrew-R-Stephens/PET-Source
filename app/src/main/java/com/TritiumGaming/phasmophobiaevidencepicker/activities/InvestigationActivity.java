package com.TritiumGaming.phasmophobiaevidencepicker.activities;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.ObjectivesViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.PermissionsViewModel;

import java.util.Locale;

/**
 * InvestigationActivity class
 *
 * @author TritiumGamingStudios
 */
public class InvestigationActivity extends AppCompatActivity {

    private PermissionsViewModel permissionsViewModel;
    private EvidenceViewModel evidence;
    private ObjectivesViewModel objectives;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setLanguage(getAppLanguage());

        int colorSpace = getSharedPreferences(getString(R.string.preferences_globalFile_name),
                Context.MODE_PRIVATE).
                getInt(getString(R.string.preference_colorSpace), 0);
        changeTheme(colorSpace);

        int intentFragment = getIntent().getExtras().getInt("lobby");
        switch (intentFragment) {
            case 0: {
                setContentView(R.layout.activity_investigation_solo);
                break;
            }
            case 1: {
                setContentView(R.layout.activity_investigation_mult);
                break;
            }
        }

        ViewModelProvider.AndroidViewModelFactory factory =
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication());

        permissionsViewModel = factory.create(PermissionsViewModel.class);
        //if(permissionsViewModel == null)
        permissionsViewModel = new ViewModelProvider(this).get(PermissionsViewModel.class);

        evidence = factory.create(EvidenceViewModel.class);
        evidence.init(getApplicationContext());

        objectives = factory.create(ObjectivesViewModel.class);

        if (getSharedPreferences(getString(R.string.preferences_globalFile_name),
                Context.MODE_PRIVATE).
                getBoolean(getString(R.string.preference_isAlwaysOn), false))
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    }

    /**
     * setLanguage method
     *
     * @param lang The desired new language
     */
    public void setLanguage(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    /**
     * getAppLanguage method
     *
     * @return the abbreviation of the chosen language that's saved to file
     */
    public String getAppLanguage() {
        return getSharedPreferences(getString(R.string.preferences_globalFile_name),
                Context.MODE_PRIVATE).
                getString("chosenLanguage", "en");
    }

    /**
     * getHuntWarningAllowed method
     *
     * @return the decided ability to emanate audio that has been saved to file
     */
    /*
    public boolean getHuntWarningAllowed() {
        return getSharedPreferences(getString(R.string.preferences_globalFile_name),
                Context.MODE_PRIVATE).
                getBoolean(getString(R.string.preference_isHuntAudioWarningAllowed), false);
    }
    */

    /**
     * getHuntWarningFlashTimeout method
     *
     * @return the decided ability to flash the HuntWarning indicator that has been saved to file
     */
    /*
    public int getHuntWarningFlashTimeout() {
        return getSharedPreferences(getString(R.string.preferences_globalFile_name),
                Context.MODE_PRIVATE).
                getInt(getString(R.string.preference_huntWarningFlashTimeout), -1);
    }
    */

    /**
     * changeTheme method
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
     * Resets ObjectiveViewModel and EvidenceViewModel data upon Activity exit
     */
    @Override
    public void onBackPressed() {

        objectives.reset();
        evidence.reset();

        super.onBackPressed();
    }

}

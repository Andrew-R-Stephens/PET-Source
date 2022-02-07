package com.TritiumGaming.phasmophobiaevidencepicker.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.GlobalPreferencesViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MapMenuViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.ObjectivesViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.PermissionsViewModel;

import java.util.Locale;

/**
 * InvestigationActivity class
 *
 * @author TritiumGamingStudios
 */
public class InvestigationActivity extends AppCompatActivity {

    private GlobalPreferencesViewModel globalPreferencesViewModel;
    private PermissionsViewModel permissionsViewModel;
    private EvidenceViewModel evidenceViewModel;
    private ObjectivesViewModel objectivesViewModel;
    private MapMenuViewModel mapMenuViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        initViewModels();
        initPrefs();

        determineInvestigationFragment();

    }

    private void initViewModels() {
        ViewModelProvider.AndroidViewModelFactory factory =
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication());

        globalPreferencesViewModel = factory.create(GlobalPreferencesViewModel.class);
        globalPreferencesViewModel.init(InvestigationActivity.this);

        permissionsViewModel = factory.create(
                PermissionsViewModel.class);
        permissionsViewModel = new ViewModelProvider(this).get(
                PermissionsViewModel.class);

        evidenceViewModel = factory.create(
                EvidenceViewModel.class);
        evidenceViewModel.init(getApplicationContext());

        objectivesViewModel = factory.create(
                ObjectivesViewModel.class);

        mapMenuViewModel = factory.create(
                MapMenuViewModel.class);
    }

    private void initPrefs() {

        setLanguage(getAppLanguage());

        changeTheme();

        if (globalPreferencesViewModel.getIsAlwaysOn()) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

    }

    private void determineInvestigationFragment() {
        int intentFragment = 0;
        if(getIntent() != null && getIntent().getExtras()!= null) {
            intentFragment = getIntent().getExtras().getInt("lobby");
        }

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
        return globalPreferencesViewModel.getLanguageName();
    }

    /**
     * changeTheme method
     *
     */
    public void changeTheme() {

        int colorSpace = globalPreferencesViewModel.getColorSpace();

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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        recreate();
    }


    /**
     * Resets ObjectiveViewModel and EvidenceViewModel data upon Activity exit
     */
    @Override
    public void onBackPressed() {

        objectivesViewModel.reset();
        evidenceViewModel.reset();

        super.onBackPressed();
    }

}

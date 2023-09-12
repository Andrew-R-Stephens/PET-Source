package com.TritiumGaming.phasmophobiaevidencepicker.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MapMenuViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.ObjectivesViewModel;

/**
 * InvestigationActivity class
 *
 * @author TritiumGamingStudios
 */
public class InvestigationActivity extends PETActivity {

    /*
    private FirebaseAnalytics analytics;

    private GlobalPreferencesViewModel globalPreferencesViewModel;
    private PermissionsViewModel permissionsViewModel;
    */
    private EvidenceViewModel evidenceViewModel;
    private ObjectivesViewModel objectivesViewModel;
    private MapMenuViewModel mapMenuViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        initViewModels();

        //determineInvestigationFragment();
        setContentView(R.layout.activity_investigation_solo);

    }

    protected ViewModelProvider.AndroidViewModelFactory initViewModels() {

        ViewModelProvider.AndroidViewModelFactory factory = super.initViewModels();

        /*analytics = FirebaseAnalytics.getInstance(this);

        ViewModelProvider.AndroidViewModelFactory factory =
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication());

        globalPreferencesViewModel = factory.create(GlobalPreferencesViewModel.class);
        globalPreferencesViewModel.init(InvestigationActivity.this);

        permissionsViewModel = factory.create(
                PermissionsViewModel.class);
        permissionsViewModel = new ViewModelProvider(this).get(
                PermissionsViewModel.class);*/

        evidenceViewModel = factory.create(
                EvidenceViewModel.class);

        objectivesViewModel = factory.create(
                ObjectivesViewModel.class);

        mapMenuViewModel = factory.create(
                MapMenuViewModel.class);

        return factory;
    }

    protected void initPrefs() {
        super.initPrefs();

        setLanguage(getAppLanguage());
    }

    /*
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
    */

    /*
    public void setLanguage(String lang) {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
    */

    /*
    public String getAppLanguage() {
        return globalPreferencesViewModel.getLanguageName();
    }
    */

    /*
    public int getFontStyle(int fontType) {
        switch (fontType) {
            case 1: {
                return R.style.Android;
            }
            case 2: {
                return R.style.Journal;
            }
            case 3: {
                return R.style.Brick;
            }
            case 4: {
                return R.style.Clean;
            }
            default: {
                return R.style.Fonts_Base;
            }
        }
    }

    public int getColorSpace(int colorSpace) {
        switch (colorSpace) {
            case 1: {
                return R.style.Monochromacy;
            }
            case 2: {
                return R.style.Deuteranomaly;
            }
            case 3: {
                return R.style.Protanomaly;
            }
            case 4: {
                return R.style.Tritanomaly;
            }
            case 5: {
                return R.style.Funhouse;
            }
            default: {
                return R.style.Colorblind_Base;
            }
        }
    }

    public void changeTheme(int colorSpace, int fontType) {

        int styleId = getFontStyle(fontType);
        getTheme().applyStyle(styleId, true);

        int colorSpaceId = getColorSpace(colorSpace);
        setTheme(colorSpaceId);

    }
    */


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

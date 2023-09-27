package com.TritiumGaming.phasmophobiaevidencepicker.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.GlobalPreferencesViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MapMenuViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.ObjectivesViewModel;

/**
 * InvestigationActivity class
 *
 * @author TritiumGamingStudios
 */
public class InvestigationActivity extends PETActivity {

    protected GlobalPreferencesViewModel globalPreferencesViewModel;

    protected EvidenceViewModel evidenceViewModel;
    protected ObjectivesViewModel objectivesViewModel;
    protected MapMenuViewModel mapMenuViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_investigation_solo);

    }

    protected ViewModelProvider.AndroidViewModelFactory initViewModels() {

        ViewModelProvider.AndroidViewModelFactory factory = super.initViewModels();

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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        recreate();
    }

    public void clearViewModels() {
        if(objectivesViewModel != null) {
            objectivesViewModel.reset();
        }
        if(evidenceViewModel != null) {
            evidenceViewModel.reset();
        }
    }

    /**
     * Resets ObjectiveViewModel and EvidenceViewModel data upon Activity exit
     */
    @Override
    public void onBackPressed() {

        clearViewModels();

        super.onBackPressed();
    }

    @Override
    public void finish() {

        clearViewModels();

        super.finish();
    }
}

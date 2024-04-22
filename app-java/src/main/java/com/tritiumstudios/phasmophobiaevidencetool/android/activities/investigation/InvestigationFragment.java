package com.tritiumstudios.phasmophobiaevidencetool.android.activities.investigation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.tritiumstudios.phasmophobiaevidencetool.R;
import com.tritiumstudios.phasmophobiaevidencetool.android.activities.pet.PETFragment;
import com.tritiumstudios.phasmophobiaevidencetool.android.data.viewmodels.MapMenuViewModel;
import com.tritiumstudios.phasmophobiaevidencetool.android.data.viewmodels.ObjectivesViewModel;
import com.tritiumstudios.phasmophobiaevidencetool.android.data.viewmodels.EvidenceViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;


/**
 * EvidenceFragment class
 *
 * @author TritiumGamingStudios
 */
public abstract class InvestigationFragment extends PETFragment {

    protected EvidenceViewModel evidenceViewModel;
    protected ObjectivesViewModel objectivesViewModel;
    protected MapMenuViewModel mapMenuViewModel;

    protected AdRequest adRequest;

    public InvestigationFragment() {
        super();
    }

    /**
     * EvidenceFragment constructor
     * <p>
     * TODO
     *
     * @param layout -
     */
    public InvestigationFragment(int layout) {
        super(layout);
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        super.init();

        try {
            ((InvestigationActivity) requireActivity()).initNavigationComponents();
        } catch (IllegalStateException e) {
             e.printStackTrace();
        }

        initAd(view.findViewById(R.id.adView));
    }

    protected void initViewModels() {
        initGlobalPreferencesViewModel();
        initEvidenceViewModel();
        initObjectivesViewModel();
        initMapMenuViewModel();
    }

    private void initMapMenuViewModel() {
        if (mapMenuViewModel == null) {
            mapMenuViewModel = new ViewModelProvider(requireActivity()).get(MapMenuViewModel.class);
            mapMenuViewModel.init(getContext());
        }
    }

    private void initObjectivesViewModel() {
        if (objectivesViewModel == null) {
            objectivesViewModel =
                    new ViewModelProvider(requireActivity()).get(ObjectivesViewModel.class);
        }
    }

    private void initEvidenceViewModel() {
        if (evidenceViewModel == null) {
            evidenceViewModel =
                    new ViewModelProvider(requireActivity()).get(EvidenceViewModel.class);
            evidenceViewModel.init(getContext());
        }
    }

    /**
     * softReset
     * Resets component data without completely rebuilding the Fragment
     */
    public abstract void softReset();

    protected void initAd(@Nullable AdView mAdView) {
        if(mAdView == null) {
            return;
        }

        MobileAds.initialize(requireContext(), initializationStatus -> { });
        adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void backPressedHandler() {

        try {
            if (((InvestigationActivity) requireActivity()).closeNavigationDrawer()) {
                return;
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        if(popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            return;
        }

        Log.d("Backstack", "Popping");
        try {
            if(!Navigation.findNavController(requireView()).popBackStack()) {
                Log.d("Backstack", "Could not Pop");
                requireActivity().finish();
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void saveStates(){
        super.saveStates();
    }

}


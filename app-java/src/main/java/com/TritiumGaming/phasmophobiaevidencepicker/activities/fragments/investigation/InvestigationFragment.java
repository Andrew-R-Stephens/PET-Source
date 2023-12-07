package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.investigation;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.InvestigationActivity;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.GlobalPreferencesViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MapMenuViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.ObjectivesViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.firebase.analytics.FirebaseAnalytics;


/**
 * EvidenceFragment class
 *
 * @author TritiumGamingStudios
 */
public abstract class InvestigationFragment extends Fragment {

    protected FirebaseAnalytics analytics;

    protected EvidenceViewModel evidenceViewModel;
    protected ObjectivesViewModel objectivesViewModel;
    protected MapMenuViewModel mapMenuViewModel;
    protected GlobalPreferencesViewModel globalPreferencesViewModel;

    protected PopupWindow popupWindow;

    protected AdRequest adRequest;


    public InvestigationFragment() {}

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

        initFirebase();

        if (evidenceViewModel == null) {
            evidenceViewModel =
                    new ViewModelProvider(requireActivity()).get(EvidenceViewModel.class);
            evidenceViewModel.init(getContext());
        }

        if (objectivesViewModel == null) {
            objectivesViewModel =
                    new ViewModelProvider(requireActivity()).get(ObjectivesViewModel.class);
        }

        if (mapMenuViewModel == null) {
            mapMenuViewModel = new ViewModelProvider(requireActivity()).get(MapMenuViewModel.class);
            mapMenuViewModel.init(getContext());
        }

        if (globalPreferencesViewModel == null) {
            globalPreferencesViewModel =
                    new ViewModelProvider(requireActivity()).get(GlobalPreferencesViewModel.class);
            if (getContext() != null) {
                globalPreferencesViewModel.init(getContext());
            }
        }

        setOnBackPressed();

        if(getActivity() != null) {
            ((InvestigationActivity) getActivity()).initComponents();
        }

    }

    /**
     * softReset
     * Resets component data without completely rebuilding the Fragment
     */
    public abstract void softReset();

    protected void initFirebase() {
        if(getContext() != null){
            analytics = FirebaseAnalytics.getInstance(getContext());
            Log.d("Firebase", "Obtained instance.");
        }
    }

    public void backPressedHandler() {

        if(super.getActivity() != null &&
                ((InvestigationActivity)super.getActivity()).closeDrawer()) {
            return;
        }

        if(popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            return;
        }

        if(getActivity() != null && getView() != null) {
            Log.d("Backstack", "Popping");
            NavController navController = Navigation.findNavController(getView());
            if(!navController.popBackStack()) {
                Log.d("Backstack", "Could not Pop");
                getActivity().finish();
            }

        }
    }

    public void setOnBackPressed() {
        if(getActivity() == null) { return; }

        getActivity().getOnBackPressedDispatcher()
                .addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                backPressedHandler();
            }
        });
    }

}


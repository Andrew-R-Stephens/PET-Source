package com.TritiumGaming.phasmophobiaevidencepicker.activities.pet;

import android.os.Build;
import android.widget.PopupWindow;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.NetworkUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared.GlobalPreferencesViewModel;
import com.google.firebase.analytics.FirebaseAnalytics;

public abstract class PETFragment extends Fragment {

    protected FirebaseAnalytics analytics;

    protected GlobalPreferencesViewModel globalPreferencesViewModel;

    @Nullable
    protected PopupWindow popupWindow;

    protected PETFragment() {
    }

    protected PETFragment(int layout) {
        super(layout);
    }

    protected void init() {
        setOnBackPressed();

        initViewModels();
        initFirebaseAnalytics();
    }

    protected abstract void initViewModels();

    protected void initGlobalPreferencesViewModel() {
        try {
            if (globalPreferencesViewModel == null) {
                globalPreferencesViewModel =
                        new ViewModelProvider(requireActivity()).get(GlobalPreferencesViewModel.class);

                globalPreferencesViewModel.init(requireContext());
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * saveStates method
     */
    protected void saveGlobalPreferencesViewModel() {
        try {
            if (globalPreferencesViewModel != null) {
                    globalPreferencesViewModel.saveToFile(requireContext());
            }
        } catch (IllegalStateException e){
            e.printStackTrace();
        }
    }

    /**
     * refreshFragment
     */
    protected void refreshFragment() {

        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(PETFragment.this).commitNow();
        ft = getParentFragmentManager().beginTransaction();
        ft.attach(PETFragment.this).commitNow();

    }

    protected void backPressedHandler() {
        if(closePopup()) {
            return;
        }

        try {
            Navigation.findNavController(requireView()).popBackStack();
    } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    protected boolean closePopup() {
        if(popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            return true;
        }

        return false;
    }

    protected void setOnBackPressed() {
        try {
            requireActivity().getOnBackPressedDispatcher()
                    .addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
                        @Override
                        public void handleOnBackPressed() {
                            backPressedHandler();
                        }
                    });
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }

    protected void initFirebaseAnalytics() {
        try {
            this.analytics = ((PETActivity) requireActivity()).getFirebaseAnalytics();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    protected boolean checkInternetConnection() {
        if(globalPreferencesViewModel == null) { return false; }

        try {
            return (NetworkUtils.isNetworkAvailable(requireContext(),
                    globalPreferencesViewModel.getNetworkPreference()));
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected void saveStates() {
        saveGlobalPreferencesViewModel();
    }
}

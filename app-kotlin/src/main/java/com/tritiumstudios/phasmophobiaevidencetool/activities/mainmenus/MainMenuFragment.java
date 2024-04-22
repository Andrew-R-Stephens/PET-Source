package com.tritiumstudios.phasmophobiaevidencetool.activities.mainmenus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.tritiumstudios.phasmophobiaevidencetool.R;
import com.tritiumstudios.phasmophobiaevidencetool.activities.pet.PETFragment;
import com.tritiumstudios.phasmophobiaevidencetool.data.viewmodels.shared.NewsletterViewModel;
import com.tritiumstudios.phasmophobiaevidencetool.data.viewmodels.MainMenuViewModel;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

/**
 * TitleScreenFragment class
 *
 * @author TritiumGamingStudios
 */
public abstract class MainMenuFragment extends PETFragment {

    protected MainMenuViewModel mainMenuViewModel;
    protected NewsletterViewModel newsLetterViewModel;

    public MainMenuFragment() {
        super();
    }

    /**
     * EvidenceFragment constructor
     * <p>
     * TODO
     *
     * @param layout -
     */
    public MainMenuFragment(int layout) {
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

    /*
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        super.init();
    }
    */

    protected void initViewModels() {
        super.initGlobalPreferencesViewModel();
    }

    protected void initMainMenuViewModel() {
        if (mainMenuViewModel == null) {
            try {
                mainMenuViewModel =
                        new ViewModelProvider(requireActivity()).get(MainMenuViewModel.class);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    protected void initNewsletterViewModel() {
        if (newsLetterViewModel == null) {
            try {
                newsLetterViewModel =
                        new ViewModelProvider(requireActivity())
                                .get(NewsletterViewModel.class);
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * saveStates method
     */
    protected void saveNewsletterViewModel() {
        if (newsLetterViewModel != null) {
            try {
                newsLetterViewModel.saveToFile(requireContext());
            } catch (IllegalStateException e){
                e.printStackTrace();
            }
        }
    }

    protected void initAdView(@NonNull AdView adView) {
        try {
            MobileAds.initialize(requireActivity(), initializationStatus -> {
            });
            if (!mainMenuViewModel.hasAdRequest()) {
                mainMenuViewModel.setAdRequest(new AdRequest.Builder().build());
            }
            adView.loadAd(mainMenuViewModel.getAdRequest());
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void backPressedHandler() {
        super.backPressedHandler();
    }

    @Override
    protected void saveStates() {
        super.saveStates();
        saveNewsletterViewModel();
    }

}
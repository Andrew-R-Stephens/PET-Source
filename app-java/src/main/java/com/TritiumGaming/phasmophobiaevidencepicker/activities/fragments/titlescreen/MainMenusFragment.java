package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.PETFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.NewsletterViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.TitlescreenViewModel;

/**
 * TitleScreenFragment class
 *
 * @author TritiumGamingStudios
 */
public class MainMenusFragment extends PETFragment {

    protected TitlescreenViewModel titleScreenViewModel;
    protected NewsletterViewModel newsLetterViewModel;

    public MainMenusFragment() {
        super();
    }

    /**
     * EvidenceFragment constructor
     * <p>
     * TODO
     *
     * @param layout -
     */
    public MainMenusFragment(int layout) {
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
    }

    protected void initViewModels() {
        super.initGlobalPreferencesViewModel();
    }

    protected void initTitleScreenViewModel() {
        if (titleScreenViewModel == null) {
            try {
                titleScreenViewModel =
                        new ViewModelProvider(requireActivity()).get(TitlescreenViewModel.class);
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

    @Override
    protected void backPressedHandler() {
        super.backPressedHandler();
    }

}
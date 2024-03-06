package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.TritiumGaming.phasmophobiaevidencepicker.activities.pet.FirebaseFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.MainMenuViewModel;

/**
 * TitleScreenFragment class
 *
 * @author TritiumGamingStudios
 */
public abstract class MainMenuFirebaseFragment extends FirebaseFragment {

    protected MainMenuViewModel mainMenuViewModel;

    public MainMenuFirebaseFragment() {
        super();
    }

    public MainMenuFirebaseFragment(int layout) {
        super(layout);
    }

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

    @Override
    protected void backPressedHandler() {
        super.backPressedHandler();
    }

    @Override
    protected void saveStates() {}

}
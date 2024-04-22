package com.tritiumstudios.phasmophobiaevidencetool.android.activities.mainmenus.applanguages;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tritiumstudios.phasmophobiaevidencetool.R;
import com.tritiumstudios.phasmophobiaevidencetool.android.activities.mainmenus.MainMenuActivity;
import com.tritiumstudios.phasmophobiaevidencetool.android.activities.mainmenus.MainMenuFragment;
import com.tritiumstudios.phasmophobiaevidencetool.android.activities.mainmenus.applanguages.views.LanguagesAdapterView;

import java.util.ArrayList;
import java.util.Arrays;

public class AppLanguageFragment extends MainMenuFragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // OBTAIN VIEW MODEL REFERENCE

        super.init();

        return inflater.inflate(R.layout.fragment_languages, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        // INITIALIZE VIEWS
        ConstraintLayout btn_confirmClose = view.findViewById(R.id.constraintlayout_confirmbutton);
        ConstraintLayout btn_cancelClose = view.findViewById(R.id.constraintlayout_cancelbutton);
        RecyclerView recyclerViewLanguages = view.findViewById(R.id.recyclerview_languageslist);

        // LISTENERS
        btn_confirmClose.setOnClickListener(v -> {
            mainMenuViewModel.setLanguageSelectedOriginal(-1);
            try {
                Navigation.findNavController(v).popBackStack();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        });

        btn_cancelClose.setOnClickListener(v -> handleDiscardChanges());

        // DATA
        int selected = 0;
        ArrayList<String> languageNames = new ArrayList<>();
        try {
            languageNames = new ArrayList<>(Arrays.asList(
                    requireContext().getResources().getStringArray(
                            R.array.languages_name)));

            selected = globalPreferencesViewModel.getLanguageIndex(
                    new ArrayList<>(Arrays.asList(
                            requireContext().getResources().getStringArray(
                                    R.array.languages_abbreviation))));
            if (mainMenuViewModel.getLanguageSelectedOriginal() == -1) {
                mainMenuViewModel.setLanguageSelectedOriginal(selected);
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < languageNames.size(); i++) {
            LanguagesAdapterView adapter = new LanguagesAdapterView(
                    languageNames, selected, position -> {
                        if (globalPreferencesViewModel != null && mainMenuViewModel != null) {
                            globalPreferencesViewModel.setLanguage(
                                    position,
                                    AppLanguageFragment.this.getResources().getStringArray(
                                            R.array.languages_abbreviation));
                            mainMenuViewModel.setCanRefreshFragment(true);
                        }
                        AppLanguageFragment.this.configureLanguage();
                        AppLanguageFragment.this.refreshFragment();
                    });
            recyclerViewLanguages.setAdapter(adapter);
            try {
                recyclerViewLanguages.setLayoutManager(new LinearLayoutManager(requireContext()));
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void initViewModels() {
        super.initViewModels();
        initMainMenuViewModel();
    }

    @Override
    protected void backPressedHandler() {
        handleDiscardChanges();

        String message = getString(R.string.toast_discardchanges);
        try {
            Toast.makeText(requireActivity(),
                    message,
                    com.google.android.material.R.integer.material_motion_duration_short_2).show();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }

    private void handleDiscardChanges() {
        if (globalPreferencesViewModel != null && mainMenuViewModel != null) {
            globalPreferencesViewModel.setLanguage(
                    mainMenuViewModel.getLanguageSelectedOriginal(),
                    getResources().getStringArray(
                            R.array.languages_abbreviation));
            Log.d("Languages", "Set language = " + mainMenuViewModel.getLanguageSelectedOriginal());
        }
        Log.d("Languages", "GlobalPreferencesViewModel = " + (globalPreferencesViewModel == null ? "null" : "not null." ) + ", TitleScreenViewModel = " + (mainMenuViewModel == null ? "null" : "not null." ));

        configureLanguage();

        try {
            Navigation.findNavController(requireView()).popBackStack();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * configureLanguage
     */
    public void configureLanguage() {
        if (globalPreferencesViewModel != null) {
            try {
                ((MainMenuActivity) requireActivity()).setLanguage(
                        globalPreferencesViewModel.getLanguageName());
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * refreshFragment
     */
    public void refreshFragment() {
        if (mainMenuViewModel != null && mainMenuViewModel.canRefreshFragment()) {
            super.refreshFragment();
            mainMenuViewModel.setCanRefreshFragment(false);
        }
    }


    /**
     * saveStates method
     * <p>
     * TODO
     */
    public void saveStates() {
        if (globalPreferencesViewModel != null) {
            try {
                globalPreferencesViewModel.saveToFile(requireContext());
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * onPause method
     */
    @Override
    public void onPause() {
        // SAVE PERSISTENT DATA
        saveStates();

        super.onPause();
    }

}

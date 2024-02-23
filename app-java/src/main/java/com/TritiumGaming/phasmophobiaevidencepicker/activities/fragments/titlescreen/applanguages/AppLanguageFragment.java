package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.applanguages;

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

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.TitleScreenActivity;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.PETFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.MainMenusFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.applanguages.views.LanguagesAdapterView;

import java.util.ArrayList;
import java.util.Arrays;

public class AppLanguageFragment extends MainMenusFragment {

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
            titleScreenViewModel.setLanguageSelectedOriginal(-1);
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
            if (titleScreenViewModel.getLanguageSelectedOriginal() == -1) {
                titleScreenViewModel.setLanguageSelectedOriginal(selected);
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < languageNames.size(); i++) {
            LanguagesAdapterView adapter = new LanguagesAdapterView(
                    languageNames, selected, position -> {
                        if (globalPreferencesViewModel != null && titleScreenViewModel != null) {
                            globalPreferencesViewModel.setLanguage(
                                    position,
                                    AppLanguageFragment.this.getResources().getStringArray(
                                            R.array.languages_abbreviation));
                            titleScreenViewModel.setCanRefreshFragment(true);
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
        initTitleScreenViewModel();
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
        if (globalPreferencesViewModel != null && titleScreenViewModel != null) {
            globalPreferencesViewModel.setLanguage(
                    titleScreenViewModel.getLanguageSelectedOriginal(),
                    getResources().getStringArray(
                            R.array.languages_abbreviation));
            Log.d("Languages", "Set language = " + titleScreenViewModel.getLanguageSelectedOriginal());
        }
        Log.d("Languages", "GlobalPreferencesViewModel = " + (globalPreferencesViewModel == null ? "null" : "not null." ) + ", TitleScreenViewModel = " + (titleScreenViewModel == null ? "null" : "not null." ));

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
                ((TitleScreenActivity) requireActivity()).setLanguage(
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
        if (titleScreenViewModel != null && titleScreenViewModel.canRefreshFragment()) {
            super.refreshFragment();
            titleScreenViewModel.setCanRefreshFragment(false);
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

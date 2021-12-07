package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.applanguages;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.TitleScreenActivity;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.applanguages.views.LanguagesAdapterView;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.GlobalPreferencesViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.TitlescreenViewModel;

import java.util.ArrayList;
import java.util.Arrays;

public class AppLanguageFragment extends Fragment {

    private GlobalPreferencesViewModel globalPreferencesViewModel = null;
    private TitlescreenViewModel titleScreenViewModel = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) { // OBTAIN VIEW MODEL REFERENCE
        if (globalPreferencesViewModel == null) {
            globalPreferencesViewModel = new ViewModelProvider(
                    requireActivity()).get(GlobalPreferencesViewModel.class);
        }
        if (getContext() != null) {
            globalPreferencesViewModel.init(getContext());
        }

        if (titleScreenViewModel == null) {
            titleScreenViewModel = new ViewModelProvider(
                    requireActivity()).get(TitlescreenViewModel.class);
        }

        return inflater.inflate(R.layout.fragment_languages, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // INITIALIZE VIEWS
        ConstraintLayout btn_confirmClose = view.findViewById(R.id.constraintlayout_confirmbutton);
        ConstraintLayout btn_cancelClose = view.findViewById(R.id.constraintlayout_cancelbutton);
        RecyclerView recyclerViewLanguages = view.findViewById(R.id.recyclerview_languageslist);


        // LISTENERS
        btn_confirmClose.setOnClickListener(v -> {
            titleScreenViewModel.setLanguageSelectedOriginal(-1);
            Navigation.findNavController(v).popBackStack();
        });
        btn_cancelClose.setOnClickListener(v -> {
                    if (globalPreferencesViewModel != null && titleScreenViewModel != null) {
                        globalPreferencesViewModel.setLanguage(
                                titleScreenViewModel.getLanguageSelectedOriginal(),
                                getResources().getStringArray(
                                        R.array.languages_abbreviation));
                    }
                    configureLanguage();
                    Navigation.findNavController(v).popBackStack();
                }
        );

        if(getActivity() != null) {
            getActivity().getOnBackPressedDispatcher().addCallback(this,
                    new OnBackPressedCallback(true) {
                @Override
                public void handleOnBackPressed() {
                    // Disables OnBackPressed
                }
            });
        }

        // DATA
        if (getContext() != null) {

            ArrayList<String> languageNames = new ArrayList<>(Arrays.asList(
                    getContext().getResources().getStringArray(R.array.languages_name)));
            int selected = globalPreferencesViewModel.getLanguageIndex(
                    new ArrayList<>(Arrays.asList(
                        getContext().getResources().getStringArray(
                                R.array.languages_abbreviation))));
            if(titleScreenViewModel.getLanguageSelectedOriginal() == -1) {
                titleScreenViewModel.setLanguageSelectedOriginal(selected);
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
                recyclerViewLanguages.setLayoutManager(new LinearLayoutManager(getContext()));
            }
        }
    }

    /**
     * configureLanguage
     */
    public void configureLanguage() {
        if (globalPreferencesViewModel != null && getActivity() != null) {
            ((TitleScreenActivity) getActivity()).setLanguage(
                    globalPreferencesViewModel.getLanguageName());
        }
    }

    /**
     * refreshFragment
     */
    public void refreshFragment() {
        if (titleScreenViewModel != null && titleScreenViewModel.canRefreshFragment()) {
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            if (Build.VERSION.SDK_INT >= 26) {
                ft.setReorderingAllowed(false);
            }
            ft.detach(AppLanguageFragment.this).commitNow();
            ft.attach(AppLanguageFragment.this).commitNow();
            titleScreenViewModel.setCanRefreshFragment(false);
        }
    }


    /**
     * saveStates method
     * <p>
     * TODO
     */
    public void saveStates() {
        if (globalPreferencesViewModel != null && getContext() != null) {
            globalPreferencesViewModel.saveToFile(getContext());
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

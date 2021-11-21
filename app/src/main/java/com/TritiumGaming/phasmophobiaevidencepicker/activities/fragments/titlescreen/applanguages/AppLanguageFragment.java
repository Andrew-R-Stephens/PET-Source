package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.applanguages;

import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
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
        if (globalPreferencesViewModel == null)
            globalPreferencesViewModel = new ViewModelProvider(
                    requireActivity()).get(GlobalPreferencesViewModel.class);
        // INITIALIZE VIEW MODEL
        if (getContext() != null)
            globalPreferencesViewModel.init(getContext());

        if (titleScreenViewModel == null)
            titleScreenViewModel = new ViewModelProvider(
                    requireActivity()).get(TitlescreenViewModel.class);

        return inflater.inflate(R.layout.fragment_languages, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // INITIALIZE VIEWS
        AppCompatTextView title = view.findViewById(R.id.label_languages);
        ImageButton closeButton = view.findViewById(R.id.popup_close_button);
        RecyclerView recyclerViewLanguages = view.findViewById(R.id.recyclerview_languageslist);

        // TEXT SIZE
        title.setAutoSizeTextTypeUniformWithConfiguration(
                12,
                50,
                1,
                TypedValue.COMPLEX_UNIT_SP);

        // LISTENERS
        closeButton.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        // DATA
        ArrayList<String> languageNames = new ArrayList<>(Arrays.asList(
                getResources().getStringArray(R.array.languages_name)));
        if (getContext() != null) {
            for (int i = 0; i < languageNames.size(); i++) {
                LanguagesAdapterView adapter = new LanguagesAdapterView(
                        languageNames, new LanguagesAdapterView.OnLanguageListener() {
                    @Override
                    public void onNoteClick(int position) {
                        if (globalPreferencesViewModel != null && titleScreenViewModel != null) {
                            globalPreferencesViewModel.setLanguage(
                                    position,
                                    AppLanguageFragment.this.getResources().getStringArray(
                                            R.array.languages_abbreviation));
                            titleScreenViewModel.setCanRefreshFragment(true);
                        }
                        AppLanguageFragment.this.configureLanguage();
                        AppLanguageFragment.this.refreshFragment();
                    }
                });
                recyclerViewLanguages.setAdapter(adapter);
                recyclerViewLanguages.setLayoutManager(new LinearLayoutManager(view.getContext()));
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
        //Log.d("Fragment", "Refreshing");
        if (titleScreenViewModel != null && titleScreenViewModel.canRefreshFragment()) {
            FragmentTransaction ft = getParentFragmentManager().beginTransaction();
            if (Build.VERSION.SDK_INT >= 26)
                ft.setReorderingAllowed(false);
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
        if (globalPreferencesViewModel != null && getContext() != null)
            globalPreferencesViewModel.saveToFile(getContext());
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

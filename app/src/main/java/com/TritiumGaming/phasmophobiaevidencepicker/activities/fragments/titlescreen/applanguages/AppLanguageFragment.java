package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.applanguages;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.TitleScreenActivity;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.titlescreen.TitlescreenFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.GlobalPreferencesViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.TitlescreenViewModel;

public class AppLanguageFragment extends Fragment {

    private GlobalPreferencesViewModel globalPreferencesViewModel = null;

    private TitlescreenViewModel titleScreenViewModel = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) { // OBTAIN VIEW MODEL REFERENCE
        if (globalPreferencesViewModel == null)
            globalPreferencesViewModel = new ViewModelProvider(requireActivity()).get(GlobalPreferencesViewModel.class);
        // INITIALIZE VIEW MODEL
        if (getContext() != null)
            globalPreferencesViewModel.init(getContext());

        if (titleScreenViewModel == null)
            titleScreenViewModel = new ViewModelProvider(requireActivity()).get(TitlescreenViewModel.class);

        return inflater.inflate(R.layout.popup_languages, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // INITIALIZE VIEWS
        AppCompatTextView title = view.findViewById(R.id.label_languages);
        ImageButton closeButton = view.findViewById(R.id.popup_close_button);
        LinearLayout languages_layout = view.findViewById(R.id.languages_verticallayout);

        // TEXT SIZE
        title.setAutoSizeTextTypeUniformWithConfiguration(12, 50, 1, TypedValue.COMPLEX_UNIT_SP);

        // LISTENERS
        closeButton.setOnClickListener(v -> {
            Navigation.findNavController(v).popBackStack();
        });

        // DATA
        String[] langNames = getResources().getStringArray(R.array.languages_name);
        if (getContext() != null) {
            for (int i = 0; i < langNames.length; i++) {
                AppCompatTextView name = new AppCompatTextView(getContext());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
                name.setGravity(Gravity.CENTER);
                name.setLayoutParams(params);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                    name.setTypeface(getResources().getFont(R.font.norse_regular), Typeface.NORMAL);
                else
                    name.setTypeface(ResourcesCompat.getFont(getContext(), R.font.norse_regular));
                name.setAutoSizeTextTypeUniformWithConfiguration(12, 30, 1, TypedValue.COMPLEX_UNIT_SP);
                name.setTextColor(Color.WHITE);
                name.setText(langNames[i]);

                int loc = i;
                name.setOnClickListener(v -> {
                    if (globalPreferencesViewModel != null && titleScreenViewModel != null) {
                        globalPreferencesViewModel.setLanguage(loc, getResources().getStringArray(R.array.languages_abbreviation));
                        titleScreenViewModel.setCanRefreshFragment(true);
                    }
                    configureLanguage();
                    refreshFragment();
                });
                languages_layout.addView(name);
            }
        }
    }

    /**
     * configureLanguage
     */
    public void configureLanguage() {
        if (globalPreferencesViewModel != null) {
            if (getActivity() != null)
                ((TitleScreenActivity) getActivity()).setLanguage(globalPreferencesViewModel.getLanguageName());
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
     *
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

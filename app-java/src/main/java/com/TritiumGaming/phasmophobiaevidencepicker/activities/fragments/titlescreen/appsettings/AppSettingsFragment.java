package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.appsettings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.PETActivity;
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming.subsets.ColorThemeControl;
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming.subsets.FontThemeControl;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.FormatterUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.GoogleMobileAdsConsentManager;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.GlobalPreferencesViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.TitlescreenViewModel;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.HashMap;

public class AppSettingsFragment extends Fragment {

    private FirebaseAnalytics analytics;
    private GoogleMobileAdsConsentManager googleMobileAdsConsentManager;

    private GlobalPreferencesViewModel globalPreferencesViewModel = null;
    private TitlescreenViewModel titleScreenViewModel = null;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        initFirebase();

        // OBTAIN VIEW MODEL REFERENCE
        if (globalPreferencesViewModel == null) {
            globalPreferencesViewModel = new ViewModelProvider(
                    requireActivity()).get(GlobalPreferencesViewModel.class);
            // INITIALIZE VIEW MODEL
            if (getContext() != null) {
                globalPreferencesViewModel.init(getContext());
            }
        }

        if (titleScreenViewModel == null) {
            titleScreenViewModel = new ViewModelProvider(
                    requireActivity()).get(TitlescreenViewModel.class);
        }

        return inflater.inflate(R.layout.fragment_appsettings, container, false);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        AppCompatTextView text_colorblindmode_selectedname =
                view.findViewById(R.id.colorblindmode_selectedname);
        final AppCompatTextView text_fontStyle_selectedname = view.findViewById(R.id.font_selectedname);

        AppCompatTextView switch_huntwarning_timetext =
                view.findViewById(R.id.seekbar_huntwarningtimeout_timetext);
        AppCompatTextView switch_huntwarning_othertext =
                view.findViewById(R.id.seekbar_huntwarningtimeout_othertext);

        SwitchCompat switch_isAlwaysOn_switch = view.findViewById(R.id.switch_alwayson_switch);
        SwitchCompat switch_network_switch = view.findViewById(R.id.switch_network_switch);
        SwitchCompat switch_huntwarningaudio_switch =
                view.findViewById(R.id.switch_huntwarningaudio_switch);
        SwitchCompat switch_leftHandMode_switch =
                view.findViewById(R.id.switch_leftHandMode_switch);

        SeekBar seekBar_huntwarningTimeout = view.findViewById(R.id.settings_huntwarning_seekbar);

        AppCompatImageView btn_colorblindMode_left = view.findViewById(R.id.colorblindmode_leftbutton);
        AppCompatImageView btn_colorblindMode_right = view.findViewById(R.id.colorblindmode_rightbutton);
        AppCompatImageView btn_fontStyle_left = view.findViewById(R.id.font_leftbutton);
        AppCompatImageView btn_fontStyle_right = view.findViewById(R.id.font_rightbutton);
        View listener_confirmClose = view.findViewById(R.id.listener_confirm);
        View listener_cancelClose = view.findViewById(R.id.listener_cancel);

        if(getActivity() != null) {
            googleMobileAdsConsentManager = new GoogleMobileAdsConsentManager(getActivity());
        }

        // COLORBLIND DATA
        ColorThemeControl colorThemesData = globalPreferencesViewModel.getColorThemeControl();
        text_colorblindmode_selectedname.setText(colorThemesData.getCurrentName());

        // COLORBLIND LISTENERS
        btn_colorblindMode_left.setOnClickListener(v -> {
            ColorThemeControl themeControl = globalPreferencesViewModel.getColorThemeControl();

            themeControl.iterateSelection(-1);
            text_colorblindmode_selectedname.setText(getString(themeControl.getCurrentName()));

            demoColorStyle(themeControl);
        });

        btn_colorblindMode_right.setOnClickListener(v -> {
            ColorThemeControl themeControl = globalPreferencesViewModel.getColorThemeControl();

            themeControl.iterateSelection(1);
            text_colorblindmode_selectedname.setText(getString(themeControl.getCurrentName()));

            demoColorStyle(themeControl);
        });

        /*
         * Setting Fonts
         */
        // FONT-STYLE DATA
        FontThemeControl fontThemesData = globalPreferencesViewModel.getFontThemeControl();
        text_fontStyle_selectedname.setText(fontThemesData.getCurrentName());

        // FONT-STYLE LISTENERS
        btn_fontStyle_left.setOnClickListener(v -> {
            fontThemesData.iterateSelection(-1);

            text_fontStyle_selectedname.setText(getString(fontThemesData.getCurrentName()));

            demoFontStyle(fontThemesData);
        });

        btn_fontStyle_right.setOnClickListener(v -> {
            fontThemesData.iterateSelection(1);

            text_fontStyle_selectedname.setText(getString(fontThemesData.getCurrentName()));

            demoFontStyle(fontThemesData);
        });

        // SWITCHES
        if (globalPreferencesViewModel != null) {
            // Screen Always On
            if (switch_isAlwaysOn_switch != null) {
                switch_isAlwaysOn_switch.setChecked(globalPreferencesViewModel.getIsAlwaysOn());
                switch_isAlwaysOn_switch.setOnClickListener(v -> {
                    if (globalPreferencesViewModel != null) {
                        globalPreferencesViewModel.setIsAlwaysOn(
                                switch_isAlwaysOn_switch.isChecked());
                    }
                });
            }
            // Allow Mobile Data
            if (switch_network_switch != null) {
                switch_network_switch.setChecked(
                        globalPreferencesViewModel.getNetworkPreference());
                switch_network_switch.setOnClickListener(v -> {
                    if (globalPreferencesViewModel != null) {
                        globalPreferencesViewModel.setNetworkPreference(
                                switch_network_switch.isChecked());
                    }
                });
            }
            // Allow Hunt Warning Audio
            if (switch_huntwarningaudio_switch != null) {
                switch_huntwarningaudio_switch.setChecked(
                        globalPreferencesViewModel.getIsHuntAudioAllowed());
                switch_huntwarningaudio_switch.setOnClickListener(v -> {
                    if (globalPreferencesViewModel != null) {
                        globalPreferencesViewModel.setHuntWarningAudioAllowed(
                                switch_huntwarningaudio_switch.isChecked());
                    }
                });
            }

            // Allow Hunt Warning Audio
            if (switch_leftHandMode_switch != null) {
                switch_leftHandMode_switch.setChecked(
                        globalPreferencesViewModel.getIsLeftHandSupportEnabled());
                switch_leftHandMode_switch.setOnClickListener(v -> {
                    if (globalPreferencesViewModel != null) {
                        globalPreferencesViewModel.setLeftHandSupportEnabled(
                                switch_leftHandMode_switch.isChecked());
                    }
                });
            }
        }


        // TIMEOUT SEEKBAR
        if (globalPreferencesViewModel != null) {
            if (seekBar_huntwarningTimeout != null) {
                seekBar_huntwarningTimeout.setMax(300001);
                if (globalPreferencesViewModel.getHuntWarningFlashTimeout() < 0) {
                    seekBar_huntwarningTimeout.setProgress(seekBar_huntwarningTimeout.getMax());
                } else {
                    seekBar_huntwarningTimeout.setProgress(
                            globalPreferencesViewModel.getHuntWarningFlashTimeout());
                }
                seekBar_huntwarningTimeout.setOnSeekBarChangeListener(
                        new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(
                                    SeekBar seekBar, int progress, boolean fromUser) {

                                if (fromUser) {

                                    globalPreferencesViewModel.setHuntWarningFlashTimeout(progress);

                                    double progressMax = 300000 /
                                            (double) seekBar_huntwarningTimeout.getMax();

                                    if (progress < seekBar_huntwarningTimeout.getMax()) {
                                        long breakdown = (long) (progressMax * progress / 1000L);
                                        String text = FormatterUtils.millisToTime(
                                                "%sm %ss", breakdown);
                                        switch_huntwarning_timetext.setText(text);
                                        switch_huntwarning_othertext.setText("");
                                    } else if (progress == seekBar_huntwarningTimeout.getMax()) {
                                        switch_huntwarning_timetext.setText("");
                                        switch_huntwarning_othertext.setText(R.string.settings_huntwarningflashtimeout_never);
                                    }
                                }
                            }

                            @Override
                            public void onStartTrackingTouch(SeekBar seekBar) {
                            }

                            @Override
                            public void onStopTrackingTouch(SeekBar seekBar) {
                            }

                        });

                double progressMax = 300000 / (double) seekBar_huntwarningTimeout.getMax();

                if (seekBar_huntwarningTimeout.getProgress() >= 0 &&
                        seekBar_huntwarningTimeout.getProgress() <
                                seekBar_huntwarningTimeout.getMax()) {
                    long breakdown =
                            (long) (progressMax * seekBar_huntwarningTimeout.getProgress() / 1000L);
                    String text = FormatterUtils.millisToTime("%sm %ss", breakdown);

                    switch_huntwarning_othertext.setText("");
                    switch_huntwarning_timetext.setText(text);
                } else {
                    switch_huntwarning_othertext.setText(R.string.settings_huntwarningflashtimeout_never);
                    switch_huntwarning_timetext.setText("");
                }
            }
        }

        // CANCEL BUTTON
        listener_cancelClose.setOnClickListener(v -> {
            revertDemoChanges();
            Navigation.findNavController(v).popBackStack();
        });

        // CONFIRM BUTTON
        listener_confirmClose.setOnClickListener(v -> {

            Bundle params = new Bundle();
            params.putString("event_type", "confirm_settings");
            HashMap<String, String> settings = globalPreferencesViewModel.getDataAsList();
            for (String key : settings.keySet()) {
                String value = settings.get(key);
                params.putString(key, value);
            }
            analytics.logEvent("event_settings", params);

            saveStates();
            Navigation.findNavController(v).popBackStack();
        });

        if(getActivity() != null) {
            getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                    new OnBackPressedCallback(true) {
                        @Override
                        public void handleOnBackPressed() {
                            revertDemoChanges();
                            Navigation.findNavController(view).popBackStack();

                            if(getContext() != null) {
                                String message = getString(R.string.toast_discardchanges);
                                Toast toast = Toast.makeText(getContext().getApplicationContext(),
                                        message,
                                        com.google.android.material.R.integer.material_motion_duration_short_2);
                                toast.show();
                            }
                        }
                    });
        }

        // Include a privacy setting if applicable
        if (googleMobileAdsConsentManager.isPrivacyOptionsRequired()) {
            ConstraintLayout adsLayout = view.findViewById(R.id.constraintLayout_ads);
            adsLayout.setVisibility(View.VISIBLE);

            AppCompatButton adsButton = view.findViewById(R.id.settings_ads_label);
            adsButton.setOnClickListener(v -> showAdsConsentForm(v.getContext()));
        }
    }

    private void revertDemoChanges() {
        globalPreferencesViewModel.getFontThemeControl().revertSelection();
        globalPreferencesViewModel.getColorThemeControl().revertSelection();

        if (getActivity() != null) {
            ((PETActivity) getActivity()).
                    changeTheme(globalPreferencesViewModel.getColorSpace(), globalPreferencesViewModel.getFontType());
        }
    }

    private void demoFontStyle(FontThemeControl fontThemeControl) {
        PETActivity activity = ((PETActivity)getActivity());
        if(activity != null) {
            activity.changeTheme(-1, fontThemeControl.getSelectedIndex());
        }
        refreshFragment();
    }

    private void demoColorStyle(ColorThemeControl colorThemeControl) {
        PETActivity activity = ((PETActivity)getActivity());
        if(activity != null) {
            activity.changeTheme(colorThemeControl.getSelectedIndex(), -1);
        }
        refreshFragment();
    }

    public void showAdsConsentForm(Context context) {

        // Handle changes to user consent.
        googleMobileAdsConsentManager.showPrivacyOptionsForm(
                getActivity(),
                formError -> {
                    if (formError != null) {
                        Toast.makeText(
                                        context,
                                        formError.getMessage(),
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                }
        );
        Log.d("AdsConsent", "should show consent form");

    }

    /**
     * refreshFragment
     */
    public void refreshFragment() {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(AppSettingsFragment.this).commitNow();
        ft = getParentFragmentManager().beginTransaction();
        ft.attach(AppSettingsFragment.this).commitNow();
    }


    private void initFirebase() {
        if(getContext() != null){
            analytics = FirebaseAnalytics.getInstance(getContext());
            Log.d("Firebase", "Obtained instance.");
        }
    }

    /**
     * saveStates method
     * <p>
     * TODO
     */
    public void saveStates() {

        if (globalPreferencesViewModel != null && getContext() != null) {
            globalPreferencesViewModel.getFontThemeControl().setSavedIndex();
            globalPreferencesViewModel.getColorThemeControl().setSavedIndex();

            globalPreferencesViewModel.saveToFile(getContext());
        }
        if (getActivity() != null) {
            ((PETActivity) getActivity()).
                    changeTheme(globalPreferencesViewModel.getColorSpace(), globalPreferencesViewModel.getFontType());
        }
        if(globalPreferencesViewModel.getIsAlwaysOn()) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

        /*
        if(getActivity() != null && mustRefresh) {
            getActivity().recreate();
        }

        if (getView() != null) {
            getView().setKeepScreenOn(globalPreferencesViewModel.getIsAlwaysOn());
        }
        */

    }

    /**
     * onPause method
     */
    @Override
    public void onPause() {

        super.onPause();
    }

}

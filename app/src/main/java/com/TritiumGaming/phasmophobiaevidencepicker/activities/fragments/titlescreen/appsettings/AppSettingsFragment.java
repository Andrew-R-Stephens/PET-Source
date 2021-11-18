package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.appsettings;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.TitleScreenActivity;
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.ColorThemesData;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.GlobalPreferencesViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.TitlescreenViewModel;

import java.text.DecimalFormat;

public class AppSettingsFragment extends Fragment {

    private GlobalPreferencesViewModel globalPreferencesViewModel = null;

    private TitlescreenViewModel titleScreenViewModel = null;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) { // OBTAIN VIEW MODEL REFERENCE
        if (globalPreferencesViewModel == null)
            globalPreferencesViewModel =
                    new ViewModelProvider(requireActivity()).get(GlobalPreferencesViewModel.class);
        // INITIALIZE VIEW MODEL
        if (getContext() != null)
            globalPreferencesViewModel.init(getContext());

        if (titleScreenViewModel == null)
            titleScreenViewModel =
                    new ViewModelProvider(requireActivity()).get(TitlescreenViewModel.class);

        return inflater.inflate(R.layout.fragment_appsettings, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        // INITIALIZE VIEWS
        AppCompatTextView primarytitle = view.findViewById(R.id.label_settingstitle);
        AppCompatTextView generalsettings_title =
                view.findViewById(R.id.settings_generalsettings_title);
        AppCompatTextView othersettings_title =
                view.findViewById(R.id.settings_othersettings_title);

        AppCompatTextView switch_screenIsAlwaysOn_title =
                view.findViewById(R.id.switch_alwayson_text);

        AppCompatTextView switch_network_title = view.findViewById(R.id.switch_network_text);

        AppCompatTextView text_colorblindmode_title = view.findViewById(R.id.colorblindmode_title);
        AppCompatTextView text_colorblindmode_selectedname =
                view.findViewById(R.id.colorblindmode_selectedname);
        AppCompatTextView text_colorblindmode_sidenote =
                view.findViewById(R.id.colorblindmode_note);

        AppCompatTextView switch_huntwarningaudio_text =
                view.findViewById(R.id.switch_huntwarningaudio_text);
        AppCompatTextView switch_huntwarning_title =
                view.findViewById(R.id.switch_huntwarningaudio_title);
        AppCompatTextView switch_huntwarning_timetext =
                view.findViewById(R.id.seekbar_huntwarningtimeout_timetext);
        AppCompatTextView switch_huntwarning_othertext =
                view.findViewById(R.id.seekbar_huntwarningtimeout_othertext);

        SwitchCompat switch_isAlwaysOn_switch = view.findViewById(R.id.switch_alwayson_switch);
        SwitchCompat switch_network_switch = view.findViewById(R.id.switch_network_switch);
        SwitchCompat switch_huntwarningaudio_switch =
                view.findViewById(R.id.switch_huntwarningaudio_switch);

        SeekBar seekBar_huntwarningTimeout = view.findViewById(R.id.settings_huntwarning_seekbar);

        ImageButton btn_colorblindMode_left = view.findViewById(R.id.colorblindmode_leftbutton);
        ImageButton btn_colorblindMode_right = view.findViewById(R.id.colorblindmode_rightbutton);
        ImageButton closeButton = view.findViewById(R.id.popup_close_button);

        // TEXT SIZE
        primarytitle.setAutoSizeTextTypeUniformWithConfiguration(
                12, 50, 1,
                TypedValue.COMPLEX_UNIT_SP);
        generalsettings_title.setAutoSizeTextTypeUniformWithConfiguration(
                12, 50, 1,
                TypedValue.COMPLEX_UNIT_SP);
        othersettings_title.setAutoSizeTextTypeUniformWithConfiguration(
                12, 50, 1,
                TypedValue.COMPLEX_UNIT_SP);

        switch_screenIsAlwaysOn_title.setAutoSizeTextTypeUniformWithConfiguration(
                12, 50, 1,
                TypedValue.COMPLEX_UNIT_SP);
        switch_network_title.setAutoSizeTextTypeUniformWithConfiguration(
                12, 50, 1,
                TypedValue.COMPLEX_UNIT_SP);

        text_colorblindmode_title.setAutoSizeTextTypeUniformWithConfiguration(
                12, 50, 1,
                TypedValue.COMPLEX_UNIT_SP);
        text_colorblindmode_selectedname.setAutoSizeTextTypeUniformWithConfiguration(
                12, 50, 1,
                TypedValue.COMPLEX_UNIT_SP);
        text_colorblindmode_sidenote.setAutoSizeTextTypeUniformWithConfiguration(
                12, 50, 1,
                TypedValue.COMPLEX_UNIT_SP);

        switch_huntwarningaudio_text.setAutoSizeTextTypeUniformWithConfiguration(
                12, 50, 1,
                TypedValue.COMPLEX_UNIT_SP);

        switch_huntwarning_title.setAutoSizeTextTypeUniformWithConfiguration(
                12, 50, 1,
                TypedValue.COMPLEX_UNIT_SP);
        switch_huntwarning_timetext.setAutoSizeTextTypeUniformWithConfiguration(
                12, 50, 1,
                TypedValue.COMPLEX_UNIT_SP);
        switch_huntwarning_othertext.setAutoSizeTextTypeUniformWithConfiguration(
                12, 50, 1,
                TypedValue.COMPLEX_UNIT_SP);

        // COLORBLIND DATA
        TypedArray typedArray =
                getResources().obtainTypedArray(R.array.settings_colorblindnessmode_array);
        String[] colorspaceNames = new String[typedArray.length()];
        for (int i = 0; i < colorspaceNames.length; i++)
            colorspaceNames[i] = typedArray.getString(i);
        typedArray.recycle();
        ColorThemesData colorSpaceData = new ColorThemesData(colorspaceNames);
        int oldIndex = 0;
        if (globalPreferencesViewModel != null)
            oldIndex = globalPreferencesViewModel.getColorSpace();
        colorSpaceData.setIndex(oldIndex);
        text_colorblindmode_selectedname.setText(colorSpaceData.getColorSpaceName());

        // LISTENERS
        btn_colorblindMode_left.setOnClickListener(v -> {
            colorSpaceData.iterate(-1);
            text_colorblindmode_selectedname.setText(colorSpaceData.getColorSpaceName());
            if (globalPreferencesViewModel != null)
                globalPreferencesViewModel.setColorSpace(colorSpaceData.getIndex());

            //TODO: call globalPreferencesViewModel function instead
            SharedPreferences sharedPref =
                    requireActivity().getSharedPreferences(
                            getString(R.string.preferences_globalFile_name),
                            Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(getString(R.string.preference_colorSpace), globalPreferencesViewModel.getColorSpace());
            editor.apply();
            editor.commit();
            //TODO: -----

            if (getActivity() != null)
                ((TitleScreenActivity) getActivity()).changeTheme(
                        globalPreferencesViewModel.getColorSpace());
        });

        btn_colorblindMode_right.setOnClickListener(v -> {
            colorSpaceData.iterate(1);
            text_colorblindmode_selectedname.setText(colorSpaceData.getColorSpaceName());
            if (globalPreferencesViewModel != null)
                globalPreferencesViewModel.setColorSpace(colorSpaceData.getIndex());

            //TODO: call globalPreferencesViewModel function instead
            SharedPreferences sharedPref =
                    requireActivity().getSharedPreferences(
                            getString(R.string.preferences_globalFile_name), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(getString(R.string.preference_colorSpace), globalPreferencesViewModel.getColorSpace());
            editor.apply();
            editor.commit();
            //TODO: ------

            if (getActivity() != null)
                ((TitleScreenActivity) getActivity()).
                        changeTheme(globalPreferencesViewModel.getColorSpace());
        });


        // SWITCHES
        if (globalPreferencesViewModel != null) {
            // Screen Always On
            if (switch_isAlwaysOn_switch != null) {
                switch_isAlwaysOn_switch.setChecked(globalPreferencesViewModel.getIsAlwaysOn());
                switch_isAlwaysOn_switch.setOnClickListener(v -> {
                    if (globalPreferencesViewModel != null)
                        globalPreferencesViewModel.setIsAlwaysOn(
                                switch_isAlwaysOn_switch.isChecked());
                    if (getView() != null)
                        getView().setKeepScreenOn(true);
                });
            }
            // Allow Mobile Data
            if (switch_network_switch != null) {
                switch_network_switch.setChecked(
                        globalPreferencesViewModel.getNetworkPreference());
                switch_network_switch.setOnClickListener(v -> {
                    if (globalPreferencesViewModel != null)
                        globalPreferencesViewModel.setNetworkPreference(
                                switch_network_switch.isChecked());
                });
            }
            // Allow Hunt Warning Audio
            if (switch_huntwarningaudio_switch != null) {
                switch_huntwarningaudio_switch.setChecked(
                        globalPreferencesViewModel.getIsHuntAudioAllowed());
                switch_huntwarningaudio_switch.setOnClickListener(v -> {
                    if (globalPreferencesViewModel != null)
                        globalPreferencesViewModel.setAllowHuntWarningAudio(
                                switch_huntwarningaudio_switch.isChecked());
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
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {

                            globalPreferencesViewModel.setHuntWarningFlashTimeout(progress);

                            double progressMax = 300000 /
                                    (double) seekBar_huntwarningTimeout.getMax();

                            if (progress < seekBar_huntwarningTimeout.getMax()) {
                                long breakdown = (long) (progressMax * progress / 1000L);
                                long minutes = breakdown / 60L;
                                long seconds = breakdown % 60L;
                                String text = String.format(
                                        "%sm %ss",
                                        new DecimalFormat("0").format(minutes),
                                        new DecimalFormat("00").format(seconds));

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
                    long minutes = breakdown / 60L;
                    long seconds = breakdown % 60L;
                    String text = String.format(
                            "%s:%s",
                            new DecimalFormat("0").format(minutes),
                            new DecimalFormat("00").format(seconds));

                    switch_huntwarning_othertext.setText("");
                    switch_huntwarning_timetext.setText(text);
                } else {
                    switch_huntwarning_othertext.setText(R.string.settings_huntwarningflashtimeout_never);
                    switch_huntwarning_timetext.setText("");
                }
            }
        }

        // CLOSE BUTTON
        int finalOldIndex = oldIndex;
        if (closeButton != null)
            closeButton.setOnClickListener(v -> {
                Navigation.findNavController(v).popBackStack();
            });
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

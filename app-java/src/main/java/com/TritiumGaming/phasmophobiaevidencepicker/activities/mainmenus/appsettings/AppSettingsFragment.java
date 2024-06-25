package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.appsettings;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.MainMenuFirebaseFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.pet.PETActivity;
import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.subsets.ColorThemeControl;
import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.subsets.FontThemeControl;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.settings.ThemeModel;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.transactions.types.FirestoreUnlockHistory;
import com.TritiumGaming.phasmophobiaevidencepicker.utils.FormatterUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.utils.GoogleMobileAdsConsentManager;
import com.TritiumGaming.phasmophobiaevidencepicker.views.global.NavHeaderLayout;
import com.TritiumGaming.phasmophobiaevidencepicker.views.global.PETImageButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;

public class AppSettingsFragment extends MainMenuFirebaseFragment {

    private GoogleMobileAdsConsentManager googleMobileAdsConsentManager;

    private boolean showEmail = false, loadThemes = true;

    private SettingsToggleItemView toggle_isAlwaysOn, toggle_network,
            toggle_huntwarningaudio, toggle_reorderGhostViews, toggle_leftHandMode;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        super.init();

        return inflater.inflate(R.layout.fragment_settings, container, false);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        final NavHeaderLayout navHeaderLayout = view.findViewById(R.id.navHeaderLayout);
        final View listener_cancelClose = navHeaderLayout.findViewById(R.id.button_left);
        final View listener_confirmClose = navHeaderLayout.findViewById(R.id.button_right);

        final AppCompatButton btn_account_login =
                view.findViewById(R.id.settings_account_login_button);
        /*final AppCompatButton btn_account_logout =
                view.findViewById(R.id.settings_account_logout_button);
        final PETImageButton btn_account_delete =
                view.findViewById(R.id.settings_account_delete_button);*/
        final ConstraintLayout btn_account_infoContainer =
                view.findViewById(R.id.constraintLayout_accountInformation);
        final AppCompatTextView btn_account_info =
                view.findViewById(R.id.settings_accountsettings_info);

        final AppCompatTextView switch_huntwarning_timetext =
                view.findViewById(R.id.seekbar_huntwarningtimeout_timetext);
        final AppCompatTextView switch_huntwarning_othertext =
                view.findViewById(R.id.seekbar_huntwarningtimeout_othertext);

        toggle_isAlwaysOn = view.findViewById(R.id.toggle_alwaysOn);
        toggle_network = view.findViewById(R.id.toggle_network);
        toggle_leftHandMode = view.findViewById(R.id.toggle_leftHandMode);
        toggle_reorderGhostViews = view.findViewById(R.id.toggle_reorderGhostViews);
        toggle_huntwarningaudio = view.findViewById(R.id.toggle_huntwarningaudio);

        SeekBar seekBar_huntwarningTimeout =
                view.findViewById(R.id.settings_huntwarning_seekbar);

        final AppCompatTextView text_colorTheme_selectedname =
                view.findViewById(R.id.colorblindmode_selectedname);
        final AppCompatTextView text_fontStyle_selectedname =
                view.findViewById(R.id.font_selectedname);

        final PETImageButton btn_colorTheme_left =
                view.findViewById(R.id.colorblindmode_leftbutton);
        final PETImageButton btn_colorTheme_right =
                view.findViewById(R.id.colorblindmode_rightbutton);

        final PETImageButton btn_fontStyle_left =
                view.findViewById(R.id.font_leftbutton);
        final PETImageButton btn_fontStyle_right =
                view.findViewById(R.id.font_rightbutton);

        try {
            googleMobileAdsConsentManager = new GoogleMobileAdsConsentManager(requireActivity());
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        if(btn_account_login != null) {
            btn_account_login.setOnClickListener(v -> {
                manualSignInAccount();

                view.invalidate();
            });
        }
        /*if(btn_account_logout != null) {
            btn_account_logout.setOnClickListener(v -> {
                signOutAccount();

                view.invalidate();
            });
        }
        if(btn_account_delete != null) {
            btn_account_delete.setOnClickListener(v -> {
                deleteAccount();

                view.invalidate();
            });
        }*/

        /*
        initAccountView(
                btn_account_login, btn_account_logout, btn_account_delete,
                btn_account_infoContainer, btn_account_info);
        */

        // SWITCHES
        // Screen Always On
        if(getGlobalPreferencesViewModel() != null) {
            // Always On Mode
            if(toggle_isAlwaysOn != null) {
                toggle_isAlwaysOn.setChecked(
                        getGlobalPreferencesViewModel().isAlwaysOn());
            }
            // Allow Mobile Data
            if(toggle_network != null) {
                toggle_network.setChecked(
                        getGlobalPreferencesViewModel().getNetworkPreference());
            }
            // Allow Left Hand Mode
            if(toggle_leftHandMode != null) {
                toggle_leftHandMode.setChecked(
                        getGlobalPreferencesViewModel().isLeftHandSupportEnabled());
            }
            // Allow Hunt Warning Audio
            if(toggle_huntwarningaudio != null) {
                toggle_huntwarningaudio.setChecked(
                        getGlobalPreferencesViewModel().isHuntWarningAudioAllowed());
            }
            // Allow Reorder Ghost Views
            if(toggle_reorderGhostViews != null) {
                toggle_reorderGhostViews.setChecked(
                        getGlobalPreferencesViewModel().getReorderGhostViews());
            }

            // COLORBLIND DATA
            if(text_colorTheme_selectedname != null) {
                ColorThemeControl colorThemesData =
                        getGlobalPreferencesViewModel().getColorThemeControl();
                try {
                    text_colorTheme_selectedname.setText(colorThemesData.getCurrentName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // FONT-STYLE DATA
            if(text_fontStyle_selectedname != null) {
                FontThemeControl fontThemesData =
                        getGlobalPreferencesViewModel().getFontThemeControl();
                try {
                    text_fontStyle_selectedname.setText(fontThemesData.getCurrentName());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // Hunt warning timeout setting
            if (seekBar_huntwarningTimeout != null) {
                seekBar_huntwarningTimeout.setMax(300001);
                if (getGlobalPreferencesViewModel().getHuntWarningFlashTimeout() < 0) {
                    seekBar_huntwarningTimeout.setProgress(seekBar_huntwarningTimeout.getMax());
                } else {
                    seekBar_huntwarningTimeout.setProgress(
                            getGlobalPreferencesViewModel().getHuntWarningFlashTimeout());
                }
                seekBar_huntwarningTimeout.setOnSeekBarChangeListener(
                        new SeekBar.OnSeekBarChangeListener() {
                            @Override
                            public void onProgressChanged(
                                    SeekBar seekBar, int progress, boolean fromUser) {

                                if (fromUser) {

                                    getGlobalPreferencesViewModel().setHuntWarningFlashTimeout(progress);

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

        // Screen Always On
        if(toggle_isAlwaysOn != null) {
            toggle_isAlwaysOn.setSwitchClickListener(v -> {
                if (getGlobalPreferencesViewModel() != null) {
                    getGlobalPreferencesViewModel().setAlwaysOn(toggle_isAlwaysOn.isChecked());
                }
            });
        }
        // Allow Mobile Data
        if(toggle_network != null) {
            toggle_network.setSwitchClickListener(v -> {
                if (getGlobalPreferencesViewModel() != null) {
                    getGlobalPreferencesViewModel().setNetworkPreference(toggle_network.isChecked());
                }
            });
        }
        // Allow Left Hand Mode
        if(toggle_leftHandMode != null) {
            toggle_leftHandMode.setSwitchClickListener(v -> {
                if (getGlobalPreferencesViewModel() != null) {
                    getGlobalPreferencesViewModel().setLeftHandSupportEnabled(
                            toggle_leftHandMode.isChecked());
                }
            });
        }
        // Allow Hunt Warning Audio
        if(toggle_huntwarningaudio != null) {
            toggle_huntwarningaudio.setSwitchClickListener(v -> {
                if (getGlobalPreferencesViewModel() != null) {
                    getGlobalPreferencesViewModel().setHuntWarningAudioAllowed(
                            toggle_huntwarningaudio.isChecked());
                }
            });
        }
        // Allow Ghost View Reordering
        if(toggle_reorderGhostViews != null) {
            toggle_reorderGhostViews.setSwitchClickListener(v -> {
                if (getGlobalPreferencesViewModel() != null) {
                    getGlobalPreferencesViewModel().setReorderGhostViews(
                            toggle_reorderGhostViews.isChecked());
                }
            });
        }

        // COLORBLIND LISTENERS
        btn_colorTheme_left.setOnClickListener(v -> {
            ColorThemeControl themeControl = getGlobalPreferencesViewModel().getColorThemeControl();

            themeControl.iterateSelectedIndex(-1);
            if(text_colorTheme_selectedname != null) {
                try {
                    text_colorTheme_selectedname.setText(getString(themeControl.getCurrentName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            demoColorStyle(themeControl);
        });

        btn_colorTheme_right.setOnClickListener(v -> {
            ColorThemeControl themeControl = getGlobalPreferencesViewModel().getColorThemeControl();

            themeControl.iterateSelectedIndex(1);
            if(text_colorTheme_selectedname != null) {
                try {
                    text_colorTheme_selectedname.setText(getString(themeControl.getCurrentName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            demoColorStyle(themeControl);
        });

        // FONT-STYLE LISTENERS
        btn_fontStyle_left.setOnClickListener(v -> {
            FontThemeControl themeControl = getGlobalPreferencesViewModel().getFontThemeControl();

            themeControl.iterateSelectedIndex(-1);
            if(text_fontStyle_selectedname != null) {
                try {
                    text_fontStyle_selectedname.setText(getString(themeControl.getCurrentName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            demoFontStyle(themeControl);
            //demoStyles();
        });

        btn_fontStyle_right.setOnClickListener(v -> {
            FontThemeControl themeControl = getGlobalPreferencesViewModel().getFontThemeControl();

            themeControl.iterateSelectedIndex(1);
            if(text_fontStyle_selectedname != null) {
                try {
                    text_fontStyle_selectedname.setText(getString(themeControl.getCurrentName()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            demoFontStyle(themeControl);
            //demoStyles();
        });

        // CANCEL BUTTON
        listener_cancelClose.setOnClickListener(v -> {
            revertDemoChanges();

            try {
                Navigation.findNavController(v).popBackStack();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        });

        // CONFIRM BUTTON
        listener_confirmClose.setOnClickListener(v -> {

            Bundle params = new Bundle();
            params.putString("event_type", "confirm_settings");
            HashMap<String, String> settings = getGlobalPreferencesViewModel().getDataAsList();
            for (String key : settings.keySet()) {
                String value = settings.get(key);
                params.putString(key, value);
            }
            getAnalytics().logEvent("event_settings", params);

            saveStates();

            try {
                Navigation.findNavController(v).popBackStack();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        });

        // Include a privacy setting if applicable
        if (googleMobileAdsConsentManager.isPrivacyOptionsRequired()) {
            ConstraintLayout adsLayout = view.findViewById(R.id.constraintLayout_ads);
            adsLayout.setVisibility(View.VISIBLE);

            AppCompatButton adsButton = view.findViewById(R.id.settings_ads_label);
            adsButton.setOnClickListener(v -> showAdsConsentForm(v.getContext()));
        }

        if(loadThemes) {
            getUserPurchaseHistory();
            //getMarketplaceColorThemes();
            loadThemes = false;
        }
/*

        if(btn_account_delete != null) {
            btn_account_delete.setVisibility(View.GONE);
        }
*/

    }

    /*
    private void initAccountView(AppCompatButton btn_account_login, AppCompatButton btn_account_logout, AppCompatButton btn_account_delete, ConstraintLayout btn_account_infoContainer, AppCompatTextView btn_account_info) {
        String accountEmail = "";
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null) {
            accountEmail = firebaseUser.getEmail();
        }
        SpannableString email_displayed =
                new SpannableString(accountEmail);

        TypedValue typedValue = new TypedValue();
        @ColorInt int obfuscationColor = getResources().getColor(R.color.white);
        try {
            Resources.Theme theme = requireContext().getTheme();
            theme.resolveAttribute(R.attr.textColorBodyEmphasis, typedValue, true);
            obfuscationColor = typedValue.data;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        SpannableString email_obfuscated = email_displayed;
        if(accountEmail != null) {
            email_obfuscated = FormatterUtils.obfuscateEmailSpannable(
                    accountEmail, obfuscationColor);
        }
        SpannableString finalEmail_obfuscated = email_obfuscated;

        btn_account_infoContainer.setOnClickListener(v -> {
            showEmail = !showEmail;

            if (showEmail) {
                btn_account_info.setText(email_displayed);
            } else {
                btn_account_info.setText(finalEmail_obfuscated);
            }
        });

        if(firebaseUser == null) {
            btn_account_login.setVisibility(View.VISIBLE);
            btn_account_logout.setVisibility(View.GONE);
            btn_account_infoContainer.setVisibility(View.GONE);
            btn_account_delete.setVisibility(View.GONE);
        } else {
            btn_account_login.setVisibility(View.GONE);
            btn_account_logout.setVisibility(View.VISIBLE);
            btn_account_infoContainer.setVisibility(View.VISIBLE);
            btn_account_delete.setVisibility(View.VISIBLE);
            btn_account_info.setText(email_displayed);

            if(!showEmail) {
                btn_account_info.setText(email_obfuscated);
            }
        }
    }
    */

    @Override
    protected void initViewModels() {
        super.initViewModels();
    }

    @Override
    protected void backPressedHandler() {
        revertDemoChanges();

        try {
            Navigation.findNavController(requireView()).popBackStack();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        try {
            String message = getString(R.string.toast_discardchanges);
            Toast toast = Toast.makeText(requireActivity(),
                    message,
                    com.google.android.material.R.integer.material_motion_duration_short_2);
            toast.show();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }

    private void getUserPurchaseHistory() {
        CollectionReference unlockHistoryCollection = null;
        try {
            unlockHistoryCollection =
                    FirestoreUnlockHistory.Companion.getUnlockHistoryCollection();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(unlockHistoryCollection == null) { return; }

        try {
            unlockHistoryCollection.get()
                    .addOnSuccessListener(task -> {
                        for(DocumentSnapshot documentSnapshot : task.getDocuments()) {
                            DocumentReference documentReference = documentSnapshot.getReference();

                            String uuid = documentReference.getId();
                            ThemeModel customTheme = getGlobalPreferencesViewModel().getColorThemeControl()
                                    .getThemeByUUID(uuid);

                            customTheme.setUnlocked(ThemeModel.Availability.UNLOCKED_PURCHASE);
                        }
                    })
                    .addOnFailureListener(e -> {
                        Log.e("Firestore", "Could not retrieve unlock history!");
                        e.printStackTrace();
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    private void demoStyles() {
        PETActivity activity = ((PETActivity)getActivity());
        if(activity != null) {
            activity.changeTheme(
                    globalPreferencesViewModel.getColorTheme(),
                    globalPreferencesViewModel.getFontTheme()
            );
        }
        refreshFragment();
    }
    */

    private void revertDemoChanges() {
        getGlobalPreferencesViewModel().getColorThemeControl().revertToSavedIndex();
        getGlobalPreferencesViewModel().getFontThemeControl().revertToSavedIndex();

        try {
            ((PETActivity) requireActivity()).changeTheme(
                    getGlobalPreferencesViewModel().getColorTheme(),
                    getGlobalPreferencesViewModel().getFontTheme());
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void demoColorStyle(@NonNull ColorThemeControl colorThemeControl) {
        try {
            ((PETActivity) requireActivity()).changeTheme(
                    colorThemeControl.getThemeAtIndex(colorThemeControl.getSelectedIndex()),
                    getGlobalPreferencesViewModel().getFontTheme());
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        refreshFragment();
    }

    private void demoFontStyle(@NonNull FontThemeControl fontThemeControl) {
        try {
            ((PETActivity) requireActivity()).changeTheme(
                    getGlobalPreferencesViewModel().getColorTheme(),
                    fontThemeControl.getThemeAtIndex(fontThemeControl.getSelectedIndex()));
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        refreshFragment();
    }

    public void showAdsConsentForm(Context context) {

        try {
            // Handle changes to user consent.
            googleMobileAdsConsentManager.showPrivacyOptionsForm(
                    requireActivity(),
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
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    /**
     * saveStates method
     * <p>
     * TODO
     */
    public void saveStates() {

        if (getGlobalPreferencesViewModel() != null) {
            getGlobalPreferencesViewModel().getFontThemeControl().saveSelectedIndex();
            getGlobalPreferencesViewModel().getColorThemeControl().saveSelectedIndex();

            try {
                getGlobalPreferencesViewModel().saveToFile(requireContext());
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }

            try {
                PETActivity activity = ((PETActivity) requireActivity());
                activity.changeTheme(
                        getGlobalPreferencesViewModel().getColorTheme(),
                        getGlobalPreferencesViewModel().getFontTheme());
                if (getGlobalPreferencesViewModel().isAlwaysOn()) {
                    activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                }
                activity.recreate();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } else {
            Log.e("Saver", "GPVM is null!");
        }

    }

    @Override
    protected void onSignInAccountSuccess() {

        refreshFragment();

        // Generate a Firestore document for the User with default data if needed
        try {
            FirestoreUser.Companion.buildUserDocument();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        getUserPurchaseHistory();
    }

    @Override
    protected void onSignOutAccountSuccess() {
        ColorThemeControl themeControl = getGlobalPreferencesViewModel().getColorThemeControl();

        themeControl.revertAllUnlockedStatuses();

        themeControl.iterateSelectedIndex(0);
        themeControl.setSelectedIndex(0);
        themeControl.saveIndex(0);

        getGlobalPreferencesViewModel().saveColorSpace(requireContext());

        demoColorStyle(themeControl);
    }
}

package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.appsettings;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.PETActivity;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.PETFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.MainMenusFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming.CustomTheme;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.NetworkUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser;
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming.subsets.ColorThemeControl;
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming.subsets.FontThemeControl;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.FormatterUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.GoogleMobileAdsConsentManager;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.transaction.FirestoreUnlockHistory;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.FirebaseUiException;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.HashMap;
import java.util.List;

public class AppSettingsFragment extends MainMenusFragment {

    private GoogleMobileAdsConsentManager googleMobileAdsConsentManager;

    private boolean showEmail = false, loadThemes = true;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        super.init();

        return inflater.inflate(R.layout.fragment_appsettings, container, false);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        final AppCompatTextView text_colorTheme_selectedname =
                view.findViewById(R.id.colorblindmode_selectedname);
        final AppCompatTextView text_fontStyle_selectedname = view.findViewById(R.id.font_selectedname);

        final AppCompatTextView switch_huntwarning_timetext =
                view.findViewById(R.id.seekbar_huntwarningtimeout_timetext);
        final AppCompatTextView switch_huntwarning_othertext =
                view.findViewById(R.id.seekbar_huntwarningtimeout_othertext);

        final SettingsToggleItem toggle_isAlwaysOn = view.findViewById(R.id.toggle_alwaysOn);
        final SettingsToggleItem toggle_network = view.findViewById(R.id.toggle_network);
        final SettingsToggleItem toggle_huntwarningaudio =
                view.findViewById(R.id.toggle_huntwarningaudio);
        final SettingsToggleItem toggle_leftHandMode =
                view.findViewById(R.id.toggle_leftHandMode);

        final SeekBar seekBar_huntwarningTimeout = view.findViewById(R.id.settings_huntwarning_seekbar);

        final AppCompatImageView btn_colorblindMode_left = view.findViewById(R.id.colorblindmode_leftbutton);
        final AppCompatImageView btn_colorblindMode_right = view.findViewById(R.id.colorblindmode_rightbutton);
        final AppCompatImageView btn_fontStyle_left = view.findViewById(R.id.font_leftbutton);
        final AppCompatImageView btn_fontStyle_right = view.findViewById(R.id.font_rightbutton);
        final View listener_confirmClose = view.findViewById(R.id.listener_confirm);
        final View listener_cancelClose = view.findViewById(R.id.listener_cancel);

        final AppCompatButton btn_account_login = view.findViewById(R.id.settings_account_login_button);
        final AppCompatButton btn_account_logout = view.findViewById(R.id.settings_account_logout_button);
        final AppCompatButton btn_account_delete = view.findViewById(R.id.settings_account_delete_button);
        final ConstraintLayout btn_account_infoContainer = view.findViewById(R.id.constraintLayout_accountInformation);
        final AppCompatTextView btn_account_info = view.findViewById(R.id.settings_accountsettings_info);

        if(getActivity() != null) {
            googleMobileAdsConsentManager = new GoogleMobileAdsConsentManager(getActivity());
        }

        btn_account_login.setOnClickListener(v -> {
            manualSignInAccount();

            view.invalidate();
        });
        btn_account_logout.setOnClickListener(v -> {
            signOutAccount();

            view.invalidate();
        });
        btn_account_delete.setOnClickListener(v -> {
            deleteAccount();

            view.invalidate();
        });

        final String accountEmail;
        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser != null) {
            accountEmail = firebaseUser.getEmail();
        } else {
            accountEmail = "";
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

            if(showEmail) {
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

        /*
         * Setting Colorblindness Theme
         */
        // COLORBLIND DATA
        ColorThemeControl colorThemesData = globalPreferencesViewModel.getColorThemeControl();
        try {
            text_colorTheme_selectedname.setText(colorThemesData.getCurrentName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // COLORBLIND LISTENERS
        btn_colorblindMode_left.setOnClickListener(v -> {
            ColorThemeControl themeControl = globalPreferencesViewModel.getColorThemeControl();

            themeControl.iterateSelection(-1);
            try {
                text_colorTheme_selectedname.setText(getString(themeControl.getCurrentName()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            demoColorStyle(themeControl);
        });

        btn_colorblindMode_right.setOnClickListener(v -> {
            ColorThemeControl themeControl = globalPreferencesViewModel.getColorThemeControl();

            themeControl.iterateSelection(1);
            try {
                text_colorTheme_selectedname.setText(getString(themeControl.getCurrentName()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            demoColorStyle(themeControl);
        });

        /*
         * Setting Fonts
         */
        // FONT-STYLE DATA
        FontThemeControl fontThemesData = globalPreferencesViewModel.getFontThemeControl();
        try {
            text_fontStyle_selectedname.setText(fontThemesData.getCurrentName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // FONT-STYLE LISTENERS
        btn_fontStyle_left.setOnClickListener(v -> {
            FontThemeControl themeControl = globalPreferencesViewModel.getFontThemeControl();

            themeControl.iterateSelection(-1);
            try {
                text_fontStyle_selectedname.setText(getString(themeControl.getCurrentName()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            demoFontStyle(themeControl);
            //demoStyles();
        });

        btn_fontStyle_right.setOnClickListener(v -> {
            FontThemeControl themeControl = globalPreferencesViewModel.getFontThemeControl();

            themeControl.iterateSelection(1);
            try {
                text_fontStyle_selectedname.setText(getString(themeControl.getCurrentName()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            demoFontStyle(themeControl);
            //demoStyles();
        });

        // SWITCHES
        if (globalPreferencesViewModel != null) {
            // Screen Always On
            if (toggle_isAlwaysOn != null) {
                toggle_isAlwaysOn.setChecked(globalPreferencesViewModel.getIsAlwaysOn());
                View.OnClickListener listener = (v -> {
                    if (globalPreferencesViewModel != null) {
                        globalPreferencesViewModel.setIsAlwaysOn(
                                toggle_isAlwaysOn.isChecked());
                    }
                });
                toggle_isAlwaysOn.setSwitchClickListener(listener);
            }
            // Allow Mobile Data
            if (toggle_network != null) {
                toggle_network.setChecked(globalPreferencesViewModel.getNetworkPreference());
                View.OnClickListener listener = (v -> {
                    if (globalPreferencesViewModel != null) {
                        globalPreferencesViewModel.setNetworkPreference(
                                toggle_network.isChecked());
                    }
                });
                toggle_network.setSwitchClickListener(listener);
            }
            // Allow Hunt Warning Audio
            if (toggle_huntwarningaudio != null) {
                toggle_huntwarningaudio.setChecked(globalPreferencesViewModel.getIsHuntAudioAllowed());
                View.OnClickListener listener = (v -> {
                    if (globalPreferencesViewModel != null) {
                        globalPreferencesViewModel.setHuntWarningAudioAllowed(
                                toggle_huntwarningaudio.isChecked());
                    }
                });
                toggle_huntwarningaudio.setSwitchClickListener(listener);
            }

            // Allow Hunt Warning Audio
            if (toggle_leftHandMode != null) {
                toggle_leftHandMode.setChecked(globalPreferencesViewModel.getIsLeftHandSupportEnabled());
                View.OnClickListener listener = (v -> {
                    if (globalPreferencesViewModel != null) {
                        globalPreferencesViewModel.setLeftHandSupportEnabled(
                                toggle_leftHandMode.isChecked());
                    }
                });
                toggle_leftHandMode.setSwitchClickListener(listener);
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
            HashMap<String, String> settings = globalPreferencesViewModel.getDataAsList();
            for (String key : settings.keySet()) {
                String value = settings.get(key);
                params.putString(key, value);
            }
            analytics.logEvent("event_settings", params);

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

        btn_account_delete.setVisibility(View.GONE);
    }

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
                    FirestoreUnlockHistory.getUnlockHistoryCollection();
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
                            CustomTheme customTheme = globalPreferencesViewModel.getColorThemeControl()
                                    .getThemeByUUID(uuid);

                            customTheme.setUnlocked(CustomTheme.Availability.UNLOCKED_PURCHASE);
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

    private void revertDemoChanges() {
        globalPreferencesViewModel.getColorThemeControl().revertSelection();
        globalPreferencesViewModel.getFontThemeControl().revertSelection();

        try {
            ((PETActivity) requireActivity()).changeTheme(
                    globalPreferencesViewModel.getColorTheme(),
                    globalPreferencesViewModel.getFontTheme());
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    private void demoFontStyle(FontThemeControl fontThemeControl) {
        try {
            ((PETActivity) requireActivity()).changeTheme(
                    globalPreferencesViewModel.getColorTheme(),
                    fontThemeControl.getThemeAtIndex(fontThemeControl.getSelectedIndex()));
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
        refreshFragment();
    }

    private void demoColorStyle(ColorThemeControl colorThemeControl) {
        try {
            ((PETActivity) requireActivity()).changeTheme(
                    colorThemeControl.getThemeAtIndex(colorThemeControl.getSelectedIndex()),
                    globalPreferencesViewModel.getFontTheme());
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

        if (globalPreferencesViewModel != null && getContext() != null) {
            globalPreferencesViewModel.getFontThemeControl().setSavedIndex();
            globalPreferencesViewModel.getColorThemeControl().setSavedIndex();

            try {
                globalPreferencesViewModel.saveToFile(requireContext());
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }

        try {
            PETActivity activity = ((PETActivity) requireActivity());
            activity.changeTheme(
                    globalPreferencesViewModel.getColorTheme(),
                    globalPreferencesViewModel.getFontTheme());
            if (globalPreferencesViewModel.getIsAlwaysOn()) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
            activity.recreate();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }

    private final ActivityResultLauncher<Intent> signInLauncher =
            registerForActivityResult(
                new FirebaseAuthUIActivityResultContract(),
                result -> {
                    try {
                        onSignInResultAccount(result);
                    } catch (RuntimeException runtimeException) {
                        String message = "Login Error: " + runtimeException.getMessage();
                        try {
                            Toast.makeText(requireActivity(),
                                    message,
                                    com.google.android.material.R.integer.material_motion_duration_short_2).show();
                        } catch (IllegalStateException e) {
                            e.printStackTrace();
                        }
                    }
                }
    );

    /**
     *
     */
    public void manualSignInAccount() {
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            Log.d("ManuLogin", "User not null!");
            return;
        }
        Log.d("ManuLogin", "Continuing to sign-in.");

        try {
            if(!NetworkUtils.isNetworkAvailable(requireContext(),
                    globalPreferencesViewModel.getNetworkPreference())) {
                Toast.makeText(requireActivity(),
                                "Internet not available.",
                                Toast.LENGTH_SHORT)
                        .show();

                return;
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }


        List<AuthUI.IdpConfig> providers = List.of(
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false)
                .setTosAndPrivacyPolicyUrls(
                        getString(R.string.preference_termsofservice_link),
                        getString(R.string.preference_privacypolicy_link)
                )
                .build();

        signInLauncher.launch(signInIntent);

    }

    /**
     *
     */
    private void onSignInResultAccount(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            FirebaseUser user = null;
            try {
                user = FirestoreUser.getCurrentFirebaseUser();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if(user != null) {
                String message = "Welcome " + user.getDisplayName();
                Toast toast = Toast.makeText(requireActivity(),
                        message,
                        com.google.android.material.R.integer.material_motion_duration_short_2);
                toast.show();

                refreshFragment();

                // Generate a Firestore document for the User with default data if needed
                try {
                    FirestoreUser.buildUserDocument();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                getUserPurchaseHistory();
            }
        } else {

            String message = "ERROR: (Error data could not be acquired).";
            if(response != null) {
                FirebaseUiException error = response.getError();
                if(error != null) {
                    message = "ERROR " + error.getErrorCode() + ": " + error.getMessage();
                }
            }

            Toast toast = Toast.makeText(requireActivity(),
                    message,
                    com.google.android.material.R.integer.material_motion_duration_short_2);
            toast.show();
        }
    }

    /**
     *
     */
    public void signOutAccount() {
        if(FirebaseAuth.getInstance().getCurrentUser() == null) {
            return;
        }

        AuthUI.getInstance()
                .signOut(requireContext())
                .addOnCompleteListener(task -> {

                    String message = "User signed out";
                    try {
                        Toast.makeText(requireActivity(),
                                message,
                                com.google.android.material.R.integer.material_motion_duration_short_2).show();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }

                    ColorThemeControl themeControl = globalPreferencesViewModel.getColorThemeControl();

                    themeControl.revertAllUnlockedStatuses();

                    themeControl.iterateSelection(0);
                    themeControl.setSelectedIndex(0);
                    themeControl.setSavedIndex(0);

                    globalPreferencesViewModel.saveColorSpace(getContext());

                    demoColorStyle(themeControl);

                });
    }

    /**
     *
     */
    public void deleteAccount() {
        AuthUI.getInstance()
                .delete(requireContext())
                .addOnCompleteListener(task -> {
                    String message = "Successfully removed account.";
                    try {
                        Toast.makeText(requireActivity(),
                                message,
                                com.google.android.material.R.integer.material_motion_duration_short_2).show();
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }

                    refreshFragment();
                });
    }

}

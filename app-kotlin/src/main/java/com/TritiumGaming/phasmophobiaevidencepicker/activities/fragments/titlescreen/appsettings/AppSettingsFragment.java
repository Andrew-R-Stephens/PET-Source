package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.appsettings;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
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

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.ColorInt;
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
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming.CustomTheme;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser;
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming.subsets.ColorThemeControl;
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming.subsets.FontThemeControl;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.FormatterUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.GoogleMobileAdsConsentManager;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.GlobalPreferencesViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.TitlescreenViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.FirestorePurchaseHistory;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.FirebaseUiException;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class AppSettingsFragment extends Fragment {

    private FirebaseAnalytics analytics;
    private GoogleMobileAdsConsentManager googleMobileAdsConsentManager;

    private GlobalPreferencesViewModel globalPreferencesViewModel = null;
    private TitlescreenViewModel titleScreenViewModel = null;

    private boolean showEmail = false, loadThemes = true;

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

        final AppCompatTextView text_colorTheme_selectedname =
                view.findViewById(R.id.colorblindmode_selectedname);
        final AppCompatTextView text_fontStyle_selectedname = view.findViewById(R.id.font_selectedname);

        final AppCompatTextView switch_huntwarning_timetext =
                view.findViewById(R.id.seekbar_huntwarningtimeout_timetext);
        final AppCompatTextView switch_huntwarning_othertext =
                view.findViewById(R.id.seekbar_huntwarningtimeout_othertext);

        final SwitchCompat switch_isAlwaysOn_switch = view.findViewById(R.id.switch_alwayson_switch);
        final SwitchCompat switch_network_switch = view.findViewById(R.id.switch_network_switch);
        final SwitchCompat switch_huntwarningaudio_switch =
                view.findViewById(R.id.switch_huntwarningaudio_switch);
        final SwitchCompat switch_leftHandMode_switch =
                view.findViewById(R.id.switch_leftHandMode_switch);

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
        Resources.Theme theme = getContext().getTheme();
        theme.resolveAttribute(R.attr.textColorBodyEmphasis, typedValue, true);
        @ColorInt int obfuscationColor = typedValue.data;
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
        text_colorTheme_selectedname.setText(colorThemesData.getCurrentName());

        // COLORBLIND LISTENERS
        btn_colorblindMode_left.setOnClickListener(v -> {
            ColorThemeControl themeControl = globalPreferencesViewModel.getColorThemeControl();

            themeControl.iterateSelection(-1);
            text_colorTheme_selectedname.setText(getString(themeControl.getCurrentName()));

            demoColorStyle(themeControl);

            Log.d("Theme", getString(themeControl.getCurrentName()) + " " + themeControl.getCurrentTheme().getUnlockedState().name());

            //demoStyles();
        });

        btn_colorblindMode_right.setOnClickListener(v -> {
            ColorThemeControl themeControl = globalPreferencesViewModel.getColorThemeControl();

            themeControl.iterateSelection(1);
            text_colorTheme_selectedname.setText(getString(themeControl.getCurrentName()));

            demoColorStyle(themeControl);

            Log.d("Theme", getString(themeControl.getCurrentName()) + " " + themeControl.getCurrentTheme().getUnlockedState().name());

            //demoStyles();
        });

        /*
         * Setting Fonts
         */
        // FONT-STYLE DATA
        FontThemeControl fontThemesData = globalPreferencesViewModel.getFontThemeControl();
        text_fontStyle_selectedname.setText(fontThemesData.getCurrentName());

        // FONT-STYLE LISTENERS
        btn_fontStyle_left.setOnClickListener(v -> {
            FontThemeControl themeControl = globalPreferencesViewModel.getFontThemeControl();

            themeControl.iterateSelection(-1);
            text_fontStyle_selectedname.setText(getString(themeControl.getCurrentName()));

            demoFontStyle(themeControl);
            //demoStyles();
        });

        btn_fontStyle_right.setOnClickListener(v -> {
            FontThemeControl themeControl = globalPreferencesViewModel.getFontThemeControl();

            themeControl.iterateSelection(1);
            text_fontStyle_selectedname.setText(getString(themeControl.getCurrentName()));

            demoFontStyle(themeControl);
            //demoStyles();
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

        if(loadThemes) {
            getUserPurchaseHistory();
            //getMarketplaceColorThemes();
            loadThemes = false;
        }
    }

    private void getUserPurchaseHistory() {
        try {
            CollectionReference purchaseHistoryCollection =
                    FirestorePurchaseHistory.getUserPurchaseHistoryCollection();

            if (purchaseHistoryCollection == null) {
                return;
            }

            purchaseHistoryCollection.get().addOnCompleteListener(task -> {
                for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                    DocumentReference documentReference = documentSnapshot.getReference();

                    String uuid = documentReference.getId();
                    CustomTheme customTheme = globalPreferencesViewModel.getColorThemeControl()
                            .getThemeByUUID(uuid);
                    customTheme.setUnlocked(CustomTheme.Availability.UNLOCKED_PURCHASE);
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /*
    private void getMarketplaceColorThemes() {
        FirestoreMarketplace.getThemes()
            .addOnFailureListener(e -> {
                Log.d("Firestore", "Theme document query FAILED!");
                e.printStackTrace();
            }).addOnCompleteListener(task -> {
                for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {

                    if (!documentSnapshot.exists()) {
                        Log.d("Firestore", "Theme document snapshot DNE.");
                    }

                    try {
                        PETTheme tempTheme = documentSnapshot.toObject(PETTheme.class);
                        if(tempTheme != null) {
                            PETTheme petTheme = new PETTheme(
                                    documentSnapshot.getReference().getId(),
                                    tempTheme);
                            Log.d("Firestore", petTheme.toString());
                        }
                    } catch (Exception e) {
                        Log.d("Firestore", "Error CREATING PETTheme!");
                        e.printStackTrace();
                    }
                }
            });
    }
    */

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

        PETActivity activity = ((PETActivity)getActivity());
        if (activity != null) {
            activity.changeTheme(
                            globalPreferencesViewModel.getColorTheme(),
                            globalPreferencesViewModel.getFontTheme());
        }
    }

    private void demoFontStyle(FontThemeControl fontThemeControl) {
        PETActivity activity = ((PETActivity)getActivity());
        if(activity != null) {
            activity.changeTheme(
                    globalPreferencesViewModel.getColorTheme(),
                    fontThemeControl.getAppThemeAt(fontThemeControl.getSelectedIndex()));
        }
        refreshFragment();
    }

    private void demoColorStyle(ColorThemeControl colorThemeControl) {
        PETActivity activity = ((PETActivity)getActivity());
        if(activity != null) {
            activity.changeTheme(
                    colorThemeControl.getAppThemeAt(colorThemeControl.getSelectedIndex()),
                    globalPreferencesViewModel.getFontTheme());
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

        PETActivity activity = ((PETActivity) getActivity());
        if (activity != null) {
            activity.changeTheme(
                            globalPreferencesViewModel.getColorTheme(),
                            globalPreferencesViewModel.getFontTheme());
            if(globalPreferencesViewModel.getIsAlwaysOn() ) {
                activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            }
            activity.recreate();
        }
    }

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            result -> {
                try {
                    onSignInResultAccount(result);
                } catch (RuntimeException e) {
                    String message = "Login Error: " + e.getMessage();
                    Toast toast = Toast.makeText(requireActivity(),
                            message,
                            com.google.android.material.R.integer.material_motion_duration_short_2);
                    toast.show();
                }
            }
    );

    /**
     *
     */
    public void manualSignInAccount() {
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            Log.d("ManuLogin", "User null!");
            return;
        }
        Log.d("ManuLogin", "Continuing to sign-in.");

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false)
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
                    Toast toast = Toast.makeText(requireActivity(),
                            message,
                            com.google.android.material.R.integer.material_motion_duration_short_2);
                    toast.show();

                    globalPreferencesViewModel.getColorThemeControl().revertAllUnlockedStatuses();
                    globalPreferencesViewModel.getColorThemeControl().setSelectedIndex(-1);
                    globalPreferencesViewModel.getColorThemeControl().setSavedIndex(-1);

                    globalPreferencesViewModel.saveColorSpace(getContext());

                    demoColorStyle(globalPreferencesViewModel.getColorThemeControl());


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
                    Toast toast = Toast.makeText(requireActivity(),
                            message,
                            com.google.android.material.R.integer.material_motion_duration_short_2);
                    toast.show();

                    refreshFragment();
                });
    }

    @Override
    public void onResume() {

        super.onResume();
    }
}

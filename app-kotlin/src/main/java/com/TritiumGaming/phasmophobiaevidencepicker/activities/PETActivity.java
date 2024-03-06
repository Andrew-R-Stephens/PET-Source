package com.TritiumGaming.phasmophobiaevidencepicker.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.CustomTheme;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.GlobalPreferencesViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.PermissionsViewModel;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.FirebaseUiException;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * InvestigationActivity class
 *
 * @author TritiumGamingStudios
 */
public abstract class PETActivity extends AppCompatActivity {

    protected FirebaseAnalytics analytics;

    protected GlobalPreferencesViewModel globalPreferencesViewModel;
    protected PermissionsViewModel permissionsViewModel;

    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new FirebaseAuthUIActivityResultContract(),
            result -> {
                try {
                    onSignInAccountResult(result);
                } catch (RuntimeException e) {
                    String message = "Login Error: " + e.getMessage();
                    Toast toast = Toast.makeText(getApplicationContext(),
                            message,
                            com.google.android.material.R.integer.material_motion_duration_short_2);
                    toast.show();
                }
            }
    );

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        initFirebase();
        initViewModels();
        initPrefs();

        automaticSignInAccount();

    }

    protected void initFirebase() {
        analytics = FirebaseAnalytics.getInstance(this);
    }

    protected ViewModelProvider.AndroidViewModelFactory initViewModels() {
        ViewModelProvider.AndroidViewModelFactory factory =
                ViewModelProvider.AndroidViewModelFactory.getInstance(this.getApplication());

        globalPreferencesViewModel = factory.create(GlobalPreferencesViewModel.class);
        globalPreferencesViewModel.init(PETActivity.this);

        permissionsViewModel = factory.create(
                PermissionsViewModel.class);
        permissionsViewModel = new ViewModelProvider(this).get(
                PermissionsViewModel.class);

        return factory;
    }

    protected void initPrefs() {
        //set colorSpace
        changeTheme(globalPreferencesViewModel.getColorTheme(), globalPreferencesViewModel.getFontTheme());

        if (globalPreferencesViewModel.getIsAlwaysOn()) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }
    }

    /**
     * changeTheme
     * <p>
     * Sets the Skin Theme based on User Preferences.
     *
     * @param colorSpace to be set
     */
    public void changeTheme(CustomTheme colorSpace, CustomTheme fontType) {

        if(fontType != null) {
            int styleId = fontType.getStyle();
            getTheme().applyStyle(styleId, true);
        }

        if(colorSpace != null) {
            int colorSpaceId = colorSpace.getStyle();
            getTheme().applyStyle(colorSpaceId, true);
        }

    }

    /**
     * setLanguage method
     *
     * @param language The desired new language
     */
    public boolean setLanguage(String language) {
        boolean isChanged = false;

        Locale defaultLocale = Locale.getDefault();
        Locale locale = new Locale(language);
        if (!(defaultLocale.getLanguage().equalsIgnoreCase(locale.getLanguage()))) {
            isChanged = true;
        }

        Locale.setDefault(locale);
        Configuration config = getResources().getConfiguration();
        config.setLocale(locale);
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        return isChanged;
    }

    /**
     * getAppLanguage method
     *
     * @return the abbreviation of the chosen language that's saved to file
     */
    public String getAppLanguage() {
        return globalPreferencesViewModel.getLanguageName();
    }

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
    public void automaticSignInAccount() {
        if(FirebaseAuth.getInstance().getCurrentUser() != null) {
            Log.d("AutoLogin", "User not null!");
            return;
        }
        Log.d("AutoLogin", "User is null. Attempting silent log in.");

        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.GoogleBuilder().build());

        AuthUI.getInstance().silentSignIn(this, providers)
                .continueWithTask(task -> {
                    if (task.isSuccessful()) {
                        return task;
                    } else {
                        // Ignore any exceptions since we don't care about credential fetch errors.
                        return FirebaseAuth.getInstance().signInAnonymously();
                    }
                }).addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {

                    } else {
                        //TODO
                    }
                });
    }

    /**
     *
     */
    private void onSignInAccountResult(FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if(user != null) {
                String message = "Welcome " + user.getDisplayName();
                Toast toast = Toast.makeText(getApplicationContext(),
                        message,
                        com.google.android.material.R.integer.material_motion_duration_short_2);
                toast.show();
            }
        } else {
            FirebaseUiException error = response.getError();
            String message = "ERROR " + error.getErrorCode() + ": " + error.getMessage();
            Toast toast = Toast.makeText(getApplicationContext(),
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
                .signOut(this)
                .addOnCompleteListener(task -> {

                    String message = "User signed out";
                    Toast toast = Toast.makeText(getApplicationContext(),
                            message,
                            com.google.android.material.R.integer.material_motion_duration_short_2);
                    toast.show();
                });
    }

    /**
     *
     */
    public void deleteAccount() {
        AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener(task -> {
                    String message = "Successfully removed account.";
                    Toast toast = Toast.makeText(getApplicationContext(),
                            message,
                            com.google.android.material.R.integer.material_motion_duration_short_2);
                    toast.show();
                });
    }

    /**
     *
     */
    public void privacyAndTerms() {
        List<AuthUI.IdpConfig> providers = Collections.emptyList();

        Intent signInIntent = AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                /*.setTosAndPrivacyPolicyUrls(
                        "https://example.com/terms.html",
                        "https://example.com/privacy.html")*/
                .build();
        signInLauncher.launch(signInIntent);
    }


    public void transaction() {
    }

}

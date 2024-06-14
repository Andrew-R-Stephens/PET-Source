package com.TritiumGaming.phasmophobiaevidencepicker.activities.pet;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utils.NetworkUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.FirebaseUiException;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.List;

public abstract class FirebaseFragment extends PETFragment {

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

    protected FirebaseFragment() { super(); }

    protected FirebaseFragment(int layout) {
        super(layout);
    }

    protected abstract void onSignInAccountSuccess();

    protected abstract void onSignOutAccountSuccess();

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
    private void onSignInResultAccount(@NonNull FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            FirebaseUser user;
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

                onSignInAccountSuccess();

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

        try {
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

                        onSignOutAccountSuccess();

                    });
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
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

    @Override
    protected abstract void saveStates();

}

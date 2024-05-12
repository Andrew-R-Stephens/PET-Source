package com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.account;

import static android.app.Activity.RESULT_OK;

import android.annotation.SuppressLint;
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
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.mainmenus.MainMenuFirebaseFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.CustomTheme;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.ColorUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.FormatterUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.NetworkUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser;
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

import java.util.List;

public class AccountOverviewFragment extends MainMenuFirebaseFragment {

    private boolean showEmail = false;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        super.init();

        return inflater.inflate(R.layout.fragment_account_overview, container, false);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        final AppCompatButton btn_account_login =
                view.findViewById(R.id.settings_account_login_button);
        final AppCompatButton btn_account_logout =
                view.findViewById(R.id.settings_account_logout_button);
        final AppCompatButton btn_account_delete =
                view.findViewById(R.id.settings_account_delete_button);
        final ConstraintLayout btn_account_infoContainer =
                view.findViewById(R.id.constraintLayout_accountInformation);
        final AppCompatTextView btn_account_info =
                view.findViewById(R.id.settings_accountsettings_info);

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

        SpannableString finalEmail_obfuscated = null;
        try {
            @ColorInt int obfuscationColor =
                    ColorUtils.getColorFromAttribute(requireContext(), R.attr.textColorBodyEmphasis);
            SpannableString email_obfuscated = email_displayed;
            if (accountEmail != null) {
                email_obfuscated = FormatterUtils.obfuscateEmailSpannable(
                        accountEmail, obfuscationColor);
            }
            finalEmail_obfuscated = email_obfuscated;
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

        SpannableString finalEmail_obfuscated1 = finalEmail_obfuscated;
        btn_account_infoContainer.setOnClickListener(v -> {
            showEmail = !showEmail;

            if(showEmail) {
                btn_account_info.setText(email_displayed);
            } else {
                btn_account_info.setText(
                        finalEmail_obfuscated1 != null ? finalEmail_obfuscated1 : "?");
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
                btn_account_info.setText(finalEmail_obfuscated1);
            }
        }

        btn_account_delete.setVisibility(View.GONE);
    }

    @Override
    protected void initViewModels() {
        super.initViewModels();
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
                            CustomTheme customTheme =
                                    globalPreferencesViewModel
                                        .getColorThemeControl()
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

    /**
     * refreshFragment
     */
    public void refreshFragment() {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(AccountOverviewFragment.this).commitNow();
        ft = getParentFragmentManager().beginTransaction();
        ft.attach(AccountOverviewFragment.this).commitNow();
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
            Log.d("ManuLogin", "User not null!");
            return;
        }
        Log.d("ManuLogin", "Continuing to sign-in.");

        try {
            if(!NetworkUtils.isNetworkAvailable(requireContext(),
                    globalPreferencesViewModel.networkPreference)) {
                Toast.makeText(requireActivity(), "Internet not available.", Toast.LENGTH_SHORT)
                        .show();

                return;
            }
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return;
        }

        List<AuthUI.IdpConfig> providers = List.of(
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
    private void onSignInResultAccount(@NonNull FirebaseAuthUIAuthenticationResult result) {
        IdpResponse response = result.getIdpResponse();
        if (result.getResultCode() == RESULT_OK) {
            // Successfully signed in
            FirebaseUser user = FirestoreUser.getCurrentFirebaseUser();

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

    @Override
    protected void onSignInAccountSuccess() {

    }

    @Override
    protected void onSignOutAccountSuccess() {

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

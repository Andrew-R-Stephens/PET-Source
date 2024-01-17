package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.marketplace;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.PETActivity;
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming.CustomTheme;
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming.subsets.ColorThemeControl;
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming.subsets.FontThemeControl;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.FormatterUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.GoogleMobileAdsConsentManager;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.GlobalPreferencesViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.TitlescreenViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.FirestoreMarketplace;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.theme.PETTheme;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser;
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

public class MarketplaceFragment extends Fragment {

    private FirebaseAnalytics analytics;
    private GoogleMobileAdsConsentManager googleMobileAdsConsentManager;

    private GlobalPreferencesViewModel globalPreferencesViewModel = null;
    private TitlescreenViewModel titleScreenViewModel = null;

    protected ViewTreeObserver.OnScrollChangedListener viewTreeObserverListener;

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

        return inflater.inflate(R.layout.fragment_marketplace, container, false);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        final AppCompatImageView button_back = view.findViewById(R.id.button_back);

        final AppCompatButton btn_account_login = view.findViewById(R.id.settings_account_login_button);

        final ConstraintLayout constraint_requestlogin = view.findViewById(R.id.constraint_requestlogin);
        final ConstraintLayout constraint_account_name = view.findViewById(R.id.constraint_account_name);
        final CardView cardview_account = view.findViewById(R.id.cardView_account);
        final AppCompatTextView label_account_info = view.findViewById(R.id.settings_accountsettings_info);

        final ScrollView scrollview_marketplace_items = view.findViewById(R.id.scrollview_marketplace_items);

        if(getActivity() != null) {
            googleMobileAdsConsentManager = new GoogleMobileAdsConsentManager(getActivity());
        }

        btn_account_login.setOnClickListener(v -> {
            manualSignInAccount();

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
        constraint_account_name.setOnClickListener(v -> {
            showEmail = !showEmail;

            if(showEmail) {
                label_account_info.setText(email_displayed);
            } else {
                label_account_info.setText(finalEmail_obfuscated);
            }
        });

        if(firebaseUser == null) {
            constraint_requestlogin.setVisibility(View.VISIBLE);
            cardview_account.setVisibility(GONE);
        } else {
            constraint_requestlogin.setVisibility(GONE);
            cardview_account.setVisibility(View.VISIBLE);
            label_account_info.setText(email_displayed);

            if(!showEmail) {
                label_account_info.setText(email_obfuscated);
            }
        }

        // CANCEL BUTTON
        button_back.setOnClickListener(v -> {
            Navigation.findNavController(v).popBackStack();
        });

        if(getActivity() != null) {
            getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                    new OnBackPressedCallback(true) {
                        @Override
                        public void handleOnBackPressed() {
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

    /**
     * refreshFragment
     */
    public void refreshFragment() {
        FragmentTransaction ft = getParentFragmentManager().beginTransaction();
        if (Build.VERSION.SDK_INT >= 26) {
            ft.setReorderingAllowed(false);
        }
        ft.detach(MarketplaceFragment.this).commitNow();
        ft = getParentFragmentManager().beginTransaction();
        ft.attach(MarketplaceFragment.this).commitNow();
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
            FirebaseUser user = FirestoreUser.getCurrentFirebaseUser();
            if(user != null) {
                String message = "Welcome " + user.getDisplayName();
                Toast toast = Toast.makeText(requireActivity(),
                        message,
                        com.google.android.material.R.integer.material_motion_duration_short_2);
                toast.show();

                refreshFragment();

                // Generate a Firestore document for the User with default data if needed
                FirestoreUser.buildUserDocument();
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
    public void onResume() {

        super.onResume();
    }
}

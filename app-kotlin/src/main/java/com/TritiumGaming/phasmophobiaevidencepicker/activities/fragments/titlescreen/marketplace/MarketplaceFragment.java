package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.marketplace;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;

import android.animation.LayoutTransition;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.text.SpannableString;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StyleRes;
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
import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.CustomTheme;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.FormatterUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.GlobalPreferencesViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.TitlescreenViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.FirestoreMarketplace;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.theme.PETTheme;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.FirestoreAccountCredit;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.FirestorePurchaseHistory;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.FirebaseUiException;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.rewarded.ServerSideVerificationOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Arrays;
import java.util.List;

public class MarketplaceFragment extends Fragment {

    private GlobalPreferencesViewModel globalPreferencesViewModel = null;
    private TitlescreenViewModel titleScreenViewModel = null;

    private LinearLayout linearLayout_marketplace_items;

    private RewardedAd rewardedAd = null;

    private boolean showEmail = false, loadThemes = true;

    private long user_credits = 0;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

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
        final AppCompatTextView label_account_credits = view.findViewById(R.id.label_credits_actual);
        final AppCompatButton button_ad_watch = view.findViewById(R.id.button_ad_watch);

        final ScrollView scrollview_marketplace_items = view.findViewById(R.id.scrollview_marketplace_items);
        linearLayout_marketplace_items =
                (LinearLayout)scrollview_marketplace_items.getChildAt(0);

        btn_account_login.setOnClickListener(v -> {
            manualSignInAccount();

            view.invalidate();
        });

        button_ad_watch.setOnClickListener(v -> showRewardedAd());

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
            try {
                DocumentReference doc = FirestoreAccountCredit.getCreditsDocument();
                doc.get().addOnCompleteListener(task -> {
                    Object c = task.getResult().get("earnedCredits");
                    if(c == null) {
                        return;
                    }

                    user_credits = (long)c;
                    label_account_credits.setText(String.valueOf(user_credits));
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            try {
                DocumentReference creditDoc = FirestoreAccountCredit.getCreditsDocument();
                creditDoc.addSnapshotListener((documentSnapshot, error) -> {
                    if(documentSnapshot == null) { return; }

                    Object c = documentSnapshot.get("earnedCredits");
                    if(c == null) {
                        return;
                    }

                    user_credits = (long)c;
                    label_account_credits.setText(String.valueOf(user_credits));
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            populateMarketplaceUnPurchasedItems();

            loadThemes = false;
        }

        loadRewardedAd();
    }

    private void populateMarketplaceUnPurchasedItems() {
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

                populateMarketplace();
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void populateMarketplace() {

        LinearLayout prestige = new LinearLayout(getContext());
        LinearLayout event = new LinearLayout(getContext());
        LinearLayout community = new LinearLayout(getContext());

        linearLayout_marketplace_items.addView(prestige);
        linearLayout_marketplace_items.addView(event);
        linearLayout_marketplace_items.addView(community);

        populateList(prestige, "group", "Prestige", "priority", Query.Direction.ASCENDING);
        populateList(event, "group", "Event", null, null);
        populateList(community, "group", "Community", null, null);
    }

    private void populateList(LinearLayout list, String field, String value, String orderField, Query.Direction order) {
        list.setOrientation(LinearLayout.VERTICAL);
        list.setLayoutTransition(new LayoutTransition());
        list.setLayoutParams(
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        addMarketplaceThemes(list, field, value, orderField, order);
    }

    private void addMarketplaceThemes(LinearLayout list, String field, String value,
                                      String orderField, Query.Direction order) {

        Task<QuerySnapshot> query = null;
        try {
            query = FirestoreMarketplace.getThemesWhere(field, value, orderField, order);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        query.addOnCompleteListener(task -> {
            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {

                if (!documentSnapshot.exists()) {
                    Log.d("Firestore", "Theme document snapshot DNE.");
                } else {
                    try {
                        PETTheme tempTheme = documentSnapshot.toObject(PETTheme.class);
                        if (tempTheme != null) {
                            CustomTheme theme = globalPreferencesViewModel.getColorThemeControl()
                                    .getThemeByUUID(documentSnapshot.getReference().getId());
                            @StyleRes int style = theme.getStyle();

                            MarketplaceItem marketplaceItem =
                                    new MarketplaceItem(new ContextThemeWrapper(
                                            getContext(), style), null, style);
                            marketplaceItem.setPurchaseable(!theme.isUnlocked());
                            marketplaceItem.setCreditCost(tempTheme.getBuyCredits());
                            AppCompatButton buyButton = marketplaceItem.getBuyButton();
                            if(buyButton != null) {
                                buyButton.setOnClickListener(v -> {
                                    try {
                                        FirestoreAccountCredit.getCreditsDocument()
                                                .get().addOnCompleteListener(creditTask -> {
                                                    Object c = creditTask.getResult().get("earnedCredits");
                                                    if (c == null) {
                                                        return;
                                                    }
                                                    user_credits = (long) c;

                                                    if (user_credits >= marketplaceItem.getCreditCost()) {

                                                        OnSuccessListener<DocumentSnapshot> callback =
                                                                dss ->
                                                                {
                                                                    try {
                                                                        FirestoreAccountCredit.removeCredits(marketplaceItem.getCreditCost());
                                                                    } catch (Exception e) {
                                                                        throw new RuntimeException(e);
                                                                    }
                                                                };
                                                        try {
                                                            FirestorePurchaseHistory.addPurchaseDocument(callback, documentSnapshot.getReference().getId());
                                                        } catch (Exception e) {
                                                            throw new RuntimeException(e);
                                                        }

                                                        Toast.makeText(getActivity(),
                                                                "Skin purchased!",
                                                                Toast.LENGTH_SHORT).show();

                                                        list.removeView(marketplaceItem);
                                                    } else {
                                                        Toast.makeText(getActivity(),
                                                                "Not enough credits for purchase!",
                                                                Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    } catch (Exception e) {
                                        throw new RuntimeException(e);
                                    }
                                });
                            }

                            list.addView(marketplaceItem);
                        }
                    } catch (Exception e) {
                        Log.d("Firestore", "Error CREATING PETTheme!");
                        e.printStackTrace();
                    }
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

                populateMarketplaceUnPurchasedItems();

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

    public void loadRewardedAd() {

        RewardedAd.load(requireActivity(), getString(R.string.ad_rewarded_1),
                new AdRequest.Builder().build(), new RewardedAdLoadCallback() {
                    @Override
                    public void onAdLoaded(RewardedAd ad) {
                        Log.d("RewardedAd", "Ad was loaded.");
                        rewardedAd = ad;
                        ServerSideVerificationOptions options = new ServerSideVerificationOptions
                                .Builder()
                                .setCustomData("SAMPLE_CUSTOM_DATA_STRING")
                                .build();
                        rewardedAd.setServerSideVerificationOptions(options);
                    }
                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        Log.d("RewardedAd", loadAdError.toString());
                        rewardedAd = null;
                    }
                });

    }

    public void showRewardedAd() {

        if (rewardedAd != null) {
            PETActivity activityContext = (PETActivity) requireActivity();
            rewardedAd.show(activityContext, rewardItem -> {
                // Handle the reward.
                Log.d("RewardedAd", "The user earned the reward.");
                int rewardAmount = rewardItem.getAmount();

                try {
                    FirestoreAccountCredit.addCredits(rewardAmount);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                // A watched ad cannot be re-watched, so chamber another ad
                loadRewardedAd();
            });
        } else {
            Log.d("RewardedAd", "The rewarded ad wasn't ready yet.");
        }
    }

    @Override
    public void onResume() {

        super.onResume();
    }
}

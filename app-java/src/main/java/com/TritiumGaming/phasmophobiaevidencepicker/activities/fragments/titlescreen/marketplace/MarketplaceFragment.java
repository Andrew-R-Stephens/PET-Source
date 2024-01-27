package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.marketplace;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
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
import android.view.ViewPropertyAnimator;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming.CustomTheme;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.FormatterUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.NetworkUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.GlobalPreferencesViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.TitlescreenViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.listeners.OnFirestoreProcessListener;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.FirestoreMarketplace;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.bundle.MarketThemeBundle;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.theme.MarketSingleTheme;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.FirestoreAccountCredit;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.FirestorePurchaseHistory;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MarketplaceFragment extends Fragment {

    private GlobalPreferencesViewModel globalPreferencesViewModel = null;
    private TitlescreenViewModel titleScreenViewModel = null;

    private LinearLayout linearLayout_marketplace_items;
    private AppCompatTextView label_account_credits, label_marketplace_error;

    private ProgressBar market_progressbar;

    private RewardedAd rewardedAd = null;

    private boolean showEmail = false, loadThemes = true;

    private long user_credits = 0;

    private BillingClient billingClient;

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
        label_marketplace_error = view.findViewById(R.id.market_loaderror);
        label_account_credits = view.findViewById(R.id.label_credits_actual);
        final AppCompatButton button_ad_watch = view.findViewById(R.id.button_ad_watch);

        final ScrollView scrollview_marketplace_items = view.findViewById(R.id.scrollview_marketplace_items);
        linearLayout_marketplace_items =
                (LinearLayout)scrollview_marketplace_items.getChildAt(0);

        market_progressbar = view.findViewById(R.id.market_progressbar);

        btn_account_login.setOnClickListener(v -> {
            manualSignInAccount();

            view.invalidate();
        });

        button_ad_watch.setOnClickListener(v -> showRewardedAd());

        if(getActivity() != null) {
            getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                    new OnBackPressedCallback(true) {
                        @Override
                        public void handleOnBackPressed() {
                            Navigation.findNavController(view).popBackStack();
                        }
                    });
        }

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
            constraint_requestlogin.setVisibility(VISIBLE);
            cardview_account.setVisibility(GONE);

            market_progressbar.setVisibility(GONE);
        } else {
            constraint_requestlogin.setVisibility(GONE);
            cardview_account.setVisibility(VISIBLE);
            label_account_info.setText(email_displayed);

            if(!showEmail) {
                label_account_info.setText(email_obfuscated);
            }
        }


        // CANCEL BUTTON
        button_back.setOnClickListener(v -> {
            Navigation.findNavController(v).popBackStack();
        });

        if(loadThemes) {

            initAccountCreditListener();

            populateMarketplaceUnPurchasedItems();

            loadThemes = false;
        }

        if(getActivity() != null) {
            getActivity().runOnUiThread(() -> loadRewardedAd(null));
        }

        if(getActivity() != null) {
            getActivity().runOnUiThread(this::initBillingClient);
        }
    }

    private void initAccountCreditListener() {
        try {
            DocumentReference creditDoc = FirestoreAccountCredit.getCreditsDocument();
            creditDoc.get().addOnCompleteListener(task -> {
                Object c = task.getResult().get(FirestoreAccountCredit.FIELD_CREDITS_EARNED);
                if(c != null) {
                    user_credits = (long)c;
                } else {
                    user_credits = -1;
                }

                label_account_credits.setText(String.valueOf(user_credits));
            });

            creditDoc.addSnapshotListener((documentSnapshot, error) -> {
                if(documentSnapshot == null) { return; }

                Object c = documentSnapshot.get(FirestoreAccountCredit.FIELD_CREDITS_EARNED);
                if(c != null) {
                    user_credits = (long)c;
                } else {
                    user_credits = -1;
                }

                label_account_credits.setText(String.valueOf(user_credits));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateMarketplaceUnPurchasedItems() {
        try {
            CollectionReference purchaseHistoryCollection =
                    FirestorePurchaseHistory.getUserPurchaseHistoryCollection();

            purchaseHistoryCollection.get().addOnCompleteListener(task -> {

                for(DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                    DocumentReference documentReference = documentSnapshot.getReference();

                    String uuid = documentReference.getId();
                    CustomTheme customTheme = globalPreferencesViewModel.getColorThemeControl()
                            .getThemeByUUID(uuid);
                    customTheme.setUnlocked(CustomTheme.Availability.UNLOCKED_PURCHASE);
                }

                populateBundledThemes();
                populateIndividualThemes();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void populateBundledThemes() {
        MarketplaceListLayout bundles = new MarketplaceListLayout(getContext());
        bundles.setLabel("Theme Bundles");

        bundles.showLabel(GONE);

        linearLayout_marketplace_items.addView(bundles);

        OnFirestoreProcessListener processCompleteListener =
                new OnFirestoreProcessListener() {
            boolean thrown = false;
            boolean labelShown = true;

            @Override
            public void onFailure() {
                if(!thrown)
                    thrown = true;

                labelShown = false;

                market_progressbar.setVisibility(GONE);
                label_marketplace_error.setVisibility(VISIBLE);

                Toast.makeText(getActivity(),
                                "Could not access the marketplace.",
                                Toast.LENGTH_SHORT)
                        .show();

            }

            @Override
            public void onSuccess() {
                if(!thrown)
                    thrown = true;
            }

            @Override
            public void onComplete() {
                if(!thrown)
                    thrown = true;

                if(labelShown) {
                    bundles.showLabel(VISIBLE);
                }

                market_progressbar.setVisibility(GONE);
            }
        };

        if(!NetworkUtils.isNetworkAvailable(getContext(),
                globalPreferencesViewModel.getNetworkPreference())) {
            Toast.makeText(getActivity(), "Internet not available.", Toast.LENGTH_SHORT)
                    .show();
            processCompleteListener.onFailure();

            return;
        }

        addMarketplaceBundleThemes(bundles, null, null, null, null, processCompleteListener);
    }

    private void populateIndividualThemes() {
        MarketplaceListLayout prestige = new MarketplaceListLayout(getContext());
        prestige.setLabel("Prestige Themes");
        MarketplaceListLayout event = new MarketplaceListLayout(getContext());
        event.setLabel("Event Themes");
        MarketplaceListLayout community = new MarketplaceListLayout(getContext());
        community.setLabel("Community Themes");

        linearLayout_marketplace_items.addView(prestige);
        linearLayout_marketplace_items.addView(event);
        linearLayout_marketplace_items.addView(community);

        OnFirestoreProcessListener processCompleteListener = new OnFirestoreProcessListener() {
            boolean thrown = false;
            boolean labelShown = false;

            @Override
            public void onFailure() {
                if(!thrown)
                    thrown = true;

                market_progressbar.setVisibility(GONE);
                label_marketplace_error.setVisibility(VISIBLE);

                Toast.makeText(getActivity(),
                                "Could not access the marketplace.",
                                Toast.LENGTH_SHORT)
                        .show();
            }

            @Override
            public void onSuccess() {
                if(!thrown)
                    thrown = true;

                labelShown = true;
            }

            @Override
            public void onComplete() {
                if(!thrown)
                    thrown = true;

                market_progressbar.setVisibility(GONE);
            }

        };

        if(!NetworkUtils.isNetworkAvailable(getContext(),
                globalPreferencesViewModel.getNetworkPreference())) {
            Toast.makeText(getActivity(), "Internet not available.", Toast.LENGTH_SHORT)
                    .show();
            processCompleteListener.onFailure();

            return;
        }

        addMarketplaceSingleThemes(prestige, "group", "Prestige", "priority", Query.Direction.ASCENDING, processCompleteListener);
        addMarketplaceSingleThemes(event, "group", "Event", null, null, processCompleteListener);
        addMarketplaceSingleThemes(community, "group", "Community", null, null, processCompleteListener);
    }

    private void addMarketplaceSingleThemes(MarketplaceListLayout list, String field, String value,
                                            String orderField, Query.Direction order,
                                            OnFirestoreProcessListener listener) {

        Task<QuerySnapshot> query = null;
        try {
            query = FirestoreMarketplace.getThemesWhere(field, value, orderField, order);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(query == null) {
            listener.onFailure();
            return;
        }

        query.addOnSuccessListener(snapshot -> {
            if(listener != null) {
                listener.onSuccess();
            }

            for (DocumentSnapshot documentSnapshot : snapshot.getDocuments()) {
                if (!documentSnapshot.exists()) {
                    Log.d("Firestore", "Theme document snapshot DNE.");
                } else {
                    try {
                        String uuid = documentSnapshot.getReference().getId();

                        MarketSingleTheme inboundTheme = documentSnapshot.toObject(MarketSingleTheme.class);
                        if (inboundTheme != null) {
                            inboundTheme = new MarketSingleTheme(uuid, inboundTheme);

                            CustomTheme customTheme =
                                    globalPreferencesViewModel.getColorThemeControl()
                                            .getThemeByUUID(uuid);
                            @StyleRes int style = customTheme.getStyle();

                            MarketplaceSingleThemeView marketplaceItem =
                                    buildMarketplaceSingleThemeView(
                                    list, inboundTheme, customTheme, style);

                            if(!customTheme.isUnlocked()) {
                                list.addView(marketplaceItem);
                                list.requestLayout();
                                list.invalidate();
                            }
                        }
                    } catch (Exception e) {
                        Log.d("Firestore", "Error CREATING PETTheme!");
                        e.printStackTrace();
                    }
                }
            }

        }).addOnFailureListener(e -> {
            if (listener != null) {
                listener.onFailure();
            }
        })
        .addOnCompleteListener(task -> {
            if(listener != null) {
                listener.onComplete();
            }

            if(list.getChildCount() <= 1) {
                list.setVisibility(GONE);
            } else {
                list.showLabel(VISIBLE);
            }
        });
    }

    private void addMarketplaceBundleThemes(MarketplaceListLayout list, String field, String value,
                                            String orderField, Query.Direction order,
                                            OnFirestoreProcessListener listener) {

        Task<QuerySnapshot> query = null;
        try {
            query = FirestoreMarketplace.getBundleWhere(field, value, orderField, order);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(query == null) {
            listener.onFailure();
            return;
        }

        query.addOnSuccessListener(snapshot -> {
            if(listener != null) {
                listener.onSuccess();
            }

            for (DocumentSnapshot documentSnapshot : snapshot.getDocuments()) {
                if (!documentSnapshot.exists()) {
                    Log.d("Firestore", "Bundle document snapshot DNE.");
                } else {

                    Map<String, Object> data = documentSnapshot.getData();
                    if(data != null) {
                        String docId = documentSnapshot.getReference().getId();

                        List<?> documentRefs = (List<?>) data.get("items");
                        ArrayList<String> themeIDs = new ArrayList<>();
                        if(documentRefs != null) {
                            for (Object item : documentRefs) {
                                if(item instanceof DocumentReference documentRef) {
                                    themeIDs.add(documentRef.getId());
                                }
                            }
                        }

                        MarketThemeBundle bundle = null;
                        try {
                            bundle = documentSnapshot.toObject(MarketThemeBundle.class);
                        } catch (Exception e) {
                            Log.d("Firestore", "Error CREATING PETTheme!");
                            e.printStackTrace();
                        }

                        if (bundle != null) {
                            ArrayList<CustomTheme> customThemes = new ArrayList<>();
                            for(String themeID: themeIDs) {
                                customThemes.add(globalPreferencesViewModel.getColorThemeControl()
                                        .getThemeByUUID(themeID));
                            }
                            bundle = new MarketThemeBundle(docId, bundle, customThemes);
                            if(!bundle.isUnlocked()) {
                                MarketplaceBundleThemeView marketplaceItem =
                                        buildMarketplaceBundleThemeView(
                                                list, bundle);

                                list.addView(marketplaceItem);
                                list.requestLayout();
                                list.invalidate();
                            }
                            /*
                            if(!customTheme.isUnlocked()) {
                                list.addView(marketplaceItem);
                                list.requestLayout();
                                list.invalidate();
                            }
                            */
                        }


                    }
                }
            }

        }).addOnFailureListener(e -> {
            if (listener != null) {
                listener.onFailure();
            }
        })
        .addOnCompleteListener(task -> {
            if(list.getChildCount() <= 1) {
                list.setVisibility(GONE);
            } else {
                //list.showLabel(GONE);
            }

            if(listener != null) {
                listener.onComplete();
            }

        });
    }

    @NonNull
    private MarketplaceBundleThemeView buildMarketplaceBundleThemeView(
            MarketplaceListLayout list, MarketThemeBundle bundleThemes) {

        MarketplaceBundleThemeView marketplaceBundleView =
                new MarketplaceBundleThemeView(getContext(), null);

        /*TODO marketplaceBundle.setPurchaseable(!theme.isUnlocked());*/
        marketplaceBundleView.setBundle(bundleThemes);
        marketplaceBundleView.setCreditCost(bundleThemes.getBuyCredits());

        AppCompatButton buyButton = marketplaceBundleView.getBuyButton();

        if(buyButton != null) {

            OnFirestoreProcessListener purchaseListener = new OnFirestoreProcessListener() {
                @Override
                public void onFailure() {
                    Log.d("Firestore", "Could not add/retrieve purchase document!");
                }
            };

            OnFirestoreProcessListener buyButtonCallback = new OnFirestoreProcessListener() {
                @Override
                public void onSuccess() {
                    ArrayList<String> uuids = new ArrayList<>();
                    for(CustomTheme t: bundleThemes.getThemes()) {
                        uuids.add(t.getID());
                    }

                    try {
                        FirestorePurchaseHistory.addPurchaseDocuments(
                                uuids,
                                "Theme Bundle",
                                purchaseListener);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if(getActivity() != null) {
                        Toast.makeText(requireActivity(),
                                "Skin purchased!",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure() {
                    if(getActivity() != null) {
                        Toast.makeText(requireActivity(),
                                "Not enough credits for purchase!",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onComplete() {
                    ViewPropertyAnimator marketItemAnimation =
                        marketplaceBundleView.animate()
                            .setDuration(300)
                            .translationX(list.getWidth())
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    super.onAnimationStart(animation);

                                    marketplaceBundleView.setEnabled(false);
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);

                                    list.removeView(marketplaceBundleView);
                                }
                            });
                    marketItemAnimation.start();
                }
            };

            buyButton.setOnClickListener(v -> {
                try {
                    FirestoreAccountCredit.removeCredits(
                            marketplaceBundleView.getCreditCost(),
                            buyButtonCallback);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return marketplaceBundleView;
    }


    @NonNull
    private MarketplaceSingleThemeView buildMarketplaceSingleThemeView(
            MarketplaceListLayout list, MarketSingleTheme tempTheme, CustomTheme theme, int style) {

        MarketplaceSingleThemeView marketplaceItem =
                new MarketplaceSingleThemeView(new ContextThemeWrapper(
                        getContext(), style), null, style);
        marketplaceItem.setPurchaseable(!theme.isUnlocked());
        marketplaceItem.setCreditCost(tempTheme.getBuyCredits());
        AppCompatButton buyButton = marketplaceItem.getBuyButton();

        if(buyButton != null) {

            OnFirestoreProcessListener purchaseListener = new OnFirestoreProcessListener() {
                @Override
                public void onFailure() {
                    Log.d("Firestore", "Could not add/retrieve purchase document!");
                }
            };

            OnFirestoreProcessListener buyButtonCallback = new OnFirestoreProcessListener() {
                @Override
                public void onSuccess() {

                    try {
                        FirestorePurchaseHistory.addPurchaseDocument(
                                String.valueOf(tempTheme.getUuid()),
                                "Single Theme",
                                purchaseListener);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if(getActivity() != null) {
                        Toast.makeText(requireActivity(),
                                "Skin purchased!",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure() {
                    if(getActivity() != null) {
                        Toast.makeText(requireActivity(),
                                "Not enough credits for purchase!",
                                Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onComplete() {
                    ViewPropertyAnimator marketItemAnimation =
                        marketplaceItem.animate()
                            .setDuration(300)
                            .translationX(list.getWidth())
                            .setListener(new AnimatorListenerAdapter() {
                                @Override
                                public void onAnimationStart(Animator animation) {
                                    super.onAnimationStart(animation);

                                    marketplaceItem.setEnabled(false);
                                }

                                @Override
                                public void onAnimationEnd(Animator animation) {
                                    super.onAnimationEnd(animation);

                                    list.removeView(marketplaceItem);
                                }
                            });
                    marketItemAnimation.start();
                }
            };

            buyButton.setOnClickListener(v -> {
                try {

                    FirestoreAccountCredit.removeCredits(
                            marketplaceItem.getCreditCost(),
                            buyButtonCallback);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
        return marketplaceItem;
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

        if(!NetworkUtils.isNetworkAvailable(getContext(),
                globalPreferencesViewModel.getNetworkPreference())) {
            Toast.makeText(getActivity(), "Internet not available.", Toast.LENGTH_SHORT)
                    .show();

            return;
        }

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
                e.printStackTrace();
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
                    FirestoreUser.buildUserDocument().get().addOnCompleteListener(task1 -> {
                        initAccountCreditListener();
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }

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

    public interface OnAdLoadedListener {
        void onAdLoaded();
    }

    public void loadRewardedAd(OnAdLoadedListener listener) {

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

                        if(listener != null) {
                            listener.onAdLoaded();
                        }
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
                loadRewardedAd(null);
            });
        } else {
            Log.d("RewardedAd", "The rewarded ad wasn't ready yet.");

            if(NetworkUtils.isNetworkAvailable(getContext(),
                    globalPreferencesViewModel.getNetworkPreference())) {
                loadRewardedAd(this::showRewardedAd);
            } else {
                Toast.makeText(getActivity(), "Internet not available.", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    @Override
    public void onResume() {

        super.onResume();
    }


    public void initBillingClient() {
        handlePendingPurchases();

        connectToGooglePlayBilling();
    }

    private void handlePendingPurchases() {
        billingClient = BillingClient.newBuilder(requireContext())
                .setListener(purchasesUpdatedListener)
                .enablePendingPurchases()
                .build();

        Log.d("Billing", "Pending purchases will be handled.");
    }

    private void connectToGooglePlayBilling() {
        Log.d("Billing", "Attempting to setup Billing...");

        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(BillingResult billingResult) {
                if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    Log.d("Billing", "Billing setup finished successfully!");
                    // The BillingClient is ready. You can query purchases here.
                    queryMarketplaceItems();
                } else {
                    Log.d("Billing", "Billing setup unsuccessful.\nCode: " +
                            billingResult.getResponseCode() + "\nDebug: " +
                            billingResult.getDebugMessage());
                }
            }
            @Override
            public void onBillingServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
                Log.d("Billing", "Billing service disconnected.");

                connectToGooglePlayBilling();
            }
        });

    }

    // Google Billing Library
    private final PurchasesUpdatedListener purchasesUpdatedListener = (billingResult, list) -> {
        if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK &&
                list != null && !list.isEmpty()) {
            for(Purchase purchase: list) {
                if(purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED &&
                    !purchase.isAcknowledged()) {
                    Toast.makeText(getContext(), "Purchase successful!", Toast.LENGTH_LONG);
                }
            }
        }
    };

    public void queryMarketplaceItems() {
        Log.d("Billing", "Obtaining list of Marketplace items...");

        List<String> productIds = new ArrayList<>();
        productIds.add("credits_100");
        productIds.add("credits_500");
        productIds.add("credits_1000");

        List<QueryProductDetailsParams.Product> productsQueryList = new ArrayList<>();
        for(String id: productIds) {
            Log.d("Billing", "Building item " + id);
            QueryProductDetailsParams.Product product = QueryProductDetailsParams.Product.newBuilder()
                    .setProductId(id)
                    .setProductType(BillingClient.ProductType.INAPP)
                    .build();
            productsQueryList.add(product);
        }

        QueryProductDetailsParams queryProductDetailsParams =
                QueryProductDetailsParams.newBuilder()
                        .setProductList(
                                productsQueryList)
                        .build();

        billingClient.queryProductDetailsAsync(
                queryProductDetailsParams,
                (billingResult, productDetailsList) -> {
                    // check billingResult
                    // process returned productDetailsList
                    if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK &&
                            !productDetailsList.isEmpty()) {

                        Log.d("Billing", "Finished querying Marketplace with " +
                                productDetailsList.size() + " results.");

                        for(ProductDetails productDetails: productDetailsList) {
                            Log.d("Billing", productDetails.toString());
                        }
                    }
                }
        );

    }
}

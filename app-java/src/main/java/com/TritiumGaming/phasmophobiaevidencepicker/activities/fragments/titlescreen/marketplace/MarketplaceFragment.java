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
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.FirestoreFragment;
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming.CustomTheme;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.FormatterUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.utilities.NetworkUtils;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.GlobalPreferencesViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.TitlescreenViewModel;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.listeners.OnFirestoreProcessListener;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.objects.theme.bundle.MarketThemeBundle;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.objects.theme.theme.MarketSingleTheme;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.merchandise.bundles.FirestoreMerchandiseBundle;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.merchandise.themes.FirestoreMerchandiseThemes;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.FirestoreAccountCredit;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.account.transaction.FirestoreUnlockHistory;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MarketplaceFragment extends FirestoreFragment {

    private GlobalPreferencesViewModel globalPreferencesViewModel = null;
    private TitlescreenViewModel titleScreenViewModel = null;

    private LinearLayout list_marketplace_items;
    private MarketplaceListLayout marketList_bundles,
            marketList_prestige, marketList_event, marketList_community;

    private AppCompatTextView label_account_credits, label_marketplace_error;

    private AccountObtainCreditsView obtainCreditsView;

    private ProgressBar market_progressbar;

    private RewardedAd rewardedAd = null;

    private boolean showEmail = false;

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
        final View cardview_account = view.findViewById(R.id.cardView_account);
        final AppCompatTextView label_account_info = view.findViewById(R.id.settings_accountsettings_info);
        final AppCompatButton button_ad_watch = view.findViewById(R.id.button_ad_watch);
        final AppCompatButton button_buy = view.findViewById(R.id.settings_account_buy_button);

        obtainCreditsView = view.findViewById(R.id.layout_account_obtainCredits);

        label_marketplace_error = view.findViewById(R.id.market_loaderror);
        label_account_credits = view.findViewById(R.id.label_credits_actual);

        final ScrollView scrollview_marketplace_items = view.findViewById(R.id.scrollview_marketplace_items);
        if(scrollview_marketplace_items != null) {
            list_marketplace_items = scrollview_marketplace_items.findViewById(R.id.list_marketplace_items);
        }

        market_progressbar = view.findViewById(R.id.market_progressbar);

        btn_account_login.setOnClickListener(v -> {
            manualSignInAccount();

            view.invalidate();
        });

        button_ad_watch.setOnClickListener(v -> showRewardedAd());

        button_buy.setOnClickListener(this::gotoBillingMarketplace);

        if(getActivity() != null) {
            getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                new OnBackPressedCallback(true) {
                    @Override
                    public void handleOnBackPressed() {
                        Navigation.findNavController(view).popBackStack();
                    }
                });
        }


        // TODO Add to Account Details view
        /*
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

        if(constraint_account_name != null) {
            constraint_account_name.setOnClickListener(v -> {
                showEmail = !showEmail;

                if (showEmail) {
                    label_account_info.setText(email_displayed);
                } else {
                    label_account_info.setText(finalEmail_obfuscated);
                }
            });
        }
        */

        /*
        if(firebaseUser == null) {
            constraint_requestlogin.setVisibility(VISIBLE);
            cardview_account.setVisibility(GONE);
            obtainCreditsView.setVisibility(GONE);

            market_progressbar.setVisibility(GONE);
        } else {
            constraint_requestlogin.setVisibility(GONE);
            cardview_account.setVisibility(VISIBLE);
            obtainCreditsView.setVisibility(VISIBLE);

            if(label_account_info != null) {
                label_account_info.setText(email_displayed);
                if(!showEmail) {
                    label_account_info.setText(email_obfuscated);
                }
            }

        }*/

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(firebaseUser == null) {
            market_progressbar.setVisibility(GONE);
        }

        // CANCEL BUTTON
        button_back.setOnClickListener(v -> {
            Navigation.findNavController(v).popBackStack();
        });

        obtainCreditsView.enableAdWatchButton(false);
        requireActivity().runOnUiThread(() -> loadRewardedAd(null));

        requireActivity().runOnUiThread(() -> new Thread(this::initAccountCreditListener).start());
        requireActivity().runOnUiThread(() -> new Thread(this::populateMarketplaceUnPurchasedItems).start());

    }
    /**
     * gotoLanguagesFragment method
     */
    public void gotoBillingMarketplace(View v) {
        Navigation.findNavController(v).
                navigate(R.id.action_marketplaceFragment_to_marketplaceBillingFragment);
    }

    private void initAccountCreditListener() {
        try {
            DocumentReference creditDoc = FirestoreAccountCredit.getCreditsDocument();
            creditDoc.get()
                    .addOnCompleteListener(task -> {
                Long credits_read = task.getResult().get(FirestoreAccountCredit.FIELD_CREDITS_EARNED, Long.class);
                long user_credits = credits_read != null ? credits_read : 0;

                label_account_credits.setText(String.valueOf(user_credits));
            });

            creditDoc.addSnapshotListener((documentSnapshot, error) -> {
                if(documentSnapshot == null) { return; }

                Long credits_read = documentSnapshot.get(FirestoreAccountCredit.FIELD_CREDITS_EARNED, Long.class);
                long user_credits = credits_read != null ? credits_read : 0;

                label_account_credits.setText(String.valueOf(user_credits));
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void populateMarketplaceUnPurchasedItems() {
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
                .addOnCompleteListener(task -> {

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

        try {
            unlockHistoryCollection
                .addSnapshotListener((value, error) -> {
                    if(value == null) { return; }

                    for(DocumentSnapshot documentSnapshot : value.getDocuments()) {
                        DocumentReference documentReference = documentSnapshot.getReference();

                        String uuid = documentReference.getId();
                        CustomTheme customTheme =
                                globalPreferencesViewModel.getColorThemeControl().getThemeByUUID(uuid);
                        customTheme.setUnlocked(CustomTheme.Availability.UNLOCKED_PURCHASE);
                    }

                    revalidateMarketplaceBundles();
                    revalidateMarketplaceSingles();

                });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void revalidateMarketplaceBundles() {
        if(marketList_bundles != null) {
            marketList_bundles.validateItems();
        }
    }

    private void revalidateMarketplaceSingles() {
        if(marketList_prestige != null) {
            marketList_prestige.validateItems();
        }
        if(marketList_event != null) {
            marketList_event.validateItems();
        }
        if(marketList_community != null) {
            marketList_community.validateItems();
        }
    }

    public void populateBundledThemes() {

        marketList_bundles = new MarketplaceListLayout(getContext());
        marketList_bundles.setLabel("Theme Bundles");
        marketList_bundles.showLabel(GONE);

        list_marketplace_items.addView(marketList_bundles);

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
                    marketList_bundles.showLabel(VISIBLE);
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

        addMarketplaceBundleThemes(marketList_bundles, null, null, null, null, processCompleteListener);
    }

    private void populateIndividualThemes() {

        marketList_prestige = new MarketplaceListLayout(getContext());
        marketList_prestige.setLabel("Prestige Themes");
        marketList_event = new MarketplaceListLayout(getContext());
        marketList_event.setLabel("Event Themes");
        marketList_community = new MarketplaceListLayout(getContext());
        marketList_community.setLabel("Community Themes");

        list_marketplace_items.addView(marketList_prestige);
        list_marketplace_items.addView(marketList_event);
        list_marketplace_items.addView(marketList_community);

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

        addMarketplaceSingleThemes(marketList_prestige, "group", "Prestige", "priority", Query.Direction.ASCENDING, processCompleteListener);
        addMarketplaceSingleThemes(marketList_event, "group", "Event", null, null, processCompleteListener);
        addMarketplaceSingleThemes(marketList_community, "group", "Community", null, null, processCompleteListener);
    }

    private void addMarketplaceSingleThemes(MarketplaceListLayout list, String field, String value,
                                            String orderField, Query.Direction order,
                                            OnFirestoreProcessListener listener) {

        Task<QuerySnapshot> query = null;
        try {
            query = FirestoreMerchandiseThemes.getThemesWhere(field, value, orderField, order);
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
                    String uuid = documentSnapshot.getReference().getId();
                    MarketSingleTheme marketSingleTheme = null;
                    try {
                        marketSingleTheme = documentSnapshot.toObject(MarketSingleTheme.class);
                    } catch (Exception e) {
                        Log.d("Firestore", "Error CREATING PETTheme!");
                        e.printStackTrace();
                    }
                    if (marketSingleTheme != null) {
                        CustomTheme customTheme =
                                globalPreferencesViewModel.getColorThemeControl()
                                        .getThemeByUUID(uuid);

                        marketSingleTheme = new MarketSingleTheme(uuid, marketSingleTheme, customTheme);

                        MarketplaceSingleThemeView marketplaceItem =
                                buildMarketplaceSingleThemeView(list, marketSingleTheme);

                        if(!marketSingleTheme.isUnlocked()) {
                            list.addView(marketplaceItem);
                            list.requestLayout();
                            list.invalidate();
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
            query = FirestoreMerchandiseBundle.getBundleWhere(field, value, orderField, order);
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
                    continue;
                }

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
        marketplaceBundleView.setBundle(bundleThemes);

        View.OnClickListener buyButtonListener = v -> {

            OnFirestoreProcessListener buyButtonCallback = new OnFirestoreProcessListener() {

                @Override
                public void onSuccess() {
                    ArrayList<String> uuids = new ArrayList<>();
                    for(CustomTheme t: bundleThemes.getThemes()) {
                        uuids.add(t.getID());
                    }

                    try {
                        FirestoreUnlockHistory.addUnlockedDocuments(
                                uuids,
                                "Theme Bundle",
                                new OnFirestoreProcessListener() {
                                    @Override
                                    public void onSuccess() {
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
                                                        });
                                        marketItemAnimation.start();
                                    }

                                    @Override
                                    public void onFailure() {
                                        Log.d("Firestore",
                                                "Could not add/retrieve purchase document!");
                                    }
                                });
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
                    Log.d("Bundle", "Bundle process completed.");
                }
            };

            try {
                FirestoreAccountCredit.removeCredits(
                        marketplaceBundleView.getCreditCost(),
                        buyButtonCallback);

            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };

        marketplaceBundleView.setBuyButtonListener(buyButtonListener);

        return marketplaceBundleView;
    }


    @NonNull
    private MarketplaceSingleThemeView buildMarketplaceSingleThemeView(
            MarketplaceListLayout list, MarketSingleTheme marketSingleTheme) {

        MarketplaceSingleThemeView marketplaceItemView =
                new MarketplaceSingleThemeView(new ContextThemeWrapper(
                        getContext(), marketSingleTheme.getStyle()),
                        null, marketSingleTheme.getStyle());
        marketplaceItemView.setTheme(marketSingleTheme);


        View.OnClickListener buyButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                OnFirestoreProcessListener buyButtonCallback = new OnFirestoreProcessListener() {
                    @Override
                    public void onSuccess() {
                        OnFirestoreProcessListener purchaseListener = new OnFirestoreProcessListener() {
                            @Override
                            public void onSuccess() {
                                ViewPropertyAnimator marketItemAnimation =
                                        marketplaceItemView.animate()
                                                .setDuration(300)
                                                .translationX(list.getWidth())
                                                .setListener(new AnimatorListenerAdapter() {
                                                    @Override
                                                    public void onAnimationStart(Animator animation) {
                                                        super.onAnimationStart(animation);

                                                        marketplaceItemView.setEnabled(false);
                                                    }

                                                    @Override
                                                    public void onAnimationEnd(Animator animation) {
                                                        super.onAnimationEnd(animation);

                                                        list.removeView(marketplaceItemView);
                                                    }
                                                });
                                marketItemAnimation.start();
                            }

                            @Override
                            public void onFailure() {
                                Log.d("Firestore", "Could not add/retrieve purchase document!");
                            }
                        };

                        try {
                            FirestoreUnlockHistory.addUnlockDocument(
                                    String.valueOf(marketSingleTheme.getUuid()),
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
                        Log.d("Bundle", "Single theme process completed.");
                    }
                };

                try {
                    FirestoreAccountCredit.removeCredits(
                            marketplaceItemView.getCreditCost(),
                            buyButtonCallback);

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        marketplaceItemView.setBuyButtonListener(buyButtonListener);

        return marketplaceItemView;
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

                        int rewardQuantity = rewardedAd.getRewardItem().getAmount();
                        obtainCreditsView.setWatchAdsLabelDescription(rewardQuantity);
                        obtainCreditsView.enableAdWatchButton(true);

                        if(listener != null) {
                            listener.onAdLoaded();
                        }
                    }
                    @Override
                    public void onAdFailedToLoad(LoadAdError loadAdError) {
                        Log.d("RewardedAd", loadAdError.toString());
                        obtainCreditsView.enableAdWatchButton(false);
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
                    e.printStackTrace();
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

}

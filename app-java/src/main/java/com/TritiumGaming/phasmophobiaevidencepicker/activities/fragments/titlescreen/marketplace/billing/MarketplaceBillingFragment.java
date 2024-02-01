package com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.marketplace.billing;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.TritiumGaming.phasmophobiaevidencepicker.R;
import com.TritiumGaming.phasmophobiaevidencepicker.activities.fragments.titlescreen.marketplace.MarketplaceListLayout;
import com.TritiumGaming.phasmophobiaevidencepicker.data.persistent.theming.CustomTheme;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.objects.billable.MarketplaceMtxItem;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.store.microtransactions.billables.FirestoreMicrotransactionBillables;
import com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.transactions.user.FirestoreUser;
import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.ProductDetails;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.QueryProductDetailsParams;
import com.android.billingclient.api.SkuDetails;
import com.google.android.gms.tasks.Task;
import com.google.common.collect.ImmutableList;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.List;

public class MarketplaceBillingFragment extends Fragment {

    private BillingClient billingClient;

    private LinearLayout list_mtx_items;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_marketplace_billing, container, false);
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        AppCompatImageView back_button = view.findViewById(R.id.button_back);
        list_mtx_items = view.findViewById(R.id.list_marketplace_items);
        ConstraintLayout constraint_requestlogin =
                view.findViewById(R.id.constraint_requestlogin);

        FirebaseUser firebaseUser = null;
        try {
            firebaseUser = FirestoreUser.getCurrentFirebaseUser();
        } catch (Exception e) {
            e.printStackTrace();
        }
        constraint_requestlogin.setVisibility(firebaseUser != null ? GONE : VISIBLE);

        if(getActivity() != null) {
            getActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(),
                    new OnBackPressedCallback(true) {
                        @Override
                        public void handleOnBackPressed() {
                            Navigation.findNavController(view).popBackStack();
                        }
                    });
        }

        list_mtx_items.setVisibility(VISIBLE);

        back_button.setOnClickListener(v -> Navigation.findNavController(v).popBackStack());

        initBillingClient();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public void initBillingClient() {
        createBillingClient();
        connectToGooglePlayBilling();
    }

    private void createBillingClient() {
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
                    try {
                        queryMarketplaceItems();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
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
                    Toast.makeText(
                                requireActivity(),
                                "Purchase successful!",
                                Toast.LENGTH_LONG)
                            .show();
                    Log.d("Billing", "Purchase successful!\n" + purchase.getProducts().get(0));
                    handlePurchase(purchase);
                }
            }
        }
    };

    private void handlePurchase(Purchase purchase) {
        // Verify the purchase.
        // Ensure entitlement was not already granted for this purchaseToken.
        // Grant entitlement to the user.

        ConsumeParams consumeParams =
                ConsumeParams.newBuilder()
                        .setPurchaseToken(purchase.getPurchaseToken())
                        .build();

        ConsumeResponseListener listener = (billingResult, purchaseToken) -> {
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                Log.d("Billing", "Purchase consumed!\n" + purchase.getProducts().get(0));
            }
        };

        billingClient.consumeAsync(consumeParams, listener);
    }

    public void queryMarketplaceItems() throws Exception {
        Log.d("Billing", "Obtaining list of Marketplace items from database...");

        Task<QuerySnapshot> billablesQuery = null;
        try {
            billablesQuery = FirestoreMicrotransactionBillables.getBillablesWhere(
                    "type", "credits", "tier", Query.Direction.ASCENDING);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(billablesQuery == null) {
            Log.d("Billing", "Microtransaction query snapshot DNE.");
            return;
        }

        billablesQuery
                .addOnSuccessListener(snapshot -> {

                    List<QueryProductDetailsParams.Product> productsQueryList = new ArrayList<>();
                    for (DocumentSnapshot documentSnapshot : snapshot.getDocuments()) {

                        if (!documentSnapshot.exists()) {
                            Log.d("Billing", "Microtransaction document snapshot DNE.");
                            continue;
                        }

                        String purchase_id = documentSnapshot.get("product_id", String.class);
                        if (purchase_id == null) {
                            continue;
                        }

                        Log.d("Billing", "Building item " + purchase_id);
                        QueryProductDetailsParams.Product product =
                                QueryProductDetailsParams.Product.newBuilder()
                                        .setProductId(purchase_id)
                                        .setProductType(BillingClient.ProductType.INAPP)
                                        .build();
                        productsQueryList.add(product);
                    }

                    QueryProductDetailsParams queryProductDetailsParams =
                            QueryProductDetailsParams.newBuilder()
                                    .setProductList(productsQueryList)
                                    .build();

                    billingClient.queryProductDetailsAsync(
                        queryProductDetailsParams,
                        (billingResult, productDetailsList) -> {
                            // check billingResult
                            // process returned productDetailsList
                            if (billingResult.getResponseCode() ==
                                BillingClient.BillingResponseCode.OK &&
                                !productDetailsList.isEmpty()) {

                                Log.d("Billing", "Finished querying Marketplace with " +
                                        productDetailsList.size() + " results.");

                                requireActivity().runOnUiThread(() -> {

                                    MarketplaceListLayout marketplaceList =
                                            new MarketplaceListLayout(getContext(), null);
                                    marketplaceList.setVisibility(VISIBLE);
                                    marketplaceList.setLabel("Credits");
                                    marketplaceList.showLabel(VISIBLE);

                                    list_mtx_items.addView(marketplaceList);

                                    for (ProductDetails productDetails : productDetailsList) {
                                        MarketplaceMtxItem mtxItem =
                                                new MarketplaceMtxItem(productDetails);
                                        Log.d("Billing", "Adding " + mtxItem);

                                        try {
                                            MarketplaceMtxView marketplaceMtxView =
                                                new MarketplaceMtxView(getContext(), null);

                                            marketplaceMtxView.setBillableItem(mtxItem);
                                            marketplaceMtxView.setBuyButtonListener(v -> {
                                                if(getActivity() == null) { return; }

                                                MarketplaceMtxItem item = marketplaceMtxView.getBillableItem();
                                                if(item == null) { return; }
                                                ProductDetails productDetail =
                                                        item.getProductDetails();

                                                ImmutableList<BillingFlowParams.ProductDetailsParams> productDetailsParamsList =
                                                        ImmutableList.of(
                                                                BillingFlowParams.ProductDetailsParams.newBuilder()
                                                                        .setProductDetails(productDetail)
                                                                        .build()
                                                        );

                                                BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                                                        .setProductDetailsParamsList(productDetailsParamsList)
                                                        .build();

                                                // Launch the billing flow
                                                billingClient.launchBillingFlow(getActivity(), billingFlowParams);
                                            });

                                            list_mtx_items.addView(marketplaceMtxView);
                                        } catch (Exception e) {
                                            Log.d("Billing", "Failed! ");
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        });
                })
                .addOnFailureListener(e -> {
                    Log.d("Billing", "Microtransaction query failed!");
                    e.printStackTrace();
                }).addOnCompleteListener(task -> {
                    Log.d("Billing", "Microtransaction query completed!");
                    ProgressBar progressbar = requireView().findViewById(R.id.market_progressbar);
                    progressbar.setVisibility(GONE);
                });
    }
}



package com.tritiumstudios.phasmophobiaevidencetool.android.firebase.firestore.objects.billable;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.billingclient.api.ProductDetails;

public class MarketplaceMtxItem {

    private final ProductDetails productDetails;

    public MarketplaceMtxItem(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }

    @NonNull
    public String getProductID() {
        return productDetails.getProductId();
    }

    @NonNull
    public String getName() {
        return productDetails.getName();
    }

    @NonNull
    public String getDescription() {
        return productDetails.getDescription();
    }

    public ProductDetails getProductDetails() {
        return productDetails;
    }

    @Nullable
    private ProductDetails.OneTimePurchaseOfferDetails getPurchaseDetails() {
        return getProductDetails().getOneTimePurchaseOfferDetails();
    }

    @NonNull
    public String getPurchaseAmount() {
        ProductDetails.OneTimePurchaseOfferDetails details = getPurchaseDetails();
        if(details == null) {
            return "ERROR";
        }

        return details.getFormattedPrice();
    }

    @NonNull
    public String getCurrencyCode() {
        return getPurchaseDetails().getPriceCurrencyCode();
    }

    @NonNull
    public String toString() {
        return getProductID() + " " + getName() + " " + getPurchaseDetails() +
                " " + getPurchaseAmount() + " " + getCurrencyCode();
    }
}


package com.TritiumGaming.phasmophobiaevidencepicker.firebase.firestore.objects.billable;

import com.android.billingclient.api.ProductDetails;

public class MarketplaceMtxItem {

    private final ProductDetails productDetails;

    public MarketplaceMtxItem(ProductDetails productDetails) {
        this.productDetails = productDetails;
    }

    public String getProductID() {
        return productDetails.getProductId();
    }

    public String getName() {
        return productDetails.getName();
    }

    public String getDescription() {
        return productDetails.getDescription();
    }

    public ProductDetails getProductDetails() {
        return productDetails;
    }

    private ProductDetails.OneTimePurchaseOfferDetails getPurchaseDetails() {
        return getProductDetails().getOneTimePurchaseOfferDetails();
    }

    public String getPurchaseAmount() {
        ProductDetails.OneTimePurchaseOfferDetails details = getPurchaseDetails();
        if(details == null) {
            return "ERROR";
        }

        return details.getFormattedPrice();
    }

    public String getCurrencyCode() {
        return getPurchaseDetails().getPriceCurrencyCode();
    }

    public String toString() {
        return getProductID() + " " + getName() + " " + getPurchaseDetails() +
                " " + getPurchaseAmount() + " " + getCurrencyCode();
    }
}


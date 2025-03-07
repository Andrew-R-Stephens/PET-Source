package com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodels.models.firestore.billable

import com.android.billingclient.api.ProductDetails
import com.android.billingclient.api.ProductDetails.OneTimePurchaseOfferDetails

class MarketMicroTransactionModel(val productDetails: ProductDetails) {
    private val productID: String
        get() = productDetails.productId

    val name: String
        get() = productDetails.name

    val description: String
        get() = productDetails.description

    private val purchaseDetails: OneTimePurchaseOfferDetails?
        get() = productDetails.oneTimePurchaseOfferDetails

    val purchaseAmount: String
        get() {
            val details = purchaseDetails ?: return "ERROR"

            return details.formattedPrice
        }

    private val currencyCode: String
        get() = purchaseDetails?.priceCurrencyCode ?: "USD"

    override fun toString(): String {
        return productID + " " + name + " " + purchaseDetails +
                " " + purchaseAmount + " " + currencyCode
    }
}


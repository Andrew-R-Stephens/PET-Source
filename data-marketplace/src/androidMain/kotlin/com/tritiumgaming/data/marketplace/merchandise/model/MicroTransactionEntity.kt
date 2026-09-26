package com.tritiumgaming.data.marketplace.merchandise.model

import com.android.billingclient.api.ProductDetails

data class MicroTransactionEntity(
    val productDetails: ProductDetails,
    val productID: String = productDetails.productId,
    val name: String = productDetails.name,
    val description: String = productDetails.description,
    val purchaseAmount: String = productDetails.oneTimePurchaseOfferDetails?.formattedPrice ?: "Error",
    private val currencyCode: String = productDetails.oneTimePurchaseOfferDetails?.priceCurrencyCode ?: "USD"
) {

    override fun toString(): String {
        return "$productID $name $purchaseAmount $currencyCode $description"
    }

}
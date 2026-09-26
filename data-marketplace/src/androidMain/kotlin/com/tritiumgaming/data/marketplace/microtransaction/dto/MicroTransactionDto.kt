package com.tritiumgaming.data.marketplace.microtransaction.dto

import com.android.billingclient.api.ProductDetails
import com.tritiumgaming.data.marketplace.merchandise.model.MicroTransactionEntity

class MicroTransactionDto(
    internal val productDetails: ProductDetails
)

fun MicroTransactionDto.toDomain(): MicroTransactionEntity =
    MicroTransactionEntity(
        productDetails = productDetails
    )
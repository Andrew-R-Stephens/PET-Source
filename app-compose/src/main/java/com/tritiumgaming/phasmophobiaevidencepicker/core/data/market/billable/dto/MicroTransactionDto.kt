package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.dto

import com.android.billingclient.api.ProductDetails
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.merchandise.model.MicroTransactionEntity

class MicroTransactionDto(
    internal val productDetails: ProductDetails
)

fun MicroTransactionDto.toExternal(): MicroTransactionEntity =
    MicroTransactionEntity(
        productDetails
    )
package com.tritiumgaming.phasmophobiaevidencepicker.data.remote.dto.billable

import com.android.billingclient.api.ProductDetails
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.themes.MicroTransactionEntity

class MicroTransactionDto(
    private val productDetails: ProductDetails
) {

    fun toMicroTransactionEntity(): MicroTransactionEntity {

        return MicroTransactionEntity(
            productDetails
        )
    }

}


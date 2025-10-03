package com.tritiumgaming.data.marketplace.microtransaction.mapper

import com.tritiumgaming.data.marketplace.merchandise.model.MicroTransactionEntity
import com.tritiumgaming.data.marketplace.microtransaction.dto.MicroTransactionDto

fun MicroTransactionDto.toDomain(): MicroTransactionEntity =
    MicroTransactionEntity(
        productDetails = productDetails
    )
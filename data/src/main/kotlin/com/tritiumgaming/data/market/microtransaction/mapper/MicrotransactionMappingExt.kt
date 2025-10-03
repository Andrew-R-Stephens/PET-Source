package com.tritiumgaming.data.market.microtransaction.mapper

import com.tritiumgaming.data.market.merchandise.model.MicroTransactionEntity
import com.tritiumgaming.data.market.microtransaction.dto.MicroTransactionDto

fun MicroTransactionDto.toDomain(): MicroTransactionEntity =
    MicroTransactionEntity(
        productDetails = productDetails
    )
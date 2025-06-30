package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.microtransaction.mapper

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.merchandise.model.MicroTransactionEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.microtransaction.dto.MicroTransactionDto

fun MicroTransactionDto.toDomain(): MicroTransactionEntity =
    MicroTransactionEntity(
        productDetails = productDetails
    )
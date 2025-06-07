package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.microtransaction.mapper

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.microtransaction.dto.MicroTransactionDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.merchandise.model.MicroTransactionEntity

fun MicroTransactionDto.toDomain(): MicroTransactionEntity =
    MicroTransactionEntity(
        productDetails = productDetails
    )
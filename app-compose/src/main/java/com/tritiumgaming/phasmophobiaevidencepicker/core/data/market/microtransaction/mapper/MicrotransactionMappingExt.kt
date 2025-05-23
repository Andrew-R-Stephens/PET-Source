package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.microtransaction.mapper

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.dto.BillableDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.microtransaction.dto.MicroTransactionDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.billable.model.BillableEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.merchandise.model.MicroTransactionEntity

fun MicroTransactionDto.toExternal(): MicroTransactionEntity =
    MicroTransactionEntity(
        productDetails = productDetails
    )
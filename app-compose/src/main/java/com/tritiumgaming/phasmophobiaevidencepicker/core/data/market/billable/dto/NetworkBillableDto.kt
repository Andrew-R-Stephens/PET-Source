package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.dto

import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.billable.model.BillableEntity
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.merchandise.model.MicroTransactionEntity

class NetworkBillableDto {
}


fun NetworkBillableDto.toExternal(): BillableEntity =
    BillableEntity()
package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.mapper

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.dto.MarketBillableDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.billable.model.BillableEntity

fun MarketBillableDto.toExternal(): BillableEntity =
    BillableEntity()
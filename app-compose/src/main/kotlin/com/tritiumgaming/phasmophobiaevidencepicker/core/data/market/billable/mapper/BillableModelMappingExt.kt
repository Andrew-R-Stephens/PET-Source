package com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.mapper

import com.tritiumgaming.phasmophobiaevidencepicker.core.data.market.billable.dto.BillableDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.billable.model.BillableEntity

fun BillableDto.toExternal(): BillableEntity =
    BillableEntity()
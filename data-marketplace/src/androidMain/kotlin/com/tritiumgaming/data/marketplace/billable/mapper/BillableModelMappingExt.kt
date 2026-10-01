package com.tritiumgaming.data.marketplace.billable.mapper

import com.tritiumgaming.data.marketplace.billable.dto.MarketBillableDto
import com.tritiumgaming.data.marketplace.billable.model.MarketBillable

fun com.tritiumgaming.data.marketplace.billable.dto.MarketBillableDto.toDomain(): MarketBillable =
    MarketBillable(
        productId = productId,
        type = type,
        tier = tier,
        rewardAmount = rewardAmount,
        rewardItem = rewardItem,
        activeStatus = activeStatus,
    )

fun List<com.tritiumgaming.data.marketplace.billable.dto.MarketBillableDto>.toDomain(): List<MarketBillable> =
    map( MarketBillableDto::toDomain )

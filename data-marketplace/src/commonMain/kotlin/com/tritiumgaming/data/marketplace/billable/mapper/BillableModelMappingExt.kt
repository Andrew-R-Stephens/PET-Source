package com.tritiumgaming.data.marketplace.billable.mapper

import com.tritiumgaming.data.marketplace.billable.dto.MarketBillableDto
import com.tritiumgaming.shared.data.market.billable.model.MarketBillable

fun MarketBillableDto.toDomain(): MarketBillable =
    MarketBillable(
        productId = productId,
        type = type,
        tier = tier,
        rewardAmount = rewardAmount,
        rewardItem = rewardItem,
        activeStatus = activeStatus,
    )

fun List<MarketBillableDto>.toDomain(): List<MarketBillable> =
    map( MarketBillableDto::toDomain )

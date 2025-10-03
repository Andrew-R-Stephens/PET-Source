package com.tritiumgaming.data.market.billable.mapper

import com.tritiumgaming.data.market.billable.dto.MarketBillableDto
import com.tritiumgaming.shared.core.domain.market.billable.model.MarketBillable

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

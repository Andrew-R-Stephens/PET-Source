package com.tritiumgaming.shared.core.domain.market.billable.model

class MarketBillable (
    val productId: String,
    val type: String,
    val tier: Int,
    val rewardAmount: Int,
    val rewardItem: String,
    val activeStatus: Boolean = false
)
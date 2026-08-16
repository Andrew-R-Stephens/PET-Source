package com.tritiumgaming.feature.marketplace.ui.common

import com.tritiumgaming.shared.data.market.billable.model.MarketBillable

data class MarketCatalogBillablesUiState(
    val billables: List<MarketBillable> = emptyList()
)

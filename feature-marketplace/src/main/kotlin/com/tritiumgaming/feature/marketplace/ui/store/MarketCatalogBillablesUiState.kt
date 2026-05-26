package com.tritiumgaming.feature.marketplace.ui.store

import com.tritiumgaming.shared.data.market.billable.model.MarketBillable

data class MarketCatalogBillablesUiState(
    val billables: List<MarketBillable> = emptyList()
)

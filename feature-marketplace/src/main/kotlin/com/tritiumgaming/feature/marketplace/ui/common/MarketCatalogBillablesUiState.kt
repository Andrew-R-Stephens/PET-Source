package com.tritiumgaming.feature.marketplace.ui.common

import com.tritiumgaming.data.marketplace.billable.model.MarketBillable

data class MarketCatalogBillablesUiState(
    val billables: List<MarketBillable> = emptyList()
)

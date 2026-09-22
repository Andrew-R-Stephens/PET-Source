package com.tritiumgaming.feature.marketplace.app.mappers

import com.tritiumgaming.core.resources.R
import com.tritiumgaming.shared.data.market.common.mappers.MarketplaceResources
import com.tritiumgaming.shared.data.market.common.mappers.MarketplaceResources.MarketplaceCategoryTitles.*

fun MarketplaceResources.MarketplaceCategoryTitles.toStringResource() =
    when(this) {
        BUNDLES -> R.string.marketplace_list_bundles
        PRESTIGE -> R.string.marketplace_list_prestige
        EVENT -> R.string.marketplace_list_event
        COMMUNITY -> R.string.marketplace_list_community
    }
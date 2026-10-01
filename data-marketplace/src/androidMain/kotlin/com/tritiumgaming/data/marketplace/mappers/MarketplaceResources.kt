package com.tritiumgaming.data.marketplace.mappers

import com.tritiumgaming.core.resources.R
import com.tritiumgaming.data.marketplace.common.mappers.MarketplaceResources
import com.tritiumgaming.data.marketplace.common.mappers.MarketplaceResources.MarketplaceCategoryTitles.BUNDLES
import com.tritiumgaming.data.marketplace.common.mappers.MarketplaceResources.MarketplaceCategoryTitles.COMMUNITY
import com.tritiumgaming.data.marketplace.common.mappers.MarketplaceResources.MarketplaceCategoryTitles.EVENT
import com.tritiumgaming.data.marketplace.common.mappers.MarketplaceResources.MarketplaceCategoryTitles.PRESTIGE

fun MarketplaceResources.MarketplaceCategoryTitles.toStringResource() =
    when(this) {
        BUNDLES -> R.string.marketplace_list_bundles
        PRESTIGE -> R.string.marketplace_list_prestige
        EVENT -> R.string.marketplace_list_event
        COMMUNITY -> R.string.marketplace_list_community
    }
package com.tritiumgaming.shared.data.market.metadata.mappers

class MarketplaceResources {

    enum class MarketplaceCategoryTitles(val value: String) {
        BUNDLES("Bundle"),
        PRESTIGE("Prestige"),
        EVENT("Event"),
        COMMUNITY("Community");

        companion object {
            fun from(value: String): MarketplaceCategoryTitles? {
                return entries.firstOrNull { it.value == value }
            }
        }
    }

}
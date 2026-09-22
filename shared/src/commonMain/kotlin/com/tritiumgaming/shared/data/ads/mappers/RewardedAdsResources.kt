package com.tritiumgaming.shared.data.ads.mappers

import com.tritiumgaming.shared.data.ads.mappers.RewardedAdsResources.AdUnitID

class RewardedAdsResources {

    enum class RewardAdMessages {
        AD_NOT_LOADED
    }

    enum class AdUnitID {
        AD_BANNER_1,
        REWARDED_AD_1
    }
}

fun AdUnitID.asString() = when (this) {
    AdUnitID.AD_BANNER_1 -> "ca-app-pub-1890816745443024/7620657926"
    AdUnitID.REWARDED_AD_1 -> "ca-app-pub-1890816745443024/3221666922"
}

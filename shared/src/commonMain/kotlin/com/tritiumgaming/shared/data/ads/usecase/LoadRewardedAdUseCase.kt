package com.tritiumgaming.shared.data.ads.usecase

import com.tritiumgaming.shared.data.ads.repository.RewardedAdRepository

class LoadRewardedAdUseCase(
    private val repository: RewardedAdRepository
) {
    operator fun invoke(
        context: Any,
        onAdLoaded: (() -> Unit)? = null,
        onAdFailedToLoad: ((error: Any?) -> Unit)? = null
    ) {
        repository.loadAd(context, onAdLoaded, onAdFailedToLoad)
    }
}

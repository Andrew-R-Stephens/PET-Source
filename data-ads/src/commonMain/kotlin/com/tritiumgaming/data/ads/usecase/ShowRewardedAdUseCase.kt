package com.tritiumgaming.data.ads.usecase

import com.tritiumgaming.data.ads.repository.RewardedAdRepository

class ShowRewardedAdUseCase(
    private val repository: RewardedAdRepository
) {
    operator fun invoke(
        activity: Any,
        onRewardEarned: (amount: Int, type: String) -> Unit,
        onAdClosed: (() -> Unit)? = null,
        onAdFailedToShow: ((error: Any?) -> Unit)? = null
    ) {
        repository.showAd(activity, onRewardEarned, onAdClosed, onAdFailedToShow)
    }
}

package com.tritiumgaming.shared.data.ads.usecase

import com.tritiumgaming.shared.data.ads.repository.RewardedAdRepository

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

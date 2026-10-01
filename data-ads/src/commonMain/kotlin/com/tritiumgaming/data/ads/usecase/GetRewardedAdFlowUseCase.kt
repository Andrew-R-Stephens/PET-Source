package com.tritiumgaming.data.ads.usecase

import com.tritiumgaming.data.ads.model.RewardedAdState
import com.tritiumgaming.data.ads.repository.RewardedAdRepository
import kotlinx.coroutines.flow.StateFlow

class GetRewardedAdFlowUseCase(
    private val repository: RewardedAdRepository
) {
    operator fun invoke(): StateFlow<RewardedAdState> {
        return repository.adState
    }
}

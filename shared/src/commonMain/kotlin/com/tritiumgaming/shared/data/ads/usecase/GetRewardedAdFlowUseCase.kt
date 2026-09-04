package com.tritiumgaming.shared.data.ads.usecase

import com.tritiumgaming.shared.data.ads.model.RewardedAdState
import com.tritiumgaming.shared.data.ads.repository.RewardedAdRepository
import kotlinx.coroutines.flow.StateFlow

class GetRewardedAdFlowUseCase(
    private val repository: RewardedAdRepository
) {
    operator fun invoke(): StateFlow<RewardedAdState> {
        return repository.adState
    }
}

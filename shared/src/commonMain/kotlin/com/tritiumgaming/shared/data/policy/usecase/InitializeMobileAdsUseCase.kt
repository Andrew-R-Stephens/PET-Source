package com.tritiumgaming.shared.data.policy.usecase

import com.tritiumgaming.shared.data.policy.repository.PolicyRepository

class InitializeMobileAdsUseCase(
    private val repository: PolicyRepository
) {
    suspend operator fun invoke(
        context: Any,
        onFinished: () -> Unit
    ) {
        repository.initializeMobileAds(context, onFinished)
    }
}

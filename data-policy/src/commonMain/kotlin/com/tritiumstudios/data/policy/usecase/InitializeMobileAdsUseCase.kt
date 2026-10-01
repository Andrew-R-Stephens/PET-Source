package com.tritiumstudios.data.policy.usecase

import com.tritiumstudios.data.policy.repository.PolicyRepository

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

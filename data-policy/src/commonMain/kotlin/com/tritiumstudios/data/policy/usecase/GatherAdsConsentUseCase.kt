package com.tritiumstudios.data.policy.usecase

import com.tritiumstudios.data.policy.repository.PolicyRepository

class GatherAdsConsentUseCase(
    private val repository: PolicyRepository
) {
    operator fun invoke(
        activity: Any,
        onFinished: (error: Any?) -> Unit
    ) {
        repository.gatherAdsConsent(activity, onFinished)
    }
}

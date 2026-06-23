package com.tritiumgaming.shared.data.policy.usecase

import com.tritiumgaming.shared.data.policy.repository.PolicyRepository

class IsPrivacyOptionsRequiredUseCase(
    private val repository: PolicyRepository
) {
    operator fun invoke(): Boolean? = repository.isPrivacyOptionsRequired()
}

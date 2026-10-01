package com.tritiumstudios.data.policy.usecase

import com.tritiumstudios.data.policy.repository.PolicyRepository

class IsPrivacyOptionsRequiredUseCase(
    private val repository: PolicyRepository
) {
    operator fun invoke(): Boolean? = repository.isPrivacyOptionsRequired()
}

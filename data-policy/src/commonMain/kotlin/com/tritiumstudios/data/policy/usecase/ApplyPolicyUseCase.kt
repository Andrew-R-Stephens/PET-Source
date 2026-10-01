package com.tritiumstudios.data.policy.usecase

import com.tritiumstudios.data.policy.repository.PolicyRepository
import com.tritiumstudios.data.policy.source.PolicyDatastore.Policy

class ApplyPolicyUseCase(
    private val repository: PolicyRepository
) {
    operator fun invoke(policy: Policy) = repository.applyPolicy(policy)
}

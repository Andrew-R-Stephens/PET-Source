package com.tritiumgaming.shared.data.policy.usecase

import com.tritiumgaming.shared.data.policy.repository.PolicyRepository

class InitFlowPolicyUseCase(
    private val repository: PolicyRepository
) {
    operator fun invoke() = repository.initDatastoreFlow()
}

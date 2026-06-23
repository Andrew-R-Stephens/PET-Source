package com.tritiumgaming.shared.data.policy.usecase

import com.tritiumgaming.shared.data.policy.repository.PolicyRepository

class ShowPrivacyOptionsFormUseCase(
    private val repository: PolicyRepository
) {
    operator fun invoke(activity: Any, onFinished: () -> Unit = {}) =
        repository.showPrivacyOptionsForm(activity, onFinished)
}

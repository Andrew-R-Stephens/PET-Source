package com.tritiumgaming.shared.core.domain.user.usecase.account

import com.tritiumgaming.shared.core.domain.market.user.repository.CredentialsRepository


class DeactivateAccountUseCase(
    private val credentialsRepository: CredentialsRepository
) {
    suspend operator fun invoke(): Result<Boolean> {
        val result = credentialsRepository.deactivateAccount()

        result.exceptionOrNull()?.printStackTrace()

        return result
    }
}

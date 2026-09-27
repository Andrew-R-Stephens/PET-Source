package com.tritiumgaming.core.domain.market.user.usecase

import com.tritiumgaming.core.domain.market.user.repository.CredentialsRepository

class DeactivateAccountUseCase(
    private val credentialsRepository: CredentialsRepository
) {
    suspend operator fun invoke(): Result<Boolean> {
        val result = credentialsRepository.deactivateAccount()

        result.exceptionOrNull()?.printStackTrace()

        return result
    }
}

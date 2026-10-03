package com.tritiumgaming.core.domain.market.user.usecase

import androidx.credentials.GetCustomCredentialOption
import com.tritiumgaming.core.domain.market.user.repository.CredentialsRepository
import com.tritiumgaming.core.common.credentials.SignInOptions

class GetSignInCredentialsUseCase(
    private val credentialsRepository: CredentialsRepository
) {
    suspend operator fun invoke(signInOptions: SignInOptions): Result<GetCustomCredentialOption> =
        credentialsRepository.getSignInCredentials(signInOptions)
}

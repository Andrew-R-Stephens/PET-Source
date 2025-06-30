package com.tritiumgaming.phasmophobiaevidencepicker.core.data.user.usecase

import androidx.credentials.GetCustomCredentialOption
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.user.repository.CredentialsRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.SignInOptions

class GetSignInCredentialsUseCase(
    private val credentialsRepository: CredentialsRepository
) {
    suspend operator fun invoke(signInOptions: SignInOptions): Result<GetCustomCredentialOption> =
        credentialsRepository.getSignInCredentials(signInOptions)
}

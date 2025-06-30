package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.user.source

import androidx.credentials.GetCustomCredentialOption
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.SignInOptions

interface CredentialsDataSource {

    fun getSignInCredentials(
        option: SignInOptions = SignInOptions.SILENT
    ): Result<GetCustomCredentialOption>

    fun signOut(): Result<Boolean>

    suspend fun deactivateAccount(): Result<Boolean>

}
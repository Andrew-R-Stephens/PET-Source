package com.tritiumgaming.core.domain.market.user.source

import androidx.credentials.GetCustomCredentialOption
import com.tritiumgaming.core.common.credentials.SignInOptions

interface CredentialsDataSource {

    fun getSignInCredentials(
        option: SignInOptions = SignInOptions.SILENT
    ): Result<GetCustomCredentialOption>

    fun signOut(): Result<Boolean>

    suspend fun deactivateAccount(): Result<Boolean>

}

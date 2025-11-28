package com.tritiumgaming.shared.core.domain.market.user.source

import androidx.credentials.GetCustomCredentialOption
import com.tritiumgaming.shared.data.account.model.SignInOptions

interface CredentialsDataSource {

    fun getSignInCredentials(
        option: SignInOptions = SignInOptions.SILENT
    ): Result<GetCustomCredentialOption>

    fun signOut(): Result<Boolean>

    suspend fun deactivateAccount(): Result<Boolean>

}
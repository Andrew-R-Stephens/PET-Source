package com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.user.repository

import androidx.credentials.GetCredentialResponse
import androidx.credentials.GetCustomCredentialOption
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountCreditTransaction
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountCredits
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountMarketAgreement
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountPalette
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.AccountTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.user.model.SignInOptions

interface CredentialsRepository {

    suspend fun getSignInCredentials(signInOptions: SignInOptions): Result<GetCustomCredentialOption>

    suspend fun signIn(credentialResponse: GetCredentialResponse): Result<Boolean>

    suspend fun signOut(): Result<Boolean>

    suspend fun deactivateAccount(): Result<Boolean>

}
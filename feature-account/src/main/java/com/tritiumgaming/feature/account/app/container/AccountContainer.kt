package com.tritiumgaming.feature.account.app.container

import com.tritiumgaming.shared.core.domain.market.user.usecase.DeactivateAccountUseCase
import com.tritiumgaming.shared.core.domain.market.user.usecase.GetSignInCredentialsUseCase
import com.tritiumgaming.shared.core.domain.market.user.usecase.SignInAccountUseCase
import com.tritiumgaming.shared.core.domain.market.user.usecase.SignOutAccountUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountCreditsUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountUnlockedPalettesUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountUnlockedTypographiesUseCase

class AccountContainer(
    val getSignInCredentialsUseCase: GetSignInCredentialsUseCase,
    val signInAccountUseCase: SignInAccountUseCase,
    val signOutAccountUseCase: SignOutAccountUseCase,
    val deactivateAccountUseCase: DeactivateAccountUseCase,
    val observeAccountCreditsUseCase: ObserveAccountCreditsUseCase,
    val observeAccountUnlockedPalettesUseCase: ObserveAccountUnlockedPalettesUseCase,
    val observeAccountUnlockedTypographiesUseCase: ObserveAccountUnlockedTypographiesUseCase
)

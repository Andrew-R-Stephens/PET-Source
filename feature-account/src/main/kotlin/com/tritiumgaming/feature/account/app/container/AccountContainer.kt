package com.tritiumgaming.feature.account.app.container

import com.tritiumgaming.shared.core.domain.market.user.usecase.DeactivateAccountUseCase
import com.tritiumgaming.shared.core.domain.market.user.usecase.GetSignInCredentialsUseCase
import com.tritiumgaming.shared.core.domain.market.user.usecase.SignInAccountUseCase
import com.tritiumgaming.shared.core.domain.market.user.usecase.SignOutAccountUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountCreditsUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountUnlockedPalettesUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountUnlockedTypographiesUseCase

class AccountContainer(
    internal val getSignInCredentialsUseCase: GetSignInCredentialsUseCase,
    internal val signInAccountUseCase: SignInAccountUseCase,
    internal val signOutAccountUseCase: SignOutAccountUseCase,
    internal val deactivateAccountUseCase: DeactivateAccountUseCase,
    internal val observeAccountCreditsUseCase: ObserveAccountCreditsUseCase,
    internal val observeAccountUnlockedPalettesUseCase: ObserveAccountUnlockedPalettesUseCase,
    internal val observeAccountUnlockedTypographiesUseCase: ObserveAccountUnlockedTypographiesUseCase
)

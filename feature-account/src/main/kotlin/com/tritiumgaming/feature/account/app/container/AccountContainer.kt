package com.tritiumgaming.feature.account.app.container

import com.tritiumgaming.core.domain.market.user.usecase.DeactivateAccountUseCase
import com.tritiumgaming.core.domain.market.user.usecase.GetSignInCredentialsUseCase
import com.tritiumgaming.core.domain.market.user.usecase.SignInAccountUseCase
import com.tritiumgaming.core.domain.market.user.usecase.SignOutAccountUseCase
import com.tritiumgaming.data.account.usecase.accountcredit.ObserveAccountCreditsUseCase
import com.tritiumgaming.data.account.usecase.accountcredit.ObserveAccountUnlockedPalettesUseCase
import com.tritiumgaming.data.account.usecase.accountcredit.ObserveAccountUnlockedTypographiesUseCase
import com.tritiumgaming.data.account.usecase.accountproperty.ObserveMarketplaceAgreementStateUseCase
import com.tritiumgaming.data.account.usecase.accountproperty.SetMarketplaceAgreementStateUseCase
import com.tritiumgaming.data.usecase.SaveCurrentPaletteUseCase

class AccountContainer(
    internal val getSignInCredentialsUseCase: GetSignInCredentialsUseCase,
    internal val signInAccountUseCase: SignInAccountUseCase,
    internal val signOutAccountUseCase: SignOutAccountUseCase,
    internal val deactivateAccountUseCase: DeactivateAccountUseCase,
    internal val observeAccountCreditsUseCase: ObserveAccountCreditsUseCase,
    internal val observeMarketplaceAgreementStateUseCase: ObserveMarketplaceAgreementStateUseCase,
    internal val setMarketplaceAgreementStateUseCase: SetMarketplaceAgreementStateUseCase,
    internal val observeAccountUnlockedPalettesUseCase: ObserveAccountUnlockedPalettesUseCase,
    internal val observeAccountUnlockedTypographiesUseCase: ObserveAccountUnlockedTypographiesUseCase,
    internal val saveCurrentPaletteUseCase: SaveCurrentPaletteUseCase,
)

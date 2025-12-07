package com.tritiumgaming.feature.marketplace.app.container

import android.content.Context
import com.tritiumgaming.core.common.network.ConnectivityManagerHelper
import com.tritiumgaming.shared.core.domain.market.user.usecase.DeactivateAccountUseCase
import com.tritiumgaming.shared.core.domain.market.user.usecase.GetSignInCredentialsUseCase
import com.tritiumgaming.shared.core.domain.market.user.usecase.SignInAccountUseCase
import com.tritiumgaming.shared.core.domain.market.user.usecase.SignOutAccountUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountCreditsUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountUnlockedPalettesUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountUnlockedTypographiesUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.FindNextAvailablePaletteUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.GetAvailablePalettesUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.GetPaletteByUUIDUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.SaveCurrentPaletteUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.FindNextAvailableTypographyUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetAvailableTypographiesUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetTypographyByUUIDUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.SaveCurrentTypographyUseCase
import kotlinx.coroutines.Dispatchers

class MarketplaceContainer(
    applicationContext: Context,
    val getSignInCredentialsUseCase: GetSignInCredentialsUseCase,
    val signInAccountUseCase: SignInAccountUseCase,
    val signOutAccountUseCase: SignOutAccountUseCase,
    val deactivateAccountUseCase: DeactivateAccountUseCase,
    val observeAccountCreditsUseCase: ObserveAccountCreditsUseCase,
    val observeAccountUnlockedPalettesUseCase: ObserveAccountUnlockedPalettesUseCase,
    val observeAccountUnlockedTypographiesUseCase: ObserveAccountUnlockedTypographiesUseCase,
    val saveCurrentTypographyUseCase: SaveCurrentTypographyUseCase,
    val getAvailableTypographiesUseCase: GetAvailableTypographiesUseCase,
    val getTypographyByUUIDUseCase: GetTypographyByUUIDUseCase,
    val findNextAvailableTypographyUseCase: FindNextAvailableTypographyUseCase,
    val saveCurrentPaletteUseCase: SaveCurrentPaletteUseCase,
    val getAvailablePalettesUseCase: GetAvailablePalettesUseCase,
    val getPaletteByUUIDUseCase: GetPaletteByUUIDUseCase,
    val findNextAvailablePaletteUseCase: FindNextAvailablePaletteUseCase,
) {

}

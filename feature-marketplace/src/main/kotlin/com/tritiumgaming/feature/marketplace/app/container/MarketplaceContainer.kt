package com.tritiumgaming.feature.marketplace.app.container

import android.content.Context
import com.tritiumgaming.shared.core.domain.market.user.usecase.DeactivateAccountUseCase
import com.tritiumgaming.shared.core.domain.market.user.usecase.GetSignInCredentialsUseCase
import com.tritiumgaming.shared.core.domain.market.user.usecase.SignInAccountUseCase
import com.tritiumgaming.shared.core.domain.market.user.usecase.SignOutAccountUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountCreditsUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountUnlockedPalettesUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountUnlockedTypographiesUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.GetNextUnlockedPaletteUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.GetMarketCatalogPalettesUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.GetMarketCatalogPaletteByUUIDUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.SaveCurrentPaletteUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetNextUnlockedTypographyUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetMarketCatalogTypographiesUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetMarketCatalogTypographyByUUIDUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.SaveCurrentTypographyUseCase

class MarketplaceContainer(
    val getSignInCredentialsUseCase: GetSignInCredentialsUseCase,
    val signInAccountUseCase: SignInAccountUseCase,
    val signOutAccountUseCase: SignOutAccountUseCase,
    val deactivateAccountUseCase: DeactivateAccountUseCase,
    val observeAccountCreditsUseCase: ObserveAccountCreditsUseCase,
    val observeAccountUnlockedPalettesUseCase: ObserveAccountUnlockedPalettesUseCase,
    val observeAccountUnlockedTypographiesUseCase: ObserveAccountUnlockedTypographiesUseCase,
    val getMarketCatalogTypographiesUseCase: GetMarketCatalogTypographiesUseCase,
    val getMarketCatalogTypographyByUUIDUseCase: GetMarketCatalogTypographyByUUIDUseCase,
    val getMarketCatalogPalettesUseCase: GetMarketCatalogPalettesUseCase,
    val getMarketCatalogPaletteByUUIDUseCase: GetMarketCatalogPaletteByUUIDUseCase,
    val getNextUnlockedTypographyUseCase: GetNextUnlockedTypographyUseCase,
    val getNextUnlockedPaletteUseCase: GetNextUnlockedPaletteUseCase,
    val saveCurrentTypographyUseCase: SaveCurrentTypographyUseCase,
    val saveCurrentPaletteUseCase: SaveCurrentPaletteUseCase,
)

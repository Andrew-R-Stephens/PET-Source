package com.tritiumgaming.feature.marketplace.app.container

import com.tritiumgaming.shared.core.domain.market.user.usecase.DeactivateAccountUseCase
import com.tritiumgaming.shared.core.domain.market.user.usecase.GetSignInCredentialsUseCase
import com.tritiumgaming.shared.core.domain.market.user.usecase.SignInAccountUseCase
import com.tritiumgaming.shared.core.domain.market.user.usecase.SignOutAccountUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.AddAccountCreditsUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountCreditsUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountUnlockedPalettesUseCase
import com.tritiumgaming.shared.data.account.usecase.accountcredit.ObserveAccountUnlockedTypographiesUseCase
import com.tritiumgaming.shared.data.account.usecase.accountproperty.ObserveMarketplaceAgreementStateUseCase
import com.tritiumgaming.shared.data.account.usecase.accountproperty.SetMarketplaceAgreementStateUseCase
import com.tritiumgaming.shared.data.account.usecase.accounttransaction.PurchaseMarketplaceItemUseCase
import com.tritiumgaming.shared.data.ads.usecase.GetRewardedAdFlowUseCase
import com.tritiumgaming.shared.data.ads.usecase.LoadRewardedAdUseCase
import com.tritiumgaming.shared.data.ads.usecase.ShowRewardedAdUseCase
import com.tritiumgaming.shared.data.market.bundle.usecase.GetMarketCatalogBundlesUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.GetMarketCatalogPaletteByUUIDUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.GetMarketCatalogPalettesUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.GetNextUnlockedPaletteUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetMarketCatalogTypographiesUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetMarketCatalogTypographyByUUIDUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetNextUnlockedTypographyUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SaveCurrentPaletteUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SaveCurrentTypographyUseCase

class MarketplaceContainer(
    internal val getSignInCredentialsUseCase: GetSignInCredentialsUseCase,
    internal val signInAccountUseCase: SignInAccountUseCase,
    internal val signOutAccountUseCase: SignOutAccountUseCase,
    internal val deactivateAccountUseCase: DeactivateAccountUseCase,
    internal val addAccountCreditsUseCase: AddAccountCreditsUseCase,
    internal val observeAccountCreditsUseCase: ObserveAccountCreditsUseCase,
    internal val observeAccountMarketplaceAgreementStateUseCase: ObserveMarketplaceAgreementStateUseCase,
    internal val setAccountMarketplaceAgreementStateUseCase: SetMarketplaceAgreementStateUseCase,
    internal val observeAccountUnlockedPalettesUseCase: ObserveAccountUnlockedPalettesUseCase,
    internal val observeAccountUnlockedTypographiesUseCase: ObserveAccountUnlockedTypographiesUseCase,
    internal val purchaseMarketplaceItemUseCase: PurchaseMarketplaceItemUseCase,
    internal val getMarketCatalogTypographiesUseCase: GetMarketCatalogTypographiesUseCase,
    internal val getMarketCatalogPalettesUseCase: GetMarketCatalogPalettesUseCase,
    internal val getMarketCatalogBundlesUseCase: GetMarketCatalogBundlesUseCase,
    internal val getMarketCatalogTypographyByUUIDUseCase: GetMarketCatalogTypographyByUUIDUseCase,
    internal val getMarketCatalogPaletteByUUIDUseCase: GetMarketCatalogPaletteByUUIDUseCase,
    internal val getNextUnlockedTypographyUseCase: GetNextUnlockedTypographyUseCase,
    internal val getNextUnlockedPaletteUseCase: GetNextUnlockedPaletteUseCase,
    internal val saveCurrentTypographyUseCase: SaveCurrentTypographyUseCase,
    internal val saveCurrentPaletteUseCase: SaveCurrentPaletteUseCase,
    internal val loadRewardedAdUseCase: LoadRewardedAdUseCase,
    internal val showRewardedAdUseCase: ShowRewardedAdUseCase,
    internal val getRewardedAdFlowUseCase: GetRewardedAdFlowUseCase,
)

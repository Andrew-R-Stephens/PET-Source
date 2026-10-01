package com.tritiumgaming.feature.marketplace.app.container

import com.tritiumgaming.core.domain.market.user.usecase.DeactivateAccountUseCase
import com.tritiumgaming.core.domain.market.user.usecase.GetSignInCredentialsUseCase
import com.tritiumgaming.core.domain.market.user.usecase.SignInAccountUseCase
import com.tritiumgaming.core.domain.market.user.usecase.SignOutAccountUseCase
import com.tritiumgaming.data.account.usecase.accountcredit.AddAccountCreditsUseCase
import com.tritiumgaming.data.account.usecase.accountcredit.ObserveAccountCreditsUseCase
import com.tritiumgaming.data.account.usecase.accountcredit.ObserveAccountUnlockedPalettesUseCase
import com.tritiumgaming.data.account.usecase.accountcredit.ObserveAccountUnlockedTypographiesUseCase
import com.tritiumgaming.data.account.usecase.accountproperty.ObserveMarketplaceAgreementStateUseCase
import com.tritiumgaming.data.account.usecase.accountproperty.SetMarketplaceAgreementStateUseCase
import com.tritiumgaming.data.account.usecase.accounttransaction.PurchaseMarketplaceItemUseCase
import com.tritiumgaming.data.ads.usecase.GetRewardedAdFlowUseCase
import com.tritiumgaming.data.ads.usecase.LoadRewardedAdUseCase
import com.tritiumgaming.data.ads.usecase.ShowRewardedAdUseCase
import com.tritiumgaming.data.marketplace.bundle.usecase.GetMarketCatalogBundlesUseCase
import com.tritiumgaming.data.marketplace.palette.usecase.GetMarketCatalogPaletteByUUIDUseCase
import com.tritiumgaming.data.marketplace.palette.usecase.GetMarketCatalogPalettesUseCase
import com.tritiumgaming.data.marketplace.palette.usecase.GetNextUnlockedPaletteUseCase
import com.tritiumgaming.data.marketplace.typography.usecase.GetMarketCatalogTypographiesUseCase
import com.tritiumgaming.data.marketplace.typography.usecase.GetMarketCatalogTypographyByUUIDUseCase
import com.tritiumgaming.data.marketplace.typography.usecase.GetNextUnlockedTypographyUseCase
import com.tritiumgaming.data.usecase.SaveCurrentPaletteUseCase
import com.tritiumgaming.data.usecase.SaveCurrentTypographyUseCase

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

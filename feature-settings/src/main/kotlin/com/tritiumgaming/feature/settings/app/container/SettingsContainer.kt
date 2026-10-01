package com.tritiumgaming.feature.settings.app.container

import com.tritiumgaming.data.account.usecase.accountcredit.ObserveAccountUnlockedPalettesUseCase
import com.tritiumgaming.data.account.usecase.accountcredit.ObserveAccountUnlockedTypographiesUseCase
import com.tritiumgaming.data.marketplace.palette.usecase.GetMarketCatalogPaletteByUUIDUseCase
import com.tritiumgaming.data.marketplace.palette.usecase.GetMarketCatalogPalettesUseCase
import com.tritiumgaming.data.marketplace.palette.usecase.GetNextUnlockedPaletteUseCase
import com.tritiumgaming.data.marketplace.typography.usecase.GetMarketCatalogTypographiesUseCase
import com.tritiumgaming.data.marketplace.typography.usecase.GetMarketCatalogTypographyByUUIDUseCase
import com.tritiumgaming.data.marketplace.typography.usecase.GetNextUnlockedTypographyUseCase
import com.tritiumgaming.data.usecase.InitFlowUserPreferencesUseCase
import com.tritiumgaming.data.usecase.SaveCurrentPaletteUseCase
import com.tritiumgaming.data.usecase.SaveCurrentTypographyUseCase
import com.tritiumgaming.data.usecase.SetAllowCellularDataUseCase
import com.tritiumgaming.data.usecase.SetAllowHuntWarnAudioUseCase
import com.tritiumgaming.data.usecase.SetAllowIntroductionUseCase
import com.tritiumgaming.data.usecase.SetDisableScreenSaverUseCase
import com.tritiumgaming.data.usecase.SetEnableGhostReorderUseCase
import com.tritiumgaming.data.usecase.SetEnableRTLUseCase
import com.tritiumgaming.data.usecase.SetMaxHuntWarnFlashTimeUseCase
import com.tritiumgaming.data.usecase.SetUiDensityTypeUseCase
import com.tritiumstudios.data.policy.usecase.InitFlowPolicyUseCase
import com.tritiumstudios.data.policy.usecase.IsPrivacyOptionsRequiredUseCase
import com.tritiumstudios.data.policy.usecase.SetAllowAnalyticsUseCase
import com.tritiumstudios.data.policy.usecase.SetAllowPersonalizedAdsUseCase
import com.tritiumstudios.data.policy.usecase.ShowPrivacyOptionsFormUseCase

class SettingsContainer(
    //val setupGlobalPreferencesUseCase: SetupUserPreferencesUseCase,
    internal val initFlowGlobalPreferencesUseCase: InitFlowUserPreferencesUseCase,
    internal val setAllowCellularDataUseCase: SetAllowCellularDataUseCase,
    internal val setAllowHuntWarnAudioUseCase: SetAllowHuntWarnAudioUseCase,
    internal val setAllowIntroductionUseCase: SetAllowIntroductionUseCase,
    internal val setDisableScreenSaverUseCase: SetDisableScreenSaverUseCase,
    internal val setEnableGhostReorderUseCase: SetEnableGhostReorderUseCase,
    internal val setEnableRTLUseCase: SetEnableRTLUseCase,
    internal val setUiDensityTypeUseCase: SetUiDensityTypeUseCase,
    internal val setMaxHuntWarnFlashTimeUseCase: SetMaxHuntWarnFlashTimeUseCase,
    // Policy
    internal val initFlowPolicyUseCase: InitFlowPolicyUseCase,
    internal val setAllowAnalyticsUseCase: SetAllowAnalyticsUseCase,
    internal val setAllowPersonalizedAdsUseCase: SetAllowPersonalizedAdsUseCase,
    internal val isPrivacyOptionsRequiredUseCase: IsPrivacyOptionsRequiredUseCase,
    internal val showPrivacyOptionsFormUseCase: ShowPrivacyOptionsFormUseCase,
    // Typographies
    internal val observeAccountUnlockedTypographiesUseCase: ObserveAccountUnlockedTypographiesUseCase,
    internal val saveCurrentTypographyUseCase: SaveCurrentTypographyUseCase,
    internal val getTypographyByUUIDUseCase: GetMarketCatalogTypographyByUUIDUseCase,
    internal val findNextAvailableTypographyUseCase: GetNextUnlockedTypographyUseCase,
    internal val observeAccountUnlockedPalettesUseCase: ObserveAccountUnlockedPalettesUseCase,
    internal val saveCurrentPaletteUseCase: SaveCurrentPaletteUseCase,
    internal val getPaletteByUUIDUseCase: GetMarketCatalogPaletteByUUIDUseCase,
    internal val findNextAvailablePaletteUseCase: GetNextUnlockedPaletteUseCase,
    // Marketplace
    internal val getMarketCatalogPalettesUseCase: GetMarketCatalogPalettesUseCase,
    internal val getMarketCatalogTypographiesUseCase: GetMarketCatalogTypographiesUseCase,
)

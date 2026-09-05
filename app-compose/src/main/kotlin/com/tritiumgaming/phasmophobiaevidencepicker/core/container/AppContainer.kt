package com.tritiumgaming.phasmophobiaevidencepicker.core.container

import com.tritiumgaming.shared.data.market.palette.usecase.GetMarketCatalogPaletteByUUIDUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SaveCurrentPaletteUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetMarketCatalogTypographyByUUIDUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SaveCurrentTypographyUseCase
import com.tritiumgaming.shared.data.ads.usecase.LoadRewardedAdUseCase
import com.tritiumgaming.shared.data.ads.usecase.GetRewardedAdFlowUseCase
import com.tritiumgaming.shared.data.ads.usecase.ShowRewardedAdUseCase
import com.tritiumgaming.shared.data.policy.usecase.ApplyPolicyUseCase
import com.tritiumgaming.shared.data.policy.usecase.GatherAdsConsentUseCase
import com.tritiumgaming.shared.data.policy.usecase.InitFlowPolicyUseCase
import com.tritiumgaming.shared.data.policy.usecase.InitializeMobileAdsUseCase
import com.tritiumgaming.shared.data.preferences.usecase.InitFlowUserPreferencesUseCase

class AppContainer(
    internal val initFlowGlobalPreferencesUseCase: InitFlowUserPreferencesUseCase,
    internal val initFlowPolicyUseCase: InitFlowPolicyUseCase,
    internal val applyPolicyUseCase: ApplyPolicyUseCase,
    internal val gatherAdsConsentUseCase: GatherAdsConsentUseCase,
    internal val initializeMobileAdsUseCase: InitializeMobileAdsUseCase,
    internal val getTypographyByUUIDUseCase: GetMarketCatalogTypographyByUUIDUseCase,
    internal val getPaletteByUUIDUseCase: GetMarketCatalogPaletteByUUIDUseCase,
    internal val saveCurrentPaletteUseCase: SaveCurrentPaletteUseCase,
    internal val saveCurrentTypographyUseCase: SaveCurrentTypographyUseCase,
    internal val loadRewardedAdUseCase: LoadRewardedAdUseCase,
    internal val showRewardedAdUseCase: ShowRewardedAdUseCase,
    internal val getRewardedAdFlowUseCase: GetRewardedAdFlowUseCase
)

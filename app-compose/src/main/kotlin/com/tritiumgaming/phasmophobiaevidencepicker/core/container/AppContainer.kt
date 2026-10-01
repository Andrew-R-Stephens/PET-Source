package com.tritiumgaming.phasmophobiaevidencepicker.core.container

import com.tritiumgaming.data.ads.usecase.GetRewardedAdFlowUseCase
import com.tritiumgaming.data.ads.usecase.LoadRewardedAdUseCase
import com.tritiumgaming.data.ads.usecase.ShowRewardedAdUseCase
import com.tritiumgaming.data.marketplace.palette.usecase.GetMarketCatalogPaletteByUUIDUseCase
import com.tritiumgaming.data.marketplace.typography.usecase.GetMarketCatalogTypographyByUUIDUseCase
import com.tritiumgaming.data.usecase.InitFlowUserPreferencesUseCase
import com.tritiumgaming.data.usecase.SaveCurrentPaletteUseCase
import com.tritiumgaming.data.usecase.SaveCurrentTypographyUseCase
import com.tritiumstudios.data.policy.usecase.ApplyPolicyUseCase
import com.tritiumstudios.data.policy.usecase.GatherAdsConsentUseCase
import com.tritiumstudios.data.policy.usecase.InitFlowPolicyUseCase
import com.tritiumstudios.data.policy.usecase.InitializeMobileAdsUseCase

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

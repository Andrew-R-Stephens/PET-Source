package com.tritiumgaming.phasmophobiaevidencepicker.core.container

import com.tritiumgaming.shared.data.market.palette.usecase.GetMarketCatalogPaletteByUUIDUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetMarketCatalogTypographyByUUIDUseCase
import com.tritiumgaming.shared.data.policy.usecase.ApplyPolicyUseCase
import com.tritiumgaming.shared.data.policy.usecase.GatherAdsConsentUseCase
import com.tritiumgaming.shared.data.policy.usecase.InitializeMobileAdsUseCase
import com.tritiumgaming.shared.data.policy.usecase.InitFlowPolicyUseCase
import com.tritiumgaming.shared.data.preferences.usecase.InitFlowUserPreferencesUseCase

class AppContainer(
    internal val initFlowGlobalPreferencesUseCase: InitFlowUserPreferencesUseCase,
    internal val initFlowPolicyUseCase: InitFlowPolicyUseCase,
    internal val applyPolicyUseCase: ApplyPolicyUseCase,
    internal val gatherAdsConsentUseCase: GatherAdsConsentUseCase,
    internal val initializeMobileAdsUseCase: InitializeMobileAdsUseCase,
    internal val getTypographyByUUIDUseCase: GetMarketCatalogTypographyByUUIDUseCase,
    internal val getPaletteByUUIDUseCase: GetMarketCatalogPaletteByUUIDUseCase
)

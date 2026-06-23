package com.tritiumgaming.phasmophobiaevidencepicker.core.container

import com.tritiumgaming.shared.data.market.palette.usecase.GetMarketCatalogPaletteByUUIDUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetMarketCatalogTypographyByUUIDUseCase
import com.tritiumgaming.shared.data.policy.usecase.InitFlowPolicyUseCase
import com.tritiumgaming.shared.data.preferences.usecase.InitFlowUserPreferencesUseCase

class AppContainer(
    internal val initFlowGlobalPreferencesUseCase: InitFlowUserPreferencesUseCase,
    internal val initFlowPolicyUseCase: InitFlowPolicyUseCase,
    internal val applyPolicyUseCase: com.tritiumgaming.shared.data.policy.usecase.ApplyPolicyUseCase,
    internal val getTypographyByUUIDUseCase: GetMarketCatalogTypographyByUUIDUseCase,
    internal val getPaletteByUUIDUseCase: GetMarketCatalogPaletteByUUIDUseCase,
)

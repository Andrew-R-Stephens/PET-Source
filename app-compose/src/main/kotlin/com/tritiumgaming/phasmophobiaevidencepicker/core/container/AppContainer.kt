package com.tritiumgaming.phasmophobiaevidencepicker.core.container

import com.tritiumgaming.shared.data.market.palette.usecase.GetMarketCatalogPaletteByUUIDUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetMarketCatalogTypographyByUUIDUseCase
import com.tritiumgaming.shared.data.preferences.usecase.InitFlowUserPreferencesUseCase

class AppContainer(
    val initFlowGlobalPreferencesUseCase: InitFlowUserPreferencesUseCase,
    val getTypographyByUUIDUseCase: GetMarketCatalogTypographyByUUIDUseCase,
    val getPaletteByUUIDUseCase: GetMarketCatalogPaletteByUUIDUseCase,
)
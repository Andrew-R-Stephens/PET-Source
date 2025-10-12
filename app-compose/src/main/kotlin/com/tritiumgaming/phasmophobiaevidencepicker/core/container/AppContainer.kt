package com.tritiumgaming.phasmophobiaevidencepicker.core.container

import com.tritiumgaming.shared.core.domain.market.palette.usecase.preference.GetPaletteByUUIDUseCase
import com.tritiumgaming.shared.core.domain.market.palette.usecase.preference.SaveCurrentPaletteUseCase
import com.tritiumgaming.shared.core.domain.market.palette.usecase.setup.InitFlowPaletteUseCase
import com.tritiumgaming.shared.core.domain.market.palette.usecase.setup.InitPaletteDataStoreUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.preference.GetTypographyByUUIDUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.preference.SaveCurrentTypographyUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.setup.InitFlowTypographyUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.setup.InitTypographyDataStoreUseCase

class AppContainer(
    // Typographies
    val initTypographyDataStoreUseCase: InitTypographyDataStoreUseCase,
    val initFlowTypographyUseCase: InitFlowTypographyUseCase,
    val saveCurrentTypographyUseCase: SaveCurrentTypographyUseCase,
    val getTypographyByUUIDUseCase: GetTypographyByUUIDUseCase,
    // Palettes
    val initPaletteDataStoreUseCase: InitPaletteDataStoreUseCase,
    val initFlowPaletteUseCase: InitFlowPaletteUseCase,
    val saveCurrentPaletteUseCase: SaveCurrentPaletteUseCase,
    val getPaletteByUUIDUseCase: GetPaletteByUUIDUseCase,
)
package com.tritiumgaming.phasmophobiaevidencepicker.core.container

import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.setup.InitFlowGlobalPreferencesUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.setup.SetupGlobalPreferencesUseCase
import com.tritiumgaming.shared.core.domain.market.palette.usecase.GetPaletteByUUIDUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.GetTypographyByUUIDUseCase

class AppContainer(
    val setupGlobalPreferencesUseCase: SetupGlobalPreferencesUseCase,
    val initFlowGlobalPreferencesUseCase: InitFlowGlobalPreferencesUseCase,
    val getTypographyByUUIDUseCase: GetTypographyByUUIDUseCase,
    val getPaletteByUUIDUseCase: GetPaletteByUUIDUseCase,
)
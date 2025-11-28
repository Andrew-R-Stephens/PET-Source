package com.tritiumgaming.phasmophobiaevidencepicker.core.container

import com.tritiumgaming.shared.data.market.palette.usecase.GetPaletteByUUIDUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetTypographyByUUIDUseCase
import com.tritiumgaming.shared.data.preferences.usecase.setup.InitFlowGlobalPreferencesUseCase
import com.tritiumgaming.shared.data.preferences.usecase.setup.SetupGlobalPreferencesUseCase

class AppContainer(
    val setupGlobalPreferencesUseCase: SetupGlobalPreferencesUseCase,
    val initFlowGlobalPreferencesUseCase: InitFlowGlobalPreferencesUseCase,
    val getTypographyByUUIDUseCase: GetTypographyByUUIDUseCase,
    val getPaletteByUUIDUseCase: GetPaletteByUUIDUseCase,
)
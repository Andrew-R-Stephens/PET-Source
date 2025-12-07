package com.tritiumgaming.phasmophobiaevidencepicker.core.container

import com.tritiumgaming.shared.data.market.palette.usecase.GetPaletteByUUIDUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetTypographyByUUIDUseCase
import com.tritiumgaming.shared.data.preferences.usecase.InitFlowUserPreferencesUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetupUserPreferencesUseCase

class AppContainer(
    val setupGlobalPreferencesUseCase: SetupUserPreferencesUseCase,
    val initFlowGlobalPreferencesUseCase: InitFlowUserPreferencesUseCase,
    val getTypographyByUUIDUseCase: GetTypographyByUUIDUseCase,
    val getPaletteByUUIDUseCase: GetPaletteByUUIDUseCase,
)
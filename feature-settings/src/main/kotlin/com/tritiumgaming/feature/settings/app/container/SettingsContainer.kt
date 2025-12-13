package com.tritiumgaming.feature.settings.app.container

import com.tritiumgaming.shared.data.market.palette.usecase.FindNextAvailablePaletteUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.GetPaletteByUUIDUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.SaveCurrentPaletteUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.FindNextAvailableTypographyUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetTypographyByUUIDUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.SaveCurrentTypographyUseCase
import com.tritiumgaming.shared.data.preferences.usecase.InitFlowUserPreferencesUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetAllowCellularDataUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetAllowHuntWarnAudioUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetAllowIntroductionUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetDisableScreenSaverUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetEnableGhostReorderUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetEnableRTLUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetMaxHuntWarnFlashTimeUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetupUserPreferencesUseCase

class SettingsContainer(
    val setupGlobalPreferencesUseCase: SetupUserPreferencesUseCase,
    val initFlowGlobalPreferencesUseCase: InitFlowUserPreferencesUseCase,
    val setAllowCellularDataUseCase: SetAllowCellularDataUseCase,
    val setAllowHuntWarnAudioUseCase: SetAllowHuntWarnAudioUseCase,
    val setAllowIntroductionUseCase: SetAllowIntroductionUseCase,
    val setDisableScreenSaverUseCase: SetDisableScreenSaverUseCase,
    val setEnableGhostReorderUseCase: SetEnableGhostReorderUseCase,
    val setEnableRTLUseCase: SetEnableRTLUseCase,
    val setMaxHuntWarnFlashTimeUseCase: SetMaxHuntWarnFlashTimeUseCase,
    val saveCurrentTypographyUseCase: SaveCurrentTypographyUseCase,
    val getTypographyByUUIDUseCase: GetTypographyByUUIDUseCase,
    val findNextAvailableTypographyUseCase: FindNextAvailableTypographyUseCase,
    val saveCurrentPaletteUseCase: SaveCurrentPaletteUseCase,
    val getPaletteByUUIDUseCase: GetPaletteByUUIDUseCase,
    val findNextAvailablePaletteUseCase: FindNextAvailablePaletteUseCase,
)

package com.tritiumgaming.feature.settings.app.container

import com.tritiumgaming.shared.data.market.palette.usecase.FetchUnlockedPalettesUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.GetNextUnlockedPaletteUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.GetMarketCatalogPaletteByUUIDUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.SaveCurrentPaletteUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.FetchUnlockedTypographiesUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetNextUnlockedTypographyUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetMarketCatalogTypographyByUUIDUseCase
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
    val fetchUnlockedTypographiesUseCase: FetchUnlockedTypographiesUseCase,
    val saveCurrentTypographyUseCase: SaveCurrentTypographyUseCase,
    val getTypographyByUUIDUseCase: GetMarketCatalogTypographyByUUIDUseCase,
    val findNextAvailableTypographyUseCase: GetNextUnlockedTypographyUseCase,
    val fetchUnlockedPalettesUseCase: FetchUnlockedPalettesUseCase,
    val saveCurrentPaletteUseCase: SaveCurrentPaletteUseCase,
    val getPaletteByUUIDUseCase: GetMarketCatalogPaletteByUUIDUseCase,
    val findNextAvailablePaletteUseCase: GetNextUnlockedPaletteUseCase,
)

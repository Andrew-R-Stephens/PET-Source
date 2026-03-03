package com.tritiumgaming.feature.settings.app.container

import com.tritiumgaming.shared.data.market.palette.usecase.FetchUnlockedPalettesUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.GetMarketCatalogPaletteByUUIDUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.GetNextUnlockedPaletteUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.SaveCurrentPaletteUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.FetchUnlockedTypographiesUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetMarketCatalogTypographyByUUIDUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetNextUnlockedTypographyUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.SaveCurrentTypographyUseCase
import com.tritiumgaming.shared.data.preferences.usecase.InitFlowUserPreferencesUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetAllowCellularDataUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetAllowHuntWarnAudioUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetAllowIntroductionUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetDisableScreenSaverUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetEnableGhostReorderUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetEnableRTLUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetMaxHuntWarnFlashTimeUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetUiDensityTypeUseCase

class SettingsContainer(
    //val setupGlobalPreferencesUseCase: SetupUserPreferencesUseCase,
    internal val initFlowGlobalPreferencesUseCase: InitFlowUserPreferencesUseCase,
    internal val setAllowCellularDataUseCase: SetAllowCellularDataUseCase,
    internal val setAllowHuntWarnAudioUseCase: SetAllowHuntWarnAudioUseCase,
    internal val setAllowIntroductionUseCase: SetAllowIntroductionUseCase,
    internal val setDisableScreenSaverUseCase: SetDisableScreenSaverUseCase,
    internal val setEnableGhostReorderUseCase: SetEnableGhostReorderUseCase,
    internal val setEnableRTLUseCase: SetEnableRTLUseCase,
    internal val setUiDensityTypeUseCase: SetUiDensityTypeUseCase,
    internal val setMaxHuntWarnFlashTimeUseCase: SetMaxHuntWarnFlashTimeUseCase,
    internal val fetchUnlockedTypographiesUseCase: FetchUnlockedTypographiesUseCase,
    internal val saveCurrentTypographyUseCase: SaveCurrentTypographyUseCase,
    internal val getTypographyByUUIDUseCase: GetMarketCatalogTypographyByUUIDUseCase,
    internal val findNextAvailableTypographyUseCase: GetNextUnlockedTypographyUseCase,
    internal val fetchUnlockedPalettesUseCase: FetchUnlockedPalettesUseCase,
    internal val saveCurrentPaletteUseCase: SaveCurrentPaletteUseCase,
    internal val getPaletteByUUIDUseCase: GetMarketCatalogPaletteByUUIDUseCase,
    internal val findNextAvailablePaletteUseCase: GetNextUnlockedPaletteUseCase,
)

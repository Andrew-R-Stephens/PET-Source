package com.tritiumgaming.feature.settings.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.core.common.util.TimeUtils
import com.tritiumgaming.core.ui.mapper.toPaletteResource
import com.tritiumgaming.core.ui.mapper.toTypographyResource
import com.tritiumgaming.core.ui.theme.palette.ExtendedPalette
import com.tritiumgaming.core.ui.theme.palette.provider.LocalDefaultPalette
import com.tritiumgaming.core.ui.theme.type.ExtendedTypography
import com.tritiumgaming.core.ui.theme.type.LocalDefaultTypography
import com.tritiumgaming.feature.settings.app.container.SettingsContainerProvider
import com.tritiumgaming.feature.settings.ui.components.TypographyUiState
import com.tritiumgaming.shared.data.market.model.IncrementDirection
import com.tritiumgaming.shared.data.market.palette.usecase.FetchUnlockedPalettesUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.GetMarketCatalogPaletteByUUIDUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.GetNextUnlockedPaletteUseCase
import com.tritiumgaming.shared.data.market.palette.usecase.SaveCurrentPaletteUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.FetchUnlockedTypographiesUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetMarketCatalogTypographyByUUIDUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.GetNextUnlockedTypographyUseCase
import com.tritiumgaming.shared.data.market.typography.usecase.SaveCurrentTypographyUseCase
import com.tritiumgaming.shared.data.preferences.DensityType
import com.tritiumgaming.shared.data.preferences.usecase.InitFlowUserPreferencesUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetAllowCellularDataUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetAllowHuntWarnAudioUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetAllowIntroductionUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetDisableScreenSaverUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetEnableGhostReorderUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetEnableRTLUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetMaxHuntWarnFlashTimeUseCase
import com.tritiumgaming.shared.data.preferences.usecase.SetUiDensityTypeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SettingsScreenViewModel(
    // Global Preferences
    private val initFlowGlobalPreferencesUseCase: InitFlowUserPreferencesUseCase,
    private val setAllowCellularDataUseCase: SetAllowCellularDataUseCase,
    private val setAllowIntroductionUseCase: SetAllowIntroductionUseCase,
    private val setDisableScreenSaverUseCase: SetDisableScreenSaverUseCase,
    private val setAllowHuntWarnAudioUseCase: SetAllowHuntWarnAudioUseCase,
    private val setEnableGhostReorderUseCase: SetEnableGhostReorderUseCase,
    private val setEnableRTLUseCase: SetEnableRTLUseCase,
    private val setUiDensityTypeUseCase: SetUiDensityTypeUseCase,
    private val setMaxHuntWarnFlashTimeUseCase: SetMaxHuntWarnFlashTimeUseCase,
    // Typographies
    private val fetchUnlockedTypographiesUseCase: FetchUnlockedTypographiesUseCase,
    private val saveCurrentTypographyUseCase: SaveCurrentTypographyUseCase,
    private val getTypographyByUUIDUseCase: GetMarketCatalogTypographyByUUIDUseCase,
    private val findNextAvailableTypographyUseCase: GetNextUnlockedTypographyUseCase,
    // Palettes
    private val fetchUnlockedPaletteUseCase: FetchUnlockedPalettesUseCase,
    private val saveCurrentPaletteUseCase: SaveCurrentPaletteUseCase,
    private val getPaletteByUUIDUseCase: GetMarketCatalogPaletteByUUIDUseCase,
    private val findNextAvailablePaletteUseCase: GetNextUnlockedPaletteUseCase,
) : ViewModel() {

    private val _unlockedPalettes = MutableStateFlow(UnlockedPalettes())
    private val unlockedPalettes = _unlockedPalettes

    private val _unlockedTypographies = MutableStateFlow(UnlockedTypographies())
    private val unlockedTypographies = _unlockedTypographies

    private val _settingsScreenUiState : StateFlow<SettingsScreenUiState> =
        initFlowGlobalPreferencesUseCase()
            .map { preferences ->
                SettingsScreenUiState(
                    screensaverPreference = preferences.disableScreenSaver,
                    networkPreference = preferences.allowCellularData,
                    huntWarningAudioPreference = preferences.allowHuntWarnAudio,
                    ghostReorderPreference = preferences.enableGhostReorder,
                    introductionPermissionPreference = preferences.allowIntroduction,
                    rTLPreference = preferences.enableRTL,
                    huntWarnDurationPreference = preferences.maxHuntWarnFlashTime,
                    paletteUiState = PaletteUiState(
                        uuid = preferences.paletteUuid,
                        palette = getPaletteByUUID(preferences.paletteUuid)
                    ),
                    typographyUiState = TypographyUiState(
                        uuid = preferences.typographyUuid,
                        typography = getTypographyByUUID(preferences.typographyUuid)
                    ),
                    uiDensityType = preferences.uiDensityType
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = SettingsScreenUiState()
            )
    val settingsScreenUiState = _settingsScreenUiState

    fun setScreenSaverPreference(disable: Boolean) {
        viewModelScope.launch {
            setDisableScreenSaverUseCase(disable)
        }
    }

    fun setNetworkPreference(allow: Boolean) {
        viewModelScope.launch {
            setAllowCellularDataUseCase(allow)
        }
    }

    fun setRTLPreference(enable: Boolean) {
        viewModelScope.launch {
            setEnableRTLUseCase(enable)
        }
    }

    fun setUiDensityPreference(enable: Boolean) {
        viewModelScope.launch {
            val type = if(enable) DensityType.COMPACT else DensityType.COMFORTABLE
            setUiDensityTypeUseCase(type)
        }
    }

    fun setGhostReorderPreference(allow: Boolean) {
        viewModelScope.launch {
            setEnableGhostReorderUseCase(allow)
        }
    }

    fun setIntroductionPermissionPreference(allow: Boolean) {
        viewModelScope.launch {
            setAllowIntroductionUseCase(allow)
        }
    }

    fun setHuntWarnDurationPreference(maxTime: Long) {
        val time = maxTime.coerceIn(TIME_MIN, TIME_MAX)
        viewModelScope.launch {
            setMaxHuntWarnFlashTimeUseCase(time)
        }
    }

    fun setHuntWarningAudioPreference(isAllowed: Boolean) {
        viewModelScope.launch {
            setAllowHuntWarnAudioUseCase(isAllowed)
        }
    }

    /**
     * Palettes
     */

    private suspend fun initUnlockedPalettes() {
        try {
            val result = fetchUnlockedPaletteUseCase()
            val list = result.getOrThrow()

            _unlockedPalettes.update {
                it.copy(
                    palettes = list
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun saveCurrentPaletteUUID(uuid: String) {
        viewModelScope.launch {
            saveCurrentPaletteUseCase(uuid)
        }
    }

    fun setNextAvailablePalette(direction: IncrementDirection) {
        viewModelScope.launch {
            try {
                val result = findNextAvailablePaletteUseCase(
                    palettes = unlockedPalettes.value.palettes,
                    currentUUID = settingsScreenUiState.value.paletteUiState.uuid,
                    direction = direction
                )
                result.getOrNull()?.let { uuid ->
                    saveCurrentPaletteUUID(uuid)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getPaletteByUUID(uuid: String): ExtendedPalette {
        return try {
            getPaletteByUUIDUseCase(uuid).getOrThrow().toPaletteResource()
        } catch (e: Exception) {
            e.printStackTrace()
            LocalDefaultPalette.palette
        }
    }

    /**
     * Typographies
     */

    private suspend fun initUnlockedTypographies() {
        try {
            val result = fetchUnlockedTypographiesUseCase()
            val list = result.getOrThrow()

            _unlockedTypographies.update {
                it.copy(
                    typographies = list
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun saveCurrentTypographyUUID(uuid: String) {
        viewModelScope.launch {
            saveCurrentTypographyUseCase(uuid)
        }
    }

    fun setNextAvailableTypography(direction: IncrementDirection) {
        viewModelScope.launch {
            try {
                val result = findNextAvailableTypographyUseCase(
                    typographies = unlockedTypographies.value.typographies,
                    currentUUID = settingsScreenUiState.value.typographyUiState.uuid,
                    direction = direction
                )
                result.getOrNull()?.let { uuid ->
                    saveCurrentTypographyUUID(uuid)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun getTypographyByUUID(uuid: String): ExtendedTypography {
        return try {
            getTypographyByUUIDUseCase(uuid).getOrThrow()
                .toTypographyResource()
        } catch (e: Exception) {
            e.printStackTrace()
            LocalDefaultTypography.typography
        }
    }

    init {
        Log.d("GlobalPreferencesViewModel", "Initializing...")

        viewModelScope.launch {
            initUnlockedPalettes()
            initUnlockedTypographies()
        }
    }

    companion object {
        internal const val MAX_TIMES_OPENED_TARGET: Int = 5

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
                val container = (application as SettingsContainerProvider).provideSettingsContainer()

                // Global Preferences
                val initFlowGlobalPreferencesUseCase: InitFlowUserPreferencesUseCase = container.initFlowGlobalPreferencesUseCase
                val setAllowCellularDataUseCase: SetAllowCellularDataUseCase = container.setAllowCellularDataUseCase
                val setAllowHuntWarnAudioUseCase: SetAllowHuntWarnAudioUseCase = container.setAllowHuntWarnAudioUseCase
                val setAllowIntroductionUseCase: SetAllowIntroductionUseCase = container.setAllowIntroductionUseCase
                val setDisableScreenSaverUseCase: SetDisableScreenSaverUseCase = container.setDisableScreenSaverUseCase
                val setEnableGhostReorderUseCase: SetEnableGhostReorderUseCase = container.setEnableGhostReorderUseCase
                val setEnableRTLUseCase: SetEnableRTLUseCase = container.setEnableRTLUseCase
                val setUiDensityTypeUseCase: SetUiDensityTypeUseCase = container.setUiDensityTypeUseCase
                val setMaxHuntWarnFlashTimeUseCase: SetMaxHuntWarnFlashTimeUseCase = container.setMaxHuntWarnFlashTimeUseCase
                // Typographies
                val fetchUnlockedTypographiesUseCase: FetchUnlockedTypographiesUseCase = container.fetchUnlockedTypographiesUseCase
                val setCurrentTypographyUseCase: SaveCurrentTypographyUseCase = container.saveCurrentTypographyUseCase
                val getTypographyByUUIDUseCase: GetMarketCatalogTypographyByUUIDUseCase = container.getTypographyByUUIDUseCase
                val findNextAvailableTypographyUseCase: GetNextUnlockedTypographyUseCase = container.findNextAvailableTypographyUseCase
                // Palettes
                val fetchUnlockedPalettesUseCase: FetchUnlockedPalettesUseCase = container.fetchUnlockedPalettesUseCase
                val saveCurrentPaletteUseCase: SaveCurrentPaletteUseCase = container.saveCurrentPaletteUseCase
                val getPaletteByUUIDUseCase: GetMarketCatalogPaletteByUUIDUseCase = container.getPaletteByUUIDUseCase
                val findNextAvailablePaletteUseCase: GetNextUnlockedPaletteUseCase = container.findNextAvailablePaletteUseCase

                SettingsScreenViewModel(
                    // Global Preferences
                    initFlowGlobalPreferencesUseCase = initFlowGlobalPreferencesUseCase,
                    setAllowCellularDataUseCase = setAllowCellularDataUseCase,
                    setAllowHuntWarnAudioUseCase = setAllowHuntWarnAudioUseCase,
                    setAllowIntroductionUseCase = setAllowIntroductionUseCase,
                    setDisableScreenSaverUseCase = setDisableScreenSaverUseCase,
                    setEnableGhostReorderUseCase = setEnableGhostReorderUseCase,
                    setEnableRTLUseCase = setEnableRTLUseCase,
                    setUiDensityTypeUseCase = setUiDensityTypeUseCase,
                    setMaxHuntWarnFlashTimeUseCase = setMaxHuntWarnFlashTimeUseCase,
                    // Typographies
                    fetchUnlockedTypographiesUseCase = fetchUnlockedTypographiesUseCase,
                    saveCurrentTypographyUseCase = setCurrentTypographyUseCase,
                    getTypographyByUUIDUseCase = getTypographyByUUIDUseCase,
                    findNextAvailableTypographyUseCase = findNextAvailableTypographyUseCase,
                    // Palettes
                    fetchUnlockedPaletteUseCase = fetchUnlockedPalettesUseCase,
                    saveCurrentPaletteUseCase = saveCurrentPaletteUseCase,
                    getPaletteByUUIDUseCase = getPaletteByUUIDUseCase,
                    findNextAvailablePaletteUseCase = findNextAvailablePaletteUseCase,
                )
            }
        }

        const val TIME_MIN = 0L
        const val TIME_MAX = 300000L

        const val FOREVER = 300000L

        fun percentAsTime(percent: Float, maxTime: Long = TIME_MAX): Long {
            return TimeUtils.percentAsTime(
                percent = percent, maxTime = maxTime)
        }

        fun timeAsPercent(time: Long, maxTime: Long = TIME_MAX): Float {
            return TimeUtils.timeAsPercent(
                time = time, maxTime = maxTime)
        }
    }

}

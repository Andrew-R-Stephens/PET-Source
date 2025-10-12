package com.tritiumgaming.feature.home.ui.appsettings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.core.common.util.TimeUtils
import com.tritiumgaming.core.ui.mappers.toPaletteResource
import com.tritiumgaming.core.ui.mappers.toTypographyResource
import com.tritiumgaming.core.ui.theme.palette.ExtendedPalette
import com.tritiumgaming.core.ui.theme.palette.LocalDefaultPalette
import com.tritiumgaming.core.ui.theme.type.ExtendedTypography
import com.tritiumgaming.core.ui.theme.type.LocalDefaultTypography
import com.tritiumgaming.feature.home.app.container.HomeContainerProvider
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.SetAllowCellularDataUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.SetAllowHuntWarnAudioUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.SetAllowIntroductionUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.SetDisableScreenSaverUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.SetEnableGhostReorderUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.SetEnableRTLUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.SetMaxHuntWarnFlashTimeUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.setup.InitFlowGlobalPreferencesUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.setup.SetupGlobalPreferencesUseCase
import com.tritiumgaming.shared.core.domain.market.model.IncrementDirection
import com.tritiumgaming.shared.core.domain.market.palette.model.PaletteResources.PaletteType
import com.tritiumgaming.shared.core.domain.market.palette.source.PaletteDatastore
import com.tritiumgaming.shared.core.domain.market.palette.usecase.preference.FindNextAvailablePaletteUseCase
import com.tritiumgaming.shared.core.domain.market.palette.usecase.preference.GetAvailablePalettesUseCase
import com.tritiumgaming.shared.core.domain.market.palette.usecase.preference.GetPaletteByUUIDUseCase
import com.tritiumgaming.shared.core.domain.market.palette.usecase.preference.SaveCurrentPaletteUseCase
import com.tritiumgaming.shared.core.domain.market.palette.usecase.setup.InitFlowPaletteUseCase
import com.tritiumgaming.shared.core.domain.market.palette.usecase.setup.InitPaletteDataStoreUseCase
import com.tritiumgaming.shared.core.domain.market.typography.model.TypographyResources.TypographyType
import com.tritiumgaming.shared.core.domain.market.typography.source.TypographyDatastore
import com.tritiumgaming.shared.core.domain.market.typography.usecase.preference.FindNextAvailableTypographyUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.preference.GetAvailableTypographiesUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.preference.GetTypographyByUUIDUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.preference.SaveCurrentTypographyUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.setup.InitFlowTypographyUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.setup.InitTypographyDataStoreUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SettingsScreenViewModel(
    // Global Preferences
    private val initGlobalPreferencesDataStoreUseCase: SetupGlobalPreferencesUseCase,
    private val initFlowGlobalPreferencesUseCase: InitFlowGlobalPreferencesUseCase,
    private val setAllowCellularDataUseCase: SetAllowCellularDataUseCase,
    private val setAllowIntroductionUseCase: SetAllowIntroductionUseCase,
    private val setDisableScreenSaverUseCase: SetDisableScreenSaverUseCase,
    private val setAllowHuntWarnAudioUseCase: SetAllowHuntWarnAudioUseCase,
    private val setEnableGhostReorderUseCase: SetEnableGhostReorderUseCase,
    private val setEnableRTLUseCase: SetEnableRTLUseCase,
    private val setMaxHuntWarnFlashTimeUseCase: SetMaxHuntWarnFlashTimeUseCase,
    // Typographies
    private val initTypographyDataStoreUseCase: InitTypographyDataStoreUseCase,
    private val initFlowTypographyUseCase: InitFlowTypographyUseCase,
    private val saveCurrentTypographyUseCase: SaveCurrentTypographyUseCase,
    private val getAvailableTypographiesUseCase: GetAvailableTypographiesUseCase,
    private val getTypographyByUUIDUseCase: GetTypographyByUUIDUseCase,
    private val findNextAvailableTypographyUseCase: FindNextAvailableTypographyUseCase,
    // Palettes
    private val initPaletteDataStoreUseCase: InitPaletteDataStoreUseCase,
    private val initFlowPaletteUseCase: InitFlowPaletteUseCase,
    private val saveCurrentPaletteUseCase: SaveCurrentPaletteUseCase,
    private val getAvailablePalettesUseCase: GetAvailablePalettesUseCase,
    private val getPaletteByUUIDUseCase: GetPaletteByUUIDUseCase,
    private val findNextAvailablePaletteUseCase: FindNextAvailablePaletteUseCase,
) : ViewModel() {

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
                    huntWarnDurationPreference = preferences.maxHuntWarnFlashTime
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = SettingsScreenUiState()
            )
    val settingsScreenUiState = _settingsScreenUiState

    private val _screensaverPreference: StateFlow<Boolean> =
        initFlowGlobalPreferencesUseCase()
            .map { it.disableScreenSaver }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = false
            )
    var screensaverPreference = _screensaverPreference
    fun setScreenSaverPreference(disable: Boolean) {
        viewModelScope.launch {
            setDisableScreenSaverUseCase(disable)
        }
    }

    private val _networkPreference: StateFlow<Boolean> =
        initFlowGlobalPreferencesUseCase()
            .map { it.allowCellularData }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = true
            )
    var networkPreference = _networkPreference
    fun setNetworkPreference(allow: Boolean) {
        viewModelScope.launch {
            setAllowCellularDataUseCase(allow)
        }
    }

    private val _rTLPreference: StateFlow<Boolean> =
        initFlowGlobalPreferencesUseCase()
            .map { it.enableRTL }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = true
            )
    val rTLPreference = _rTLPreference
    fun setRTLPreference(enable: Boolean) {
        viewModelScope.launch {
            setEnableRTLUseCase(enable)
        }
    }

    private val _ghostReorderPreference: StateFlow<Boolean> =
        initFlowGlobalPreferencesUseCase()
            .map { it.enableGhostReorder }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = true
            )
    val ghostReorderPreference = _ghostReorderPreference
    fun setGhostReorderPreference(allow: Boolean) {
        viewModelScope.launch {
            setEnableGhostReorderUseCase(allow)
        }
    }

    private val _introductionPermissionPreference: StateFlow<Boolean> =
        initFlowGlobalPreferencesUseCase()
            .map { it.allowIntroduction }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = true
            )
    val introductionPermissionPreference = _introductionPermissionPreference
    fun setIntroductionPermissionPreference(allow: Boolean) {
        viewModelScope.launch {
            setAllowIntroductionUseCase(allow)
        }
    }

    private val _huntWarnDurationPreference: StateFlow<Long> =
        initFlowGlobalPreferencesUseCase()
            .map { it.maxHuntWarnFlashTime }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = FOREVER
            )
    val huntWarnDurationPreference: StateFlow<Long> = _huntWarnDurationPreference
    fun setHuntWarnDurationPreference(maxTime: Long) {
        val time = maxTime.coerceIn(TIME_MIN, TIME_MAX)
        viewModelScope.launch {
            setMaxHuntWarnFlashTimeUseCase(time)
        }
    }

    private val _huntWarningAudioPreference: StateFlow<Boolean> =
        initFlowGlobalPreferencesUseCase()
            .map { it.allowHuntWarnAudio }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = true
            )
    val huntWarningAudioPreference: StateFlow<Boolean> = _huntWarningAudioPreference
    fun setHuntWarningAudioPreference(isAllowed: Boolean) {
        viewModelScope.launch {
            setAllowHuntWarnAudioUseCase(isAllowed)
        }
    }

    /**
     * Palettes
     */

    private val _currentPaletteUUID : StateFlow<PaletteDatastore.PalettePreferences> =
        initFlowPaletteUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = PaletteDatastore.PalettePreferences(
                    uuid = LocalDefaultPalette.uuid
                )
            )
    val currentPaletteUUID = _currentPaletteUUID

    fun saveCurrentPaletteUUID(uuid: String) {
        viewModelScope.launch {
            saveCurrentPaletteUseCase(uuid)
            Log.d("Settings",
                "$uuid -> ${_currentPaletteUUID.value} == ${currentPaletteUUID.value}")
        }
    }

    fun setNextAvailablePalette(direction: IncrementDirection) {
        viewModelScope.launch {
            val uuid = findNextAvailablePaletteUseCase(
                currentPaletteUUID.value.uuid, direction
            )
            saveCurrentPaletteUUID(uuid)
        }
    }

    fun getPaletteByUUID(uuid: String): ExtendedPalette =
        getPaletteByUUIDUseCase(uuid, PaletteType.CLASSIC)
            .toPaletteResource()

    /**
     * Typographies
     */

    private val _currentTypographyUUID : StateFlow<TypographyDatastore.TypographyPreferences> =
        initFlowTypographyUseCase()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = TypographyDatastore.TypographyPreferences(
                    uuid = LocalDefaultTypography.uuid
                )
            )
    val currentTypographyUUID = _currentTypographyUUID

    private fun saveCurrentTypographyUUID(uuid: String) {
        viewModelScope.launch {
            saveCurrentTypographyUseCase(uuid)
            Log.d("Settings",
                "$uuid -> ${_currentTypographyUUID.value} == ${currentTypographyUUID.value}")
        }
    }

    fun setNextAvailableTypography(direction: IncrementDirection) {
        viewModelScope.launch {
            val uuid = findNextAvailableTypographyUseCase(
                currentTypographyUUID.value.uuid, direction
            )
            saveCurrentTypographyUUID(uuid)
        }
    }

    fun getTypographyByUUID(uuid: String): ExtendedTypography =
        getTypographyByUUIDUseCase(uuid, TypographyType.CLASSIC)
            .toTypographyResource()


    private fun initialDataStoreSetupEvent() {
        initGlobalPreferencesDataStoreUseCase()
        initPaletteDataStoreUseCase()
        initTypographyDataStoreUseCase()
    }

    init {
        Log.d("GlobalPreferencesViewModel", "Initializing...")

        initialDataStoreSetupEvent()
    }

    companion object {
        internal const val MAX_TIMES_OPENED_TARGET: Int = 5

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY]
                val container = (application as HomeContainerProvider).provideHomeContainer()

                // Global Preferences
                val setupGlobalPreferencesUseCase: SetupGlobalPreferencesUseCase = container.setupGlobalPreferencesUseCase
                val initFlowGlobalPreferencesUseCase: InitFlowGlobalPreferencesUseCase = container.initFlowGlobalPreferencesUseCase
                val setAllowCellularDataUseCase: SetAllowCellularDataUseCase = container.setAllowCellularDataUseCase
                val setAllowHuntWarnAudioUseCase: SetAllowHuntWarnAudioUseCase = container.setAllowHuntWarnAudioUseCase
                val setAllowIntroductionUseCase: SetAllowIntroductionUseCase = container.setAllowIntroductionUseCase
                val setDisableScreenSaverUseCase: SetDisableScreenSaverUseCase = container.setDisableScreenSaverUseCase
                val setEnableGhostReorderUseCase: SetEnableGhostReorderUseCase = container.setEnableGhostReorderUseCase
                val setEnableRTLUseCase: SetEnableRTLUseCase = container.setEnableRTLUseCase
                val setMaxHuntWarnFlashTimeUseCase: SetMaxHuntWarnFlashTimeUseCase = container.setMaxHuntWarnFlashTimeUseCase
                // Typographies
                val initTypographyDataStoreUseCase: InitTypographyDataStoreUseCase = container.initTypographyDataStoreUseCase
                val initFlowTypographyUseCase: InitFlowTypographyUseCase = container.initFlowTypographyUseCase
                val setCurrentTypographyUseCase: SaveCurrentTypographyUseCase = container.saveCurrentTypographyUseCase
                val getTypographiesUseCase: GetAvailableTypographiesUseCase = container.getAvailableTypographiesUseCase
                val getTypographyByUUIDUseCase: GetTypographyByUUIDUseCase = container.getTypographyByUUIDUseCase
                val findNextAvailableTypographyUseCase: FindNextAvailableTypographyUseCase = container.findNextAvailableTypographyUseCase
                // Palettes
                val initPaletteDataStoreUseCase: InitPaletteDataStoreUseCase = container.initPaletteDataStoreUseCase
                val initFlowPaletteUseCase: InitFlowPaletteUseCase = container.initFlowPaletteUseCase
                val saveCurrentPaletteUseCase: SaveCurrentPaletteUseCase = container.saveCurrentPaletteUseCase
                val getAvailablePalettesUseCase: GetAvailablePalettesUseCase = container.getAvailablePalettesUseCase
                val getPaletteByUUIDUseCase: GetPaletteByUUIDUseCase = container.getPaletteByUUIDUseCase
                val findNextAvailablePaletteUseCase: FindNextAvailablePaletteUseCase = container.findNextAvailablePaletteUseCase

                SettingsScreenViewModel(
                    // Global Preferences
                    initGlobalPreferencesDataStoreUseCase = setupGlobalPreferencesUseCase,
                    initFlowGlobalPreferencesUseCase = initFlowGlobalPreferencesUseCase,
                    setAllowCellularDataUseCase = setAllowCellularDataUseCase,
                    setAllowHuntWarnAudioUseCase = setAllowHuntWarnAudioUseCase,
                    setAllowIntroductionUseCase = setAllowIntroductionUseCase,
                    setDisableScreenSaverUseCase = setDisableScreenSaverUseCase,
                    setEnableGhostReorderUseCase = setEnableGhostReorderUseCase,
                    setEnableRTLUseCase = setEnableRTLUseCase,
                    setMaxHuntWarnFlashTimeUseCase = setMaxHuntWarnFlashTimeUseCase,
                    // Typographies
                    getAvailableTypographiesUseCase = getTypographiesUseCase,
                    initTypographyDataStoreUseCase = initTypographyDataStoreUseCase,
                    initFlowTypographyUseCase = initFlowTypographyUseCase,
                    saveCurrentTypographyUseCase = setCurrentTypographyUseCase,
                    getTypographyByUUIDUseCase = getTypographyByUUIDUseCase,
                    findNextAvailableTypographyUseCase = findNextAvailableTypographyUseCase,
                    // Palettes
                    initPaletteDataStoreUseCase = initPaletteDataStoreUseCase,
                    initFlowPaletteUseCase = initFlowPaletteUseCase,
                    saveCurrentPaletteUseCase = saveCurrentPaletteUseCase,
                    getAvailablePalettesUseCase = getAvailablePalettesUseCase,
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

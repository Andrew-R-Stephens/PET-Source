package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.core.ui.theme.palette.ExtendedPalette
import com.tritiumgaming.core.ui.theme.palette.LocalDefaultPalette
import com.tritiumgaming.core.ui.theme.type.ExtendedTypography
import com.tritiumgaming.core.ui.theme.type.LocalDefaultTypography
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.mappers.toPaletteResource
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.mappers.toStringResource
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.mappers.toTypographyResource
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.SetAllowCellularDataUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.SetAllowHuntWarnAudioUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.SetAllowIntroductionUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.SetDisableScreenSaverUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.SetEnableGhostReorderUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.SetEnableRTLUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.preferences.SetMaxHuntWarnFlashTimeUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.setup.InitFlowGlobalPreferencesUseCase
import com.tritiumgaming.shared.core.domain.globalpreferences.usecase.setup.SetupGlobalPreferencesUseCase
import com.tritiumgaming.shared.core.domain.language.model.LanguageEntity
import com.tritiumgaming.shared.core.domain.language.usecase.GetAvailableLanguagesUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.GetCurrentLanguageUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.InitFlowLanguageUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.LoadCurrentLanguageUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.SaveCurrentLanguageUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.SetDefaultLanguageUseCase
import com.tritiumgaming.shared.core.domain.language.usecase.SetupLanguageUseCase
import com.tritiumgaming.shared.core.domain.market.model.IncrementDirection
import com.tritiumgaming.shared.core.domain.market.palette.model.PaletteResources.PaletteType
import com.tritiumgaming.shared.core.domain.market.palette.usecase.preference.FindNextAvailablePaletteUseCase
import com.tritiumgaming.shared.core.domain.market.palette.usecase.preference.GetAvailablePalettesUseCase
import com.tritiumgaming.shared.core.domain.market.palette.usecase.preference.GetPaletteByUUIDUseCase
import com.tritiumgaming.shared.core.domain.market.palette.usecase.preference.SaveCurrentPaletteUseCase
import com.tritiumgaming.shared.core.domain.market.palette.usecase.setup.InitFlowPaletteUseCase
import com.tritiumgaming.shared.core.domain.market.palette.usecase.setup.SetupPaletteUseCase
import com.tritiumgaming.shared.core.domain.market.typography.model.TypographyResources.TypographyType
import com.tritiumgaming.shared.core.domain.market.typography.usecase.preference.FindNextAvailableTypographyUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.preference.GetAvailableTypographiesUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.preference.GetTypographyByUUIDUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.preference.SaveCurrentTypographyUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.setup.InitFlowTypographyUseCase
import com.tritiumgaming.shared.core.domain.market.typography.usecase.setup.SetupTypographyUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.setup.InitFlowReviewTrackerUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.setup.SetupReviewTrackerUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.status.GetReviewRequestStatusUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.status.LoadReviewRequestStatusUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.status.SetReviewRequestStatusUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.timealive.GetAppTimeAliveUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.timealive.LoadAppTimeAliveUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.timealive.SetAppTimeAliveUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.timesopened.GetAppTimesOpenedUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.timesopened.LoadAppTimesOpenedUseCase
import com.tritiumgaming.shared.core.domain.reviewtracker.usecase.timesopened.SetAppTimesOpenedUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

class GlobalPreferencesViewModel(
    // Global Preferences
    private val initGlobalPreferencesDataStoreUseCase: SetupGlobalPreferencesUseCase,
    private val initFlowGlobalPreferencesUseCase: InitFlowGlobalPreferencesUseCase,
    private val setAllowCellularDataUseCase: SetAllowCellularDataUseCase,
    private val setAllowHuntWarnAudioUseCase: SetAllowHuntWarnAudioUseCase,
    private val setAllowIntroductionUseCase: SetAllowIntroductionUseCase,
    private val setDisableScreenSaverUseCase: SetDisableScreenSaverUseCase,
    private val setEnableGhostReorderUseCase: SetEnableGhostReorderUseCase,
    private val setEnableRTLUseCase: SetEnableRTLUseCase,
    private val setMaxHuntWarnFlashTimeUseCase: SetMaxHuntWarnFlashTimeUseCase,
    // Languages
    private val getAvailableLanguagesUseCase: GetAvailableLanguagesUseCase,
    private val setDefaultLanguageUseCase: SetDefaultLanguageUseCase,
    private val initLanguageDataStoreUseCase: SetupLanguageUseCase,
    private val initFlowLanguageUseCase: InitFlowLanguageUseCase,
    private val saveCurrentLanguageUseCase: SaveCurrentLanguageUseCase,
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val loadCurrentLanguageUseCase: LoadCurrentLanguageUseCase,
    // Typographies
    private val initTypographyDataStoreUseCase: SetupTypographyUseCase,
    private val initFlowTypographyUseCase: InitFlowTypographyUseCase,
    private val saveCurrentTypographyUseCase: SaveCurrentTypographyUseCase,
    private val getAvailableTypographiesUseCase: GetAvailableTypographiesUseCase,
    private val getTypographyByUUIDUseCase: GetTypographyByUUIDUseCase,
    private val findNextAvailableTypographyUseCase: FindNextAvailableTypographyUseCase,
    // Palettes
    private val initPaletteDataStoreUseCase: SetupPaletteUseCase,
    private val initFlowPaletteUseCase: InitFlowPaletteUseCase,
    private val saveCurrentPaletteUseCase: SaveCurrentPaletteUseCase,
    private val getAvailablePalettesUseCase: GetAvailablePalettesUseCase,
    private val getPaletteByUUIDUseCase: GetPaletteByUUIDUseCase,
    private val findNextAvailablePaletteUseCase: FindNextAvailablePaletteUseCase,
    // Review Tracker
    private val initReviewTrackerDataStoreUseCase: SetupReviewTrackerUseCase,
    private val initFlowReviewTrackerUseCase: InitFlowReviewTrackerUseCase,
    private val setReviewRequestStatusUseCase: SetReviewRequestStatusUseCase,
    private val getReviewRequestStatusUseCase: GetReviewRequestStatusUseCase,
    private val loadReviewRequestStatusUseCase: LoadReviewRequestStatusUseCase,
    private val setAppTimeAliveUseCase: SetAppTimeAliveUseCase,
    private val getAppTimeAliveUseCase: GetAppTimeAliveUseCase,
    private val loadAppTimeAliveUseCase: LoadAppTimeAliveUseCase,
    private val setAppTimesOpenedUseCase: SetAppTimesOpenedUseCase,
    private val getAppTimesOpenedUseCase: GetAppTimesOpenedUseCase,
    private val loadAppTimesOpenedUseCase: LoadAppTimesOpenedUseCase
) : ViewModel() {

    val defaultTypographyUUID: String = LocalDefaultTypography.uuid
    val defaultPaletteUUID: String = LocalDefaultPalette.uuid

    /**
     * Global Preferences
     */
    // Generic settings
    private val _screensaverPreference = MutableStateFlow(false)
    var screensaverPreference = _screensaverPreference.asStateFlow()
    fun setScreenSaverPreference(disable: Boolean) {
        _screensaverPreference.update { disable }
        viewModelScope.launch {
            setDisableScreenSaverUseCase(disable)
        }
    }

    private val _networkPreference = MutableStateFlow(true)
    var networkPreference = _networkPreference.asStateFlow()
    fun setNetworkPreference(allow: Boolean) {
        _networkPreference.update { allow }
        viewModelScope.launch {
            setAllowCellularDataUseCase(allow)
        }
    }

    private val _rTLPreference = MutableStateFlow(false)
    val rTLPreference = _rTLPreference.asStateFlow()
    fun setRTLPreference(enable: Boolean) {
        _rTLPreference.update { enable }
        viewModelScope.launch {
            setEnableRTLUseCase(enable)
        }
    }

    private val _ghostReorderPreference = MutableStateFlow(true)
    val ghostReorderPreference = _ghostReorderPreference.asStateFlow()
    fun setGhostReorderPreference(allow: Boolean) {
        _ghostReorderPreference.update { allow }
        viewModelScope.launch {
            setEnableGhostReorderUseCase(allow)
        }
    }

    private val _introductionPermissionPreference = MutableStateFlow(true)
    val introductionPermissionPreference = _introductionPermissionPreference.asStateFlow()
    fun setIntroductionPermissionPreference(allow: Boolean) {
        _introductionPermissionPreference.update { allow }
        viewModelScope.launch {
            setAllowIntroductionUseCase(allow)
        }
    }

    private val _huntWarnDurationPreference = MutableStateFlow(FOREVER)
    val huntWarnDurationPreference: StateFlow<Long> = _huntWarnDurationPreference.asStateFlow()
    fun setHuntWarnDurationPreference(maxTime: Long) {
        val time = maxTime.coerceIn(MIN_TIME, MAX_TIME)
        _huntWarnDurationPreference.update { time }
        viewModelScope.launch {
            setMaxHuntWarnFlashTimeUseCase(time)
        }
    }

    private val _huntWarningAudioPreference = MutableStateFlow(true)
    val huntWarningAudioPreference: StateFlow<Boolean> = _huntWarningAudioPreference.asStateFlow()
    fun setHuntWarningAudioPreference(isAllowed: Boolean) {
        _huntWarningAudioPreference.update { isAllowed }
        viewModelScope.launch {
            setAllowHuntWarnAudioUseCase(isAllowed)
        }
    }

    /**
     * Language
     */
    val languageList: List<LanguageEntity>
        get() {
            var languages: List<LanguageEntity> = emptyList()

            try {
                languages = getAvailableLanguagesUseCase().getOrThrow()

                setDefaultLanguageUseCase(
                    localeLanguage = Locale.getDefault().language,
                    languages = languages
                ).getOrNull()?.let { language ->
                    DEFAULT_LANGUAGE = language.code
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return languages
        }

    private var _currentLanguageCode = MutableStateFlow(DEFAULT_LANGUAGE)
    val currentLanguageCode = _currentLanguageCode.asStateFlow()
    fun setCurrentLanguageCode(languageCode: String) {
        viewModelScope.launch {
            saveCurrentLanguageUseCase(languageCode)
        }
        _currentLanguageCode.update { languageCode }
    }
    fun loadCurrentLanguageCode() {
        viewModelScope.launch {
            loadCurrentLanguageUseCase()
        }
    }

    /**
     * ReviewTracker
     */
    private val _wasReviewRequested = MutableStateFlow(false)
    val wasReviewRequested = _wasReviewRequested.asStateFlow()
    fun setReviewRequestStatus(status: Boolean) {
        viewModelScope.launch {
            setReviewRequestStatusUseCase(status)
        }
        _wasReviewRequested.value = status
    }
    fun loadReviewRequestStatus() {
        viewModelScope.launch {
            loadReviewRequestStatusUseCase()
        }
    }

    private val _appTimeActive = MutableStateFlow(0L)
    val appTimeActive = _appTimeActive.asStateFlow()
    fun setAppTimeActive(time: Long) {
        viewModelScope.launch {
            setAppTimeAliveUseCase(time)
        }
        _appTimeActive.value = time
    }
    fun loadAppTimeActive() {
        viewModelScope.launch {
            loadAppTimeAliveUseCase()
        }
    }
    fun getAppTimeActive() {
        _appTimeActive.update { getAppTimeAliveUseCase() }
    }

    private val _timesOpened = MutableStateFlow(0)
    val timesOpened = _timesOpened.asStateFlow()
    fun incrementAppTimesOpened() {
        viewModelScope.launch {
            _timesOpened.update { it + 1 }
            setAppTimesOpenedUseCase(timesOpened.value)
        }
    }
    fun setTimesOpened(count: Int) {
        _timesOpened.update { count }
    }
    fun loadAppTimesOpened() {
        viewModelScope.launch {
            loadAppTimesOpenedUseCase
        }
    }
    fun getAppTimesOpened() {
        _timesOpened.update { getAppTimesOpenedUseCase() }
    }

    fun canRequestReview(): Boolean {
        return (!wasReviewRequested.value) && (timesOpened.value >= MAX_TIMES_OPENED_TARGET)
    }

    fun canShowReviewButton(): Boolean {
        return (wasReviewRequested.value) && (timesOpened.value >= MAX_TIMES_OPENED_TARGET)
    }

    /**
     * Palettes
     */

    private val _currentPaletteUUID : MutableStateFlow<String> =
        MutableStateFlow(defaultPaletteUUID)
    val currentPaletteUUID = _currentPaletteUUID.asStateFlow()
    fun setCurrentPaletteUUID(uuid: String) {
        _currentPaletteUUID.update { uuid }
        viewModelScope.launch {
            saveCurrentPaletteUseCase(uuid)
            Log.d("Settings", "$uuid -> ${_currentPaletteUUID.value} == ${currentPaletteUUID.value}")
        }
    }

    fun getPaletteByUUID(uuid: String): ExtendedPalette =
        getPaletteByUUIDUseCase(uuid, PaletteType.CLASSIC).toPaletteResource()


    fun setNextAvailablePalette(direction: IncrementDirection) {
        viewModelScope.launch {
            val uuid = findNextAvailablePaletteUseCase(currentPaletteUUID.value, direction)
            setCurrentPaletteUUID(uuid)
        }
    }

    /**
     * Typographies
     */

    private val _currentTypographyUUID : MutableStateFlow<String> =
        MutableStateFlow(defaultTypographyUUID)
    val currentTypographyUUID = _currentTypographyUUID.asStateFlow()
    fun setCurrentTypographyUUID(uuid: String) {
        _currentTypographyUUID.update { uuid }
        viewModelScope.launch {
            saveCurrentTypographyUseCase(uuid)
        }
    }

    fun getTypographyByUUID(uuid: String): ExtendedTypography =
        getTypographyByUUIDUseCase(uuid, TypographyType.CLASSIC).toTypographyResource()

    fun setNextAvailableTypography(direction: IncrementDirection) {
        viewModelScope.launch {
            val uuid = findNextAvailableTypographyUseCase(currentTypographyUUID.value, direction)
            setCurrentTypographyUUID(uuid)
        }
    }

    private fun initialDataStoreSetupEvent() {
        initGlobalPreferencesDataStoreUseCase()
        initReviewTrackerDataStoreUseCase()
        initLanguageDataStoreUseCase()
        initPaletteDataStoreUseCase()
        initTypographyDataStoreUseCase()
    }

    init {
        Log.d("GlobalPreferencesViewModel", "Initializing...")

        initialDataStoreSetupEvent()

        // Review Tracker
        viewModelScope.launch {
            initFlowReviewTrackerUseCase { preferences ->
                _wasReviewRequested.update { preferences.allowRequestReview }
                _appTimeActive.update { preferences.timeActive }
                _timesOpened.update { preferences.timesOpened }
            }
        }

        // Global Preferences
        viewModelScope.launch {
            initFlowGlobalPreferencesUseCase { preferences ->
                _screensaverPreference.update { preferences.disableScreenSaver }
                _networkPreference.update { preferences.allowCellularData }
                _huntWarningAudioPreference.update { preferences.allowHuntWarnAudio }
                _ghostReorderPreference.update { preferences.enableGhostReorder }
                _introductionPermissionPreference.update { preferences.allowIntroduction }
                _rTLPreference.update { preferences.enableRTL }
                _huntWarnDurationPreference.update { preferences.maxHuntWarnFlashTime }
            }
        }

        // Language
        viewModelScope.launch {
            initFlowLanguageUseCase { preferences ->
                _currentLanguageCode.update { preferences.languageCode }
                Log.d("Language", "Collected Language Code: ${preferences.languageCode}")

                //Define the language used whenever the saved language changes
                AppCompatDelegate.setApplicationLocales(
                    LocaleListCompat.create(
                        Locale.forLanguageTag(preferences.languageCode)
                    )
                )
            }
        }

        // Palette
        viewModelScope.launch {
            initFlowPaletteUseCase { preferences ->
                _currentPaletteUUID.update {
                    preferences.uuid.ifBlank { defaultPaletteUUID }
                }
                Log.d("Palette", "Collecting from flow:\n\tID -> ${currentPaletteUUID.value}")
            }
        }

        // Typography
        viewModelScope.launch {
            initFlowTypographyUseCase { preferences ->
                _currentTypographyUUID.update {
                    preferences.uuid.ifBlank { defaultTypographyUUID }
                }
                Log.d("Typography", "Collecting from flow:\n\tID -> ${currentTypographyUUID.value}")
            }
        }

    }

    companion object {
        internal const val MAX_TIMES_OPENED_TARGET: Int = 5

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val appKeyContainer =
                    (this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY] as PETApplication).coreContainer

                // Global Preferences
                val setupGlobalPreferencesUseCase: SetupGlobalPreferencesUseCase = appKeyContainer.setupGlobalPreferencesUseCase
                val initFlowGlobalPreferencesUseCase: InitFlowGlobalPreferencesUseCase = appKeyContainer.initFlowGlobalPreferencesUseCase
                val setAllowCellularDataUseCase: SetAllowCellularDataUseCase = appKeyContainer.setAllowCellularDataUseCase
                val setAllowHuntWarnAudioUseCase: SetAllowHuntWarnAudioUseCase = appKeyContainer.setAllowHuntWarnAudioUseCase
                val setAllowIntroductionUseCase: SetAllowIntroductionUseCase = appKeyContainer.setAllowIntroductionUseCase
                val setDisableScreenSaverUseCase: SetDisableScreenSaverUseCase = appKeyContainer.setDisableScreenSaverUseCase
                val setEnableGhostReorderUseCase: SetEnableGhostReorderUseCase = appKeyContainer.setEnableGhostReorderUseCase
                val setEnableRTLUseCase: SetEnableRTLUseCase = appKeyContainer.setEnableRTLUseCase
                val setMaxHuntWarnFlashTimeUseCase: SetMaxHuntWarnFlashTimeUseCase = appKeyContainer.setMaxHuntWarnFlashTimeUseCase
                // Languages
                val getLanguagesUseCase: GetAvailableLanguagesUseCase = appKeyContainer.getLanguagesUseCase
                val setDefaultLanguageUseCase: SetDefaultLanguageUseCase = appKeyContainer.setDefaultLanguageUseCase
                val setupLanguageUseCase: SetupLanguageUseCase = appKeyContainer.setupLanguageUseCase
                val initializeLanguageUseCase: InitFlowLanguageUseCase = appKeyContainer.initializeLanguageUseCase
                val setCurrentLanguageUseCase: SaveCurrentLanguageUseCase = appKeyContainer.setCurrentLanguageUseCase
                val getCurrentLanguageUseCase: GetCurrentLanguageUseCase = appKeyContainer.getCurrentLanguageUseCase
                val loadCurrentLanguageUseCase: LoadCurrentLanguageUseCase = appKeyContainer.loadCurrentLanguageUseCase
                // Typographies
                val setupTypographyUseCase: SetupTypographyUseCase = appKeyContainer.setupTypographyUseCase
                val initFlowTypographyUseCase: InitFlowTypographyUseCase = appKeyContainer.initFlowTypographyUseCase
                val setCurrentTypographyUseCase: SaveCurrentTypographyUseCase = appKeyContainer.saveCurrentTypographyUseCase
                val getTypographiesUseCase: GetAvailableTypographiesUseCase = appKeyContainer.getAvailableTypographiesUseCase
                val getTypographyByUUIDUseCase: GetTypographyByUUIDUseCase = appKeyContainer.getTypographyByUUIDUseCase
                val findNextAvailableTypographyUseCase: FindNextAvailableTypographyUseCase = appKeyContainer.findNextAvailableTypographyUseCase
                // Palettes
                val setupPaletteUseCase: SetupPaletteUseCase = appKeyContainer.setupPaletteUseCase
                val initFlowPaletteUseCase: InitFlowPaletteUseCase = appKeyContainer.initFlowPaletteUseCase
                val saveCurrentPaletteUseCase: SaveCurrentPaletteUseCase = appKeyContainer.saveCurrentPaletteUseCase
                val getAvailablePalettesUseCase: GetAvailablePalettesUseCase = appKeyContainer.getAvailablePalettesUseCase
                val getPaletteByUUIDUseCase: GetPaletteByUUIDUseCase = appKeyContainer.getPaletteByUUIDUseCase
                val findNextAvailablePaletteUseCase: FindNextAvailablePaletteUseCase = appKeyContainer.findNextAvailablePaletteUseCase
                // Reviews
                val setupReviewTrackerUseCase: SetupReviewTrackerUseCase = appKeyContainer.setupReviewTrackerUseCase
                val initializeReviewTrackerUseCase: InitFlowReviewTrackerUseCase = appKeyContainer.initializeReviewTrackerUseCase
                val setReviewRequestStatusUseCase: SetReviewRequestStatusUseCase = appKeyContainer.setReviewRequestStatusUseCase
                val getReviewRequestStatusUseCase: GetReviewRequestStatusUseCase = appKeyContainer.getReviewRequestStatusUseCase
                val loadReviewRequestStatusUseCase: LoadReviewRequestStatusUseCase = appKeyContainer.loadReviewRequestStatusUseCase
                val setAppTimeAliveUseCase: SetAppTimeAliveUseCase = appKeyContainer.setAppTimeAliveUseCase
                val getAppTimeAliveUseCase: GetAppTimeAliveUseCase = appKeyContainer.getAppTimeAliveUseCase
                val loadAppTimeAliveUseCase: LoadAppTimeAliveUseCase = appKeyContainer.loadAppTimeAliveUseCase
                val setAppTimesOpenedUseCase: SetAppTimesOpenedUseCase = appKeyContainer.setAppTimesOpenedUseCase
                val getAppTimesOpenedUseCase: GetAppTimesOpenedUseCase = appKeyContainer.getAppTimesOpenedUseCase
                val loadAppTimesOpenedUseCase: LoadAppTimesOpenedUseCase = appKeyContainer.loadAppTimesOpenedUseCase

                GlobalPreferencesViewModel(
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
                    // Languages
                    getAvailableLanguagesUseCase = getLanguagesUseCase,
                    setDefaultLanguageUseCase = setDefaultLanguageUseCase,
                    initLanguageDataStoreUseCase = setupLanguageUseCase,
                    initFlowLanguageUseCase = initializeLanguageUseCase,
                    saveCurrentLanguageUseCase = setCurrentLanguageUseCase,
                    getCurrentLanguageUseCase = getCurrentLanguageUseCase,
                    loadCurrentLanguageUseCase = loadCurrentLanguageUseCase,
                    // Typographies
                    getAvailableTypographiesUseCase = getTypographiesUseCase,
                    initTypographyDataStoreUseCase = setupTypographyUseCase,
                    initFlowTypographyUseCase = initFlowTypographyUseCase,
                    saveCurrentTypographyUseCase = setCurrentTypographyUseCase,
                    getTypographyByUUIDUseCase = getTypographyByUUIDUseCase,
                    findNextAvailableTypographyUseCase = findNextAvailableTypographyUseCase,
                    // Palettes
                    initPaletteDataStoreUseCase = setupPaletteUseCase,
                    initFlowPaletteUseCase = initFlowPaletteUseCase,
                    saveCurrentPaletteUseCase = saveCurrentPaletteUseCase,
                    getAvailablePalettesUseCase = getAvailablePalettesUseCase,
                    getPaletteByUUIDUseCase = getPaletteByUUIDUseCase,
                    findNextAvailablePaletteUseCase = findNextAvailablePaletteUseCase,
                    // Reviews
                    initReviewTrackerDataStoreUseCase = setupReviewTrackerUseCase,
                    initFlowReviewTrackerUseCase = initializeReviewTrackerUseCase,
                    setReviewRequestStatusUseCase = setReviewRequestStatusUseCase,
                    getReviewRequestStatusUseCase = getReviewRequestStatusUseCase,
                    loadReviewRequestStatusUseCase = loadReviewRequestStatusUseCase,
                    setAppTimeAliveUseCase = setAppTimeAliveUseCase,
                    getAppTimeAliveUseCase = getAppTimeAliveUseCase,
                    loadAppTimeAliveUseCase = loadAppTimeAliveUseCase,
                    setAppTimesOpenedUseCase = setAppTimesOpenedUseCase,
                    getAppTimesOpenedUseCase = getAppTimesOpenedUseCase,
                    loadAppTimesOpenedUseCase = loadAppTimesOpenedUseCase,
                )
            }
        }

        const val MIN_TIME = 0L
        const val MAX_TIME = 300000L

        const val FOREVER = 300000L

        private var DEFAULT_LANGUAGE = Locale.ENGLISH.language

    }

}

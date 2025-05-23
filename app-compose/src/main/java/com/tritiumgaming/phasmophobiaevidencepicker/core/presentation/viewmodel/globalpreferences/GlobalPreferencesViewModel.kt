package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.setup.InitFlowGlobalPreferencesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.preferences.SetAllowCellularDataUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.preferences.SetAllowHuntWarnAudioUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.preferences.SetAllowIntroductionUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.preferences.SetDisableScreenSaverUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.preferences.SetEnableGhostReorderUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.preferences.SetEnableRTLUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.preferences.SetMaxHuntWarnFlashTimeUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.globalpreferences.usecase.setup.SetupGlobalPreferencesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageRepository.Companion.DEFAULT_LANGUAGE
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase.GetCurrentLanguageUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase.GetLanguagesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase.InitFlowLanguageUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase.LoadCurrentLanguageUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase.SetCurrentLanguageUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase.SetupLanguageUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.market.model.IncrementDirection
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.setup.InitFlowReviewTrackerUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.setup.SetupReviewTrackerUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.status.GetReviewRequestStatusUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.status.LoadReviewRequestStatusUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.status.SetReviewRequestStatusUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timealive.GetAppTimeAliveUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timealive.LoadAppTimeAliveUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timealive.SetAppTimeAliveUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timesopened.GetAppTimesOpenedUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timesopened.LoadAppTimesOpenedUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timesopened.SetAppTimesOpenedUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.helpers.theme.PaletteManager
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.helpers.theme.TypographyManager
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.sanity.warning.PhaseHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

class GlobalPreferencesViewModel(
    // Global Preferences
    private val setupGlobalPreferencesUseCase: SetupGlobalPreferencesUseCase,
    private val initFlowGlobalPreferencesUseCase: InitFlowGlobalPreferencesUseCase,
    private val setAllowCellularDataUseCase: SetAllowCellularDataUseCase,
    private val setAllowHuntWarnAudioUseCase: SetAllowHuntWarnAudioUseCase,
    private val setAllowIntroductionUseCase: SetAllowIntroductionUseCase,
    private val setDisableScreenSaverUseCase: SetDisableScreenSaverUseCase,
    private val setEnableGhostReorderUseCase: SetEnableGhostReorderUseCase,
    private val setEnableRTLUseCase: SetEnableRTLUseCase,
    private val setMaxHuntWarnFlashTimeUseCase: SetMaxHuntWarnFlashTimeUseCase,
    // Languages
    private val getLanguagesUseCase: GetLanguagesUseCase,
    private val setupLanguageUseCase: SetupLanguageUseCase,
    private val initFlowLanguageUseCase: InitFlowLanguageUseCase,
    private val setCurrentLanguageUseCase: SetCurrentLanguageUseCase,
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val loadCurrentLanguageUseCase: LoadCurrentLanguageUseCase,
    // Typographies
    private val typographyManager: TypographyManager,
    // Palettes
    private val paletteManager: PaletteManager,
    // Review Tracker
    private val setupReviewTrackerUseCase: SetupReviewTrackerUseCase,
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

    private fun initialSetupEvent() {
        setupGlobalPreferencesUseCase()
        setupReviewTrackerUseCase()
        setupLanguageUseCase()
        paletteManager.initialSetupEvent()
        typographyManager.initialSetupEvent()
    }

    init {
        Log.d("GlobalPreferencesViewModel", "Initializing...")

        initialSetupEvent()

        viewModelScope.launch {
            initFlowReviewTrackerUseCase().collect { preferences ->
                _wasReviewRequested.update { preferences.allowRequestReview }
                _appTimeActive.update { preferences.timeActive }
                _timesOpened.update { preferences.timesOpened }
            }
        }

        viewModelScope.launch {
            initFlowGlobalPreferencesUseCase().collect { preferences ->
                _screensaverPreference.update { preferences.disableScreenSaver }
                _networkPreference.update { preferences.allowCellularData }
                _huntWarningAudioPreference.update { preferences.allowHuntWarnAudio }
                _ghostReorderPreference.update { preferences.enableGhostReorder }
                _introductionPermissionPreference.update { preferences.allowIntroduction }
                _rTLPreference.update { preferences.enableRTL }
                _huntWarnDurationPreference.update { preferences.maxHuntWarnFlashTime }
            }
        }
        viewModelScope.launch {
            initFlowLanguageUseCase().collect { preferences ->
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

        viewModelScope.launch {
            paletteManager.initFlow()
        }
        viewModelScope.launch {
            typographyManager.initFlow()
        }

        viewModelScope.launch {
            paletteManager.fetchPalettes()
        }
        viewModelScope.launch {
            typographyManager.fetchTypographies()
        }
    }

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

    private val _huntWarnDurationPreference = MutableStateFlow(PhaseHandler.Time.FOREVER)
    val huntWarnDurationPreference: StateFlow<Long> = _huntWarnDurationPreference.asStateFlow()
    fun setHuntWarnDurationPreference(maxTime: Long) {
        val time = maxTime.coerceIn(PhaseHandler.Time.MIN_TIME, PhaseHandler.Time.MAX_TIME)
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
    val languageList = getLanguagesUseCase()

    private var _currentLanguageCode = MutableStateFlow(DEFAULT_LANGUAGE)
    val currentLanguageCode = _currentLanguageCode.asStateFlow()
    fun setCurrentLanguageCode(languageCode: String) {
        viewModelScope.launch {
            setCurrentLanguageUseCase(languageCode)
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
    val currentPaletteUUID = paletteManager.currentUUID
    fun setCurrentPaletteUUID(uuid: String) {
        viewModelScope.launch {
            paletteManager.setCurrentUUID(uuid)
        }
    }
    fun setNextAvailablePalette(direction: IncrementDirection) {
        return setCurrentPaletteUUID(paletteManager.findNextAvailable(direction))
    }
    fun getPaletteByUUID(uuid: String) = paletteManager.getPaletteByUUID(uuid)

    /**
     * Typographies
     */
    val currentTypographyUUID = typographyManager.currentUUID
    fun setCurrentTypographyUUID(uuid: String) {
        viewModelScope.launch {
            typographyManager.setCurrentUUID(uuid)
        }
    }
    fun setNextAvailableTypography(direction: IncrementDirection) {
        return setCurrentTypographyUUID(typographyManager.findNextAvailable(direction))
    }
    fun getTypographyByUUID(uuid: String) = typographyManager.getTypographyByUUID(uuid)

    class GlobalPreferencesFactory(
        private val setupGlobalPreferencesUseCase: SetupGlobalPreferencesUseCase,
        private val initFlowGlobalPreferencesUseCase: InitFlowGlobalPreferencesUseCase,
        private val setAllowCellularDataUseCase: SetAllowCellularDataUseCase,
        private val setAllowHuntWarnAudioUseCase: SetAllowHuntWarnAudioUseCase,
        private val setAllowIntroductionUseCase: SetAllowIntroductionUseCase,
        private val setDisableScreenSaverUseCase: SetDisableScreenSaverUseCase,
        private val setEnableGhostReorderUseCase: SetEnableGhostReorderUseCase,
        private val setEnableRTLUseCase: SetEnableRTLUseCase,
        private val setMaxHuntWarnFlashTimeUseCase: SetMaxHuntWarnFlashTimeUseCase,
        private val getLanguagesUseCase: GetLanguagesUseCase,
        private val setupLanguageUseCase: SetupLanguageUseCase,
        private val initializeLanguageUseCase: InitFlowLanguageUseCase,
        private val setCurrentLanguageUseCase: SetCurrentLanguageUseCase,
        private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
        private val loadCurrentLanguageUseCase: LoadCurrentLanguageUseCase,
        private val typographyManager: TypographyManager,
        private val paletteManager: PaletteManager,
        private val setupReviewTrackerUseCase: SetupReviewTrackerUseCase,
        private val initializeReviewTrackerUseCase: InitFlowReviewTrackerUseCase,
        private val getReviewRequestStatusUseCase: GetReviewRequestStatusUseCase,
        private val loadReviewRequestStatusUseCase: LoadReviewRequestStatusUseCase,
        private val setReviewRequestStatusUseCase: SetReviewRequestStatusUseCase,
        private val setAppTimeAliveUseCase: SetAppTimeAliveUseCase,
        private val getAppTimeAliveUseCase: GetAppTimeAliveUseCase,
        private val loadAppTimeAliveUseCase: LoadAppTimeAliveUseCase,
        private val setAppTimesOpenedUseCase: SetAppTimesOpenedUseCase,
        private val getAppTimesOpenedUseCase: GetAppTimesOpenedUseCase,
        private val loadAppTimesOpenedUseCase: LoadAppTimesOpenedUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GlobalPreferencesViewModel::class.java)) {
                val viewModel =
                    GlobalPreferencesViewModel(
                        setupGlobalPreferencesUseCase = setupGlobalPreferencesUseCase,
                        initFlowGlobalPreferencesUseCase = initFlowGlobalPreferencesUseCase,
                        setAllowCellularDataUseCase = setAllowCellularDataUseCase,
                        setAllowHuntWarnAudioUseCase = setAllowHuntWarnAudioUseCase,
                        setAllowIntroductionUseCase = setAllowIntroductionUseCase,
                        setDisableScreenSaverUseCase = setDisableScreenSaverUseCase,
                        setEnableGhostReorderUseCase = setEnableGhostReorderUseCase,
                        setEnableRTLUseCase = setEnableRTLUseCase,
                        setMaxHuntWarnFlashTimeUseCase = setMaxHuntWarnFlashTimeUseCase,
                        getLanguagesUseCase = getLanguagesUseCase,
                        setupLanguageUseCase = setupLanguageUseCase,
                        initFlowLanguageUseCase = initializeLanguageUseCase,
                        setCurrentLanguageUseCase = setCurrentLanguageUseCase,
                        getCurrentLanguageUseCase = getCurrentLanguageUseCase,
                        loadCurrentLanguageUseCase = loadCurrentLanguageUseCase,
                        typographyManager = typographyManager,
                        paletteManager = paletteManager,
                        setupReviewTrackerUseCase = setupReviewTrackerUseCase,
                        initFlowReviewTrackerUseCase = initializeReviewTrackerUseCase,
                        getReviewRequestStatusUseCase = getReviewRequestStatusUseCase,
                        loadReviewRequestStatusUseCase = loadReviewRequestStatusUseCase,
                        setReviewRequestStatusUseCase = setReviewRequestStatusUseCase,
                        setAppTimeAliveUseCase = setAppTimeAliveUseCase,
                        getAppTimeAliveUseCase = getAppTimeAliveUseCase,
                        loadAppTimeAliveUseCase = loadAppTimeAliveUseCase,
                        setAppTimesOpenedUseCase = setAppTimesOpenedUseCase,
                        getAppTimesOpenedUseCase = getAppTimesOpenedUseCase,
                        loadAppTimesOpenedUseCase = loadAppTimesOpenedUseCase
                    )
                /*viewModel.init()*/
                @Suppress("UNCHECKED_CAST")
                return (viewModel)as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {
        internal const val MAX_TIMES_OPENED_TARGET: Int = 5

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val appKeyContainer =
                    (this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY] as PETApplication).coreContainer

                val setupGlobalPreferencesUseCase: SetupGlobalPreferencesUseCase = appKeyContainer.setupGlobalPreferencesUseCase
                val initFlowGlobalPreferencesUseCase: InitFlowGlobalPreferencesUseCase = appKeyContainer.initFlowGlobalPreferencesUseCase
                val setAllowCellularDataUseCase: SetAllowCellularDataUseCase = appKeyContainer.setAllowCellularDataUseCase
                val setAllowHuntWarnAudioUseCase: SetAllowHuntWarnAudioUseCase = appKeyContainer.setAllowHuntWarnAudioUseCase
                val setAllowIntroductionUseCase: SetAllowIntroductionUseCase = appKeyContainer.setAllowIntroductionUseCase
                val setDisableScreenSaverUseCase: SetDisableScreenSaverUseCase = appKeyContainer.setDisableScreenSaverUseCase
                val setEnableGhostReorderUseCase: SetEnableGhostReorderUseCase = appKeyContainer.setEnableGhostReorderUseCase
                val setEnableRTLUseCase: SetEnableRTLUseCase = appKeyContainer.setEnableRTLUseCase
                val setMaxHuntWarnFlashTimeUseCase: SetMaxHuntWarnFlashTimeUseCase = appKeyContainer.setMaxHuntWarnFlashTimeUseCase
                val getLanguagesUseCase: GetLanguagesUseCase = appKeyContainer.getLanguagesUseCase
                val setupLanguageUseCase: SetupLanguageUseCase = appKeyContainer.setupLanguageUseCase
                val initializeLanguageUseCase: InitFlowLanguageUseCase = appKeyContainer.initializeLanguageUseCase
                val setCurrentLanguageUseCase: SetCurrentLanguageUseCase = appKeyContainer.setCurrentLanguageUseCase
                val getCurrentLanguageUseCase: GetCurrentLanguageUseCase = appKeyContainer.getCurrentLanguageUseCase
                val loadCurrentLanguageUseCase: LoadCurrentLanguageUseCase = appKeyContainer.loadCurrentLanguageUseCase
                val typographyManager: TypographyManager = appKeyContainer.typographyManager
                val paletteManager: PaletteManager = appKeyContainer.paletteManager
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
                    setupGlobalPreferencesUseCase = setupGlobalPreferencesUseCase,
                    initFlowGlobalPreferencesUseCase = initFlowGlobalPreferencesUseCase,
                    setAllowCellularDataUseCase = setAllowCellularDataUseCase,
                    setAllowHuntWarnAudioUseCase = setAllowHuntWarnAudioUseCase,
                    setAllowIntroductionUseCase = setAllowIntroductionUseCase,
                    setDisableScreenSaverUseCase = setDisableScreenSaverUseCase,
                    setEnableGhostReorderUseCase = setEnableGhostReorderUseCase,
                    setEnableRTLUseCase = setEnableRTLUseCase,
                    setMaxHuntWarnFlashTimeUseCase = setMaxHuntWarnFlashTimeUseCase,
                    getLanguagesUseCase = getLanguagesUseCase,
                    setupLanguageUseCase = setupLanguageUseCase,
                    initFlowLanguageUseCase = initializeLanguageUseCase,
                    setCurrentLanguageUseCase = setCurrentLanguageUseCase,
                    getCurrentLanguageUseCase = getCurrentLanguageUseCase,
                    loadCurrentLanguageUseCase = loadCurrentLanguageUseCase,
                    typographyManager = typographyManager,
                    paletteManager = paletteManager,
                    setupReviewTrackerUseCase = setupReviewTrackerUseCase,
                    initFlowReviewTrackerUseCase = initializeReviewTrackerUseCase,
                    setReviewRequestStatusUseCase = setReviewRequestStatusUseCase,
                    getReviewRequestStatusUseCase = getReviewRequestStatusUseCase,
                    loadReviewRequestStatusUseCase = loadReviewRequestStatusUseCase,
                    setAppTimeAliveUseCase = setAppTimeAliveUseCase,
                    getAppTimeAliveUseCase = getAppTimeAliveUseCase,
                    loadAppTimeAliveUseCase = loadAppTimeAliveUseCase,
                    setAppTimesOpenedUseCase = setAppTimesOpenedUseCase,
                    getAppTimesOpenedUseCase = getAppTimesOpenedUseCase,
                    loadAppTimesOpenedUseCase = loadAppTimesOpenedUseCase
                )
            }
        }
    }

}

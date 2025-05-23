package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
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
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.helpers.globalpreferences.GlobalPreferencesManager
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.helpers.theme.PaletteManager
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.helpers.theme.TypographyManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale

class GlobalPreferencesViewModel(
    // Global Preferences
    private val globalPreferencesManager: GlobalPreferencesManager,
    // Languages
    private val getLanguagesUseCase: GetLanguagesUseCase,
    private val setupLanguageUseCase: SetupLanguageUseCase,
    private val initializeLanguageUseCase: InitFlowLanguageUseCase,
    private val setCurrentLanguageUseCase: SetCurrentLanguageUseCase,
    private val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
    private val loadCurrentLanguageUseCase: LoadCurrentLanguageUseCase,
    // Typographies
    private val typographyManager: TypographyManager,
    // Palettes
    private val paletteManager: PaletteManager,
    // Review Tracker
    private val setupReviewTrackerUseCase: SetupReviewTrackerUseCase,
    private val initializeReviewTrackerUseCase: InitFlowReviewTrackerUseCase,
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
        globalPreferencesManager.initialSetupEvent()
        setupReviewTrackerUseCase()
        setupLanguageUseCase()
        paletteManager.initialSetupEvent()
        typographyManager.initialSetupEvent()
    }

    init {
        Log.d("GlobalPreferencesViewModel", "Initializing...")

        initialSetupEvent()

        viewModelScope.launch {
            initializeReviewTrackerUseCase().collect { preferences ->
                _wasReviewRequested.update {
                    preferences.allowRequestReview
                }
                _appTimeActive.update {
                    preferences.timeActive
                }
                _timesOpened.update {
                    preferences.timesOpened
                }
            }
        }

        viewModelScope.launch {
            globalPreferencesManager.initFlow()
        }
        viewModelScope.launch {
            initializeLanguageUseCase().collect { preferences ->
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

    /**
     * Global Preferences
     */
    val screenSaverPreference = globalPreferencesManager.disableScreensaver
    fun setScreenSaverPreference(disable: Boolean) {
        viewModelScope.launch {
            globalPreferencesManager.setDisableScreenSaver(disable)
        }
    }

    val networkPreference = globalPreferencesManager.allowCellularData
    fun setNetworkPreference(allow: Boolean) {
        viewModelScope.launch {
            globalPreferencesManager.setAllowCellularData(allow)
        }
    }

    val rTLPreference = globalPreferencesManager.enableRTL
    fun setRTLPreference(enable: Boolean) {
        viewModelScope.launch {
            globalPreferencesManager.setEnableRTL(enable)
        }
    }

    val huntWarningAudioPreference = globalPreferencesManager.allowHuntWarnAudio
    fun setHuntWarningAudioPreference(enable: Boolean) {
        viewModelScope.launch {
            globalPreferencesManager.setAllowHuntWarnAudio(enable)
        }
    }

    val huntWarnTimeoutPreference = globalPreferencesManager.maxHuntWarnFlashTime
    fun setHuntWarnTimeoutPreference(timeout: Long) {
        viewModelScope.launch {
            globalPreferencesManager.setHuntWarnFlashTimeMax(timeout)
        }
    }

    val ghostReorderPreference = globalPreferencesManager.enableGhostReorder
    fun setGhostReorderPreference(allow: Boolean) {
        viewModelScope.launch {
            globalPreferencesManager.setEnableGhostReorder(allow)
        }
    }

    class GlobalPreferencesFactory(
        val globalPreferencesManager: GlobalPreferencesManager,
        val getLanguagesUseCase: GetLanguagesUseCase,
        val setupLanguageUseCase: SetupLanguageUseCase,
        val initializeLanguageUseCase: InitFlowLanguageUseCase,
        val setCurrentLanguageUseCase: SetCurrentLanguageUseCase,
        val getCurrentLanguageUseCase: GetCurrentLanguageUseCase,
        val loadCurrentLanguageUseCase: LoadCurrentLanguageUseCase,
        val typographyManager: TypographyManager,
        val paletteManager: PaletteManager,
        val setupReviewTrackerUseCase: SetupReviewTrackerUseCase,
        val initializeReviewTrackerUseCase: InitFlowReviewTrackerUseCase,
        val getReviewRequestStatusUseCase: GetReviewRequestStatusUseCase,
        val loadReviewRequestStatusUseCase: LoadReviewRequestStatusUseCase,
        val setReviewRequestStatusUseCase: SetReviewRequestStatusUseCase,
        val setAppTimeAliveUseCase: SetAppTimeAliveUseCase,
        val getAppTimeAliveUseCase: GetAppTimeAliveUseCase,
        val loadAppTimeAliveUseCase: LoadAppTimeAliveUseCase,
        val setAppTimesOpenedUseCase: SetAppTimesOpenedUseCase,
        val getAppTimesOpenedUseCase: GetAppTimesOpenedUseCase,
        val loadAppTimesOpenedUseCase: LoadAppTimesOpenedUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GlobalPreferencesViewModel::class.java)) {
                val viewModel =
                    GlobalPreferencesViewModel(
                        globalPreferencesManager = globalPreferencesManager,
                        getLanguagesUseCase = getLanguagesUseCase,
                        setupLanguageUseCase = setupLanguageUseCase,
                        initializeLanguageUseCase = initializeLanguageUseCase,
                        setCurrentLanguageUseCase = setCurrentLanguageUseCase,
                        getCurrentLanguageUseCase = getCurrentLanguageUseCase,
                        loadCurrentLanguageUseCase = loadCurrentLanguageUseCase,
                        typographyManager = typographyManager,
                        paletteManager = paletteManager,
                        setupReviewTrackerUseCase = setupReviewTrackerUseCase,
                        initializeReviewTrackerUseCase = initializeReviewTrackerUseCase,
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

                val globalPreferencesManager: GlobalPreferencesManager = appKeyContainer.globalPreferencesManager
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
                    globalPreferencesManager = globalPreferencesManager,
                    getLanguagesUseCase = getLanguagesUseCase,
                    setupLanguageUseCase = setupLanguageUseCase,
                    initializeLanguageUseCase = initializeLanguageUseCase,
                    setCurrentLanguageUseCase = setCurrentLanguageUseCase,
                    getCurrentLanguageUseCase = getCurrentLanguageUseCase,
                    loadCurrentLanguageUseCase = loadCurrentLanguageUseCase,
                    typographyManager = typographyManager,
                    paletteManager = paletteManager,
                    setupReviewTrackerUseCase = setupReviewTrackerUseCase,
                    initializeReviewTrackerUseCase = initializeReviewTrackerUseCase,
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

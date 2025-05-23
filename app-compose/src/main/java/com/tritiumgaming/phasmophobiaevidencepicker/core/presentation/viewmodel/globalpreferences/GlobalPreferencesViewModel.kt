package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.reviewtracker.source.datastore.ReviewTrackerDatastoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageRepository.Companion.DEFAULT_LANGUAGE
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.usecase.GetLanguagesUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.market.model.IncrementDirection
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.repository.ReviewTrackerDatastoreRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.source.ReviewTrackerDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.setup.InitFlowReviewTrackerUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.setup.SetupReviewTrackerUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.status.GetReviewRequestStatusUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.status.LoadReviewRequestStatusUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timealive.SetAppTimeAliveUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.status.SetReviewRequestStatusUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timealive.GetAppTimeAliveUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timealive.LoadAppTimeAliveUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timesopened.GetAppTimesOpenedUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timesopened.LoadAppTimesOpenedUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.reviewtracker.usecase.timesopened.SetAppTimesOpenedUseCase
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.helpers.globalpreferences.GlobalPreferencesManager
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.helpers.language.LanguageManager
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.helpers.theme.PaletteManager
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.helpers.theme.TypographyManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class GlobalPreferencesViewModel(
    // Global Preferences
    private val globalPreferencesManager: GlobalPreferencesManager,
    // Languages
    private val languageManager: LanguageManager,
    private val getLanguagesUseCase: GetLanguagesUseCase,
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
        languageManager.initialSetupEvent()
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
            languageManager.initFlow()
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
    suspend fun setCurrentLanguageCode(languageCode: String) {
        _currentLanguageCode.update { languageCode }
        viewModelScope.launch {
            //TODO setCurrentLanguageCodeUseCase(languageCode)
        }
    }

    /*val currentLanguageCode = languageManager.currentLanguageCode
    fun setCurrentLanguageCode(languageCode: String) {
        viewModelScope.launch {
            languageManager.setCurrentLanguageCode(languageCode)
        }
    }*/

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
    fun getReviewRequestStatus() {
        _wasReviewRequested.value = getReviewRequestStatusUseCase()
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

    val currentPaletteUUID = paletteManager.currentUUID
    fun setCurrentPaletteUUID(uuid: String) {
        viewModelScope.launch {
            paletteManager.setCurrentUUID(uuid)
        }
    }
    fun setNextAvailablePalette(direction: IncrementDirection) {
        return setCurrentPaletteUUID(paletteManager.findNextAvailable(direction))
    }

    val currentTypographyUUID = typographyManager.currentUUID
    fun setCurrentTypographyUUID(uuid: String) {
        viewModelScope.launch {
            typographyManager.setCurrentUUID(uuid)
        }
    }
    fun setNextAvailableTypography(direction: IncrementDirection) {
        return setCurrentTypographyUUID(typographyManager.findNextAvailable(direction))
    }

    fun getPaletteByUUID(uuid: String) = paletteManager.getPaletteByUUID(uuid)
    fun getTypographyByUUID(uuid: String) = typographyManager.getTypographyByUUID(uuid)

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
        val languageManager: LanguageManager,
        val getLanguagesUseCase: GetLanguagesUseCase,
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
                        languageManager = languageManager,
                        getLanguagesUseCase = getLanguagesUseCase,
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
                val languageManager: LanguageManager = appKeyContainer.languageManager
                val getLanguagesUseCase: GetLanguagesUseCase = appKeyContainer.getLanguagesUseCase
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
                    languageManager = languageManager,
                    getLanguagesUseCase = getLanguagesUseCase,
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

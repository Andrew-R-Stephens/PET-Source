package com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.app.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.GlobalPreferencesRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.LanguageRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.PaletteRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.ReviewTrackingRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.TypographyRepository
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.controllers.GlobalPreferencesHandler
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.controllers.LanguageHandler
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.controllers.theming.subsets.PaletteHandler
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.controllers.theming.subsets.TypographyHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GlobalPreferencesViewModel(
    globalPreferencesRepository: GlobalPreferencesRepository,
    private val reviewTrackingRepository: ReviewTrackingRepository,
    typographyRepository: TypographyRepository,
    private val paletteRepository: PaletteRepository,
    languageRepository: LanguageRepository
) : ViewModel() {

    private val globalPreferencesHandler: GlobalPreferencesHandler =
        GlobalPreferencesHandler(globalPreferencesRepository)
    private val languageHandler: LanguageHandler = LanguageHandler(languageRepository)
    val typographyHandler: TypographyHandler = TypographyHandler(typographyRepository)
    val paletteHandler: PaletteHandler = PaletteHandler(paletteRepository)

    private fun initialSetupEvent() {
        globalPreferencesHandler.initialSetupEvent()
        languageHandler.initialSetupEvent()
        paletteHandler.initialSetupEvent()
        typographyHandler.initialSetupEvent()
    }

    init {
        Log.d("GlobalPreferencesViewModel", "Initializing...")

        initialSetupEvent()

        viewModelScope.launch {
            globalPreferencesHandler.initFlow()
        }
        viewModelScope.launch {
            languageHandler.initFlow()
        }
        viewModelScope.launch {
            paletteHandler.initFlow()
        }
        viewModelScope.launch {
            typographyHandler.initFlow()
        }

        viewModelScope.launch(Dispatchers.IO) {
            Log.d("Firebase", "Attempting fetch 1")
            paletteRepository.fetchAllThemes()
            paletteRepository.fetchAllBundles()
        }
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("Firebase", "Attempting fetch 1")
            typographyRepository.fetchAllTypographies()
        }
    }

    val languageList = languageHandler.languageList

    val currentPaletteUUID = paletteHandler.currentUUID
    fun setCurrentPaletteUUID(uuid: String) {
        viewModelScope.launch {
            paletteHandler.setCurrentUUID(uuid)
        }
    }

    val currentTypographyUUID = typographyHandler.currentUUID
    fun setCurrentTypographyUUID(uuid: String) {
        viewModelScope.launch {
            typographyHandler.setCurrentUUID(uuid)
        }
    }

    val currentLanguageCode = languageHandler.currentLanguageCode
    private fun setCurrentLanguageCode(languageCode: String) {
        viewModelScope.launch {
            languageHandler.setCurrentLanguageCode(languageCode)
        }
    }

    fun getCurrentLanguageIndex() = languageHandler.getCurrentLanguageIndex()
    fun setCurrentLanguageCodeByIndex(index: Int) {
        viewModelScope.launch {
            languageHandler.setCurrentLanguageCodeByIndex(index)
        }
    }

    val screenSaverPreference = globalPreferencesHandler.disableScreensaver
    fun setScreenSaverPreference(disable: Boolean) {
        viewModelScope.launch {
            globalPreferencesHandler.setDisableScreenSaver(disable)
        }
    }

    val networkPreference = globalPreferencesHandler.allowCellularData
    fun setNetworkPreference(allow: Boolean) {
        viewModelScope.launch {
            globalPreferencesHandler.setAllowCellularData(allow)
        }
    }

    val rTLPreference = globalPreferencesHandler.enableRTL
    fun setRTLPreference(enable: Boolean) {
        viewModelScope.launch {
            globalPreferencesHandler.setEnableRTL(enable)
        }
    }

    val huntWarningAudioPreference = globalPreferencesHandler.allowHuntWarnAudio
    fun setHuntWarningAudioPreference(enable: Boolean) {
        viewModelScope.launch {
            globalPreferencesHandler.setAllowHuntWarnAudio(enable)
        }
    }

    val getHuntWarnTimeoutPreference = globalPreferencesHandler.maxHuntWarnFlashTime
    fun setHuntWarnTimeoutPreference(timeout: Long) {
        viewModelScope.launch {
            globalPreferencesHandler.setHuntWarnFlashTimeMax(timeout)
        }
    }

    val ghostReorderPreference = globalPreferencesHandler.enableGhostReorder
    fun setGhostReorderPreference(allow: Boolean) {
        viewModelScope.launch {
            globalPreferencesHandler.setEnableGhostReorder(allow)
        }
    }

    val appTimesOpened = reviewTrackingRepository.timesOpened
    fun incrementAppTimesOpened() {
        viewModelScope.launch {
            reviewTrackingRepository.incrementTimesOpened()
        }
    }

    val wasRequested = reviewTrackingRepository.wasRequested
    fun setWasRequested(status: Boolean) {
        viewModelScope.launch {
            reviewTrackingRepository.setWasRequested(status)
        }
    }

    fun saveSelectedTypographyUUID() {
        viewModelScope.launch {
            typographyHandler.saveCurrentUUID()
        }
    }

    val canRequestReview: Boolean
        get() { return reviewTrackingRepository.canRequestReview() }

    val canShowReviewButton: Boolean
        get() { return reviewTrackingRepository.canShowReviewButton() }

    class GlobalPreferencesFactory(
        private val globalPreferencesRepository: GlobalPreferencesRepository,
        private val reviewTrackingRepository: ReviewTrackingRepository,
        private val fontThemeRepository: TypographyRepository,
        private val colorThemeRepository: PaletteRepository,
        private val languageRepository: LanguageRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GlobalPreferencesViewModel::class.java)) {
                val viewModel =
                    GlobalPreferencesViewModel(
                        globalPreferencesRepository,
                        reviewTrackingRepository,
                        fontThemeRepository,
                        colorThemeRepository,
                        languageRepository
                    )
                /*viewModel.init()*/
                @Suppress("UNCHECKED_CAST")
                return (viewModel)as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    companion object {

        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val appKeyContainer = (this[APPLICATION_KEY] as PETApplication).container

                val globalPreferencesRepository = appKeyContainer.globalPreferencesRepository
                val reviewTrackingRepository: ReviewTrackingRepository = appKeyContainer.reviewTrackingRepository
                val fontThemeRepository: TypographyRepository = appKeyContainer.typographyRepository
                val colorThemeRepository: PaletteRepository = appKeyContainer.paletteRepository
                val languageRepository: LanguageRepository = appKeyContainer.languageRepository

                GlobalPreferencesViewModel(
                    globalPreferencesRepository = globalPreferencesRepository,
                    reviewTrackingRepository = reviewTrackingRepository,
                    typographyRepository = fontThemeRepository,
                    paletteRepository = colorThemeRepository,
                    languageRepository = languageRepository
                )
            }
        }
    }
}

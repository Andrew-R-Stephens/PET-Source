package com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.ui.compose.pet.application.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.data.controllers.GlobalPreferencesHandler
import com.tritiumgaming.phasmophobiaevidencepicker.data.controllers.LanguageHandler
import com.tritiumgaming.phasmophobiaevidencepicker.data.controllers.theming.subsets.ColorThemeHandler
import com.tritiumgaming.phasmophobiaevidencepicker.data.controllers.theming.subsets.FontThemeHandler
import com.tritiumgaming.phasmophobiaevidencepicker.data.model.reviews.ReviewTrackingRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.ColorThemeRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.FontThemeRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.GlobalPreferencesRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.LanguageRepository
import kotlinx.coroutines.launch

class GlobalPreferencesViewModel(
    globalPreferencesRepository: GlobalPreferencesRepository,
    private val reviewTrackingRepository: ReviewTrackingRepository,
    fontThemeRepository: FontThemeRepository,
    colorThemeRepository: ColorThemeRepository,
    languageRepository: LanguageRepository
) : ViewModel() {

    private val globalPreferencesHandler: GlobalPreferencesHandler =
        GlobalPreferencesHandler(globalPreferencesRepository)
    private val languageHandler: LanguageHandler = LanguageHandler(languageRepository)
    val fontThemeHandler: FontThemeHandler = FontThemeHandler(fontThemeRepository)
    val colorThemeHandler: ColorThemeHandler = ColorThemeHandler(colorThemeRepository)

    private fun initialSetupEvent() {
        globalPreferencesHandler.initialSetupEvent()
        languageHandler.initialSetupEvent()
        fontThemeHandler.initialSetupEvent()
        colorThemeHandler.initialSetupEvent()
    }

    val languageList = languageRepository.languageList

    fun init() {
        Log.d("GlobalPreferencesViewModel", "Initializing...")

        initialSetupEvent()

        viewModelScope.launch {
            globalPreferencesHandler.initFlow()
        }
        viewModelScope.launch {
            languageHandler.initFlow()
        }
        viewModelScope.launch {
            fontThemeHandler.initFlow()
        }
        viewModelScope.launch {
            colorThemeHandler.initFlow()
        }
    }

    val currentFontID = fontThemeHandler.iD
    private fun setCurrentFontID(fontID: String) {
        viewModelScope.launch {
            fontThemeHandler.setThemeID(fontID)
        }
    }
    fun saveCurrentFontID() {
        viewModelScope.launch {
            fontThemeHandler.saveTheme()
        }
    }

    val currentColorID = colorThemeHandler.iD
    private fun setCurrentColorID(colorID: String) {
        viewModelScope.launch {
            colorThemeHandler.setThemeID(colorID)
        }
    }
    fun saveCurrentColorID() {
        viewModelScope.launch {
            colorThemeHandler.setID()
            colorThemeHandler.saveTheme()
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

    val canRequestReview: Boolean
        get() { return reviewTrackingRepository.canRequestReview() }

    val canShowReviewButton: Boolean
        get() { return reviewTrackingRepository.canShowReviewButton() }

    class GlobalPreferencesFactory(
        private val globalPreferencesRepository: GlobalPreferencesRepository,
        private val reviewTrackingRepository: ReviewTrackingRepository,
        private val fontThemeRepository: FontThemeRepository,
        private val colorThemeRepository: ColorThemeRepository,
        private val languageRepository: LanguageRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.GlobalPreferencesViewModel::class.java)) {
                val viewModel =
                    com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.GlobalPreferencesViewModel(
                        globalPreferencesRepository,
                        reviewTrackingRepository,
                        fontThemeRepository,
                        colorThemeRepository,
                        languageRepository
                    )
                viewModel.init()
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
                val fontThemeRepository: FontThemeRepository = appKeyContainer.fontThemeRepository
                val colorThemeRepository: ColorThemeRepository = appKeyContainer.colorThemeRepository
                val languageRepository: LanguageRepository = appKeyContainer.languageRepository

                com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodel.GlobalPreferencesViewModel(
                    globalPreferencesRepository = globalPreferencesRepository,
                    reviewTrackingRepository = reviewTrackingRepository,
                    fontThemeRepository = fontThemeRepository,
                    colorThemeRepository = colorThemeRepository,
                    languageRepository = languageRepository
                )
            }
        }
    }
}

package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodel.datastore.ds

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.GlobalPreferencesHandler
import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.LanguageHandler
import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.subsets.ColorThemeHandler
import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.subsets.FontThemeHandler
import com.TritiumGaming.phasmophobiaevidencepicker.data.model.reviews.ReviewTrackingRepository
import com.TritiumGaming.phasmophobiaevidencepicker.data.repository.ColorThemeRepository
import com.TritiumGaming.phasmophobiaevidencepicker.data.repository.FontThemeRepository
import com.TritiumGaming.phasmophobiaevidencepicker.data.repository.GlobalPreferencesRepository
import com.TritiumGaming.phasmophobiaevidencepicker.data.repository.LanguageRepository
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

    private val initialSetupEvent = {
        globalPreferencesHandler.initialSetupEvent
        languageHandler.initialSetupEvent
        //fontThemeHandler.initialSetupEvent
        //colorThemeHandler.initialSetupEvent
    }

    val languageList = languageRepository.languageList

    private fun init() {
        viewModelScope.launch { initialSetupEvent }

        Log.d("GlobalPreferencesViewModel", "Initializing...")
        viewModelScope.launch {
            globalPreferencesHandler.initGlobalPreferencesFlow()
        }
        viewModelScope.launch {
            fontThemeHandler.initThemeFlow()
        }
        viewModelScope.launch {
            colorThemeHandler.initThemeFlow()
        }
        viewModelScope.launch {
            languageHandler.initLanguageFlow()
        }
    }

    val currentFontThemeID = fontThemeHandler.iD
    private fun setCurrentFontID(fontID: String) {
        viewModelScope.launch {
            fontThemeHandler.setThemeID(fontID)
        }
    }
    fun saveCurrentFontThemeID() {
        viewModelScope.launch {
            fontThemeHandler.saveTheme()
        }
    }

    val currentColorThemeID = colorThemeHandler.iD
    private fun setCurrentColorID(colorID: String) {
        viewModelScope.launch {
            colorThemeHandler.setThemeID(colorID)
        }
    }
    fun saveCurrentColorThemeID() {
        viewModelScope.launch {
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
            if (modelClass.isAssignableFrom(GlobalPreferencesViewModel::class.java)) {
                val viewModel = GlobalPreferencesViewModel(
                    globalPreferencesRepository,
                    reviewTrackingRepository,
                    fontThemeRepository,
                    colorThemeRepository,
                    languageRepository)
                viewModel.init()
                @Suppress("UNCHECKED_CAST")
                return (viewModel)as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}

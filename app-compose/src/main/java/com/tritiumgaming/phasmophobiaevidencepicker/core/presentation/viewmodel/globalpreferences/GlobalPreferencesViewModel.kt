package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.reviewtracker.source.datastore.ReviewTrackingDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.market.market.model.IncrementDirection
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.helpers.globalpreferences.GlobalPreferencesManager
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.helpers.language.LanguageManager
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.helpers.theme.PaletteManager
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.helpers.theme.TypographyManager
import kotlinx.coroutines.launch

class GlobalPreferencesViewModel(
    private val reviewTrackingDatastore: ReviewTrackingDatastore,
    private val globalPreferencesManager: GlobalPreferencesManager,
    private val languageManager: LanguageManager,
    private val typographyManager: TypographyManager,
    private val paletteManager: PaletteManager
) : ViewModel() {

    private fun initialSetupEvent() {
        globalPreferencesManager.initialSetupEvent()
        languageManager.initialSetupEvent()
        paletteManager.initialSetupEvent()
        typographyManager.initialSetupEvent()
    }

    init {
        Log.d("GlobalPreferencesViewModel", "Initializing...")

        initialSetupEvent()

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

    val languageList = languageManager.getLanguages()

    val currentLanguageCode = languageManager.currentLanguageCode
    fun setCurrentLanguageCode(languageCode: String) {
        viewModelScope.launch {
            languageManager.setCurrentLanguageCode(languageCode)
        }
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

    val appTimesOpened = reviewTrackingDatastore.timesOpened
    fun incrementAppTimesOpened() {
        viewModelScope.launch {
            reviewTrackingDatastore.incrementTimesOpened()
        }
    }

    val wasReviewRequested = reviewTrackingDatastore.wasRequested
    fun setWasRequested(status: Boolean) {
        viewModelScope.launch {
            reviewTrackingDatastore.setWasRequested(status)
        }
    }

    val canRequestReview: Boolean
        get() { return reviewTrackingDatastore.canRequestReview() }

    val canShowReviewButton: Boolean
        get() { return reviewTrackingDatastore.canShowReviewButton() }

    class GlobalPreferencesFactory(
        val reviewTrackingRepository: ReviewTrackingDatastore,
        val globalPreferencesManager: GlobalPreferencesManager,
        val languageManager: LanguageManager,
        val typographyManager: TypographyManager,
        val paletteManager: PaletteManager
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GlobalPreferencesViewModel::class.java)) {
                val viewModel =
                    GlobalPreferencesViewModel(
                        reviewTrackingDatastore = reviewTrackingRepository,
                        globalPreferencesManager = globalPreferencesManager,
                        languageManager = languageManager,
                        typographyManager = typographyManager,
                        paletteManager = paletteManager
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
                val appKeyContainer =
                    (this[ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY] as PETApplication).coreContainer

                val reviewTrackingRepository: ReviewTrackingDatastore = appKeyContainer.reviewTrackingRepository
                val globalPreferencesManager: GlobalPreferencesManager = appKeyContainer.globalPreferencesManager
                val languageManager: LanguageManager = appKeyContainer.languageManager
                val typographyManager: TypographyManager = appKeyContainer.typographyManager
                val paletteManager: PaletteManager = appKeyContainer.paletteManager

                GlobalPreferencesViewModel(
                    reviewTrackingDatastore = reviewTrackingRepository,
                    globalPreferencesManager = globalPreferencesManager,
                    languageManager = languageManager,
                    typographyManager = typographyManager,
                    paletteManager = paletteManager
                )
            }
        }
    }

}

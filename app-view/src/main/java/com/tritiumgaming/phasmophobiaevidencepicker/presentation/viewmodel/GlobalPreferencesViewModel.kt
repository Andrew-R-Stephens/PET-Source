package com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.app.PETApplication
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.GlobalPreferencesRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.LanguageRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.ReviewTrackingRepository
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.settings.ThemeModel
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.ui.activities.pet.PETActivity
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.controllers.GlobalPreferencesHandler
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.controllers.LanguageHandler
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.controllers.theming.subsets.ColorThemeControl
import com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.controllers.theming.subsets.FontThemeControl
import kotlinx.coroutines.launch

class GlobalPreferencesViewModel(
    application: Application,
    globalPreferencesRepository: GlobalPreferencesRepository,
    private val reviewTrackingRepository: ReviewTrackingRepository,
    /*typographyRepository: TypographyRepository,
    private val paletteRepository: PaletteRepository,*/
    languageRepository: LanguageRepository
): SharedViewModel(application) {

    private val globalPreferencesHandler: GlobalPreferencesHandler =
        GlobalPreferencesHandler(globalPreferencesRepository)
    private val languageHandler: LanguageHandler = LanguageHandler(languageRepository)
    /*val typographyHandler: TypographyHandler = TypographyHandler(typographyRepository)
    val paletteHandler: PaletteHandler = PaletteHandler(paletteRepository)*/

    // Persistent Styles
    var fontThemeControl: FontThemeControl = FontThemeControl(application)
        private set
    var colorThemeControl: ColorThemeControl = ColorThemeControl(application)
        private set

    private val colorThemeID: String
        get() = colorThemeControl.iD
    val colorTheme: ThemeModel
        get() = colorThemeControl.currentTheme

    private val fontThemeID: String
        get() = fontThemeControl.iD
    val fontTheme: ThemeModel
        get() = fontThemeControl.currentTheme

    override fun setFileName() {
        fileName = R.string.preferences_globalFile_name
    }

    private fun initialSetupEvent() {
        globalPreferencesHandler.initialSetupEvent()
        languageHandler.initialSetupEvent()
        /*paletteHandler.initialSetupEvent()
        typographyHandler.initialSetupEvent()*/
    }

    init {
        initialSetupEvent()

        viewModelScope.launch {
            globalPreferencesHandler.initFlow()
        }
        viewModelScope.launch {
            languageHandler.initFlow()
        }

    }

    fun init(context: Context) {
        setFileName()

        val sharedPref = getSharedPreferences(context)

        fontThemeControl = FontThemeControl(context)
        fontThemeControl.init(sharedPref.getString(
            context.resources.getString(R.string.preference_savedFont), fontThemeID) ?: ""
        )

        colorThemeControl = ColorThemeControl(context)
        colorThemeControl.init(sharedPref.getString(
            context.resources.getString(R.string.preference_savedTheme), colorThemeID) ?: ""
        )

        saveToFile(context)
    }

    val languageList = languageHandler.languageList

    val currentLanguageCode = languageHandler.currentLanguageCode
    fun setCurrentLanguageCode(languageCode: String, activity: PETActivity? = null) {
        viewModelScope.launch {
            languageHandler.setCurrentLanguageCode(languageCode)
        }
        activity?.let {
            try { activity.setLanguage(languageCode) }
            catch (e: Exception) { e.printStackTrace() }
        }
    }

    val screenSaverPreference = globalPreferencesHandler.disableScreensaver
    fun setScreenSaverPreference(disable: Boolean, activity: PETActivity? = null) {
        viewModelScope.launch {
            globalPreferencesHandler.setDisableScreenSaver(disable)
        }
        activity?.let {
            try { activity.setScreenSaverFlag(disable) }
            catch (e: Exception) { e.printStackTrace() }
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

    val huntWarnTimeoutPreference = globalPreferencesHandler.maxHuntWarnFlashTime
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

    /*fun saveColorSpace(c: Context) {
        saveColorSpace(c, getEditor(c), true)
    }*/

    /*private fun saveColorSpace(
        context: Context, editor: SharedPreferences.Editor = getEditor(context), localApply: Boolean = false
    ) {
        saveColorSpace(context, editor, localApply)
        if (localApply) { editor.apply() }
    }*/

    override fun saveToFile(context: Context) {
        val editor = getEditor(context)

        save(context.resources.getString(R.string.preference_savedTheme), colorThemeID, editor)
        save(context.resources.getString(R.string.preference_savedFont), fontThemeID, editor)

        editor.apply()
    }

    class GlobalPreferencesFactory(
        private val application: Application,
        private val globalPreferencesRepository: GlobalPreferencesRepository,
        private val reviewTrackingRepository: ReviewTrackingRepository,
        /*private val fontThemeRepository: TypographyRepository,
        private val colorThemeRepository: PaletteRepository,*/
        private val languageRepository: LanguageRepository
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GlobalPreferencesViewModel::class.java)) {
                val viewModel =
                    GlobalPreferencesViewModel(
                        application = application,
                        globalPreferencesRepository,
                        reviewTrackingRepository,
                        /*fontThemeRepository,
                        colorThemeRepository,*/
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
                val application: PETApplication = this[APPLICATION_KEY] as PETApplication
                val appKeyContainer = application.container

                val globalPreferencesRepository = appKeyContainer.globalPreferencesRepository
                val reviewTrackingRepository: ReviewTrackingRepository = appKeyContainer.reviewTrackingRepository
                /*val fontThemeRepository: TypographyRepository = appKeyContainer.typographyRepository
                val colorThemeRepository: PaletteRepository = appKeyContainer.paletteRepository*/
                val languageRepository: LanguageRepository = appKeyContainer.languageRepository

                GlobalPreferencesViewModel(
                    application = application,
                    globalPreferencesRepository = globalPreferencesRepository,
                    reviewTrackingRepository = reviewTrackingRepository,
                    /*typographyRepository = fontThemeRepository,
                    paletteRepository = colorThemeRepository,*/
                    languageRepository = languageRepository
                )
            }
        }
    }
}

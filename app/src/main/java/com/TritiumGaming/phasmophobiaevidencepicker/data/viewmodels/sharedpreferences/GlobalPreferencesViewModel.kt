package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.sharedpreferences

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.subsets.ColorThemeControl
import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.subsets.FontThemeControl
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.sanity.warning.PhaseWarningModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.reviews.ReviewTrackingModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.settings.ThemeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Locale

class GlobalPreferencesViewModel(application: Application): SharedViewModel(application) {

    companion object Language {
        var DEFAULT_LANGUAGE: String = Locale.ENGLISH.language
    }

    data class LanguageObject(val name: String, val abbreviation: String)

    // Review Tracker
    var reviewRequestData: ReviewTrackingModel = ReviewTrackingModel()
        private set

    // Persistent Styles
    var fontThemeControl: FontThemeControl = FontThemeControl(application)
        private set
    var colorThemeControl: ColorThemeControl = ColorThemeControl(application)
        private set

    // Language
    // TODO encapsulate language controller
    //  var languagesModel: LanguagesModel = LanguagesModel(application)
    var languageList: ArrayList<LanguageObject> = ArrayList()
    var currentLanguageCode: String = DEFAULT_LANGUAGE

    // Generic settings
    var isAlwaysOn: Boolean = false
    var networkPreference: Boolean = true

    // Investigation Behaviors
    private val _huntWarnFlashTimeMax = MutableStateFlow(PhaseWarningModel.INFINITY)
    val huntWarnFlashTimeMax: StateFlow<Long> = _huntWarnFlashTimeMax.asStateFlow()
    fun setHuntWarningFlashTimeMax(maxTime: Long) {
        _huntWarnFlashTimeMax.value = maxTime
    }
    private var _isHuntWarnAudioAllowed = MutableStateFlow(true)
    val isHuntWarnAudioAllowed: StateFlow<Boolean> = _isHuntWarnAudioAllowed.asStateFlow()
    fun setHuntWarnAudioAllowed(isAllowed: Boolean) {
        _isHuntWarnAudioAllowed.value = isAllowed
    }

    var isLeftHandSupportEnabled: Boolean = false
    var reorderGhostViews = true

    // Title screen increments
    private var canShowIntroduction: Boolean = true

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

    fun init(context: Context) {
        setFileName()

        val languageNames = ArrayList(
            listOf(*context.resources.getStringArray(R.array.language_names)))
        val languageCodes = ArrayList(
            listOf(*context.resources.getStringArray(R.array.language_codes)))

        languageList = ArrayList()
        if(languageNames.size == languageCodes.size) {
            languageNames.forEachIndexed { index: Int, name: String ->
                languageList.add(LanguageObject(name, languageCodes[index]))
            }
        }

        // OVERRIDE DEFAULT LANGUAGE
        languageList.forEach { language ->
            if(language.abbreviation.equals(Locale.getDefault().language, ignoreCase = true)) {
                DEFAULT_LANGUAGE = Locale.getDefault().language
            }
        }

        val sharedPref = getSharedPreferences(context)

        currentLanguageCode = sharedPref.getString(
            context.resources.getString(R.string.preference_language), DEFAULT_LANGUAGE
        ) ?: DEFAULT_LANGUAGE


        networkPreference =
            sharedPref.getBoolean(context.resources.getString(R.string.preference_network), networkPreference)

        isAlwaysOn =
            sharedPref.getBoolean(context.resources.getString(R.string.preference_isAlwaysOn), isAlwaysOn)

        setHuntWarnAudioAllowed(
            sharedPref.getBoolean(
                context.resources.getString(R.string.preference_isHuntAudioWarningAllowed),
                isHuntWarnAudioAllowed.value))
        setHuntWarningFlashTimeMax(
            sharedPref.getLong(
                context.resources.getString(R.string.preference_huntWarningFlashTimeout),
                huntWarnFlashTimeMax.value))

        isLeftHandSupportEnabled =
            sharedPref.getBoolean(context.resources.getString(R.string.preference_isLeftHandSupportEnabled), isLeftHandSupportEnabled)
        canShowIntroduction =
            sharedPref.getBoolean(context.resources.getString(R.string.tutorialTracking_canShowIntroduction), canShowIntroduction)

        reorderGhostViews =
            sharedPref.getBoolean(context.resources.getString(R.string.preference_enableReorderGhostViews), reorderGhostViews)

        reviewRequestData = ReviewTrackingModel(
            sharedPref.getBoolean(context.resources.getString(R.string.reviewtracking_canRequestReview), false),
            sharedPref.getLong(context.resources.getString(R.string.reviewtracking_appTimeAlive), 0),
            sharedPref.getInt(context.resources.getString(R.string.reviewtracking_appTimesOpened), 0)
        )

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

    fun incrementAppOpenCount(context: Context) {
        reviewRequestData.incrementTimesOpened()

        try {
            save(
                context.resources.getString(R.string.reviewtracking_appTimesOpened),
                reviewRequestData.timesOpened,
                getEditor(context))
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    fun getCurrentLanguageIndex(): Int {
        for (i in languageList.indices) {
            if (currentLanguageCode.equals(languageList[i].abbreviation, ignoreCase = true))
            { return i } }
        return 0
    }

    fun setCurrentLanguage(position: Int) {
        if (position < 0 || position >= languageList.size) { return }

        currentLanguageCode = languageList[position].abbreviation
    }

    fun saveColorSpace(c: Context) {
        saveColorSpace(c, getEditor(c), true)
    }

    private fun saveColorSpace(
        context: Context, editor: SharedPreferences.Editor = getEditor(context), localApply: Boolean = false
    ) {
        saveColorSpace(context, editor, localApply)
        if (localApply) { editor.apply() }
    }

    override fun saveToFile(context: Context) {
        val editor = getEditor(context)

        save(context.resources.getString(R.string.preference_network), networkPreference, editor)
        save(context.resources.getString(R.string.preference_language), currentLanguageCode, editor)
        save(context.resources.getString(R.string.preference_isAlwaysOn), isAlwaysOn, editor)
        save(context.resources.getString(R.string.preference_isHuntAudioWarningAllowed), isHuntWarnAudioAllowed.value, editor)
        save(context.resources.getString(R.string.preference_huntWarningFlashTimeout), huntWarnFlashTimeMax.value, editor)
        save(context.resources.getString(R.string.preference_savedTheme), colorThemeID, editor)
        save(context.resources.getString(R.string.preference_savedFont), fontThemeID, editor)
        save(context.resources.getString(R.string.preference_isLeftHandSupportEnabled), isLeftHandSupportEnabled, editor)
        save(context.resources.getString(R.string.preference_enableReorderGhostViews), reorderGhostViews, editor)
        save(context.resources.getString(R.string.reviewtracking_canRequestReview),reviewRequestData.wasRequested, editor)
        save(context.resources.getString(R.string.reviewtracking_appTimesOpened), reviewRequestData.timesOpened, editor)
        save(context.resources.getString(R.string.reviewtracking_appTimeAlive), reviewRequestData.timeActive, editor)
        save(context.resources.getString(R.string.tutorialTracking_canShowIntroduction), canShowIntroduction, editor)

        editor.apply()
    }

    val dataAsList: HashMap<String, String?>
        get() {
            val settings = HashMap<String, String?>()
            settings["network_pref"] = networkPreference.toString()
            settings["language"] = currentLanguageCode
            settings["always_on"] = isAlwaysOn.toString()
            settings["warning_enabled"] = isHuntWarnAudioAllowed.value.toString()
            settings["warning_timeout"] = huntWarnFlashTimeMax.value.toString()
            settings["color_theme"] = colorThemeID
            settings["font_type"] = fontThemeID
            settings["left_support"] = isLeftHandSupportEnabled.toString()
            settings["can_show_intro"] = canShowIntroduction.toString()
            settings["review_request"] = reviewRequestData.canRequestReview().toString()
            settings["times_opened"] = reviewRequestData.timesOpened.toString()
            settings["active_time"] = reviewRequestData.timeActive.toString()

            return settings
        }

}

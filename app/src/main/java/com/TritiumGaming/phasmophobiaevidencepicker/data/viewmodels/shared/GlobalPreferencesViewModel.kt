package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
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

class GlobalPreferencesViewModel : SharedViewModel() {

    companion object Language {
        const val DEFAULT_LANGUAGE = "en"
    }

    // Review Tracker
    lateinit var reviewRequestData: ReviewTrackingModel
        private set

    // Persistent Styles
    lateinit var fontThemeControl: FontThemeControl
        private set
    lateinit var colorThemeControl: ColorThemeControl
        private set

    // Language
    var languageName: String = Locale.getDefault().language

    // Generic settings
    var isAlwaysOn: Boolean = false
    var networkPreference: Boolean = true

    // Investigation Behaviors
    private val _huntWarnFlashTimeMax = MutableStateFlow(PhaseWarningModel.INFINITY)
    val huntWarnFlashTimeMax: StateFlow<Long> = _huntWarnFlashTimeMax.asStateFlow()
    fun setHuntWarningFlashTimeMax(maxTime: Long) {
        _huntWarnFlashTimeMax.value = maxTime
    }
    var _isHuntWarnAudioAllowed = MutableStateFlow(true)
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

    override fun init(context: Context): Boolean {
        setFileName()

        val sharedPref = getSharedPreferences(context)

        networkPreference =
            sharedPref.getBoolean(context.resources.getString(R.string.preference_network), networkPreference)
        languageName =
            sharedPref.getString(context.resources.getString(R.string.preference_language), languageName) ?:
            Locale.getDefault().language
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

        return true
    }

    fun incrementAppOpenCount(context: Context) {
        reviewRequestData.incrementTimesOpened()

        try {
            saveTimesOpened(context, getEditor(context), true)
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }


    /**
     * Gets the language saved to GlobalPreferences file.
     * Defaults to return 'en' if there is no previously saved preference.
     *
     * @return The language specified in the Preferences data, or otherwise English
     */
    fun getLanguage(context: Context): String {
        val lang = context.getSharedPreferences(
            context.resources.getString(fileName), Context.MODE_PRIVATE
        ).getString("chosenLanguage", DEFAULT_LANGUAGE)

        Log.d("Current Chosen Language", lang!!)

        return lang
    }

    fun getLanguageIndex(languageNames: ArrayList<String?>): Int {
        for (i in languageNames.indices) {
            if (languageName.equals(languageNames[i], ignoreCase = true)) {
                return i
            }
        }
        return 0
    }

    fun setLanguage(position: Int, languageNames: Array<String?>) {
        if (position < 0 || position >= languageNames.size) { return }

        languageName = languageNames[position] ?: Locale.getDefault().language
    }

    fun canShowIntroduction(): Boolean {
        return canShowIntroduction && reviewRequestData.timesOpened <= 1
    }

    private fun saveNetworkPreference(
        context: Context, editor: SharedPreferences.Editor = getEditor(context), localApply: Boolean = false
    ) {
        editor.putBoolean(
            context.resources.getString(R.string.preference_network),
            networkPreference
        )

        if (localApply) { editor.apply() }
    }

    private fun saveChosenLanguage(
        context: Context, editor: SharedPreferences.Editor = getEditor(context), localApply: Boolean = false
    ) {
        editor.putString(
            context.resources.getString(R.string.preference_language),
            languageName
        )

        if (localApply) { editor.apply() }
    }

    private fun saveAlwaysOnState(
        context: Context, editor: SharedPreferences.Editor = getEditor(context), localApply: Boolean = false
    ) {
        editor.putBoolean(
            context.resources.getString(R.string.preference_isAlwaysOn),
            isAlwaysOn
        )

        if (localApply) { editor.apply() }
    }

    private fun saveHuntWarningAudioAllowed(
        context: Context, editor: SharedPreferences.Editor = getEditor(context), localApply: Boolean = false
    ) {
        editor.putBoolean(
            context.resources.getString(R.string.preference_isHuntAudioWarningAllowed),
            isHuntWarnAudioAllowed.value
        )

        if (localApply) { editor.apply() }
    }

    private fun saveHuntWarningFlashTimeout(
        context: Context, editor: SharedPreferences.Editor = getEditor(context), localApply: Boolean = false
    ) {
        editor.putLong(
            context.resources.getString(R.string.preference_huntWarningFlashTimeout),
            huntWarnFlashTimeMax.value
        )

        if (localApply) { editor.apply() }
    }

    fun saveColorSpace(c: Context) {
        saveColorSpace(c, getEditor(c), true)
    }

    private fun saveColorSpace(
        context: Context, editor: SharedPreferences.Editor = getEditor(context), localApply: Boolean = false
    ) {
        editor.putString(
            context.resources.getString(R.string.preference_savedTheme),
            colorThemeID
        )

        if (localApply) { editor.apply() }
    }

    private fun saveFontType(
        context: Context, editor: SharedPreferences.Editor = getEditor(context), localApply: Boolean = false
    ) {
        editor.putString(context.resources.getString(R.string.preference_savedFont), fontThemeID)

        if (localApply) {
            editor.apply()
        }
    }

    private fun saveAppTimeAlive(
        context: Context, editor: SharedPreferences.Editor = getEditor(context), localApply: Boolean = false
    ) {
        editor.putLong(
            context.resources.getString(R.string.reviewtracking_appTimeAlive),
            reviewRequestData.timeActive
        )

        if (localApply) { editor.apply() }
    }

    private fun saveTimesOpened(
        context: Context, editor: SharedPreferences.Editor = getEditor(context), localApply: Boolean = false
    ) {
        editor.putInt(
            context.resources.getString(R.string.reviewtracking_appTimesOpened),
            reviewRequestData.timesOpened
        )

        if (localApply) { editor.apply() }
    }

    private fun saveCanRequestReview(
        context: Context, editor: SharedPreferences.Editor = getEditor(context), localApply: Boolean = false
    ) {
        editor.putBoolean(
            context.resources.getString(R.string.reviewtracking_canRequestReview),
            reviewRequestData.wasRequested
        )

        if (localApply) { editor.apply() }
    }

    private fun saveIsLeftHandSupportEnabled(
        context: Context, editor: SharedPreferences.Editor = getEditor(context), localApply: Boolean = false
    ) {
        editor.putBoolean(
            context.resources.getString(R.string.preference_isLeftHandSupportEnabled),
            isLeftHandSupportEnabled
        )

        if (localApply) { editor.apply() }
    }

    private fun saveReorderGhostViews(
        context: Context, editor: SharedPreferences.Editor = getEditor(context), localApply: Boolean = false
    ) {
        editor.putBoolean(
            context.resources.getString(R.string.preference_enableReorderGhostViews),
            reorderGhostViews
        )

        if (localApply) { editor.apply() }
    }

    private fun saveCanShowIntroduction(
        context: Context, editor: SharedPreferences.Editor = getEditor(context), localApply: Boolean = false
    ) {
        editor.putBoolean(
            context.resources.getString(R.string.tutorialTracking_canShowIntroduction),
            canShowIntroduction
        )

        if (localApply) { editor.apply() }
    }

    override fun saveToFile(context: Context) {
        val editor = getEditor(context)

        saveNetworkPreference(context, editor, false)
        saveChosenLanguage(context, editor, false)
        saveAlwaysOnState(context, editor, false)
        saveHuntWarningAudioAllowed(context, editor, false)
        saveHuntWarningFlashTimeout(context, editor, false)
        saveColorSpace(context, editor, false)
        saveFontType(context, editor, false)
        saveIsLeftHandSupportEnabled(context, editor, false)
        saveReorderGhostViews(context, editor, false)
        saveCanRequestReview(context, editor, false)
        saveTimesOpened(context, editor, false)
        saveAppTimeAlive(context, editor, false)
        saveCanShowIntroduction(context, editor, false)

        editor.apply()
    }

    val dataAsList: HashMap<String, String?>
        get() {
            val settings = HashMap<String, String?>()
            settings["network_pref"] = networkPreference.toString()
            settings["language"] = languageName
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

    fun printFromFile(context: Context) {
        val sharedPref = getSharedPreferences(context)

        Log.d(
            context.getString(fileName),
            "NetworkPreference: " + sharedPref.getBoolean(
                context.resources.getString(R.string.preference_network),
                networkPreference
            ) + "; Language: " + sharedPref.getString(
                context.resources.getString(R.string.preference_language),
                languageName
            ) + "; Always On: " + sharedPref.getBoolean(
                context.resources.getString(R.string.preference_isAlwaysOn),
                isAlwaysOn
            ) + "; Is Hunt Audio Allowed: " + sharedPref.getBoolean(
                context.resources.getString(
                    R.string.preference_isHuntAudioWarningAllowed
                ), isHuntWarnAudioAllowed.value
            ) + "; Hunt Warning Flash Timeout: " + sharedPref.getLong(
                context.resources.getString(
                    R.string.preference_huntWarningFlashTimeout
                ), huntWarnFlashTimeMax.value
            ) + "; Color Space: " + sharedPref.getString(
                context.resources.getString(R.string.preference_savedTheme),
                colorThemeID
            ) + "; ReviewRequestData: [" +
                    "Time Alive: " + sharedPref.getLong(
                context.resources.getString(R.string.reviewtracking_appTimeAlive),
                0
            ) +
                    "; Times Opened: " + sharedPref.getInt(
                context.resources.getString(R.string.reviewtracking_appTimesOpened),
                0
            ) +
                    "; Can Request Review: " + sharedPref.getBoolean(
                context.resources.getString(R.string.reviewtracking_canRequestReview),
                false
            ) + "]" +
                    "; Can Show Introduction: " + sharedPref.getBoolean(
                context.resources.getString(
                    R.string.tutorialTracking_canShowIntroduction
                ), false
            )
        )
    }

    fun printFromVariables() {
        Log.d(
            "GlobalPreferencesVars",
            "NetworkPreference: " + networkPreference +
                    "; Language: " + languageName +
                    "; Always On: " + isAlwaysOn +
                    "; Is Hunt Audio Allowed: " + isHuntWarnAudioAllowed +
                    "; Hunt Warning Flash Timeout: " + huntWarnFlashTimeMax.value +
                    "; Color Space: " + colorThemeID +
                    "; Can Show Introduction: " + canShowIntroduction +
                    "; ReviewRequestData: [" + reviewRequestData.toString() + "]"
        )
    }
}

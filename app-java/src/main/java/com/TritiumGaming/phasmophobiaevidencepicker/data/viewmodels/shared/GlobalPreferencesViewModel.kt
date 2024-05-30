package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.annotation.NonNull
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.ReviewTrackingData
import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.CustomTheme
import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.subsets.ColorThemeControl
import com.TritiumGaming.phasmophobiaevidencepicker.data.controllers.theming.subsets.FontThemeControl
import com.google.android.play.integrity.internal.c
import org.jetbrains.annotations.NotNull
import java.util.Locale

class GlobalPreferencesViewModel : SharedViewModel() {

    // Review Tracker
    lateinit var reviewRequestData: ReviewTrackingData
        private set

    // Persistent Styles
    lateinit var fontThemeControl: FontThemeControl
        private set
    lateinit var colorThemeControl: ColorThemeControl
        private set

    // Language
    var languageName: String = Locale.getDefault().language

    // Generic settings
    var huntWarningFlashTimeout: Int = -1 // Investigation behavior

    var isAlwaysOn: Boolean = false
    var isHuntWarningAudioAllowed: Boolean = true // Investigation behavior

    var networkPreference: Boolean = true
    var isLeftHandSupportEnabled: Boolean = false // Investigation behavior
    var reorderGhostViews = true // Investigation behavior

    // Title screen increments
    var canShowIntroduction: Boolean = true

    val colorThemeID: String
        get() = colorThemeControl.id
    val colorTheme: CustomTheme
        get() = colorThemeControl.currentTheme

    val fontThemeID: String
        get() = fontThemeControl.id
    val fontTheme: CustomTheme
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
            sharedPref.getString(
                context.resources.getString(R.string.preference_language), languageName
            ) ?: Locale.getDefault().language
        isAlwaysOn =
            sharedPref.getBoolean(context.resources.getString(R.string.preference_isAlwaysOn), isAlwaysOn)
        isHuntWarningAudioAllowed =
            sharedPref.getBoolean(context.resources.getString(R.string.preference_isHuntAudioWarningAllowed), isHuntWarningAudioAllowed)
        huntWarningFlashTimeout =
            sharedPref.getInt(context.resources.getString(R.string.preference_huntWarningFlashTimeout), huntWarningFlashTimeout)

        isLeftHandSupportEnabled =
            sharedPref.getBoolean(context.resources.getString(R.string.preference_isLeftHandSupportEnabled), isLeftHandSupportEnabled)
        canShowIntroduction =
            sharedPref.getBoolean(context.resources.getString(R.string.tutorialTracking_canShowIntroduction), canShowIntroduction)

        reorderGhostViews =
            sharedPref.getBoolean(context.resources.getString(R.string.preference_enableReorderGhostViews), reorderGhostViews)


        reviewRequestData = ReviewTrackingData(
            sharedPref.getBoolean(context.resources.getString(R.string.reviewtracking_canRequestReview), false),
            sharedPref.getLong(context.resources.getString(R.string.reviewtracking_appTimeAlive), 0),
            sharedPref.getInt(context.resources.getString(R.string.reviewtracking_appTimesOpened), 0)
        )

        fontThemeControl = FontThemeControl(context)
        fontThemeControl.init(
            sharedPref.getString(context.resources.getString(R.string.preference_savedFont), fontThemeID)
        )

        colorThemeControl = ColorThemeControl(context)
        colorThemeControl.init(
            sharedPref.getString(context.resources.getString(R.string.preference_savedTheme), colorThemeID)
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
     * Gets the language saved to GlobalPreferences.
     * Defaults to return 'en' if there is no previously saved preference.
     *
     * @return The language specified in the Preferences data, or otherwise English
     */
    fun getLanguage(context: Context): String {
        val lang = context.getSharedPreferences(
            context.resources.getString(fileName), Context.MODE_PRIVATE
        ).getString("chosenLanguage", "en")

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
        if (position < 0 || position >= languageNames.size) {
            return
        }

        languageName = languageNames[position] ?: Locale.getDefault().language
    }

    fun canShowIntroduction(): Boolean {
        return canShowIntroduction && reviewRequestData!!.timesOpened <= 1
    }

    private fun saveNetworkPreference(
        context: Context, editor: SharedPreferences.Editor = getEditor(context), localApply: Boolean = false
    ) {
        editor.putBoolean(
            context.resources.getString(R.string.preference_network),
            networkPreference
        )

        if (localApply) {
            editor.apply()
        }
    }

    private fun saveChosenLanguage(
        context: Context, editor: SharedPreferences.Editor = getEditor(context), localApply: Boolean = false
    ) {
        editor.putString(
            context.resources.getString(R.string.preference_language),
            languageName
        )

        if (localApply) {
            editor.apply()
        }
    }

    private fun saveAlwaysOnState(
        context: Context, editor: SharedPreferences.Editor = getEditor(context), localApply: Boolean = false
    ) {
        editor.putBoolean(
            context.resources.getString(R.string.preference_isAlwaysOn),
            isAlwaysOn
        )

        if (localApply) {
            editor.apply()
        }
    }

    private fun saveHuntWarningAudioAllowed(
        context: Context, editor: SharedPreferences.Editor = getEditor(context), localApply: Boolean = false
    ) {
        editor.putBoolean(
            context.resources.getString(R.string.preference_isHuntAudioWarningAllowed),
            isHuntWarningAudioAllowed
        )

        if (localApply) {
            editor.apply()
        }
    }

    private fun saveHuntWarningFlashTimeout(
        context: Context, editor: SharedPreferences.Editor = getEditor(context), localApply: Boolean = false
    ) {
        editor.putInt(
            context.resources.getString(R.string.preference_huntWarningFlashTimeout),
            huntWarningFlashTimeout
        )

        if (localApply) {
            editor.apply()
        }
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

        if (localApply) {
            editor.apply()
        }
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

        if (localApply) {
            editor.apply()
        }
    }

    private fun saveTimesOpened(
        context: Context, editor: SharedPreferences.Editor = getEditor(context), localApply: Boolean = false
    ) {
        editor.putInt(
            context.resources.getString(R.string.reviewtracking_appTimesOpened),
            reviewRequestData.timesOpened
        )

        if (localApply) {
            editor.apply()
        }
    }

    private fun saveCanRequestReview(
        context: Context, editor: SharedPreferences.Editor = getEditor(context), localApply: Boolean = false
    ) {
        editor.putBoolean(
            context.resources.getString(R.string.reviewtracking_canRequestReview),
            reviewRequestData.wasRequested
        )

        if (localApply) {
            editor.apply()
        }
    }

    private fun saveIsLeftHandSupportEnabled(
        context: Context, editor: SharedPreferences.Editor = getEditor(context), localApply: Boolean = false
    ) {
        editor.putBoolean(
            context.resources.getString(R.string.preference_isLeftHandSupportEnabled),
            isLeftHandSupportEnabled
        )

        if (localApply) {
            editor.apply()
        }
    }

    private fun saveReorderGhostViews(
        context: Context, editor: SharedPreferences.Editor = getEditor(context), localApply: Boolean = false
    ) {
        editor.putBoolean(
            context.resources.getString(R.string.preference_enableReorderGhostViews),
            reorderGhostViews
        )

        if (localApply) {
            editor.apply()
        }
    }

    fun saveCanShowIntroduction(
        context: Context, editor: SharedPreferences.Editor = getEditor(context), localApply: Boolean = false
    ) {
        editor.putBoolean(
            context.resources.getString(R.string.tutorialTracking_canShowIntroduction),
            canShowIntroduction
        )

        if (localApply) {
            editor.apply()
        }
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
            settings["warning_enabled"] = isHuntWarningAudioAllowed.toString()
            settings["warning_timeout"] = huntWarningFlashTimeout.toString()
            settings["color_theme"] = colorThemeID
            settings["font_type"] = fontThemeID
            settings["left_support"] = isLeftHandSupportEnabled.toString()
            settings["can_show_intro"] = canShowIntroduction.toString()
            if (reviewRequestData != null) {
                settings["review_request"] = reviewRequestData!!.canRequestReview().toString()
                settings["times_opened"] = reviewRequestData!!.timesOpened.toString()
                settings["active_time"] = reviewRequestData!!.timeActive.toString()
            }

            return settings
        }

    fun printFromFile(context: Context) {
        val sharedPref = getSharedPreferences(context)

        Log.d(
            context.getString(fileName),
            "NetworkPreference: " + sharedPref.getBoolean(
                context.resources.getString(R.string.preference_network),
                networkPreference
            ) +
                    "; Language: " + sharedPref.getString(
                context.resources.getString(R.string.preference_language),
                languageName
            ) +
                    "; Always On: " + sharedPref.getBoolean(
                context.resources.getString(R.string.preference_isAlwaysOn),
                isAlwaysOn
            ) +
                    "; Is Hunt Audio Allowed: " + sharedPref.getBoolean(
                context.resources.getString(
                    R.string.preference_isHuntAudioWarningAllowed
                ), isHuntWarningAudioAllowed
            ) +
                    "; Hunt Warning Flash Timeout: " + sharedPref.getInt(
                context.resources.getString(
                    R.string.preference_huntWarningFlashTimeout
                ), huntWarningFlashTimeout
            ) +
                    "; Color Space: " + sharedPref.getString(
                context.resources.getString(R.string.preference_savedTheme),
                colorThemeID
            ) +
                    "; ReviewRequestData: [" +
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

    /**
     *
     */
    fun printFromVariables() {
        Log.d(
            "GlobalPreferencesVars",
            "NetworkPreference: " + networkPreference +
                    "; Language: " + languageName +
                    "; Always On: " + isAlwaysOn +
                    "; Is Hunt Audio Allowed: " + isHuntWarningAudioAllowed +
                    "; Hunt Warning Flash Timeout: " + huntWarningFlashTimeout +
                    "; Color Space: " + colorThemeID +
                    "; Can Show Introduction: " + canShowIntroduction +
                    "; ReviewRequestData: [" + reviewRequestData.toString() + "]"
        )
    }
}

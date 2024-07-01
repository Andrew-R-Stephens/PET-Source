package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.TritiumGaming.phasmophobiaevidencepicker.R

/** @noinspection SameParameterValue
 */
class OnboardingViewModel : SharedViewModel() {

    var showIntroduction: Boolean = true

    override fun setFileName() {
        fileName = R.string.preferences_onboardingFile_name
    }

    override fun init(context: Context): Boolean {
        setFileName()

        val sharedPref = getSharedPreferences(context)

        showIntroduction = sharedPref.getBoolean(
            context.resources.getString(R.string.onboarding_canShow_intro),
            showIntroduction
        )

        saveToFile(context)

        return true
    }

    fun saveCanShowIntroduction(
        c: Context, editor: SharedPreferences.Editor?, localApply: Boolean
    ) {
        val editor = editor

        editor!!.putBoolean(
            c.resources.getString(R.string.tutorialTracking_canShowIntroduction),
            showIntroduction
        )

        if (localApply) {
            editor.apply()
        }
    }

    /**
     * saveToFile method
     *
     * @param context The Activity context.
     */
    override fun saveToFile(context: Context) {
        val editor = getEditor(context)

        saveCanShowIntroduction(context, editor, false)

        editor.apply()
    }

    val dataAsList: HashMap<String, String>
        get() {
            val settings = HashMap<String, String>()
            settings["can_show_intro"] = showIntroduction.toString()

            return settings
        }

    /**
     *
     * @param context
     */
    fun printFromFile(context: Context) {
        val sharedPref = getSharedPreferences(context)

        Log.d(
            "OnboardingPrefs",
            "Can Show Introduction: " + sharedPref.getBoolean(
                context.resources.getString(R.string.tutorialTracking_canShowIntroduction),
                false
            )
        )
    }

    /**
     *
     */
    fun printFromVariables() {
        Log.d(
            "GlobalPreferencesVars",
            "; Can Show Introduction: " + showIntroduction
        )
    }
}
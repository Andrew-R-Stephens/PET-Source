package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.shared

import android.app.Application
import android.content.Context
import com.TritiumGaming.phasmophobiaevidencepicker.R

/** @noinspection SameParameterValue
 */
class OnboardingViewModel(application: Application): SharedViewModel(application) {

    var showIntroduction: Boolean = true

    override fun setFileName() {
        fileName = R.string.preferences_onboardingFile_name
    }

    init {
        setFileName()

        val sharedPref = getSharedPreferences(application)

        showIntroduction = sharedPref.getBoolean(application.resources.getString(R.string.onboarding_canShow_intro), showIntroduction)

        saveToFile(application)
    }

    /** @param context The Activity context. */
    override fun saveToFile(context: Context) {
        val editor = getEditor(context)

        save(context.resources.getString(R.string.tutorialTracking_canShowIntroduction), showIntroduction, editor)

        editor.apply()
    }

}

package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodel.datastore.ds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/** @noinspection SameParameterValue
 */
class OnboardingViewModel(

): ViewModel() {

    var showIntroduction: Boolean = true

    /*
    override fun setFileName() {
        fileName = R.string.preferences_onboardingFile_name
    }

    init {
        setFileName()

        val sharedPref = getSharedPreferences(application)

        showIntroduction = sharedPref.getBoolean(application.resources.getString(R.string.onboarding_canShow_intro), showIntroduction)

        saveToFile(application)
    }

    override fun saveToFile(context: Context) {
        val editor = getEditor(context)

        save(context.resources.getString(R.string.tutorialTracking_canShowIntroduction), showIntroduction, editor)

        editor.apply()
    }
    */

    class OnboardingFactory(

    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(OnboardingViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return OnboardingViewModel(
                ) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}

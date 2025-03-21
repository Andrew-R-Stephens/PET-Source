package com.tritiumgaming.phasmophobiaevidencepicker.presentation.viewmodel.controllers

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.liveData
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.LanguageRepository
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.LanguageRepository.Companion.DEFAULT_LANGUAGE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale

class LanguageHandler(
    private val repository: LanguageRepository
) {

    fun initialSetupEvent() {
        liveData {
            emit(repository.fetchInitialPreferences())
        }
    }

    val languageList = repository.languageList

    suspend fun initFlow() {
        repository.flow.collect { languagePreference ->
            _currentLanguageCode.update { languagePreference.languageCode }
            Log.d("Language", "Collected Language Code: ${languagePreference.languageCode}")

            /* Define the language used whenever the saved language changes */
            AppCompatDelegate.setApplicationLocales(
                LocaleListCompat.create(
                    Locale.forLanguageTag(languagePreference.languageCode)
                )
            )

        }
    }

    private var _currentLanguageCode = MutableStateFlow(DEFAULT_LANGUAGE)
    val currentLanguageCode = _currentLanguageCode.asStateFlow()
    suspend fun setCurrentLanguageCode(languageCode: String) {
        _currentLanguageCode.update { languageCode }
        repository.setCurrentLanguageCode(languageCode)
    }

    /*private var _tempLanguageCode = MutableStateFlow(Locale.getDefault().language)
    val tempLanguageCode = _tempLanguageCode.asStateFlow()
    fun setTempLanguageCode(languageCode: String) {
        _tempLanguageCode.update { languageCode }
    }*/

    suspend fun setCurrentLanguageCodeByIndex(position: Int) {
        if (position < 0 || position >= languageList.size) {
            return
        }
        setCurrentLanguageCode(languageList[position].abbreviation)
    }

    fun getCurrentLanguageIndex(): Int {
        for (i in languageList.indices) {
            if (_currentLanguageCode.value.equals(languageList[i].abbreviation, ignoreCase = true))
            { return i } }
        return 0
    }


}
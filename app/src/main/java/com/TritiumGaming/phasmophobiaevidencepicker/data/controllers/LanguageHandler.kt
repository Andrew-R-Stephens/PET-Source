package com.TritiumGaming.phasmophobiaevidencepicker.data.controllers

import android.util.Log
import androidx.lifecycle.liveData
import com.TritiumGaming.phasmophobiaevidencepicker.data.repository.LanguageRepository
import com.TritiumGaming.phasmophobiaevidencepicker.data.repository.LanguageRepository.Companion.DEFAULT_LANGUAGE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LanguageHandler(
    private val repository: LanguageRepository
) {

    val initialSetupEvent = liveData {
        emit(repository.fetchInitialPreferences())
    }

    val languageList = repository.languageList

    suspend fun initLanguageFlow() {
        repository.languageFlow.collect { languagePreference ->
            _currentLanguageCode.update { languagePreference.languageCode }
            Log.d("Language", "Collected Language Code: ${languagePreference.languageCode}")
        }
    }

    private var _currentLanguageCode = MutableStateFlow(DEFAULT_LANGUAGE)
    val currentLanguageCode = _currentLanguageCode.asStateFlow()
    suspend fun setCurrentLanguageCode(languageCode: String) {
        _currentLanguageCode.update { languageCode }
        repository.setCurrentLanguageCode(languageCode)
    }

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
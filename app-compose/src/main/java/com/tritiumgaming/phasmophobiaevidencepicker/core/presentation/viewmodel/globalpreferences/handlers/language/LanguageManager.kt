package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.handlers.language

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.repository.LanguageRepository
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.datastore.LanguageDatastore
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.source.local.language.LanguageLocalDataSource.Companion.DEFAULT_LANGUAGE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale

class LanguageManager(
    private val repository: LanguageRepository,
    private val datastore: LanguageDatastore
) {

    fun initialSetupEvent() {
        datastore.initialSetupEvent()
    }

    val languageList
        get() = repository.languageList

    suspend fun initFlow() {
        datastore.flow.collect { languagePreference ->
            _currentLanguageCode.update { languagePreference.languageCode }
            Log.d("Language", "Collected Language Code: ${languagePreference.languageCode}")

            //Define the language used whenever the saved language changes
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
        datastore.setCurrentLanguageCode(languageCode)
    }

}
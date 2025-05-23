package com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.viewmodel.globalpreferences.helpers.language

import android.util.Log
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.datastore.LanguageDatastoreDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageRepository.Companion.DEFAULT_LANGUAGE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale

class LanguageManager(
    private val datastore: LanguageDatastoreDataSource
) {

    fun initialSetupEvent() = datastore.initialSetupEvent()

   /* suspend fun initFlow() {
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
*/
}
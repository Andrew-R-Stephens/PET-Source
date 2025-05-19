package com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.local

import android.content.Context
import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.dto.NetworkLanguageDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageRepository.Companion.DEFAULT_LANGUAGE
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDataSource
import java.util.Locale

class LanguageLocalDataSource(
    private val applicationContext: Context
): LanguageDataSource {

    override fun fetchLanguages(): List<NetworkLanguageDto> {

        val resources = applicationContext.resources

        Log.d("Language", "Fetching Languages")

        val languages = mutableListOf<NetworkLanguageDto>()

        val languageNames = resources.obtainTypedArray(R.array.language_names)
        val languageNativeNames = resources.obtainTypedArray(R.array.language_names_native)
        val languageCodes = listOf(*resources.getStringArray(R.array.language_codes))

        if((languageNames.length() == languageCodes.size) &&
            (languageNativeNames.length() == languageCodes.size)) {

            for(index in 0 until languageCodes.size) {
                val name = languageNames.getResourceId(index, 0)
                val nativeName = languageNativeNames.getResourceId(index, 0)
                val code = languageCodes[index]

                languages.add(NetworkLanguageDto(name, nativeName, code))
            }
        }

        // OVERRIDE DEFAULT LANGUAGE
        languages.find { language ->
            language.abbreviation.equals(Locale.getDefault().language, ignoreCase = true)
        }?.let { abbr ->
            DEFAULT_LANGUAGE = abbr.abbreviation
        }

        languageNativeNames.recycle()
        languageNames.recycle()

        Log.d("Language", "Finished fetching ${languages.size} languages")

        return languages
    }

}

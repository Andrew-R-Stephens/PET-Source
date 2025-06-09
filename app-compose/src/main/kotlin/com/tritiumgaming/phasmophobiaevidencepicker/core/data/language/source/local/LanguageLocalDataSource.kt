package com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.local

import android.content.Context
import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.dto.LanguageDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageRepository.Companion.DEFAULT_LANGUAGE
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDataSource
import java.util.Locale

class LanguageLocalDataSource(
    private val applicationContext: Context
): LanguageDataSource {

    override fun getAvailableLanguages(): Result<List<LanguageDto>> {

        Log.d("Language", "Fetching Languages")

        // OVERRIDE DEFAULT LANGUAGE
        languages.find { language ->
            applicationContext.getString(language.code)
                .equals(Locale.getDefault().language, ignoreCase = true)
        }?.let { language ->
            DEFAULT_LANGUAGE = applicationContext.getString(language.code)
        }

        Log.d("Language", "Finished fetching ${languages.size} languages")

        return Result.success(languages)
    }

    /*
    override fun getAvailableLanguages(): Result<List<LanguageDto>> {

        val resources = applicationContext.resources

        Log.d("Language", "Fetching Languages")

        val languages = mutableListOf<LanguageDto>()

        val languageNames = resources.obtainTypedArray(R.array.language_names)
        val languageNativeNames = resources.obtainTypedArray(R.array.language_names_native)
        val languageCodes = listOf(*resources.getStringArray(R.array.language_codes))

        if((languageNames.length() == languageCodes.size) &&
            (languageNativeNames.length() == languageCodes.size)) {

            for(index in 0 until languageCodes.size) {
                val name = languageNames.getResourceId(index, 0)
                val nativeName = languageNativeNames.getResourceId(index, 0)
                val code = languageCodes[index]

                languages.add(LanguageDto(name, nativeName, code))
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

        return Result.success(languages)
    }
    */

}

val languages: List<LanguageDto>
    get() = listOf(

        LanguageDto(
            localizedName = R.string.language_name_localized_zh_Hans,
            nativeName = R.string.language_name_native_zh_Hans,
            code = R.string.language_code_zh_Hans
        ),
        LanguageDto(
            localizedName = R.string.language_name_localized_cz,
            nativeName = R.string.language_name_native_cz,
            code = R.string.language_code_cz
        ),
        LanguageDto(
            localizedName = R.string.language_name_localized_de,
            nativeName = R.string.language_name_native_de,
            code = R.string.language_code_de
        ),
        LanguageDto(
            localizedName = R.string.language_name_localized_en,
            nativeName = R.string.language_name_native_en,
            code = R.string.language_code_en
        ),
        LanguageDto(
            localizedName = R.string.language_name_localized_es,
            nativeName = R.string.language_name_native_es,
            code = R.string.language_code_es
        ),
        LanguageDto(
            localizedName = R.string.language_name_localized_fr,
            nativeName = R.string.language_name_native_fr,
            code = R.string.language_code_fr
        ),
        LanguageDto(
            localizedName = R.string.language_name_localized_it,
            nativeName = R.string.language_name_native_it,
            code = R.string.language_code_it
        ),
        LanguageDto(
            localizedName = R.string.language_name_localized_ja,
            nativeName = R.string.language_name_native_ja,
            code = R.string.language_code_ja
        ),
        LanguageDto(
            localizedName = R.string.language_name_localized_pt,
            nativeName = R.string.language_name_native_pt,
            code = R.string.language_code_pt
        ),
        LanguageDto(
            localizedName = R.string.language_name_localized_ru,
            nativeName = R.string.language_name_native_ru,
            code = R.string.language_code_ru
        ),
        LanguageDto(
            localizedName = R.string.language_name_localized_uk,
            nativeName = R.string.language_name_native_uk,
            code = R.string.language_code_uk
        )

    )

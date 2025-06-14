package com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.local

import android.content.Context
import android.util.Log
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.dto.LanguageDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageRepository.Companion.DEFAULT_LANGUAGE
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDataSource
import java.util.Locale

class LanguageLocalDataSource(
    private val applicationContext: Context
): LanguageDataSource {

    private val languagesResource: List<LanguageResourceDto>
        get() = listOf(

            LanguageResourceDto(
                localizedName = R.string.language_name_localized_zh_Hans,
                nativeName = R.string.language_name_native_zh_Hans,
                code = R.string.language_code_zh_Hans
            ),
            LanguageResourceDto(
                localizedName = R.string.language_name_localized_cz,
                nativeName = R.string.language_name_native_cz,
                code = R.string.language_code_cz
            ),
            LanguageResourceDto(
                localizedName = R.string.language_name_localized_de,
                nativeName = R.string.language_name_native_de,
                code = R.string.language_code_de
            ),
            LanguageResourceDto(
                localizedName = R.string.language_name_localized_en,
                nativeName = R.string.language_name_native_en,
                code = R.string.language_code_en
            ),
            LanguageResourceDto(
                localizedName = R.string.language_name_localized_es,
                nativeName = R.string.language_name_native_es,
                code = R.string.language_code_es
            ),
            LanguageResourceDto(
                localizedName = R.string.language_name_localized_fr,
                nativeName = R.string.language_name_native_fr,
                code = R.string.language_code_fr
            ),
            LanguageResourceDto(
                localizedName = R.string.language_name_localized_it,
                nativeName = R.string.language_name_native_it,
                code = R.string.language_code_it
            ),
            LanguageResourceDto(
                localizedName = R.string.language_name_localized_ja,
                nativeName = R.string.language_name_native_ja,
                code = R.string.language_code_ja
            ),
            LanguageResourceDto(
                localizedName = R.string.language_name_localized_pt,
                nativeName = R.string.language_name_native_pt,
                code = R.string.language_code_pt
            ),
            LanguageResourceDto(
                localizedName = R.string.language_name_localized_ru,
                nativeName = R.string.language_name_native_ru,
                code = R.string.language_code_ru
            ),
            LanguageResourceDto(
                localizedName = R.string.language_name_localized_uk,
                nativeName = R.string.language_name_native_uk,
                code = R.string.language_code_uk
            )

        )

    override fun getAvailableLanguages(): Result<List<LanguageDto>> {

        Log.d("Language", "Fetching Languages")
        
        val languagesDto = languagesResource.toLanguageDto()

        // OVERRIDE DEFAULT LANGUAGE
        languagesDto.find { language ->
            applicationContext.getString(language.code)
                .equals(Locale.getDefault().language, ignoreCase = true)
        }?.let { language ->
            DEFAULT_LANGUAGE = applicationContext.getString(language.code)
        }

        Log.d("Language", "Finished fetching ${languagesResource.size} languages")

        return Result.success(languagesDto)
    }

    private fun LanguageResourceDto.toLanguageDto() = LanguageDto(
        localizedName = localizedName,
        nativeName = nativeName,
        code = code
    )
    
    private fun List<LanguageResourceDto>.toLanguageDto() = map { it.toLanguageDto() }
    
    private class LanguageResourceDto(
        @StringRes val localizedName: Int,
        @StringRes val nativeName: Int,
        @StringRes val code: Int
    )
    
}

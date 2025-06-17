package com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.source.local

import android.content.Context
import android.util.Log
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.language.dto.LanguageDto
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.mappers.LanguageResources.LocalizationCode
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.mappers.LanguageResources.LocalizedTitle
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.mappers.LanguageResources.NativeTitle
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.repository.LanguageRepository.Companion.DEFAULT_LANGUAGE
import com.tritiumgaming.phasmophobiaevidencepicker.core.domain.language.source.LanguageDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.app.mappers.toStringResource
import java.util.Locale

class LanguageLocalDataSource(
    private val applicationContext: Context
): LanguageDataSource {

    private val languagesResource: List<LanguageResourceDto>
        get() = listOf(

            LanguageResourceDto(
                localizedName = LocalizedTitle.ZH_HANS,
                nativeName = NativeTitle.ZH_HANS,
                code = LocalizationCode.ZH_HANS
            ),
            LanguageResourceDto(
                localizedName = LocalizedTitle.CS,
                nativeName = NativeTitle.CS,
                code = LocalizationCode.CS
            ),
            LanguageResourceDto(
                localizedName = LocalizedTitle.DE,
                nativeName = NativeTitle.DE,
                code = LocalizationCode.DE
            ),
            LanguageResourceDto(
                localizedName = LocalizedTitle.EN,
                nativeName = NativeTitle.EN,
                code = LocalizationCode.EN
            ),
            LanguageResourceDto(
                localizedName = LocalizedTitle.ES,
                nativeName = NativeTitle.ES,
                code = LocalizationCode.ES
            ),
            LanguageResourceDto(
                localizedName = LocalizedTitle.FR,
                nativeName = NativeTitle.FR,
                code = LocalizationCode.FR
            ),
            LanguageResourceDto(
                localizedName = LocalizedTitle.IT,
                nativeName = NativeTitle.IT,
                code = LocalizationCode.IT
            ),
            LanguageResourceDto(
                localizedName = LocalizedTitle.JA,
                nativeName = NativeTitle.JA,
                code = LocalizationCode.JA
            ),
            LanguageResourceDto(
                localizedName = LocalizedTitle.PT,
                nativeName = NativeTitle.PT,
                code = LocalizationCode.PT
            ),
            LanguageResourceDto(
                localizedName = LocalizedTitle.RU,
                nativeName = NativeTitle.RU,
                code = LocalizationCode.RU
            ),
            LanguageResourceDto(
                localizedName = LocalizedTitle.UK,
                nativeName = NativeTitle.UK,
                code = LocalizationCode.UK
            )

        )

    override fun getAvailableLanguages(): Result<List<LanguageDto>> {

        Log.d("Language", "Fetching Languages")
        
        val languagesDto = languagesResource.toLanguageDto()

        // OVERRIDE DEFAULT LANGUAGE
        languagesDto.find { language ->
            applicationContext.getString(language.code.toStringResource())
                .equals(Locale.getDefault().language, ignoreCase = true)
        }?.let { language ->
            DEFAULT_LANGUAGE = applicationContext.getString(language.code.toStringResource())
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
        val localizedName: LocalizedTitle,
        val nativeName: NativeTitle,
        val code: LocalizationCode
    )
    
}

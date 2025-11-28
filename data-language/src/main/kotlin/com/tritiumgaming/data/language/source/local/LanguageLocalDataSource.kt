package com.tritiumgaming.data.language.source.local

import android.content.Context
import android.util.Log
import com.tritiumgaming.data.language.dto.LanguageDto
import com.tritiumgaming.shared.data.language.mappers.LanguageResources.LocalizedTitle
import com.tritiumgaming.shared.data.language.mappers.LanguageResources.NativeTitle
import java.util.Locale

class LanguageLocalDataSource(
    private val applicationContext: Context
): LanguageDataSource {

    private val languagesResource: List<LanguageResourceDto>
        get() = listOf(

            LanguageResourceDto(
                localizedName = LocalizedTitle.ZH_HANS,
                nativeName = NativeTitle.ZH_HANS,
                code = Locale.SIMPLIFIED_CHINESE.language
            ),
            LanguageResourceDto(
                localizedName = LocalizedTitle.CS,
                nativeName = NativeTitle.CS,
                code = "cs"
            ),
            LanguageResourceDto(
                localizedName = LocalizedTitle.DE,
                nativeName = NativeTitle.DE,
                code = Locale.GERMAN.language
            ),
            LanguageResourceDto(
                localizedName = LocalizedTitle.EN,
                nativeName = NativeTitle.EN,
                code = Locale.ENGLISH.language
            ),
            LanguageResourceDto(
                localizedName = LocalizedTitle.ES,
                nativeName = NativeTitle.ES,
                code = "es"
            ),
            LanguageResourceDto(
                localizedName = LocalizedTitle.FR,
                nativeName = NativeTitle.FR,
                code = Locale.FRENCH.language
            ),
            LanguageResourceDto(
                localizedName = LocalizedTitle.IT,
                nativeName = NativeTitle.IT,
                code = Locale.ITALIAN.language
            ),
            LanguageResourceDto(
                localizedName = LocalizedTitle.JA,
                nativeName = NativeTitle.JA,
                code = Locale.JAPANESE.language
            ),
            LanguageResourceDto(
                localizedName = LocalizedTitle.PT,
                nativeName = NativeTitle.PT,
                code = "pt"
            ),
            LanguageResourceDto(
                localizedName = LocalizedTitle.RU,
                nativeName = NativeTitle.RU,
                code = "ru"
            ),
            LanguageResourceDto(
                localizedName = LocalizedTitle.UK,
                nativeName = NativeTitle.UK,
                code = "uk"
            )

        )

    override fun getAvailableLanguages(): Result<List<LanguageDto>> {

        Log.d("Language", "Fetching Languages")
        
        val languagesDto = languagesResource.toLanguageDto()

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
        val code: String
    )
    
}

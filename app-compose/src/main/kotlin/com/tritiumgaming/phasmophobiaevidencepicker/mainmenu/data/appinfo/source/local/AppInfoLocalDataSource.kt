package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.appinfo.source.local

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.appinfo.source.AppInfoDataSource

class AppInfoLocalDataSource(): AppInfoDataSource {

    private val specialThanksResourceDto
        get() = listOf(
            SpecialThanksResourceDto(
                username = "Bravo-1"
            ),
            SpecialThanksResourceDto(
                username = "DoctorCrow"
            ),
            SpecialThanksResourceDto(
                username = "Edahix"
            ),
            SpecialThanksResourceDto(
                username = "Miss Shuee"
            ),
            SpecialThanksResourceDto(
                username = "HAINER"
            )
        )


    override fun fetchSpecialThanks(): List<String> {

        val specialThanksList = mutableListOf<String>()

        specialThanksResourceDto.forEach { resDto ->
            specialThanksList.add(resDto.username)
        }

        return specialThanksList
    }

}

data class SpecialThanksResourceDto(
    val username: String
)
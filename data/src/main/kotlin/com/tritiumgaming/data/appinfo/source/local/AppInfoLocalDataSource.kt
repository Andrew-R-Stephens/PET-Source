package com.tritiumgaming.data.appinfo.source.local

import com.tritiumgaming.data.appinfo.dto.SpecialThanksContributorDto
import com.tritiumgaming.data.appinfo.source.AppInfoDataSource
import com.tritiumgaming.shared.mainmenu.domain.appinfo.model.SpecialThanksContributor

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


    override fun fetchSpecialThanks(): List<SpecialThanksContributorDto> =
        specialThanksResourceDto.toSpecialThanksContributorDto()


    private fun SpecialThanksResourceDto.toSpecialThanksContributorDto() = SpecialThanksContributorDto(
        username = username
    )

    private fun List<SpecialThanksResourceDto>.toSpecialThanksContributorDto() =
        map { it.toSpecialThanksContributorDto() }

    private data class SpecialThanksResourceDto(
        val username: String
    )
}

package com.tritiumgaming.data.appinfo.dto

import com.tritiumgaming.shared.mainmenu.domain.appinfo.model.SpecialThanksContributor

data class SpecialThanksContributorDto(
    val username: String
)

fun List<SpecialThanksContributorDto>.toDomain() = map { it.toDomain() }

fun SpecialThanksContributorDto.toDomain(): SpecialThanksContributor {
    return SpecialThanksContributor(
        username = username
    )
}

package com.tritiumgaming.data.contributor.dto

import com.tritiumgaming.shared.home.domain.appinfo.model.Contributor

data class ContributorDto(
    val username: String
)

fun List<ContributorDto>.toDomain() = map { it.toDomain() }

fun ContributorDto.toDomain(): Contributor {
    return Contributor(
        username = username
    )
}

package com.tritiumgaming.data.contributor.source.local

import com.tritiumgaming.data.contributor.dto.ContributorDto
import com.tritiumgaming.data.contributor.source.ContributorDataSource

class ContributorLocalDataSource(): ContributorDataSource {

    private val contributorDto
        get() = listOf(
            ContributorResourceDto(
                username = "Bravo-1"
            ),
            ContributorResourceDto(
                username = "DoctorCrow"
            ),
            ContributorResourceDto(
                username = "Edahix"
            ),
            ContributorResourceDto(
                username = "Miss Shuee"
            ),
            ContributorResourceDto(
                username = "HAINER"
            )
        )


    override fun fetchContributors(): List<ContributorDto> =
        contributorDto.toContributorDto()


    private fun ContributorResourceDto.toContributorDto() = ContributorDto(
        username = username
    )

    private fun List<ContributorResourceDto>.toContributorDto() =
        map { it.toContributorDto() }

    private data class ContributorResourceDto(
        val username: String
    )
}

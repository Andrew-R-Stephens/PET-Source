package com.tritiumgaming.data.contributor.source

import com.tritiumgaming.data.contributor.dto.ContributorDto

interface ContributorDataSource {

    fun fetchContributors(): List<ContributorDto>

}
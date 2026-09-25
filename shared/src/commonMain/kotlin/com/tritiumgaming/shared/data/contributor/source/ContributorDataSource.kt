package com.tritiumgaming.shared.data.contributor.source

import com.tritiumgaming.shared.data.contributor.dto.ContributorDto

interface ContributorDataSource {

    fun fetchContributors(): List<ContributorDto>

}

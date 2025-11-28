package com.tritiumgaming.data.contributor.repository

import com.tritiumgaming.data.contributor.dto.toDomain
import com.tritiumgaming.data.contributor.source.ContributorDataSource
import com.tritiumgaming.shared.data.contributor.model.Contributor
import com.tritiumgaming.shared.data.contributor.repository.ContributorRepository

class ContributorRepositoryImpl(
    val localSource: ContributorDataSource
): ContributorRepository {

    override fun getSpecialThanks(): Result<List<Contributor>> {
        return Result.success(localSource.fetchContributors().toDomain())
    }

}

package com.tritiumgaming.shared.data.contributor.repository

import com.tritiumgaming.shared.data.contributor.dto.toDomain
import com.tritiumgaming.shared.data.contributor.model.Contributor
import com.tritiumgaming.shared.data.contributor.source.ContributorDataSource

class ContributorRepositoryImpl(
    val localSource: ContributorDataSource
): ContributorRepository {

    override fun getSpecialThanks(): Result<List<Contributor>> {
        return Result.success(localSource.fetchContributors().toDomain())
    }

}

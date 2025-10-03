package com.tritiumgaming.data.contributor.repository

import com.tritiumgaming.data.contributor.dto.toDomain
import com.tritiumgaming.data.contributor.source.ContributorDataSource
import com.tritiumgaming.shared.mainmenu.domain.appinfo.model.Contributor
import com.tritiumgaming.shared.mainmenu.domain.appinfo.repository.AppInfoRepository

class AppInfoRepositoryImpl(
    val localSource: ContributorDataSource
): AppInfoRepository {

    override fun getSpecialThanks(): Result<List<Contributor>> {
        return Result.success(localSource.fetchContributors().toDomain())
    }

}

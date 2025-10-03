package com.tritiumgaming.data.appinfo.repository

import com.tritiumgaming.data.appinfo.dto.toDomain
import com.tritiumgaming.data.appinfo.source.AppInfoDataSource
import com.tritiumgaming.shared.mainmenu.domain.appinfo.model.SpecialThanksContributor
import com.tritiumgaming.shared.mainmenu.domain.appinfo.repository.AppInfoRepository

class AppInfoRepositoryImpl(
    val localSource: AppInfoDataSource
): AppInfoRepository {

    override fun getSpecialThanks(): Result<List<SpecialThanksContributor>> {
        return Result.success(localSource.fetchSpecialThanks().toDomain())
    }

}

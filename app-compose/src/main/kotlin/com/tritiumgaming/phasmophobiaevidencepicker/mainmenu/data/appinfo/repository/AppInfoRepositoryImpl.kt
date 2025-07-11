package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.appinfo.repository

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.appinfo.dto.toDomain
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.appinfo.source.AppInfoDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.appinfo.model.SpecialThanksContributor
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.appinfo.repository.AppInfoRepository

class AppInfoRepositoryImpl(
    val localSource: AppInfoDataSource
): AppInfoRepository {

    override fun getSpecialThanks(): Result<List<SpecialThanksContributor>> {
        return Result.success(localSource.fetchSpecialThanks().toDomain())
    }

}

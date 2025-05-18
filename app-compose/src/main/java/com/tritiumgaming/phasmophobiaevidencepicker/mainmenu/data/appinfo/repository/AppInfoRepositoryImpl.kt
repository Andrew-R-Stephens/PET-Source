package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.appinfo.repository

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.appinfo.repository.AppInfoRepository
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.appinfo.source.AppInfoDataSource

class AppInfoRepositoryImpl(
    val localSource: AppInfoDataSource
): AppInfoRepository {

    var specialThanksList: List<String> = listOf()

    override fun getSpecialThanks(): List<String> {
        return localSource.fetchSpecialThanks()
    }

}

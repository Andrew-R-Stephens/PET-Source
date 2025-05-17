package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.appinfo.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.appinfo.repository.AppInfoRepository
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.appinfo.source.AppInfoDataSource

class AppInfoRepositoryImpl(
    context: Context,
    val localSource: AppInfoDataSource
): AppInfoRepository {

    var specialThanksList: List<String> = listOf()

    override fun fetchSpecialThanks(context: Context): List<String> {
        return localSource.fetchSpecialThanks(context)
    }

    init {
        specialThanksList = fetchSpecialThanks(context)
    }

}

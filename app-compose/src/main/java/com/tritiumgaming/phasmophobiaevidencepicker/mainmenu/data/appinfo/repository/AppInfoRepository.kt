package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.appinfo.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.appinfo.source.local.AppInfoLocalDataSourceInterface

class AppInfoRepository(
    context: Context,
    val localSource: AppInfoLocalDataSourceInterface
): AppInfoRepositoryInterface {

    var specialThanksList: List<String> = listOf()

    override fun fetchSpecialThanks(context: Context): List<String> {
        return localSource.fetchSpecialThanks(context)
    }

    init {
        specialThanksList = fetchSpecialThanks(context)
    }

}

interface AppInfoRepositoryInterface {
    fun fetchSpecialThanks(context: Context): List<String>
}
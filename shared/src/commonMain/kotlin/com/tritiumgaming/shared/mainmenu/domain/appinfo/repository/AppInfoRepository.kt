package com.tritiumgaming.shared.mainmenu.domain.appinfo.repository

import com.tritiumgaming.shared.mainmenu.domain.appinfo.model.Contributor


interface AppInfoRepository {

    fun getSpecialThanks(): Result<List<Contributor>>

}
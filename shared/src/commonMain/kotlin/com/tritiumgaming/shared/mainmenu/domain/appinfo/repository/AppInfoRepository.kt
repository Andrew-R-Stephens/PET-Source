package com.tritiumgaming.shared.mainmenu.domain.appinfo.repository

import com.tritiumgaming.shared.mainmenu.domain.appinfo.model.SpecialThanksContributor


interface AppInfoRepository {

    fun getSpecialThanks(): Result<List<SpecialThanksContributor>>

}
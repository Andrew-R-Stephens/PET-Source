package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.appinfo.repository

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.appinfo.model.SpecialThanksContributor

interface AppInfoRepository {

    fun getSpecialThanks(): Result<List<SpecialThanksContributor>>

}
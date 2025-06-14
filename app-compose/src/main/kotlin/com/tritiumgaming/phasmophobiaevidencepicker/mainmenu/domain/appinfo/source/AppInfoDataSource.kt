package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.appinfo.source

import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.appinfo.dto.SpecialThanksContributorDto

interface AppInfoDataSource {

    fun fetchSpecialThanks(): List<SpecialThanksContributorDto>

}
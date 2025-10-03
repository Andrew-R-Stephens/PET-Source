package com.tritiumgaming.data.appinfo.source

import com.tritiumgaming.data.appinfo.dto.SpecialThanksContributorDto

interface AppInfoDataSource {

    fun fetchSpecialThanks(): List<SpecialThanksContributorDto>

}
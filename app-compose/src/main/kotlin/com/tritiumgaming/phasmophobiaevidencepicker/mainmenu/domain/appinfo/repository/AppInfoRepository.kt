package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.appinfo.repository

interface AppInfoRepository {
    fun getSpecialThanks(): Result<List<String>>
}
package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.appinfo.repository

interface AppInfoRepository {
    fun getSpecialThanks(): List<String>
}
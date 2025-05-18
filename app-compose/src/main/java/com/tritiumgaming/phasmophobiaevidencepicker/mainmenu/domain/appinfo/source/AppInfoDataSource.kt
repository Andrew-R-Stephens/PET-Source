package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.appinfo.source

interface AppInfoDataSource {

    fun fetchSpecialThanks(): List<String>

}
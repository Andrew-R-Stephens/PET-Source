package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.appinfo.source

import android.content.Context

interface AppInfoDataSource {
    fun fetchSpecialThanks(context: Context): List<String>
}
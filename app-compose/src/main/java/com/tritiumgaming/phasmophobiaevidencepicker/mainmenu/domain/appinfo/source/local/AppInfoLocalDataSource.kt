package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.appinfo.source.local

import android.content.Context

interface AppInfoLocalDataSource {
    fun fetchSpecialThanks(context: Context): List<String>
}
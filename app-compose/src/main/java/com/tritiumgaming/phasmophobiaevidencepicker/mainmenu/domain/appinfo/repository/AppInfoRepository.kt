package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.appinfo.repository

import android.content.Context

interface AppInfoRepository {
    fun fetchSpecialThanks(context: Context): List<String>
}
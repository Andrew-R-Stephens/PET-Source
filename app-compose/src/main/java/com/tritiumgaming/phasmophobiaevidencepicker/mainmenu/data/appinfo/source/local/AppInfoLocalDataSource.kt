package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.appinfo.source.local

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.domain.appinfo.source.AppInfoDataSource


class AppInfoLocalDataSource(
    private val applicationContext: Context
): AppInfoDataSource {

    override fun fetchSpecialThanks(): List<String> {

        val resources = applicationContext.resources

        val specialThanksList = mutableListOf<String>()

        val typedArray = resources.obtainTypedArray(R.array.aboutinfo_specialthanks_list)
        for (i in 0 until typedArray.length()) {
            typedArray.getString(i)?.let { name ->
                specialThanksList.add(name)
            }
        }
        typedArray.recycle()

        return specialThanksList
    }
}

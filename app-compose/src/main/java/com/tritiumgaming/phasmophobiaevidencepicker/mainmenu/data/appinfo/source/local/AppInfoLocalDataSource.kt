package com.tritiumgaming.phasmophobiaevidencepicker.mainmenu.data.appinfo.source.local

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.R


class AppInfoLocalDataSource: AppInfoLocalDataSourceInterface {

    override fun fetchSpecialThanks(context: Context): List<String> {
        val specialThanksList = mutableListOf<String>()

        val typedArray = context.resources.obtainTypedArray(R.array.aboutinfo_specialthanks_list)
        for (i in 0 until typedArray.length()) {
            typedArray.getString(i)?.let { name ->
                specialThanksList.add(name)
            }
        }
        typedArray.recycle()

        return specialThanksList
    }
}

interface AppInfoLocalDataSourceInterface {
    fun fetchSpecialThanks(context: Context): List<String>
}
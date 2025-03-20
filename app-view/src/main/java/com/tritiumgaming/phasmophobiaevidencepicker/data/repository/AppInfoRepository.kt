package com.tritiumgaming.phasmophobiaevidencepicker.data.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.R

class AppInfoRepository(
    context: Context
) {

    val specialThanksList = mutableListOf<String>()

    init {
        populateSpecialThanksList(context)
    }

    private fun populateSpecialThanksList(context: Context) {
        val typedArray = context.resources.obtainTypedArray(R.array.aboutinfo_specialthanks_list)

        for (i in 0 until typedArray.length()) {
            typedArray.getString(i)?.let { name ->
                specialThanksList.add(name)
            }
        }

        typedArray.recycle()
    }

}

package com.tritiumgaming.phasmophobiaevidencepicker.data.repository

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.R

class AppInfoRepository(
    context: Context
) {

    val specialThanksList: ArrayList<String> = ArrayList(0)

    init {

        populateSpecialThanksList(context)

    }

    private fun populateSpecialThanksList(context: Context) {
        specialThanksList.clear()

        val typedArray =
            context.resources.obtainTypedArray(R.array.aboutinfo_specialthanks_list)

        for (i in 0 until typedArray.length()) {
            typedArray.getString(i)
        }

        typedArray.recycle()
    }

}

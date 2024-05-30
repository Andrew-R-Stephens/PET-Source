package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.popups

import android.content.Context
import androidx.annotation.StringRes
import com.TritiumGaming.phasmophobiaevidencepicker.R

class GhostPopupModel(context: Context) : InvestigationPopupModel() {

    @StringRes private val infos: IntArray
    @StringRes private val strengths: IntArray
    @StringRes private val weaknesses: IntArray
    @StringRes private val huntData: IntArray

    init {
        val resources = context.resources

        val infoTypedArray = resources.obtainTypedArray(R.array.ghost_info_array)
        val strengthsTypedArray = resources.obtainTypedArray(R.array.ghost_strengths_array)
        val weaknessesTypedArray = resources.obtainTypedArray(R.array.ghost_weaknesses_array)
        val huntDataTypedArray = resources.obtainTypedArray(R.array.ghost_huntdata_array)

        infos = intArrayFromTypedArray(resources, infoTypedArray)
        strengths = intArrayFromTypedArray(resources, strengthsTypedArray)
        weaknesses = intArrayFromTypedArray(resources, weaknessesTypedArray)
        huntData = intArrayFromTypedArray(resources, huntDataTypedArray)
    }

    fun getCycleDetails(c: Context, i: Int): Array<String?> {
        val cycleDetails = arrayOfNulls<String>(3)
        cycleDetails[0] = getInfo(c, i)
        cycleDetails[1] = getStrength(c, i)
        cycleDetails[2] = getWeakness(c, i)

        return cycleDetails
    }

    private fun getWeakness(c: Context, i: Int): String {
        return c.getString(weaknesses[i])
    }

    fun getHuntData(c: Context, i: Int): String {
        return c.getString(huntData[i])
    }

    private fun getStrength(c: Context, i: Int): String {
        return c.getString(strengths[i])
    }

    private fun getInfo(c: Context, i: Int): String {
        return c.getString(infos[i])
    }

    @StringRes
    fun getWeakness(i: Int): Int {
        return weaknesses[i]
    }

    @StringRes
    fun getHuntData(i: Int): Int {
        return huntData[i]
    }

    @StringRes
    fun getStrength(i: Int): Int {
        return strengths[i]
    }

    @StringRes
    fun getInfo(i: Int): Int {
        return infos[i]
    }
}

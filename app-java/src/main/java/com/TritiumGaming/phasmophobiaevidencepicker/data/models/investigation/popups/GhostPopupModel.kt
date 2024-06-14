package com.TritiumGaming.phasmophobiaevidencepicker.data.models.investigation.popups

import android.content.Context
import androidx.annotation.StringRes
import com.TritiumGaming.phasmophobiaevidencepicker.R

class GhostPopupModel(context: Context) : InvestigationPopupModel() {

    @StringRes private val infoArray: IntArray
    @StringRes private val strengthArray: IntArray
    @StringRes private val weaknessArray: IntArray
    @StringRes private val huntDataArray: IntArray

    init {
        val resources = context.resources

        val infoTypedArray = resources.obtainTypedArray(R.array.ghost_info_array)
        val strengthsTypedArray = resources.obtainTypedArray(R.array.ghost_strengths_array)
        val weaknessesTypedArray = resources.obtainTypedArray(R.array.ghost_weaknesses_array)
        val huntDataTypedArray = resources.obtainTypedArray(R.array.ghost_huntdata_array)

        infoArray = intArrayFromTypedArray(resources, infoTypedArray)
        strengthArray = intArrayFromTypedArray(resources, strengthsTypedArray)
        weaknessArray = intArrayFromTypedArray(resources, weaknessesTypedArray)
        huntDataArray = intArrayFromTypedArray(resources, huntDataTypedArray)
    }

    fun getCycleDetails(c: Context, i: Int): MutableList<String> {
        val cycleDetails = mutableListOf<String>()
        cycleDetails.add(0, getInfo(c, i))
        cycleDetails.add(1, getStrength(c, i))
        cycleDetails.add(2, getWeakness(c, i))

        return cycleDetails
    }

    private fun getWeakness(c: Context, i: Int): String {
        return c.getString(weaknessArray[i])
    }

    fun getHuntData(c: Context, i: Int): String {
        return c.getString(huntDataArray[i])
    }

    private fun getStrength(c: Context, i: Int): String {
        return c.getString(strengthArray[i])
    }

    private fun getInfo(c: Context, i: Int): String {
        return c.getString(infoArray[i])
    }

    @StringRes
    fun getWeakness(i: Int): Int {
        return weaknessArray[i]
    }

    @StringRes
    fun getHuntData(i: Int): Int {
        return huntDataArray[i]
    }

    @StringRes
    fun getStrength(i: Int): Int {
        return strengthArray[i]
    }

    @StringRes
    fun getInfo(i: Int): Int {
        return infoArray[i]
    }
}

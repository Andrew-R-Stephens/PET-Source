package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.popups

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.TritiumGaming.phasmophobiaevidencepicker.R

class EvidencePopupModel(context: Context) : InvestigationPopupModel() {

    private val evidenceViewDataList = mutableListOf<EvidencePopupRecord>()

    val count: Int
        get() = evidenceViewDataList.size

    init {
        val resources = context.resources

        val keyEvidenceName = 0
        val keyDescriptions = 1
        val keyAnimationResources = 2
        val keyEvidenceCost = 3
        val keyUnlockLevels = 4

        val evidenceTypes = resources.obtainTypedArray(R.array.equipment_tiers_arrays)
        for (i in 0 until evidenceTypes.length()) {
            val evidenceType =
                resources.obtainTypedArray(evidenceTypes.getResourceId(i, 0))

            @StringRes val evidenceName = evidenceType.getResourceId(keyEvidenceName, 0)
            @StringRes val descriptions = intArrayFromTypedArray(resources, evidenceType, keyDescriptions)
            @DrawableRes val animationResources = intArrayFromTypedArray(resources, evidenceType, keyAnimationResources)
            @IntegerRes val evidenceCost = evidenceType.getResourceId(keyEvidenceCost, 0)
            @IntegerRes val unlockLevels = intArrayFromTypedArray(resources, evidenceType, keyUnlockLevels)

            evidenceType.recycle()

            val evidenceViewData = EvidencePopupRecord(
                i, evidenceName, evidenceCost, unlockLevels, descriptions, animationResources)

            evidenceViewDataList.add(evidenceViewData)
        }

        evidenceTypes.recycle()
    }

    fun getEvidencePopupRecordAt(index: Int): EvidencePopupRecord {
        return evidenceViewDataList[index]
    }

    @JvmRecord
    data class EvidencePopupRecord(
        @JvmField val index: Int,
        @StringRes val name: Int,
        @IntegerRes val cost: Int,
        @IntegerRes val unlock_level: IntArray,
        @StringRes val descriptions: IntArray,
        @DrawableRes val animations: IntArray
    ) {
        fun getName(c: Context): String {
            return c.getString(name)
        }

        fun getCost(c: Context): String {
            return c.resources.getInteger(cost).toString()
        }

        @DrawableRes
        fun getAnimationAt(i: Int): Int {
            return animations[i]
        }

        fun getDescriptionAt(c: Context, i: Int): String {
            return c.getString(descriptions[i])
        }

        fun getUnlockLevelAt(c: Context, i: Int): String {
            return c.resources.getInteger(unlock_level[i]).toString()
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as EvidencePopupRecord

            if (index != other.index) return false
            if (name != other.name) return false
            if (cost != other.cost) return false
            if (!unlock_level.contentEquals(other.unlock_level)) return false
            if (!descriptions.contentEquals(other.descriptions)) return false
            if (!animations.contentEquals(other.animations)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = index
            result = 31 * result + name
            result = 31 * result + cost
            result = 31 * result + unlock_level.contentHashCode()
            result = 31 * result + descriptions.contentHashCode()
            result = 31 * result + animations.contentHashCode()
            return result
        }
    }
}

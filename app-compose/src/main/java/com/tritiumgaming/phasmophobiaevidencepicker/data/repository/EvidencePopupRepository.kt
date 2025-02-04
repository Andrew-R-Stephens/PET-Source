package com.tritiumgaming.phasmophobiaevidencepicker.data.repository

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.popups.InvestigationPopupModel

class EvidencePopupRepository(
    context: Context
) : InvestigationPopupModel() {

    private val evidenceViewDataList = mutableListOf<EvidencePopupRecord>()

    val count: Int
        get() = evidenceViewDataList.size

    companion object{
        const val KEY_EVIDENCE_NAME = 0
        const val KEY_DESCRIPTION = 1
        const val KEY_ANIMATION_RES = 2
        const val KEY_COST = 3
        const val KEY_UNLOCK_LEVEL = 4
    }

    init {
        val resources = context.resources

        val evidenceTypes = resources.obtainTypedArray(R.array.equipment_tiers_arrays)
        for (i in 0 until evidenceTypes.length()) {
            val evidenceType =
                resources.obtainTypedArray(evidenceTypes.getResourceId(i, 0))

            @StringRes val evidenceName = evidenceType.getResourceId(KEY_EVIDENCE_NAME, 0)
            @StringRes val descriptions = intArrayFromTypedArray(resources, evidenceType, KEY_DESCRIPTION)
            @DrawableRes val animationResources = intArrayFromTypedArray(resources, evidenceType, KEY_ANIMATION_RES)
            @IntegerRes val evidenceCost = evidenceType.getResourceId(KEY_COST, 0)
            @IntegerRes val unlockLevels = intArrayFromTypedArray(resources, evidenceType, KEY_UNLOCK_LEVEL)

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

    data class EvidencePopupRecord(
        val index: Int,
        @StringRes val name: Int,
        @IntegerRes val cost: Int,
        @IntegerRes val unlockLevel: IntArray,
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
            return c.resources.getInteger(unlockLevel[i]).toString()
        }

        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as EvidencePopupRecord

            if (index != other.index) return false
            if (name != other.name) return false
            if (cost != other.cost) return false
            if (!unlockLevel.contentEquals(other.unlockLevel)) return false
            if (!descriptions.contentEquals(other.descriptions)) return false
            if (!animations.contentEquals(other.animations)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = index
            result = 31 * result + name
            result = 31 * result + cost
            result = 31 * result + unlockLevel.contentHashCode()
            result = 31 * result + descriptions.contentHashCode()
            result = 31 * result + animations.contentHashCode()
            return result
        }
    }
}

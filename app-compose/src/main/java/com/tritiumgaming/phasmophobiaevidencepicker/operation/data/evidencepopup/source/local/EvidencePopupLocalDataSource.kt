package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.evidencepopup.source.local

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.util.ResourceUtils.intArrayFromTypedArray

class EvidencePopupLocalDataSource {

    fun fetchPopups(
        context: Context
    ): List<EvidencePopupRecord> {

        val evidencePopupRecords = mutableListOf<EvidencePopupRecord>()

        val keyId = 0
        val keyDescription = 3
        val keyAnimationRes = 4
        val keyBuyCost = 5
        val keyUnlockLevel = 6

        val resources = context.resources

        val evidenceTypes = resources.obtainTypedArray(R.array.evidence_tiers_arrays)
        for (i in 0 until evidenceTypes.length()) {
            val evidenceType =
                resources.obtainTypedArray(evidenceTypes.getResourceId(i, 0))

            @StringRes val id = evidenceType.getResourceId(keyId, 0)

            @StringRes val descriptions =
                intArrayFromTypedArray(
                    resources,
                    evidenceType,
                    keyDescription
                )
            @DrawableRes val animations =
                intArrayFromTypedArray(
                    resources,
                    evidenceType,
                    keyAnimationRes
                )
            @IntegerRes val cost = evidenceType.getResourceId(keyBuyCost, 0)
            @IntegerRes val unlockLevels =
                intArrayFromTypedArray(
                    resources,
                    evidenceType,
                    keyUnlockLevel
                )

            evidenceType.recycle()

            val record = EvidencePopupRecord(
                id = resources.getString(id),
                cost = cost,
                unlockLevel = unlockLevels,
                descriptions = descriptions,
                animations = animations
            )

            evidencePopupRecords.add(record)
        }

        evidenceTypes.recycle()

        return evidencePopupRecords
    }

    data class EvidencePopupRecord(
        val id: String = "0",
        @IntegerRes val cost: Int,
        @IntegerRes val unlockLevel: IntArray,
        @StringRes val descriptions: IntArray,
        @DrawableRes val animations: IntArray
    ) {

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

            if (id != other.id) return false
            if (cost != other.cost) return false
            if (!unlockLevel.contentEquals(other.unlockLevel)) return false
            if (!descriptions.contentEquals(other.descriptions)) return false
            if (!animations.contentEquals(other.animations)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = cost
            result = 31 * result + id.hashCode()
            result = 31 * result + unlockLevel.contentHashCode()
            result = 31 * result + descriptions.contentHashCode()
            result = 31 * result + animations.contentHashCode()
            return result
        }

    }
}
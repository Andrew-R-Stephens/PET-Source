package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.popup.evidence.source.local

import android.content.Context
import android.content.res.Resources
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.presentation.util.ResourceUtils.intArrayFromTypedArray
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.model.EvidencePopupRecord
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.source.EvidencePopupDataSource

class EvidencePopupLocalDataSource(
    private val applicationContext: Context
): EvidencePopupDataSource {

    override fun fetchPopups(): List<EvidencePopupRecord> {

        val resources = applicationContext.resources

        val records = mutableListOf<EvidencePopupRecord>()

        val evidencesTypedArray = resources.obtainTypedArray(R.array.evidence_tiers_arrays)
        for(i in 0 until evidencesTypedArray.length()) {
            val evidenceTypedArrayId = evidencesTypedArray.getResourceId(i, 0)
            records.add(
                readEvidence(resources, evidenceTypedArrayId)
            )
        }
        evidencesTypedArray.recycle()

        return records
    }

    private fun readEvidence(
        resources: Resources,
        evidencesArrayID: Int
    ): EvidencePopupRecord {

        val evidenceTypedArray = resources.obtainTypedArray(evidencesArrayID)

        val keyId = 0
        val keyDescription = 3
        val keyAnimationRes = 4
        val keyBuyCost = 5
        val keyUnlockLevel = 6

        @StringRes val id = evidenceTypedArray.getResourceId(keyId, 0)
        @StringRes val descriptions = intArrayFromTypedArray(
            resources, evidenceTypedArray, keyDescription)
        @DrawableRes val animations = intArrayFromTypedArray(
            resources, evidenceTypedArray, keyAnimationRes)
        @IntegerRes val cost = evidenceTypedArray.getResourceId(keyBuyCost, 0)
        @IntegerRes val unlockLevels = intArrayFromTypedArray(
            resources, evidenceTypedArray, keyUnlockLevel)

        evidenceTypedArray.recycle()

        val record = EvidencePopupRecord(
            id = resources.getString(id),
            cost = cost,
            unlockLevel = unlockLevels,
            descriptions = descriptions,
            animations = animations
        )

        return record
    }

}
package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.popup.evidence.source.local

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.util.ResourceUtils.intArrayFromTypedArray
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.model.EvidencePopupRecord
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.popup.source.EvidencePopupDataSource

class EvidencePopupLocalDataSource: EvidencePopupDataSource {

    override fun fetchPopups(
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

}
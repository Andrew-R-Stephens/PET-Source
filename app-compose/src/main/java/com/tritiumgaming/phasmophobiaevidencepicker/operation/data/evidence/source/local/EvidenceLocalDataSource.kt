package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.evidence.source.local

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.evidence.source.EvidenceDataSource
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.type.EvidenceType

class EvidenceLocalDataSource: EvidenceDataSource {

    override fun fetchEvidence(context: Context): List<EvidenceType> {

        val evidenceList = mutableListOf<EvidenceType>()

        val resources = context.resources

        val keyEvidenceID = 0
        val keyEvidenceName = 1
        val keyEvidenceIcon = 2

        val evidenceTypes = resources.obtainTypedArray(R.array.evidence_tiers_arrays)
        for (i in 0 until evidenceTypes.length()) {

            val evidenceType = resources.obtainTypedArray(evidenceTypes.getResourceId(i, 0))

            // Set Popup
            @StringRes val evidenceID = evidenceType.getResourceId(keyEvidenceID, 0)
            @StringRes val evidenceName = evidenceType.getResourceId(keyEvidenceName, 0)
            @DrawableRes val evidenceIcon = evidenceType.getResourceId(keyEvidenceIcon, 0)

            val evidence = EvidenceType(
                id = resources.getString(evidenceID),
                name = evidenceName,
                icon = evidenceIcon
            )

            evidenceList.add(evidence)

            evidenceType.recycle()

        }

        evidenceTypes.recycle()

        return evidenceList
    }

}
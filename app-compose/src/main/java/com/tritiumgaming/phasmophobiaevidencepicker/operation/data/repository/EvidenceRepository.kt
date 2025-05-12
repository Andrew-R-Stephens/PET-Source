package com.tritiumgaming.phasmophobiaevidencepicker.operation.data.repository

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.core.data.util.ResourceUtils.intArrayFromTypedArray
import com.tritiumgaming.phasmophobiaevidencepicker.operation.data.model.investigation.popups.EvidencePopup
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.model.investigation.investigationmodels.investigationtype.evidence.EvidenceType

class EvidenceRepository(
    context: Context
) {

    val evidenceList = mutableListOf<EvidenceType>()

    init {
        val resources = context.resources

        // Populate Evidence
        evidenceList.clear()

        //val iconsTypedArray = resources.obtainTypedArray(R.array.evidence_icon_array)

        val keyEvidenceID = 0
        val keyEvidenceName = 1
        val keyEvidenceIcon = 2
        val keyDescriptions = 3
        val keyAnimationResources = 4
        val keyEvidenceCost = 5
        val keyUnlockLevels = 6

        val evidenceTypes = resources.obtainTypedArray(R.array.equipment_tiers_arrays)
        for (i in 0 until evidenceTypes.length()) {

            val evidenceType = resources.obtainTypedArray(evidenceTypes.getResourceId(i, 0))

            // Set Popup

            @StringRes val evidenceID = evidenceType.getResourceId(keyEvidenceID, 0)
            @StringRes val evidenceName = evidenceType.getResourceId(keyEvidenceName, 0)
            @DrawableRes val evidenceIcon = evidenceType.getResourceId(keyEvidenceIcon, 0)
            @IntegerRes val evidenceCost = evidenceType.getResourceId(keyEvidenceCost, 0)
            @IntegerRes val unlockLevels = intArrayFromTypedArray(resources, evidenceType, keyUnlockLevels)
            @StringRes val descriptions = intArrayFromTypedArray(resources, evidenceType, keyDescriptions)
            @DrawableRes val animationResources = intArrayFromTypedArray(resources, evidenceType, keyAnimationResources)

            val evidencePopupModel = EvidencePopup(
                evidenceCost,
                unlockLevels,
                descriptions,
                animationResources
            )

            val evidence = EvidenceType(
                id = evidenceID,
                name = evidenceName,
                icon = evidenceIcon,
                popupModel = evidencePopupModel
            )

            evidenceList.add(evidence)

            evidenceType.recycle()

        }

        evidenceTypes.recycle()

    }
}

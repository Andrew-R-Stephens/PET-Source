package com.tritiumgaming.phasmophobiaevidencepicker.data.repository

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.evidence.EvidenceModel
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.popups.EvidencePopupModel
import com.tritiumgaming.phasmophobiaevidencepicker.util.ResourceUtils.ResourceUtils.intArrayFromTypedArray

class EvidenceRepository(
    context: Context
) {

    val evidenceList: ArrayList<EvidenceModel> = ArrayList(0)

    val count: Int
        get() = evidenceList.size

    init {
        val resources = context.resources

        // Populate Evidence

        evidenceList.clear()

        val idsTypedArray = resources.obtainTypedArray(R.array.evidence_type_ids)
        val idsArray = intArrayFromTypedArray(resources, idsTypedArray)

        val typedArray = resources.obtainTypedArray(R.array.evidence_icon_array)

        val keyEvidenceName = 0
        val keyDescriptions = 1
        val keyAnimationResources = 2
        val keyEvidenceCost = 3
        val keyUnlockLevels = 4

        val evidenceTypes = resources.obtainTypedArray(R.array.equipment_tiers_arrays)
        for (i in 0 until evidenceTypes.length()) {

            val evidenceType = resources.obtainTypedArray(evidenceTypes.getResourceId(i, 0))

            // Set Popup

            @StringRes val evidenceName = evidenceType.getResourceId(keyEvidenceName, 0)
            @StringRes val descriptions = intArrayFromTypedArray(resources, evidenceType, keyDescriptions)
            @DrawableRes val animationResources = intArrayFromTypedArray(resources, evidenceType, keyAnimationResources)
            @IntegerRes val evidenceCost = evidenceType.getResourceId(keyEvidenceCost, 0)
            @IntegerRes val unlockLevels = intArrayFromTypedArray(resources, evidenceType, keyUnlockLevels)

            val evidencePopupModel = EvidencePopupModel(
                evidenceCost,
                unlockLevels,
                descriptions,
                animationResources
            )

            val evidence = EvidenceModel(
                id = resources.getInteger(if(i <= idsArray.size) idsArray[i] else 0),
                name = evidenceName,
                icon = typedArray.getResourceId(i, 0),
                popupModel = evidencePopupModel
            )

            evidenceList.add(evidence)

            evidenceType.recycle()

        }

        evidenceTypes.recycle()
        typedArray.recycle()

    }

}

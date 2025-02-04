package com.tritiumgaming.phasmophobiaevidencepicker.data.repository

import android.content.Context
import androidx.annotation.IntegerRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.evidence.EvidenceModel
import com.tritiumgaming.phasmophobiaevidencepicker.util.ResourceUtils.ResourceUtils.intArrayFromTypedArray

class EvidenceRepository(
    context: Context
) {

    val evidenceList: ArrayList<EvidenceModel> = ArrayList(0)

    val count: Int
        get() = evidenceList.size

    init {
        evidenceList.clear()

        val namesTypedArray = context.resources.obtainTypedArray(R.array.evidence_type_names)
        val namesArray = intArrayFromTypedArray(context.resources, namesTypedArray)

        val idsTypedArray = context.resources.obtainTypedArray(R.array.evidence_type_ids)
        val idsArray = intArrayFromTypedArray(context.resources, idsTypedArray)

        val typedArray = context.resources.obtainTypedArray(R.array.evidence_icon_array)

        for (i in namesArray.indices) {

            val name = namesArray[i]
            @IntegerRes val idRes = if(i <= idsArray.size) idsArray[i] else 0
            val id = context.resources.getInteger(idRes)

            val evidence = EvidenceModel(id, name)
            evidence.icon = typedArray.getResourceId(i, 0)
            evidenceList.add(evidence)
        }

        typedArray.recycle()

    }

    /** Resets the Ruling for each Evidence type */
    fun reset() {
        for (i in evidenceList.indices) {
            evidenceList[i].ruling = EvidenceModel.Ruling.NEUTRAL
        }
    }

}

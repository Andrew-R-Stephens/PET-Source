package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.investigationtype.evidence

import android.content.Context
import com.TritiumGaming.phasmophobiaevidencepicker.R
import com.TritiumGaming.phasmophobiaevidencepicker.utils.ResourceUtils.ResourceUtils.intArrayFromTypedArray

class EvidenceRepository {

    val evidenceList: ArrayList<EvidenceModel> = ArrayList(0)

    val count: Int
        get() = evidenceList.size


    fun init(context: Context) {
        evidenceList.clear()

        val namesTypedArray = context.resources.obtainTypedArray(R.array.evidence_type_names)
        val namesArray = intArrayFromTypedArray(context.resources, namesTypedArray)

        val typedArray = context.resources.obtainTypedArray(R.array.evidence_icon_array)

        for (i in namesArray.indices) {
            val evidence = EvidenceModel(i, namesArray[i])
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

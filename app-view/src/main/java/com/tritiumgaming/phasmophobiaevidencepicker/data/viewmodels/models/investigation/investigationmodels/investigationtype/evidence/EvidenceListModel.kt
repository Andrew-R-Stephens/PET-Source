package com.tritiumgaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.investigationtype.evidence

import android.content.Context
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.utils.ResourceUtils.ResourceUtils.intArrayFromTypedArray

class EvidenceListModel {

    val evidenceList: ArrayList<EvidenceModel> = ArrayList(0)

    val count: Int
        get() = evidenceList.size


    fun init(c: Context) {
        evidenceList.clear()

        val namesTypedArray = c.resources.obtainTypedArray(R.array.evidence_type_names)
        val namesArray = intArrayFromTypedArray(c.resources, namesTypedArray)

        val typedArray = c.resources.obtainTypedArray(R.array.evidence_icon_array)

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

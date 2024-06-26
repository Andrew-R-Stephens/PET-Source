package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.investigationtype.evidence

import android.content.Context
import com.TritiumGaming.phasmophobiaevidencepicker.R

class EvidenceListModel {

    val evidenceList: ArrayList<EvidenceModel> = ArrayList(0)

    val count: Int
        get() = evidenceList.size


    fun init(c: Context) {
        evidenceList.clear()
        val evidenceNames = c.resources.getStringArray(R.array.evidence_type_names)
        val typedArray = c.resources.obtainTypedArray(R.array.evidence_icon_array)
        for (i in evidenceNames.indices) {
            val evidence = EvidenceModel()
            evidence.name = evidenceNames[i]
            evidence.icon = typedArray.getResourceId(i, 0)
            evidenceList?.add(evidence)
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

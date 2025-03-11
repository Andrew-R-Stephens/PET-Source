package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.evidence

import android.content.Context
import androidx.annotation.IntegerRes
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.util.ResourceUtils.ResourceUtils.intArrayFromTypedArray

class EvidenceListModel {

    val evidenceList: ArrayList<EvidenceModel> = ArrayList(0)

    val count: Int
        get() = evidenceList.size

    fun init(c: Context) {
        evidenceList.clear()

        val namesTypedArray = c.resources.obtainTypedArray(R.array.evidence_type_names)
        val namesArray = intArrayFromTypedArray(c.resources, namesTypedArray)

        val idsTypedArray = c.resources.obtainTypedArray(R.array.evidence_type_ids)
        val idsArray = intArrayFromTypedArray(c.resources, idsTypedArray)

        val typedArray = c.resources.obtainTypedArray(R.array.evidence_icon_array)

        for (i in namesArray.indices) {

            val name = namesArray[i]
            @IntegerRes val idRes = if(i <= idsArray.size) idsArray[i] else 0
            val id = c.resources.getInteger(idRes)

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

package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.investigationmodels

import android.content.Context
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.investigationtype.EvidenceListModel
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.investigationtype.GhostListModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel

/**
 * InvestigationData class
 *
 * @author TritiumGamingStudios
 */
class InvestigationModel(
    context: Context,
    val evidenceViewModel: EvidenceViewModel
) {
    val ghostList: GhostListModel = GhostListModel()
    val evidenceList: EvidenceListModel = EvidenceListModel()

    init {
        evidenceList.init(context)
        ghostList.init(context, this)
    }

    /** Resets the Ruling for each Evidence type */
    fun reset() {
        evidenceList.reset()
        ghostList.reset()
    }

}
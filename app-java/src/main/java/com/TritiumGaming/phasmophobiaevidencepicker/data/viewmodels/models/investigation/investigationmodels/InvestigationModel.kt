package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels

import android.content.Context
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.EvidenceViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.investigationtype.evidence.EvidenceListModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.investigationtype.ghost.GhostListModel

/**
 * InvestigationData class
 *
 * @author TritiumGamingStudios
 */
class InvestigationModel(
    context: Context,
    val evidenceViewModel: EvidenceViewModel
) {
    val ghostList: GhostListModel =
        GhostListModel()
    val evidenceList: EvidenceListModel =
        EvidenceListModel()

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
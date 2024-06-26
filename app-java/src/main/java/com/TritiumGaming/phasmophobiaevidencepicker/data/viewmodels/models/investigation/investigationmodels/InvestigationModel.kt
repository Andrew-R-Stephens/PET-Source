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
    val ghostListModel: GhostListModel = GhostListModel()
    val evidenceListModel: EvidenceListModel = EvidenceListModel()

    init {
        evidenceListModel.init(context)
        ghostListModel.init(context, this)
    }

    /** Resets the Ruling for each Evidence type */
    fun reset() {
        evidenceListModel.reset()
        ghostListModel.reset()
    }

}
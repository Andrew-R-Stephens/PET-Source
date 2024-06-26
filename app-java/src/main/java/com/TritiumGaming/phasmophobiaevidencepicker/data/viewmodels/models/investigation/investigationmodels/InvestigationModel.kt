package com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels

import android.content.Context
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.InvestigationViewModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.investigationtype.evidence.EvidenceListModel
import com.TritiumGaming.phasmophobiaevidencepicker.data.viewmodels.models.investigation.investigationmodels.investigationtype.ghost.GhostListModel

/**
 * InvestigationData class
 *
 * @author TritiumGamingStudios
 */
class InvestigationModel(
    context: Context,
    val investigationViewModel: InvestigationViewModel?
) {
    val ghostListModel: GhostListModel = GhostListModel()
    val evidenceListModel: EvidenceListModel = EvidenceListModel()
    val ghostOrderModel: GhostOrderModel = GhostOrderModel(ghostListModel)

    init {
        evidenceListModel.init(context)
        ghostListModel.init(context, this)
    }

    /** Resets the Ruling for each Evidence type */
    fun reset() {
        evidenceListModel.reset()
        ghostListModel.reset()
        ghostOrderModel.reset()
    }

}
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
    init {
        Companion.evidenceList.init(context)
        Companion.ghostList.init(context, this)
    }

    val ghostList: GhostListModel
        get() = Companion.ghostList

    val evidenceList: EvidenceListModel
        get() = Companion.evidenceList

    /**
     * Resets the Ruling for each Evidence type
     */
    fun reset() {
        Companion.evidenceList.reset()
        Companion.ghostList.reset()
    }

    companion object {
        @JvmField
        var ghostList: GhostListModel =
            GhostListModel()
        @JvmField
        var evidenceList: EvidenceListModel =
            EvidenceListModel()
    }
}
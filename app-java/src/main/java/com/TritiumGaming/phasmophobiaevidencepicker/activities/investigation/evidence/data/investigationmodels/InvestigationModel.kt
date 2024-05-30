package com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.investigationmodels

import android.content.Context
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.investigationtype.EvidenceList
import com.TritiumGaming.phasmophobiaevidencepicker.activities.investigation.evidence.data.investigationtype.GhostList
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

    val ghostList: GhostList
        get() = Companion.ghostList

    val evidenceList: EvidenceList
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
        var ghostList: GhostList = GhostList()
        @JvmField
        var evidenceList: EvidenceList = EvidenceList()
    }
}
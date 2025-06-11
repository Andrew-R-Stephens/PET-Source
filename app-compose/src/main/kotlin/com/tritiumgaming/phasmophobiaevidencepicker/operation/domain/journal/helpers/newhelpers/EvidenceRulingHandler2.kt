package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.helpers.newhelpers

import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.EvidenceType
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.newmodel.RuledEvidence2
import com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model.newmodel.RuledEvidence2.Ruling2
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EvidenceRulingHandler2(
    private val evidences: List<EvidenceType>
) {

    private val _ruledEvidence = MutableStateFlow(SnapshotStateList<RuledEvidence2>())
    val ruledEvidence = _ruledEvidence.asStateFlow()
    fun initRuledEvidence() {
        val list = evidences.map {
            RuledEvidence2(it).apply { setRuling(Ruling2.NEUTRAL) }
        }
        _ruledEvidence.update { list.toMutableStateList() }
    }
    fun setEvidenceRuling(evidenceIndex: Int, ruling: Ruling2) {
        ruledEvidence.value[evidenceIndex].setRuling(ruling)
    }
    fun setEvidenceRuling(evidence: EvidenceType, ruling: Ruling2) {
        getRuledEvidence(evidence)?.setRuling(ruling)
    }

    fun getRuledEvidence(evidenceModel: EvidenceType): RuledEvidence2? {
        return ruledEvidence.value.find { it.isEvidence(evidenceModel) }
    }

    /** Resets the Ruling for each Evidence type */
    fun reset() {
        //initRuledEvidence()
        ruledEvidence.value.forEach {
            it.setRuling( Ruling2.NEUTRAL)
        }
    }

    override fun toString(): String {
        val str = StringBuilder()
        ruledEvidence.value.forEach {
            str.append(" [${it.evidence.id}:${it.ruling.value}] ")
        }
        return "$str"
    }

    init {
        initRuledEvidence()
    }
}


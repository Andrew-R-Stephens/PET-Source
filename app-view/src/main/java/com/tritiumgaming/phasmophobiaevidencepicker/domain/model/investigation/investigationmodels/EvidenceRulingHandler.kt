package com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels

import androidx.annotation.DrawableRes
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import com.tritiumgaming.phasmophobiaevidencepicker.R
import com.tritiumgaming.phasmophobiaevidencepicker.data.repository.EvidenceRepository
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.RuledEvidence.Ruling
import com.tritiumgaming.phasmophobiaevidencepicker.domain.model.investigation.investigationmodels.investigationtype.evidence.EvidenceModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class EvidenceRulingHandler(
    private val evidenceRepository: EvidenceRepository
) {

    private val _ruledEvidence = MutableStateFlow(SnapshotStateList<RuledEvidence>())
    val ruledEvidence = _ruledEvidence.asStateFlow()
    fun initRuledEvidence() {
        val list = evidenceRepository.evidenceList.map {
            RuledEvidence(it).apply { setRuling(Ruling.NEUTRAL) }
        }
        _ruledEvidence.update { list.toMutableStateList() }
    }

    fun setEvidenceRuling(evidenceIndex: Int, ruling: Ruling) {
        ruledEvidence.value[evidenceIndex].setRuling(ruling)
    }
    fun setEvidenceRuling(evidence: EvidenceModel, ruling: Ruling) {
        getRuledEvidence(evidence)?.setRuling(ruling)
    }

    fun getRuledEvidence(evidenceModel: EvidenceModel): RuledEvidence? {
        return ruledEvidence.value.find { it.isEvidence(evidenceModel) }
    }

    /** Resets the Ruling for each Evidence type */
    fun reset() {
        ruledEvidence.value.forEach {
            it.setRuling(Ruling.NEUTRAL)
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

@Stable
@Immutable
data class RuledEvidence(
    val evidence: EvidenceModel
) {
    private val _ruling = MutableStateFlow(Ruling.NEUTRAL)
    val ruling = _ruling.asStateFlow()
    fun setRuling(ruling: Ruling) {
        _ruling.update { ruling }
    }

    enum class Ruling(
        @DrawableRes var icon: Int,
    ) {
        NEGATIVE(R.drawable.ic_selector_neg_unsel),
        NEUTRAL(R.drawable.ic_selector_inc_unsel),
        POSITIVE(R.drawable.ic_selector_pos_unsel)
    }

    fun isRuling(other: Ruling?): Boolean {
        return ruling.value == other
    }

    fun isEvidence(other: RuledEvidence): Boolean {
        return isEvidence(other.evidence)
    }

    fun isEvidence(other: EvidenceModel): Boolean {
        return this.evidence == other
    }

    override fun toString(): String {
        return ruling.value.name
    }

}

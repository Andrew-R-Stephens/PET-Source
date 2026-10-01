package com.tritiumgaming.data.journal.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.tritiumgaming.data.evidence.model.EvidenceType

@Stable
@Immutable
data class EvidenceState(
    val evidence: EvidenceType,
    val state: EvidenceValidationType = EvidenceValidationType.NEUTRAL,
    val enabled: Boolean = true
) {

    fun isRuling(other: EvidenceValidationType?): Boolean {
        return state == other
    }

    fun isEvidence(other: EvidenceState): Boolean {
        return isEvidence(other.evidence)
    }

    fun isEvidence(other: EvidenceType): Boolean {
        return this.evidence == other
    }

    override fun toString(): String {
        return state.name
    }

}
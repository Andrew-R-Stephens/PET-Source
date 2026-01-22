package com.tritiumgaming.shared.data.evidence.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@Stable
@Immutable
data class RuledEvidence(
    val evidence: EvidenceType,
    val ruling: Ruling = Ruling.NEUTRAL
) {

    enum class Ruling() {
        NEGATIVE,
        NEUTRAL,
        POSITIVE,
    }

    fun isRuling(other: Ruling?): Boolean {
        return ruling == other
    }

    fun isEvidence(other: RuledEvidence): Boolean {
        return isEvidence(other.evidence)
    }

    fun isEvidence(other: EvidenceType): Boolean {
        return this.evidence == other
    }

    override fun toString(): String {
        return ruling.name
    }

}
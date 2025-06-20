package com.tritiumgaming.phasmophobiaevidencepicker.operation.domain.journal.model

expect class GhostEvidence() {

    expect val ghost: GhostType
    expect val normalEvidenceList: List<EvidenceType>
    expect val strictEvidenceList: List<EvidenceType>

    expect override fun toString(): String

}
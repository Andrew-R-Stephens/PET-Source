package com.tritiumgaming.shared.data.wearable.model

import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.operation.model.EvidenceValidationType
import kotlinx.serialization.Serializable

@Serializable
data class WearableOperationData(
    val mapName: String = "",
    val difficultyName: String = "",
    val setupTimeRemaining: Long = 0L,
    val sanityLevel: Float = 100f,
    val evidenceStates: List<WearableEvidenceState> = emptyList()
)

@Serializable
data class WearableEvidenceState(
    val type: EvidenceType, // EvidenceIdentifier.name
    val state: EvidenceValidationType, // EvidenceValidationType.name
    val enabled: Boolean = true
)

package com.tritiumgaming.shared.data.wearable.model

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
    val id: String, // EvidenceIdentifier.name
    val state: String, // EvidenceValidationType.name
    val enabled: Boolean = true
)

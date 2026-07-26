package com.tritiumgaming.shared.data.wearable.model

import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources
import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources
import com.tritiumgaming.shared.data.operation.model.EvidenceValidationType
import kotlinx.serialization.Serializable

@Serializable
data class WearableOperationData(
    val mapName: SimpleMapResources.MapTitle,
    val difficultyName: DifficultyResources.DifficultyType,
    val setupTimeRemaining: Long,
    val sanityLevel: Float,
    val evidenceStates: List<WearableEvidenceState>
)

@Serializable
data class WearableEvidenceState(
    val type: EvidenceType, // EvidenceIdentifier.name
    val state: EvidenceValidationType, // EvidenceValidationType.name
    val enabled: Boolean = true
)

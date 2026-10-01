package com.tritiumstudios.data.wearable.model

import com.tritiumgaming.data.difficulty.mapper.DifficultyResources
import com.tritiumgaming.data.evidence.model.EvidenceType
import com.tritiumgaming.data.journal.model.EvidenceValidationType
import com.tritiumgaming.data.map.simple.mappers.SimpleMapResources
import com.tritiumgaming.data.palette.mappers.PaletteResources.PaletteType
import com.tritiumgaming.data.typography.mappers.TypographyResources.TypographyType
import kotlinx.serialization.Serializable

@Serializable
data class WearableOperationData(
    val investigationData: WearableInvestigationData,
    val palette: PaletteType = PaletteType.CLASSIC,
    val typography: TypographyType = TypographyType.CLASSIC
)

@Serializable
data class WearableInvestigationData(
    val mapName: SimpleMapResources.MapTitle,
    val difficultyName: DifficultyResources.DifficultyType,
    val setupTimeRemaining: Long,
    val sanityLevel: Float,
    val evidenceStates: List<WearableEvidenceState>
)

@Serializable
data class WearableEvidenceState(
    val type: EvidenceType,
    val state: EvidenceValidationType,
    val enabled: Boolean = true
)

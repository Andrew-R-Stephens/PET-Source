package com.tritiumgaming.shared.data.wearable.model

import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources
import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources
import com.tritiumgaming.shared.data.operation.model.EvidenceValidationType
import com.tritiumgaming.shared.data.market.palette.mappers.PaletteResources.PaletteType
import com.tritiumgaming.shared.data.market.typography.mappers.TypographyResources.TypographyType
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

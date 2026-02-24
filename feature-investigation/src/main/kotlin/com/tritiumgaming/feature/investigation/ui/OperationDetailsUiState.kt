package com.tritiumgaming.feature.investigation.ui

import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyTitle
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyType
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources.EvidenceIdentifier
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostDescription
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostHuntInfo
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostIcon
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostIdentifier
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostSpeed
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostStrength
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostTitle
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostWeakness
import com.tritiumgaming.shared.data.ghost.model.Ghost
import com.tritiumgaming.shared.data.ghost.model.GhostType
import com.tritiumgaming.shared.data.ghostname.model.GhostName
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources
import com.tritiumgaming.shared.data.phase.model.Phase

internal data class OperationDetailsUiState(
    internal val mapDetails: MapDetails = MapDetails(),
    internal val difficultyDetails: DifficultyDetails = DifficultyDetails()
) {
    internal data class DifficultyDetails(
        val type: DifficultyType = DifficultyType.AMATEUR,
        val name: DifficultyTitle = DifficultyTitle.AMATEUR,
        val modifier: Float = 0f,
        val setupTime: Long = 0L,
        val initialSanity: Float = 0f,
        val responseType: DifficultyResponseType = DifficultyResponseType.KNOWN
    )

    internal data class MapDetails(
        internal val name: SimpleMapResources.MapTitle = SimpleMapResources.MapTitle.BLEASDALE_FARMHOUSE,
        internal val size: MapModifierResources.MapSize = MapModifierResources.MapSize.SMALL,
        internal val modifiers: MapModifiers = MapModifiers()
    ) {
        internal data class MapModifiers(
            internal val normal: Float = 1f,
            internal val setup: Float = 1f
        )
    }

    internal data class PhaseDetails(
        val type: Phase = Phase.SETUP
    )

    internal data class GhostDetails(
        val activeGhosts: List<GhostDetail> = emptyList(),
    ) {
        internal data class GhostDetail(
            val name: GhostTitle,
            val normalEvidence: List<EvidenceIdentifier>,
            val strictEvidence: List<EvidenceIdentifier>,
            val speed: GhostSpeed
        )
    }
}
package com.tritiumgaming.feature.investigation.ui.tool.analysis

import com.tritiumgaming.feature.investigation.ui.TimerUiState
import com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.item.GhostState
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources
import com.tritiumgaming.shared.data.phase.model.Phase

internal data class OperationDetailsUiState(
    internal val mapDetails: MapDetails = MapDetails(),
    internal val difficultyDetails: DifficultyDetails = DifficultyDetails(),
    internal val phaseDetails: PhaseDetails = PhaseDetails(),
    internal val ghostDetails: GhostDetails = GhostDetails()
) {
    internal data class DifficultyDetails(
        internal val type: DifficultyResources.DifficultyType = DifficultyResources.DifficultyType.AMATEUR,
        internal val name: DifficultyResources.DifficultyTitle = DifficultyResources.DifficultyTitle.AMATEUR,
        internal val modifier: Float = 0f,
        internal val setupTime: Long = 0L,
        internal val initialSanity: Float = 0f,
        internal val responseType: DifficultyResources.DifficultyResponseType = DifficultyResources.DifficultyResponseType.KNOWN
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
        internal val type: Phase = Phase.SETUP,
        internal val canAlertAudio: Boolean = false,
        internal val canFlash: Boolean = true,
        internal val startFlashTime: Long = TimerUiState.DEFAULT,
        internal val elapsedFlashTime: Long = TimerUiState.DEFAULT,
        internal val maxFlashTime: Long = TimerUiState.DURATION_30_SECONDS,
    )

    internal data class GhostDetails(
        internal val activeGhosts: List<GhostDetail> = emptyList(),
    ) {
        internal data class GhostDetail(
            internal val state: GhostState
        )
    }
}
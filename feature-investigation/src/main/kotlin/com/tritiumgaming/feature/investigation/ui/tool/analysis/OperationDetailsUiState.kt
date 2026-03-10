package com.tritiumgaming.feature.investigation.ui.tool.analysis

import com.tritiumgaming.feature.investigation.ui.TimerUiState
import com.tritiumgaming.feature.investigation.ui.journal.ghost.item.GhostState
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyResponseType
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyTitle
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyType
import com.tritiumgaming.shared.data.difficultysetting.model.DifficultySettingsModel
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSize
import com.tritiumgaming.shared.data.map.modifier.mappers.MapModifierResources.MapSizePhaseModifier
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources.MapTitle
import com.tritiumgaming.shared.data.phase.model.Phase

internal data class OperationDetailsUiState(
    internal val mapDetails: MapDetails = MapDetails(),
    internal val difficultyDetails: DifficultyDetails = DifficultyDetails(),
    internal val phaseDetails: PhaseDetails = PhaseDetails(),
    internal val ghostDetails: GhostDetails = GhostDetails()
) {
    internal data class DifficultyDetails(
        internal val type: DifficultyType = DifficultyType.AMATEUR,
        internal val difficultyTitle: DifficultyTitle = DifficultyTitle.AMATEUR,
        internal val responseType: DifficultyResponseType = DifficultyResponseType.KNOWN,
        internal val challengeTitle: ChallengeResources.ChallengeTitle? = null,
        internal val settings: DifficultySettingsModel = DifficultySettingsModel()
    )

    internal data class MapDetails(
        internal val name: MapTitle = MapTitle.BLEASDALE_FARMHOUSE,
        internal val size: MapSize = MapSize.SMALL,
        internal val modifiers: MapModifiers = MapModifiers()
    ) {
        internal data class MapModifiers(
            internal val action: MapSizePhaseModifier = MapSizePhaseModifier.ACTION_SMALL,
            internal val setup: MapSizePhaseModifier = MapSizePhaseModifier.SETUP_SMALL,
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
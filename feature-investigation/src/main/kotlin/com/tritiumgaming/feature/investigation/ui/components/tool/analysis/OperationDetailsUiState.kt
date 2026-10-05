package com.tritiumgaming.feature.investigation.ui.components.tool.analysis

import com.tritiumgaming.data.challenge.mapper.ChallengeResources.ChallengeTitle
import com.tritiumgaming.data.customdifficulty.model.CustomDifficultyModel
import com.tritiumgaming.data.difficulty.mapper.DifficultyResources.DifficultyTitle
import com.tritiumgaming.data.difficulty.mapper.DifficultyResources.DifficultyType
import com.tritiumgaming.data.difficulty.mapper.DifficultyResources.GhostResponsePresentation
import com.tritiumgaming.data.map.modifier.mappers.MapModifierResources.MapSize
import com.tritiumgaming.data.map.modifier.mappers.MapModifierResources.MapSizePhaseModifier
import com.tritiumgaming.data.map.simple.mappers.SimpleMapResources.MapTitle
import com.tritiumstudios.data.difficultysetting.mappers.DifficultySettingResources.Weather
import com.tritiumstudios.data.difficultysetting.model.DifficultySettingsModel
import com.tritiumstudios.data.operation.model.GhostState
import com.tritiumstudios.data.operation.model.OperationOverrideData
import com.tritiumstudios.data.operation.model.PhaseData.Companion.DEFAULT
import com.tritiumstudios.data.operation.model.PhaseData.Companion.DURATION_30_SECONDS
import com.tritiumstudios.data.phase.mappers.PhaseResources.PhaseIdentifier

internal data class OperationDetailsUiState(
    internal val mapDetails: MapDetails = MapDetails(),
    internal val difficultyDetails: DifficultyDetails = DifficultyDetails(),
    internal val phaseDetails: PhaseDetails = PhaseDetails(),
    internal val ghostDetails: GhostDetails = GhostDetails(),
    internal val weatherDetails: WeatherDetails = WeatherDetails(),
    internal val overrides: OperationOverrideData = OperationOverrideData()
) {

    internal data class WeatherDetails(
        internal val weather: Weather = Weather.RANDOM
    )

    internal data class DifficultyDetails(
        internal val type: DifficultyType = DifficultyType.AMATEUR,
        internal val difficultyTitle: DifficultyTitle = DifficultyTitle.AMATEUR,
        internal val responseType: GhostResponsePresentation = GhostResponsePresentation.KNOWN,
        internal val challengeTitle: ChallengeTitle? = null,
        internal val customTitle: CustomDifficultyModel? = null,
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
        internal val type: PhaseIdentifier = PhaseIdentifier.SETUP,
        internal val canAlertAudio: Boolean = false,
        internal val canFlash: Boolean = true,
        internal val startFlashTime: Long = DEFAULT,
        internal val elapsedFlashTime: Long = DEFAULT,
        internal val maxFlashTime: Long = DURATION_30_SECONDS,
    )

    internal data class GhostDetails(
        internal val activeGhosts: List<GhostDetail> = emptyList(),
    ) {
        internal data class GhostDetail(
            internal val state: GhostState
        )
    }
}
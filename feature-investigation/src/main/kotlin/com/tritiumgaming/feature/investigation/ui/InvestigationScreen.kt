package com.tritiumgaming.feature.investigation.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.mapper.toStringResource
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer.GraphPoint
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer.RealtimeUiState
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarUiColors
import com.tritiumgaming.feature.investigation.app.mappers.difficulty.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.map.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.weather.toDrawable
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ClearPopup
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.PlayerDeath
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ResetInvestigation
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.SetBpmData
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.SetBpmMeasurementType
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.SetDifficulty
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.SetEvidence
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.SetMap
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.SetPlayerSanity
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.SetToolbarCategory
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.SetTraitFilter
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.SetWeather
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ShowEvidencePopup
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ShowGhostPopup
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.SkipOperationTimer
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ToggleApplyBpmMeasurement
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ToggleFuseBoxOverride
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ToggleGhostNegation
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ToggleOperationTimer
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ToggleToolbar
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ToggleTrait
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ToggleUniqueTraitFilter
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.TriggerToolTimer
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.UseSanityMedication
import com.tritiumgaming.feature.investigation.ui.journal.JournalComponent
import com.tritiumgaming.feature.investigation.ui.popups.common.InvestigationPopup
import com.tritiumgaming.feature.investigation.ui.popups.evidence.EvidencePopup
import com.tritiumgaming.feature.investigation.ui.popups.ghost.GhostPopup
import com.tritiumgaming.feature.investigation.ui.sheet.ToolsBottomSheetComponent
import com.tritiumgaming.feature.investigation.ui.sheet.ToolsSideSheetComponent
import com.tritiumgaming.feature.investigation.ui.tool.footstep.visualizer.VisualizerMeasurementType
import com.tritiumgaming.feature.investigation.ui.tool.statusbar.OperationStatusBar
import com.tritiumgaming.feature.investigation.ui.tool.temperature.TemperatureStateBundle
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiActions
import com.tritiumgaming.feature.investigation.ui.toolbar.operation.OperationToolRail
import com.tritiumgaming.feature.investigation.ui.toolbar.operation.OperationToolbar
import com.tritiumgaming.feature.investigation.ui.toolbar.operation.OperationToolbarUiState
import com.tritiumgaming.shared.core.navigation.NavRoute
import com.tritiumgaming.shared.data.customdifficulty.CustomDifficultyResources
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Weather
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitCategory
import com.tritiumgaming.shared.data.investigation.model.ToolTimerType
import com.tritiumgaming.shared.data.investigation.model.TraitFilter
import com.tritiumgaming.shared.data.investigation.model.ValidatedGhostTrait
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources


@Composable
fun InvestigationSoloScreen(
    navController: NavHostController,
    investigationViewModel: InvestigationScreenViewModel
) {

    InvestigationContent(
        navController = navController,
        investigationViewModel = investigationViewModel
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InvestigationContent(
    navController: NavHostController,
    investigationViewModel: InvestigationScreenViewModel
) {
    val popupUiState by investigationViewModel.popupUiState.collectAsStateWithLifecycle()

    val toolbarUiState by investigationViewModel.operationToolbarUiState.collectAsStateWithLifecycle()

    val operationTimerUiState by investigationViewModel.operationTimerUiState.collectAsStateWithLifecycle()
    val phaseUiState by investigationViewModel.phaseUiState.collectAsStateWithLifecycle()
    val mapConfigUiState by investigationViewModel.mapConfigUiState.collectAsStateWithLifecycle()
    val operationDetailsUiState by investigationViewModel.operationDetailsUiState.collectAsStateWithLifecycle()
    val difficultyUiState by investigationViewModel.difficultyConfigUiState.collectAsStateWithLifecycle()
    val customDifficultyConfigUiState by investigationViewModel.customDifficultyConfigUiState.collectAsStateWithLifecycle()
    val weatherUiState by investigationViewModel.weatherUiState.collectAsStateWithLifecycle()
    val temperatureUiState by investigationViewModel.temperatureUiState.collectAsStateWithLifecycle()
    val difficultyOverrideUiState by investigationViewModel.difficultyOverridesState.collectAsStateWithLifecycle()
    val sanityUiState by investigationViewModel.playerSanityUiState.collectAsStateWithLifecycle()
    val evidenceListUiStates by investigationViewModel.evidenceListUiState.collectAsStateWithLifecycle()

    val traitFilterOptions by investigationViewModel.traitFilterOptionsUiState.collectAsStateWithLifecycle()
    val traitListUiStates by investigationViewModel.traitListUiState.collectAsStateWithLifecycle()
    val ghostOrder by investigationViewModel.ghostsSortedUiState.collectAsStateWithLifecycle()
    val evidenceStates by investigationViewModel.evidenceStates.collectAsStateWithLifecycle()

    val smudgeHuntProtectionTimerState by investigationViewModel.smudgeHuntProtectionTimerUiState.collectAsStateWithLifecycle()
    val huntDurationTimerState by investigationViewModel.huntDurationTimerUiState.collectAsStateWithLifecycle()
    val huntGapTimerState by investigationViewModel.huntCooldownTimerUiState.collectAsStateWithLifecycle()
    val fingerprintTimerState by investigationViewModel.fingerprintTimerUiState.collectAsStateWithLifecycle()

    val bpmToolUiState by investigationViewModel.bpmToolUiState.collectAsStateWithLifecycle()

    val toolbarCategory = toolbarUiState.category

    val weather = weatherUiState.weather
    val weatherIcon = weather.toDrawable()
    val isWeatherEnabled = weatherUiState.enabled
    val weatherLabel = if (weather == Weather.RANDOM) {
        R.string.difficulty_setting_response_unknown
    } else {
        weather.toStringResource()
    }
    val weatherDropdownOptions = Weather.entries.map {
        if (it == Weather.RANDOM) R.string.difficulty_setting_state_weather_unknown
        else it.toStringResource()
    }

    val mapName = mapConfigUiState.name
    val isMapEnabled = mapConfigUiState.enabled
    val mapLabel = mapName.toStringResource(SimpleMapResources.MapTitleLength.ABBREVIATED)
    val mapDropdownOptions = mapConfigUiState.allMaps.map {
        it.toStringResource(SimpleMapResources.MapTitleLength.FULL)
    }

    val difficultyName = difficultyUiState.name
    val isDifficultyEnabled = true
    val difficultyLabel = difficultyName.toStringResource()
    val difficultyDropdownOptions = difficultyUiState.allDifficulties.map {
        it.toStringResource()
    }

    val customDifficultyLabel = customDifficultyConfigUiState.selectedDifficulty?.let {
        it.name ?: "${stringResource(CustomDifficultyResources.Title.CUSTOM.toStringResource())} ${it.id}"
    } ?: ""
    val customDifficultyDropdownOptions = customDifficultyConfigUiState.difficulties.map {
        it.name ?: "${stringResource(CustomDifficultyResources.Title.CUSTOM.toStringResource())} ${it.id}"
    }

    val sanityLevel = sanityUiState.sanityLevel
    val insanityLevel = sanityUiState.insanityLevel

    val timerRemainingTime = operationTimerUiState.remainingTime
    val timerPaused = operationTimerUiState.paused

    val temperatureStateBundle = TemperatureStateBundle(temperatureUiState)
    val fuseBoxFlag = difficultyOverrideUiState.fuseBox

    val smudgeHuntPreventionTitle = "Smudge Hunt Protection"
    val smudgeHuntPreventionMax = smudgeHuntProtectionTimerState.max
    val smudgeHuntPreventionRemaining = smudgeHuntProtectionTimerState.remaining
    val smudgeHuntPreventionTimeText = smudgeHuntProtectionTimerState.timeText
    val smudgeHuntPreventionRunning = smudgeHuntProtectionTimerState.running
    val smudgeNotches = smudgeHuntProtectionTimerState.notches

    val huntDurationTitle = "Hunt Duration"
    val huntDurationMax = huntDurationTimerState.max
    val huntDurationRemaining = huntDurationTimerState.remaining
    val huntDurationTimeText = huntDurationTimerState.timeText
    val huntDurationRunning = huntDurationTimerState.running
    val huntDurationNotches = huntDurationTimerState.notches

    val huntCooldownTitle = "Hunt Cooldown"
    val huntCooldownMax = huntGapTimerState.max
    val huntCooldownRemaining = huntGapTimerState.remaining
    val huntCooldownTimeText = huntGapTimerState.timeText
    val huntCooldownRunning = huntGapTimerState.running
    val huntCooldownNotches = huntGapTimerState.notches

    val fingerprintTimerTitle = "UV Evidence Lifetime"
    val fingerprintTimerMax = fingerprintTimerState.max
    val fingerprintTimerRemaining = fingerprintTimerState.remaining
    val fingerprintTimerTimeText = fingerprintTimerState.timeText
    val fingerprintTimerRunning = fingerprintTimerState.running
    val fingerprintNotches = fingerprintTimerState.notches

    val bpmRealtimeState = bpmToolUiState.realtimeState
    val bpmMeasurementType = bpmToolUiState.measurementType
    val bpmApplyMeasurement = bpmToolUiState.applyMeasurement

    val onWeatherDropdownSelect: (Int) -> Unit = { investigationViewModel.onEvent(SetWeather(Weather.entries[it])) }

    val onMapDropdownSelect: (Int) -> Unit = { investigationViewModel.onEvent(SetMap(it)) }

    val onDifficultyDropdownSelect: (Int) -> Unit = { investigationViewModel.onEvent(SetDifficulty(it)) }

    val onCustomDifficultyDropdownSelect: (Int) -> Unit = { investigationViewModel.onEvent(
        InvestigationScreenViewModel.InvestigationEvent.SetCustomDifficulty(it)) }

    val onNavigateToEditCustomDifficulty: () -> Unit = {
        navController.navigate(
            route = NavRoute.SCREEN_CUSTOM_DIFFICULTY_EDIT.route,
            navOptions = NavOptions.Builder()
                .setPopUpTo(NavRoute.SCREEN_INVESTIGATION.route, inclusive = false)
                .setLaunchSingleTop(true)
                .setEnterAnim(0)
                .setExitAnim(0)
                .setPopEnterAnim(0)
                .setPopExitAnim(0)
                .build()
        )
    }

    val onSanityChange: (Float) -> Unit = { investigationViewModel.onEvent(SetPlayerSanity(it)) }
    val onUseSanityMedication = { investigationViewModel.onEvent(UseSanityMedication) }
    val onPlayerDeath = { investigationViewModel.onEvent(PlayerDeath) }

    val onTimerToggle = { investigationViewModel.onEvent(ToggleOperationTimer) }
    val onTimerSkip = { investigationViewModel.onEvent(SkipOperationTimer) }

    val onTogglePower = { investigationViewModel.onEvent(ToggleFuseBoxOverride) }

    val onSelectTraitCategory: (TraitCategory) -> Unit = { category ->
        investigationViewModel.onEvent(SetTraitFilter(TraitFilter(category = category)))
    }
    val onToggleTrait: (ValidatedGhostTrait) -> Unit = { trait ->
        investigationViewModel.onEvent(ToggleTrait(trait))
    }
    val onToggleUniqueOnly: () -> Unit = { investigationViewModel.onEvent(ToggleUniqueTraitFilter) }

    val onSmudgeToggle: () -> Unit = { investigationViewModel.onEvent(TriggerToolTimer(ToolTimerType.SMUDGE_TIMER)) }
    val onHuntDurationToggle: () -> Unit = { investigationViewModel.onEvent(TriggerToolTimer(ToolTimerType.HUNT_DURATION)) }
    val onHuntCooldownToggle: () -> Unit = { investigationViewModel.onEvent(TriggerToolTimer(ToolTimerType.HUNT_COOLDOWN)) }
    val onFingerprintToggle: () -> Unit = { investigationViewModel.onEvent(TriggerToolTimer(ToolTimerType.UV_EVIDENCE_DURATION)) }

    val onBpmUpdate: (RealtimeUiState<GraphPoint>) -> Unit = { investigationViewModel.onEvent(SetBpmData(it)) }
    val onBpmChangeMeasurementType: (VisualizerMeasurementType) -> Unit = { investigationViewModel.onEvent(SetBpmMeasurementType(it)) }
    val onBpmToggleApplyMeasurement: () -> Unit = { investigationViewModel.onEvent(ToggleApplyBpmMeasurement) }

    val notchedProgressBarUiColors = NotchedProgressBarUiColors(
        remaining = LocalPalette.current.primary,
        background = LocalPalette.current.surface,
        border = LocalPalette.current.onSurface,
        notch = LocalPalette.current.onSurface,
        label = LocalPalette.current.onSurface,
    )

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    val bottomSheetComponent: @Composable (Modifier) -> Unit = { modifier ->
        ToolsBottomSheetComponent(
            modifier = modifier,
            toolbarCategory = toolbarCategory,
            weather = weather,
            weatherIcon = weatherIcon,
            weatherDropdownOptions = weatherDropdownOptions,
            isWeatherDropdownEnabled = isWeatherEnabled,
            weatherDropdownLabel = weatherLabel,
            mapDropdownOptions = mapDropdownOptions,
            isMapDropdownEnabled = isMapEnabled,
            mapDropdownLabel = mapLabel,
            difficulty = difficultyUiState.type,
            difficultyDropdownOptions = difficultyDropdownOptions,
            isDifficultyDropdownEnabled = isDifficultyEnabled,
            difficultyDropdownLabel = difficultyLabel,
            customDifficultyDropdownOptions = customDifficultyDropdownOptions,
            customDifficultyDropdownLabel = customDifficultyLabel,
            onCustomDifficultyDropdownSelect = onCustomDifficultyDropdownSelect,
            sanityLevel = sanityLevel,
            insanityLevel = insanityLevel,
            timerRemainingTime = timerRemainingTime,
            timerPaused = timerPaused,
            phaseUiState = phaseUiState,
            temperatureStateBundle = temperatureStateBundle,
            fuseBoxFlag = fuseBoxFlag,
            traitListOptions = traitFilterOptions,
            traitList = traitListUiStates,
            operationDetailsUiState = operationDetailsUiState,
            smudgeHuntPreventionTitle = smudgeHuntPreventionTitle,
            smudgeHuntPreventionMax = smudgeHuntPreventionMax,
            smudgeHuntPreventionRemaining = smudgeHuntPreventionRemaining,
            smudgeHuntPreventionTimeText = smudgeHuntPreventionTimeText,
            smudgeHuntPreventionRunning = smudgeHuntPreventionRunning,
            smudgeNotches = smudgeNotches,
            huntDurationTitle = huntDurationTitle,
            huntDurationMax = huntDurationMax,
            huntDurationRemaining = huntDurationRemaining,
            huntDurationTimeText = huntDurationTimeText,
            huntDurationRunning = huntDurationRunning,
            huntDurationNotches = huntDurationNotches,
            huntCooldownTitle = huntCooldownTitle,
            huntCooldownMax = huntCooldownMax,
            huntCooldownRemaining = huntCooldownRemaining,
            huntCooldownTimeText = huntCooldownTimeText,
            huntCooldownRunning = huntCooldownRunning,
            huntCooldownNotches = huntCooldownNotches,
            fingerprintTimerTitle = fingerprintTimerTitle,
            fingerprintTimerMax = fingerprintTimerMax,
            fingerprintTimerRemaining = fingerprintTimerRemaining,
            fingerprintTimerTimeText = fingerprintTimerTimeText,
            fingerprintTimerRunning = fingerprintTimerRunning,
            fingerprintNotches = fingerprintNotches,
            bpmRealtimeState = bpmRealtimeState,
            bpmMeasurementType = bpmMeasurementType,
            bpmApplyMeasurement = bpmApplyMeasurement,
            notchedProgressBarUiColors = notchedProgressBarUiColors,
            onWeatherDropdownSelect = onWeatherDropdownSelect,
            onMapDropdownSelect = onMapDropdownSelect,
            onDifficultyDropdownSelect = onDifficultyDropdownSelect,
            onNavigateToEditCustomDifficulty = onNavigateToEditCustomDifficulty,
            onSanityChange = onSanityChange,
            onUseSanityMedication = onUseSanityMedication,
            onPlayerDeath = onPlayerDeath,
            onTimerToggle = onTimerToggle,
            onTimerSkip = onTimerSkip,
            onTogglePower = onTogglePower,
            onSelectTraitCategory = onSelectTraitCategory,
            onToggleTrait = onToggleTrait,
            onToggleUniqueOnly = onToggleUniqueOnly,
            onSmudgeToggle = onSmudgeToggle,
            onHuntDurationToggle = onHuntDurationToggle,
            onHuntCooldownToggle = onHuntCooldownToggle,
            onFingerprintToggle = onFingerprintToggle,
            onBpmUpdate = onBpmUpdate,
            onBpmChangeMeasurementType = onBpmChangeMeasurementType,
            onBpmToggleApplyMeasurement = onBpmToggleApplyMeasurement,
        )
    }

    val sideSheetComponent: @Composable (Modifier) -> Unit = { modifier ->
        ToolsSideSheetComponent(
            modifier = modifier,
            toolbarCategory = toolbarCategory,
            weather = weather,
            weatherIcon = weatherIcon,
            weatherDropdownOptions = weatherDropdownOptions,
            isWeatherDropdownEnabled = isWeatherEnabled,
            weatherDropdownLabel = weatherLabel,
            mapDropdownOptions = mapDropdownOptions,
            isMapDropdownEnabled = isMapEnabled,
            mapDropdownLabel = mapLabel,
            difficulty = difficultyUiState.type,
            difficultyDropdownOptions = difficultyDropdownOptions,
            isDifficultyDropdownEnabled = isDifficultyEnabled,
            difficultyDropdownLabel = difficultyLabel,
            customDifficultyDropdownOptions = customDifficultyDropdownOptions,
            customDifficultyDropdownLabel = customDifficultyLabel,
            onCustomDifficultyDropdownSelect = onCustomDifficultyDropdownSelect,
            onNavigateToEditCustomDifficulty = onNavigateToEditCustomDifficulty,
            sanityLevel = sanityLevel,
            insanityLevel = insanityLevel,
            timerRemainingTime = timerRemainingTime,
            timerPaused = timerPaused,
            phaseUiState = phaseUiState,
            temperatureStateBundle = temperatureStateBundle,
            fuseBoxFlag = fuseBoxFlag,
            traitListOptions = traitFilterOptions,
            traitList = traitListUiStates,
            operationDetailsUiState = operationDetailsUiState,
            smudgeHuntPreventionTitle = smudgeHuntPreventionTitle,
            smudgeHuntPreventionMax = smudgeHuntPreventionMax,
            smudgeHuntPreventionRemaining = smudgeHuntPreventionRemaining,
            smudgeHuntPreventionTimeText = smudgeHuntPreventionTimeText,
            smudgeHuntPreventionRunning = smudgeHuntPreventionRunning,
            smudgeNotches = smudgeNotches,
            huntDurationTitle = huntDurationTitle,
            huntDurationMax = huntDurationMax,
            huntDurationRemaining = huntDurationRemaining,
            huntDurationTimeText = huntDurationTimeText,
            huntDurationRunning = huntDurationRunning,
            huntDurationNotches = huntDurationNotches,
            huntCooldownTitle = huntCooldownTitle,
            huntCooldownMax = huntCooldownMax,
            huntCooldownRemaining = huntCooldownRemaining,
            huntCooldownTimeText = huntCooldownTimeText,
            huntCooldownRunning = huntCooldownRunning,
            huntCooldownNotches = huntCooldownNotches,
            fingerprintTimerTitle = fingerprintTimerTitle,
            fingerprintTimerMax = fingerprintTimerMax,
            fingerprintTimerRemaining = fingerprintTimerRemaining,
            fingerprintTimerTimeText = fingerprintTimerTimeText,
            fingerprintTimerRunning = fingerprintTimerRunning,
            fingerprintNotches = fingerprintNotches,
            bpmRealtimeState = bpmRealtimeState,
            bpmMeasurementType = bpmMeasurementType,
            bpmApplyMeasurement = bpmApplyMeasurement,
            notchedProgressBarUiColors = notchedProgressBarUiColors,
            onWeatherDropdownSelect = onWeatherDropdownSelect,
            onMapDropdownSelect = onMapDropdownSelect,
            onDifficultyDropdownSelect = onDifficultyDropdownSelect,
            onSanityChange = onSanityChange,
            onUseSanityMedication = onUseSanityMedication,
            onPlayerDeath = onPlayerDeath,
            onTimerToggle = onTimerToggle,
            onTimerSkip = onTimerSkip,
            onTogglePower = onTogglePower,
            onSelectTraitCategory = onSelectTraitCategory,
            onSelectTrait = onToggleTrait,
            onToggleUniqueOnly = onToggleUniqueOnly,
            onSmudgeToggle = onSmudgeToggle,
            onHuntDurationToggle = onHuntDurationToggle,
            onHuntCooldownToggle = onHuntCooldownToggle,
            onFingerprintToggle = onFingerprintToggle,
            onBpmUpdate = onBpmUpdate,
            onBpmChangeMeasurementType = onBpmChangeMeasurementType,
            onBpmToggleApplyMeasurement = onBpmToggleApplyMeasurement,
        )
    }

    when(deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT,
        DeviceConfiguration.TABLET_PORTRAIT -> {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Top
            ) {
                Investigation(
                    modifier = Modifier.weight(1f, false),
                    operationToolbarUiState = toolbarUiState,
                    toolbarUiActions = ToolbarUiActions(
                        onToggleCollapseToolbar = { investigationViewModel.onEvent(ToggleToolbar) },
                        onChangeToolbarCategory = { category -> investigationViewModel.onEvent(SetToolbarCategory(category)) },
                        onReset = { investigationViewModel.onEvent(ResetInvestigation) }
                    ),
                    statusBarComponent = { modifier ->
                        OperationStatusBar(
                            modifier = modifier,
                            remainingTime = operationTimerUiState.remainingTime,
                            phaseType = phaseUiState.type,
                            sanityLevel = sanityUiState.sanityLevel
                        )
                    },
                    journalComponent = { modifier ->
                        JournalComponent(
                            modifier = modifier,
                            evidenceStateList = evidenceListUiStates,
                            ghostOrder = ghostOrder,
                            ghostEvidenceState = evidenceStates,
                            onGhostNameClick = { investigationViewModel.onEvent(ShowGhostPopup(it)) },
                            onToggleNegateGhost = { investigationViewModel.onEvent(ToggleGhostNegation(it)) },
                            onRequestToolTip = { },
                            onChangeEvidenceRuling = { e, r -> investigationViewModel.onEvent(SetEvidence(e, r)) },
                            onEvidenceClick = { investigationViewModel.onEvent(ShowEvidencePopup(it)) }
                        )
                    },
                    bottomSheetComponent = bottomSheetComponent
                )

            }

        }
        DeviceConfiguration.MOBILE_LANDSCAPE,
        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP -> {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Start
            ) {
                Investigation(
                    modifier = Modifier,
                    operationToolbarUiState = toolbarUiState,
                    toolbarUiActions = ToolbarUiActions(
                        onToggleCollapseToolbar = { investigationViewModel.onEvent(ToggleToolbar) },
                        onChangeToolbarCategory = { category -> investigationViewModel.onEvent(SetToolbarCategory(category)) },
                        onReset = { investigationViewModel.onEvent(ResetInvestigation) }
                    ),
                    statusBarComponent = { modifier ->
                        OperationStatusBar(
                            modifier = modifier,
                            remainingTime = operationTimerUiState.remainingTime,
                            phaseType = phaseUiState.type,
                            sanityLevel = sanityUiState.sanityLevel
                        )
                    },
                    journalComponent = { modifier ->
                        JournalComponent(
                            modifier = modifier,
                            evidenceStateList = evidenceListUiStates,
                            ghostOrder = ghostOrder,
                            ghostEvidenceState = evidenceStates,
                            onGhostNameClick = { investigationViewModel.onEvent(ShowGhostPopup(it)) },
                            onToggleNegateGhost = { investigationViewModel.onEvent(ToggleGhostNegation(it)) },
                            onRequestToolTip = { },
                            onChangeEvidenceRuling = { e, r -> investigationViewModel.onEvent(SetEvidence(e, r)) },
                            onEvidenceClick = { investigationViewModel.onEvent(ShowEvidencePopup(it)) }
                        )
                    },
                    sideSheetComponent = sideSheetComponent
                )
            }

        }
    }

    InvestigationPopup(
        modifier = Modifier
            .fillMaxSize(),
        backgroundColor = LocalPalette.current.scrim,
        shown = popupUiState.isShown,
    ) { modifier ->
        popupUiState.ghostPopupRecord?.let { record ->
            GhostPopup(
                modifier = modifier,
                record = record,
            ) { investigationViewModel.onEvent(ClearPopup) }
        }
        popupUiState.evidencePopupRecord?.let { record ->
            EvidencePopup(
                modifier = modifier,
                record = record
            ) { investigationViewModel.onEvent(ClearPopup) }
        }
    }
}

@Composable
private fun ColumnScope.Investigation(
    modifier: Modifier = Modifier,
    operationToolbarUiState: OperationToolbarUiState,
    toolbarUiActions: ToolbarUiActions,
    statusBarComponent: @Composable (Modifier) -> Unit = {},
    bottomSheetComponent: @Composable (Modifier) -> Unit,
    journalComponent: @Composable (Modifier) -> Unit
) {

    val toolbarComponent: @Composable (Modifier) -> Unit = { modifier ->
        OperationToolbar(
            modifier = modifier
                .heightIn(min = 48.dp),
            category = operationToolbarUiState.category,
            onChangeToolbarCategory = toolbarUiActions.onChangeToolbarCategory,
            onReset = toolbarUiActions.onReset,
            containerColor = LocalPalette.current.surfaceContainerHigh
        )
    }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        journalComponent(
            Modifier
                .weight(1f, false)
                .padding(horizontal = 8.dp)
        )

        statusBarComponent(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )

        HorizontalToolbar(
            modifier = Modifier
                .padding(8.dp),
            selectBarComponent = { modifier -> toolbarComponent(modifier) },
            content = { modifier ->
                bottomSheetComponent(
                    modifier
                        .fillMaxWidth()
                        .animateContentSize()
                        .then(
                            if (!operationToolbarUiState.isCollapsed)
                                Modifier
                                    .alpha(1f)
                                    .wrapContentHeight()
                            else
                                Modifier
                                    .height(0.dp)
                                    .alpha(0f)
                        )
                )
            }
        )
    }
}

@Composable
private fun RowScope.Investigation(
    modifier: Modifier = Modifier,
    operationToolbarUiState: OperationToolbarUiState,
    toolbarUiActions: ToolbarUiActions,
    statusBarComponent: @Composable (Modifier) -> Unit = {},
    journalComponent: @Composable (Modifier) -> Unit,
    sideSheetComponent: @Composable (Modifier) -> Unit
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.Start),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val toolbarContent: @Composable (Modifier) -> Unit = { modifier ->
            OperationToolRail(
                modifier = modifier
                    .widthIn(min = 48.dp),
                category = operationToolbarUiState.category,
                onChangeToolbarCategory = toolbarUiActions.onChangeToolbarCategory,
                onReset = toolbarUiActions.onReset,
                containerColor = LocalPalette.current.surfaceContainerHigh
            )
        }

        VerticalToolbar(
            modifier = Modifier
                .padding(8.dp),
            selectRailComponent = { modifier -> toolbarContent(modifier) },
            content = { modifier ->
                sideSheetComponent(
                    modifier
                        .fillMaxHeight()
                        .animateContentSize()
                        .then(
                            if (!operationToolbarUiState.isCollapsed)
                                Modifier
                                    .fillMaxWidth(.35f)
                                    .alpha(1f)
                            else
                                Modifier
                                    .height(0.dp)
                                    .alpha(0f)
                        )
                )
            }
        )

        Column(
            modifier = Modifier
                .weight(1f, false),
            verticalArrangement = Arrangement.Top
        ) {

            statusBarComponent(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )

            journalComponent(Modifier
                .weight(1f)
            )
        }

    }
}

@Composable
private fun HorizontalToolbar(
    modifier: Modifier = Modifier,
    selectBarComponent: @Composable (Modifier) -> Unit = {},
    content: @Composable (Modifier) -> Unit = {}
) {
    Surface(
        modifier = Modifier,
        color = LocalPalette.current.surfaceContainerLow,
        shape = RoundedCornerShape(
            topStart = 16.dp, topEnd = 16.dp, bottomStart = 0.dp, bottomEnd = 0.dp)
    ) {
        Column(
            modifier = modifier
        ) {
            selectBarComponent(
                Modifier
                    .heightIn(min = 48.dp)
            )

            content(Modifier)
        }
    }
}

@Composable
private fun VerticalToolbar(
    modifier: Modifier = Modifier,
    selectRailComponent: @Composable (Modifier) -> Unit = {},
    content: @Composable (Modifier) -> Unit = {}
) {
    Surface(
        modifier = Modifier,
        color = LocalPalette.current.surfaceContainerLow,
        shape = RoundedCornerShape(
            topStart = 0.dp, topEnd = 16.dp, bottomStart = 0.dp, bottomEnd = 16.dp
        )
    ) {
        Row(
            modifier = modifier
        ) {
            content(Modifier)

            selectRailComponent(
                Modifier
                    .widthIn(min = 48.dp)
            )
        }
    }
}

@Composable
fun OperationConfigsBottomSheet(
    modifier: Modifier = Modifier,
    sanityMedicationComponent: @Composable (Modifier) -> Unit = {},
    playerDeathButtonComponent: @Composable (Modifier) -> Unit = {},
    timerComponent: @Composable (Modifier) -> Unit = {},
    mapConfigComponent: @Composable (Modifier) -> Unit = {},
    difficultyConfigComponent: @Composable (Modifier) -> Unit = {},
    customDifficultyConfigComponent: @Composable (Modifier) -> Unit = {},
    weatherConfigComponent: @Composable (Modifier) -> Unit = {},
    temperatureMeterComponent: @Composable (Modifier) -> Unit = {},
    fuseBoxControlComponent: @Composable (Modifier) -> Unit = {},
    sanityMeterComponent: @Composable (Modifier) -> Unit = {},
    showTemperatureMeterComponent: Boolean,
    showEditCustomDifficultyComponent: Boolean
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically) {

            HorizontalDivider(
                modifier = Modifier
                    .weight(1f),
                color = LocalPalette.current.onSurfaceVariant,
                thickness = Dp.Hairline
            )

            Text(
                modifier = Modifier
                    .wrapContentWidth(),
                text = stringResource(R.string.investigation_label_operation_configuration).uppercase(),
                color = LocalPalette.current.onSurfaceVariant,
                style = LocalTypography.current.quaternary.bold.copy(
                    textAlign = TextAlign.Start
                ),
                fontSize = 18.sp,
                maxLines = 1
            )

            HorizontalDivider(
                modifier = Modifier
                    .weight(1f),
                color = LocalPalette.current.onSurfaceVariant,
                thickness = Dp.Hairline
            )
        }

        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                color = LocalPalette.current.surfaceContainer,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = LocalPalette.current.surfaceContainerLow
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.Start
                ) {

                    mapConfigComponent(
                        Modifier
                            .fillMaxWidth()
                    )

                    /*Row(
                        modifier = Modifier.
                            fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        difficultyConfigComponent(
                            Modifier
                                .weight(1f)
                        )

                        if(showEditCustomDifficultyComponent) {
                            editCustomDifficultyComponent(
                                Modifier
                                    .size(48.dp)
                                    .padding(8.dp)
                            )
                        }
                    }*/

                    Row(
                        modifier = Modifier.
                            fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {

                        difficultyConfigComponent(
                            Modifier
                                .weight(1f)
                        )

                    }
                    if(showEditCustomDifficultyComponent) {
                        customDifficultyConfigComponent(
                            Modifier
                                .fillMaxWidth()
                        )
                    }

                    weatherConfigComponent(
                        Modifier
                            .fillMaxWidth()
                    )
                }
            }


            Column(
                Modifier
                    .fillMaxHeight()
                    .width(IntrinsicSize.Min),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxWidth(),
                    color = LocalPalette.current.surfaceContainer,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(
                        width = 2.dp,
                        color = LocalPalette.current.surfaceContainerLow
                    )
                ) {
                    timerComponent(
                        Modifier
                            .width(IntrinsicSize.Min)
                            .padding(8.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
                ) {
                    if (showTemperatureMeterComponent) {

                        Surface(
                            modifier = Modifier
                                .width(IntrinsicSize.Min),
                            color = LocalPalette.current.surfaceContainer,
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(
                                width = 2.dp,
                                color = LocalPalette.current.surfaceContainerLow
                            )
                        ) {

                            temperatureMeterComponent(
                                Modifier
                                    .width(IntrinsicSize.Min)
                                    .padding(8.dp)
                            )

                        }

                    }

                    fuseBoxControlComponent(
                        Modifier
                            .width(48.dp)
                            .fillMaxHeight()
                            .heightIn(min = 48.dp)
                    )
                }
            }

        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                modifier = Modifier
                    .weight(1f),
                color = LocalPalette.current.surfaceContainer,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = LocalPalette.current.surfaceContainerLow
                )
            ) {
                sanityMeterComponent(
                    Modifier
                        .height(48.dp)
                        .weight(1f)
                        .padding(8.dp)
                )
            }

            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Surface(
                    modifier = Modifier,
                    color = LocalPalette.current.surfaceContainer,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(
                        width = 2.dp,
                        color = LocalPalette.current.surfaceContainerLow
                    )
                ) {
                    sanityMedicationComponent(
                        Modifier
                            .size(48.dp)
                    )
                }

                Surface(
                    modifier = Modifier,
                    color = LocalPalette.current.surfaceContainer,
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(
                        width = 2.dp,
                        color = LocalPalette.current.surfaceContainerLow
                    )
                ) {
                    playerDeathButtonComponent(
                        Modifier
                            .size(48.dp)
                    )
                }

            }

        }

    }

}

@Composable
fun OperationConfigsSideSheet(
    modifier: Modifier = Modifier,
    sanityMedicationComponent: @Composable (Modifier) -> Unit = {},
    playerDeathButtonComponent: @Composable (Modifier) -> Unit = {},
    timerComponent: @Composable (Modifier) -> Unit = {},
    mapConfigComponent: @Composable (Modifier) -> Unit = {},
    difficultyConfigComponent: @Composable (Modifier) -> Unit = {},
    customDifficultyConfigComponent: @Composable (Modifier) -> Unit = {},
    weatherConfigComponent: @Composable (Modifier) -> Unit = {},
    temperatureMeterComponent: @Composable (Modifier) -> Unit = {},
    fuseBoxControlComponent: @Composable (Modifier) -> Unit = {},
    sanityMeterComponent: @Composable (Modifier) -> Unit = {},
    showTemperatureMeterComponent: Boolean,
    showEditCustomDifficultyComponent: Boolean
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically) {

            HorizontalDivider(
                modifier = Modifier
                    .weight(1f),
                color = LocalPalette.current.onSurfaceVariant,
                thickness = Dp.Hairline
            )

            Text(
                modifier = Modifier
                    .wrapContentWidth(),
                text = "Operation Config".uppercase(),
                color = LocalPalette.current.onSurfaceVariant,
                style = LocalTypography.current.quaternary.bold.copy(
                    textAlign = TextAlign.Start
                ),
                fontSize = 18.sp,
                maxLines = 1
            )
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f),
                color = LocalPalette.current.onSurfaceVariant,
                thickness = Dp.Hairline
            )
        }

        Surface(
            modifier = Modifier,
            color = LocalPalette.current.surfaceContainer,
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(
                width = 2.dp,
                color = LocalPalette.current.surfaceContainerLow
            )
        ) {
            Column(
                modifier = Modifier
                    .padding(8.dp),
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start
            ) {

                mapConfigComponent(
                    Modifier
                        .fillMaxWidth()
                )

                difficultyConfigComponent(
                    Modifier
                        .fillMaxWidth()
                )

                if(showEditCustomDifficultyComponent) {
                    customDifficultyConfigComponent(
                        Modifier
                            .fillMaxWidth()
                    )
                }

            }
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            color = LocalPalette.current.surfaceContainer,
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(
                width = 2.dp,
                color = LocalPalette.current.surfaceContainerLow
            )
        ) {
            Box(
                Modifier
                    .padding(8.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                timerComponent(
                    Modifier
                        .fillMaxWidth()
                )
            }
        }


        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                modifier = Modifier
                    .weight(1f),
                color = LocalPalette.current.surfaceContainer,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = LocalPalette.current.surfaceContainerLow
                )
            ) {
                sanityMeterComponent(
                    Modifier
                        .height(48.dp)
                        .weight(1f)
                        .padding(8.dp)
                )
            }

            Surface(
                modifier = Modifier,
                color = LocalPalette.current.surfaceContainer,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = LocalPalette.current.surfaceContainerLow
                )
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    sanityMedicationComponent(
                        Modifier
                            .size(48.dp)
                    )
                }
            }

        }

        Row(
            modifier = Modifier
                .height(IntrinsicSize.Max)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                color = LocalPalette.current.surfaceContainer,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = LocalPalette.current.surfaceContainerLow
                )
            ) {
                Column(
                    modifier = Modifier
                        .padding(8.dp),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.Start
                ) {

                    weatherConfigComponent(
                        Modifier
                            .fillMaxWidth()
                    )

                }
            }

            Surface(
                modifier = Modifier
                    .fillMaxHeight(),
                color = LocalPalette.current.surfaceContainer,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = LocalPalette.current.surfaceContainerLow
                )
            ) {
                Box(
                    Modifier
                        .padding(8.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    temperatureMeterComponent(
                        Modifier
                            .width(IntrinsicSize.Min)
                    )
                }
            }

        }

    }

}

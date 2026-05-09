package com.tritiumgaming.feature.investigation.ui.sheet

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.impl.composite.HuntCooldownDurationIcon
import com.tritiumgaming.core.ui.icon.impl.composite.HuntDurationIcon
import com.tritiumgaming.core.ui.icon.impl.composite.PreventHuntIcon
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer.GraphPoint
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer.RealtimeUiState
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarUiColors
import com.tritiumgaming.core.ui.widgets.progressbar.ProgressBarNotch
import com.tritiumgaming.feature.investigation.ui.OperationConfigsBottomSheet
import com.tritiumgaming.feature.investigation.ui.OperationConfigsSideSheet
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetails
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetailsUiState
import com.tritiumgaming.feature.investigation.ui.tool.configs.CustomDifficultyConfigControl
import com.tritiumgaming.feature.investigation.ui.tool.configs.DifficultyConfigControl
import com.tritiumgaming.feature.investigation.ui.tool.configs.FuseBoxButton
import com.tritiumgaming.feature.investigation.ui.tool.configs.MapConfigControl
import com.tritiumgaming.feature.investigation.ui.tool.configs.WeatherConfigComponent
import com.tritiumgaming.feature.investigation.ui.tool.footstep.BpmTool
import com.tritiumgaming.feature.investigation.ui.tool.footstep.visualizer.VisualizerMeasurementType
import com.tritiumgaming.feature.investigation.ui.tool.operationtimer.OperationTimerColumn
import com.tritiumgaming.feature.investigation.ui.tool.operationtimer.OperationTimerRow
import com.tritiumgaming.feature.investigation.ui.tool.phase.PhaseUiState
import com.tritiumgaming.feature.investigation.ui.tool.sanity.PlayerDeathButton
import com.tritiumgaming.feature.investigation.ui.tool.sanity.SanityMedicationButton
import com.tritiumgaming.feature.investigation.ui.tool.sanity.SanityMeterComponent
import com.tritiumgaming.feature.investigation.ui.tool.temperature.TemperatureComponent
import com.tritiumgaming.feature.investigation.ui.tool.temperature.TemperatureStateBundle
import com.tritiumgaming.feature.investigation.ui.tool.timers.ProgressBarTimer
import com.tritiumgaming.feature.investigation.ui.tool.timers.TimerTools
import com.tritiumgaming.feature.investigation.ui.tool.traits.TraitConfig
import com.tritiumgaming.feature.investigation.ui.tool.traits.TraitListItemUiColors
import com.tritiumgaming.feature.investigation.ui.toolbar.operation.OperationToolbarUiState
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyType
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Weather
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitCategory
import com.tritiumgaming.shared.data.investigation.model.DifficultyOverridesData.Companion.FuseBoxFlag
import com.tritiumgaming.shared.data.investigation.model.GhostTraitFilterUiOptions
import com.tritiumgaming.shared.data.investigation.model.ValidatedGhostTrait

@Composable
internal fun ToolsBottomSheetComponent(
    modifier: Modifier = Modifier,
    // Toolbar state
    toolbarCategory: OperationToolbarUiState.Category,
    // Weather state
    weather: Weather,
    weatherIcon: Int,
    weatherDropdownOptions: List<Int>,
    isWeatherDropdownEnabled: Boolean,
    weatherDropdownLabel: Int,
    onWeatherDropdownSelect: (Int) -> Unit,
    // Map state
    mapDropdownOptions: List<Int>,
    isMapDropdownEnabled: Boolean,
    mapDropdownLabel: Int,
    onMapDropdownSelect: (Int) -> Unit,
    // Difficulty state
    difficulty: DifficultyType,
    difficultyDropdownOptions: List<Int>,
    isDifficultyDropdownEnabled: Boolean,
    difficultyDropdownLabel: Int,
    onDifficultyDropdownSelect: (Int) -> Unit,
    // Custom Difficulty State
    onNavigateToEditCustomDifficulty: () -> Unit,
    customDifficultyDropdownOptions: List<String>,
    customDifficultyDropdownLabel: String,
    onCustomDifficultyDropdownSelect: (Int) -> Unit,
    // Sanity state
    sanityLevel: Float,
    insanityLevel: Float,
    onSanityChange: (Float) -> Unit,
    onUseSanityMedication: () -> Unit,
    onPlayerDeath: () -> Unit,
    // Timer state
    timerRemainingTime: String,
    timerPaused: Boolean,
    onTimerToggle: () -> Unit,
    onTimerSkip: () -> Unit,
    phaseUiState: PhaseUiState,
    // Temperature state
    temperatureStateBundle: TemperatureStateBundle,
    // FuseBox state
    fuseBoxFlag: FuseBoxFlag,
    onTogglePower: () -> Unit,
    // Trait state
    traitListOptions: GhostTraitFilterUiOptions,
    traitList: List<ValidatedGhostTrait>,
    onSelectTraitCategory: (TraitCategory) -> Unit,
    onSelectTrait: (ValidatedGhostTrait) -> Unit,
    onToggleUniqueOnly: () -> Unit,
    // Analyzer state
    operationDetailsUiState: OperationDetailsUiState,
    // Timers state
    smudgeHuntPreventionTitle: String,
    smudgeHuntPreventionMax: Long,
    smudgeHuntPreventionRemaining: Long,
    smudgeHuntPreventionTimeText: String,
    smudgeHuntPreventionRunning: Boolean,
    onSmudgeToggle: () -> Unit,
    smudgeNotches: List<ProgressBarNotch>,

    huntDurationTitle: String,
    huntDurationMax: Long,
    huntDurationRemaining: Long,
    huntDurationTimeText: String,
    huntDurationRunning: Boolean,
    onHuntDurationToggle: () -> Unit,
    huntDurationNotches: List<ProgressBarNotch>,

    huntCooldownTitle: String,
    huntCooldownMax: Long,
    huntCooldownRemaining: Long,
    huntCooldownTimeText: String,
    huntCooldownRunning: Boolean,
    onHuntCooldownToggle: () -> Unit,
    huntCooldownNotches: List<ProgressBarNotch>,

    fingerprintTimerTitle: String,
    fingerprintTimerMax: Long,
    fingerprintTimerRemaining: Long,
    fingerprintTimerTimeText: String,
    fingerprintTimerRunning: Boolean,
    onFingerprintToggle: () -> Unit,
    fingerprintNotches: List<ProgressBarNotch>,

    // BPM Tool state
    bpmRealtimeState: RealtimeUiState<GraphPoint>,
    bpmMeasurementType: VisualizerMeasurementType,
    bpmApplyMeasurement: Boolean,
    bpmGhostSpeedModifier: Float,
    bpmFuseBoxFlag: FuseBoxFlag,
    bpmDomainMillis: Long,
    bpmDomainSampleIntervalMillis: Long,
    bpmGhostSpeed: DifficultySettingResources.GhostSpeed,
    onBpmUpdate: (RealtimeUiState<GraphPoint>) -> Unit,
    onBpmChangeMeasurementType: (VisualizerMeasurementType) -> Unit,
    onBpmToggleApplyMeasurement: () -> Unit,
    onBpmChangeDomain: (Long) -> Unit,
    onBpmChangeSampleInterval: (Long) -> Unit,
    // Colors
    notchedProgressBarUiColors: NotchedProgressBarUiColors
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        when (toolbarCategory) {
            OperationToolbarUiState.Category.TOOL_NONE -> {}
            OperationToolbarUiState.Category.TOOL_CONFIG -> {
                OperationConfigsBottomSheet(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .height(IntrinsicSize.Max),
                    mapConfigComponent = { modifier ->
                        MapConfigControl(
                            modifier = modifier,
                            dropdownOptions = mapDropdownOptions,
                            isDropdownEnabled = isMapDropdownEnabled,
                            dropdownLabel = mapDropdownLabel,
                            onDropdownSelect = onMapDropdownSelect
                        )
                    },
                    difficultyConfigComponent = { modifier ->
                        DifficultyConfigControl(
                            modifier = modifier,
                            dropdownOptions = difficultyDropdownOptions,
                            isDropdownEnabled = isDifficultyDropdownEnabled,
                            dropdownLabel = difficultyDropdownLabel,
                            onDropdownSelect = onDifficultyDropdownSelect
                        )
                    },
                    customDifficultyConfigComponent = { modifier ->
                        CustomDifficultyConfigControl(
                            modifier = modifier,
                            dropdownOptions = customDifficultyDropdownOptions,
                            isDropdownEnabled = isDifficultyDropdownEnabled,
                            dropdownLabel = customDifficultyDropdownLabel,
                            onDropdownSelect = { onCustomDifficultyDropdownSelect(it) }
                        ) { modifier ->
                            IconButton(
                                modifier = modifier,
                                onClick = { onNavigateToEditCustomDifficulty() }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_settings),
                                    contentDescription = "Custom Difficulty Settings",
                                    tint = LocalPalette.current.onSurface
                                )
                            }
                        }
                    },
                    weatherConfigComponent = { modifier ->
                        WeatherConfigComponent(
                            modifier = modifier,
                            icon = weatherIcon,
                            dropdownOptions = weatherDropdownOptions,
                            isDropdownEnabled = isWeatherDropdownEnabled,
                            dropdownLabel = weatherDropdownLabel,
                            onDropdownSelect = onWeatherDropdownSelect
                        )
                    },
                    sanityMeterComponent = { modifier ->
                        SanityMeterComponent(
                            modifier = modifier,
                            sanityLevel = sanityLevel,
                            insanityLevel = insanityLevel,
                            onSanityChange = onSanityChange
                        )
                    },
                    timerComponent = { modifier ->
                        OperationTimerColumn(
                            modifier = modifier,
                            remainingTime = timerRemainingTime,
                            paused = timerPaused,
                            onToggle = onTimerToggle,
                            onSkip = onTimerSkip,
                            phaseUiState = phaseUiState
                        )
                    },
                    sanityMedicationComponent = { modifier ->
                        SanityMedicationButton(
                            modifier = modifier,
                            onClick = onUseSanityMedication
                        )
                    },
                    playerDeathButtonComponent = { modifier ->
                        PlayerDeathButton(
                            modifier = modifier,
                            onClick = onPlayerDeath
                        )
                    },
                    temperatureMeterComponent = { modifier ->
                        TemperatureComponent(
                            modifier = modifier,
                            state = temperatureStateBundle
                        )
                    },
                    fuseBoxControlComponent = { modifier ->
                        FuseBoxButton(
                            modifier = modifier,
                            flag = fuseBoxFlag,
                            onTogglePower = onTogglePower
                        )
                    },
                    showTemperatureMeterComponent = weather != Weather.RANDOM,
                    showEditCustomDifficultyComponent =
                        difficulty == DifficultyType.CUSTOM
                )
            }

            OperationToolbarUiState.Category.TOOL_TRAITS -> {
                TraitConfig(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxHeight(.5f),
                    uniqueOnly = traitListOptions.uniqueOnly ?: false,
                    categories = traitListOptions.category,
                    list = traitList,
                    onSelectCategory = onSelectTraitCategory,
                    onSelectTrait = onSelectTrait,
                    onToggleUniqueOnly = onToggleUniqueOnly,
                    colors = TraitListItemUiColors(
                        unselectedColor = LocalPalette.current.surfaceContainerHigh,
                        unselectedOnColor = LocalPalette.current.onSurface,
                        selectedColor = LocalPalette.current.surfaceContainerLow,
                        selectedOnColor = LocalPalette.current.onSurfaceVariant,
                    )
                )
            }

            OperationToolbarUiState.Category.TOOL_ANALYZER -> {
                OperationDetails(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .height(IntrinsicSize.Max),
                    operationDetailsUiState = operationDetailsUiState
                )
            }

            OperationToolbarUiState.Category.TOOL_TIMERS -> {
                TimerTools(
                    modifier = Modifier
                        .padding(8.dp)
                        .height(IntrinsicSize.Max),
                ) {

                    Surface(
                        modifier = Modifier,
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        ProgressBarTimer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            title = smudgeHuntPreventionTitle,
                            max = smudgeHuntPreventionMax,
                            remaining = smudgeHuntPreventionRemaining,
                            timeText = smudgeHuntPreventionTimeText,
                            running = smudgeHuntPreventionRunning,
                            onToggle = onSmudgeToggle,
                            notches = smudgeNotches,
                            colors = notchedProgressBarUiColors
                        ) { modifier ->
                            PreventHuntIcon(
                                modifier = modifier,
                                colors = IconVectorColors.defaults().copy(
                                    fillColor = LocalPalette.current.onSurface
                                )
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier,
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        ProgressBarTimer(
                            modifier = Modifier
                                .padding(8.dp),
                            title = huntDurationTitle,
                            max = huntDurationMax,
                            remaining = huntDurationRemaining,
                            timeText = huntDurationTimeText,
                            running = huntDurationRunning,
                            onToggle = onHuntDurationToggle,
                            notches = huntDurationNotches,
                            colors = notchedProgressBarUiColors
                        ) { modifier ->
                            HuntDurationIcon(
                                modifier = modifier,
                                colors = IconVectorColors.defaults().copy(
                                    fillColor = LocalPalette.current.onSurface
                                )
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier,
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        ProgressBarTimer(
                            modifier = Modifier
                                .padding(8.dp),
                            title = huntCooldownTitle,
                            max = huntCooldownMax,
                            remaining = huntCooldownRemaining,
                            timeText = huntCooldownTimeText,
                            running = huntCooldownRunning,
                            onToggle = onHuntCooldownToggle,
                            notches = huntCooldownNotches,
                            colors = notchedProgressBarUiColors
                        ) { modifier ->
                            HuntCooldownDurationIcon(
                                modifier = modifier,
                                colors = IconVectorColors.defaults().copy(
                                    fillColor = LocalPalette.current.onSurface
                                )
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier,
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        ProgressBarTimer(
                            modifier = Modifier
                                .padding(8.dp),
                            title = fingerprintTimerTitle,
                            max = fingerprintTimerMax,
                            remaining = fingerprintTimerRemaining,
                            timeText = fingerprintTimerTimeText,
                            running = fingerprintTimerRunning,
                            onToggle = onFingerprintToggle,
                            notches = fingerprintNotches,
                            colors = notchedProgressBarUiColors
                        ) { modifier ->
                            HuntCooldownDurationIcon(
                                modifier = modifier,
                                colors = IconVectorColors.defaults().copy(
                                    fillColor = LocalPalette.current.onSurface
                                )
                            )
                        }
                    }
                }
            }

            OperationToolbarUiState.Category.TOOL_FOOTSTEP -> {
                BpmTool(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .height(IntrinsicSize.Max),
                    realtimeState = bpmRealtimeState,
                    measurementType = bpmMeasurementType,
                    applyMeasurement = bpmApplyMeasurement,
                    ghostSpeedModifier = bpmGhostSpeedModifier,
                    fuseBoxFlag = bpmFuseBoxFlag,
                    domainMillis = bpmDomainMillis,
                    domainSampleIntervalMillis = bpmDomainSampleIntervalMillis,
                    ghostSpeed = bpmGhostSpeed,
                    weather = weather,
                    fuseBox = fuseBoxFlag,
                    onUpdate = onBpmUpdate,
                    onChangeMeasurementType = onBpmChangeMeasurementType,
                    toggleApplyMeasurement = onBpmToggleApplyMeasurement,
                    onChangeDomain = onBpmChangeDomain,
                    onChangeDomainSampleInterval = onBpmChangeSampleInterval
                )
            }

        }
    }
}

@Composable
internal fun ToolsSideSheetComponent(
    modifier: Modifier = Modifier,
    // Toolbar state
    toolbarCategory: OperationToolbarUiState.Category,
    // Weather state
    weather: Weather,
    weatherIcon: Int,
    weatherDropdownOptions: List<Int>,
    isWeatherDropdownEnabled: Boolean,
    weatherDropdownLabel: Int,
    onWeatherDropdownSelect: (Int) -> Unit,
    // Map state
    mapDropdownOptions: List<Int>,
    isMapDropdownEnabled: Boolean,
    mapDropdownLabel: Int,
    onMapDropdownSelect: (Int) -> Unit,
    // Difficulty state
    difficulty: DifficultyType,
    difficultyDropdownOptions: List<Int>,
    isDifficultyDropdownEnabled: Boolean,
    difficultyDropdownLabel: Int,
    onDifficultyDropdownSelect: (Int) -> Unit,
    // Custom Difficulty State
    onNavigateToEditCustomDifficulty: () -> Unit,
    customDifficultyDropdownOptions: List<String>,
    customDifficultyDropdownLabel: String,
    onCustomDifficultyDropdownSelect: (Int) -> Unit,
    // Sanity state
    sanityLevel: Float,
    insanityLevel: Float,
    onSanityChange: (Float) -> Unit,
    onUseSanityMedication: () -> Unit,
    onPlayerDeath: () -> Unit,
    // Timer state
    timerRemainingTime: String,
    timerPaused: Boolean,
    onTimerToggle: () -> Unit,
    onTimerSkip: () -> Unit,
    phaseUiState: PhaseUiState,
    // Temperature state
    temperatureStateBundle: TemperatureStateBundle,
    // FuseBox state
    fuseBoxFlag: FuseBoxFlag,
    onTogglePower: () -> Unit,
    // Trait state
    traitListOptions: GhostTraitFilterUiOptions,
    traitList: List<ValidatedGhostTrait>,
    onSelectTraitCategory: (TraitCategory) -> Unit,
    onSelectTrait: (ValidatedGhostTrait) -> Unit,
    onToggleUniqueOnly: () -> Unit,
    // Analyzer state
    operationDetailsUiState: OperationDetailsUiState,
    // Timers state
    smudgeHuntPreventionTitle: String,
    smudgeHuntPreventionMax: Long,
    smudgeHuntPreventionRemaining: Long,
    smudgeHuntPreventionTimeText: String,
    smudgeHuntPreventionRunning: Boolean,
    onSmudgeToggle: () -> Unit,
    smudgeNotches: List<ProgressBarNotch>,

    huntDurationTitle: String,
    huntDurationMax: Long,
    huntDurationRemaining: Long,
    huntDurationTimeText: String,
    huntDurationRunning: Boolean,
    onHuntDurationToggle: () -> Unit,
    huntDurationNotches: List<ProgressBarNotch>,

    huntCooldownTitle: String,
    huntCooldownMax: Long,
    huntCooldownRemaining: Long,
    huntCooldownTimeText: String,
    huntCooldownRunning: Boolean,
    onHuntCooldownToggle: () -> Unit,
    huntCooldownNotches: List<ProgressBarNotch>,

    fingerprintTimerTitle: String,
    fingerprintTimerMax: Long,
    fingerprintTimerRemaining: Long,
    fingerprintTimerTimeText: String,
    fingerprintTimerRunning: Boolean,
    onFingerprintToggle: () -> Unit,
    fingerprintNotches: List<ProgressBarNotch>,

    // BPM Tool state
    bpmRealtimeState: RealtimeUiState<GraphPoint>,
    bpmMeasurementType: VisualizerMeasurementType,
    bpmApplyMeasurement: Boolean,
    bpmGhostSpeed: DifficultySettingResources.GhostSpeed,
    bpmGhostSpeedModifier: Float,
    bpmWeather: Weather,
    bpmFuseBoxFlag: FuseBoxFlag,
    bpmDomainMillis: Long,
    bpmDomainSampleIntervalMillis: Long,
    onBpmUpdate: (RealtimeUiState<GraphPoint>) -> Unit,
    onBpmChangeMeasurementType: (VisualizerMeasurementType) -> Unit,
    onBpmToggleApplyMeasurement: () -> Unit,
    onBpmChangeDomain: (Long) -> Unit,
    onBpmChangeSampleInterval: (Long) -> Unit,
    // Colors
    notchedProgressBarUiColors: NotchedProgressBarUiColors
) {

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        when (toolbarCategory) {
            OperationToolbarUiState.Category.TOOL_NONE -> {}
            OperationToolbarUiState.Category.TOOL_CONFIG -> {

                OperationConfigsSideSheet(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .height(IntrinsicSize.Max),
                    sanityMedicationComponent = { modifier ->
                        SanityMedicationButton(
                            modifier = modifier,
                            onClick = onUseSanityMedication
                        )
                    },
                    mapConfigComponent = { modifier ->
                        MapConfigControl(
                            modifier = modifier,
                            dropdownOptions = mapDropdownOptions,
                            isDropdownEnabled = isMapDropdownEnabled,
                            dropdownLabel = mapDropdownLabel,
                            onDropdownSelect = onMapDropdownSelect
                        )
                    },
                    difficultyConfigComponent = { modifier ->
                        DifficultyConfigControl(
                            modifier = modifier,
                            dropdownOptions = difficultyDropdownOptions,
                            isDropdownEnabled = isDifficultyDropdownEnabled,
                            dropdownLabel = difficultyDropdownLabel,
                            onDropdownSelect = onDifficultyDropdownSelect
                        )
                    },
                    customDifficultyConfigComponent = { modifier ->
                        CustomDifficultyConfigControl(
                            modifier = modifier,
                            dropdownOptions = customDifficultyDropdownOptions,
                            isDropdownEnabled = isDifficultyDropdownEnabled,
                            dropdownLabel = customDifficultyDropdownLabel,
                            onDropdownSelect = { onCustomDifficultyDropdownSelect(it) }
                        ) { modifier ->
                            IconButton(
                                modifier = modifier,
                                onClick = { onNavigateToEditCustomDifficulty() }
                            ) {
                                Icon(
                                    painter = painterResource(R.drawable.ic_settings),
                                    contentDescription = "Custom Difficulty Settings",
                                    tint = LocalPalette.current.onSurface
                                )
                            }
                        }
                    },
                    weatherConfigComponent = { modifier ->
                        WeatherConfigComponent(
                            modifier = modifier,
                            icon = weatherIcon,
                            dropdownOptions = weatherDropdownOptions,
                            isDropdownEnabled = isWeatherDropdownEnabled,
                            dropdownLabel = weatherDropdownLabel,
                            onDropdownSelect = onWeatherDropdownSelect
                        )
                    },
                    sanityMeterComponent = { modifier ->
                        SanityMeterComponent(
                            modifier = modifier,
                            sanityLevel = sanityLevel,
                            insanityLevel = insanityLevel,
                            onSanityChange = onSanityChange
                        )
                    },
                    timerComponent = { modifier ->
                        OperationTimerRow(
                            modifier = modifier,
                            remainingTime = timerRemainingTime,
                            paused = timerPaused,
                            onToggle = onTimerToggle,
                            onSkip = onTimerSkip,
                            phaseUiState = phaseUiState
                        )
                    },
                    playerDeathButtonComponent = { modifier ->
                        PlayerDeathButton(
                            modifier = modifier,
                            onClick = onPlayerDeath
                        )
                    },
                    temperatureMeterComponent = { modifier ->
                        TemperatureComponent(
                            modifier = modifier,
                            state = temperatureStateBundle
                        )
                    },
                    fuseBoxControlComponent = { modifier ->
                        FuseBoxButton(
                            modifier = modifier,
                            flag = fuseBoxFlag,
                            onTogglePower = onTogglePower
                        )
                    },
                    showTemperatureMeterComponent = weather != Weather.RANDOM,
                    showEditCustomDifficultyComponent =
                        difficulty == DifficultyType.CUSTOM
                )
            }

            OperationToolbarUiState.Category.TOOL_TRAITS -> {
                TraitConfig(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxHeight(),
                    uniqueOnly = traitListOptions.uniqueOnly ?: false,
                    categories = traitListOptions.category,
                    list = traitList,
                    onSelectCategory = onSelectTraitCategory,
                    onSelectTrait = onSelectTrait,
                    onToggleUniqueOnly = onToggleUniqueOnly,
                    colors = TraitListItemUiColors(
                        unselectedColor = LocalPalette.current.surfaceContainerHigh,
                        unselectedOnColor = LocalPalette.current.onSurface,
                        selectedColor = LocalPalette.current.surfaceContainerLow,
                        selectedOnColor = LocalPalette.current.onSurfaceVariant,
                    )
                )
            }

            OperationToolbarUiState.Category.TOOL_ANALYZER -> {
                OperationDetails(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .height(IntrinsicSize.Max),
                    operationDetailsUiState = operationDetailsUiState
                )
            }

            OperationToolbarUiState.Category.TOOL_TIMERS -> {
                TimerTools(
                    modifier = Modifier
                        .padding(8.dp)
                        .height(IntrinsicSize.Max),
                ) {

                    Surface(
                        modifier = Modifier,
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        ProgressBarTimer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            title = smudgeHuntPreventionTitle,
                            max = smudgeHuntPreventionMax,
                            remaining = smudgeHuntPreventionRemaining,
                            timeText = smudgeHuntPreventionTimeText,
                            running = smudgeHuntPreventionRunning,
                            onToggle = onSmudgeToggle,
                            notches = smudgeNotches,
                            colors = notchedProgressBarUiColors
                        ) { modifier ->
                            PreventHuntIcon(
                                modifier = modifier,
                                colors = IconVectorColors.defaults().copy(
                                    fillColor = LocalPalette.current.onSurface
                                )
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier,
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        ProgressBarTimer(
                            modifier = Modifier
                                .padding(8.dp),
                            title = huntDurationTitle,
                            max = huntDurationMax,
                            remaining = huntDurationRemaining,
                            timeText = huntDurationTimeText,
                            running = huntDurationRunning,
                            onToggle = onHuntDurationToggle,
                            notches = huntDurationNotches,
                            colors = notchedProgressBarUiColors
                        ) { modifier ->
                            HuntDurationIcon(
                                modifier = modifier,
                                colors = IconVectorColors.defaults().copy(
                                    fillColor = LocalPalette.current.onSurface
                                )
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier,
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        ProgressBarTimer(
                            modifier = Modifier
                                .padding(8.dp),
                            title = huntCooldownTitle,
                            max = huntCooldownMax,
                            remaining = huntCooldownRemaining,
                            timeText = huntCooldownTimeText,
                            running = huntCooldownRunning,
                            onToggle = onHuntCooldownToggle,
                            notches = huntCooldownNotches,
                            colors = notchedProgressBarUiColors
                        ) { modifier ->
                            HuntCooldownDurationIcon(
                                modifier = modifier,
                                colors = IconVectorColors.defaults().copy(
                                    fillColor = LocalPalette.current.onSurface
                                )
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier,
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        ProgressBarTimer(
                            modifier = Modifier
                                .padding(8.dp),
                            title = fingerprintTimerTitle,
                            max = fingerprintTimerMax,
                            remaining = fingerprintTimerRemaining,
                            timeText = fingerprintTimerTimeText,
                            running = fingerprintTimerRunning,
                            onToggle = onFingerprintToggle,
                            notches = fingerprintNotches,
                            colors = notchedProgressBarUiColors
                        ) { modifier ->
                            HuntCooldownDurationIcon(
                                modifier = modifier,
                                colors = IconVectorColors.defaults().copy(
                                    fillColor = LocalPalette.current.onSurface
                                )
                            )
                        }
                    }
                }
            }

            OperationToolbarUiState.Category.TOOL_FOOTSTEP -> {
                BpmTool(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .height(IntrinsicSize.Max),
                    realtimeState = bpmRealtimeState,
                    measurementType = bpmMeasurementType,
                    applyMeasurement = bpmApplyMeasurement,
                    ghostSpeed = bpmGhostSpeed,
                    weather = weather,
                    fuseBox = fuseBoxFlag,
                    ghostSpeedModifier = bpmGhostSpeedModifier,
                    fuseBoxFlag = bpmFuseBoxFlag,
                    domainMillis = bpmDomainMillis,
                    domainSampleIntervalMillis = bpmDomainSampleIntervalMillis,
                    onUpdate = onBpmUpdate,
                    onChangeMeasurementType = onBpmChangeMeasurementType,
                    toggleApplyMeasurement = onBpmToggleApplyMeasurement,
                    onChangeDomain = onBpmChangeDomain,
                    onChangeDomainSampleInterval = onBpmChangeSampleInterval
                )
            }

        }
    }
}

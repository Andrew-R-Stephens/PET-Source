package com.tritiumgaming.feature.investigation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.mapper.toStringResource
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalTypography
import com.tritiumgaming.core.ui.theme.LocalThemeProvider
import com.tritiumgaming.core.ui.widgets.collapsebutton.CollapseButton
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer.BpmPoint
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer.RealtimeUiState
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarUiColors
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarUiState
import com.tritiumgaming.feature.investigation.app.mappers.difficulty.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.map.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.weather.toDrawable
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ClearPopup
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.PlayerDeath
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ResetInvestigation
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.SetBpmData
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.SetBpmDomain
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.SetBpmMeasurementType
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.SetBpmSampleInterval
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.SetCustomDifficulty
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
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ToggleCursedOverride
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ToggleFuseBoxOverride
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ToggleGhostNegation
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ToggleOperationTimer
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ToggleTimerLinking
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ToggleToolbar
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ToggleTrait
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ToggleUniqueTraitFilter
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.TriggerToolTimer
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.UseSanityMedication
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.OperationConfigUiColors
import com.tritiumgaming.feature.investigation.ui.common.sanitymeter.PlayerSanityUiState
import com.tritiumgaming.feature.investigation.ui.journal.JournalComponent
import com.tritiumgaming.feature.investigation.ui.popups.JournalPopupUiState
import com.tritiumgaming.feature.investigation.ui.popups.common.InvestigationPopup
import com.tritiumgaming.feature.investigation.ui.popups.evidence.EvidencePopup
import com.tritiumgaming.feature.investigation.ui.popups.ghost.GhostPopup
import com.tritiumgaming.feature.investigation.ui.sheet.ToolsBottomSheetComponent
import com.tritiumgaming.feature.investigation.ui.sheet.ToolsSideSheetComponent
import com.tritiumgaming.feature.investigation.ui.sheet.ToolsTimerComponent
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetails
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetailsUiState
import com.tritiumgaming.feature.investigation.ui.tool.configs.CustomDifficultyConfigControl
import com.tritiumgaming.feature.investigation.ui.tool.configs.DifficultyConfigControl
import com.tritiumgaming.feature.investigation.ui.tool.configs.DifficultyConfigUiState
import com.tritiumgaming.feature.investigation.ui.tool.configs.FuseBoxButton
import com.tritiumgaming.feature.investigation.ui.tool.configs.MapConfigControl
import com.tritiumgaming.feature.investigation.ui.tool.configs.MapConfigUiState
import com.tritiumgaming.feature.investigation.ui.tool.configs.WeatherConfigComponent
import com.tritiumgaming.feature.investigation.ui.tool.configs.WeatherUiState
import com.tritiumgaming.feature.investigation.ui.tool.footstep.BpmTool
import com.tritiumgaming.feature.investigation.ui.tool.footstep.BpmToolUiState
import com.tritiumgaming.feature.investigation.ui.tool.footstep.visualizer.VisualizerMeasurementType
import com.tritiumgaming.feature.investigation.ui.tool.operationtimer.OperationTimerColumn
import com.tritiumgaming.feature.investigation.ui.tool.operationtimer.OperationTimerRow
import com.tritiumgaming.feature.investigation.ui.tool.operationtimer.OperationTimerUiState
import com.tritiumgaming.feature.investigation.ui.tool.phase.PhaseUiState
import com.tritiumgaming.feature.investigation.ui.tool.sanity.PlayerDeathButton
import com.tritiumgaming.feature.investigation.ui.tool.sanity.SanityMedicationButton
import com.tritiumgaming.feature.investigation.ui.tool.sanity.SanityMeterComponent
import com.tritiumgaming.feature.investigation.ui.tool.statusbar.OperationStatusBar
import com.tritiumgaming.feature.investigation.ui.tool.temperature.TemperatureComponent
import com.tritiumgaming.feature.investigation.ui.tool.temperature.TemperatureStateBundle
import com.tritiumgaming.feature.investigation.ui.tool.temperature.TemperatureUiState
import com.tritiumgaming.feature.investigation.ui.tool.traits.TraitConfig
import com.tritiumgaming.feature.investigation.ui.tool.traits.TraitListItemUiColors
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiActions
import com.tritiumgaming.feature.investigation.ui.toolbar.operation.OperationToolRail
import com.tritiumgaming.feature.investigation.ui.toolbar.operation.OperationToolbar
import com.tritiumgaming.feature.investigation.ui.toolbar.operation.OperationToolbarUiState
import com.tritiumgaming.shared.core.navigation.NavRoute
import com.tritiumgaming.shared.data.customdifficulty.CustomDifficultyResources
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyType
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Weather
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources
import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources
import com.tritiumgaming.shared.data.ghost.model.Ghost
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitCategory
import com.tritiumgaming.shared.data.operation.model.CategoryOption
import com.tritiumgaming.shared.data.operation.model.OperationOverrideData
import com.tritiumgaming.shared.data.operation.model.EvidenceState
import com.tritiumgaming.shared.data.operation.model.EvidenceValidationType
import com.tritiumgaming.shared.data.operation.model.GhostState
import com.tritiumgaming.shared.data.operation.model.GhostTraitFilterUiOptions
import com.tritiumgaming.shared.data.operation.model.ToolTimerType
import com.tritiumgaming.shared.data.operation.model.TraitFilter
import com.tritiumgaming.shared.data.operation.model.TraitScore
import com.tritiumgaming.shared.data.operation.model.ValidatedGhostTrait
import com.tritiumgaming.shared.data.journal.model.GhostEvidence
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources.MapTitle


@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@Preview(name = "Small Phone", device = "id:small_phone")
@Preview(name = "Small Phone Landscape", device = "spec:parent=small_phone,orientation=landscape")
@Preview(name = "Medium Phone Portrait", device = "spec:width=411dp,height=891dp")
@Preview(name = "Medium Phone Landscape", device = "spec:width=891dp,height=411dp")
@Preview(name = "Medium Tablet Portrait", device = "spec:width=1280dp,height=800dp,dpi=240,orientation=portrait")
@Preview(name = "Medium Tablet Landscape", device = "spec:width=1280dp,height=800dp,dpi=240")
@Preview(name = "Foldable", device = "spec:width=673dp,height=841dp")
private annotation class DevicePreviews

private class ToolbarCategoryProvider : PreviewParameterProvider<OperationToolbarUiState.Category> {
    override val values = sequenceOf(
        OperationToolbarUiState.Category.TOOL_CONFIG,
        //OperationToolbarUiState.Category.TOOL_TRAITS,
        //OperationToolbarUiState.Category.TOOL_ANALYZER,
        //OperationToolbarUiState.Category.TOOL_FOOTSTEP,
        //OperationToolbarUiState.Category.TOOL_TIMERS
    )
}

@DevicePreviews
@Composable
private fun InvestigationScreenPreview(
    @PreviewParameter(ToolbarCategoryProvider::class)
    category: OperationToolbarUiState.Category
) {
    LocalThemeProvider {
        Surface(
            color = LocalPalette.current.surface
        ) {
            InvestigationContent(
                uiState = InvestigationUiState(
                    operationTimer = OperationTimerUiState(
                        remainingTime = "0:00"
                    ),
                    difficultyOverrides = OperationOverrideData(
                        fuseBox = OperationOverrideData.Companion.FuseBoxFlag.FUSEBOX_ENABLED
                    ),
                    weather = WeatherUiState(
                        weather = Weather.SNOW
                    ),
                    ghostsSorted = GhostResources.GhostIdentifier.entries.take(10).map { identifier ->
                        GhostState(
                            ghostEvidence = GhostEvidence(
                                ghost = Ghost(
                                    id = identifier,
                                    name = GhostResources.GhostTitle.valueOf(identifier.name),
                                    icon = GhostResources.GhostIcon.valueOf(identifier.name),
                                    info = GhostResources.GhostDescription.valueOf(identifier.name),
                                    strengthData = GhostResources.GhostStrength.valueOf(identifier.name),
                                    weaknessData = GhostResources.GhostWeakness.valueOf(identifier.name),
                                    huntData = GhostResources.GhostHuntInfo.valueOf(identifier.name),
                                    normalEvidence = listOf(
                                        EvidenceResources.EvidenceIdentifier.GHOST_WRITING,
                                        EvidenceResources.EvidenceIdentifier.FREEZING_TEMPERATURE,
                                        EvidenceResources.EvidenceIdentifier.DOTS,
                                    ),
                                    strictEvidence = listOf(
                                        EvidenceResources.EvidenceIdentifier.EMF_5
                                    ),
                                    speed = GhostResources.GhostSpeed.valueOf(identifier.name),
                                    huntSanityBounds = GhostResources.HuntSanityBounds.valueOf(identifier.name),
                                    huntCooldown = GhostResources.HuntCooldown.valueOf(identifier.name)
                                ),
                                normalEvidenceList = listOf(
                                    EvidenceType(
                                            id = EvidenceResources.EvidenceIdentifier.GHOST_WRITING,
                                            name = EvidenceResources.EvidenceTitle.GHOST_WRITING,
                                            icon = EvidenceResources.EvidenceIcon.GHOST_WRITING

                                    ),
                                    EvidenceType(
                                            id = EvidenceResources.EvidenceIdentifier.FREEZING_TEMPERATURE,
                                            name = EvidenceResources.EvidenceTitle.FREEZING_TEMPERATURE,
                                            icon = EvidenceResources.EvidenceIcon.FREEZING_TEMPERATURE

                                    ),
                                    EvidenceType(
                                            id = EvidenceResources.EvidenceIdentifier.DOTS,
                                            name = EvidenceResources.EvidenceTitle.DOTS,
                                            icon = EvidenceResources.EvidenceIcon.DOTS
                                    ),
                                ),
                                strictEvidenceList = listOf(
                                    EvidenceType(
                                        id = EvidenceResources.EvidenceIdentifier.DOTS,
                                        name = EvidenceResources.EvidenceTitle.DOTS,
                                        icon = EvidenceResources.EvidenceIcon.DOTS

                                    ),
                                ),
                            ),
                            score = 0,
                            manualRejection = false,
                            bpmIsValid = false,
                            traitScore = TraitScore(),
                            traits = emptySet()
                        )
                    },
                    evidenceList = listOf(
                        EvidenceState(
                            evidence = EvidenceType(
                                id = EvidenceResources.EvidenceIdentifier.GHOST_WRITING,
                                name = EvidenceResources.EvidenceTitle.GHOST_WRITING,
                                icon = EvidenceResources.EvidenceIcon.GHOST_WRITING
                            )
                        ),
                        EvidenceState(
                            evidence = EvidenceType(
                                id = EvidenceResources.EvidenceIdentifier.FREEZING_TEMPERATURE,
                                name = EvidenceResources.EvidenceTitle.FREEZING_TEMPERATURE,
                                icon = EvidenceResources.EvidenceIcon.FREEZING_TEMPERATURE
                            )
                        ),
                        EvidenceState(
                            evidence = EvidenceType(
                                id = EvidenceResources.EvidenceIdentifier.DOTS,
                                name = EvidenceResources.EvidenceTitle.DOTS,
                                icon = EvidenceResources.EvidenceIcon.DOTS
                            )
                        ),

                    ),
                    traitFilterOptions = GhostTraitFilterUiOptions(
                        category = listOf(
                            CategoryOption(
                                data = TraitCategory.ALL
                            ),
                            CategoryOption(
                                data = TraitCategory.BEHAVIOR
                            ),
                            CategoryOption(
                                data = TraitCategory.CHARACTERISTIC
                            ),
                            CategoryOption(
                                data = TraitCategory.INTERACTION
                            )
                        )
                    ),
                    toolbar = OperationToolbarUiState(category = category),
                    mapConfig = MapConfigUiState(
                        allMaps = listOf(
                            MapTitle.BLEASDALE_FARMHOUSE,
                            MapTitle.CAMP_WOODWIND
                        )
                    )
                ),
                uiActions = InvestigationUiActions()
            )
        }
    }
}

@Composable
fun InvestigationSoloScreen(
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
    val difficultyOverrideUiState by investigationViewModel.operationOverridesState.collectAsStateWithLifecycle()
    val sanityUiState by investigationViewModel.playerSanityUiState.collectAsStateWithLifecycle()
    val evidenceListUiStates by investigationViewModel.evidenceListUiState.collectAsStateWithLifecycle()
    val traitFilterOptions by investigationViewModel.traitFilterOptionsUiState.collectAsStateWithLifecycle()
    val traitListUiStates by investigationViewModel.traitListUiState.collectAsStateWithLifecycle()
    val ghostOrder by investigationViewModel.ghostsSortedUiState.collectAsStateWithLifecycle()
    val evidenceStates by investigationViewModel.evidenceStates.collectAsStateWithLifecycle()
    val timersLinkedUiState by investigationViewModel.timersLinked.collectAsStateWithLifecycle()
    val smudgeHuntProtectionTimerState by investigationViewModel.smudgeHuntProtectionTimerUiState.collectAsStateWithLifecycle()
    val huntDurationTimerState by investigationViewModel.huntDurationTimerUiState.collectAsStateWithLifecycle()
    val huntGapTimerState by investigationViewModel.huntCooldownTimerUiState.collectAsStateWithLifecycle()
    val fingerprintTimerState by investigationViewModel.fingerprintTimerUiState.collectAsStateWithLifecycle()
    val bpmToolUiState by investigationViewModel.bpmToolUiState.collectAsStateWithLifecycle()

    val uiState = InvestigationUiState(
        popup = popupUiState,
        toolbar = toolbarUiState,
        operationTimer = operationTimerUiState,
        phase = phaseUiState,
        mapConfig = mapConfigUiState,
        operationDetails = operationDetailsUiState,
        difficultyConfig = difficultyUiState,
        customDifficultyConfig = customDifficultyConfigUiState,
        weather = weatherUiState,
        temperature = temperatureUiState,
        difficultyOverrides = difficultyOverrideUiState,
        playerSanity = sanityUiState,
        evidenceList = evidenceListUiStates,
        traitFilterOptions = traitFilterOptions,
        traitList = traitListUiStates,
        ghostsSorted = ghostOrder,
        ghostEvidenceStates = evidenceStates,
        timersLinked = timersLinkedUiState,
        smudgeHuntProtectionTimer = smudgeHuntProtectionTimerState,
        huntDurationTimer = huntDurationTimerState,
        huntCooldownTimer = huntGapTimerState,
        fingerprintTimer = fingerprintTimerState,
        bpmTool = bpmToolUiState
    )

    val uiActions = InvestigationUiActions(
        onWeatherDropdownSelect = { investigationViewModel.onEvent(SetWeather(Weather.entries[it])) },
        onMapDropdownSelect = { investigationViewModel.onEvent(SetMap(it)) },
        onDifficultyDropdownSelect = { investigationViewModel.onEvent(SetDifficulty(it)) },
        onCustomDifficultyDropdownSelect = { investigationViewModel.onEvent(SetCustomDifficulty(it)) },
        onNavigateToEditCustomDifficulty = {
            navController.navigate(
                route = NavRoute.SCREEN_CUSTOM_DIFFICULTY_EDIT.route,
                navOptions = NavOptions.Builder()
                    .setPopUpTo(NavRoute.SCREEN_INVESTIGATION.route, inclusive = false)
                    .setLaunchSingleTop(true)
                    .setEnterAnim(0).setExitAnim(0).setPopEnterAnim(0).setPopExitAnim(0)
                    .build()
            )
        },
        onSanityChange = { investigationViewModel.onEvent(SetPlayerSanity(it)) },
        onUseSanityMedication = { investigationViewModel.onEvent(UseSanityMedication) },
        onPlayerDeath = { investigationViewModel.onEvent(PlayerDeath) },
        onTimerToggle = { investigationViewModel.onEvent(ToggleOperationTimer) },
        onTimerSkip = { investigationViewModel.onEvent(SkipOperationTimer) },
        onTogglePower = { investigationViewModel.onEvent(ToggleFuseBoxOverride) },
        onSelectTraitCategory = { category ->
            investigationViewModel.onEvent(SetTraitFilter(
                TraitFilter(category = category)
            ))
        },
        onToggleTrait = { trait ->
            investigationViewModel.onEvent(ToggleTrait(trait))
        },
        onToggleUniqueOnly = { investigationViewModel.onEvent(ToggleUniqueTraitFilter) },
        onSmudgeToggle = { investigationViewModel.onEvent(TriggerToolTimer(ToolTimerType.SMUDGE_TIMER)) },
        onHuntDurationToggle = { investigationViewModel.onEvent(TriggerToolTimer(ToolTimerType.HUNT_DURATION)) },
        onHuntCooldownToggle = { investigationViewModel.onEvent(TriggerToolTimer(ToolTimerType.HUNT_COOLDOWN)) },
        onFingerprintToggle = { investigationViewModel.onEvent(TriggerToolTimer(ToolTimerType.UV_EVIDENCE_DURATION)) },
        onLinkToggle = { investigationViewModel.onEvent(ToggleTimerLinking) },
        onToggleCursed = { investigationViewModel.onEvent(ToggleCursedOverride) },
        onBpmUpdate = { investigationViewModel.onEvent(SetBpmData(it)) },
        onBpmChangeMeasurementType = { investigationViewModel.onEvent(SetBpmMeasurementType(it)) },
        onBpmToggleApplyMeasurement = { investigationViewModel.onEvent(ToggleApplyBpmMeasurement) },
        onBpmChangeDomain = { investigationViewModel.onEvent(SetBpmDomain(it)) },
        onBpmChangeSampleInterval = { investigationViewModel.onEvent(SetBpmSampleInterval(it)) },
        onToggleCollapseToolbar = { investigationViewModel.onEvent(ToggleToolbar) },
        onChangeToolbarCategory = { category, allowCollapse ->
            investigationViewModel.onEvent(SetToolbarCategory(category, allowCollapse))
        },
        onReset = { investigationViewModel.onEvent(ResetInvestigation) },
        onGhostNameClick = { investigationViewModel.onEvent(ShowGhostPopup(it)) },
        onToggleNegateGhost = { investigationViewModel.onEvent(ToggleGhostNegation(it)) },
        onChangeEvidenceRuling = { e, r -> investigationViewModel.onEvent(SetEvidence(e, r)) },
        onEvidenceClick = { investigationViewModel.onEvent(ShowEvidencePopup(it)) },
        onClearPopup = { investigationViewModel.onEvent(ClearPopup) }
    )

    InvestigationContent(
        uiState = uiState,
        uiActions = uiActions
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InvestigationContent(
    uiState: InvestigationUiState,
    uiActions: InvestigationUiActions
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    val popupUiState = uiState.popup

    val toolbarUiState = uiState.toolbar

    val operationTimerUiState = uiState.operationTimer
    val phaseUiState = uiState.phase
    val mapConfigUiState = uiState.mapConfig
    val operationDetailsUiState = uiState.operationDetails
    val difficultyUiState = uiState.difficultyConfig
    val customDifficultyConfigUiState = uiState.customDifficultyConfig
    val weatherUiState = uiState.weather
    val temperatureUiState = uiState.temperature
    val difficultyOverrideUiState = uiState.difficultyOverrides
    val sanityUiState = uiState.playerSanity
    val evidenceListUiStates = uiState.evidenceList

    val traitFilterOptions = uiState.traitFilterOptions
    val traitListUiStates = uiState.traitList
    val ghostOrder = uiState.ghostsSorted
    val evidenceStates = uiState.ghostEvidenceStates

    val timersLinked = uiState.timersLinked
    val smudgeHuntProtectionTimerState = uiState.smudgeHuntProtectionTimer
    val huntDurationTimerState = uiState.huntDurationTimer
    val huntGapTimerState = uiState.huntCooldownTimer
    val fingerprintTimerState = uiState.fingerprintTimer

    val bpmToolUiState = uiState.bpmTool

    val onWeatherDropdownSelect = uiActions.onWeatherDropdownSelect
    val onMapDropdownSelect = uiActions.onMapDropdownSelect
    val onDifficultyDropdownSelect = uiActions.onDifficultyDropdownSelect
    val onCustomDifficultyDropdownSelect = uiActions.onCustomDifficultyDropdownSelect
    val onNavigateToEditCustomDifficulty = uiActions.onNavigateToEditCustomDifficulty
    val onSanityChange = uiActions.onSanityChange
    val onUseSanityMedication = uiActions.onUseSanityMedication
    val onPlayerDeath = uiActions.onPlayerDeath
    val onTimerToggle = uiActions.onTimerToggle
    val onTimerSkip = uiActions.onTimerSkip
    val onTogglePower = uiActions.onTogglePower
    val onSelectTraitCategory = uiActions.onSelectTraitCategory
    val onToggleTrait = uiActions.onToggleTrait
    val onToggleUniqueOnly = uiActions.onToggleUniqueOnly
    val onSmudgeToggle = uiActions.onSmudgeToggle
    val onHuntDurationToggle = uiActions.onHuntDurationToggle
    val onHuntCooldownToggle = uiActions.onHuntCooldownToggle
    val onFingerprintToggle = uiActions.onFingerprintToggle
    val onBpmUpdate = uiActions.onBpmUpdate
    val onBpmChangeMeasurementType = uiActions.onBpmChangeMeasurementType
    val onBpmToggleApplyMeasurement = uiActions.onBpmToggleApplyMeasurement
    val onBpmChangeDomain = uiActions.onBpmChangeDomain
    val onBpmChangeSampleInterval = uiActions.onBpmChangeSampleInterval
    val onToggleCollapseToolbar = uiActions.onToggleCollapseToolbar
    val onChangeToolbarCategory = uiActions.onChangeToolbarCategory
    val onReset = uiActions.onReset
    val onGhostNameClick = uiActions.onGhostNameClick
    val onToggleNegateGhost = uiActions.onToggleNegateGhost
    val onChangeEvidenceRuling = uiActions.onChangeEvidenceRuling
    val onEvidenceClick = uiActions.onEvidenceClick
    val onClearPopup = uiActions.onClearPopup

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

    val smudgeHuntPreventionTitle = stringResource(R.string.tool_timer_label_smudge_protection)
    val smudgeHuntPreventionMax = smudgeHuntProtectionTimerState.max
    val smudgeHuntPreventionRemaining = smudgeHuntProtectionTimerState.remaining
    val smudgeHuntPreventionTimeText = smudgeHuntProtectionTimerState.timeText
    val smudgeHuntPreventionRunning = smudgeHuntProtectionTimerState.running
    val smudgeNotches = smudgeHuntProtectionTimerState.notches

    val huntDurationTitle = stringResource(R.string.tool_timer_label_hunt_duration)
    val huntDurationMax = huntDurationTimerState.max
    val huntDurationRemaining = huntDurationTimerState.remaining
    val huntDurationTimeText = huntDurationTimerState.timeText
    val huntDurationRunning = huntDurationTimerState.running
    val huntDurationNotches = huntDurationTimerState.notches

    val huntCooldownTitle = stringResource(R.string.tool_timer_label_hunt_cooldown)
    val huntCooldownMax = huntGapTimerState.max
    val huntCooldownRemaining = huntGapTimerState.remaining
    val huntCooldownTimeText = huntGapTimerState.timeText
    val huntCooldownRunning = huntGapTimerState.running
    val huntCooldownNotches = huntGapTimerState.notches

    val fingerprintTimerTitle = stringResource(R.string.tool_timer_label_uv_lifetime)
    val fingerprintTimerMax = fingerprintTimerState.max
    val fingerprintTimerRemaining = fingerprintTimerState.remaining
    val fingerprintTimerTimeText = fingerprintTimerState.timeText
    val fingerprintTimerRunning = fingerprintTimerState.running
    val fingerprintNotches = fingerprintTimerState.notches

    val bpmMeasurementType = bpmToolUiState.measurementType
    val bpmApplyMeasurement = bpmToolUiState.applyMeasurement

    val notchedProgressBarUiColors = NotchedProgressBarUiColors(
        remaining = LocalPalette.current.primary,
        background = LocalPalette.current.surface,
        border = LocalPalette.current.onSurface,
        notch = LocalPalette.current.onSurface,
        label = LocalPalette.current.onSurface,
    )

    val operationConfigUiColors = OperationConfigUiColors(
        color = LocalPalette.current.surfaceContainerHigh,
        onColor = LocalPalette.current.onSurface
    )

    val traitsComponent: @Composable (Modifier) -> Unit = { modifier ->
        TraitConfig(
            modifier = modifier,
            uniqueOnly = traitFilterOptions.uniqueOnly ?: false,
            categories = traitFilterOptions.category,
            list = traitListUiStates,
            onSelectCategory = onSelectTraitCategory,
            onSelectTrait = onToggleTrait,
            onToggleUniqueOnly = onToggleUniqueOnly,
            colors = TraitListItemUiColors(
                unselectedColor = LocalPalette.current.surfaceContainerHigh,
                unselectedOnColor = LocalPalette.current.onSurface,
                selectedColor = LocalPalette.current.surfaceContainerLow,
                selectedOnColor = LocalPalette.current.onSurfaceVariant,
            )
        )
    }

    val analyzerComponent: @Composable (Modifier) -> Unit = { modifier ->
        OperationDetails(
            modifier = modifier,
            operationDetailsUiState = operationDetailsUiState
        )
    }

    val footstepComponent: @Composable (Modifier) -> Unit = { modifier ->
        BpmTool(
            modifier = modifier,
            realtimeState = bpmToolUiState.realtimeState,
            measurementType = bpmMeasurementType,
            applyMeasurement = bpmApplyMeasurement,
            ghostSpeedModifier = bpmToolUiState.ghostSpeedModifier,
            fuseBoxFlag = bpmToolUiState.fuseBoxFlag,
            domainMillis = bpmToolUiState.domainMillis,
            domainSampleIntervalMillis = bpmToolUiState.domainSampleIntervalMillis,
            weather = weather,
            range = bpmToolUiState.range,
            domainOptions = bpmToolUiState.domainOptions,
            sampleIntervalOptions = bpmToolUiState.sampleIntervalOptions,
            onUpdate = onBpmUpdate,
            onChangeMeasurementType = onBpmChangeMeasurementType,
            toggleApplyMeasurement = onBpmToggleApplyMeasurement,
            onChangeDomain = onBpmChangeDomain,
            onChangeDomainSampleInterval = onBpmChangeSampleInterval
        )
    }

    val timersComponent: @Composable (Modifier) -> Unit = { modifier ->
        ToolsTimerComponent(
            modifier = modifier,
            smudgeHuntPreventionTitle = smudgeHuntPreventionTitle,
            smudgeHuntPreventionMax = smudgeHuntPreventionMax,
            smudgeHuntPreventionRemaining = smudgeHuntPreventionRemaining,
            smudgeHuntPreventionTimeText = smudgeHuntPreventionTimeText,
            smudgeHuntPreventionRunning = smudgeHuntPreventionRunning,
            onSmudgeToggle = onSmudgeToggle,
            smudgeNotches = smudgeNotches,
            huntDurationTitle = huntDurationTitle,
            huntDurationMax = huntDurationMax,
            huntDurationRemaining = huntDurationRemaining,
            huntDurationTimeText = huntDurationTimeText,
            huntDurationRunning = huntDurationRunning,
            onHuntDurationToggle = onHuntDurationToggle,
            huntDurationNotches = huntDurationNotches,
            huntCooldownTitle = huntCooldownTitle,
            huntCooldownMax = huntCooldownMax,
            huntCooldownRemaining = huntCooldownRemaining,
            huntCooldownTimeText = huntCooldownTimeText,
            huntCooldownRunning = huntCooldownRunning,
            onHuntCooldownToggle = onHuntCooldownToggle,
            huntCooldownNotches = huntCooldownNotches,
            fingerprintTimerTitle = fingerprintTimerTitle,
            fingerprintTimerMax = fingerprintTimerMax,
            fingerprintTimerRemaining = fingerprintTimerRemaining,
            fingerprintTimerTimeText = fingerprintTimerTimeText,
            fingerprintTimerRunning = fingerprintTimerRunning,
            onFingerprintToggle = uiActions.onFingerprintToggle,
            fingerprintNotches = fingerprintNotches,
            timersLinked = uiState.timersLinked,
            onLinkToggle = uiActions.onLinkToggle,
            onToggleCursed = uiActions.onToggleCursed,
            isCursedInvestigation = difficultyOverrideUiState.cursedInvestigation,
            difficultyTitle = operationDetailsUiState.difficultyDetails.difficultyTitle,
            mapSize = operationDetailsUiState.mapDetails.size,
            huntDuration = operationDetailsUiState.difficultyDetails.settings.huntDuration,
            fingerprintDuration = operationDetailsUiState.difficultyDetails.settings.fingerprintDuration,
            notchedProgressBarUiColors = notchedProgressBarUiColors
        )
    }

    val configBottomSheet: @Composable (Modifier) -> Unit = { modifier ->
        OperationConfigsBottomSheet(
            modifier = modifier,
            mapConfigComponent = { modifier ->
                MapConfigControl(
                    modifier = modifier,
                    dropdownOptions = mapDropdownOptions,
                    isDropdownEnabled = isMapEnabled,
                    dropdownLabel = mapLabel,
                    colors = operationConfigUiColors,
                    onDropdownSelect = onMapDropdownSelect
                )
            },
            difficultyConfigComponent = { modifier ->
                DifficultyConfigControl(
                    modifier = modifier,
                    dropdownOptions = difficultyDropdownOptions,
                    isDropdownEnabled = isDifficultyEnabled,
                    dropdownLabel = difficultyLabel,
                    colors = operationConfigUiColors,
                    onDropdownSelect = onDifficultyDropdownSelect
                )
            },
            customDifficultyConfigComponent = { modifier ->
                CustomDifficultyConfigControl(
                    modifier = modifier,
                    dropdownOptions = customDifficultyDropdownOptions,
                    isDropdownEnabled = isDifficultyEnabled,
                    dropdownLabel = customDifficultyLabel,
                    colors = operationConfigUiColors,
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
                    isDropdownEnabled = isWeatherEnabled,
                    dropdownLabel = weatherLabel,
                    onDropdownSelect = onWeatherDropdownSelect
                )
            },
            sanityMeterComponent = { modifier, onHeadClick ->
                SanityMeterComponent(
                    modifier = modifier,
                    sanityLevel = sanityLevel,
                    insanityLevel = insanityLevel,
                    onSanityChange = onSanityChange,
                    onHeadClick = onHeadClick
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
            showEditCustomDifficultyComponent = difficultyUiState.type == DifficultyType.CUSTOM
        )
    }

    val configSideSheetCompact: @Composable (Modifier) -> Unit = { modifier ->
        OperationConfigsSideSheetCompact(
            modifier = modifier,
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
                    isDropdownEnabled = isMapEnabled,
                    dropdownLabel = mapLabel,
                    colors = operationConfigUiColors,
                    onDropdownSelect = onMapDropdownSelect
                )
            },
            difficultyConfigComponent = { modifier ->
                DifficultyConfigControl(
                    modifier = modifier,
                    dropdownOptions = difficultyDropdownOptions,
                    isDropdownEnabled = isDifficultyEnabled,
                    dropdownLabel = difficultyLabel,
                    colors = operationConfigUiColors,
                    onDropdownSelect = onDifficultyDropdownSelect
                )
            },
            customDifficultyConfigComponent = { modifier ->
                CustomDifficultyConfigControl(
                    modifier = modifier,
                    dropdownOptions = customDifficultyDropdownOptions,
                    isDropdownEnabled = isDifficultyEnabled,
                    dropdownLabel = customDifficultyLabel,
                    colors = operationConfigUiColors,
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
                    isDropdownEnabled = isWeatherEnabled,
                    dropdownLabel = weatherLabel,
                    onDropdownSelect = onWeatherDropdownSelect
                )
            },
            sanityMeterComponent = { modifier, onHeadClick ->
                SanityMeterComponent(
                    modifier = modifier,
                    sanityLevel = sanityLevel,
                    insanityLevel = insanityLevel,
                    onSanityChange = onSanityChange,
                    onHeadClick = onHeadClick
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
            showEditCustomDifficultyComponent = difficultyUiState.type == DifficultyType.CUSTOM
        )
    }

    val configSideSheetExpanded: @Composable (Modifier) -> Unit = { modifier ->
        OperationConfigsSideSheetExpanded(
            modifier = modifier,
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
                    isDropdownEnabled = isMapEnabled,
                    dropdownLabel = mapLabel,
                    colors = operationConfigUiColors,
                    onDropdownSelect = onMapDropdownSelect
                )
            },
            difficultyConfigComponent = { modifier ->
                DifficultyConfigControl(
                    modifier = modifier,
                    dropdownOptions = difficultyDropdownOptions,
                    isDropdownEnabled = isDifficultyEnabled,
                    dropdownLabel = difficultyLabel,
                    colors = operationConfigUiColors,
                    onDropdownSelect = onDifficultyDropdownSelect
                )
            },
            customDifficultyConfigComponent = { modifier ->
                CustomDifficultyConfigControl(
                    modifier = modifier,
                    dropdownOptions = customDifficultyDropdownOptions,
                    isDropdownEnabled = isDifficultyEnabled,
                    dropdownLabel = customDifficultyLabel,
                    colors = operationConfigUiColors,
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
                    isDropdownEnabled = isWeatherEnabled,
                    dropdownLabel = weatherLabel,
                    onDropdownSelect = onWeatherDropdownSelect
                )
            },
            sanityMeterComponent = { modifier, onHeadClick ->
                SanityMeterComponent(
                    modifier = modifier,
                    sanityLevel = sanityLevel,
                    insanityLevel = insanityLevel,
                    onSanityChange = onSanityChange,
                    onHeadClick = onHeadClick
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
            showEditCustomDifficultyComponent = difficultyUiState.type == DifficultyType.CUSTOM
        )
    }

    val bottomSheetComponent: @Composable (Modifier) -> Unit = { modifier ->
        ToolsBottomSheetComponent(
            modifier = modifier,
            toolbarCategory = toolbarCategory,
            configComponent = configBottomSheet,
            traitsComponent = traitsComponent,
            analyzerComponent = analyzerComponent,
            timersComponent = timersComponent,
            footstepComponent = footstepComponent
        )
    }

    val sideSheetComponent: @Composable (Modifier) -> Unit = { modifier ->
        when (deviceConfiguration) {
            DeviceConfiguration.MOBILE_LANDSCAPE -> {
                ToolsSideSheetComponent(
                    modifier = modifier,
                    toolbarCategory = toolbarCategory,
                    configComponent = configSideSheetCompact,
                    traitsComponent = traitsComponent,
                    analyzerComponent = analyzerComponent,
                    timersComponent = timersComponent,
                    footstepComponent = footstepComponent
                )
            }
            else -> {
                ToolsSideSheetComponent(
                    modifier = modifier,
                    toolbarCategory = toolbarCategory,
                    configComponent = configSideSheetExpanded,
                    traitsComponent = traitsComponent,
                    analyzerComponent = analyzerComponent,
                    timersComponent = timersComponent,
                    footstepComponent = footstepComponent
                )
            }
        }

    }

    val journalComponent: @Composable (Modifier) -> Unit = { modifier ->
        JournalComponent(
            modifier = modifier,
            evidenceStateList = evidenceListUiStates,
            ghostOrder = ghostOrder,
            ghostEvidenceState = evidenceStates,
            onGhostNameClick = onGhostNameClick,
            onToggleNegateGhost = onToggleNegateGhost,
            onRequestToolTip = { },
            onChangeEvidenceRuling = onChangeEvidenceRuling,
            onEvidenceClick = onEvidenceClick
        )
    }

    val statusBarComponent: @Composable (Modifier) -> Unit = { modifier ->
        OperationStatusBar(
            modifier = modifier,
            remainingTime = operationTimerUiState.remainingTime,
            phaseType = phaseUiState.type,
            sanityLevel = sanityUiState.sanityLevel,
            weatherType = weatherUiState.weather,
            temperature = temperatureUiState.currentAsString,
            fuseBoxFlag = fuseBoxFlag
        )
    }

    when(deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT,
        DeviceConfiguration.TABLET_PORTRAIT -> {
            CompactPortraitContent(
                modifier = Modifier,
                toolbarState = toolbarUiState,
                toolbarActions = ToolbarUiActions(
                    onToggleCollapseToolbar = onToggleCollapseToolbar,
                    onChangeToolbarCategory = { category ->
                        onChangeToolbarCategory(category, true)
                    },
                    onReset = onReset
                ),
                statusBarComponent = { modifier -> statusBarComponent(modifier) },
                journalComponent = { modifier -> journalComponent(modifier) },
                bottomSheetComponent = bottomSheetComponent
            )
        }

        DeviceConfiguration.MOBILE_LANDSCAPE -> {
            CompactLandscapeContent(
                modifier = Modifier,
                operationToolbarUiState = toolbarUiState,
                toolbarUiActions = ToolbarUiActions(
                    onToggleCollapseToolbar = onToggleCollapseToolbar,
                    onChangeToolbarCategory = { category ->
                        onChangeToolbarCategory(category, true)
                    },
                    onReset = onReset
                ),
                statusBarComponent = { modifier -> statusBarComponent(modifier) },
                journalComponent = { modifier -> journalComponent(modifier) },
                sideSheetComponent = { modifier -> sideSheetComponent(modifier) }
            )
        }

        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP -> {
            ExpandedLandscapeContent(
                modifier = Modifier,
                operationToolbarUiState = toolbarUiState,
                toolbarUiActions = ToolbarUiActions(
                    onToggleCollapseToolbar = onToggleCollapseToolbar,
                    onChangeToolbarCategory = { category ->
                        onChangeToolbarCategory(category, false)
                    },
                    onReset = onReset
                ),
                statusBarComponent = { modifier -> statusBarComponent(modifier) },
                journalComponent = { modifier -> journalComponent(modifier) },
                sideSheetComponent = { modifier -> sideSheetComponent(modifier) }
            )
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
            ) { onClearPopup() }
        }
        popupUiState.evidencePopupRecord?.let { record ->
            EvidencePopup(
                modifier = modifier,
                record = record
            ) { onClearPopup() }
        }
    }

}

@Composable
private fun CompactPortraitContent(
    modifier: Modifier = Modifier,
    toolbarState: OperationToolbarUiState,
    toolbarActions: ToolbarUiActions,
    statusBarComponent: @Composable (Modifier) -> Unit = {},
    bottomSheetComponent: @Composable (Modifier) -> Unit,
    journalComponent: @Composable (Modifier) -> Unit
) {

    val toolbarComponent: @Composable (Modifier) -> Unit = { modifier ->
        OperationToolbar(
            modifier = modifier
                .heightIn(min = 48.dp),
            category = toolbarState.category,
            onChangeToolbarCategory = toolbarActions.onChangeToolbarCategory,
            onReset = toolbarActions.onReset,
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
            selectBarComponent = { modifier ->
                toolbarComponent(modifier) },
            content = { modifier ->
                bottomSheetComponent(
                    modifier
                        .fillMaxWidth()
                        .animateContentSize()
                        .then(
                            if (!toolbarState.isCollapsed)
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
private fun CompactLandscapeContent(
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
                /*.padding(8.dp)*/,
            selectRailComponent = { modifier ->
                toolbarContent(modifier) },
            content = { modifier ->
                sideSheetComponent(
                    modifier
                        .fillMaxHeight()
                        .animateContentSize()
                        .then(
                            if (!operationToolbarUiState.isCollapsed)
                                Modifier
                                    .fillMaxWidth(.35f)
                                    .widthIn(max = 400.dp)
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

            journalComponent(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )
        }

    }
}

@Composable
private fun ExpandedLandscapeContent(
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
            modifier = Modifier,
            selectRailComponent = { modifier ->
                toolbarContent(modifier) },
            content = { modifier ->
                sideSheetComponent(
                    modifier
                        .fillMaxWidth(.35f)
                        .widthIn(max = 400.dp)
                )
            }
        )

        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.Top
        ) {

            statusBarComponent(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )

            journalComponent(
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
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
        modifier = modifier,
        color = LocalPalette.current.surfaceContainerLow,
        shape = RoundedCornerShape(
            topStart = 0.dp, topEnd = 16.dp, bottomStart = 0.dp, bottomEnd = 16.dp
        )
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            content(
                Modifier
            )

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
    sanityMeterComponent: @Composable (Modifier, onHeadClick: () -> Unit) -> Unit = { _, _ -> },
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
                shape = RoundedCornerShape(8.dp)
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
                            .widthIn(max = 400.dp)
                    )

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
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
                            .fillMaxWidth()                    )
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
                ) {
                    timerComponent(
                        Modifier
                            .width(IntrinsicSize.Min)
                            .padding(8.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .height(IntrinsicSize.Max)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
                ) {
                    if (showTemperatureMeterComponent) {

                        Surface(
                            modifier = Modifier
                                .width(IntrinsicSize.Min)
                                .fillMaxHeight(),
                            color = LocalPalette.current.surfaceContainer,
                            shape = RoundedCornerShape(8.dp),
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
                    .weight(1f)
                    .fillMaxHeight(),
                color = LocalPalette.current.surfaceContainer,
                shape = RoundedCornerShape(8.dp),
            ) {
                sanityMeterComponent(
                    Modifier
                        .height(48.dp)
                        .weight(1f)
                        .padding(8.dp),
                    {}
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
fun OperationConfigsSideSheetCompact(
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
    sanityMeterComponent: @Composable (Modifier, onHeadClick: () -> Unit) -> Unit = { _, _ -> },
    showTemperatureMeterComponent: Boolean,
    showEditCustomDifficultyComponent: Boolean
) {
    var isSanityCollapsed by rememberSaveable { mutableStateOf(false) }
    val toggleSanityExpansion = { isSanityCollapsed = !isSanityCollapsed }

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row (
            modifier = Modifier
                .fillMaxWidth(),
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

        Surface(
            modifier = Modifier,
            color = LocalPalette.current.surfaceContainer,
            shape = RoundedCornerShape(8.dp),
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

                weatherConfigComponent(
                    Modifier
                        .fillMaxWidth()
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Surface(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                color = LocalPalette.current.surfaceContainer,
                shape = RoundedCornerShape(8.dp),
            ) {
                timerComponent(
                    Modifier
                        .width(IntrinsicSize.Min)
                        .padding(8.dp)
                )
            }

            fuseBoxControlComponent(
                Modifier
                    .fillMaxHeight()
                    .width(48.dp)
                    .heightIn(min = 48.dp)
            )

        }

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .animateContentSize(),
            color = LocalPalette.current.surfaceContainer,
            shape = RoundedCornerShape(8.dp),
        ) {
            BoxWithConstraints(
                modifier = Modifier.fillMaxWidth()
            ) {
                val buttonsWidth = (48.dp * 2) + 8.dp
                val canFitBoth = maxWidth >= (224.dp + 8.dp + buttonsWidth)

                if (canFitBoth) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            8.dp,
                            Alignment.CenterHorizontally
                        )
                    ) {
                        sanityMeterComponent(
                            Modifier
                                .height(48.dp)
                                .weight(1f)
                                .widthIn(max = 224.dp),
                            toggleSanityExpansion
                        )

                        Surface(
                            modifier = Modifier,
                            color = LocalPalette.current.surfaceContainerLow,
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            sanityMedicationComponent(
                                Modifier.size(48.dp)
                            )
                        }

                        Surface(
                            modifier = Modifier,
                            color = LocalPalette.current.surfaceContainerLow,
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            playerDeathButtonComponent(
                                Modifier.size(48.dp)
                            )
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            CollapseButton(
                                modifier = Modifier.size(24.dp),
                                isCollapsed = isSanityCollapsed,
                                onClick = toggleSanityExpansion,
                                enabledRotationAddition = 90
                            )

                            sanityMeterComponent(
                                Modifier
                                    .height(48.dp)
                                    .weight(1f)
                                    .padding(8.dp),
                                toggleSanityExpansion
                            )
                        }

                        AnimatedVisibility(visible = isSanityCollapsed) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(
                                    8.dp,
                                    Alignment.CenterHorizontally
                                ),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Surface(
                                    modifier = Modifier,
                                    color = LocalPalette.current.surfaceContainerLow,
                                    shape = RoundedCornerShape(8.dp),
                                ) {
                                    sanityMedicationComponent(
                                        Modifier.size(48.dp)
                                    )
                                }

                                Surface(
                                    modifier = Modifier,
                                    color = LocalPalette.current.surfaceContainerLow,
                                    shape = RoundedCornerShape(8.dp),
                                ) {
                                    playerDeathButtonComponent(
                                        Modifier.size(48.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }

        if (showTemperatureMeterComponent) {
            Surface(
                modifier = Modifier.width(IntrinsicSize.Min),
                color = LocalPalette.current.surfaceContainer,
                shape = RoundedCornerShape(8.dp),
            ) {
                temperatureMeterComponent(
                    Modifier
                        .padding(8.dp)
                        .wrapContentWidth()
                )
            }
        }

        /*BoxWithConstraints(
            modifier = Modifier.fillMaxWidth()
        ) {
            val canFitBoth = maxWidth >= (140.dp * 2 + 8.dp)
            val shouldFillWidth = !showTemperatureMeterComponent || !canFitBoth

            FlowRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
                itemVerticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier
                        .then(
                            if (shouldFillWidth) Modifier.fillMaxWidth()
                            else Modifier.weight(1f).widthIn(max = 140.dp)
                        ),
                    color = LocalPalette.current.surfaceContainer,
                    shape = RoundedCornerShape(8.dp),
                ) {
                    weatherConfigComponent(
                        Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    )
                }

                if (showTemperatureMeterComponent) {
                    Surface(
                        modifier = Modifier
                            .then(
                                if (shouldFillWidth) Modifier.fillMaxWidth()
                                else Modifier.width(IntrinsicSize.Min)
                            ),
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        temperatureMeterComponent(
                            Modifier
                                .padding(8.dp)
                                .wrapContentWidth()
                        )
                    }
                }
            }
        }*/

    }

}

@Composable
fun OperationConfigsSideSheetExpanded(
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
    sanityMeterComponent: @Composable (Modifier, onHeadClick: () -> Unit) -> Unit = { _, _ -> },
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

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {

            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = LocalPalette.current.surfaceContainer,
                shape = RoundedCornerShape(8.dp),
            ) {
                Column(
                    modifier = Modifier.padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    mapConfigComponent(Modifier.fillMaxWidth())

                    difficultyConfigComponent(Modifier.fillMaxWidth())

                    if (showEditCustomDifficultyComponent) {
                        customDifficultyConfigComponent(Modifier.fillMaxWidth())
                    }

                }
            }

            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .height(IntrinsicSize.Max),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Surface(
                        modifier = Modifier.weight(1f),
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        weatherConfigComponent(
                            Modifier
                                .fillMaxSize()
                                .padding(8.dp)
                        )
                    }

                    if (showTemperatureMeterComponent) {
                        Surface(
                            modifier = Modifier.wrapContentWidth(),
                            color = LocalPalette.current.surfaceContainer,
                            shape = RoundedCornerShape(8.dp),
                        ) {
                            temperatureMeterComponent(
                                Modifier
                                    .padding(8.dp)
                            )
                        }
                    }
                }
            }

        }

        /*Surface(
            modifier = Modifier.fillMaxWidth(),
            color = LocalPalette.current.surfaceContainer,
            shape = RoundedCornerShape(12.dp),
            border = BorderStroke(
                width = 2.dp,
                color = LocalPalette.current.surfaceContainerLow
            )
        ) {*/
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth()
                        .height(IntrinsicSize.Max),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight(),
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        timerComponent(
                            Modifier.padding(8.dp)
                                .fillMaxWidth()
                        )
                    }

                    fuseBoxControlComponent(
                        Modifier
                            .fillMaxHeight()
                            .width(48.dp)
                            .heightIn(min = 48.dp)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Surface(
                        modifier = Modifier.weight(1f),
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        sanityMeterComponent(
                            Modifier
                                .height(48.dp), {})
                    }

                    Surface(
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        sanityMedicationComponent(Modifier.size(48.dp))
                    }

                    Surface(
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        playerDeathButtonComponent(Modifier.size(48.dp))
                    }
                }
            }
        //}
    }
}

internal data class InvestigationUiState(
    val popup: JournalPopupUiState = JournalPopupUiState(),
    val toolbar: OperationToolbarUiState = OperationToolbarUiState(),
    val operationTimer: OperationTimerUiState = OperationTimerUiState(),
    val phase: PhaseUiState = PhaseUiState(),
    val mapConfig: MapConfigUiState = MapConfigUiState(),
    val operationDetails: OperationDetailsUiState = OperationDetailsUiState(),
    val difficultyConfig: DifficultyConfigUiState = DifficultyConfigUiState(),
    val customDifficultyConfig: InvestigationScreenViewModel.CustomDifficultyConfigUiState = InvestigationScreenViewModel.CustomDifficultyConfigUiState(),
    val weather: WeatherUiState = WeatherUiState(),
    val temperature: TemperatureUiState = TemperatureUiState(),
    val difficultyOverrides: OperationOverrideData = OperationOverrideData(),
    val playerSanity: PlayerSanityUiState = PlayerSanityUiState(),
    val evidenceList: List<EvidenceState> = emptyList(),
    val traitFilterOptions: GhostTraitFilterUiOptions = GhostTraitFilterUiOptions(),
    val traitList: List<ValidatedGhostTrait> = emptyList(),
    val ghostsSorted: List<GhostState> = emptyList(),
    val ghostEvidenceStates: List<EvidenceState> = emptyList(),
    val timersLinked: Boolean = false,
    val smudgeHuntProtectionTimer: NotchedProgressBarUiState = NotchedProgressBarUiState(),
    val huntDurationTimer: NotchedProgressBarUiState = NotchedProgressBarUiState(),
    val huntCooldownTimer: NotchedProgressBarUiState = NotchedProgressBarUiState(),
    val fingerprintTimer: NotchedProgressBarUiState = NotchedProgressBarUiState(),
    val bpmTool: BpmToolUiState = BpmToolUiState(),
)

internal data class InvestigationUiActions(
    val onWeatherDropdownSelect: (Int) -> Unit = {},
    val onMapDropdownSelect: (Int) -> Unit = {},
    val onDifficultyDropdownSelect: (Int) -> Unit = {},
    val onCustomDifficultyDropdownSelect: (Int) -> Unit = {},
    val onNavigateToEditCustomDifficulty: () -> Unit = {},
    val onSanityChange: (Float) -> Unit = {},
    val onUseSanityMedication: () -> Unit = {},
    val onPlayerDeath: () -> Unit = {},
    val onTimerToggle: () -> Unit = {},
    val onTimerSkip: () -> Unit = {},
    val onTogglePower: () -> Unit = {},
    val onSelectTraitCategory: (TraitCategory) -> Unit = {},
    val onToggleTrait: (ValidatedGhostTrait) -> Unit = {},
    val onToggleUniqueOnly: () -> Unit = {},
    val onSmudgeToggle: () -> Unit = {},
    val onHuntDurationToggle: () -> Unit = {},
    val onHuntCooldownToggle: () -> Unit = {},
    val onFingerprintToggle: () -> Unit = {},
    val onLinkToggle: () -> Unit = {},
    val onToggleCursed: () -> Unit = {},
    val onBpmUpdate: (RealtimeUiState<BpmPoint>) -> Unit = {},
    val onBpmChangeMeasurementType: (VisualizerMeasurementType) -> Unit = {},
    val onBpmToggleApplyMeasurement: () -> Unit = {},
    val onBpmChangeDomain: (Long) -> Unit = {},
    val onBpmChangeSampleInterval: (Long) -> Unit = {},
    val onToggleCollapseToolbar: () -> Unit = {},
    val onChangeToolbarCategory: (OperationToolbarUiState.Category, Boolean) -> Unit = { _, _ -> },
    val onReset: () -> Unit = {},
    val onGhostNameClick: (GhostResources.GhostIdentifier) -> Unit = {},
    val onToggleNegateGhost: (Ghost) -> Unit = {},
    val onChangeEvidenceRuling: (EvidenceType, EvidenceValidationType) -> Unit = { _, _ -> },
    val onEvidenceClick: (EvidenceType) -> Unit = {},
    val onClearPopup: () -> Unit = {}
)

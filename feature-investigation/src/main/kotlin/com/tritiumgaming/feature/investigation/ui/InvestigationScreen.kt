package com.tritiumgaming.feature.investigation.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.FrameRateCategory
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.preferredFrameRate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.mapper.toStringResource
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalThemeProvider
import com.tritiumgaming.core.ui.widgets.collapsebutton.CollapseButton
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer.BpmPoint
import com.tritiumgaming.core.ui.widgets.graph.realtime.ui.visualizer.RealtimeUiState
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarUiColors
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarUiState
import com.tritiumgaming.core.ui.widgets.walkthrough.WalkthroughHost
import com.tritiumgaming.core.ui.widgets.walkthrough.WalkthroughPage
import com.tritiumgaming.core.ui.widgets.walkthrough.WalkthroughState
import com.tritiumgaming.core.ui.widgets.walkthrough.WalkthroughStep
import com.tritiumgaming.core.ui.widgets.walkthrough.rememberWalkthroughState
import com.tritiumgaming.core.ui.widgets.walkthrough.walkthroughTarget
import com.tritiumgaming.feature.investigation.app.mappers.challenge.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.difficulty.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.map.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.weather.toDrawable
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.CustomDifficultyConfigUiState
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
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetails
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetailsUiState
import com.tritiumgaming.feature.investigation.ui.tool.configs.CustomDifficultyConfigControl
import com.tritiumgaming.feature.investigation.ui.tool.configs.DifficultyChallengeLabel
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
import com.tritiumgaming.feature.investigation.ui.tool.timers.TimerTools
import com.tritiumgaming.feature.investigation.ui.tool.traits.TraitConfig
import com.tritiumgaming.feature.investigation.ui.tool.traits.TraitListItemUiColors
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiActions
import com.tritiumgaming.feature.investigation.ui.toolbar.operation.OperationToolRail
import com.tritiumgaming.feature.investigation.ui.toolbar.operation.OperationToolbar
import com.tritiumgaming.feature.investigation.ui.toolbar.operation.OperationToolbarUiState
import com.tritiumgaming.shared.core.navigation.NavRoute
import com.tritiumgaming.shared.data.challenge.mapper.ChallengeResources.ChallengeTitle
import com.tritiumgaming.shared.data.customdifficulty.CustomDifficultyResources
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyTitle
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyType
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Weather
import com.tritiumgaming.shared.data.evidence.mapper.EvidenceResources
import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources
import com.tritiumgaming.shared.data.ghost.model.Ghost
import com.tritiumgaming.shared.data.ghosttrait.mapper.GhostTraitResources.TraitCategory
import com.tritiumgaming.shared.data.journal.model.GhostEvidence
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources.MapTitle
import com.tritiumgaming.shared.data.operation.model.CategoryOption
import com.tritiumgaming.shared.data.operation.model.EvidenceState
import com.tritiumgaming.shared.data.operation.model.EvidenceValidationType
import com.tritiumgaming.shared.data.operation.model.GhostState
import com.tritiumgaming.shared.data.operation.model.GhostTraitFilterUiOptions
import com.tritiumgaming.shared.data.operation.model.OperationOverrideData
import com.tritiumgaming.shared.data.operation.model.ToolTimerType
import com.tritiumgaming.shared.data.operation.model.TraitFilter
import com.tritiumgaming.shared.data.operation.model.TraitScore
import com.tritiumgaming.shared.data.operation.model.ValidatedGhostTrait


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
        //OperationToolbarUiState.Category.TOOL_ANALYZER,
        //OperationToolbarUiState.Category.TOOL_TRAITS,
        //OperationToolbarUiState.Category.TOOL_TIMERS,
        //OperationToolbarUiState.Category.TOOL_FOOTSTEP,
        //OperationToolbarUiState.Category.TOOL_NONE,
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
                        weather = Weather.HEAVY_RAIN
                    ),
                    ghostsSorted = GhostResources.GhostIdentifier.entries.take(10)
                        .map { identifier ->
                            GhostState(
                                ghostEvidence = GhostEvidence(
                                    ghost = Ghost(
                                        id = identifier,
                                        name = GhostResources.GhostTitle.valueOf(identifier.name),
                                        icon = GhostResources.GhostIcon.valueOf(identifier.name),
                                        info = GhostResources.GhostDescription.valueOf(identifier.name),
                                        strengthData = GhostResources.GhostStrength.valueOf(
                                            identifier.name
                                        ),
                                        weaknessData = GhostResources.GhostWeakness.valueOf(
                                            identifier.name
                                        ),
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
                                        huntSanityBounds = GhostResources.HuntSanityBounds.valueOf(
                                            identifier.name
                                        ),
                                        huntCooldown = GhostResources.HuntCooldown.valueOf(
                                            identifier.name
                                        )
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
                    ),
                    difficultyConfig = DifficultyConfigUiState(
                        type = DifficultyType.CHALLENGE,
                        name = DifficultyTitle.CHALLENGE,
                        challengeTitle = ChallengeTitle.TORTOISE_AND_THE_HARE_TORTOISE
                    )
                ),
                uiActions = InvestigationUiActions(),
                walkthroughState = rememberWalkthroughState(emptyList()),
                isSanityCollapsed = false,
                onToggleSanityCollapse = {}
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

    var isSanityCollapsed by rememberSaveable { mutableStateOf(false) }

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
        onReset = { option -> investigationViewModel.onEvent(ResetInvestigation(option)) },
        onGhostNameClick = { investigationViewModel.onEvent(ShowGhostPopup(it)) },
        onToggleNegateGhost = { investigationViewModel.onEvent(ToggleGhostNegation(it)) },
        onChangeEvidenceRuling = { e, r -> investigationViewModel.onEvent(SetEvidence(e, r)) },
        onEvidenceClick = { investigationViewModel.onEvent(ShowEvidencePopup(it)) },
        onClearPopup = { investigationViewModel.onEvent(ClearPopup) }
    )

    val walkthroughState = rememberWalkthroughState(
        onTargetRequired = { targetId ->
            if (targetId == "config_medication" || targetId == "config_death") {
                isSanityCollapsed = true
            }

            val targetCategory = when {
                targetId.startsWith("traits") -> OperationToolbarUiState.Category.TOOL_TRAITS
                targetId.startsWith("analyzer") -> OperationToolbarUiState.Category.TOOL_ANALYZER
                targetId.startsWith("timers") -> OperationToolbarUiState.Category.TOOL_TIMERS
                targetId.startsWith("footstep") -> OperationToolbarUiState.Category.TOOL_FOOTSTEP
                targetId.startsWith("config") || targetId == "sanity" ->
                    OperationToolbarUiState.Category.TOOL_CONFIG
                else -> null
            }

            targetCategory?.let { category ->
                if (toolbarUiState.category != category) {
                    uiActions.onChangeToolbarCategory(category, false)
                }
            }
        },
        steps = listOf(
            WalkthroughStep(
                id = "screen_overview",
                pages = listOf(
                    WalkthroughPage(
                        descriptionRes = R.string.walkthrough_desc_investigation_1,
                        targetIds = listOf("journal", "status_bar", "toolbar")
                    )
                ),
                titleRes = R.string.walkthrough_title_investigation,
                isMajor = true
            ),
            WalkthroughStep(
                id = "journal",
                pages = listOf(
                    WalkthroughPage(
                        descriptionRes = R.string.walkthrough_desc_journal_1,
                        targetIds = listOf("journal")
                    ),
                    WalkthroughPage(
                        descriptionRes = R.string.walkthrough_desc_journal_2,
                        targetIds = listOf("journal")
                    )
                ),
                titleRes = R.string.walkthrough_title_journal
            ),
            WalkthroughStep(
                id = "toolbar",
                pages = listOf(
                    WalkthroughPage(
                        descriptionRes = R.string.walkthrough_desc_toolbar_1,
                        targetIds = listOf("toolbar")
                    )
                ),
                titleRes = R.string.walkthrough_title_toolbar
            ),
            WalkthroughStep(
                id = "configs",
                pages = listOf(
                    WalkthroughPage(
                        descriptionRes = R.string.walkthrough_desc_configs_1,
                        targetIds = listOf("configs")
                    ),
                    WalkthroughPage(
                        subtitleRes = R.string.walkthrough_subtitle_configs_difficulty,
                        descriptionRes = R.string.walkthrough_desc_configs_difficulty,
                        targetIds = listOf("config_difficulty")
                    ),
                    WalkthroughPage(
                        subtitleRes = R.string.walkthrough_subtitle_configs_map,
                        descriptionRes = R.string.walkthrough_desc_configs_map,
                        targetIds = listOf("config_map")
                    ),
                    WalkthroughPage(
                        subtitleRes = R.string.walkthrough_subtitle_configs_weather,
                        descriptionRes = R.string.walkthrough_desc_configs_weather,
                        targetIds = listOf("config_weather")
                    ),
                    WalkthroughPage(
                        subtitleRes = R.string.walkthrough_subtitle_configs_temperature,
                        descriptionRes = R.string.walkthrough_desc_configs_temperature,
                        targetIds = listOf("config_temperature")
                    ),
                    WalkthroughPage(
                        subtitleRes = R.string.walkthrough_subtitle_configs_timer,
                        descriptionRes = R.string.walkthrough_desc_configs_timer,
                        targetIds = listOf("config_timer")
                    ),
                    WalkthroughPage(
                        subtitleRes = R.string.walkthrough_subtitle_configs_power,
                        descriptionRes = R.string.walkthrough_desc_configs_power,
                        targetIds = listOf("config_power")
                    ),
                    WalkthroughPage(
                        subtitleRes = R.string.walkthrough_subtitle_configs_sanity,
                        descriptionRes = R.string.walkthrough_desc_sanity_1,
                        targetIds = listOf("sanity")
                    ),
                    WalkthroughPage(
                        subtitleRes = R.string.walkthrough_subtitle_configs_death,
                        descriptionRes = R.string.walkthrough_desc_configs_death,
                        targetIds = listOf("config_death")
                    ),
                    WalkthroughPage(
                        subtitleRes = R.string.walkthrough_subtitle_configs_medication,
                        descriptionRes = R.string.walkthrough_desc_configs_medication,
                        targetIds = listOf("config_medication")
                    ),
                    WalkthroughPage(
                        subtitleRes = R.string.walkthrough_subtitle_configs_status,
                        descriptionRes = R.string.walkthrough_desc_status_1,
                        targetIds = listOf("status_bar")
                    )
                ),
                titleRes = R.string.investigation_label_contract
            ),
            WalkthroughStep(
                id = "traits",
                pages = listOf(
                    WalkthroughPage(
                        descriptionRes = R.string.walkthrough_desc_traits_1,
                        targetIds = listOf("traits")
                    ),
                    WalkthroughPage(
                        subtitleRes = R.string.walkthrough_subtitle_traits_unique,
                        descriptionRes = R.string.walkthrough_desc_traits_unique,
                        targetIds = listOf("traits_unique")
                    ),
                    WalkthroughPage(
                        subtitleRes = R.string.walkthrough_subtitle_traits_filter,
                        descriptionRes = R.string.walkthrough_desc_traits_filter,
                        targetIds = listOf("traits_filter")
                    )
                ),
                titleRes = R.string.walkthrough_title_traits
            ),
            WalkthroughStep(
                id = "analyzer",
                pages = listOf(
                    WalkthroughPage(
                        descriptionRes = R.string.walkthrough_desc_analyzer_1,
                        targetIds = listOf("analyzer")
                    )
                ),
                titleRes = R.string.walkthrough_title_analyzer
            ),
            WalkthroughStep(
                id = "timers",
                pages = listOf(
                    WalkthroughPage(
                        descriptionRes = R.string.walkthrough_desc_timers_1,
                        targetIds = listOf("timers")
                    ),
                    WalkthroughPage(
                        subtitleRes = R.string.walkthrough_subtitle_timers_hunt_duration,
                        descriptionRes = R.string.walkthrough_desc_timers_hunt_duration,
                        targetIds = listOf("timer_hunt_duration")
                    ),
                    WalkthroughPage(
                        subtitleRes = R.string.walkthrough_subtitle_timers_hunt_cooldown,
                        descriptionRes = R.string.walkthrough_desc_timers_hunt_cooldown,
                        targetIds = listOf("timer_hunt_cooldown")
                    ),
                    WalkthroughPage(
                        subtitleRes = R.string.walkthrough_subtitle_timers_link,
                        descriptionRes = R.string.walkthrough_desc_timers_link,
                        targetIds = listOf("timer_hunt_duration", "timer_hunt_cooldown", "timer_link")
                    ),
                    WalkthroughPage(
                        subtitleRes = R.string.walkthrough_subtitle_timers_smudge,
                        descriptionRes = R.string.walkthrough_desc_timers_smudge,
                        targetIds = listOf("timer_smudge")
                    ),
                    WalkthroughPage(
                        subtitleRes = R.string.walkthrough_subtitle_timers_fingerprint,
                        descriptionRes = R.string.walkthrough_desc_timers_fingerprint,
                        targetIds = listOf("timer_fingerprint")
                    )
                ),
                titleRes = R.string.walkthrough_title_timers
            ),
            WalkthroughStep(
                id = "footstep",
                pages = listOf(
                    WalkthroughPage(
                        subtitleRes = R.string.walkthrough_title_footstep,
                        descriptionRes = R.string.walkthrough_desc_footstep_1,
                        targetIds = listOf("footstep")
                    ),
                    WalkthroughPage(
                        subtitleRes = R.string.walkthrough_title_footstep,
                        descriptionRes = R.string.walkthrough_desc_footstep_1,
                        targetIds = listOf("footstep_modifiers")
                    ),
                    WalkthroughPage(
                        subtitleRes = R.string.walkthrough_title_footstep,
                        descriptionRes = R.string.walkthrough_desc_footstep_1,
                        targetIds = listOf("footstep_apply")
                    ),
                    WalkthroughPage(
                        subtitleRes = R.string.walkthrough_title_footstep,
                        descriptionRes = R.string.walkthrough_desc_footstep_1,
                        targetIds = listOf("footstep_visualizer")
                    ),
                    WalkthroughPage(
                        subtitleRes = R.string.walkthrough_title_footstep,
                        descriptionRes = R.string.walkthrough_desc_footstep_1,
                        targetIds = listOf("footstep_viewport", "footstep_sample")
                    )
                ),
                titleRes = R.string.walkthrough_title_footstep
            )
        )
    )

    InvestigationContent(
        uiState = uiState,
        uiActions = uiActions,
        walkthroughState = walkthroughState,
        isSanityCollapsed = isSanityCollapsed,
        onToggleSanityCollapse = {
            isSanityCollapsed = !isSanityCollapsed
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InvestigationContent(
    uiState: InvestigationUiState,
    uiActions: InvestigationUiActions,
    walkthroughState: WalkthroughState,
    isSanityCollapsed: Boolean,
    onToggleSanityCollapse: () -> Unit
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
    val onLinkToggle = uiActions.onLinkToggle
    val onToggleCursed = uiActions.onToggleCursed
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
    val onStartTutorial = uiActions.onStartTutorial

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

    val challengeLabel = difficultyUiState.challengeTitle?.toStringResource()

    val customDifficultyLabel = customDifficultyConfigUiState.selectedDifficulty?.let {
        it.name ?:
        "${stringResource(CustomDifficultyResources.Title.CUSTOM.toStringResource())} ${it.id}"
    } ?: ""
    val customDifficultyDropdownOptions = customDifficultyConfigUiState.difficulties.map {
        it.name ?:
        "${stringResource(CustomDifficultyResources.Title.CUSTOM.toStringResource())} ${it.id}"
    }

    val sanityLevel = sanityUiState.sanityLevel
    val insanityLevel = sanityUiState.insanityLevel

    val timerRemainingTime = operationTimerUiState.remainingTime
    val timerPaused = operationTimerUiState.paused

    val temperatureStateBundle = TemperatureStateBundle(temperatureUiState)
    val fuseBoxFlag = difficultyOverrideUiState.fuseBox

    val smudgeHuntPreventionTitle = stringResource(R.string.tool_timer_label_smudge_cleanse)
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
            modifier = modifier
                .walkthroughTarget(
                    walkthroughState,
                    "traits",
                    RoundedCornerShape(8.dp)
                ),
            walkthroughState = walkthroughState,
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
            modifier = modifier
                .walkthroughTarget(
                    walkthroughState,
                    "analyzer",
                    RoundedCornerShape(8.dp)
                ),
            operationDetailsUiState = operationDetailsUiState
        )
    }

    val footstepComponent: @Composable (Modifier) -> Unit = { modifier ->
        BpmTool(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .walkthroughTarget(
                    walkthroughState,
                    "footstep",
                    RoundedCornerShape(8.dp)
                )
                .preferredFrameRate(FrameRateCategory.Normal),
            walkthroughState = walkthroughState,
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
        TimerTools(
            modifier = modifier
                .walkthroughTarget(
                    walkthroughState,
                    "timers",
                    RoundedCornerShape(8.dp)
                ),
            walkthroughState = walkthroughState,
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
            onFingerprintToggle = onFingerprintToggle,
            fingerprintNotches = fingerprintNotches,
            timersLinked = timersLinked,
            onLinkToggle = onLinkToggle,
            onToggleCursed = onToggleCursed,
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
            modifier = modifier
                .walkthroughTarget(
                    walkthroughState,
                    "configs",
                    RoundedCornerShape(8.dp)
                ),
            mapConfigComponent = { modifier ->
                MapConfigControl(
                    modifier = modifier
                        .walkthroughTarget(walkthroughState, "config_map", RoundedCornerShape(8.dp)),
                    dropdownOptions = mapDropdownOptions,
                    isDropdownEnabled = isMapEnabled,
                    dropdownLabel = mapLabel,
                    colors = operationConfigUiColors,
                    onDropdownSelect = onMapDropdownSelect
                )
            },
            difficultyConfigComponent = { modifier ->
                DifficultyConfigControl(
                    modifier = modifier
                        .walkthroughTarget(walkthroughState, "config_difficulty", RoundedCornerShape(8.dp)),
                    dropdownOptions = difficultyDropdownOptions,
                    isDropdownEnabled = isDifficultyEnabled,
                    dropdownLabel = difficultyLabel,
                    colors = operationConfigUiColors,
                    onDropdownSelect = onDifficultyDropdownSelect
                )
            },
            challengeLabelComponent = { modifier ->
                challengeLabel?.let {
                    DifficultyChallengeLabel(
                        modifier = modifier,
                        label = challengeLabel,
                        colors = operationConfigUiColors
                    )
                }
            },
            customDifficultyConfigComponent = { modifier ->
                CustomDifficultyConfigControl(
                    modifier = modifier,
                    dropdownOptions = customDifficultyDropdownOptions,
                    isDropdownEnabled = isDifficultyEnabled,
                    dropdownLabel = customDifficultyLabel,
                    colors = operationConfigUiColors,
                    onDropdownSelect = { onCustomDifficultyDropdownSelect(it) },
                    onEditClick = { onNavigateToEditCustomDifficulty() }
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
                    modifier = modifier
                        .walkthroughTarget(walkthroughState, "config_weather", RoundedCornerShape(8.dp)),
                    icon = weatherIcon,
                    dropdownOptions = weatherDropdownOptions,
                    isDropdownEnabled = isWeatherEnabled,
                    dropdownLabel = weatherLabel,
                    onDropdownSelect = onWeatherDropdownSelect
                )
            },
            sanityMeterComponent = { modifier, onHeadClick ->
                SanityMeterComponent(
                    modifier = modifier
                        .walkthroughTarget(
                            walkthroughState,
                            "sanity",
                            RoundedCornerShape(8.dp)
                        ),
                    sanityLevel = sanityLevel,
                    insanityLevel = insanityLevel,
                    onSanityChange = onSanityChange,
                    onHeadClick = onHeadClick
                )
            },
            timerComponent = { modifier ->
                OperationTimerColumn(
                    modifier = modifier
                        .walkthroughTarget(walkthroughState, "config_timer", RoundedCornerShape(8.dp)),
                    remainingTime = timerRemainingTime,
                    paused = timerPaused,
                    onToggle = onTimerToggle,
                    onSkip = onTimerSkip,
                    phaseUiState = phaseUiState
                )
            },
            sanityMedicationComponent = { modifier ->
                SanityMedicationButton(
                    modifier = modifier
                        .walkthroughTarget(walkthroughState, "config_medication", RoundedCornerShape(8.dp)),
                    onClick = onUseSanityMedication
                )
            },
            playerDeathButtonComponent = { modifier ->
                PlayerDeathButton(
                    modifier = modifier
                        .walkthroughTarget(walkthroughState, "config_death", CircleShape),
                    onClick = onPlayerDeath
                )
            },
            temperatureMeterComponent = { modifier ->
                TemperatureComponent(
                    modifier = modifier
                        .walkthroughTarget(walkthroughState, "config_temperature", RoundedCornerShape(8.dp)),
                    state = temperatureStateBundle
                )
            },
            fuseBoxControlComponent = { modifier ->
                FuseBoxButton(
                    modifier = modifier
                        .walkthroughTarget(walkthroughState, "config_power", CircleShape),
                    flag = fuseBoxFlag,
                    onTogglePower = onTogglePower
                )
            },
            showTemperatureMeterComponent = weather != Weather.RANDOM,
            showChallengeTitleComponent = difficultyUiState.type == DifficultyType.CHALLENGE,
            showEditCustomDifficultyComponent = difficultyUiState.type == DifficultyType.CUSTOM
        )
    }

    val configSideSheetCompact: @Composable (Modifier) -> Unit = { modifier ->
        OperationConfigsSideSheetCompact(
            modifier = modifier
                .walkthroughTarget(
                    walkthroughState,
                    "configs",
                    RoundedCornerShape(8.dp)
                ),
            isSanityCollapsed = isSanityCollapsed,
            onToggleSanityExpansion = { onToggleSanityCollapse() },
            sanityMedicationComponent = { modifier ->
                SanityMedicationButton(
                    modifier = modifier
                        .walkthroughTarget(walkthroughState, "config_medication", RoundedCornerShape(8.dp)),
                    onClick = onUseSanityMedication
                )
            },
            mapConfigComponent = { modifier ->
                MapConfigControl(
                    modifier = modifier
                        .walkthroughTarget(walkthroughState, "config_map", RoundedCornerShape(8.dp)),
                    dropdownOptions = mapDropdownOptions,
                    isDropdownEnabled = isMapEnabled,
                    dropdownLabel = mapLabel,
                    colors = operationConfigUiColors,
                    onDropdownSelect = onMapDropdownSelect
                )
            },
            difficultyConfigComponent = { modifier ->
                DifficultyConfigControl(
                    modifier = modifier
                        .walkthroughTarget(walkthroughState, "config_difficulty", RoundedCornerShape(8.dp)),
                    dropdownOptions = difficultyDropdownOptions,
                    isDropdownEnabled = isDifficultyEnabled,
                    dropdownLabel = difficultyLabel,
                    colors = operationConfigUiColors,
                    onDropdownSelect = onDifficultyDropdownSelect
                )
            },
            challengeLabelComponent = { modifier ->
                challengeLabel?.let {
                    DifficultyChallengeLabel(
                        modifier = modifier,
                        label = challengeLabel,
                        colors = operationConfigUiColors
                    )
                }
            },
            customDifficultyConfigComponent = { modifier ->
                CustomDifficultyConfigControl(
                    modifier = modifier,
                    dropdownOptions = customDifficultyDropdownOptions,
                    isDropdownEnabled = isDifficultyEnabled,
                    dropdownLabel = customDifficultyLabel,
                    colors = operationConfigUiColors,
                    onDropdownSelect = { onCustomDifficultyDropdownSelect(it) },
                    onEditClick = { onNavigateToEditCustomDifficulty() }
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
                    modifier = modifier
                        .walkthroughTarget(walkthroughState, "config_weather", RoundedCornerShape(8.dp)),
                    icon = weatherIcon,
                    dropdownOptions = weatherDropdownOptions,
                    isDropdownEnabled = isWeatherEnabled,
                    dropdownLabel = weatherLabel,
                    onDropdownSelect = onWeatherDropdownSelect
                )
            },
            sanityMeterComponent = { modifier, onHeadClick ->
                SanityMeterComponent(
                    modifier = modifier
                        .walkthroughTarget(
                            walkthroughState,
                            "sanity",
                            RoundedCornerShape(8.dp)
                        ),
                    sanityLevel = sanityLevel,
                    insanityLevel = insanityLevel,
                    onSanityChange = onSanityChange,
                    onHeadClick = onHeadClick
                )
            },
            timerComponent = { modifier ->
                OperationTimerRow(
                    modifier = modifier
                        .walkthroughTarget(walkthroughState, "config_timer", RoundedCornerShape(8.dp)),
                    remainingTime = timerRemainingTime,
                    paused = timerPaused,
                    onToggle = onTimerToggle,
                    onSkip = onTimerSkip,
                    phaseUiState = phaseUiState
                )
            },
            playerDeathButtonComponent = { modifier ->
                PlayerDeathButton(
                    modifier = modifier
                        .walkthroughTarget(walkthroughState, "config_death", CircleShape),
                    onClick = onPlayerDeath
                )
            },
            temperatureMeterComponent = { modifier ->
                TemperatureComponent(
                    modifier = modifier
                        .walkthroughTarget(walkthroughState, "config_temperature", RoundedCornerShape(8.dp)),
                    state = temperatureStateBundle
                )
            },
            fuseBoxControlComponent = { modifier ->
                FuseBoxButton(
                    modifier = modifier
                        .walkthroughTarget(walkthroughState, "config_power", CircleShape),
                    flag = fuseBoxFlag,
                    onTogglePower = onTogglePower
                )
            },
            showTemperatureMeterComponent = weather != Weather.RANDOM,
            showChallengeTitleComponent = difficultyUiState.type == DifficultyType.CHALLENGE,
            showEditCustomDifficultyComponent = difficultyUiState.type == DifficultyType.CUSTOM
        )
    }

    val configSideSheetExpanded: @Composable (Modifier) -> Unit = { modifier ->
        OperationConfigsSideSheetExpanded(
            modifier = modifier
                .walkthroughTarget(
                    walkthroughState,
                    "configs",
                    RoundedCornerShape(8.dp)
                ),
            sanityMedicationComponent = { modifier ->
                SanityMedicationButton(
                    modifier = modifier
                        .walkthroughTarget(walkthroughState, "config_medication", RoundedCornerShape(8.dp)),
                    onClick = onUseSanityMedication
                )
            },
            mapConfigComponent = { modifier ->
                MapConfigControl(
                    modifier = modifier
                        .walkthroughTarget(walkthroughState, "config_map", RoundedCornerShape(8.dp)),
                    dropdownOptions = mapDropdownOptions,
                    isDropdownEnabled = isMapEnabled,
                    dropdownLabel = mapLabel,
                    colors = operationConfigUiColors,
                    onDropdownSelect = onMapDropdownSelect
                )
            },
            difficultyConfigComponent = { modifier ->
                DifficultyConfigControl(
                    modifier = modifier
                        .walkthroughTarget(walkthroughState, "config_difficulty", RoundedCornerShape(8.dp)),
                    dropdownOptions = difficultyDropdownOptions,
                    isDropdownEnabled = isDifficultyEnabled,
                    dropdownLabel = difficultyLabel,
                    colors = operationConfigUiColors,
                    onDropdownSelect = onDifficultyDropdownSelect
                )
            },
            challengeLabelComponent = { modifier ->
                challengeLabel?.let {
                    DifficultyChallengeLabel(
                        modifier = modifier,
                        label = challengeLabel,
                        colors = operationConfigUiColors
                    )
                }
            },
            customDifficultyConfigComponent = { modifier ->
                CustomDifficultyConfigControl(
                    modifier = modifier,
                    dropdownOptions = customDifficultyDropdownOptions,
                    isDropdownEnabled = isDifficultyEnabled,
                    dropdownLabel = customDifficultyLabel,
                    colors = operationConfigUiColors,
                    onDropdownSelect = { onCustomDifficultyDropdownSelect(it) },
                    onEditClick = { onNavigateToEditCustomDifficulty() }
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
                    modifier = modifier
                        .walkthroughTarget(walkthroughState, "config_weather", RoundedCornerShape(8.dp)),
                    icon = weatherIcon,
                    dropdownOptions = weatherDropdownOptions,
                    isDropdownEnabled = isWeatherEnabled,
                    dropdownLabel = weatherLabel,
                    onDropdownSelect = onWeatherDropdownSelect
                )
            },
            sanityMeterComponent = { modifier, onHeadClick ->
                SanityMeterComponent(
                    modifier = modifier
                        .walkthroughTarget(
                            walkthroughState,
                            "sanity",
                            RoundedCornerShape(8.dp)
                        ),
                    sanityLevel = sanityLevel,
                    insanityLevel = insanityLevel,
                    onSanityChange = onSanityChange,
                    onHeadClick = onHeadClick
                )
            },
            timerComponent = { modifier ->
                OperationTimerRow(
                    modifier = modifier
                        .walkthroughTarget(walkthroughState, "config_timer", RoundedCornerShape(8.dp)),
                    remainingTime = timerRemainingTime,
                    paused = timerPaused,
                    onToggle = onTimerToggle,
                    onSkip = onTimerSkip,
                    phaseUiState = phaseUiState
                )
            },
            playerDeathButtonComponent = { modifier ->
                PlayerDeathButton(
                    modifier = modifier
                        .walkthroughTarget(walkthroughState, "config_death", CircleShape),
                    onClick = onPlayerDeath
                )
            },
            temperatureMeterComponent = { modifier ->
                TemperatureComponent(
                    modifier = modifier
                        .walkthroughTarget(walkthroughState, "config_temperature", RoundedCornerShape(8.dp)),
                    state = temperatureStateBundle
                )
            },
            fuseBoxControlComponent = { modifier ->
                FuseBoxButton(
                    modifier = modifier
                        .walkthroughTarget(walkthroughState, "config_power", CircleShape),
                    flag = fuseBoxFlag,
                    onTogglePower = onTogglePower
                )
            },
            showTemperatureMeterComponent = weather != Weather.RANDOM,
            showChallengeTitleComponent = difficultyUiState.type == DifficultyType.CHALLENGE,
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
            footstepComponent = footstepComponent,
            onStartTutorial = { walkthroughState.start(it) }
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
                    footstepComponent = footstepComponent,
                    onStartTutorial = { walkthroughState.start(it) }
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
                    footstepComponent = footstepComponent,
                    onStartTutorial = { walkthroughState.start(it) }
                )
            }
        }

    }

    val journalComponent: @Composable (Modifier) -> Unit = { modifier ->
        JournalComponent(
            modifier = modifier
                .walkthroughTarget(walkthroughState, "journal", RoundedCornerShape(8.dp)),
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
            modifier = modifier
                .walkthroughTarget(walkthroughState, "status_bar"),
            remainingTime = operationTimerUiState.remainingTime,
            phaseType = phaseUiState.type,
            sanityLevel = sanityUiState.sanityLevel,
            weatherType = weatherUiState.weather,
            temperature = temperatureUiState.currentAsString,
            fuseBoxFlag = fuseBoxFlag
        )
    }

    WalkthroughHost(state = walkthroughState) {
        when (deviceConfiguration) {
            DeviceConfiguration.MOBILE_PORTRAIT,
            DeviceConfiguration.TABLET_PORTRAIT -> {
                CompactPortraitContent(
                    modifier = Modifier,
                    walkthroughState = walkthroughState,
                    toolbarState = toolbarUiState,
                    toolbarActions = ToolbarUiActions(
                        onToggleCollapseToolbar = onToggleCollapseToolbar,
                        onChangeToolbarCategory = { category, allowCollapse ->
                            onChangeToolbarCategory(category, allowCollapse)
                        },
                        onReset = onReset,
                        onStartTutorial = { walkthroughState.start() }
                    ),
                    statusBarComponent = { modifier -> statusBarComponent(modifier) },
                    journalComponent = { modifier -> journalComponent(modifier) },
                    bottomSheetComponent = bottomSheetComponent
                )
            }

            DeviceConfiguration.MOBILE_LANDSCAPE -> {
                CompactLandscapeContent(
                    modifier = Modifier,
                    walkthroughState = walkthroughState,
                    operationToolbarUiState = toolbarUiState,
                    toolbarUiActions = ToolbarUiActions(
                        onToggleCollapseToolbar = onToggleCollapseToolbar,
                        onChangeToolbarCategory = { category, allowCollapse ->
                            onChangeToolbarCategory(category, allowCollapse)
                        },
                        onReset = onReset,
                        onStartTutorial = { walkthroughState.start() }
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
                    walkthroughState = walkthroughState,
                    operationToolbarUiState = toolbarUiState,
                    toolbarUiActions = ToolbarUiActions(
                        onToggleCollapseToolbar = onToggleCollapseToolbar,
                        onChangeToolbarCategory = { category, allowCollapse ->
                            onChangeToolbarCategory(category, allowCollapse)
                        },
                        onReset = onReset,
                        onStartTutorial = { walkthroughState.start() }
                    ),
                    statusBarComponent = { modifier -> statusBarComponent(modifier) },
                    journalComponent = { modifier -> journalComponent(modifier) },
                    sideSheetComponent = { modifier -> sideSheetComponent(modifier) }
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
    walkthroughState: WalkthroughState,
    toolbarState: OperationToolbarUiState,
    toolbarActions: ToolbarUiActions,
    statusBarComponent: @Composable (Modifier) -> Unit = {},
    bottomSheetComponent: @Composable (Modifier) -> Unit,
    journalComponent: @Composable (Modifier) -> Unit
) {

    val toolbarComponent: @Composable (Modifier) -> Unit = { modifier ->
        OperationToolbar(
            modifier = modifier
                .walkthroughTarget(walkthroughState, "toolbar")
                .heightIn(min = 48.dp),
            category = toolbarState.category,
            onChangeToolbarCategory = { category, allowCollapse ->
                toolbarActions.onChangeToolbarCategory(category, allowCollapse)
            },
            onReset = toolbarActions.onReset,
            onStartTutorial = toolbarActions.onStartTutorial,
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
                .padding(8.dp)
                .walkthroughTarget(walkthroughState, "toolbar"),
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
    walkthroughState: WalkthroughState,
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
                    .walkthroughTarget(walkthroughState, "toolbar")
                    .widthIn(min = 48.dp),
                category = operationToolbarUiState.category,
                onChangeToolbarCategory = { category, allowCollapse ->
                    toolbarUiActions.onChangeToolbarCategory(category, allowCollapse)
                },
                onReset = toolbarUiActions.onReset,
                onStartTutorial = toolbarUiActions.onStartTutorial,
                containerColor = LocalPalette.current.surfaceContainerHigh
            )
        }

        VerticalToolbar(
            modifier = Modifier
                .walkthroughTarget(walkthroughState, "toolbar"),
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
                                    .width(0.dp)
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
    walkthroughState: WalkthroughState,
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
                    .walkthroughTarget(walkthroughState, "toolbar")
                    .widthIn(min = 48.dp),
                category = operationToolbarUiState.category,
                onChangeToolbarCategory = { category, allowCollapse ->
                    toolbarUiActions.onChangeToolbarCategory(category, allowCollapse)
                },
                onReset = toolbarUiActions.onReset,
                onStartTutorial = toolbarUiActions.onStartTutorial,
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
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Top),
            horizontalAlignment = Alignment.CenterHorizontally
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
    challengeLabelComponent: @Composable (Modifier) -> Unit = {},
    customDifficultyConfigComponent: @Composable (Modifier) -> Unit = {},
    weatherConfigComponent: @Composable (Modifier) -> Unit = {},
    temperatureMeterComponent: @Composable (Modifier) -> Unit = {},
    fuseBoxControlComponent: @Composable (Modifier) -> Unit = {},
    sanityMeterComponent: @Composable (Modifier, onHeadClick: () -> Unit) -> Unit = { _, _ -> },
    showTemperatureMeterComponent: Boolean,
    showChallengeTitleComponent: Boolean,
    showEditCustomDifficultyComponent: Boolean
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

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

                    difficultyConfigComponent(
                        Modifier
                            .fillMaxWidth()
                    )

                    if (showChallengeTitleComponent) {
                        challengeLabelComponent(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    if (showEditCustomDifficultyComponent) {
                        customDifficultyConfigComponent(
                            Modifier
                                .fillMaxWidth()
                        )

                        Spacer(modifier = Modifier.height(8.dp))
                    }

                    mapConfigComponent(
                        Modifier
                            .fillMaxWidth()
                            .widthIn(max = 400.dp)
                    )

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
                            .fillMaxSize()
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
                        .padding(8.dp)
                ) {}
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
    isSanityCollapsed: Boolean,
    onToggleSanityExpansion: () -> Unit,
    sanityMedicationComponent: @Composable (Modifier) -> Unit = {},
    playerDeathButtonComponent: @Composable (Modifier) -> Unit = {},
    timerComponent: @Composable (Modifier) -> Unit = {},
    mapConfigComponent: @Composable (Modifier) -> Unit = {},
    difficultyConfigComponent: @Composable (Modifier) -> Unit = {},
    challengeLabelComponent: @Composable (Modifier) -> Unit = {},
    customDifficultyConfigComponent: @Composable (Modifier) -> Unit = {},
    weatherConfigComponent: @Composable (Modifier) -> Unit = {},
    temperatureMeterComponent: @Composable (Modifier) -> Unit = {},
    fuseBoxControlComponent: @Composable (Modifier) -> Unit = {},
    sanityMeterComponent: @Composable (Modifier, onHeadClick: () -> Unit) -> Unit = { _, _ -> },
    showTemperatureMeterComponent: Boolean,
    showChallengeTitleComponent: Boolean,
    showEditCustomDifficultyComponent: Boolean
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

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

                difficultyConfigComponent(
                    Modifier
                        .fillMaxWidth()
                )

                if(showChallengeTitleComponent) {
                    challengeLabelComponent(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }

                if(showEditCustomDifficultyComponent) {
                    customDifficultyConfigComponent(
                        Modifier
                            .fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))
                }

                mapConfigComponent(
                    Modifier
                        .fillMaxWidth()
                )

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
                            onToggleSanityExpansion
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
                                onClick = onToggleSanityExpansion,
                                enabledRotationAddition = 90
                            )

                            sanityMeterComponent(
                                Modifier
                                    .height(48.dp)
                                    .weight(1f)
                                    .padding(8.dp),
                                onToggleSanityExpansion
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
    challengeLabelComponent: @Composable (Modifier) -> Unit = {},
    customDifficultyConfigComponent: @Composable (Modifier) -> Unit = {},
    weatherConfigComponent: @Composable (Modifier) -> Unit = {},
    temperatureMeterComponent: @Composable (Modifier) -> Unit = {},
    fuseBoxControlComponent: @Composable (Modifier) -> Unit = {},
    sanityMeterComponent: @Composable (Modifier, onHeadClick: () -> Unit) -> Unit = { _, _ -> },
    showTemperatureMeterComponent: Boolean,
    showChallengeTitleComponent: Boolean,
    showEditCustomDifficultyComponent: Boolean
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

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
                    difficultyConfigComponent(Modifier.fillMaxWidth())

                    if(showChallengeTitleComponent) {
                        challengeLabelComponent(
                            Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp)
                        )
                    }

                    if (showEditCustomDifficultyComponent) {
                        customDifficultyConfigComponent(Modifier.fillMaxWidth())
                    }

                    mapConfigComponent(Modifier.fillMaxWidth())

                }
            }

            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.Start
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
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

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
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
                        Modifier
                            .padding(8.dp)
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
    val customDifficultyConfig: CustomDifficultyConfigUiState = CustomDifficultyConfigUiState(),
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
    val onReset: (OperationToolbarUiState.ResetOption?) -> Unit = {},
    val onGhostNameClick: (GhostResources.GhostIdentifier) -> Unit = {},
    val onToggleNegateGhost: (Ghost) -> Unit = {},
    val onChangeEvidenceRuling: (EvidenceType, EvidenceValidationType) -> Unit = { _, _ -> },
    val onEvidenceClick: (EvidenceType) -> Unit = {},
    val onClearPopup: () -> Unit = {},
    val onStartTutorial: () -> Unit = {}
)

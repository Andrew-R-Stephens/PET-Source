package com.tritiumgaming.feature.investigation.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.impl.base.TruckTimeIcon
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.ClassicPalette
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.feature.investigation.app.mappers.difficulty.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.map.toStringResource
import com.tritiumgaming.feature.investigation.ui.common.analysis.OperationDetailsUiState
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.DigitalTimer
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.TimerToggleButton
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.TimerUiActions
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.ConfigActionsBundle
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.ConfigStateBundle
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.carousel.CarouselUiActions
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.carousel.ConfigCarouselUiState
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.carousel.OperationConfigCarousel
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.dropdown.ConfigDropdownUiState
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.dropdown.DropdownUiActions
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.dropdown.OperationConfigDropdown
import com.tritiumgaming.feature.investigation.ui.common.sanitymeter.PlayerSanityUiState
import com.tritiumgaming.feature.investigation.ui.common.sanitymeter.SanityMeter
import com.tritiumgaming.feature.investigation.ui.journal.Journal
import com.tritiumgaming.feature.investigation.ui.journal.JournalStateBundle
import com.tritiumgaming.feature.investigation.ui.journal.JournalUiState
import com.tritiumgaming.feature.investigation.ui.journal.lists.evidence.EvidenceListUiActions
import com.tritiumgaming.feature.investigation.ui.journal.lists.evidence.EvidenceListUiState
import com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.GhostListUiActions
import com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.GhostListUiState
import com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.item.GhostListUiItemActions
import com.tritiumgaming.feature.investigation.ui.popups.common.InvestigationPopup
import com.tritiumgaming.feature.investigation.ui.popups.evidence.EvidencePopup
import com.tritiumgaming.feature.investigation.ui.popups.ghost.GhostPopup
import com.tritiumgaming.feature.investigation.ui.section.analysis.OperationAnalysis
import com.tritiumgaming.feature.investigation.ui.section.configs.OperationConfigs
import com.tritiumgaming.feature.investigation.ui.section.footstep.BpmTool
import com.tritiumgaming.feature.investigation.ui.section.footstep.BpmToolUiActions
import com.tritiumgaming.feature.investigation.ui.section.footstep.BpmToolUiState
import com.tritiumgaming.feature.investigation.ui.section.timers.OperationTimers
import com.tritiumgaming.feature.investigation.ui.tool.timers.NotchedProgressBarBundle
import com.tritiumgaming.feature.investigation.ui.tool.timers.NotchedProgressBarUiColors
import com.tritiumgaming.feature.investigation.ui.tool.timers.NotchedProgressBarUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiActions
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.impl.OperationToolbar
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources

@Composable
fun InvestigationSoloScreen(
    investigationViewModel: InvestigationScreenViewModel
) {

    InvestigationContent(
        investigationViewModel = investigationViewModel
    )

}

@Composable
private fun InvestigationContent(
    investigationViewModel: InvestigationScreenViewModel
) {

    val popupUiState by investigationViewModel.popupUiState.collectAsStateWithLifecycle()

    val toolbarUiState by investigationViewModel.toolbarUiState.collectAsStateWithLifecycle()

    val timerUiState by investigationViewModel.timerUiState.collectAsStateWithLifecycle()
    val phaseUiState by investigationViewModel.phaseUiState.collectAsStateWithLifecycle()
    val mapConfigUiState by investigationViewModel.mapConfigUiState.collectAsStateWithLifecycle()
    val mapDetailsUiState by investigationViewModel.operationDetailsUiState.collectAsStateWithLifecycle()
    val difficultyUiState by investigationViewModel.difficultyConfigUiState.collectAsStateWithLifecycle()
    val sanityUiState by investigationViewModel.playerSanityUiState.collectAsStateWithLifecycle()

    val ghostStates by investigationViewModel.ghostStates.collectAsStateWithLifecycle()
    val ghostOrder by investigationViewModel.sortedGhosts.collectAsStateWithLifecycle()
    val evidenceStates by investigationViewModel.evidenceStates.collectAsStateWithLifecycle()

    // TODO val smudgeHuntPreventionState by investigationViewModel.smudgeHuntPreventionState.collectAsStateWithLifecycle()

    val toolbarFootstepsVisualizerSectionUiState by investigationViewModel
        .bpmToolUiState.collectAsStateWithLifecycle()

    val journalUiState = JournalUiState(
        rtlPreference = investigationViewModel.rTLPreference
    )

    val ghostListUiState = GhostListUiState(
        ghostStates = ghostStates,
        ghostOrder = ghostOrder,
        evidenceState = evidenceStates
    )

    val evidenceListUiState = EvidenceListUiState(
        evidenceStateList = evidenceStates
    )

    val ghostListUiActions = GhostListUiActions(
        onFindGhostById = { ghostId -> investigationViewModel.getGhostById(ghostId) },
        onNameClick = { ghostType -> investigationViewModel.setPopup(ghostType) }
    )

    val ghostListUiItemActions = GhostListUiItemActions(
        onGetEvidenceState = { evidenceType ->
            investigationViewModel.getRuledEvidence(evidenceType)
        },
        onToggleNegateGhost = { ghostType ->
            investigationViewModel.toggleGhostNegation(ghostType)
        },
    )

    val operationDetailsUiState = OperationDetailsUiState(
        mapConfigUiState = mapConfigUiState,
        operationDetailsUiState = mapDetailsUiState,
        phaseUiState = phaseUiState,
        difficultyConfigUiState = difficultyUiState,
        timerUiState = timerUiState,
        sanityUiState = sanityUiState,
        ghostStates = ghostStates,
        ghostOrder = ghostOrder,
        evidenceState = evidenceStates
    )

    val toolbarUiActions = ToolbarUiActions(
        onToggleCollapseToolbar = { investigationViewModel.toggleToolbarState() },
        onChangeToolbarCategory = { category -> investigationViewModel.setToolbarCategory(category)
        },
        onReset = { investigationViewModel.reset() }
    )

    val timerUiActions = TimerUiActions(
        onToggleTimer = {
            investigationViewModel.toggleTimer()
        }
    )

    val mapUiActions = ConfigActionsBundle(
        carouselUiActions = CarouselUiActions(
            onLeftClick = { investigationViewModel.decrementMapIndex() },
            onRightClick = { investigationViewModel.incrementMapIndex() },
        ),
        dropdownUiActions = DropdownUiActions(
            onSelect = { investigationViewModel.setMapIndex(it) }
        )
    )

    val difficultyUiActions = ConfigActionsBundle(
        carouselUiActions = CarouselUiActions(
            onLeftClick = { investigationViewModel.decrementDifficultyIndex() },
            onRightClick = { investigationViewModel.incrementDifficultyIndex() }
        ),
        dropdownUiActions = DropdownUiActions(
            onSelect = { investigationViewModel.setDifficultyIndex(it) }
        )
    )

    val evidenceListUiActions = EvidenceListUiActions(
        onChangeEvidenceRuling = { e, r ->
            investigationViewModel.setEvidenceRuling(e, r) },
        onClickItem = { investigationViewModel.setPopup(it) },
    )

    val bpmToolUiActions = BpmToolUiActions(
        onUpdate = {
            investigationViewModel.setBpmData(it)
        },
        onChangeMeasurementType = {
            investigationViewModel.setBpmMeasurementType(it)
        },
        toggleApplyMeasurement = {
            investigationViewModel.toggleApplyBpmMeasurement()
        }
    )

    //TODO replace with viewmodel state
    val smudgeHuntProtectionTimerState = NotchedProgressBarUiState(
        max = 72000,
        origin = 0,
        remaining = 50000,
        notches = listOf(
        ),
        running = false
    )

    //TODO replace with viewmodel state
    val smudgeBlindingProtectionTimerState = NotchedProgressBarUiState(
        max = 72000,
        origin = 0,
        remaining = 50000,
        notches = listOf(
        ),
        running = false
    )

    //TODO replace with viewmodel state
    val huntDurationTimerState = NotchedProgressBarUiState(
        max = 72000,
        origin = 0,
        remaining = 50000,
        notches = listOf(
        ),
        running = false
    )

    //TODO replace with viewmodel state
    val huntGapTimerState = NotchedProgressBarUiState(
        max = 72000,
        origin = 0,
        remaining = 50000,
        notches = listOf(
        ),
        running = false
    )

    val mapUiStateBundle = ConfigStateBundle(
        carouselUiState = ConfigCarouselUiState(
            label = mapConfigUiState.name.toStringResource(
                SimpleMapResources.MapTitleLength.ABBREVIATED)
        ),
        dropdownUiState = ConfigDropdownUiState(
            list = mapConfigUiState.allMaps.map { it
                .toStringResource(SimpleMapResources.MapTitleLength.FULL) },
            label = mapConfigUiState.name
                .toStringResource(SimpleMapResources.MapTitleLength.ABBREVIATED)
        )
    )

    val difficultyUiStateBundle = ConfigStateBundle(
        carouselUiState = ConfigCarouselUiState(
            label = difficultyUiState.name.toStringResource()
        ),
        dropdownUiState = ConfigDropdownUiState(
            list = difficultyUiState.allDifficulties.map { it.toStringResource() },
            label = difficultyUiState.name.toStringResource()
        )
    )

    val notchedProgressBarUiColors = NotchedProgressBarUiColors(
        remaining = LocalPalette.current.primary,
        background = LocalPalette.current.surface,
        border = LocalPalette.current.onSurface,
        notch = LocalPalette.current.onSurface,
        label = LocalPalette.current.onSurface,
    )

    val smudgeHuntPreventionBundle = NotchedProgressBarBundle(
        title = "Smudge Hunt Protection",
        state = smudgeHuntProtectionTimerState,
        colors = notchedProgressBarUiColors
    )

    val smudgeBlindingBundle = NotchedProgressBarBundle(
        title = "Smudge Hunt Protection",
        state = smudgeHuntProtectionTimerState,
        colors = notchedProgressBarUiColors
    )

    val huntDurationBundle = NotchedProgressBarBundle(
        title = "Smudge Hunt Protection",
        state = smudgeHuntProtectionTimerState,
        colors = notchedProgressBarUiColors
    )

    val huntGapBundle = NotchedProgressBarBundle(
        title = "Smudge Hunt Protection",
        state = smudgeHuntProtectionTimerState,
        colors = notchedProgressBarUiColors
    )

    val investigationUiState = InvestigationUiState(
        toolbarUiState = toolbarUiState,
        journalStateBundle = JournalStateBundle(
            journalUiState = journalUiState,
            evidenceListUiState = evidenceListUiState,
            ghostListUiState = ghostListUiState
        ),
        timerUiState = timerUiState,
        timerUiActions = timerUiActions,
        phaseUiState = phaseUiState,
        mapUiStateBundle = mapUiStateBundle,
        mapUiActionBundle = mapUiActions,
        difficultyUiStateBundle = difficultyUiStateBundle,
        difficultyUiActionBundle = difficultyUiActions,
        sanityUiState = sanityUiState,
        ghostListUiState = ghostListUiState,
        evidenceListUiState = evidenceListUiState,
        operationDetailsUiState = operationDetailsUiState,
        bpmToolUiState = toolbarFootstepsVisualizerSectionUiState,
        bpmToolUiActions = bpmToolUiActions,
        smudgeHuntPreventionBundle = smudgeHuntPreventionBundle,
        smudgeBlindingBundle = smudgeBlindingBundle,
        huntDurationBundle = huntDurationBundle,
        huntGapBundle = huntGapBundle
    )

    val investigationUiActions = InvestigationUiActions(
        evidenceListUi = evidenceListUiActions,
        ghostListUi = ghostListUiActions,
        ghostListItemUi = ghostListUiItemActions,
        toolbarUi = toolbarUiActions,
        bpmUi = bpmToolUiActions
    )

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    when(deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT,
        DeviceConfiguration.TABLET_PORTRAIT -> {
            InvestigationContentPortrait(
                state = investigationUiState,
                actions = investigationUiActions
            )
        }
        DeviceConfiguration.MOBILE_LANDSCAPE,
        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP -> {
            InvestigationContentLandscape(
                state = investigationUiState,
                actions = investigationUiActions
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
            ) { investigationViewModel.clearPopup() }
        }

        popupUiState.evidencePopupRecord?.let { record ->
            EvidencePopup(
                modifier = modifier,
                record = record
            ) { investigationViewModel.clearPopup() }
        }
    }
}

@Composable
private fun InvestigationContentPortrait(
    state: InvestigationUiState,
    actions: InvestigationUiActions
) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.Top
    ) {
        Investigation(
            state = state,
            actions = actions,
            mapConfigComponent = { modifier ->
                MapConfigComponent(
                    modifier = modifier,
                    stateBundle = state.mapUiStateBundle,
                    actionsBundle = state.mapUiActionBundle,
                    isCompact = true
                )
            },
            difficultyConfigComponent = { modifier ->
                DifficultyConfigComponent(
                    modifier = modifier,
                    stateBundle = state.difficultyUiStateBundle,
                    actionsBundle = state.difficultyUiActionBundle,
                    isCompact = true
                )
            },
            sanityMeterComponent = { modifier ->
                SanityMeterComponent(
                    modifier = modifier,
                    sanityUiState = state.sanityUiState
                )
            },
            timerComponent = { modifier ->
                TimerComponent(
                    modifier = modifier,
                    timerUiState = state.timerUiState,
                    timerUiActions = state.timerUiActions
                )
            }
        ) 
    }
}

@Composable
private fun InvestigationContentLandscape(
    state: InvestigationUiState,
    actions: InvestigationUiActions
) {
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.Start
    ) {
        Investigation(
            state = state,
            actions = actions,
            mapConfigComponent = { modifier ->
                MapConfigComponent(
                    modifier = modifier,
                    stateBundle = state.mapUiStateBundle,
                    actionsBundle = state.mapUiActionBundle,
                    isCompact = true
                )
            },
            difficultyConfigComponent = { modifier ->
                DifficultyConfigComponent(
                    modifier = modifier,
                    stateBundle = state.difficultyUiStateBundle,
                    actionsBundle = state.difficultyUiActionBundle,
                    isCompact = true
                )
            },
            sanityMeterComponent = { modifier ->
                SanityMeterComponent(
                    modifier = modifier,
                    sanityUiState = state.sanityUiState
                )
            },
            timerComponent = { modifier ->
                TimerComponent(
                    modifier = modifier,
                    timerUiState = state.timerUiState,
                    timerUiActions = state.timerUiActions
                )
            }
        )
    }
}

@Composable
private fun ColumnScope.Investigation(
    state: InvestigationUiState,
    actions: InvestigationUiActions,
    mapConfigComponent: @Composable (Modifier) -> Unit = {},
    difficultyConfigComponent: @Composable (Modifier) -> Unit = {},
    sanityMeterComponent: @Composable (Modifier) -> Unit = {},
    timerComponent: @Composable (Modifier) -> Unit = {}
) {

    Journal(
        modifier = Modifier
            .weight(1f, false),
        journalStateBundle = state.journalStateBundle,
        evidenceListUiActions = actions.evidenceListUi,
        ghostListUiActions = actions.ghostListUi,
        ghostListUiItemActions = actions.ghostListItemUi
    )

    val primaryContent: @Composable (Modifier) -> Unit = { modifier ->
        Column(
            modifier = modifier
                .fillMaxWidth()
                .animateContentSize()
                .then(
                    if (!state.toolbarUiState.isCollapsed)
                        Modifier
                            .alpha(1f)
                            .wrapContentHeight()
                    else
                        Modifier
                            .height(0.dp)
                            .alpha(0f)
                ),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.Bottom),
            horizontalAlignment = Alignment.Start
        ) {
            when (state.toolbarUiState.category) {
                ToolbarUiState.Category.TOOL_NONE -> {}
                ToolbarUiState.Category.TOOL_CONFIG -> OperationConfigs(
                    modifier = Modifier
                        .height(IntrinsicSize.Max),
                    mapConfigComponent = { modifier ->
                        mapConfigComponent(modifier)
                    },
                    difficultyConfigComponent = { modifier ->
                        difficultyConfigComponent(modifier)
                    },
                    sanityMeterComponent = { modifier ->
                        sanityMeterComponent(modifier)
                    },
                    timerComponent = { modifier ->
                        timerComponent(modifier)
                    },
                    phaseComponent = { modifier ->

                    }
                )

                ToolbarUiState.Category.TOOL_ANALYZER -> OperationAnalysis(
                    modifier = Modifier,
                    operationDetailsUiState = state.operationDetailsUiState
                )

                ToolbarUiState.Category.TOOL_TIMERS -> OperationTimers(
                    modifier = Modifier,
                    smudgeHuntPreventionBundle = state.smudgeHuntPreventionBundle,
                    smudgeBlindingBundle = state.smudgeBlindingBundle,
                    huntDurationBundle = state.huntDurationBundle,
                    huntGapBundle = state.huntGapBundle
                )

                ToolbarUiState.Category.TOOL_FOOTSTEP -> BpmTool(
                    modifier = Modifier,
                    state = state.bpmToolUiState,
                    actions = actions.bpmUi
                )

            }
        }
    }

    val toolbarContent: @Composable (Modifier) -> Unit = { modifier ->
        OperationToolbar(
            modifier = modifier
                .heightIn(min = 48.dp),
            toolbarUiState = state.toolbarUiState,
            toolbarUiActions = actions.toolbarUi,
            containerColor = LocalPalette.current.surfaceContainerHigh
        )
    }

    ToolbarBottomSheet(
        modifier = Modifier
            .padding(8.dp),
        toolbarContent = { modifier -> toolbarContent(modifier) },
        content = { modifier -> primaryContent(modifier) }
    )

}

@Composable
private fun RowScope.Investigation(
    state: InvestigationUiState,
    actions: InvestigationUiActions,
    mapConfigComponent: @Composable (Modifier) -> Unit = {},
    difficultyConfigComponent: @Composable (Modifier) -> Unit = {},
    sanityMeterComponent: @Composable (Modifier) -> Unit = {},
    timerComponent: @Composable (Modifier) -> Unit = {}
) {

    val toolbarContent: @Composable (Modifier) -> Unit = { modifier ->
        OperationToolbar(
            modifier = modifier
                .widthIn(min = 48.dp),
            toolbarUiState = state.toolbarUiState,
            toolbarUiActions = actions.toolbarUi,
            containerColor = LocalPalette.current.surfaceContainerHigh
        )
    }

    val primaryContent: @Composable (Modifier) -> Unit = { modifier ->
        Column(
            modifier = modifier
                .fillMaxHeight()
                .animateContentSize()
                .then(
                    if (!state.toolbarUiState.isCollapsed)
                        Modifier
                            .fillMaxWidth(.35f)
                            .alpha(1f)
                    else
                        Modifier
                            .height(0.dp)
                            .alpha(0f)
                ),
            /*modifier = modifier
                .then(
                    if (state.toolbarUiState.isCollapsed) Modifier
                        .animateContentSize()
                        .fillMaxWidth(0f)
                        .alpha(0f)
                    else Modifier.fillMaxWidth(.35f)
                ),*/
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            when (state.toolbarUiState.category) {
                ToolbarUiState.Category.TOOL_NONE -> {}
                ToolbarUiState.Category.TOOL_CONFIG -> OperationConfigs(
                    modifier = Modifier
                        .width(IntrinsicSize.Max),
                    mapConfigComponent = { modifier ->
                        mapConfigComponent(modifier)
                    },
                    difficultyConfigComponent = { modifier ->
                        difficultyConfigComponent(modifier)
                    },
                    sanityMeterComponent = { modifier ->
                        sanityMeterComponent(modifier)
                    },
                    timerComponent = { modifier ->
                        timerComponent(modifier)
                    },
                    phaseComponent = { modifier ->

                    }
                )

                ToolbarUiState.Category.TOOL_ANALYZER -> OperationAnalysis(
                    modifier = Modifier,
                    operationDetailsUiState = state.operationDetailsUiState
                )

                ToolbarUiState.Category.TOOL_TIMERS -> OperationTimers(
                    modifier = Modifier,
                    smudgeHuntPreventionBundle = state.smudgeHuntPreventionBundle,
                    smudgeBlindingBundle = state.smudgeBlindingBundle,
                    huntDurationBundle = state.huntDurationBundle,
                    huntGapBundle = state.huntGapBundle
                )

                ToolbarUiState.Category.TOOL_FOOTSTEP -> BpmTool(
                    modifier = Modifier,
                    state = state.bpmToolUiState,
                    actions = actions.bpmUi
                )

            }
        }
    }

    ToolbarSideSheet(
        modifier = Modifier
            .padding(8.dp),
        toolbarContent = { modifier -> toolbarContent(modifier) },
        content = { modifier -> primaryContent(modifier) }
    )

    Journal(
        modifier = Modifier,
        journalStateBundle = state.journalStateBundle,
        evidenceListUiActions = actions.evidenceListUi,
        ghostListUiActions = actions.ghostListUi,
        ghostListUiItemActions = actions.ghostListItemUi,
    )
}

@Composable
private fun ToolbarBottomSheet(
    modifier: Modifier = Modifier,
    toolbarContent: @Composable (Modifier) -> Unit = {},
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
            toolbarContent(
                Modifier
                    .heightIn(min = 48.dp)
            )

            content(Modifier)

        }
    }
}

@Composable
private fun RowScope.ToolbarSideSheet(
    modifier: Modifier = Modifier,
    toolbarContent: @Composable (Modifier) -> Unit = {},
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

            toolbarContent(
                Modifier
                    .widthIn(min = 48.dp)
            )

        }
    }
}

@Composable
private fun TimerComponent(
    modifier: Modifier,
    timerUiState: TimerUiState,
    timerUiActions: TimerUiActions,
) {
    Row(
        modifier = modifier
    ) {

        TruckTimeIcon(
            modifier = Modifier
                .size(48.dp)
                .padding(4.dp),
            colors = IconVectorColors(
                fillColor = LocalPalette.current.onSurface,
                strokeColor = LocalPalette.current.surface
            )
        )

        DigitalTimer(
            modifier = Modifier
                .height(48.dp)
                .padding(8.dp)
                .fillMaxWidth(),
            state = timerUiState
        )

        TimerToggleButton(
            modifier = Modifier
                .size(48.dp)
                .padding(start = 4.dp),
            state = timerUiState,
            actions = timerUiActions,
            playContent = { modifier ->
                Icon(
                    modifier = modifier,
                    painter = painterResource(R.drawable.ic_control_play),
                    contentDescription = null,
                    tint = LocalPalette.current.onSurface
                )
            },
            pauseContent = { modifier ->
                Icon(
                    modifier = modifier,
                    painter = painterResource(R.drawable.ic_control_pause),
                    contentDescription = null,
                    tint = LocalPalette.current.onSurface
                )
            }
        )

    }
}

@Composable
fun SanityMeterComponent(
    modifier: Modifier,
    sanityUiState: PlayerSanityUiState,
) {

    SanityMeter(
        modifier = modifier,
        sanityUiState = sanityUiState
    )

}

@Composable
private fun MapConfigComponent(
    modifier: Modifier,
    stateBundle: ConfigStateBundle,
    actionsBundle: ConfigActionsBundle,
    isCompact: Boolean,
) {

    val icon: @Composable (Modifier) -> Unit = { modifier ->
        Image(
            modifier = modifier,
            contentScale = ContentScale.Inside,
            alignment = Alignment.Center,
            painter = painterResource(R.drawable.icon_nav_mapmenu2),
            colorFilter = ColorFilter.tint(LocalPalette.current.onSurface),
            contentDescription = ""
        )
    }

    when(isCompact) {
        true -> {
            OperationConfigDropdown(
                modifier = modifier,
                icon = { icon(it) },
                state = stateBundle.dropdownUiState,
                textStyle = LocalTypography.current.secondary.regular,
                onColor = LocalPalette.current.onSurface,
                expandedColor = LocalPalette.current.surfaceContainer,
                actions = actionsBundle.dropdownUiActions,
            )
        }
        false -> {
            OperationConfigCarousel(
                modifier = modifier,
                state = stateBundle.carouselUiState,
                icon = { icon(it) },
                textStyle = LocalTypography.current.secondary.regular,
                color = LocalPalette.current.onSurface,
                actions = actionsBundle.carouselUiActions
            )
        }
    }
}

@Composable
private fun DifficultyConfigComponent(
    modifier: Modifier,
    stateBundle: ConfigStateBundle,
    actionsBundle: ConfigActionsBundle,
    isCompact: Boolean,
) {

    val icon: @Composable (Modifier) -> Unit = { modifier ->
        Image(
            modifier = modifier,
            contentScale = ContentScale.Inside,
            alignment = Alignment.Center,
            painter = painterResource(R.drawable.ic_puzzle),
            colorFilter = ColorFilter.tint(LocalPalette.current.onSurface),
            contentDescription = ""
        )
    }

    when(isCompact) {
        true -> {
            OperationConfigDropdown(
                modifier = modifier,
                state = stateBundle.dropdownUiState,
                actions = actionsBundle.dropdownUiActions,
                icon = { icon(it) },
                textStyle = LocalTypography.current.secondary.regular,
                expandedColor = LocalPalette.current.surfaceContainer,
                onColor = LocalPalette.current.onSurface,
            )
        }
        false -> {
            OperationConfigCarousel(
                modifier = modifier,
                state = stateBundle.carouselUiState,
                actions = actionsBundle.carouselUiActions,
                icon = { icon(it) },
                textStyle = LocalTypography.current.secondary.regular,
                color = LocalPalette.current.onSurface,
            )
        }
    }
}

@Preview(device = "id:pixel_9_pro_xl")
@Composable
private fun PortraitPreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        val timerUiState = TimerUiState()
        val phaseUiState = PhaseUiState()
        val sanityUiState = PlayerSanityUiState()
        val ghostListUiState = GhostListUiState(
            ghostStates = listOf(),
            ghostOrder = listOf(),
            evidenceState = listOf()
        )
        val evidenceListUiState = EvidenceListUiState(
            evidenceStateList = listOf()
        )

        val toolbarUiState = ToolbarUiState(
            isCollapsed = false,
            category = ToolbarUiState.Category.TOOL_CONFIG
        )

        val mapUiStateBundle = ConfigStateBundle(
            carouselUiState = ConfigCarouselUiState(label = R.string.map_name_short_prison),
            dropdownUiState = ConfigDropdownUiState(list = listOf(R.string.map_name_short_prison), label = R.string.map_name_short_prison)
        )

        val difficultyUiStateBundle = ConfigStateBundle(
            carouselUiState = ConfigCarouselUiState(label = R.string.difficulty_title_amateur),
            dropdownUiState = ConfigDropdownUiState(list = listOf(R.string.difficulty_title_amateur), label = R.string.difficulty_title_amateur)
        )

        val notchedProgressBarState = NotchedProgressBarUiState(
            max = 100,
            origin = 0,
            remaining = 50,
            notches = listOf(),
            running = false
        )

        val notchedProgressBarColors = NotchedProgressBarUiColors(
            remaining = LocalPalette.current.primary,
            background = LocalPalette.current.surface,
            border = LocalPalette.current.onSurface,
            notch = LocalPalette.current.onSurface,
            label = LocalPalette.current.onSurface,
        )

        val smudgeHuntPreventionBundle = NotchedProgressBarBundle(
            title = "Smudge Hunt Protection",
            state = notchedProgressBarState,
            colors = notchedProgressBarColors
        )

        val investigationUiState = InvestigationUiState(
            toolbarUiState = toolbarUiState,
            journalStateBundle = JournalStateBundle(
                journalUiState = JournalUiState(
                    rtlPreference = false
                ),
                evidenceListUiState = evidenceListUiState,
                ghostListUiState = ghostListUiState
            ),
            timerUiState = timerUiState,
            timerUiActions = TimerUiActions(),
            phaseUiState = phaseUiState,
            mapUiStateBundle = mapUiStateBundle,
            mapUiActionBundle = ConfigActionsBundle(
                carouselUiActions = CarouselUiActions(),
                dropdownUiActions = DropdownUiActions()
            ),
            difficultyUiStateBundle = difficultyUiStateBundle,
            difficultyUiActionBundle = ConfigActionsBundle(
                carouselUiActions = CarouselUiActions(),
                dropdownUiActions = DropdownUiActions()
            ),
            sanityUiState = sanityUiState,
            ghostListUiState = ghostListUiState,
            evidenceListUiState = evidenceListUiState,
            operationDetailsUiState = OperationDetailsUiState(
                mapConfigUiState = MapConfigUiState(),
                operationDetailsUiState = OperationDetailsUiState(),
                phaseUiState = phaseUiState,
                difficultyConfigUiState = DifficultyConfigUiState(),
                timerUiState = timerUiState,
                sanityUiState = sanityUiState,
                ghostStates = listOf(),
                ghostOrder = listOf(),
                evidenceState = listOf()
            ),
            bpmToolUiState = BpmToolUiState(),
            bpmToolUiActions = BpmToolUiActions(
                onUpdate = {},
                onChangeMeasurementType = {},
                toggleApplyMeasurement = {}
            ),
            smudgeHuntPreventionBundle = smudgeHuntPreventionBundle,
            smudgeBlindingBundle = smudgeHuntPreventionBundle,
            huntDurationBundle = smudgeHuntPreventionBundle,
            huntGapBundle = smudgeHuntPreventionBundle
        )

        Column(
            modifier = Modifier
                .background(LocalPalette.current.surface)
        ) {
            Investigation(
                state = investigationUiState,
                actions = InvestigationUiActions(
                    evidenceListUi = EvidenceListUiActions(),
                    ghostListUi = GhostListUiActions(),
                    ghostListItemUi = GhostListUiItemActions(),
                    toolbarUi = ToolbarUiActions(),
                    bpmUi = BpmToolUiActions()
                ),
                mapConfigComponent = { modifier ->
                    MapConfigComponent(
                        modifier = modifier,
                        stateBundle = mapUiStateBundle,
                        actionsBundle = ConfigActionsBundle(
                            carouselUiActions = CarouselUiActions(),
                            dropdownUiActions = DropdownUiActions()
                        ),
                        isCompact = true
                    )
                },
                difficultyConfigComponent = { modifier ->
                    DifficultyConfigComponent(
                        modifier = modifier,
                        stateBundle = difficultyUiStateBundle,
                        actionsBundle = ConfigActionsBundle(
                            carouselUiActions = CarouselUiActions(),
                            dropdownUiActions = DropdownUiActions()
                        ),
                        isCompact = true
                    )
                },
                sanityMeterComponent = { modifier ->
                    SanityMeterComponent(
                        modifier = modifier,
                        sanityUiState = sanityUiState
                    )
                },
                timerComponent = { modifier ->
                    TimerComponent(
                        modifier = modifier,
                        timerUiState = timerUiState,
                        timerUiActions = TimerUiActions()
                    )
                }
            )
        }
    }
}

@Preview(device = "spec:parent=pixel_9_pro_xl,orientation=landscape")
@Composable
private fun LandscapePreview() {
    SelectiveTheme(
        palette = ClassicPalette,
        typography = ClassicTypography
    ) {
        val timerUiState = TimerUiState()
        val phaseUiState = PhaseUiState()
        val sanityUiState = PlayerSanityUiState()
        val ghostListUiState = GhostListUiState(
            ghostStates = listOf(),
            ghostOrder = listOf(),
            evidenceState = listOf()
        )
        val evidenceListUiState = EvidenceListUiState(
            evidenceStateList = listOf()
        )

        val toolbarUiState = ToolbarUiState(
            isCollapsed = false,
            category = ToolbarUiState.Category.TOOL_CONFIG
        )

        val mapUiStateBundle = ConfigStateBundle(
            carouselUiState = ConfigCarouselUiState(label = R.string.map_name_short_prison),
            dropdownUiState = ConfigDropdownUiState(list = listOf(R.string.map_name_short_prison), label = R.string.map_name_short_prison)
        )

        val difficultyUiStateBundle = ConfigStateBundle(
            carouselUiState = ConfigCarouselUiState(label = R.string.difficulty_title_amateur),
            dropdownUiState = ConfigDropdownUiState(list = listOf(R.string.difficulty_title_amateur), label = R.string.difficulty_title_amateur)
        )

        val notchedProgressBarState = NotchedProgressBarUiState(
            max = 100,
            origin = 0,
            remaining = 50,
            notches = listOf(),
            running = false
        )

        val notchedProgressBarColors = NotchedProgressBarUiColors(
            remaining = LocalPalette.current.primary,
            background = LocalPalette.current.surface,
            border = LocalPalette.current.onSurface,
            notch = LocalPalette.current.onSurface,
            label = LocalPalette.current.onSurface,
        )

        val smudgeHuntPreventionBundle = NotchedProgressBarBundle(
            title = "Smudge Hunt Protection",
            state = notchedProgressBarState,
            colors = notchedProgressBarColors
        )

        val investigationUiState = InvestigationUiState(
            toolbarUiState = toolbarUiState,
            journalStateBundle = JournalStateBundle(
                journalUiState = JournalUiState(
                    rtlPreference = false
                ),
                evidenceListUiState = evidenceListUiState,
                ghostListUiState = ghostListUiState
            ),
            timerUiState = timerUiState,
            timerUiActions = TimerUiActions(),
            phaseUiState = phaseUiState,
            mapUiStateBundle = mapUiStateBundle,
            mapUiActionBundle = ConfigActionsBundle(
                carouselUiActions = CarouselUiActions(),
                dropdownUiActions = DropdownUiActions()
            ),
            difficultyUiStateBundle = difficultyUiStateBundle,
            difficultyUiActionBundle = ConfigActionsBundle(
                carouselUiActions = CarouselUiActions(),
                dropdownUiActions = DropdownUiActions()
            ),
            sanityUiState = sanityUiState,
            ghostListUiState = ghostListUiState,
            evidenceListUiState = evidenceListUiState,
            operationDetailsUiState = OperationDetailsUiState(
                mapConfigUiState = MapConfigUiState(),
                operationDetailsUiState = OperationDetailsUiState(),
                phaseUiState = phaseUiState,
                difficultyConfigUiState = DifficultyConfigUiState(),
                timerUiState = timerUiState,
                sanityUiState = sanityUiState,
                ghostStates = listOf(),
                ghostOrder = listOf(),
                evidenceState = listOf()
            ),
            bpmToolUiState = BpmToolUiState(),
            bpmToolUiActions = BpmToolUiActions(
                onUpdate = {},
                onChangeMeasurementType = {},
                toggleApplyMeasurement = {}
            ),
            smudgeHuntPreventionBundle = smudgeHuntPreventionBundle,
            smudgeBlindingBundle = smudgeHuntPreventionBundle,
            huntDurationBundle = smudgeHuntPreventionBundle,
            huntGapBundle = smudgeHuntPreventionBundle
        )

        Row(
            modifier = Modifier
                .background(LocalPalette.current.surface)
        ) {
            Investigation(
                state = investigationUiState,
                actions = InvestigationUiActions(
                    evidenceListUi = EvidenceListUiActions(),
                    ghostListUi = GhostListUiActions(),
                    ghostListItemUi = GhostListUiItemActions(),
                    toolbarUi = ToolbarUiActions(),
                    bpmUi = BpmToolUiActions()
                ),
                mapConfigComponent = { modifier ->
                    MapConfigComponent(
                        modifier = modifier,
                        stateBundle = mapUiStateBundle,
                        actionsBundle = ConfigActionsBundle(
                            carouselUiActions = CarouselUiActions(),
                            dropdownUiActions = DropdownUiActions()
                        ),
                        isCompact = true
                    )
                },
                difficultyConfigComponent = { modifier ->
                    DifficultyConfigComponent(
                        modifier = modifier,
                        stateBundle = difficultyUiStateBundle,
                        actionsBundle = ConfigActionsBundle(
                            carouselUiActions = CarouselUiActions(),
                            dropdownUiActions = DropdownUiActions()
                        ),
                        isCompact = true
                    )
                },
                sanityMeterComponent = { modifier ->
                    SanityMeterComponent(
                        modifier = modifier,
                        sanityUiState = sanityUiState
                    )
                },
                timerComponent = { modifier ->
                    TimerComponent(
                        modifier = modifier,
                        timerUiState = timerUiState,
                        timerUiActions = TimerUiActions()
                    )
                }
            )
        }
    }
}

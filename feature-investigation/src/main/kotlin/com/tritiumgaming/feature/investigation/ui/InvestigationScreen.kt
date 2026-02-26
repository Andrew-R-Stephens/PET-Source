package com.tritiumgaming.feature.investigation.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.impl.base.TruckTimeIcon
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
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
import com.tritiumgaming.feature.investigation.ui.section.timers.OperationTimers
import com.tritiumgaming.feature.investigation.ui.tool.timers.NotchedProgressBarBundle
import com.tritiumgaming.feature.investigation.ui.tool.timers.NotchedProgressBarUiColors
import com.tritiumgaming.feature.investigation.ui.tool.timers.NotchedProgressBarUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiActions
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.impl.OperationToolRail
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

    val preferencesState by investigationViewModel.preferences.collectAsStateWithLifecycle()

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
    
    val isCompact = true

    // TODO val smudgeHuntPreventionState by investigationViewModel.smudgeHuntPreventionState.collectAsStateWithLifecycle()

    val toolbarFootstepsVisualizerSectionUiState by investigationViewModel
        .bpmToolUiState.collectAsStateWithLifecycle()

    val journalUiState = JournalUiState(
        rtlPreference = preferencesState.enableRTL
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

    val mapConfigComponent: @Composable (Modifier) -> Unit = { modifier ->
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
                    state = mapUiStateBundle.dropdownUiState,
                    actions = mapUiActions.dropdownUiActions,
                    textStyle = LocalTypography.current.secondary.regular,
                    onColor = LocalPalette.current.onSurface,
                    expandedColor = LocalPalette.current.surfaceContainer,
                )
            }
            false -> {
                OperationConfigCarousel(
                    modifier = modifier,
                    state = mapUiStateBundle.carouselUiState,
                    actions = mapUiActions.carouselUiActions,
                    icon = { icon(it) },
                    textStyle = LocalTypography.current.secondary.regular,
                    color = LocalPalette.current.onSurface,
                )
            }
        }
    }
    val difficultyConfigComponent: @Composable (Modifier) -> Unit = { modifier ->

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
                    state = difficultyUiStateBundle.dropdownUiState,
                    actions = difficultyUiActions.dropdownUiActions,
                    icon = { icon(it) },
                    textStyle = LocalTypography.current.secondary.regular,
                    expandedColor = LocalPalette.current.surfaceContainer,
                    onColor = LocalPalette.current.onSurface,
                )
            }
            false -> {
                OperationConfigCarousel(
                    modifier = modifier,
                    state = difficultyUiStateBundle.carouselUiState,
                    actions = difficultyUiActions.carouselUiActions,
                    icon = { icon(it) },
                    textStyle = LocalTypography.current.secondary.regular,
                    color = LocalPalette.current.onSurface,
                )
            }
        }
    }
    val sanityMeterComponent: @Composable (Modifier) -> Unit = { modifier ->
        SanityMeter(
            modifier = modifier,
            sanityUiState = sanityUiState
        )
    }
    val timerComponent: @Composable (Modifier) -> Unit = { modifier ->
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

    val journalComponent: @Composable (Modifier) -> Unit = { modifier ->
        Journal(
            modifier = modifier,
            journalStateBundle = JournalStateBundle(
                journalUiState = journalUiState,
                evidenceListUiState = evidenceListUiState,
                ghostListUiState = ghostListUiState
            ),
            evidenceListUiActions = evidenceListUiActions,
            ghostListUiActions = ghostListUiActions,
            ghostListUiItemActions = ghostListUiItemActions,
        )
    }

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    when(deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT,
        DeviceConfiguration.TABLET_PORTRAIT -> {
            InvestigationContentPortrait(
                modifier = Modifier,
                investigationComponent = { modifier ->
                    Investigation(
                        state = investigationUiState,
                        actions = investigationUiActions,
                        journalComponent = { modifier -> journalComponent(modifier) },
                        mapConfigComponent = { modifier -> mapConfigComponent(modifier) },
                        difficultyConfigComponent = { modifier -> difficultyConfigComponent(modifier) },
                        sanityMeterComponent = { modifier -> sanityMeterComponent(modifier) },
                        timerComponent = { modifier -> timerComponent(modifier) }
                    )
                }
            )
        }
        DeviceConfiguration.MOBILE_LANDSCAPE,
        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP -> {
            InvestigationContentLandscape(
                modifier = Modifier,
                investigationComponent = { modifier ->
                    Investigation(
                        state = investigationUiState,
                        actions = investigationUiActions,
                        journalComponent = { modifier -> journalComponent(modifier) },
                        mapConfigComponent = { modifier -> mapConfigComponent(modifier) },
                        difficultyConfigComponent = { modifier -> difficultyConfigComponent(modifier) },
                        sanityMeterComponent = { modifier -> sanityMeterComponent(modifier) },
                        timerComponent = { modifier -> timerComponent(modifier) }
                    )
                }
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
    modifier: Modifier = Modifier,
    investigationComponent: @Composable ColumnScope.(Modifier) -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top
    ) {
        investigationComponent(modifier.weight(1f, false))
    }
}

@Composable
private fun InvestigationContentLandscape(
    modifier: Modifier = Modifier,
    investigationComponent: @Composable RowScope.(Modifier) -> Unit = {}
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.Start
    ) {
        investigationComponent(Modifier)
    }
}

@Composable
private fun ColumnScope.Investigation(
    state: InvestigationUiState,
    actions: InvestigationUiActions,
    journalComponent: @Composable (Modifier) -> Unit,
    mapConfigComponent: @Composable (Modifier) -> Unit,
    difficultyConfigComponent: @Composable (Modifier) -> Unit,
    sanityMeterComponent: @Composable (Modifier) -> Unit,
    timerComponent: @Composable (Modifier) -> Unit
) {

    val toolbarComponent: @Composable (Modifier) -> Unit = { modifier ->
        OperationToolbar(
            modifier = modifier
                .heightIn(min = 48.dp),
            toolbarUiState = state.toolbarUiState,
            toolbarUiActions = actions.toolbarUi,
            containerColor = LocalPalette.current.surfaceContainerHigh
        )
    }

    journalComponent(Modifier.weight(1f, false))

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
                    mapConfigComponent = { modifier -> mapConfigComponent(modifier) },
                    difficultyConfigComponent = { modifier ->
                        difficultyConfigComponent(modifier) },
                    sanityMeterComponent = { modifier -> sanityMeterComponent(modifier) },
                    timerComponent = { modifier -> timerComponent(modifier) },
                    phaseComponent = { modifier -> }
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

    ToolbarBottomSheet(
        modifier = Modifier
            .padding(8.dp),
        toolbarComponent = { modifier -> toolbarComponent(modifier) },
        content = { modifier -> primaryContent(modifier) }
    )

}

@Composable
private fun RowScope.Investigation(
    state: InvestigationUiState,
    actions: InvestigationUiActions,
    mapConfigComponent: @Composable (Modifier) -> Unit,
    difficultyConfigComponent: @Composable (Modifier) -> Unit,
    sanityMeterComponent: @Composable (Modifier) -> Unit,
    timerComponent: @Composable (Modifier) -> Unit,
    journalComponent: @Composable (Modifier) -> Unit
) {

    val toolbarContent: @Composable (Modifier) -> Unit = { modifier ->
        OperationToolRail(
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
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            when (state.toolbarUiState.category) {
                ToolbarUiState.Category.TOOL_NONE -> {}
                ToolbarUiState.Category.TOOL_CONFIG -> OperationConfigs(
                    modifier = Modifier
                        .width(IntrinsicSize.Max),
                    mapConfigComponent = { modifier -> mapConfigComponent(modifier) },
                    difficultyConfigComponent = { modifier ->
                        difficultyConfigComponent(modifier) },
                    sanityMeterComponent = { modifier -> sanityMeterComponent(modifier) },
                    timerComponent = { modifier -> timerComponent(modifier) },
                    phaseComponent = { modifier -> }
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
        toolRailComponent = { modifier -> toolbarContent(modifier) },
        content = { modifier -> primaryContent(modifier) }
    )

    journalComponent(Modifier)
}

@Composable
private fun ToolbarBottomSheet(
    modifier: Modifier = Modifier,
    toolbarComponent: @Composable (Modifier) -> Unit = {},
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
            toolbarComponent(
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
    toolRailComponent: @Composable (Modifier) -> Unit = {},
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

            toolRailComponent(
                Modifier
                    .widthIn(min = 48.dp)
            )

        }
    }
}

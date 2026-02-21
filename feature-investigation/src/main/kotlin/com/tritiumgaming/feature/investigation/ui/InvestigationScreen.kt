package com.tritiumgaming.feature.investigation.ui

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.impl.base.TruckTimeIcon
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.Holiday22
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
import com.tritiumgaming.feature.investigation.ui.section.timers.OperationTimers
import com.tritiumgaming.feature.investigation.ui.tool.timers.NotchedProgressBarBundle
import com.tritiumgaming.feature.investigation.ui.tool.timers.NotchedProgressBarUiColors
import com.tritiumgaming.feature.investigation.ui.tool.timers.NotchedProgressBarUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiActions
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.impl.OperationToolbar
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources

@Composable
@Preview
private fun InvestigationSoloScreenPreview() {
    SelectiveTheme(
        palette = Holiday22,
        typography = ClassicTypography
    ) {

        InvestigationSoloScreen(
            investigationViewModel = viewModel(factory = InvestigationScreenViewModel.Factory))

    }

}

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
    val mapUiState by investigationViewModel.mapUiState.collectAsStateWithLifecycle()
    val difficultyUiState by investigationViewModel.difficultyUiState.collectAsStateWithLifecycle()
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
        mapUiState = mapUiState,
        phaseUiState = phaseUiState,
        difficultyUiState = difficultyUiState,
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
            label = mapUiState.name.toStringResource(
                SimpleMapResources.MapTitleLength.ABBREVIATED)
        ),
        dropdownUiState = ConfigDropdownUiState(
            list = mapUiState.allMaps.map { it
                .toStringResource(SimpleMapResources.MapTitleLength.FULL) },
            label = mapUiState.name
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

    val smudgeHuntPreventionBundle = NotchedProgressBarBundle(
        title = "Smudge Hunt Protection",
        state = smudgeHuntProtectionTimerState,
        colors = NotchedProgressBarUiColors(
            remaining = LocalPalette.current.primary,
            background = LocalPalette.current.surface,
            border = LocalPalette.current.onSurface,
            notch = LocalPalette.current.onSurface,
            label = LocalPalette.current.onSurface,
        )
    )

    val smudgeBlindingBundle = NotchedProgressBarBundle(
        title = "Smudge Hunt Protection",
        state = smudgeHuntProtectionTimerState,
        colors = NotchedProgressBarUiColors(
            remaining = LocalPalette.current.primary,
            background = LocalPalette.current.surface,
            border = LocalPalette.current.onSurface,
            notch = LocalPalette.current.onSurface,
            label = LocalPalette.current.onSurface,
        )
    )

    val huntDurationBundle = NotchedProgressBarBundle(
        title = "Smudge Hunt Protection",
        state = smudgeHuntProtectionTimerState,
        colors = NotchedProgressBarUiColors(
            remaining = LocalPalette.current.primary,
            background = LocalPalette.current.surface,
            border = LocalPalette.current.onSurface,
            notch = LocalPalette.current.onSurface,
            label = LocalPalette.current.onSurface,
        )
    )

    val huntGapBundle = NotchedProgressBarBundle(
        title = "Smudge Hunt Protection",
        state = smudgeHuntProtectionTimerState,
        colors = NotchedProgressBarUiColors(
            remaining = LocalPalette.current.primary,
            background = LocalPalette.current.surface,
            border = LocalPalette.current.onSurface,
            notch = LocalPalette.current.onSurface,
            label = LocalPalette.current.onSurface,
        )
    )

    val investigationStateBundle = InvestigationStateBundle(
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

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    when(deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT,
        DeviceConfiguration.TABLET_PORTRAIT -> {
            InvestigationContentPortrait(
                investigationStateBundle,
                evidenceListUiActions,
                toolbarUiActions,
                ghostListUiActions,
                ghostListUiItemActions,
                bpmToolUiActions
            )
        }
        DeviceConfiguration.MOBILE_LANDSCAPE,
        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP -> {
            InvestigationContentLandscape(
                investigationStateBundle,
                evidenceListUiActions,
                toolbarUiActions,
                ghostListUiActions,
                ghostListUiItemActions,
                bpmToolUiActions
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
    investigationStateBundle: InvestigationStateBundle,
    evidenceListUiActions: EvidenceListUiActions,
    toolbarUiActions: ToolbarUiActions,
    ghostListUiActions: GhostListUiActions,
    ghostListUiItemActions: GhostListUiItemActions,
    bpmToolUiActions: BpmToolUiActions
) {
    Column(
        modifier = Modifier
            .padding(bottom = 8.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Investigation(
            investigationStateBundle = investigationStateBundle,
            evidenceListUiActions = evidenceListUiActions,
            toolbarUiActions = toolbarUiActions,
            ghostListUiActions = ghostListUiActions,
            ghostListUiItemActions = ghostListUiItemActions,
            bpmToolUiActions = bpmToolUiActions
        )
    }
}

@Composable
private fun InvestigationContentLandscape(
    investigationStateBundle: InvestigationStateBundle,
    evidenceListUiActions: EvidenceListUiActions,
    toolbarUiActions: ToolbarUiActions,
    ghostListUiActions: GhostListUiActions,
    ghostListUiItemActions: GhostListUiItemActions,
    bpmToolUiActions: BpmToolUiActions
) {
    Row(
        modifier = Modifier
            .padding(start = 8.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Investigation(
            investigationStateBundle = investigationStateBundle,
            evidenceListUiActions = evidenceListUiActions,
            toolbarUiActions = toolbarUiActions,
            ghostListUiActions = ghostListUiActions,
            ghostListUiItemActions = ghostListUiItemActions,
            bpmToolUiActions = bpmToolUiActions
        )
    }
}

@Composable
private fun ColumnScope.Investigation(
    investigationStateBundle: InvestigationStateBundle,
    evidenceListUiActions: EvidenceListUiActions,
    ghostListUiActions: GhostListUiActions,
    ghostListUiItemActions: GhostListUiItemActions,
    toolbarUiActions: ToolbarUiActions,
    bpmToolUiActions: BpmToolUiActions
) {

    val journalStateBundle = investigationStateBundle.journalStateBundle
    val toolbarUiState = investigationStateBundle.toolbarUiState

    Journal(
        modifier = Modifier
            .weight(1f, false),
        journalStateBundle = journalStateBundle,
        evidenceListUiActions = evidenceListUiActions,
        ghostListUiActions = ghostListUiActions,
        ghostListUiItemActions = ghostListUiItemActions
    )

    OperationToolbar(
        modifier = Modifier
            .widthIn(min = 48.dp),
        toolbarUiState = toolbarUiState,
        toolbarUiActions = toolbarUiActions
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize()
            .then(
                if (!toolbarUiState.isCollapsed)
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
        when(toolbarUiState.category) {
            ToolbarUiState.Category.TOOL_CONFIG -> OperationConfigs(
                modifier = Modifier
                    .height(IntrinsicSize.Max),
                mapConfigComponent = { modifier ->
                    MapConfigComponent(
                        modifier = modifier,
                        stateBundle = investigationStateBundle.mapUiStateBundle,
                        actionsBundle = investigationStateBundle.mapUiActionBundle,
                        isCompact = true
                    )
                },
                difficultyConfigComponent = { modifier ->
                    DifficultyConfigComponent(
                        modifier = modifier,
                        stateBundle = investigationStateBundle.difficultyUiStateBundle,
                        actionsBundle = investigationStateBundle.difficultyUiActionBundle,
                        isCompact = true
                    )
                },
                sanityMeterComponent = { modifier ->
                    SanityMeterComponent(
                        modifier = modifier,
                        sanityUiState = investigationStateBundle.sanityUiState
                    )
                },
                timerComponent = { modifier ->
                    TimerComponent(
                        modifier = modifier,
                        timerUiState = investigationStateBundle.timerUiState,
                        timerUiActions = investigationStateBundle.timerUiActions
                    )
                },
                phaseComponent = { modifier ->

                }
            )

            ToolbarUiState.Category.TOOL_ANALYZER -> OperationAnalysis(
                modifier = Modifier,
                operationDetailsUiState = investigationStateBundle.operationDetailsUiState
            )

            ToolbarUiState.Category.TOOL_TIMERS -> OperationTimers(
                modifier = Modifier,
                smudgeHuntPreventionBundle = investigationStateBundle.smudgeHuntPreventionBundle,
                smudgeBlindingBundle = investigationStateBundle.smudgeBlindingBundle,
                huntDurationBundle = investigationStateBundle.huntDurationBundle,
                huntGapBundle = investigationStateBundle.huntGapBundle
            )

            ToolbarUiState.Category.TOOL_FOOTSTEP -> BpmTool(
                modifier = Modifier,
                state = investigationStateBundle.bpmToolUiState,
                actions = bpmToolUiActions
            )

        }
    }
}

@Composable
private fun RowScope.Investigation(
    investigationStateBundle: InvestigationStateBundle,
    evidenceListUiActions: EvidenceListUiActions,
    ghostListUiActions: GhostListUiActions,
    ghostListUiItemActions: GhostListUiItemActions,
    toolbarUiActions: ToolbarUiActions,
    bpmToolUiActions: BpmToolUiActions
) {

    val journalStateBundle = investigationStateBundle.journalStateBundle
    val toolbarUiState = investigationStateBundle.toolbarUiState

    Column(
        modifier = Modifier
            .then(
                if (toolbarUiState.isCollapsed) Modifier
                    .animateContentSize()
                    .fillMaxWidth(0f)
                    .alpha(0f)
                else Modifier.fillMaxWidth(.35f)
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        when(toolbarUiState.category) {
            ToolbarUiState.Category.TOOL_CONFIG -> OperationConfigs(
                modifier = Modifier
                    .height(IntrinsicSize.Max),
                mapConfigComponent = { modifier ->

                },
                difficultyConfigComponent = { modifier ->

                },
                sanityMeterComponent = { modifier ->

                },
                timerComponent = { modifier ->

                },
                phaseComponent = { modifier ->

                }
            )
            ToolbarUiState.Category.TOOL_ANALYZER -> OperationAnalysis(
                modifier = Modifier
                    .wrapContentHeight(align = Alignment.Bottom),
                operationDetailsUiState = investigationStateBundle.operationDetailsUiState
            )

            ToolbarUiState.Category.TOOL_TIMERS -> OperationTimers(
                modifier = Modifier,
                smudgeHuntPreventionBundle = investigationStateBundle.smudgeHuntPreventionBundle,
                smudgeBlindingBundle = investigationStateBundle.smudgeBlindingBundle,
                huntDurationBundle = investigationStateBundle.huntDurationBundle,
                huntGapBundle = investigationStateBundle.huntGapBundle
            )

            ToolbarUiState.Category.TOOL_FOOTSTEP -> BpmTool(
                modifier = Modifier,
                state = investigationStateBundle.bpmToolUiState,
                actions = bpmToolUiActions
            )

        }
    }

    OperationToolbar(
        modifier = Modifier
            .heightIn(48.dp),
        toolbarUiState = toolbarUiState,
        toolbarUiActions = toolbarUiActions
    )

    Journal(
        modifier = Modifier,
        journalStateBundle = journalStateBundle,
        evidenceListUiActions = evidenceListUiActions,
        ghostListUiActions = ghostListUiActions,
        ghostListUiItemActions = ghostListUiItemActions,
    )
}

@Composable
private fun ColumnScope.TimerComponent(
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

    when(isCompact) {
        true -> {
            OperationConfigDropdown(
                modifier = modifier,
                state = stateBundle.dropdownUiState,
                primaryIcon = R.drawable.icon_nav_mapmenu2,
                textStyle = LocalTypography.current.secondary.regular,
                color = LocalPalette.current.onSurface,
                iconColorFilter = ColorFilter.tint(LocalPalette.current.onSurface),
                actions = actionsBundle.dropdownUiActions,
                containerColor = LocalPalette.current.surfaceContainer
            )
        }
        false -> {
            OperationConfigCarousel(
                modifier = Modifier,
                state = stateBundle.carouselUiState,
                primaryIcon = R.drawable.icon_nav_mapmenu2,
                textStyle = LocalTypography.current.secondary.regular,
                color = LocalPalette.current.onSurface,
                iconColorFilter = ColorFilter.tint(LocalPalette.current.onSurface),
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

    when(isCompact) {
        true -> {
            OperationConfigDropdown(
                modifier = modifier,
                state = stateBundle.dropdownUiState,
                primaryIcon = R.drawable.ic_puzzle,
                textStyle = LocalTypography.current.secondary.regular,
                color = LocalPalette.current.onSurface,
                iconColorFilter = ColorFilter.tint(LocalPalette.current.onSurface),
                actions = actionsBundle.dropdownUiActions,
                containerColor = LocalPalette.current.surfaceContainer
            )
        }
        false -> {
            OperationConfigCarousel(
                modifier = modifier,
                primaryIcon = R.drawable.ic_puzzle,
                state = stateBundle.carouselUiState,
                textStyle = LocalTypography.current.secondary.regular,
                color = LocalPalette.current.onSurface,
                iconColorFilter = ColorFilter.tint(LocalPalette.current.onSurface),
                actions = actionsBundle.carouselUiActions
            )
        }
    }
}
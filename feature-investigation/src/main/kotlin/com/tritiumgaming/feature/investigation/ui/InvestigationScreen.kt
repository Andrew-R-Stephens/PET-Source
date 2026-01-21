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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.Holiday22
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.feature.investigation.ui.common.analysis.OperationDetailsUiState
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.TimerUiState
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.difficulty.DifficultyUiState
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.map.MapUiState
import com.tritiumgaming.feature.investigation.ui.common.phase.PhaseUiState
import com.tritiumgaming.feature.investigation.ui.common.sanitymeter.PlayerSanityUiState
import com.tritiumgaming.feature.investigation.ui.journal.Journal
import com.tritiumgaming.feature.investigation.ui.journal.JournalUiState
import com.tritiumgaming.feature.investigation.ui.journal.lists.evidence.EvidenceListUiState
import com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.GhostListUiActions
import com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.GhostListUiState
import com.tritiumgaming.feature.investigation.ui.journal.lists.ghost.item.GhostListUiItemActions
import com.tritiumgaming.feature.investigation.ui.popups.common.InvestigationPopup
import com.tritiumgaming.feature.investigation.ui.popups.evidence.EvidencePopup
import com.tritiumgaming.feature.investigation.ui.popups.ghost.GhostPopup
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiActions
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.component.OperationConfigUiActions
import com.tritiumgaming.feature.investigation.ui.toolbarsection.OperationToolbar
import com.tritiumgaming.feature.investigation.ui.toolbarsection.ToolbarConfigurationSection
import com.tritiumgaming.feature.investigation.ui.toolbarsection.ToolbarFootsteps
import com.tritiumgaming.feature.investigation.ui.toolbarsection.ToolbarOperationAnalysis
import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.evidence.model.RuledEvidence

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

    InvestigationSoloContent(
        investigationViewModel = investigationViewModel
    )

}

@Composable
private fun InvestigationSoloContent(
    investigationViewModel: InvestigationScreenViewModel
) {

    val popupUiState by investigationViewModel.popupUiState.collectAsStateWithLifecycle()

    val toolbarUiState by investigationViewModel.toolbarUiState.collectAsStateWithLifecycle()

    val timerUiState by investigationViewModel.timerUiState.collectAsStateWithLifecycle()
    val phaseUiState by investigationViewModel.phaseUiState.collectAsStateWithLifecycle()
    val mapUiState by investigationViewModel.mapUiState.collectAsStateWithLifecycle()
    val difficultyUiState by investigationViewModel.difficultyUiState.collectAsStateWithLifecycle()
    val sanityUiState by investigationViewModel.playerSanityUiState.collectAsStateWithLifecycle()

    val ghostScores by investigationViewModel.ghostScores.collectAsStateWithLifecycle()
    val ghostOrder by investigationViewModel.ghostOrder.collectAsStateWithLifecycle()
    val ruledEvidence by investigationViewModel.ruledEvidence.collectAsStateWithLifecycle()

    val journalUiState = JournalUiState(
        rtlPreference = investigationViewModel.rTLPreference
    )

    val ghostListUiState = GhostListUiState(
        ghostScores = ghostScores,
        ghostOrder = ghostOrder,
        ruledEvidence = ruledEvidence
    )

    val evidenceListUiState = EvidenceListUiState(
        ruledEvidenceList = ruledEvidence
    )

    val ghostListUiActions = GhostListUiActions(
        onFindGhostById = { ghostId ->
            investigationViewModel.getGhostById(ghostId)
        },
        onNameClick = { ghostType -> investigationViewModel.setPopup(ghostType) }
    )

    val ghostListUiItemActions = GhostListUiItemActions(
        onGetRuledEvidence = { evidenceType ->
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
        ghostScores = ghostScores,
        ghostOrder = ghostOrder,
        ruledEvidence = ruledEvidence
    )

    val toolbarUiActions = ToolbarUiActions(
        onToggleCollapseToolbar = { investigationViewModel.toggleToolbarState() },
        onChangeToolbarCategory = { category -> investigationViewModel.setToolbarCategory(category)
        },
        onReset = { investigationViewModel.reset() }
    )

    val operationConfigUiActions = OperationConfigUiActions(
        onMapLeftClick = { investigationViewModel.decrementMapIndex() },
        onMapRightClick = { investigationViewModel.incrementMapIndex() },
        onDifficultyLeftClick = { investigationViewModel.decrementDifficultyIndex() },
        onDifficultyRightClick = { investigationViewModel.incrementDifficultyIndex() },
        onToggleTimer = { investigationViewModel.toggleTimer() },
    )

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    when(deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT,
        DeviceConfiguration.TABLET_PORTRAIT -> {
            InvestigationSoloContentPortrait(
                toolbarUiState,
                journalUiState,
                timerUiState,
                phaseUiState,
                mapUiState,
                difficultyUiState,
                sanityUiState,
                ghostListUiState,
                evidenceListUiState,
                investigationViewModel,
                operationConfigUiActions,
                toolbarUiActions,
                ghostListUiActions,
                ghostListUiItemActions,
                operationDetailsUiState
            )
        }
        DeviceConfiguration.MOBILE_LANDSCAPE,
        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP -> {
            InvestigationSoloContentLandscape(
                toolbarUiState,
                journalUiState,
                timerUiState,
                phaseUiState,
                mapUiState,
                difficultyUiState,
                sanityUiState,
                ghostListUiState,
                evidenceListUiState,
                investigationViewModel,
                operationConfigUiActions,
                toolbarUiActions,
                ghostListUiActions,
                ghostListUiItemActions,
                operationDetailsUiState
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
private fun InvestigationSoloContentLandscape(
    toolbarUiState: ToolbarUiState,
    journalUiState: JournalUiState,
    timerUiState: TimerUiState,
    phaseUiState: PhaseUiState,
    mapUiState: MapUiState,
    difficultyUiState: DifficultyUiState,
    sanityUiState: PlayerSanityUiState,
    ghostListUiState: GhostListUiState,
    evidenceListUiState: EvidenceListUiState,
    investigationViewModel: InvestigationScreenViewModel,
    operationConfigUiActions: OperationConfigUiActions,
    toolbarUiActions: ToolbarUiActions,
    ghostListUiActions: GhostListUiActions,
    ghostListUiItemActions: GhostListUiItemActions,
    operationDetailsUiState: OperationDetailsUiState
) {
    Row(
        modifier = Modifier
            .padding(start = 8.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Investigation(
            toolbarUiState = toolbarUiState,
            journalUiState = journalUiState,
            timerUiState = timerUiState,
            phaseUiState = phaseUiState,
            mapUiState = mapUiState,
            difficultyUiState = difficultyUiState,
            sanityUiState = sanityUiState,
            ghostListUiState = ghostListUiState,
            evidenceListUiState = evidenceListUiState,
            onChangeEvidenceRuling = { e, r ->
                investigationViewModel.setEvidenceRuling(e, r)
            },
            onChangeEvidencePopup = { investigationViewModel.setPopup(it) },
            operationConfigUiActions = operationConfigUiActions,
            toolbarUiActions = toolbarUiActions,
            ghostListUiActions = ghostListUiActions,
            ghostListUiItemActions = ghostListUiItemActions,
            operationDetailsUiState = operationDetailsUiState
        )
    }
}

@Composable
private fun InvestigationSoloContentPortrait(
    toolbarUiState: ToolbarUiState,
    journalUiState: JournalUiState,
    timerUiState: TimerUiState,
    phaseUiState: PhaseUiState,
    mapUiState: MapUiState,
    difficultyUiState: DifficultyUiState,
    sanityUiState: PlayerSanityUiState,
    ghostListUiState: GhostListUiState,
    evidenceListUiState: EvidenceListUiState,
    investigationViewModel: InvestigationScreenViewModel,
    operationConfigUiActions: OperationConfigUiActions,
    toolbarUiActions: ToolbarUiActions,
    ghostListUiActions: GhostListUiActions,
    ghostListUiItemActions: GhostListUiItemActions,
    operationDetailsUiState: OperationDetailsUiState
) {
    Column(
        modifier = Modifier
            .padding(bottom = 8.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Investigation(
            toolbarUiState = toolbarUiState,
            journalUiState = journalUiState,
            timerUiState = timerUiState,
            phaseUiState = phaseUiState,
            mapUiState = mapUiState,
            difficultyUiState = difficultyUiState,
            sanityUiState = sanityUiState,
            ghostListUiState = ghostListUiState,
            evidenceListUiState = evidenceListUiState,
            onChangeEvidenceRuling = { e, r ->
                investigationViewModel.setEvidenceRuling(e, r)
            },
            onChangeEvidencePopup = { investigationViewModel.setPopup(it) },
            operationConfigUiActions = operationConfigUiActions,
            toolbarUiActions = toolbarUiActions,
            ghostListUiActions = ghostListUiActions,
            ghostListUiItemActions = ghostListUiItemActions,
            operationDetailsUiState = operationDetailsUiState
        )
    }
}

@Composable
private fun ColumnScope.Investigation(
    toolbarUiState: ToolbarUiState,
    journalUiState: JournalUiState,
    timerUiState: TimerUiState,
    phaseUiState: PhaseUiState,
    mapUiState: MapUiState,
    difficultyUiState: DifficultyUiState,
    sanityUiState: PlayerSanityUiState,
    ghostListUiState: GhostListUiState,
    evidenceListUiState: EvidenceListUiState,
    onChangeEvidenceRuling: (EvidenceType, RuledEvidence.Ruling) -> Unit,
    onChangeEvidencePopup: (EvidenceType) -> Unit,
    operationConfigUiActions: OperationConfigUiActions,
    ghostListUiActions: GhostListUiActions,
    ghostListUiItemActions: GhostListUiItemActions,
    toolbarUiActions: ToolbarUiActions,
    operationDetailsUiState: OperationDetailsUiState
) {

    Journal(
        modifier = Modifier
            .weight(1f, false),
        journalUiState = journalUiState,
        ghostListUiState = ghostListUiState,
        evidenceListUiState = evidenceListUiState,
        onChangeEvidenceRuling = { e, r -> onChangeEvidenceRuling(e, r) },
        onChangeEvidencePopup = { onChangeEvidencePopup(it) },
        ghostListUiActions = ghostListUiActions,
        ghostListUiItemActions = ghostListUiItemActions
    )

    OperationToolbar(
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
            ToolbarUiState.Category.TOOL_CONFIG -> ToolbarConfigurationSection(
                modifier = Modifier
                    .height(IntrinsicSize.Max),
                timerUiState = timerUiState,
                mapUiState = mapUiState,
                difficultyUiState = difficultyUiState,
                sanityUiState = sanityUiState,
                operationConfigUiActions = operationConfigUiActions
            )

            ToolbarUiState.Category.TOOL_ANALYZER -> ToolbarOperationAnalysis(
                modifier = Modifier,
                    //.fillMaxHeight(.5f),
                operationDetailsUiState = operationDetailsUiState
            )

            ToolbarUiState.Category.TOOL_FOOTSTEP -> ToolbarFootsteps(
                modifier = Modifier
            )

        }
    }
}

@Composable
private fun RowScope.Investigation(
    toolbarUiState: ToolbarUiState,
    journalUiState: JournalUiState,
    timerUiState: TimerUiState,
    phaseUiState: PhaseUiState,
    mapUiState: MapUiState,
    difficultyUiState: DifficultyUiState,
    ghostListUiState: GhostListUiState,
    evidenceListUiState: EvidenceListUiState,
    sanityUiState: PlayerSanityUiState,
    onChangeEvidenceRuling: (EvidenceType, RuledEvidence.Ruling) -> Unit,
    onChangeEvidencePopup: (EvidenceType) -> Unit,
    operationConfigUiActions: OperationConfigUiActions,
    ghostListUiActions: GhostListUiActions,
    ghostListUiItemActions: GhostListUiItemActions,
    toolbarUiActions: ToolbarUiActions,
    operationDetailsUiState: OperationDetailsUiState,
) {

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
            ToolbarUiState.Category.TOOL_CONFIG -> ToolbarConfigurationSection(
                modifier = Modifier
                    .height(IntrinsicSize.Max),
                timerUiState = timerUiState,
                mapUiState = mapUiState,
                difficultyUiState = difficultyUiState,
                sanityUiState = sanityUiState,
                operationConfigUiActions = operationConfigUiActions
            )
            ToolbarUiState.Category.TOOL_ANALYZER -> ToolbarOperationAnalysis(
                modifier = Modifier
                    .wrapContentHeight(align = Alignment.Bottom),
                operationDetailsUiState = operationDetailsUiState
            )
            ToolbarUiState.Category.TOOL_FOOTSTEP -> ToolbarFootsteps(
                modifier = Modifier
            )

        }
    }

    OperationToolbar(
        modifier = Modifier
            .width(48.dp),
        toolbarUiState = toolbarUiState,
        toolbarUiActions = toolbarUiActions
    )

    Journal(
        modifier = Modifier,
        journalUiState = journalUiState,
        ghostListUiState = ghostListUiState,
        evidenceListUiState = evidenceListUiState,
        onChangeEvidenceRuling = { e, r -> onChangeEvidenceRuling(e, r) },
        onChangeEvidencePopup = { onChangeEvidencePopup(it) },
        ghostListUiActions = ghostListUiActions,
        ghostListUiItemActions = ghostListUiItemActions,
    )
}

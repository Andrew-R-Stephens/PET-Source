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
import androidx.compose.foundation.layout.widthIn
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
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.OperationConfigUiActions
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
import com.tritiumgaming.feature.investigation.ui.section.configs.ToolbarSectionOperationConfigs
import com.tritiumgaming.feature.investigation.ui.section.footstep.ToolbarSectionBpmVisualizer
import com.tritiumgaming.feature.investigation.ui.section.analysis.ToolbarSectionOperationAnalysis
import com.tritiumgaming.feature.investigation.ui.section.footstep.ToolbarSectionBpmVisualizerUiActions
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiActions
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.impl.OperationToolbar

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

    val ghostScores by investigationViewModel.ghostScores.collectAsStateWithLifecycle()
    val ghostOrder by investigationViewModel.ghostOrder.collectAsStateWithLifecycle()
    val ruledEvidence by investigationViewModel.ruledEvidence.collectAsStateWithLifecycle()

    val toolbarFootstepsVisualizerSectionUiState by investigationViewModel
        .footstepVisualizerUiState.collectAsStateWithLifecycle()

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
        onFindGhostById = { ghostId -> investigationViewModel.getGhostById(ghostId) },
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

    val evidenceListUiActions = EvidenceListUiActions(
        onChangeEvidenceRuling = { e, r ->
            investigationViewModel.setEvidenceRuling(e, r) },
        onClickItem = { investigationViewModel.setPopup(it) },
    )

    val toolbarSectionBpmVisualizerUiActions = ToolbarSectionBpmVisualizerUiActions(
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

    val investigationStateBundle = InvestigationStateBundle(
        toolbarUiState = toolbarUiState,
        journalStateBundle = JournalStateBundle(
            journalUiState = journalUiState,
            evidenceListUiState = evidenceListUiState,
            ghostListUiState = ghostListUiState
        ),
        timerUiState = timerUiState,
        phaseUiState = phaseUiState,
        mapUiState = mapUiState,
        difficultyUiState = difficultyUiState,
        sanityUiState = sanityUiState,
        ghostListUiState = ghostListUiState,
        evidenceListUiState = evidenceListUiState,
        operationDetailsUiState = operationDetailsUiState,
        toolbarSectionBpmVisualizerUiState = toolbarFootstepsVisualizerSectionUiState,
        toolbarSectionBpmVisualizerUiActions = toolbarSectionBpmVisualizerUiActions
    )

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    when(deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT,
        DeviceConfiguration.TABLET_PORTRAIT -> {
            InvestigationContentPortrait(
                investigationStateBundle,
                evidenceListUiActions,
                operationConfigUiActions,
                toolbarUiActions,
                ghostListUiActions,
                ghostListUiItemActions,
                toolbarSectionBpmVisualizerUiActions
            )
        }
        DeviceConfiguration.MOBILE_LANDSCAPE,
        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP -> {
            InvestigationContentLandscape(
                investigationStateBundle,
                evidenceListUiActions,
                operationConfigUiActions,
                toolbarUiActions,
                ghostListUiActions,
                ghostListUiItemActions,
                toolbarSectionBpmVisualizerUiActions
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
    operationConfigUiActions: OperationConfigUiActions,
    toolbarUiActions: ToolbarUiActions,
    ghostListUiActions: GhostListUiActions,
    ghostListUiItemActions: GhostListUiItemActions,
    toolbarSectionBpmVisualizerUiActions: ToolbarSectionBpmVisualizerUiActions
) {
    Column(
        modifier = Modifier
            .padding(bottom = 8.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Investigation(
            investigationStateBundle = investigationStateBundle,
            evidenceListUiActions = evidenceListUiActions,
            operationConfigUiActions = operationConfigUiActions,
            toolbarUiActions = toolbarUiActions,
            ghostListUiActions = ghostListUiActions,
            ghostListUiItemActions = ghostListUiItemActions,
            toolbarSectionBpmVisualizerUiActions = toolbarSectionBpmVisualizerUiActions
        )
    }
}

@Composable
private fun InvestigationContentLandscape(
    investigationStateBundle: InvestigationStateBundle,
    evidenceListUiActions: EvidenceListUiActions,
    operationConfigUiActions: OperationConfigUiActions,
    toolbarUiActions: ToolbarUiActions,
    ghostListUiActions: GhostListUiActions,
    ghostListUiItemActions: GhostListUiItemActions,
    toolbarSectionBpmVisualizerUiActions: ToolbarSectionBpmVisualizerUiActions
) {
    Row(
        modifier = Modifier
            .padding(start = 8.dp),
        horizontalArrangement = Arrangement.Start
    ) {
        Investigation(
            investigationStateBundle = investigationStateBundle,
            evidenceListUiActions = evidenceListUiActions,
            operationConfigUiActions = operationConfigUiActions,
            toolbarUiActions = toolbarUiActions,
            ghostListUiActions = ghostListUiActions,
            ghostListUiItemActions = ghostListUiItemActions,
            toolbarSectionBpmVisualizerUiActions = toolbarSectionBpmVisualizerUiActions
        )
    }
}

@Composable
private fun ColumnScope.Investigation(
    investigationStateBundle: InvestigationStateBundle,
    evidenceListUiActions: EvidenceListUiActions,
    operationConfigUiActions: OperationConfigUiActions,
    ghostListUiActions: GhostListUiActions,
    ghostListUiItemActions: GhostListUiItemActions,
    toolbarUiActions: ToolbarUiActions,
    toolbarSectionBpmVisualizerUiActions: ToolbarSectionBpmVisualizerUiActions

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
            ToolbarUiState.Category.TOOL_CONFIG -> ToolbarSectionOperationConfigs(
                modifier = Modifier
                    .height(IntrinsicSize.Max),
                timerUiState = investigationStateBundle.timerUiState,
                mapUiState = investigationStateBundle.mapUiState,
                difficultyUiState = investigationStateBundle.difficultyUiState,
                sanityUiState = investigationStateBundle.sanityUiState,
                operationConfigUiActions = operationConfigUiActions
            )

            ToolbarUiState.Category.TOOL_ANALYZER -> ToolbarSectionOperationAnalysis(
                modifier = Modifier,
                operationDetailsUiState = investigationStateBundle.operationDetailsUiState
            )

            ToolbarUiState.Category.TOOL_FOOTSTEP -> ToolbarSectionBpmVisualizer(
                modifier = Modifier,
                state = investigationStateBundle.toolbarSectionBpmVisualizerUiState,
                actions = toolbarSectionBpmVisualizerUiActions
            )

        }
    }
}

@Composable
private fun RowScope.Investigation(
    investigationStateBundle: InvestigationStateBundle,
    evidenceListUiActions: EvidenceListUiActions,
    operationConfigUiActions: OperationConfigUiActions,
    ghostListUiActions: GhostListUiActions,
    ghostListUiItemActions: GhostListUiItemActions,
    toolbarUiActions: ToolbarUiActions,
    toolbarSectionBpmVisualizerUiActions: ToolbarSectionBpmVisualizerUiActions
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
            ToolbarUiState.Category.TOOL_CONFIG -> ToolbarSectionOperationConfigs(
                modifier = Modifier
                    .height(IntrinsicSize.Max),
                timerUiState = investigationStateBundle.timerUiState,
                mapUiState = investigationStateBundle.mapUiState,
                difficultyUiState = investigationStateBundle.difficultyUiState,
                sanityUiState = investigationStateBundle.sanityUiState,
                operationConfigUiActions = operationConfigUiActions
            )
            ToolbarUiState.Category.TOOL_ANALYZER -> ToolbarSectionOperationAnalysis(
                modifier = Modifier
                    .wrapContentHeight(align = Alignment.Bottom),
                operationDetailsUiState = investigationStateBundle.operationDetailsUiState
            )
            ToolbarUiState.Category.TOOL_FOOTSTEP -> ToolbarSectionBpmVisualizer(
                modifier = Modifier,
                state = investigationStateBundle.toolbarSectionBpmVisualizerUiState,
                actions = toolbarSectionBpmVisualizerUiActions
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

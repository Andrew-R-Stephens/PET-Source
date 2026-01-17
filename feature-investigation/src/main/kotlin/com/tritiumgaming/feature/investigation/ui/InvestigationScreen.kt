package com.tritiumgaming.feature.investigation.ui

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.FrameRateCategory
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.preferredFrameRate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.button.CollapseButton
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.Holiday22
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.ClassicTypography
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.core.ui.vector.getExitVector
import com.tritiumgaming.core.ui.vector.getGearVector
import com.tritiumgaming.core.ui.vector.getInfoVector
import com.tritiumgaming.feature.investigation.app.mappers.difficulty.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.map.toStringResource
import com.tritiumgaming.feature.investigation.ui.journal.Journal
import com.tritiumgaming.feature.investigation.ui.journal.lists.item.GhostScore
import com.tritiumgaming.feature.investigation.ui.popups.common.InvestigationPopup
import com.tritiumgaming.feature.investigation.ui.popups.evidence.EvidencePopup
import com.tritiumgaming.feature.investigation.ui.popups.ghost.GhostPopup
import com.tritiumgaming.feature.investigation.ui.toolbar.InvestigationToolbar
import com.tritiumgaming.feature.investigation.ui.toolbar.ResetButton
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarItem
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.analysis.OperationDetails
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.footstep.FootstepVisualizer
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.operationconfig.OperationConfigCarousel
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.operationconfig.difficulty.DifficultyUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.operationconfig.map.MapUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.phase.PhaseUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.sanity.PlayerSanityUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.sanity.SanityMeter
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.timer.DigitalTimer
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.timer.TimerToggleButton
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.timer.TimerUiState
import com.tritiumgaming.shared.data.evidence.model.EvidenceType
import com.tritiumgaming.shared.data.evidence.model.RuledEvidence
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources
import com.tritiumgaming.shared.data.ghost.model.GhostType
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources
import kotlin.time.Duration.Companion.seconds

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

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    /*
    when(deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT -> { StartContentCompactPortrait(
            navController = navController) }
        DeviceConfiguration.MOBILE_LANDSCAPE -> { StartContentCompactLandscape(
            navController = navController) }
        DeviceConfiguration.TABLET_PORTRAIT -> { StartContentCompactPortrait(
            navController = navController) }
        DeviceConfiguration.TABLET_LANDSCAPE -> { StartContentCompactLandscape(
            navController = navController) }
        DeviceConfiguration.DESKTOP -> { StartContentCompactLandscape(
            navController = navController) }
    }*/

    if(LocalConfiguration.current.orientation == ORIENTATION_PORTRAIT) {
        Column(
            modifier = Modifier
                .padding(bottom = 8.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Investigation(
                toolbarUiState = toolbarUiState,
                timerUiState = timerUiState,
                phaseUiState = phaseUiState,
                mapUiState = mapUiState,
                difficultyUiState = difficultyUiState,
                sanityUiState = sanityUiState,
                ruledEvidence = ruledEvidence,
                ghostScores = ghostScores,
                ghostOrder = ghostOrder,
                onChangeEvidenceRuling = { e, r ->
                    investigationViewModel.setEvidenceRuling(e, r) },
                onChangeEvidencePopup = { investigationViewModel.setPopup(it) },
                onChangeGhostPopup = { investigationViewModel.setPopup(it) },
                onMapLeftClick = { investigationViewModel.decrementMapIndex() },
                onMapRightClick = { investigationViewModel.incrementMapIndex() },
                onDifficultyLeftClick = { investigationViewModel.decrementDifficultyIndex() },
                onDifficultyRightClick = { investigationViewModel.incrementDifficultyIndex() },
                onToggleTimer = { investigationViewModel.toggleTimer() },
                onToggleCollapseToolbar = { investigationViewModel.toggleToolbarState() },
                onChangeToolbarCategory = { category ->
                    investigationViewModel.setToolbarCategory(category)
                },
                onReset = { investigationViewModel.reset() }
            )
        }
    } else {
        Row(
            modifier = Modifier
                .padding(start = 8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Investigation(
                toolbarUiState = toolbarUiState,
                timerUiState = timerUiState,
                phaseUiState = phaseUiState,
                mapUiState = mapUiState,
                difficultyUiState = difficultyUiState,
                sanityUiState = sanityUiState,
                ruledEvidence = ruledEvidence,
                ghostScores = ghostScores,
                ghostOrder = ghostOrder,
                onChangeEvidenceRuling = { e, r ->
                    investigationViewModel.setEvidenceRuling(e, r) },
                onChangeEvidencePopup = { investigationViewModel.setPopup(it) },
                onChangeGhostPopup = { investigationViewModel.setPopup(it) },
                onMapLeftClick = { investigationViewModel.decrementMapIndex() },
                onMapRightClick = { investigationViewModel.incrementMapIndex() },
                onDifficultyLeftClick = { investigationViewModel.decrementDifficultyIndex() },
                onDifficultyRightClick = { investigationViewModel.incrementDifficultyIndex() },
                onToggleTimer = { investigationViewModel.toggleTimer() },
                onToggleCollapseToolbar = { investigationViewModel.toggleToolbarState() },
                onChangeToolbarCategory = { category ->
                    investigationViewModel.setToolbarCategory(category)
                },
                onReset = { investigationViewModel.reset() }
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
private fun ColumnScope.Investigation(
    toolbarUiState: ToolbarUiState,
    timerUiState: TimerUiState,
    phaseUiState: PhaseUiState,
    mapUiState: MapUiState,
    difficultyUiState: DifficultyUiState,
    sanityUiState: PlayerSanityUiState,
    ruledEvidence: List<RuledEvidence>,
    ghostScores: List<GhostScore>,
    ghostOrder: List<GhostResources.GhostIdentifier>,
    onChangeEvidenceRuling: (EvidenceType, RuledEvidence.Ruling) -> Unit,
    onChangeEvidencePopup: (EvidenceType) -> Unit,
    onChangeGhostPopup: (GhostType) -> Unit,
    onToggleCollapseToolbar: () -> Unit = {},
    onChangeToolbarCategory: (ToolbarUiState.Category) -> Unit = {},
    onReset: () -> Unit = {},
    onMapLeftClick: () -> Unit = {},
    onMapRightClick: () -> Unit = {},
    onDifficultyLeftClick: () -> Unit = {},
    onDifficultyRightClick: () -> Unit = {},
    onToggleTimer: () -> Unit = {}
) {

    Journal(
        modifier = Modifier
            .weight(1f, false),
        investigationViewModel = viewModel(factory = InvestigationScreenViewModel.Factory),
        ruledEvidenceList = ruledEvidence,
        ghostsScore = ghostScores,
        ghostsOrder = ghostOrder,
        onChangeEvidenceRuling = { e, r -> onChangeEvidenceRuling(e, r) },
        onChangeEvidencePopup = { onChangeEvidencePopup(it) },
        onChangeGhostPopup = { onChangeGhostPopup(it) },
    )

    OperationToolbar(
        modifier = Modifier
            .height(48.dp),
        toolbarUiState = toolbarUiState,
        onToggleCollapseToolbar = { onToggleCollapseToolbar() },
        onChangeToolbarCategory = { category -> onChangeToolbarCategory(category) },
        onReset = { onReset() }
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
                onMapLeftClick = { onMapLeftClick() },
                onMapRightClick = { onMapRightClick() },
                onDifficultyLeftClick = { onDifficultyLeftClick() },
                onDifficultyRightClick = { onDifficultyRightClick() },
                onToggleTimer = { onToggleTimer() }
            )

            ToolbarUiState.Category.TOOL_ANALYZER -> ToolbarOperationAnalysis(
                modifier = Modifier,
                    //.fillMaxHeight(.5f),
                phaseUiState = phaseUiState,
                mapUiState = mapUiState,
                difficultyUiState = difficultyUiState,
                ghostScores = ghostScores
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
    timerUiState: TimerUiState,
    phaseUiState: PhaseUiState,
    mapUiState: MapUiState,
    difficultyUiState: DifficultyUiState,
    sanityUiState: PlayerSanityUiState,
    ruledEvidence: List<RuledEvidence>,
    ghostScores: List<GhostScore>,
    ghostOrder: List<GhostResources.GhostIdentifier>,
    onChangeEvidenceRuling: (EvidenceType, RuledEvidence.Ruling) -> Unit,
    onChangeEvidencePopup: (EvidenceType) -> Unit,
    onChangeGhostPopup: (GhostType) -> Unit,
    onToggleCollapseToolbar: () -> Unit = {},
    onChangeToolbarCategory: (ToolbarUiState.Category) -> Unit = {},
    onReset: () -> Unit = {},
    onMapLeftClick: () -> Unit = {},
    onMapRightClick: () -> Unit = {},
    onDifficultyLeftClick: () -> Unit = {},
    onDifficultyRightClick: () -> Unit = {},
    onToggleTimer: () -> Unit = {}
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
                onMapLeftClick = { onMapLeftClick() },
                onMapRightClick = { onMapRightClick() },
                onDifficultyLeftClick = { onDifficultyLeftClick() },
                onDifficultyRightClick = { onDifficultyRightClick() },
                onToggleTimer = { onToggleTimer() }
            )
            ToolbarUiState.Category.TOOL_ANALYZER -> ToolbarOperationAnalysis(
                modifier = Modifier
                    .wrapContentHeight(align = Alignment.Bottom),
                phaseUiState = phaseUiState,
                mapUiState = mapUiState,
                difficultyUiState = difficultyUiState,
                ghostScores = ghostScores,
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
        onToggleCollapseToolbar = { onToggleCollapseToolbar() },
        onChangeToolbarCategory = { category ->
            onChangeToolbarCategory(category) },
        onReset = { onReset() }
    )

    Journal(
        modifier = Modifier,
        investigationViewModel = viewModel(factory = InvestigationScreenViewModel.Factory),
        ruledEvidenceList = ruledEvidence,
        ghostsScore = ghostScores,
        ghostsOrder = ghostOrder,
        onChangeEvidenceRuling = { e, r -> onChangeEvidenceRuling(e, r) },
        onChangeEvidencePopup = { onChangeEvidencePopup(it) },
        onChangeGhostPopup = { onChangeGhostPopup(it) },
    )
}

@Composable
private fun ColumnScope.ToolbarConfigurationSection(
    modifier: Modifier = Modifier,
    timerUiState: TimerUiState,
    mapUiState: MapUiState,
    difficultyUiState: DifficultyUiState,
    sanityUiState: PlayerSanityUiState,
    onMapLeftClick: () -> Unit = {},
    onMapRightClick: () -> Unit = {},
    onDifficultyLeftClick: () -> Unit = {},
    onDifficultyRightClick: () -> Unit = {},
    onToggleTimer: () -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {

        OperationConfigCarousel(
            modifier = Modifier,
            primaryIcon = R.drawable.icon_nav_mapmenu2,
            label = stringResource(mapUiState.name.toStringResource(
                SimpleMapResources.MapTitleLength.ABBREVIATED)),
            textStyle = LocalTypography.current.secondary.regular,
            color = LocalPalette.current.onSurface,
            iconColorFilter = ColorFilter.tint(LocalPalette.current.onSurface),
            onClickLeft = { onMapLeftClick() },
            onClickRight = { onMapRightClick() }
        )

        OperationConfigCarousel(
            modifier = Modifier,
            primaryIcon = R.drawable.ic_puzzle,
            label = stringResource(difficultyUiState.name.toStringResource()),
            textStyle = LocalTypography.current.secondary.regular,
            color = LocalPalette.current.onSurface,
            iconColorFilter = ColorFilter.tint(LocalPalette.current.onSurface),
            onClickLeft = {
                onDifficultyLeftClick()
            },
            onClickRight = {
                onDifficultyRightClick()
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            SanityMeter(
                modifier = Modifier
                    .size(64.dp),
                sanityUiState = sanityUiState
            )
            
            Column(
                modifier = Modifier
                    .weight(1f, false)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    text = "Remaining Time",
                    style = LocalTypography.current.primary.regular,
                    color = LocalPalette.current.onSurface
                )

                DigitalTimer(
                    modifier = Modifier
                        .height(48.dp)
                        .padding(8.dp)
                        .fillMaxWidth(),
                    timerUiState = timerUiState
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f, false)
            ) {
                TimerToggleButton(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(start = 4.dp),
                    timerUiState = timerUiState,
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
                            modifier = modifier
                                .size(48.dp),
                            painter = painterResource(R.drawable.ic_control_pause),
                            contentDescription = null,
                            tint = LocalPalette.current.onSurface
                        )
                    }
                ) {
                    onToggleTimer()
                }
            }
        }
    }
}

@Composable
private fun ToolbarFootsteps(
    modifier: Modifier = Modifier
) {
    Box (
        modifier = modifier
    ) {

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .preferredFrameRate(FrameRateCategory.Normal)
        ) {

            var rememberSmoothedBPM by remember {
                mutableFloatStateOf(0f)
            }

            var rememberInstantBPM by remember {
                mutableFloatStateOf(0f)
            }

            var rememberSampledBPM by remember {
                mutableFloatStateOf(0f)
            }

            FootstepVisualizer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                graphBackgroundColor = Color.Unspecified,
                graphXAxisColor = LocalPalette.current.onSurface,
                graphYAxisColor = Color.Unspecified,
                sampleBackgroundColor = LocalPalette.current.surfaceContainer.copy(alpha = .5f),
                labelColor = LocalPalette.current.onSurface,
                endpointColor = LocalPalette.current.primary,
                lineSegmentColor = LocalPalette.current.primary,
                meterBeatLineColor = LocalPalette.current.onSurface,
                meterColor = LocalPalette.current.onSurface,
                meterOnColor = LocalPalette.current.tertiary,
                alpha = .01f,
                viewportBPM = 360,
                viewportDuration = 10.seconds,
                durationSplit = 10f,
                bpmSplit = 120f,
                samplingInterval = 3.seconds
            ) { instantBPM, smoothedBPM, sampleAverage ->
                rememberSmoothedBPM = smoothedBPM
                rememberInstantBPM = instantBPM
                rememberSampledBPM = sampleAverage
            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = "BPM: ${rememberSmoothedBPM.toInt()}; IPM: ${rememberInstantBPM.toInt()}",
                color = LocalPalette.current.onSurface
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = "MSPS: ${rememberSmoothedBPM/60f}; MIPM: ${rememberInstantBPM/60f}",
                color = LocalPalette.current.onSurface
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = "AVG BPM: $rememberSampledBPM",
                color = LocalPalette.current.onSurface
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = "AVG MPS: ${rememberSampledBPM/60f}",
                color = LocalPalette.current.onSurface
            )

        }
    }

}

@Composable
private fun ToolbarTimer(
    modifier: Modifier = Modifier,
    timerUiState: TimerUiState
) {

    Box (
        modifier = modifier
    ) {
        DigitalTimer(
            timerUiState = timerUiState
        )
    }
}

@Composable
private fun ToolbarOperationAnalysis(
    modifier: Modifier = Modifier,
    phaseUiState: PhaseUiState,
    mapUiState: MapUiState,
    difficultyUiState: DifficultyUiState,
    ghostScores: List<GhostScore>
) {
    Box (
        modifier = modifier
    ) {
        OperationDetails(
            modifier = modifier,
            phaseUiState = phaseUiState,
            mapUiState = mapUiState,
            difficultyUiState = difficultyUiState,
            ghostScores = ghostScores
        )
    }
}

@Composable
private fun OperationToolbar(
    modifier: Modifier = Modifier,
    toolbarUiState: ToolbarUiState,
    onToggleCollapseToolbar: () -> Unit = {},
    onChangeToolbarCategory: (ToolbarUiState.Category) -> Unit = {},
    onReset: () -> Unit = {}
) {

    InvestigationToolbar(
        modifier = modifier
            .heightIn(min = 48.dp),
        stickyContentStart = {

            ToolbarItem(
                onClick = {}
            ){
                CollapseButton(
                    modifier = Modifier
                        .size(48.dp),
                    isCollapsed = toolbarUiState.isCollapsed,
                    icon = R.drawable.ic_arrow_chevron_right,
                    disabledRotationVertical = 90,
                    disabledRotationHorizontal = 90,
                    enabledRotationAddition = 180,
                    onClick = { onToggleCollapseToolbar() }
                )
            }

        },
        stickyContentEnd = {

            ToolbarItem(
                onClick = {}
            ){
                ResetButton {
                    onReset()
                }
            }

        }
    ) {

        ToolbarItem(
            onClick = {
                onChangeToolbarCategory(ToolbarUiState.Category.TOOL_CONFIG)
            }
        ){
            Image(
                modifier = modifier,
                imageVector = getGearVector(
                    if(toolbarUiState.category == ToolbarUiState.Category.TOOL_CONFIG) {
                        IconVectorColors.defaults(
                            fillColor = Color.Transparent,
                            strokeColor = LocalPalette.current.primary,
                        )
                    } else {
                        IconVectorColors.defaults(
                            fillColor =Color.Transparent,
                            strokeColor = LocalPalette.current.onSurface,
                        )
                    }
                ),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }

        ToolbarItem(
            onClick = {
                onChangeToolbarCategory(ToolbarUiState.Category.TOOL_ANALYZER)
            }
        ){
            Image(
                modifier = modifier,
                imageVector = getInfoVector(
                    if(toolbarUiState.category == ToolbarUiState.Category.TOOL_ANALYZER) {
                        IconVectorColors.defaults(
                            fillColor = Color.Transparent,
                            strokeColor = LocalPalette.current.primary,
                        )
                    } else {
                        IconVectorColors.defaults(
                            fillColor = Color.Transparent,
                            strokeColor = LocalPalette.current.onSurface,
                        )
                    }
                ),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }

        ToolbarItem(
            onClick = {
                onChangeToolbarCategory(ToolbarUiState.Category.TOOL_FOOTSTEP)
            }
        ){
            Image(
                modifier = modifier,
                imageVector = getExitVector(
                    if(toolbarUiState.category == ToolbarUiState.Category.TOOL_FOOTSTEP) {
                        IconVectorColors.defaults(
                            fillColor = LocalPalette.current.primary,
                            strokeColor = Color.Transparent,
                        )
                    } else {
                        IconVectorColors.defaults(
                            fillColor = Color.Transparent,
                            strokeColor = LocalPalette.current.onSurface,
                        )
                    }
                ),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }
    }
}
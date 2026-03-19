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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import com.tritiumgaming.core.ui.theme.LocalUiConfiguration
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarBundle
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarUiColors
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarUiState
import com.tritiumgaming.core.ui.widgets.progressbar.ProgressBarNotch
import com.tritiumgaming.feature.investigation.app.mappers.difficulty.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.map.toStringResource
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
import com.tritiumgaming.feature.investigation.ui.common.sanitymeter.SanityMeter
import com.tritiumgaming.feature.investigation.ui.journal.EvidenceListColumn
import com.tritiumgaming.feature.investigation.ui.journal.GhostListColumn
import com.tritiumgaming.feature.investigation.ui.journal.evidence.primary.EvidenceListUiActions
import com.tritiumgaming.feature.investigation.ui.journal.evidence.primary.EvidenceListUiState
import com.tritiumgaming.feature.investigation.ui.journal.ghost.GhostListUiActions
import com.tritiumgaming.feature.investigation.ui.journal.ghost.GhostListUiState
import com.tritiumgaming.feature.investigation.ui.journal.ghost.item.GhostListUiItemActions
import com.tritiumgaming.feature.investigation.ui.popups.common.InvestigationPopup
import com.tritiumgaming.feature.investigation.ui.popups.evidence.EvidencePopup
import com.tritiumgaming.feature.investigation.ui.popups.ghost.GhostPopup
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetails
import com.tritiumgaming.feature.investigation.ui.tool.footstep.BpmTool
import com.tritiumgaming.feature.investigation.ui.tool.footstep.BpmToolUiActions
import com.tritiumgaming.feature.investigation.ui.tool.timers.TimerTools
import com.tritiumgaming.feature.investigation.ui.tool.traits.TraitConfig
import com.tritiumgaming.feature.investigation.ui.tool.traits.TraitListItemUiColors
import com.tritiumgaming.feature.investigation.ui.tool.traits.TraitListUiActions
import com.tritiumgaming.feature.investigation.ui.tool.traits.TraitListUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiActions
import com.tritiumgaming.feature.investigation.ui.toolbar.operation.OperationToolRail
import com.tritiumgaming.feature.investigation.ui.toolbar.operation.OperationToolbar
import com.tritiumgaming.feature.investigation.ui.toolbar.operation.OperationToolbarUiState
import com.tritiumgaming.shared.data.investigation.model.TraitFilter
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources
import com.tritiumgaming.shared.data.preferences.properties.DensityType
import kotlinx.coroutines.launch

@Composable
fun InvestigationSoloScreen(
    investigationViewModel: InvestigationScreenViewModel
) {

    InvestigationContent(
        investigationViewModel = investigationViewModel
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InvestigationContent(
    investigationViewModel: InvestigationScreenViewModel
) {
    val uiDensityType = LocalUiConfiguration.current.densityType
    val uiIsRtl = LocalUiConfiguration.current.isRtl

    var showCustomDifficultyBottomSheet by remember { mutableStateOf(false) }

    val popupUiState by investigationViewModel.popupUiState.collectAsStateWithLifecycle()

    val toolbarUiState by investigationViewModel.primaryToolbarUiState.collectAsStateWithLifecycle()

    val timerUiState by investigationViewModel.timerUiState.collectAsStateWithLifecycle()
    val phaseUiState by investigationViewModel.phaseUiState.collectAsStateWithLifecycle()
    val mapConfigUiState by investigationViewModel.mapConfigUiState.collectAsStateWithLifecycle()
    val operationDetailsUiState by investigationViewModel.operationDetailsUiState.collectAsStateWithLifecycle()
    val difficultyUiState by investigationViewModel.difficultyConfigUiState.collectAsStateWithLifecycle()
    val sanityUiState by investigationViewModel.playerSanityUiState.collectAsStateWithLifecycle()
    val evidenceListUiStates by investigationViewModel.evidenceListUiState.collectAsStateWithLifecycle()

    val traitFilterOptions by investigationViewModel.filterOptionsUiState.collectAsStateWithLifecycle()
    val traitListUiStates by investigationViewModel.traitListUiState.collectAsStateWithLifecycle()

    val ghostStates by investigationViewModel.ghostStates.collectAsStateWithLifecycle()
    val ghostOrder by investigationViewModel.sortedGhosts.collectAsStateWithLifecycle()
    val evidenceStates by investigationViewModel.evidenceStates.collectAsStateWithLifecycle()

    // TODO val smudgeHuntPreventionState by investigationViewModel.smudgeHuntPreventionState.collectAsStateWithLifecycle()

    val bpmToolUiState by investigationViewModel
        .bpmToolUiState.collectAsStateWithLifecycle()

    val ghostListUiState = GhostListUiState(
        ghostStates = ghostStates,
        ghostOrder = ghostOrder,
        evidenceState = evidenceStates
    )

    val evidenceListUiState = EvidenceListUiState(
        evidenceStateList = evidenceListUiStates
    )

    val traitListUiState = TraitListUiState(
        options = traitFilterOptions,
        list = traitListUiStates
    )

    val traitListUiActions = TraitListUiActions(
        onSelectCategory = { category ->
            val filter = TraitFilter(
                category = category
            )
            investigationViewModel.updateTraitFilter(filter)
        },
        onSelectTrait = { trait ->
            investigationViewModel.toggleTraitSelection(trait)
        },
        onToggleUniqueOnly = {
            investigationViewModel.toggleUniqueOnly()
        }
    )

    val ghostListUiActions = GhostListUiActions(
        onFindGhostById = { ghostId -> investigationViewModel.getGhostById(ghostId) },
        onNameClick = { ghostType -> investigationViewModel.setPopup(ghostType) }
    )

    val ghostListUiItemActions = GhostListUiItemActions(
        onGetEvidenceState = { evidenceType ->
            investigationViewModel.getRuledEvidence(evidenceType)
        },
        onToggleNegateGhost = { ghost ->
            investigationViewModel.toggleExplicitNegation(ghost)
        },
    )

    val operationToolbarUiActions = ToolbarUiActions(
        onToggleCollapseToolbar = { investigationViewModel.toggleToolbarState() },
        onChangeToolbarCategory = { category -> investigationViewModel.setToolbarCategory(category)
        },
        onReset = { investigationViewModel.reset() }
    )

    val configSelectUiActions = ToolbarUiActions(
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
            investigationViewModel.setEvidenceRuling(e, r)
        },
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

    //TODO replace with viewmodel state
    val maxTimeFromSetting = 120000L
    val fingerprintTimerState = NotchedProgressBarUiState(
        max = maxTimeFromSetting,
        origin = 0,
        remaining = 50000L,
        notches = listOf(
            ProgressBarNotch("Obake", (maxTimeFromSetting * .5f).toLong()),
            ProgressBarNotch("Normal", maxTimeFromSetting)
        ),
        running = false
    )

    val mapUiStateBundle = ConfigStateBundle(
        carouselUiState = ConfigCarouselUiState(
            label = mapConfigUiState.name.toStringResource(
                SimpleMapResources.MapTitleLength.ABBREVIATED),
            enabled = mapConfigUiState.enabled
        ),
        dropdownUiState = ConfigDropdownUiState(
            options = mapConfigUiState.allMaps.map { it
                .toStringResource(SimpleMapResources.MapTitleLength.FULL) },
            enabled = mapConfigUiState.enabled,
            label = mapConfigUiState.name
                .toStringResource(SimpleMapResources.MapTitleLength.ABBREVIATED)
        )
    )

    val difficultyUiStateBundle = ConfigStateBundle(
        carouselUiState = ConfigCarouselUiState(
            label = difficultyUiState.name.toStringResource()
        ),
        dropdownUiState = ConfigDropdownUiState(
            options = difficultyUiState.allDifficulties.map { it.toStringResource() },
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
        title = "Smudge Blinding",
        state = smudgeBlindingProtectionTimerState,
        colors = notchedProgressBarUiColors
    )

    val huntDurationBundle = NotchedProgressBarBundle(
        title = "Hunt Duration",
        state = huntDurationTimerState,
        colors = notchedProgressBarUiColors
    )

    val huntGapBundle = NotchedProgressBarBundle(
        title = "Hunt Cooldown",
        state = huntGapTimerState,
        colors = notchedProgressBarUiColors
    )

    val fingerprintTimerBundle = NotchedProgressBarBundle(
        title = "Fingerprint Lifetime",
        state = fingerprintTimerState,
        colors = notchedProgressBarUiColors
    )

    val investigationUiState = InvestigationUiState(
        operationToolbarUiState = toolbarUiState
    )

    val investigationUiActions = InvestigationUiActions(
        evidenceListUi = evidenceListUiActions,
        ghostListUi = ghostListUiActions,
        ghostListItemUi = ghostListUiItemActions,
        toolbarUi = operationToolbarUiActions,
        bpmUi = bpmToolUiActions
    )

    val mapConfigComponent: @Composable (Modifier) -> Unit = { modifier ->

        val textStyle = LocalTypography.current.quaternary.regular
        val color = LocalPalette.current.surfaceContainer
        val onColor = LocalPalette.current.onSurface

        val icon: @Composable (Modifier) -> Unit = { modifier ->
            Image(
                modifier = modifier,
                contentScale = ContentScale.Inside,
                alignment = Alignment.Center,
                painter = painterResource(R.drawable.icon_nav_mapmenu2),
                colorFilter = ColorFilter.tint(onColor),
                contentDescription = ""
            )
        }

        when(uiDensityType) {
            DensityType.COMPACT -> {
                OperationConfigDropdown(
                    modifier = modifier,
                    icon = { icon(it) },
                    state = mapUiStateBundle.dropdownUiState,
                    actions = mapUiActions.dropdownUiActions,
                    textStyle = textStyle,
                    onColor = onColor,
                    expandedColor = color,
                )
            }
            else -> {
                OperationConfigCarousel(
                    modifier = modifier,
                    state = mapUiStateBundle.carouselUiState,
                    actions = mapUiActions.carouselUiActions,
                    leadingIcon = { icon(it) },
                    textStyle = textStyle,
                    onColor = onColor,
                )
            }
        }
    }
    val difficultyConfigComponent: @Composable (Modifier) -> Unit = { modifier ->

        val textStyle = LocalTypography.current.quaternary.regular
        val color = LocalPalette.current.surfaceContainer
        val onColor = LocalPalette.current.onSurface

        val icon: @Composable (Modifier) -> Unit = { modifier ->
            Image(
                modifier = modifier,
                contentScale = ContentScale.Inside,
                alignment = Alignment.Center,
                painter = painterResource(R.drawable.ic_puzzle),
                colorFilter = ColorFilter.tint(onColor),
                contentDescription = ""
            )
        }

        when(uiDensityType) {
            DensityType.COMPACT -> {
                OperationConfigDropdown(
                    modifier = modifier,
                    state = difficultyUiStateBundle.dropdownUiState,
                    actions = difficultyUiActions.dropdownUiActions,
                    icon = { icon(it) },
                    textStyle = textStyle,
                    expandedColor = color,
                    onColor = onColor,
                )
            }
            else -> {
                OperationConfigCarousel(
                    modifier = modifier,
                    state = difficultyUiStateBundle.carouselUiState,
                    actions = difficultyUiActions.carouselUiActions,
                    leadingIcon = { icon(it) },
                    textStyle = textStyle,
                    onColor = onColor,
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
                    .weight(1f),
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
        val evidenceListComponent: @Composable (Modifier) -> Unit = { modifier ->
            EvidenceListColumn(
                modifier = modifier,
                evidenceListUiState = evidenceListUiState,
                evidenceListUiActions = evidenceListUiActions
            )
        }

        val ghostListComponent: @Composable (Modifier) -> Unit = { modifier ->
            GhostListColumn(
                modifier = modifier,
                ghostListUiState = ghostListUiState,
                ghostListUiActions = ghostListUiActions,
                ghostListUiItemActions = ghostListUiItemActions
            )
        }

        Row(
            modifier = modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.Top
        ) {
            if(uiIsRtl) {
                evidenceListComponent(Modifier
                    .weight(1f)
                    .fillMaxSize())
                ghostListComponent(Modifier
                    .weight(1f, false)
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .fillMaxHeight())
            } else {
                ghostListComponent(Modifier
                    .weight(1f, false)
                    .wrapContentWidth(Alignment.CenterHorizontally)
                    .fillMaxHeight())
                evidenceListComponent(Modifier
                    .weight(1f)
                    .fillMaxSize())
            }
        }

    }

    val sheetComponent: @Composable (Modifier) -> Unit = { modifier ->

        val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
        val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.Start
        ) {
            when (toolbarUiState.category) {
                OperationToolbarUiState.Category.TOOL_NONE -> {}
                OperationToolbarUiState.Category.TOOL_CONFIG -> OperationConfigs(
                    modifier = Modifier
                        .height(IntrinsicSize.Max),
                    mapConfigComponent = { modifier -> mapConfigComponent(modifier) },
                    difficultyConfigComponent = { modifier ->
                        difficultyConfigComponent(modifier) },
                    sanityMeterComponent = { modifier -> sanityMeterComponent(modifier) },
                    timerComponent = { modifier -> timerComponent(modifier) },
                    phaseComponent = { modifier -> }
                )

                OperationToolbarUiState.Category.TOOL_TRAITS -> {
                    val modifier = when(deviceConfiguration) {
                        DeviceConfiguration.MOBILE_PORTRAIT,
                        DeviceConfiguration.TABLET_PORTRAIT -> Modifier.fillMaxHeight(.5f)
                        else -> Modifier.fillMaxHeight()
                    }

                    TraitConfig(
                        modifier = modifier,
                        state = traitListUiState,
                        actions = traitListUiActions,
                        colors = TraitListItemUiColors(
                            unselectedColor = LocalPalette.current.surfaceContainerHigh,
                            unselectedOnColor = LocalPalette.current.onSurface,
                            selectedColor = LocalPalette.current.surfaceContainerLow,
                            selectedOnColor = LocalPalette.current.onSurfaceVariant,
                        )
                    )
                }

                OperationToolbarUiState.Category.TOOL_ANALYZER -> OperationDetails(
                    modifier = Modifier
                        .height(IntrinsicSize.Max),
                    operationDetailsUiState = operationDetailsUiState
                )

                OperationToolbarUiState.Category.TOOL_TIMERS -> TimerTools(
                    modifier = Modifier
                        .height(IntrinsicSize.Max),
                    smudgeHuntPreventionBundle = smudgeHuntPreventionBundle,
                    smudgeBlindingBundle = smudgeBlindingBundle,
                    huntDurationBundle = huntDurationBundle,
                    huntGapBundle = huntGapBundle,
                    fingerprintTimerBundle = fingerprintTimerBundle
                )

                OperationToolbarUiState.Category.TOOL_FOOTSTEP -> BpmTool(
                    modifier = Modifier
                        .height(IntrinsicSize.Max),
                    state = bpmToolUiState,
                    actions = bpmToolUiActions
                )

            }
        }
    }

    val customDifficultySheetComponent: @Composable (Modifier) -> Unit = { modifier ->
        val sheetState = rememberModalBottomSheetState()
        val scope = rememberCoroutineScope()

        if (showCustomDifficultyBottomSheet) {
            ModalBottomSheet(
                modifier = modifier,
                onDismissRequest = {
                    showCustomDifficultyBottomSheet = false
                },
                sheetState = sheetState
            ) {
                // Sheet content
                Button(onClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showCustomDifficultyBottomSheet = false
                        }
                    }
                }) {
                    Text("Hide bottom sheet")
                }
            }
        }
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
                        bottomSheetComponent = { modifier -> sheetComponent(modifier) },
                        customDifficultySheetComponent = { modifier -> customDifficultySheetComponent(modifier) }
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
                        sideSheetComponent = { modifier -> sheetComponent(modifier) },
                        customDifficultySheetComponent = { modifier -> customDifficultySheetComponent(modifier) }
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
    bottomSheetComponent: @Composable (Modifier) -> Unit,
    journalComponent: @Composable (Modifier) -> Unit,
    customDifficultySheetComponent: @Composable (Modifier) -> Unit
) {

    val toolbarComponent: @Composable (Modifier) -> Unit = { modifier ->
        OperationToolbar(
            modifier = modifier
                .heightIn(min = 48.dp),
            operationToolbarUiState = state.operationToolbarUiState,
            toolbarUiActions = actions.toolbarUi,
            containerColor = LocalPalette.current.surfaceContainerHigh
        )
    }

    journalComponent(Modifier.weight(1f, false))

    ToolbarBottomSheet(
        modifier = Modifier
            .padding(8.dp),
        selectBarComponent = { modifier -> toolbarComponent(modifier) },
        content = { modifier ->
            bottomSheetComponent(modifier
                .fillMaxWidth()
                .animateContentSize()
                .then(
                    if (!state.operationToolbarUiState.isCollapsed)
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

    customDifficultySheetComponent(Modifier)

}

@Composable
private fun RowScope.Investigation(
    state: InvestigationUiState,
    actions: InvestigationUiActions,
    journalComponent: @Composable (Modifier) -> Unit,
    sideSheetComponent: @Composable (Modifier) -> Unit,
    customDifficultySheetComponent: @Composable (Modifier) -> Unit
) {

    val toolbarContent: @Composable (Modifier) -> Unit = { modifier ->
        OperationToolRail(
            modifier = modifier
                .widthIn(min = 48.dp),
            operationToolbarUiState = state.operationToolbarUiState,
            toolbarUiActions = actions.toolbarUi,
            containerColor = LocalPalette.current.surfaceContainerHigh
        )
    }

    ToolbarSideSheet(
        modifier = Modifier
            .padding(8.dp),
        selectRailComponent = { modifier -> toolbarContent(modifier) },
        content = { modifier ->
            sideSheetComponent(modifier
                .fillMaxHeight()
                .animateContentSize()
                .then(
                    if (!state.operationToolbarUiState.isCollapsed)
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

    journalComponent(Modifier)

    customDifficultySheetComponent(Modifier)

}

@Composable
private fun ToolbarBottomSheet(
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
private fun ToolbarSideSheet(
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
fun OperationConfigs(
    modifier: Modifier = Modifier,
    timerComponent: @Composable (Modifier) -> Unit = {},
    mapConfigComponent: @Composable (Modifier) -> Unit = {},
    difficultyConfigComponent: @Composable (Modifier) -> Unit = {},
    sanityMeterComponent: @Composable (Modifier) -> Unit = {},
    phaseComponent: @Composable (Modifier) -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {

        mapConfigComponent(Modifier
            .fillMaxWidth()
        )

        difficultyConfigComponent(Modifier
            .fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {

            sanityMeterComponent(
                Modifier
                    .size(64.dp)
            )

            timerComponent(
                Modifier
                    .weight(1f, false)
            )
        }

        phaseComponent(Modifier)
    }
}

internal data class InvestigationScreenUserPreferences(
    val enableGhostReorder: Boolean = false,
    val maxHuntWarnFlashTime: Long = 0L,
    val allowHuntWarnAudio: Boolean = false
)

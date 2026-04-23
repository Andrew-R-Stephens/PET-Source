package com.tritiumgaming.feature.investigation.ui

import android.widget.Toast
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarBundle
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarUiActions
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarUiColors
import com.tritiumgaming.feature.investigation.app.mappers.difficulty.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.difficultysettings.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.map.toStringResource
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ClearPopup
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.DecrementDifficulty
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.DecrementMap
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.IncrementDifficulty
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.IncrementMap
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.PlayerDeath
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ResetInvestigation
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.SetBpmData
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.SetBpmMeasurementType
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.SetDifficulty
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.SetEvidence
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.SetMap
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.SetPlayerSanity
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.SetToolbarCategory
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.SetTraitFilter
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.SetWeather
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.SetWeatherOverride
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ShowEvidencePopup
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ShowGhostPopup
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.SkipOperationTimer
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ToggleApplyBpmMeasurement
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ToggleFuseBoxOverride
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ToggleGhostNegation
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ToggleOperationTimer
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ToggleToolbar
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ToggleTrait
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.ToggleUniqueTraitFilter
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.TriggerToolTimer
import com.tritiumgaming.feature.investigation.ui.InvestigationScreenViewModel.InvestigationEvent.UseSanityMedication
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.DigitalTimerUiState
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.TimerUiActions
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.ConfigActionsBundle
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.ConfigStateBundle
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.carousel.CarouselUiActions
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.carousel.ConfigCarouselUiState
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.dropdown.ConfigDropdownUiState
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.dropdown.DropdownUiActions
import com.tritiumgaming.feature.investigation.ui.journal.JournalComponent
import com.tritiumgaming.feature.investigation.ui.journal.evidence.primary.EvidenceListUiActions
import com.tritiumgaming.feature.investigation.ui.journal.evidence.primary.EvidenceListUiState
import com.tritiumgaming.feature.investigation.ui.journal.ghost.GhostListUiActions
import com.tritiumgaming.feature.investigation.ui.journal.ghost.GhostListUiState
import com.tritiumgaming.feature.investigation.ui.journal.ghost.item.GhostListUiItemActions
import com.tritiumgaming.feature.investigation.ui.popups.common.InvestigationPopup
import com.tritiumgaming.feature.investigation.ui.popups.evidence.EvidencePopup
import com.tritiumgaming.feature.investigation.ui.popups.ghost.GhostPopup
import com.tritiumgaming.feature.investigation.ui.sheet.ToolSheetActionsBundle
import com.tritiumgaming.feature.investigation.ui.sheet.ToolSheetStateBundle
import com.tritiumgaming.feature.investigation.ui.sheet.ToolsBottomSheetComponent
import com.tritiumgaming.feature.investigation.ui.sheet.ToolsSideSheetComponent
import com.tritiumgaming.feature.investigation.ui.tool.footstep.BpmToolUiActions
import com.tritiumgaming.feature.investigation.ui.tool.statusbar.StatusBarComponent
import com.tritiumgaming.feature.investigation.ui.tool.statusbar.StatusBarComponentStateBundle
import com.tritiumgaming.feature.investigation.ui.tool.temperature.TemperatureStateBundle
import com.tritiumgaming.feature.investigation.ui.tool.temperature.TemperatureUiActions
import com.tritiumgaming.feature.investigation.ui.tool.traits.TraitListUiActions
import com.tritiumgaming.feature.investigation.ui.tool.traits.TraitListUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiActions
import com.tritiumgaming.feature.investigation.ui.toolbar.operation.OperationToolRail
import com.tritiumgaming.feature.investigation.ui.toolbar.operation.OperationToolbar
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Weather
import com.tritiumgaming.shared.data.investigation.model.ToolTimerType
import com.tritiumgaming.shared.data.investigation.model.TraitFilter
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources

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
    val context = LocalContext.current

    val popupUiState by investigationViewModel.popupUiState.collectAsStateWithLifecycle()

    val toolbarUiState by investigationViewModel.primaryToolbarUiState.collectAsStateWithLifecycle()

    val timerUiState by investigationViewModel.operationTimerUiState.collectAsStateWithLifecycle()
    val phaseUiState by investigationViewModel.phaseUiState.collectAsStateWithLifecycle()
    val mapConfigUiState by investigationViewModel.mapConfigUiState.collectAsStateWithLifecycle()
    val operationDetailsUiState by investigationViewModel.operationDetailsUiState.collectAsStateWithLifecycle()
    val difficultyUiState by investigationViewModel.difficultyConfigUiState.collectAsStateWithLifecycle()
    val weatherUiState by investigationViewModel.weatherUiState.collectAsStateWithLifecycle()
    val temperatureUiState by investigationViewModel.temperatureUiState.collectAsStateWithLifecycle()
    val difficultyOverrideUiState by investigationViewModel.difficultyOverridesState.collectAsStateWithLifecycle()
    val sanityUiState by investigationViewModel.playerSanityUiState.collectAsStateWithLifecycle()
    val evidenceListUiStates by investigationViewModel.evidenceListUiState.collectAsStateWithLifecycle()

    val traitFilterOptions by investigationViewModel.traitFilterOptionsUiState.collectAsStateWithLifecycle()
    val traitListUiStates by investigationViewModel.traitListUiState.collectAsStateWithLifecycle()
    val ghostOrder by investigationViewModel.ghostsSortedUiState.collectAsStateWithLifecycle()
    val evidenceStates by investigationViewModel.evidenceStates.collectAsStateWithLifecycle()

    val smudgeHuntProtectionTimerState by investigationViewModel.smudgeHuntProtectionTimerUiState.collectAsStateWithLifecycle()
    val huntDurationTimerState by investigationViewModel.huntDurationTimerUiState.collectAsStateWithLifecycle()
    val huntGapTimerState by investigationViewModel.huntCooldownTimerUiState.collectAsStateWithLifecycle()
    val fingerprintTimerState by investigationViewModel.fingerprintTimerUiState.collectAsStateWithLifecycle()

    val bpmToolUiState by investigationViewModel.bpmToolUiState.collectAsStateWithLifecycle()

    val digitalTimerUiState = DigitalTimerUiState(
        startTime = timerUiState.startTime,
        remainingTime = timerUiState.remainingTime
    )

    val ghostListUiState = GhostListUiState(
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
            investigationViewModel.onEvent(SetTraitFilter(filter))
        },
        onSelectTrait = { trait ->
            investigationViewModel.onEvent(ToggleTrait(trait))
        },
        onToggleUniqueOnly = {
            investigationViewModel.onEvent(ToggleUniqueTraitFilter)
        }
    )

    val ghostListUiActions = GhostListUiActions(
        onNameClick = { investigationViewModel.onEvent(ShowGhostPopup(it)) }
    )
    val ghostListUiItemActions = GhostListUiItemActions(
        onToggleNegateGhost = { ghost ->
            investigationViewModel.onEvent(ToggleGhostNegation(ghost))
        },
        onRequestToolTip = {  } //TODO
    )

    val operationToolbarUiActions = ToolbarUiActions(
        onToggleCollapseToolbar = { investigationViewModel.onEvent(ToggleToolbar) },
        onChangeToolbarCategory = { category -> investigationViewModel.onEvent(SetToolbarCategory(category))
        },
        onReset = { investigationViewModel.onEvent(ResetInvestigation) }
    )

    val timerUiActions = TimerUiActions(
        onToggle = {
            investigationViewModel.onEvent(ToggleOperationTimer)
        },
        onSkip = {
            investigationViewModel.onEvent(SkipOperationTimer)
        }
    )

    val mapUiActions = ConfigActionsBundle(
        carouselUiActions = CarouselUiActions(
            onLeftClick = { investigationViewModel.onEvent(DecrementMap) },
            onRightClick = { investigationViewModel.onEvent(IncrementMap) },
        ),
        dropdownUiActions = DropdownUiActions(
            onSelect = { investigationViewModel.onEvent(SetMap(it)) }
        )
    )

    val difficultyUiActions = ConfigActionsBundle(
        carouselUiActions = CarouselUiActions(
            onLeftClick = { investigationViewModel.onEvent(DecrementDifficulty) },
            onRightClick = { investigationViewModel.onEvent(IncrementDifficulty) }
        ),
        dropdownUiActions = DropdownUiActions(
            onSelect = { investigationViewModel.onEvent(SetDifficulty(it)) }
        )
    )

    val weatherUiActions = ConfigActionsBundle(
        carouselUiActions = CarouselUiActions(
            onLeftClick = { /*investigationViewModel.decrementWeatherIndex()*/ },
            onRightClick = { /*investigationViewModel.incrementWeatherIndex()*/ }
        ),
        dropdownUiActions = DropdownUiActions(
            onSelect = {
                investigationViewModel.onEvent(
                    SetWeather(Weather.entries[it])
                )
            }
        )
    )

    val evidenceListUiActions = EvidenceListUiActions(
        onChangeEvidenceRuling = { e, r ->
            investigationViewModel.onEvent(SetEvidence(e, r))
        },
        onClickItem = { investigationViewModel.onEvent(ShowEvidencePopup(it)) },
    )

    val bpmToolUiActions = BpmToolUiActions(
        onUpdate = {
            investigationViewModel.onEvent(SetBpmData(it))
        },
        onChangeMeasurementType = {
            investigationViewModel.onEvent(SetBpmMeasurementType(it))
        },
        toggleApplyMeasurement = {
            investigationViewModel.onEvent(ToggleApplyBpmMeasurement)
        }
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

    val weatherUiStateBundle = ConfigStateBundle(
        carouselUiState = ConfigCarouselUiState(
            label =
                if(weatherUiState.weather == Weather.RANDOM) {
                    R.string.difficulty_setting_response_unknown }
                else weatherUiState.weather.toStringResource(),
            enabled = weatherUiState.enabled
        ),
        dropdownUiState = ConfigDropdownUiState(
            options = Weather.entries.map {
                if(it == Weather.RANDOM) R.string.difficulty_setting_state_weather_unknown
                else it.toStringResource() },
            enabled = weatherUiState.enabled,
            label =
                if(weatherUiState.weather == Weather.RANDOM) {
                    R.string.difficulty_setting_response_unknown }
                else weatherUiState.weather.toStringResource(),
        )
    )

    val temperatureUiActions = TemperatureUiActions(
        onTogglePower = {
            investigationViewModel.onEvent(ToggleFuseBoxOverride)

            Toast.makeText(context, "Fuse Box Toggled", Toast.LENGTH_SHORT).show()
        }
    )

    val temperatureStateBundle = TemperatureStateBundle(
        temperatureUiState = temperatureUiState,
        fuseBoxState = difficultyOverrideUiState.fuseBox,
        temperatureUiActions = temperatureUiActions
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
        colors = notchedProgressBarUiColors,
        actions = NotchedProgressBarUiActions(
            onToggle = {
                investigationViewModel.onEvent(TriggerToolTimer(ToolTimerType.SMUDGE_TIMER))
            }
        )
    )

    val huntDurationBundle = NotchedProgressBarBundle(
        title = "Hunt Duration",
        state = huntDurationTimerState,
        colors = notchedProgressBarUiColors,
        actions = NotchedProgressBarUiActions(
            onToggle = {
                investigationViewModel.onEvent(TriggerToolTimer(ToolTimerType.HUNT_DURATION))
            }
        )
    )

    val huntCooldownBundle = NotchedProgressBarBundle(
        title = "Hunt Cooldown",
        state = huntGapTimerState,
        colors = notchedProgressBarUiColors,
        actions = NotchedProgressBarUiActions(
            onToggle = {
                investigationViewModel.onEvent(TriggerToolTimer(ToolTimerType.HUNT_COOLDOWN))
            }
        )
    )

    val fingerprintTimerBundle = NotchedProgressBarBundle(
        title = "UV Evidence Lifetime",
        state = fingerprintTimerState,
        colors = notchedProgressBarUiColors,
        actions = NotchedProgressBarUiActions(
            onToggle = {
                investigationViewModel.onEvent(TriggerToolTimer(ToolTimerType.UV_EVIDENCE_DURATION))
            }
        )
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

    val toolSheetStateBundle = ToolSheetStateBundle(
        bpmToolUiState = bpmToolUiState,
        traitListUiState = traitListUiState,
        toolbarUiState = toolbarUiState,
        operationDetailsUiState = operationDetailsUiState,
        sanityUiState = sanityUiState,
        timerUiState = timerUiState,
        phaseUiState = phaseUiState,
        smudgeHuntPreventionBundle = smudgeHuntPreventionBundle,
        huntDurationBundle = huntDurationBundle,
        huntCooldownBundle = huntCooldownBundle,
        fingerprintTimerBundle = fingerprintTimerBundle,
        difficultyUiStateBundle = difficultyUiStateBundle,
        mapUiStateBundle = mapUiStateBundle,
        weatherUiStateBundle = weatherUiStateBundle,
        weatherUiState = weatherUiState,
        temperatureStateBundle = temperatureStateBundle
    )

    val toolSheetActionsBundle = ToolSheetActionsBundle(
        bpmToolUiActions = bpmToolUiActions,
        traitListUiActions = traitListUiActions,
        timerUiActions = timerUiActions,
        difficultyUiActions = difficultyUiActions,
        mapUiActions = mapUiActions,
        weatherUiActions = weatherUiActions,
        temperatureUiActions = temperatureUiActions,
        onSanityChange = {
            investigationViewModel.onEvent(SetPlayerSanity(it))
        },
        onWeatherChange = {
            investigationViewModel.onEvent(SetWeatherOverride(it))
        },
        onUseSanityMedication = {
            investigationViewModel.onEvent(UseSanityMedication)
        },
        onPlayerDeath = {
            investigationViewModel.onEvent(PlayerDeath)
        },
    )

    val statusBarComponentStateBundle = StatusBarComponentStateBundle(
        sanityUiState = sanityUiState,
        digitalTimerUiState = digitalTimerUiState,
        phaseUiState = phaseUiState
    )

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val deviceConfiguration = DeviceConfiguration.fromWindowSizeClass(windowSizeClass)

    when(deviceConfiguration) {
        DeviceConfiguration.MOBILE_PORTRAIT,
        DeviceConfiguration.TABLET_PORTRAIT -> {
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.Top
            ) {
                Investigation(
                    modifier = Modifier.weight(1f, false),
                    state = investigationUiState,
                    actions = investigationUiActions,
                    statusBarComponent = { modifier ->
                        StatusBarComponent(
                            modifier = modifier,
                            bundle = statusBarComponentStateBundle
                        )
                    },
                    journalComponent = { modifier ->
                        JournalComponent(
                            modifier = modifier,
                            evidenceListUiState = evidenceListUiState,
                            evidenceListUiActions = evidenceListUiActions,
                            ghostListUiState = ghostListUiState,
                            ghostListUiActions = ghostListUiActions,
                            ghostListUiItemActions = ghostListUiItemActions
                        )
                    },
                    bottomSheetComponent = { modifier ->
                        ToolsBottomSheetComponent(
                            modifier = modifier,
                            stateBundle = toolSheetStateBundle,
                            actionsBundle = toolSheetActionsBundle
                        )
                    }
                )

            }

        }
        DeviceConfiguration.MOBILE_LANDSCAPE,
        DeviceConfiguration.TABLET_LANDSCAPE,
        DeviceConfiguration.DESKTOP -> {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Start
            ) {
                Investigation(
                    modifier = Modifier,
                    state = investigationUiState,
                    actions = investigationUiActions,
                    statusBarComponent = { modifier ->
                        StatusBarComponent(
                            modifier = modifier,
                            bundle = statusBarComponentStateBundle
                        )
                    },
                    journalComponent = { modifier ->
                        JournalComponent(
                            modifier = modifier,
                            evidenceListUiState = evidenceListUiState,
                            evidenceListUiActions = evidenceListUiActions,
                            ghostListUiState = ghostListUiState,
                            ghostListUiActions = ghostListUiActions,
                            ghostListUiItemActions = ghostListUiItemActions
                        )
                    },
                    sideSheetComponent = { modifier ->
                        ToolsSideSheetComponent(
                            modifier = modifier,
                            stateBundle = toolSheetStateBundle,
                            actionsBundle = toolSheetActionsBundle
                        )
                    }
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
            ) { investigationViewModel.onEvent(ClearPopup) }
        }
        popupUiState.evidencePopupRecord?.let { record ->
            EvidencePopup(
                modifier = modifier,
                record = record
            ) { investigationViewModel.onEvent(ClearPopup) }
        }
    }
}

@Composable
private fun ColumnScope.Investigation(
    modifier: Modifier = Modifier,
    state: InvestigationUiState,
    actions: InvestigationUiActions,
    statusBarComponent: @Composable (Modifier) -> Unit = {},
    bottomSheetComponent: @Composable (Modifier) -> Unit,
    journalComponent: @Composable (Modifier) -> Unit
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
            selectBarComponent = { modifier -> toolbarComponent(modifier) },
            content = { modifier ->
                bottomSheetComponent(
                    modifier
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
    }
}

@Composable
private fun RowScope.Investigation(
    modifier: Modifier = Modifier,
    state: InvestigationUiState,
    actions: InvestigationUiActions,
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
                operationToolbarUiState = state.operationToolbarUiState,
                toolbarUiActions = actions.toolbarUi,
                containerColor = LocalPalette.current.surfaceContainerHigh
            )
        }

        VerticalToolbar(
            modifier = Modifier
                .padding(8.dp),
            selectRailComponent = { modifier -> toolbarContent(modifier) },
            content = { modifier ->
                sideSheetComponent(
                    modifier
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

            journalComponent(Modifier
                .weight(1f)
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
fun OperationConfigsBottomSheet(
    modifier: Modifier = Modifier,
    sanityMedicationComponent: @Composable (Modifier) -> Unit = {},
    playerDeathButtonComponent: @Composable (Modifier) -> Unit = {},
    timerComponent: @Composable (Modifier) -> Unit = {},
    mapConfigComponent: @Composable (Modifier) -> Unit = {},
    difficultyConfigComponent: @Composable (Modifier) -> Unit = {},
    weatherConfigComponent: @Composable (Modifier) -> Unit = {},
    temperatureMeterComponent: @Composable (Modifier) -> Unit = {},
    fuseBoxControlComponent: @Composable (Modifier) -> Unit = {},
    sanityMeterComponent: @Composable (Modifier) -> Unit = {},
    showTemperatureMeterComponent: Boolean,
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
                text = "Operation Config".uppercase(),
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
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = LocalPalette.current.surfaceContainerLow
                )
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
                    )

                    difficultyConfigComponent(
                        Modifier
                            .fillMaxWidth()
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
                    border = BorderStroke(
                        width = 2.dp,
                        color = LocalPalette.current.surfaceContainerLow
                    )
                ) {
                    timerComponent(
                        Modifier
                            .width(IntrinsicSize.Min)
                            .padding(8.dp)
                    )
                }

                if (showTemperatureMeterComponent) {

                    Surface(
                        modifier = Modifier
                            .fillMaxWidth(),
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp),
                        border = BorderStroke(
                            width = 2.dp,
                            color = LocalPalette.current.surfaceContainerLow
                        )
                    ) {

                        Row(
                            modifier = Modifier
                                .width(IntrinsicSize.Min)
                                .padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            temperatureMeterComponent(
                                Modifier
                                    .width(IntrinsicSize.Min)
                            )

                            fuseBoxControlComponent(
                                Modifier
                                    .width(48.dp)
                                    .fillMaxHeight()
                                    .heightIn(min = 48.dp)
                            )

                        }
                    }
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
                    .weight(1f),
                color = LocalPalette.current.surfaceContainer,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = LocalPalette.current.surfaceContainerLow
                )
            ) {
                sanityMeterComponent(
                    Modifier
                        .height(48.dp)
                        .weight(1f)
                        .padding(8.dp)
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
                    border = BorderStroke(
                        width = 2.dp,
                        color = LocalPalette.current.surfaceContainerLow
                    )
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
                    border = BorderStroke(
                        width = 2.dp,
                        color = LocalPalette.current.surfaceContainerLow
                    )
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

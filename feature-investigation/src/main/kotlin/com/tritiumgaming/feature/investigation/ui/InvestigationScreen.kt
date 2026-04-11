package com.tritiumgaming.feature.investigation.ui

import android.util.Log
import androidx.annotation.DrawableRes
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.material3.rememberSliderState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tritiumgaming.core.common.config.DeviceConfiguration
import com.tritiumgaming.core.common.util.ColorUtils
import com.tritiumgaming.core.common.util.FormatterUtils.toPercentageString
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.impl.base.BloodMoonIcon
import com.tritiumgaming.core.ui.icon.impl.base.SanityMedicationIcon
import com.tritiumgaming.core.ui.icon.impl.composite.HuntCooldownDurationIcon
import com.tritiumgaming.core.ui.icon.impl.composite.HuntDurationIcon
import com.tritiumgaming.core.ui.icon.impl.composite.PreventHuntIcon
import com.tritiumgaming.core.ui.theme.LocalUiConfiguration
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarBundle
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarUiColors
import com.tritiumgaming.core.ui.widgets.progressbar.NotchedProgressBarUiState
import com.tritiumgaming.core.ui.widgets.progressbar.ProgressBarNotch
import com.tritiumgaming.feature.investigation.app.mappers.difficulty.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.difficultysettings.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.ghost.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.map.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.phase.toPhaseTitle
import com.tritiumgaming.feature.investigation.app.mappers.phase.toStringResource
import com.tritiumgaming.feature.investigation.app.mappers.weather.toDrawable
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.DigitalTimer
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.DigitalTimerUiState
import com.tritiumgaming.feature.investigation.ui.common.digitaltimer.TimerSkipButton
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
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetailsUiState
import com.tritiumgaming.feature.investigation.ui.tool.footstep.BpmTool
import com.tritiumgaming.feature.investigation.ui.tool.footstep.BpmToolUiActions
import com.tritiumgaming.feature.investigation.ui.tool.footstep.BpmToolUiState
import com.tritiumgaming.feature.investigation.ui.tool.timers.ProgressBarTimer
import com.tritiumgaming.feature.investigation.ui.tool.timers.TimerTools
import com.tritiumgaming.feature.investigation.ui.tool.traits.TraitConfig
import com.tritiumgaming.feature.investigation.ui.tool.traits.TraitListItemUiColors
import com.tritiumgaming.feature.investigation.ui.tool.traits.TraitListUiActions
import com.tritiumgaming.feature.investigation.ui.tool.traits.TraitListUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiActions
import com.tritiumgaming.feature.investigation.ui.toolbar.operation.OperationToolRail
import com.tritiumgaming.feature.investigation.ui.toolbar.operation.OperationToolbar
import com.tritiumgaming.feature.investigation.ui.toolbar.operation.OperationToolbarUiState
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Weather
import com.tritiumgaming.shared.data.ghost.mapper.GhostResources.GhostTitle
import com.tritiumgaming.shared.data.investigation.model.OperationConditionsData
import com.tritiumgaming.shared.data.investigation.model.TraitFilter
import com.tritiumgaming.shared.data.map.simple.mappers.SimpleMapResources
import com.tritiumgaming.shared.data.preferences.properties.DensityType
import kotlin.time.Duration.Companion.minutes

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
    val popupUiState by investigationViewModel.popupUiState.collectAsStateWithLifecycle()

    val toolbarUiState by investigationViewModel.primaryToolbarUiState.collectAsStateWithLifecycle()

    val timerUiState by investigationViewModel.sanityTimerUiState.collectAsStateWithLifecycle()
    val phaseUiState by investigationViewModel.phaseUiState.collectAsStateWithLifecycle()
    val mapConfigUiState by investigationViewModel.mapConfigUiState.collectAsStateWithLifecycle()
    val operationDetailsUiState by investigationViewModel.operationDetailsUiState.collectAsStateWithLifecycle()
    val difficultyUiState by investigationViewModel.difficultyConfigUiState.collectAsStateWithLifecycle()
    //val operationConditionsState by investigationViewModel.operationConditionsState.collectAsStateWithLifecycle()
    val weatherUiState by investigationViewModel.weatherUiState.collectAsStateWithLifecycle()
    val sanityUiState by investigationViewModel.playerSanityUiState.collectAsStateWithLifecycle()
    val evidenceListUiStates by investigationViewModel.evidenceListUiState.collectAsStateWithLifecycle()

    val traitFilterOptions by investigationViewModel.traitFilterOptionsUiState.collectAsStateWithLifecycle()
    val traitListUiStates by investigationViewModel.traitListUiState.collectAsStateWithLifecycle()
    val ghostOrder by investigationViewModel.ghostsSortedUiState.collectAsStateWithLifecycle()
    val evidenceStates by investigationViewModel.evidenceStates.collectAsStateWithLifecycle()

    // TODO val smudgeHuntPreventionState by investigationViewModel.smudgeHuntPreventionState.collectAsStateWithLifecycle()

    val digitalTimerUiState = DigitalTimerUiState(
        startTime = timerUiState.startTime,
        remainingTime = timerUiState.remainingTime
    )

    val bpmToolUiState by investigationViewModel
        .bpmToolUiState.collectAsStateWithLifecycle()

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
        onNameClick = { investigationViewModel.setPopup(it) }
    )

    val ghostListUiItemActions = GhostListUiItemActions(
        onGetEvidenceState = { evidenceType ->
            investigationViewModel.getRuledEvidence(evidenceType)
        },
        onToggleNegateGhost = { ghost ->
            investigationViewModel.toggleExplicitNegation(ghost)
        },
        onRequestToolTip = {  } //TODO
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
        onToggle = {
            investigationViewModel.toggleTimer()
        },
        onSkip = {
            investigationViewModel.skipTimer()
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

    val weatherUiActions = ConfigActionsBundle(
        carouselUiActions = CarouselUiActions(
            onLeftClick = { investigationViewModel.decrementWeatherIndex() },
            onRightClick = { investigationViewModel.incrementWeatherIndex() }
        ),
        dropdownUiActions = DropdownUiActions(
            onSelect = {
                investigationViewModel.setWeather(
                    Weather.entries[it]
                )
            }
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
        max = 3.minutes.inWholeMilliseconds,
        origin = 0,
        remaining = 50000,
        notches = listOf(
            ProgressBarNotch(
                stringResource(GhostTitle.SPIRIT.toStringResource()),
                (3).minutes.inWholeMilliseconds
            ),
            ProgressBarNotch(
                "Standard",
                (1.5).minutes.inWholeMilliseconds
            ),
            ProgressBarNotch(
                stringResource(GhostTitle.DEMON.toStringResource()),
                (1).minutes.inWholeMilliseconds
            ),
        ),
        running = false
    )

    //TODO replace with viewmodel state
    /*val smudgeBlindingProtectionTimerState = NotchedProgressBarUiState(
        max = 72000,
        origin = 0,
        remaining = 50000,
        notches = listOf(

        ),
        running = false
    )*/

    //TODO replace with viewmodel state
    val huntDurationTimerState = NotchedProgressBarUiState(
        max = 1.minutes.inWholeMilliseconds,
        origin = 0,
        remaining = (.87).minutes.inWholeMilliseconds,
        notches = listOf(
            ProgressBarNotch(
                "Standard",
                (1.5).minutes.inWholeMilliseconds
            ),
            ProgressBarNotch(
                "Cursed",
                (1.5).minutes.inWholeMilliseconds
            ),
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

    val weatherUiStateBundle = ConfigStateBundle(
        carouselUiState = ConfigCarouselUiState(
            label = weatherUiState.weather.toStringResource(),
            enabled = operationDetailsUiState.difficultyDetails.settings.weather == Weather.RANDOM
        ),
        dropdownUiState = ConfigDropdownUiState(
            options = Weather.entries.map { it.toStringResource() },
            enabled = operationDetailsUiState.difficultyDetails.settings.weather == Weather.RANDOM,
            label = weatherUiState.weather.toStringResource()
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

    /*val smudgeBlindingBundle = NotchedProgressBarBundle(
        title = "Smudge Blinding Protection",
        state = smudgeBlindingProtectionTimerState,
        colors = notchedProgressBarUiColors
    )*/

    val huntDurationBundle = NotchedProgressBarBundle(
        title = "Hunt Duration",
        state = huntDurationTimerState,
        colors = notchedProgressBarUiColors
    )

    val huntCooldownBundle = NotchedProgressBarBundle(
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
        weatherUiState = weatherUiState
    )

    val toolSheetActionsBundle = ToolSheetActionsBundle(
        bpmToolUiActions = bpmToolUiActions,
        traitListUiActions = traitListUiActions,
        timerUiActions = timerUiActions,
        difficultyUiActions = difficultyUiActions,
        mapUiActions = mapUiActions,
        weatherUiActions = weatherUiActions,
        onSanityChange = {
            investigationViewModel.setPlayerSanity(it)
        },
        onWeatherChange = {
            investigationViewModel.setWeatherOverride(it)
        },
        onUseSanityMedication = {
            investigationViewModel.useSanityMedication()
        },
        onPlayerDeath = {

        },
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
                            sanityUiState = sanityUiState,
                            digitalTimerUiState = digitalTimerUiState,
                            phaseUiState = phaseUiState,
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
                    },
                    customDifficultySheetComponent = { modifier ->
                        /*CustomDifficultySheetComponent(modifier) */
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
                            sanityUiState = sanityUiState,
                            digitalTimerUiState = digitalTimerUiState,
                            phaseUiState = phaseUiState,
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
                    },
                    customDifficultySheetComponent = { modifier ->
                        /*CustomDifficultySheetComponent(modifier) */
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
private fun WeatherConfigComponent(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    bundle: ConfigStateBundle,
    actions: ConfigActionsBundle
) {
    val uiDensityType = LocalUiConfiguration.current.densityType

    val textStyle = LocalTypography.current.quaternary.regular
    val color = LocalPalette.current.surfaceContainer
    val onColor = LocalPalette.current.onSurface

    val icon: @Composable (Modifier) -> Unit = { modifier ->
        Image(
            modifier = modifier,
            contentScale = ContentScale.Inside,
            alignment = Alignment.Center,
            painter = painterResource(icon),
            colorFilter = ColorFilter.tint(onColor),
            contentDescription = ""
        )
    }

    when(uiDensityType) {
        DensityType.COMPACT -> {
            OperationConfigDropdown(
                modifier = modifier,
                icon = { icon(it) },
                state = bundle.dropdownUiState,
                actions = actions.dropdownUiActions,
                textStyle = textStyle,
                onColor = onColor,
                expandedColor = color,
            )
        }
        else -> {
            OperationConfigCarousel(
                modifier = modifier,
                state = bundle.carouselUiState,
                actions = actions.carouselUiActions,
                leadingIcon = { icon(it) },
                textStyle = textStyle,
                onColor = onColor,
            )
        }
    }
}

@Composable
private fun MapConfigComponent(
    modifier: Modifier = Modifier,
    bundle: ConfigStateBundle,
    actions: ConfigActionsBundle
) {
    val uiDensityType = LocalUiConfiguration.current.densityType

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
                state = bundle.dropdownUiState,
                actions = actions.dropdownUiActions,
                textStyle = textStyle,
                onColor = onColor,
                expandedColor = color,
            )
        }
        else -> {
            OperationConfigCarousel(
                modifier = modifier,
                state = bundle.carouselUiState,
                actions = actions.carouselUiActions,
                leadingIcon = { icon(it) },
                textStyle = textStyle,
                onColor = onColor,
            )
        }
    }
}

@Composable
private fun DifficultyConfigComponent(
    modifier: Modifier = Modifier,
    bundle: ConfigStateBundle,
    actions: ConfigActionsBundle
) {
    val uiDensityType = LocalUiConfiguration.current.densityType

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
                state = bundle.dropdownUiState,
                actions = actions.dropdownUiActions,
                icon = { icon(it) },
                textStyle = textStyle,
                expandedColor = color,
                onColor = onColor,
            )
        }
        else -> {
            OperationConfigCarousel(
                modifier = modifier,
                state = bundle.carouselUiState,
                actions = actions.carouselUiActions,
                leadingIcon = { icon(it) },
                textStyle = textStyle,
                onColor = onColor,
            )
        }
    }
}

@Composable
private fun SanityMeterComponent(
    modifier: Modifier = Modifier,
    sanityUiState: PlayerSanityUiState,
    onSanityChange: (Float) -> Unit = {}
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SanityMeter(
            modifier = Modifier
                .aspectRatio(1f),
            sanityUiState = sanityUiState
        )

        val sanityPercentString = sanityUiState.sanityLevel.toPercentageString()

        Text(
            modifier = Modifier
                .wrapContentSize(),
            text = sanityPercentString,
            maxLines = 1,
            style = LocalTypography.current.tertiary.bold.copy(
                textAlign = TextAlign.Center
            ),
            color = Color(ColorUtils.interpolate(
                LocalPalette.current.onSurface.toArgb(),
                endColor = LocalPalette.current.primary.toArgb(),
                sanityUiState.sanityLevel)
            ),
            fontSize = 14.sp,
        )

        Box(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
        ) {
            SanitySeekbar(
                modifier = Modifier
                    .fillMaxWidth(),
                state = sanityUiState.insanityLevel,
                onValueChange = {
                    onSanityChange(it)
                },
                containerColor = Color.Transparent,
                inactiveTrackColor = LocalPalette.current.onSurface,
                activeTrackColor = LocalPalette.current.primary,
                thumbOutlineColor = LocalPalette.current.primaryContainer,
                thumbInnerColor = LocalPalette.current.primaryContainer,
            )
        }

    }
}


@Composable
private fun PhaseComponent(
    modifier: Modifier = Modifier,
    phaseUiState: OperationDetailsUiState.PhaseDetails
) {

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier
                .wrapContentHeight(),
            text = stringResource(phaseUiState.type.toPhaseTitle().toStringResource()),
            color = LocalPalette.current.onSurface,
            style = LocalTypography.current.tertiary.regular.copy(
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
            ),
            maxLines = 1
        )
    }
}

@Composable
private fun TimerComponentColumn(
    modifier: Modifier = Modifier,
    timerUiState: TimerUiState,
    timerUiActions: TimerUiActions,
    phaseUiState: OperationDetailsUiState.PhaseDetails
) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            DigitalTimer(
                modifier = Modifier
                    .height(36.dp)
                    .padding(8.dp),
                state = timerUiState,
            )
        }

        Row (
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            TimerToggleButton(
                modifier = Modifier
                    .size(48.dp),
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

            TimerSkipButton(
                modifier = Modifier
                    .size(48.dp),
                state = timerUiState,
                actions = timerUiActions,
                content = { modifier ->
                    Icon(
                        modifier = modifier,
                        painter = painterResource(R.drawable.ic_control_skip),
                        contentDescription = null,
                        tint = LocalPalette.current.onSurface
                    )
                },
            )
        }

        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            color = LocalPalette.current.surfaceContainerLowest,
            shape = RoundedCornerShape(8.dp),
        ) {
            PhaseComponent(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                phaseUiState = phaseUiState
            )
        }
    }
}

@Composable
private fun TimerComponentRow(
    modifier: Modifier = Modifier,
    timerUiState: TimerUiState,
    timerUiActions: TimerUiActions,
    phaseUiState: OperationDetailsUiState.PhaseDetails
) {

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        Column(
            modifier = Modifier
                .wrapContentSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DigitalTimer(
                modifier = Modifier
                    .height(36.dp)
                    .padding(8.dp),
                state = timerUiState,
            )

            Surface(
                modifier = Modifier,
                color = LocalPalette.current.surfaceContainerLowest,
                shape = RoundedCornerShape(8.dp),
            ) {
                PhaseComponent(
                    Modifier
                        .padding(8.dp),
                    phaseUiState = phaseUiState
                )
            }
        }

        Row (
            modifier = Modifier
                .wrapContentSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            TimerToggleButton(
                modifier = Modifier
                    .size(48.dp),
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

            TimerSkipButton(
                modifier = Modifier
                    .size(48.dp),
                state = timerUiState,
                actions = timerUiActions,
                content = { modifier ->
                    Icon(
                        modifier = modifier,
                        painter = painterResource(R.drawable.ic_control_skip),
                        contentDescription = null,
                        tint = LocalPalette.current.onSurface
                    )
                },
            )
        }

    }
}

@Composable
private fun JournalComponent(
    modifier: Modifier = Modifier,
    evidenceListUiState: EvidenceListUiState,
    evidenceListUiActions: EvidenceListUiActions,
    ghostListUiState: GhostListUiState,
    ghostListUiActions: GhostListUiActions,
    ghostListUiItemActions: GhostListUiItemActions
) {

    val uiIsRtl = LocalUiConfiguration.current.isRtl

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
            .fillMaxSize(),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.Top
    ) {
        if(uiIsRtl) {
            evidenceListComponent(Modifier
                .weight(1f)
                .fillMaxSize()
            )
            ghostListComponent(Modifier
                .weight(1f, false)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .fillMaxHeight()
            )
        } else {
            ghostListComponent(Modifier
                .weight(1f, false)
                .wrapContentWidth(Alignment.CenterHorizontally)
                .fillMaxHeight()
            )
            evidenceListComponent(Modifier
                .weight(1f)
                .fillMaxSize()
            )
        }
    }

}

@Composable
private fun SanityMedicationComponent(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    val fillColor = LocalPalette.current.onSurfaceVariant
    val strokeColor = LocalPalette.current.onSurface
    var color = if(isPressed) { fillColor } else { strokeColor }

    // This effect runs whenever isPressed changes
    LaunchedEffect(isPressed) {
        color = if(isPressed) { fillColor } else { strokeColor }
    }

    Button(
        modifier = modifier,
        interactionSource = interactionSource,
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = Color.Transparent
        ),
        onClick = { onClick() },
        contentPadding = PaddingValues(8.dp)
    ) {
        SanityMedicationIcon(
            modifier = Modifier,
            colors = IconVectorColors.defaults().copy(
                fillColor = color,
                strokeColor = color
            )
        )
    }
}

@Composable
private fun BloodMoonComponent(
    modifier: Modifier = Modifier,
    state: Boolean,
    onClick: () -> Unit = {}
) {

    BloodMoonIcon(
        modifier = modifier
            .clickable(onClick = {
                onClick()
            })
            .padding(8.dp)
            .clip(CircleShape),
        colors = IconVectorColors.defaults().copy(
            fillColor = LocalPalette.current.onSurfaceVariant,
            strokeColor = LocalPalette.current.onSurface
        ),
        filled = state
    )
}

internal data class StatusBarComponentUiState(
    val sanityUiState: PlayerSanityUiState,
    val digitalTimerUiState: DigitalTimerUiState
)

@Composable
private fun StatusBarComponent(
    modifier: Modifier = Modifier,
    sanityUiState: PlayerSanityUiState,
    digitalTimerUiState: DigitalTimerUiState,
    phaseUiState: OperationDetailsUiState.PhaseDetails
) {
    val sanityPercentString = sanityUiState.sanityLevel.toPercentageString()

    FlowRow(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        itemVerticalAlignment = Alignment.CenterVertically
    ) {

        Surface(
            modifier = Modifier,
            color = LocalPalette.current.surfaceContainerLow,
            shape = RoundedCornerShape(8.dp),
        ) {
            Row(
                modifier = Modifier
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier,
                    text = "${stringResource(R.string.investigation_label_phase)}:",
                    color = LocalPalette.current.onSurface,
                    style = LocalTypography.current.tertiary.regular.copy(
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                )
                Text(
                    modifier = Modifier,
                    text = stringResource(
                        phaseUiState.type.toPhaseTitle().toStringResource()),
                    color = LocalPalette.current.onSurfaceVariant,
                    style = LocalTypography.current.tertiary.regular.copy(
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                )

                if(digitalTimerUiState.remainingTime > 0L) {
                    DigitalTimer(
                        modifier = Modifier,
                        state = digitalTimerUiState,
                        fontSize = 12.sp,
                        color = LocalPalette.current.onSurfaceVariant
                    )
                }
            }
        }

        Surface(
            modifier = Modifier,
            color = LocalPalette.current.surfaceContainerLow,
            shape = RoundedCornerShape(8.dp),
        ) {

            Row(
                modifier = Modifier
                    .padding(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier,
                    text = "${stringResource(R.string.investigation_sanitymeter_title)}:",
                    color = LocalPalette.current.onSurface,
                    style = LocalTypography.current.tertiary.regular.copy(
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                )
                Text(
                    modifier = Modifier,
                    text = sanityPercentString,
                    color = LocalPalette.current.onSurfaceVariant,
                    style = LocalTypography.current.tertiary.regular.copy(
                        fontSize = 12.sp,
                        textAlign = TextAlign.Center
                    )
                )
            }
        }

    }
}

private data class ToolSheetStateBundle(
    val smudgeHuntPreventionBundle: NotchedProgressBarBundle,
    val huntDurationBundle: NotchedProgressBarBundle,
    val huntCooldownBundle: NotchedProgressBarBundle,
    val fingerprintTimerBundle: NotchedProgressBarBundle,
    val difficultyUiStateBundle: ConfigStateBundle,
    val mapUiStateBundle: ConfigStateBundle,
    val weatherUiStateBundle: ConfigStateBundle,
    val weatherUiState: InvestigationScreenViewModel.WeatherUiState,
    val toolbarUiState: OperationToolbarUiState,
    val traitListUiState: TraitListUiState,
    val operationDetailsUiState: OperationDetailsUiState,
    val bpmToolUiState: BpmToolUiState,
    val sanityUiState: PlayerSanityUiState,
    val timerUiState: TimerUiState,
    val phaseUiState: OperationDetailsUiState.PhaseDetails,
)

private data class ToolSheetActionsBundle(
    val difficultyUiActions: ConfigActionsBundle,
    val mapUiActions: ConfigActionsBundle,
    val weatherUiActions: ConfigActionsBundle,
    val traitListUiActions: TraitListUiActions,
    val bpmToolUiActions: BpmToolUiActions,
    val timerUiActions: TimerUiActions,
    val onSanityChange: (Float) -> Unit = {},
    val onWeatherChange: (Weather) -> Unit = {},
    val onUseSanityMedication: () -> Unit = {},
    val onPlayerDeath: () -> Unit = {}
)

@Composable
private fun ToolsBottomSheetComponent(
    modifier: Modifier = Modifier,
    stateBundle: ToolSheetStateBundle,
    actionsBundle: ToolSheetActionsBundle
) {

    val weatherUiState = stateBundle.weatherUiState
    val toolbarUiState = stateBundle.toolbarUiState
    val traitListUiState = stateBundle.traitListUiState
    val operationDetailsUiState = stateBundle.operationDetailsUiState
    val bpmToolUiState = stateBundle.bpmToolUiState
    val sanityUiState = stateBundle.sanityUiState
    val timerUiState = stateBundle.timerUiState
    val phaseUiState = stateBundle.phaseUiState
    val smudgeHuntPreventionBundle = stateBundle.smudgeHuntPreventionBundle
    val huntDurationBundle = stateBundle.huntDurationBundle
    val huntCooldownBundle = stateBundle.huntCooldownBundle
    val fingerprintTimerBundle = stateBundle.fingerprintTimerBundle
    val difficultyUiStateBundle = stateBundle.difficultyUiStateBundle
    val mapUiStateBundle = stateBundle.mapUiStateBundle
    val weatherUiStateBundle = stateBundle.weatherUiStateBundle

    val difficultyUiActions = actionsBundle.difficultyUiActions
    val mapUiActions = actionsBundle.mapUiActions
    val weatherUiActions = actionsBundle.weatherUiActions
    val traitListUiActions = actionsBundle.traitListUiActions
    val bpmToolUiActions = actionsBundle.bpmToolUiActions
    val timerUiActions = actionsBundle.timerUiActions

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        when (toolbarUiState.category) {
            OperationToolbarUiState.Category.TOOL_NONE -> {}
            OperationToolbarUiState.Category.TOOL_CONFIG -> {
                OperationConfigsBottomSheet(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .height(IntrinsicSize.Max),
                    operationConditionsComponent = { modifier ->

                    },
                    mapConfigComponent = { modifier ->
                        MapConfigComponent(
                            modifier = modifier,
                            bundle = mapUiStateBundle,
                            actions = mapUiActions
                        )
                    },
                    difficultyConfigComponent = { modifier ->
                        DifficultyConfigComponent(
                            modifier = modifier,
                            bundle = difficultyUiStateBundle,
                            actions = difficultyUiActions
                        )
                    },
                    weatherConfigComponent = { modifier ->
                        WeatherConfigComponent(
                            modifier = modifier,
                            icon = weatherUiState.weather.toDrawable(),
                            bundle = weatherUiStateBundle,
                            actions = weatherUiActions
                        )
                    },
                    sanityMeterComponent = { modifier ->
                        SanityMeterComponent(
                            modifier = modifier,
                            sanityUiState = sanityUiState
                        ) {
                            actionsBundle.onSanityChange(it)
                        }
                    },
                    timerComponent = { modifier ->
                        TimerComponentColumn(
                            modifier = modifier,
                            timerUiState = timerUiState,
                            timerUiActions = timerUiActions,
                            phaseUiState = phaseUiState
                        )
                    },
                    sanityMedicationComponent = { modifier ->
                        SanityMedicationComponent(
                            modifier = modifier,
                            onClick = {
                                actionsBundle.onUseSanityMedication()
                            }
                        )
                    }
                )
            }

            OperationToolbarUiState.Category.TOOL_TRAITS -> {
                TraitConfig(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxHeight(.5f),
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

            OperationToolbarUiState.Category.TOOL_ANALYZER -> {
                OperationDetails(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .height(IntrinsicSize.Max),
                    operationDetailsUiState = operationDetailsUiState
                )
            }

            OperationToolbarUiState.Category.TOOL_TIMERS -> {
                TimerTools(
                    modifier = Modifier
                        .padding(8.dp)
                        .height(IntrinsicSize.Max),
                ) {

                    Surface(
                        modifier = Modifier,
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        ProgressBarTimer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            bundle = smudgeHuntPreventionBundle
                        ) { modifier ->
                            PreventHuntIcon(
                                modifier = modifier,
                                colors = IconVectorColors.defaults().copy(
                                    fillColor = LocalPalette.current.onSurface
                                )
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier,
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        ProgressBarTimer(
                            modifier = Modifier
                                .padding(8.dp),
                            bundle = huntDurationBundle
                        ) { modifier ->
                            HuntDurationIcon(
                                modifier = modifier,
                                colors = IconVectorColors.defaults().copy(
                                    fillColor = LocalPalette.current.onSurface
                                )
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier,
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        ProgressBarTimer(
                            modifier = Modifier
                                .padding(8.dp),
                            bundle = huntCooldownBundle
                        ) { modifier ->
                            HuntCooldownDurationIcon(
                                modifier = modifier,
                                colors = IconVectorColors.defaults().copy(
                                    fillColor = LocalPalette.current.onSurface
                                )
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier,
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        ProgressBarTimer(
                            modifier = Modifier
                                .padding(8.dp),
                            bundle = fingerprintTimerBundle
                        ) { modifier ->
                            HuntCooldownDurationIcon(
                                modifier = modifier,
                                colors = IconVectorColors.defaults().copy(
                                    fillColor = LocalPalette.current.onSurface
                                )
                            )
                        }
                    }
                }
            }

            OperationToolbarUiState.Category.TOOL_FOOTSTEP -> {
                BpmTool(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .height(IntrinsicSize.Max),
                    state = bpmToolUiState,
                    actions = bpmToolUiActions
                )
            }

        }
    }
}

@Composable
private fun ToolsSideSheetComponent(
    modifier: Modifier = Modifier,
    stateBundle: ToolSheetStateBundle,
    actionsBundle: ToolSheetActionsBundle
) {

    val toolbarUiState = stateBundle.toolbarUiState
    val traitListUiState = stateBundle.traitListUiState
    val operationDetailsUiState = stateBundle.operationDetailsUiState
    val bpmToolUiState = stateBundle.bpmToolUiState
    val sanityUiState = stateBundle.sanityUiState
    val timerUiState = stateBundle.timerUiState
    val phaseUiState = stateBundle.phaseUiState
    val smudgeHuntPreventionBundle = stateBundle.smudgeHuntPreventionBundle
    val huntDurationBundle = stateBundle.huntDurationBundle
    val huntCooldownBundle = stateBundle.huntCooldownBundle
    val fingerprintTimerBundle = stateBundle.fingerprintTimerBundle
    val difficultyUiStateBundle = stateBundle.difficultyUiStateBundle
    val mapUiStateBundle = stateBundle.mapUiStateBundle
    val weatherUiStateBundle = stateBundle.weatherUiStateBundle

    val difficultyUiActions = actionsBundle.difficultyUiActions
    val mapUiActions = actionsBundle.mapUiActions
    val weatherUiActions = actionsBundle.weatherUiActions
    val traitListUiActions = actionsBundle.traitListUiActions
    val bpmToolUiActions = actionsBundle.bpmToolUiActions
    val timerUiActions = actionsBundle.timerUiActions

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        when (toolbarUiState.category) {
            OperationToolbarUiState.Category.TOOL_NONE -> {}
            OperationToolbarUiState.Category.TOOL_CONFIG -> {

                OperationConfigsSideSheet(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .height(IntrinsicSize.Max),
                    operationConditionsComponent = { modifier ->

                    },
                    sanityMedicationComponent = { modifier ->
                        SanityMedicationComponent(
                            modifier = modifier,
                            onClick = {
                                actionsBundle.onUseSanityMedication()
                            }
                        )
                    },
                    mapConfigComponent = { modifier ->
                        MapConfigComponent(
                            modifier = modifier,
                            bundle = mapUiStateBundle,
                            actions = mapUiActions
                        )
                    },
                    difficultyConfigComponent = { modifier ->
                        DifficultyConfigComponent(
                            modifier = modifier,
                            bundle = difficultyUiStateBundle,
                            actions = difficultyUiActions
                        )
                    },
                    weatherConfigComponent = { modifier ->
                        WeatherConfigComponent(
                            modifier = modifier,
                            icon = R.drawable.icon_cp_tarot_moon,
                            bundle = weatherUiStateBundle,
                            actions = weatherUiActions
                        )
                    },
                    sanityMeterComponent = { modifier ->
                        SanityMeterComponent(
                            modifier = modifier,
                            sanityUiState = sanityUiState
                        ) {
                            actionsBundle.onSanityChange(it)
                        }
                    },
                    timerComponent = { modifier ->
                        TimerComponentRow(
                            modifier = modifier,
                            timerUiState = timerUiState,
                            timerUiActions = timerUiActions,
                            phaseUiState = phaseUiState
                        )
                    }
                )
            }

            OperationToolbarUiState.Category.TOOL_TRAITS -> {
                TraitConfig(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxHeight(),
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

            OperationToolbarUiState.Category.TOOL_ANALYZER -> {
                OperationDetails(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .height(IntrinsicSize.Max),
                    operationDetailsUiState = operationDetailsUiState
                )
            }

            OperationToolbarUiState.Category.TOOL_TIMERS -> {
                TimerTools(
                    modifier = Modifier
                        .padding(8.dp)
                        .height(IntrinsicSize.Max),
                ) {

                    Surface(
                        modifier = Modifier,
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        ProgressBarTimer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            bundle = smudgeHuntPreventionBundle
                        ) { modifier ->
                            PreventHuntIcon(
                                modifier = modifier,
                                colors = IconVectorColors.defaults().copy(
                                    fillColor = LocalPalette.current.onSurface
                                )
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier,
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        ProgressBarTimer(
                            modifier = Modifier
                                .padding(8.dp),
                            bundle = huntDurationBundle
                        ) { modifier ->
                            HuntDurationIcon(
                                modifier = modifier,
                                colors = IconVectorColors.defaults().copy(
                                    fillColor = LocalPalette.current.onSurface
                                )
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier,
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        ProgressBarTimer(
                            modifier = Modifier
                                .padding(8.dp),
                            bundle = huntCooldownBundle
                        ) { modifier ->
                            HuntCooldownDurationIcon(
                                modifier = modifier,
                                colors = IconVectorColors.defaults().copy(
                                    fillColor = LocalPalette.current.onSurface
                                )
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier,
                        color = LocalPalette.current.surfaceContainer,
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        ProgressBarTimer(
                            modifier = Modifier
                                .padding(8.dp),
                            bundle = fingerprintTimerBundle
                        ) { modifier ->
                            HuntCooldownDurationIcon(
                                modifier = modifier,
                                colors = IconVectorColors.defaults().copy(
                                    fillColor = LocalPalette.current.onSurface
                                )
                            )
                        }
                    }
                }
            }

            OperationToolbarUiState.Category.TOOL_FOOTSTEP -> {
                BpmTool(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .height(IntrinsicSize.Max),
                    state = bpmToolUiState,
                    actions = bpmToolUiActions
                )
            }

        }
    }
}

/*@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDifficultySheetComponent(
    modifier: Modifier = Modifier,
    showCustomDifficultyBottomSheet: Boolean = false,
    onDismissRequest: () -> Unit = {}
) {

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
}*/

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SanitySeekbar(
    modifier: Modifier = Modifier,
    state: Float,
    containerColor: Color = Color.White,
    inactiveTrackColor: Color = Color.White,
    activeTrackColor: Color = Color.White,
    thumbOutlineColor: Color = Color.White,
    thumbInnerColor: Color = Color.White,
    onValueChange: (Float) -> Unit = {}
) {

    var rememberSliderPosition by remember { mutableFloatStateOf(state) }

    val rememberSliderState =
        rememberSliderState(
            value = state,
            valueRange = 0f..1f,
            steps = 100,
            onValueChangeFinished = {
                onValueChange(rememberSliderPosition)
                Log.d("Slider", "Slider finished $rememberSliderPosition $state")
            }
        )

    LaunchedEffect(state) {
        rememberSliderPosition = state
        rememberSliderState.value = state
    }

    val interactionSource = remember { MutableInteractionSource() }

    Card(
        modifier = modifier
            .wrapContentHeight(),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = containerColor
        )
    ) {

        Slider(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            value = rememberSliderPosition,
            onValueChange = {
                rememberSliderPosition = it
                rememberSliderState.value = it
                onValueChange(rememberSliderPosition)
            },
            onValueChangeFinished = {
                onValueChange(rememberSliderPosition)
                Log.d("Slider",
                    "Slider finished $rememberSliderPosition $state")
            },
            valueRange = 0f..1f,
            interactionSource = interactionSource,
            thumb = {
                SeekbarThumb(
                    outlineColor = thumbOutlineColor,
                    innerColor = thumbInnerColor
                )
            },
            track = {
                SliderDefaults.Track(
                    modifier = Modifier
                        .height(3.dp),
                    colors = SliderDefaults.colors(
                        activeTrackColor = activeTrackColor,
                        inactiveTrackColor = inactiveTrackColor,
                        activeTickColor = Color.Transparent,
                        inactiveTickColor = Color.Transparent,
                        disabledInactiveTickColor = Color.Transparent,
                        disabledThumbColor = Color.Transparent,
                        disabledActiveTrackColor = Color.Transparent,
                        disabledInactiveTrackColor = Color.Transparent,
                        disabledActiveTickColor = Color.Transparent,
                        thumbColor = Color.Transparent
                    ),
                    sliderState = rememberSliderState,
                    thumbTrackGapSize = 0.dp
                )
            },
            colors = SliderDefaults.colors(
                activeTrackColor = activeTrackColor,
                inactiveTrackColor = inactiveTrackColor,
                activeTickColor = Color.Transparent,
                inactiveTickColor = Color.Transparent
            )
        )


    }
}

@Composable
private fun SeekbarThumb(
    modifier: Modifier = Modifier
        .size(ButtonDefaults.IconSize),
    outlineColor: Color = Color.White,
    innerColor: Color = Color.White
) {
    Box(
        modifier = modifier
            .border(2.dp, outlineColor, CircleShape)
            .padding(2.dp)
            .clip(CircleShape)
            .background(innerColor)
    )
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
    modifier: Modifier = Modifier,
    state: InvestigationUiState,
    actions: InvestigationUiActions,
    statusBarComponent: @Composable (Modifier) -> Unit = {},
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

        /*if(state.operationToolbarUiState.isCollapsed || state.operationToolbarUiState.category !=
            OperationToolbarUiState.Category.TOOL_CONFIG) {
            statusBarComponent(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )
        }*/

        statusBarComponent(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
        )

        ToolbarBottomSheet(
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

        //customDifficultySheetComponent(Modifier)
    }
}

@Composable
private fun RowScope.Investigation(
    modifier: Modifier = Modifier,
    state: InvestigationUiState,
    actions: InvestigationUiActions,
    statusBarComponent: @Composable (Modifier) -> Unit = {},
    journalComponent: @Composable (Modifier) -> Unit,
    sideSheetComponent: @Composable (Modifier) -> Unit,
    customDifficultySheetComponent: @Composable (Modifier) -> Unit
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

        ToolbarSideSheet(
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
            /*if(state.operationToolbarUiState.isCollapsed || state.operationToolbarUiState.category !=
                OperationToolbarUiState.Category.TOOL_CONFIG) {
                statusBarComponent(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                )
            }*/

            statusBarComponent(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            )

            journalComponent(Modifier
                .weight(1f)
            )
        }

        //customDifficultySheetComponent(Modifier)
    }
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
fun OperationConfigsBottomSheet(
    modifier: Modifier = Modifier,
    operationConditionsComponent: @Composable (Modifier) -> Unit = {},
    sanityMedicationComponent: @Composable (Modifier) -> Unit = {},
    timerComponent: @Composable (Modifier) -> Unit = {},
    mapConfigComponent: @Composable (Modifier) -> Unit = {},
    difficultyConfigComponent: @Composable (Modifier) -> Unit = {},
    weatherConfigComponent: @Composable (Modifier) -> Unit = {},
    sanityMeterComponent: @Composable (Modifier) -> Unit = {},
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
                .height(IntrinsicSize.Min)
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

                }
            }

            Surface(
                modifier = Modifier
                    .fillMaxHeight(),
                color = LocalPalette.current.surfaceContainer,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = LocalPalette.current.surfaceContainerLow
                )
            ) {
                Box(
                    Modifier
                        .padding(8.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    timerComponent(
                        Modifier
                            .width(IntrinsicSize.Min)
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

            Surface(
                modifier = Modifier,
                color = LocalPalette.current.surfaceContainer,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = LocalPalette.current.surfaceContainerLow
                )
            ) {
                Row(
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    sanityMedicationComponent(
                        Modifier
                            .size(48.dp)
                    )
                }
            }

        }

        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
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

                    weatherConfigComponent(
                        Modifier
                            .fillMaxWidth()
                    )

                }
            }

            //TODO Temperature Meter
            /*Surface(
                modifier = Modifier
                    .fillMaxHeight(),
                color = LocalPalette.current.surfaceContainer,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(
                    width = 2.dp,
                    color = LocalPalette.current.surfaceContainerLow
                )
            ) {
                Box(
                    Modifier
                        .padding(8.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    timerComponent(
                        Modifier
                            .width(IntrinsicSize.Min)
                    )
                }
            }*/

        }

    }

}

@Composable
fun OperationConfigsSideSheet(
    modifier: Modifier = Modifier,
    operationConditionsComponent: @Composable (Modifier) -> Unit = {},
    sanityMedicationComponent: @Composable (Modifier) -> Unit = {},
    timerComponent: @Composable (Modifier) -> Unit = {},
    mapConfigComponent: @Composable (Modifier) -> Unit = {},
    difficultyConfigComponent: @Composable (Modifier) -> Unit = {},
    weatherConfigComponent: @Composable (Modifier) -> Unit = {},
    sanityMeterComponent: @Composable (Modifier) -> Unit = {},
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

        Surface(
            modifier = Modifier,
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

            }
        }

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
            Box(
                Modifier
                    .padding(8.dp),
                contentAlignment = Alignment.BottomCenter
            ) {
                timerComponent(
                    Modifier
                        .fillMaxWidth()
                )
            }
        }


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

        Surface(
            modifier = Modifier,
            color = LocalPalette.current.surfaceContainer,
            shape = RoundedCornerShape(8.dp),
            border = BorderStroke(
                width = 2.dp,
                color = LocalPalette.current.surfaceContainerLow
            )
        ) {
            Row(
                modifier = Modifier,
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                sanityMedicationComponent(
                    Modifier
                        .size(48.dp)
                )
            }
        }

    }

}

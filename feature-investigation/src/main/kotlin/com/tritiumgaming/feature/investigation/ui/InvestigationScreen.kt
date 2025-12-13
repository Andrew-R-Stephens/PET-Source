package com.tritiumgaming.feature.investigation.ui

import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
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
import com.tritiumgaming.feature.investigation.ui.journal.Journal
import com.tritiumgaming.feature.investigation.ui.popups.common.InvestigationPopup
import com.tritiumgaming.feature.investigation.ui.popups.evidence.EvidencePopup
import com.tritiumgaming.feature.investigation.ui.popups.ghost.GhostPopup
import com.tritiumgaming.feature.investigation.ui.toolbar.InvestigationToolbar
import com.tritiumgaming.feature.investigation.ui.toolbar.ResetButton
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarItem
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.analysis.OperationDetails
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.footstep.FootstepTool
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.operationconfig.DifficultyConfigCarousel
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.operationconfig.MapConfigCarousel
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.sanity.SanityMeterView
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.timer.DigitalTimer
import com.tritiumgaming.feature.investigation.ui.toolbar.subsection.sanitytracker.controller.timer.TimerToggleButton

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

    val investigationToolbarUiState =
        investigationViewModel.toolbarUiState.collectAsStateWithLifecycle()

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
                investigationViewModel = investigationViewModel,
                collapsedState = investigationToolbarUiState.value.isCollapsed
            )
        }
    } else {
        Row(
            modifier = Modifier
                .padding(start = 8.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            Investigation(
                investigationViewModel = investigationViewModel,
                collapsedState = investigationToolbarUiState.value.isCollapsed
            )
        }
    }

    val popupUiState by investigationViewModel.popupUiState.collectAsStateWithLifecycle()
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
fun InvestigationCompactPortrait() {

}

@Composable
fun InvestigationCompactLandscape() {

}

@Composable
private fun ColumnScope.Investigation(
    investigationViewModel: InvestigationScreenViewModel,
    collapsedState: Boolean,
) {

    Journal(
        modifier = Modifier
            .weight(1f, false),
        investigationViewModel = investigationViewModel
    )

    val investigationToolbarUiState =
        investigationViewModel.toolbarUiState.collectAsStateWithLifecycle()
    val section = investigationToolbarUiState.value.category

    OperationToolbar(
        modifier = Modifier
            .height(48.dp),
        investigationViewModel = investigationViewModel
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (!collapsedState)
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
        when(section) {
            ToolbarUiState.Category.TOOL_CONFIG -> ToolbarConfigurationSection(
                investigationViewModel = investigationViewModel,
                modifier = Modifier
                    .height(IntrinsicSize.Max))
            ToolbarUiState.Category.TOOL_ANALYZER -> ToolbarOperationAnalysis(
                investigationViewModel = investigationViewModel,
                modifier = Modifier
                    .fillMaxHeight(.5f))
            ToolbarUiState.Category.TOOL_FOOTSTEP -> ToolbarFootsteps(
                modifier = Modifier,
                investigationViewModel = investigationViewModel
            )

        }
    }
}

@Composable
private fun RowScope.Investigation(
    investigationViewModel: InvestigationScreenViewModel,
    collapsedState: Boolean,
) {

    val investigationToolbarUiState =
        investigationViewModel.toolbarUiState.collectAsStateWithLifecycle()
    val section = investigationToolbarUiState.value.category

    Column(
        modifier = Modifier
            .then(
                if (collapsedState) Modifier
                    .fillMaxWidth(0f)
                    .alpha(0f)
                else Modifier.fillMaxWidth(.35f)
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        when(section) {
            ToolbarUiState.Category.TOOL_CONFIG -> ToolbarConfigurationSection(
                investigationViewModel = investigationViewModel,
                modifier = Modifier
                    .height(IntrinsicSize.Max)
            )
            ToolbarUiState.Category.TOOL_ANALYZER -> ToolbarOperationAnalysis(
                investigationViewModel = investigationViewModel,
                modifier = Modifier
                    .wrapContentHeight(align = Alignment.Bottom))
            ToolbarUiState.Category.TOOL_FOOTSTEP -> ToolbarFootsteps(
                modifier = Modifier,
                investigationViewModel = investigationViewModel
            )

        }
    }
    OperationToolbar(
        modifier = Modifier
            .width(48.dp),
        investigationViewModel = investigationViewModel
    )
    Journal(
        investigationViewModel = investigationViewModel,
    )
}

@Composable
private fun ColumnScope.ToolbarConfigurationSection(
    investigationViewModel: InvestigationScreenViewModel,
    modifier: Modifier = Modifier
) {
    val timerUiState = investigationViewModel.timerUiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {

        MapConfigCarousel(
            investigationViewModel = investigationViewModel
        )
        DifficultyConfigCarousel(
            investigationViewModel = investigationViewModel
        )
        SanityMeterView(
            modifier = Modifier
                .size(64.dp),
            investigationViewModel = investigationViewModel
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
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
                    timeMillis = timerUiState.value.remainingTime
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
                    state = timerUiState.value.paused,
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
                    investigationViewModel.toggleTimer()
                }
            }
        }
    }
}

@Composable
private fun ToolbarFootsteps(
    investigationViewModel: InvestigationScreenViewModel,
    modifier: Modifier = Modifier
) {
    var bpm by remember { mutableFloatStateOf(0f) }

    Box (
        modifier = modifier
    ) {
        FootstepTool(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
                .clickable(onClick = {
                    bpm++
                })
                .clip(RoundedCornerShape(8.dp)),
            bpm = bpm
        )
    }

}

@Composable
private fun ToolbarTimer(
    investigationViewModel: InvestigationScreenViewModel,
    modifier: Modifier = Modifier
) {
    val timerUiState = investigationViewModel.timerUiState.collectAsStateWithLifecycle()
    val timeMillis = timerUiState.value.remainingTime

    Box (
        modifier = modifier
    ) {
        DigitalTimer(
            timeMillis = timeMillis
        )
    }
}

@Composable
private fun ToolbarOperationAnalysis(
    investigationViewModel: InvestigationScreenViewModel,
    modifier: Modifier = Modifier
) {
    Box (
        modifier = modifier
    ) {
        OperationDetails(
            investigationViewModel = investigationViewModel
        )
    }
}

@Composable
private fun OperationToolbar(
    modifier: Modifier = Modifier,
    investigationViewModel: InvestigationScreenViewModel,
) {
    val investigationToolbarUiState =
        investigationViewModel.toolbarUiState.collectAsStateWithLifecycle()
    val section = investigationToolbarUiState.value.category


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
                    isCollapsed = investigationToolbarUiState.value.isCollapsed,
                    icon = R.drawable.ic_arrow_chevron_right,
                    disabledRotationVertical = 90,
                    disabledRotationHorizontal = 90,
                    enabledRotationAddition = 180,
                    onClick = { investigationViewModel.toggleToolbarState() }
                )
            }

        },
        stickyContentEnd = {

            ToolbarItem(
                onClick = {}
            ){
                ResetButton {
                    investigationViewModel.reset()
                }
            }

        }
    ) {

        ToolbarItem(
            onClick = {
                investigationViewModel.setToolbarCategory(ToolbarUiState.Category.TOOL_CONFIG)
            }
        ){
            Image(
                modifier = modifier,
                imageVector = getGearVector(
                    if(section == ToolbarUiState.Category.TOOL_CONFIG) {
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
                investigationViewModel.setToolbarCategory(ToolbarUiState.Category.TOOL_ANALYZER)
            }
        ){
            Image(
                modifier = modifier,
                imageVector = getInfoVector(
                    if(section == ToolbarUiState.Category.TOOL_ANALYZER) {
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
                investigationViewModel.setToolbarCategory(ToolbarUiState.Category.TOOL_FOOTSTEP)
            }
        ){
            Image(
                modifier = modifier,
                imageVector = getExitVector(
                    if(section == ToolbarUiState.Category.TOOL_FOOTSTEP) {
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
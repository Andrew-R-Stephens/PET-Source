package com.tritiumgaming.feature.investigation.ui.section

import androidx.appcompat.widget.DialogTitle
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.FrameRateCategory
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.preferredFrameRate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.PopupProperties
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.investigation.ui.common.footstep.beatline.BeatLineUiColors
import com.tritiumgaming.feature.investigation.ui.common.footstep.FootstepVisualizer
import com.tritiumgaming.feature.investigation.ui.common.footstep.visualizer.VisualizerUiActions
import com.tritiumgaming.feature.investigation.ui.common.footstep.BpmVisualizerUiColorBundle
import com.tritiumgaming.feature.investigation.ui.common.footstep.FootstepVisualizerUiStateBundle
import com.tritiumgaming.feature.investigation.ui.common.footstep.graphlabels.GraphLabelsUiColors
import com.tritiumgaming.feature.investigation.ui.common.footstep.graphsurface.GraphSurfaceUiColors
import com.tritiumgaming.feature.investigation.ui.common.footstep.realtimeplot.RealtimePlotUiColors
import com.tritiumgaming.feature.investigation.ui.common.footstep.realtimeverticalmeter.RealtimeVerticalMeterColors
import com.tritiumgaming.feature.investigation.ui.common.footstep.visualizer.TapUiState
import com.tritiumgaming.feature.investigation.ui.toolbar.impl.ModifiersButton
import com.tritiumgaming.shared.data.ghostname.model.GhostName
import kotlin.collections.forEach
import kotlin.time.Duration.Companion.seconds

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ToolbarFootstepsVisualizerSection(
    modifier: Modifier = Modifier,
    footstepVisualizerUiStateBundle: FootstepVisualizerUiStateBundle
) {

    val footstepVisualizerUiStateBundle by remember {
        mutableStateOf(footstepVisualizerUiStateBundle)
    }

    val bpmVisualizerUiColorBundle = BpmVisualizerUiColorBundle(
        realtimePlotUiColors = RealtimePlotUiColors(
            instant = LocalPalette.current.primary,
            smoothed = LocalPalette.current.tertiary,
            weighted = LocalPalette.current.secondary,
            line = LocalPalette.current.onSurface
        ),
        realtimeVerticalMeterColors = RealtimeVerticalMeterColors(
            color = LocalPalette.current.onSurface,
            onColor = LocalPalette.current.tertiary,
        ),
        beatLineUiColors = BeatLineUiColors(
            color = LocalPalette.current.onSurface
        ),
        graphLabelsXAxisUiColors = GraphLabelsUiColors(
            label = LocalPalette.current.onSurface,
        ),
        graphLabelsYAxisUiColors = GraphLabelsUiColors(
            label = LocalPalette.current.onSurface,
        ),
        graphSurfaceUiColors = GraphSurfaceUiColors(
            surface = LocalPalette.current.surface,
            surfaceContainer = LocalPalette.current.surfaceContainer,
            xAxis = LocalPalette.current.onSurface,
            yAxis = Color.Unspecified
        )
    )

    Box (
        modifier = modifier
    ) {

        Column (
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .preferredFrameRate(FrameRateCategory.Normal)
        ) {

            var tapUiState by remember{
                mutableStateOf(TapUiState())
            }

            FootstepVisualizer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp),
                stateBundle = footstepVisualizerUiStateBundle,
                colorBundle = bpmVisualizerUiColorBundle,
                actions = VisualizerUiActions(
                    onUpdate = { newTapUiState ->
                        tapUiState = newTapUiState
                    }
                )
            )

            val dropdownList = listOf(
                "A", "B", "C"
            )

            var state by remember {
                mutableStateOf("Initial State")
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(LocalPalette.current.surfaceContainer),
                contentAlignment = Alignment.Center
            ){
                var expanded by remember { mutableStateOf(false) }

                Surface(
                    modifier = modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(8.dp),
                    color = LocalPalette.current.surfaceContainer
                ) {

                    ExposedDropdownMenuBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        expanded = expanded,
                        onExpandedChange = { expanded = !expanded },
                    ) {

                        TextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 4.dp)
                                .wrapContentHeight()
                                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true),
                            value = state,
                            textStyle = LocalTypography.current.quaternary.regular.copy(
                                color = LocalPalette.current.onSurface,
                                fontSize = 18.sp
                            ),
                            placeholder = {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(24.dp)
                                        .wrapContentHeight(),
                                    text = "Placeholder",
                                    style = LocalTypography.current.quaternary.regular,
                                    color = LocalPalette.current.onSurface.copy(alpha = 0.5f),
                                    fontSize = 18.sp
                                )
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                            },
                            maxLines = 1,
                            singleLine = true,
                            colors = TextFieldDefaults.colors().copy(
                                focusedContainerColor = Color.Transparent,
                                unfocusedContainerColor = Color.Transparent,
                                disabledContainerColor = Color.Transparent,
                                errorContainerColor = Color.Transparent,
                                focusedIndicatorColor = Color.Transparent,
                                unfocusedIndicatorColor = Color.Transparent,
                                disabledIndicatorColor = Color.Transparent,
                                errorIndicatorColor = Color.Transparent,
                            ),
                            onValueChange = {},
                            readOnly = true,
                        )

                        ExposedDropdownMenu(
                            modifier = Modifier
                                .width(IntrinsicSize.Max)
                                .wrapContentHeight()
                                .padding(horizontal = 8.dp, vertical = 4.dp),
                            expanded = expanded,
                            onDismissRequest = { expanded = false },
                            containerColor = LocalPalette.current.surfaceContainer,
                            shape = RoundedCornerShape(
                                bottomStart = 8.dp,
                                bottomEnd = 8.dp
                            ),
                            scrollState = rememberScrollState(),
                            matchAnchorWidth = true,
                        ) {

                            dropdownList.forEach { label ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            text = label,
                                            style = LocalTypography.current.quaternary.regular,
                                            color = LocalPalette.current.onSurface,
                                            fontSize = 18.sp
                                        )
                                    },
                                    colors = MenuDefaults.itemColors().copy(
                                        textColor = LocalPalette.current.onSurface,
                                    ),
                                    onClick = {
                                        expanded = false
                                        state = label
                                    },
                                )
                            }

                        }
                    }

                }


            }

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = "BPM: ${tapUiState.smoothed.toInt()}; IPM: ${tapUiState.instant.toInt()}",
                color = LocalPalette.current.onSurface
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = "MSPS: ${tapUiState.smoothed/60f}; MIPM: ${tapUiState.instant/60f}",
                color = LocalPalette.current.onSurface
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = "AVG BPM: ${tapUiState.points.tail?.data?.avg ?: 0f}",
                color = LocalPalette.current.onSurface
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = "AVG MPS: ${tapUiState.points.tail?.data?.avg
                    ?.let { avg -> avg / 60f }}",
                color = LocalPalette.current.onSurface
            )

            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                text = "WAVG MPS: ${tapUiState.points.tail?.data?.weightedAvg
                    ?.let { wavg -> wavg / 60f }}",
                color = LocalPalette.current.onSurface
            )

        }
    }

}

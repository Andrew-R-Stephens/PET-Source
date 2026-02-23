package com.tritiumgaming.feature.investigation.ui.toolbar.impl

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.impl.base.AnalyticsIcon
import com.tritiumgaming.core.ui.icon.impl.base.ConfigIcon
import com.tritiumgaming.core.ui.icon.impl.base.FootprintsIcon
import com.tritiumgaming.core.ui.icon.impl.base.StopwatchIcon
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.widgets.collapsebutton.CollapseButton
import com.tritiumgaming.feature.investigation.ui.toolbar.InvestigationToolRail
import com.tritiumgaming.feature.investigation.ui.toolbar.ScrollableToolbar
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarItem
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiActions
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ColumnScope.OperationToolbar(
    modifier: Modifier = Modifier,
    toolbarUiState: ToolbarUiState,
    toolbarUiActions: ToolbarUiActions,
    containerColor: Color// = Color.Unspecified
) {

    ScrollableToolbar(
        modifier = modifier,
        surfaceColor = containerColor,
        stickyContentStart = {

            ToolbarItem(
                modifier = Modifier
                    .size(48.dp)
                    .padding(2.dp),
                onClick = {
                    toolbarUiActions.onChangeToolbarCategory(ToolbarUiState.Category.TOOL_CONFIG)
                }
            ){
                ConfigIcon(
                    modifier = Modifier
                        .fillMaxSize(),
                    if(toolbarUiState.category == ToolbarUiState.Category.TOOL_CONFIG) {
                        IconVectorColors.defaults(
                            fillColor = LocalPalette.current.primary,
                            strokeColor = LocalPalette.current.primary,
                        )
                    } else {
                        IconVectorColors.defaults(
                            fillColor = LocalPalette.current.onSurface,
                            strokeColor = LocalPalette.current.onSurface,
                        )
                    }
                )
            }

        },
        stickyContentEnd = {

            ToolbarItem(
                modifier = Modifier
                    .size(48.dp),
                onClick = {}
            ){
                ResetButton(
                    modifier = Modifier,
                    tintColor = LocalPalette.current.onSurface,
                ) {
                    toolbarUiActions.onReset()
                }
            }

        },
        scrollContent = {

            ToolbarItem(
                modifier = Modifier
                    .size(48.dp),
                onClick = {
                    toolbarUiActions.onChangeToolbarCategory(ToolbarUiState.Category.TOOL_ANALYZER)
                }
            ) {
                AnalyticsIcon(
                    modifier = Modifier
                        .fillMaxSize(),
                    if(toolbarUiState.category == ToolbarUiState.Category.TOOL_ANALYZER) {
                        IconVectorColors.defaults(
                            fillColor = LocalPalette.current.primary,
                            strokeColor = LocalPalette.current.primary,
                        )
                    } else {
                        IconVectorColors.defaults(
                            fillColor = LocalPalette.current.onSurface,
                            strokeColor = LocalPalette.current.onSurface,
                        )
                    }
                )

            }

            ToolbarItem(
                modifier = Modifier
                    .size(48.dp),
                onClick = {
                    toolbarUiActions.onChangeToolbarCategory(ToolbarUiState.Category.TOOL_TIMERS)
                }
            ){
                StopwatchIcon(
                    modifier = Modifier
                        .fillMaxSize(),
                    if(toolbarUiState.category == ToolbarUiState.Category.TOOL_TIMERS) {
                        IconVectorColors.defaults(
                            fillColor = LocalPalette.current.primary,
                            strokeColor = LocalPalette.current.primary,
                        )
                    } else {
                        IconVectorColors.defaults(
                            fillColor = LocalPalette.current.onSurface,
                            strokeColor = LocalPalette.current.onSurface,
                        )
                    }
                )
            }

            ToolbarItem(
                modifier = Modifier
                    .size(48.dp),
                onClick = {
                    toolbarUiActions.onChangeToolbarCategory(ToolbarUiState.Category.TOOL_FOOTSTEP)
                }
            ){
                FootprintsIcon(
                    modifier = Modifier
                        .fillMaxSize(),
                    if(toolbarUiState.category == ToolbarUiState.Category.TOOL_FOOTSTEP) {
                        IconVectorColors.defaults(
                            fillColor = LocalPalette.current.primary,
                            strokeColor = LocalPalette.current.primary,
                        )
                    } else {
                        IconVectorColors.defaults(
                            fillColor = LocalPalette.current.onSurface,
                            strokeColor = LocalPalette.current.onSurface,
                        )
                    }
                )
            }
        }
    )

}

@Composable
fun RowScope.OperationToolbar(
    modifier: Modifier = Modifier,
    toolbarUiState: ToolbarUiState,
    toolbarUiActions: ToolbarUiActions,
    containerColor: Color
) {

    InvestigationToolRail(
        modifier = modifier,
        surfaceColor = containerColor,
        stickyContentStart = {

            ToolbarItem(
                modifier = Modifier
                    .size(48.dp)
                    .padding(2.dp),
                onClick = {
                    toolbarUiActions.onChangeToolbarCategory(ToolbarUiState.Category.TOOL_CONFIG)
                }
            ){
                ConfigIcon(
                    modifier = Modifier
                        .fillMaxSize(),
                    if(toolbarUiState.category == ToolbarUiState.Category.TOOL_CONFIG) {
                        IconVectorColors.defaults(
                            fillColor = LocalPalette.current.primary,
                            strokeColor = LocalPalette.current.primary,
                        )
                    } else {
                        IconVectorColors.defaults(
                            fillColor = LocalPalette.current.onSurface,
                            strokeColor = LocalPalette.current.onSurface,
                        )
                    }
                )
            }

        },
        stickyContentEnd = {

            ToolbarItem(
                modifier = Modifier
                    .size(48.dp),
                onClick = {}
            ){
                ResetButton(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    toolbarUiActions.onReset()
                }
            }

        },
        scrollContent = {

            ToolbarItem(
                modifier = Modifier
                    .size(48.dp),
                onClick = {
                    toolbarUiActions.onChangeToolbarCategory(ToolbarUiState.Category.TOOL_ANALYZER)
                }
            ){
                AnalyticsIcon(
                    modifier = Modifier
                        .fillMaxSize(),
                    if(toolbarUiState.category == ToolbarUiState.Category.TOOL_ANALYZER) {
                        IconVectorColors.defaults(
                            fillColor = LocalPalette.current.primary,
                            strokeColor = LocalPalette.current.primary,
                        )
                    } else {
                        IconVectorColors.defaults(
                            fillColor = LocalPalette.current.onSurface,
                            strokeColor = LocalPalette.current.onSurface,
                        )
                    }
                )
            }

            ToolbarItem(
                modifier = Modifier
                    .size(48.dp),
                onClick = {
                    toolbarUiActions.onChangeToolbarCategory(ToolbarUiState.Category.TOOL_FOOTSTEP)
                }
            ){
                StopwatchIcon(
                    modifier = Modifier
                        .fillMaxSize(),
                    if(toolbarUiState.category == ToolbarUiState.Category.TOOL_FOOTSTEP) {
                        IconVectorColors.defaults(
                            fillColor = LocalPalette.current.primary,
                            strokeColor = LocalPalette.current.primary,
                        )
                    } else {
                        IconVectorColors.defaults(
                            fillColor = LocalPalette.current.onSurface,
                            strokeColor = LocalPalette.current.onSurface,
                        )
                    }
                )
            }

            ToolbarItem(
                modifier = Modifier
                    .size(48.dp),
                onClick = {
                    toolbarUiActions.onChangeToolbarCategory(ToolbarUiState.Category.TOOL_FOOTSTEP)
                }
            ){
                FootprintsIcon(
                    modifier = Modifier
                        .fillMaxSize(),
                    if(toolbarUiState.category == ToolbarUiState.Category.TOOL_FOOTSTEP) {
                        IconVectorColors.defaults(
                            fillColor = LocalPalette.current.primary,
                            strokeColor = LocalPalette.current.primary,
                        )
                    } else {
                        IconVectorColors.defaults(
                            fillColor = LocalPalette.current.onSurface,
                            strokeColor = LocalPalette.current.onSurface,
                        )
                    }
                )
            }
        }
    )
}

@Composable
private fun ResetButton(
    modifier: Modifier = Modifier,
    tintColor: Color = Color.Unspecified,
    onClick: () -> Unit = {}
) {
    var isRunning by remember{ mutableStateOf(false) }

    val rotation by animateFloatAsState(
        targetValue = if(isRunning) 1f else 0f,
        animationSpec = spring(
            stiffness = StiffnessLow,
            dampingRatio = DampingRatioLowBouncy
        ),
        finishedListener = {
            isRunning = false
        },
        label = "",
    )

    Box(
        modifier = modifier
            .size(48.dp)
            .clickable {
                onClick()
                isRunning = true
            }
    ) {
        Image(
            painterResource(id = R.drawable.ic_control_reset),
            contentDescription = "Reset Drawable",
            colorFilter = ColorFilter.tint(tintColor),
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .clip(RectangleShape)
                .rotate(
                    if (isRunning) {
                        rotation * -360f
                    } else {
                        0f
                    }
                )
                .padding(6.dp)
        )
    }
}



@Composable
fun SanityButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isShownState: StateFlow<Boolean> = MutableStateFlow(false)
) {

    val foregroundColor = LocalPalette.current.onSurface

    Box(
        modifier = modifier
            .size(48.dp)
            //.border(1.5.dp, Color(foregroundColor), RoundedCornerShape(percent = 25))
            .clickable {
                onClick()
            }
    ) {

        when(LocalConfiguration.current.orientation) {
            ORIENTATION_PORTRAIT -> 90
            ORIENTATION_LANDSCAPE -> 180
            else -> 0
        }

        Image(
            painterResource(id = R.drawable.icon_sanityhead_skull),
            contentDescription = "Sanity Drawable",
            colorFilter = ColorFilter.tint(foregroundColor),
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .clip(RectangleShape)
        )
    }
}

@Composable
fun ModifiersButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isShownState: StateFlow<Boolean> = MutableStateFlow(false)
) {

    val foregroundColor = LocalPalette.current.onSurface

    Box(
        modifier = modifier
            .size(48.dp)
            //.border(1.5.dp, Color(foregroundColor), RoundedCornerShape(percent = 25))
            .clickable {
                onClick()
            }
    ) {

        when(LocalConfiguration.current.orientation) {
            ORIENTATION_PORTRAIT -> 90
            ORIENTATION_LANDSCAPE -> 180
            else -> 0
        }

        Image(
            painterResource(id = R.drawable.ic_eraser),
            contentDescription = "Modifiers Drawable",
            colorFilter = ColorFilter.tint(foregroundColor),
            modifier = Modifier
                .fillMaxSize()
                .align(Alignment.Center)
                .clip(RectangleShape)
        )
    }
}

@Composable
@Preview
fun IconPreview() {

    SelectiveTheme {

        Column{

            var isShownState by remember { mutableStateOf(false) }

            SanityButton()
            ResetButton()
            CollapseButton(
                isCollapsed = false,
                icon = R.drawable.ic_arrow_chevron_right,
                disabledRotationVertical = 90,
                disabledRotationHorizontal = 90,
                enabledRotationAddition = 180,
                onClick = { isShownState = !isShownState }
            )
        }
    }

}
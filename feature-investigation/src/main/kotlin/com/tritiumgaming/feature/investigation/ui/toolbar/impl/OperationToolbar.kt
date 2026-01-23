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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.button.CollapseButton
import com.tritiumgaming.core.ui.icon.color.IconVectorColors
import com.tritiumgaming.core.ui.theme.SelectiveTheme
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.vector.getExitVector
import com.tritiumgaming.core.ui.vector.getGearVector
import com.tritiumgaming.core.ui.vector.getInfoVector
import com.tritiumgaming.feature.investigation.ui.toolbar.InvestigationToolRail
import com.tritiumgaming.feature.investigation.ui.toolbar.InvestigationToolbar
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarItem
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiActions
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ColumnScope.OperationToolbar(
    modifier: Modifier = Modifier,
    toolbarUiState: ToolbarUiState,
    toolbarUiActions: ToolbarUiActions
) {

    InvestigationToolbar(
        modifier = modifier,
        stickyContentStart = {

            ToolbarItem(
                modifier = Modifier
                    .size(48.dp),
                onClick = {}
            ){
                CollapseButton(
                    modifier = Modifier
                        .fillMaxSize(),
                    isCollapsed = toolbarUiState.isCollapsed,
                    icon = R.drawable.ic_arrow_chevron_right,
                    disabledRotationVertical = 90,
                    disabledRotationHorizontal = 90,
                    enabledRotationAddition = 180,
                    onClick = { toolbarUiActions.onToggleCollapseToolbar() }
                )
            }

        },
        stickyContentEnd = {

            ToolbarItem(
                modifier = Modifier
                    .size(48.dp),
                onClick = {}
            ){
                ResetButton {
                    toolbarUiActions.onReset()
                }
            }

        }
    ) {

        ToolbarItem(
            modifier = Modifier
                .size(48.dp),
            onClick = {
                toolbarUiActions.onChangeToolbarCategory(ToolbarUiState.Category.TOOL_CONFIG)
            }
        ){
            Image(
                modifier = Modifier
                    .fillMaxSize(),
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
            modifier = Modifier
                .size(48.dp),
            onClick = {
                toolbarUiActions.onChangeToolbarCategory(ToolbarUiState.Category.TOOL_ANALYZER)
            }
        ){
            Image(
                modifier = Modifier
                    .fillMaxSize(),
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
            modifier = Modifier
                .size(48.dp),
            onClick = {
                toolbarUiActions.onChangeToolbarCategory(ToolbarUiState.Category.TOOL_FOOTSTEP)
            }
        ){
            Image(
                modifier = Modifier,
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

@Composable
fun RowScope.OperationToolbar(
    modifier: Modifier = Modifier,
    toolbarUiState: ToolbarUiState,
    toolbarUiActions: ToolbarUiActions
) {

    InvestigationToolRail(
        modifier = modifier,
        stickyContentStart = {

            ToolbarItem(
                modifier = Modifier
                    .size(48.dp),
                onClick = {}
            ){
                CollapseButton(
                    modifier = Modifier
                        .fillMaxSize(),
                    isCollapsed = toolbarUiState.isCollapsed,
                    icon = R.drawable.ic_arrow_chevron_right,
                    disabledRotationVertical = 90,
                    disabledRotationHorizontal = 90,
                    enabledRotationAddition = 180,
                    onClick = { toolbarUiActions.onToggleCollapseToolbar() }
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

        }
    ) {

        ToolbarItem(
            modifier = Modifier
                .size(48.dp),
            onClick = {
                toolbarUiActions.onChangeToolbarCategory(ToolbarUiState.Category.TOOL_CONFIG)
            }
        ){
            Image(
                modifier = Modifier
                    .fillMaxSize(),
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
            modifier = Modifier
                .size(48.dp),
            onClick = {
                toolbarUiActions.onChangeToolbarCategory(ToolbarUiState.Category.TOOL_ANALYZER)
            }
        ){
            Image(
                modifier = Modifier
                    .fillMaxSize(),
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
            modifier = Modifier
                .size(48.dp),
            onClick = {
                toolbarUiActions.onChangeToolbarCategory(ToolbarUiState.Category.TOOL_FOOTSTEP)
            }
        ){
            Image(
                modifier = Modifier
                    .fillMaxSize(),
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

@Composable
fun ResetButton(
    modifier: Modifier = Modifier,
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

    val foregroundColor = LocalPalette.current.onSurface

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
            colorFilter = ColorFilter.tint(foregroundColor),
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
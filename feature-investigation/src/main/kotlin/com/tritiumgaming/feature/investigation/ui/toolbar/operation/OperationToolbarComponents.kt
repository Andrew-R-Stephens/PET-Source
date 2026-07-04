package com.tritiumgaming.feature.investigation.ui.toolbar.operation

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import android.content.res.Configuration.ORIENTATION_PORTRAIT
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.material3.MenuItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.impl.base.AnalyticsIcon
import com.tritiumgaming.core.ui.icon.impl.base.ConfigIcon
import com.tritiumgaming.core.ui.icon.impl.base.FootprintsIcon
import com.tritiumgaming.core.ui.icon.impl.base.GeneticsIcon
import com.tritiumgaming.core.ui.icon.impl.base.StopwatchIcon
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


@Composable
internal fun ResetButton(
    modifier: Modifier = Modifier,
    category: OperationToolbarUiState.Category,
    onReset: (OperationToolbarUiState.ResetOption?) -> Unit
) {
    var showOptions by remember { mutableStateOf(false) }

    ToolbarItem(
        modifier = modifier,
        onLongClick = { showOptions = true }
    ) { modifier ->
        Box {
            ResetIcon(
                modifier = modifier
                    .fillMaxSize(),
                onClick = { onReset(null) },
                onLongClick = { showOptions = true }
            )

            DropdownMenu(
                expanded = showOptions,
                onDismissRequest = { showOptions = false },
                containerColor = LocalPalette.current.surfaceContainerHighest
            ) {
                when (category) {
                    OperationToolbarUiState.Category.TOOL_CONFIG -> {
                        DropdownMenuItem(
                            text = { Text(
                                stringResource(
                                    OperationToolbarUiState.ResetOption.TOOL_CONFIG.title)
                            ) },
                            onClick = {
                                onReset(OperationToolbarUiState.ResetOption.TOOL_CONFIG)
                                showOptions = false
                            },
                            colors = MenuDefaults.itemColors(
                                textColor = LocalPalette.current.onSurface
                            )
                        )
                    }
                    OperationToolbarUiState.Category.TOOL_TRAITS -> {
                        DropdownMenuItem(
                            text = { Text(
                                stringResource(
                                    OperationToolbarUiState.ResetOption.TOOL_TRAITS.title)
                            ) },
                            onClick = {
                                onReset(OperationToolbarUiState.ResetOption.TOOL_TRAITS)
                                showOptions = false
                            },
                            colors = MenuDefaults.itemColors(
                                textColor = LocalPalette.current.onSurface
                            )
                        )
                    }
                    OperationToolbarUiState.Category.TOOL_FOOTSTEP -> {
                        DropdownMenuItem(
                            text = { Text(
                                stringResource(
                                    OperationToolbarUiState.ResetOption.TOOL_FOOTSTEP.title)
                            ) },
                            onClick = {
                                onReset(OperationToolbarUiState.ResetOption.TOOL_FOOTSTEP)
                                showOptions = false
                            },
                            colors = MenuDefaults.itemColors(
                                textColor = LocalPalette.current.onSurface
                            )
                        )
                    }
                    OperationToolbarUiState.Category.TOOL_TIMERS -> {
                        DropdownMenuItem(
                            text = { Text(
                                stringResource(
                                    OperationToolbarUiState.ResetOption.TOOL_TIMERS.title)
                            ) },
                            onClick = {
                                onReset(OperationToolbarUiState.ResetOption.TOOL_TIMERS)
                                showOptions = false
                            },
                            colors = MenuDefaults.itemColors(
                                textColor = LocalPalette.current.onSurface
                            )
                        )
                    }
                    else -> {}
                }

                DropdownMenuItem(
                    text = { Text(
                        stringResource(
                            OperationToolbarUiState.ResetOption.JOURNAL.title)
                    ) },
                    onClick = {
                        onReset(OperationToolbarUiState.ResetOption.JOURNAL)
                        showOptions = false
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = LocalPalette.current.onSurface
                    )
                )
                DropdownMenuItem(
                    text = { Text(
                        stringResource(
                            OperationToolbarUiState.ResetOption.MISSION.title)
                    ) },
                    onClick = {
                        onReset(OperationToolbarUiState.ResetOption.MISSION)
                        showOptions = false
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = LocalPalette.current.onSurface
                    )
                )
                DropdownMenuItem(
                    text = { Text(stringResource(R.string.investigation_label_all)) },
                    onClick = {
                        onReset(OperationToolbarUiState.ResetOption.ALL)
                        showOptions = false
                    },
                    colors = MenuDefaults.itemColors(
                        textColor = LocalPalette.current.onSurface
                    )
                )
            }

        }
    }
}

@Composable
internal fun BpmButton(
    modifier: Modifier = Modifier,
    category: OperationToolbarUiState.Category,
    onCategoryChange: (OperationToolbarUiState.Category, Boolean) -> Unit
) {
    ToolbarItem(
        modifier = modifier,
        onClick = {
            onCategoryChange(OperationToolbarUiState.Category.TOOL_FOOTSTEP, true)
        }
    ) { modifier ->
        FootprintsIcon(
            modifier = modifier
                .fillMaxSize(),
            if (category == OperationToolbarUiState.Category.TOOL_FOOTSTEP) {
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

@Composable
internal fun StopwatchButton(
    modifier: Modifier = Modifier,
    category: OperationToolbarUiState.Category,
    onCategoryChange: (OperationToolbarUiState.Category, Boolean) -> Unit
) {
    ToolbarItem(
        modifier = modifier,
        onClick = {
            onCategoryChange(OperationToolbarUiState.Category.TOOL_TIMERS, true)
        }
    ) { modifier ->
        StopwatchIcon(
            modifier = modifier
                .fillMaxSize(),
            if (category == OperationToolbarUiState.Category.TOOL_TIMERS) {
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

@Composable
internal fun AnalyticsButton(
    modifier: Modifier = Modifier,
    category: OperationToolbarUiState.Category,
    onCategoryChange: (OperationToolbarUiState.Category, Boolean) -> Unit
) {
    ToolbarItem(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxSize(),
        onClick = {
            onCategoryChange(OperationToolbarUiState.Category.TOOL_ANALYZER, true)
        }
    ) { modifier ->
        AnalyticsIcon(
            modifier = modifier
                .fillMaxSize(),
            if (category == OperationToolbarUiState.Category.TOOL_ANALYZER) {
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

@Composable
internal fun ConfigButton(
    modifier: Modifier = Modifier,
    category: OperationToolbarUiState.Category,
    onCategoryChange: (OperationToolbarUiState.Category, Boolean) -> Unit
) {
    ToolbarItem(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxSize(),
        onClick = {
            onCategoryChange(OperationToolbarUiState.Category.TOOL_CONFIG, true)
        }
    ) { modifier ->
        ConfigIcon(
            modifier = modifier
                .fillMaxSize(),
            if (category == OperationToolbarUiState.Category.TOOL_CONFIG) {
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

@Composable
internal fun TraitsButton(
    modifier: Modifier = Modifier,
    category: OperationToolbarUiState.Category,
    onCategoryChange: (OperationToolbarUiState.Category, Boolean) -> Unit
) {
    ToolbarItem(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxSize(),
        onClick = {
            onCategoryChange(OperationToolbarUiState.Category.TOOL_TRAITS, true)
        }
    ) { modifier ->
        GeneticsIcon(
            modifier = modifier
                .fillMaxSize(),
            if (category == OperationToolbarUiState.Category.TOOL_TRAITS) {
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun ResetIcon(
    modifier: Modifier = Modifier,
    tintColor: Color = Color.Unspecified,
    onClick: () -> Unit = {},
    onLongClick: (() -> Unit)? = null
) {
    var rotationTarget by remember { mutableFloatStateOf(0f) }

    val rotation by animateFloatAsState(
        targetValue = rotationTarget,
        animationSpec = spring(
            stiffness = StiffnessLow,
            dampingRatio = DampingRatioLowBouncy
        ),
        label = "ResetRotation"
    )

    Icon(
        painter = painterResource(id = R.drawable.ic_control_reset),
        contentDescription = "Reset Button",
        tint = tintColor,
        modifier = modifier
            .fillMaxSize()
            .aspectRatio(1f)
            .combinedClickable(
                onClick = {
                    rotationTarget -= 360f
                    onClick()
                },
                onLongClick = onLongClick
            )
            .padding(2.dp)
            .rotate(rotation)
    )
}

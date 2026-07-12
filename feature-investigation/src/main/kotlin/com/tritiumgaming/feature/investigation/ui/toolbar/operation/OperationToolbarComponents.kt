package com.tritiumgaming.feature.investigation.ui.toolbar.operation

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MenuDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.icon.impl.base.AnalyticsIcon
import com.tritiumgaming.core.ui.icon.impl.base.ConfigIcon
import com.tritiumgaming.core.ui.icon.impl.base.FootprintsIcon
import com.tritiumgaming.core.ui.icon.impl.base.GeneticsIcon
import com.tritiumgaming.core.ui.icon.impl.base.StopwatchIcon
import com.tritiumgaming.core.ui.icon.impl.composite.ResetAltIcon
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.vector.color.IconVectorColors
import com.tritiumgaming.core.ui.widgets.tooltip.CommonTooltip
import com.tritiumgaming.feature.investigation.ui.toolbar.ToolbarItem


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
                modifier = Modifier.wrapContentWidth(),
                expanded = showOptions,
                onDismissRequest = { showOptions = false },
                containerColor = LocalPalette.current.surfaceContainerHighest
            ) {
                val iconColors = IconVectorColors.defaults(
                    fillColor = LocalPalette.current.onSurface
                )

                when (category) {
                    OperationToolbarUiState.Category.TOOL_CONFIG -> {
                        ResetDropdownMenuItem(
                            tooltipText = stringResource(
                                OperationToolbarUiState.ResetOption.TOOL_CONFIG.title),
                            iconColors = iconColors,
                            subImage = { modifier, colors -> ConfigIcon(modifier, colors) },
                            onClick = {
                                onReset(OperationToolbarUiState.ResetOption.TOOL_CONFIG)
                                showOptions = false
                            }
                        )
                    }
                    OperationToolbarUiState.Category.TOOL_TRAITS -> {
                        ResetDropdownMenuItem(
                            tooltipText = stringResource(
                                OperationToolbarUiState.ResetOption.TOOL_TRAITS.title),
                            iconColors = iconColors,
                            subImage = { modifier, colors -> GeneticsIcon(modifier, colors) },
                            onClick = {
                                onReset(OperationToolbarUiState.ResetOption.TOOL_TRAITS)
                                showOptions = false
                            }
                        )
                    }
                    OperationToolbarUiState.Category.TOOL_FOOTSTEP -> {
                        ResetDropdownMenuItem(
                            tooltipText = stringResource(
                                OperationToolbarUiState.ResetOption.TOOL_FOOTSTEP.title),
                            iconColors = iconColors,
                            subImage = { modifier, colors -> FootprintsIcon(modifier, colors) },
                            onClick = {
                                onReset(OperationToolbarUiState.ResetOption.TOOL_FOOTSTEP)
                                showOptions = false
                            }
                        )
                    }
                    OperationToolbarUiState.Category.TOOL_TIMERS -> {
                        ResetDropdownMenuItem(
                            tooltipText = stringResource(
                                OperationToolbarUiState.ResetOption.TOOL_TIMERS.title),
                            iconColors = iconColors,
                            subImage = R.drawable.ic_stopwatch,
                            onClick = {
                                onReset(OperationToolbarUiState.ResetOption.TOOL_TIMERS)
                                showOptions = false
                            }
                        )
                    }
                    OperationToolbarUiState.Category.TOOL_ANALYZER -> {
                        ResetDropdownMenuItem(
                            tooltipText = stringResource(
                                OperationToolbarUiState.ResetOption.TOOL_ANALYZER.title),
                            iconColors = iconColors,
                            subImage = { modifier, colors -> AnalyticsIcon(modifier, colors) },
                            onClick = {
                                onReset(OperationToolbarUiState.ResetOption.TOOL_ANALYZER)
                                showOptions = false
                            }
                        )
                    }
                    else -> {}
                }

                ResetDropdownMenuItem(
                    tooltipText = stringResource(OperationToolbarUiState.ResetOption.JOURNAL.title),
                    iconColors = iconColors,
                    subImage = { modifier, colors ->
                        Icon(
                            painter = painterResource(R.drawable.icon_nav_evidence),
                            contentDescription = null,
                            modifier = modifier,
                            tint = colors.fillColor
                        )
                    },
                    onClick = {
                        onReset(OperationToolbarUiState.ResetOption.JOURNAL)
                        showOptions = false
                    }
                )

                ResetDropdownMenuItem(
                    tooltipText = stringResource(
                        OperationToolbarUiState.ResetOption.OBJECTIVE_BOARD.title),
                    iconColors = iconColors,
                    subImage = { modifier, colors ->
                        Icon(
                            painter = painterResource(R.drawable.icon_nav_tasks),
                            contentDescription = null,
                            modifier = modifier,
                            tint = colors.fillColor
                        )
                    },
                    onClick = {
                        onReset(OperationToolbarUiState.ResetOption.OBJECTIVE_BOARD)
                        showOptions = false
                    }
                )

                /*ResetDropdownMenuItem(
                    tooltipText = stringResource(R.string.investigation_label_all),
                    iconColors = iconColors,
                    onClick = {
                        onReset(OperationToolbarUiState.ResetOption.ALL)
                        showOptions = false
                    }
                )*/
            }

        }
    }
}

@Composable
private fun ResetDropdownMenuItem(
    tooltipText: String,
    iconColors: IconVectorColors,
    subImage: (@Composable (Modifier, IconVectorColors) -> Unit)? = null,
    onClick: () -> Unit
) {
    CommonTooltip(
        modifier = Modifier.size(48.dp),
        tooltipText = "${stringResource(R.string.general_label_reset)} $tooltipText"
    ) { modifier ->
        DropdownMenuItem(
            modifier = modifier,
            text = {
                ResetAltIcon(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxSize(),
                    colors = iconColors,
                    subImage = subImage ?: { _, _ -> }
                )
            },
            onClick = onClick,
            contentPadding = PaddingValues(horizontal = 8.dp),
            colors = MenuDefaults.itemColors(
                textColor = LocalPalette.current.onSurface
            )
        )
    }
}

@Composable
private fun ResetDropdownMenuItem(
    tooltipText: String,
    iconColors: IconVectorColors,
    @DrawableRes subImage: Int,
    onClick: () -> Unit
) {
    CommonTooltip(
        modifier = Modifier.size(48.dp),
        tooltipText = "${stringResource(R.string.general_label_reset)} $tooltipText"
    ) { modifier ->
        DropdownMenuItem(
            modifier = modifier,
            text = {
                ResetAltIcon(
                    modifier = Modifier
                        .aspectRatio(1f)
                        .fillMaxSize(),
                    colors = iconColors,
                    subImage = subImage
                )
            },
            onClick = onClick,
            contentPadding = PaddingValues(horizontal = 8.dp),
            colors = MenuDefaults.itemColors(
                textColor = LocalPalette.current.onSurface
            )
        )
    }
}

@Composable
private fun ToolbarCategoryButton(
    modifier: Modifier = Modifier,
    category: OperationToolbarUiState.Category,
    targetCategory: OperationToolbarUiState.Category,
    onCategoryChange: (OperationToolbarUiState.Category, Boolean) -> Unit,
    onReset: (OperationToolbarUiState.ResetOption?) -> Unit,
    resetOption: OperationToolbarUiState.ResetOption,
    icon: @Composable (Modifier, IconVectorColors) -> Unit,
    tooltipText: String
) {
    var showOptions by remember { mutableStateOf(false) }
    val isSelected = category == targetCategory
    val iconColors = if (isSelected) {
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

    ToolbarItem(
        modifier = modifier,
        onClick = { onCategoryChange(targetCategory, true) },
        onLongClick = { showOptions = true }
    ) { itemModifier ->
        Box {
            icon(itemModifier.fillMaxSize(), iconColors)

            DropdownMenu(
                modifier = Modifier.wrapContentWidth(),
                expanded = showOptions,
                onDismissRequest = { showOptions = false },
                containerColor = LocalPalette.current.surfaceContainerHighest
            ) {
                ResetDropdownMenuItem(
                    tooltipText = tooltipText,
                    iconColors = IconVectorColors.defaults(fillColor = LocalPalette.current.onSurface),
                    subImage = icon,
                    onClick = {
                        onReset(resetOption)
                        showOptions = false
                    }
                )
            }
        }
    }
}

@Composable
private fun ToolbarCategoryButton(
    modifier: Modifier = Modifier,
    category: OperationToolbarUiState.Category,
    targetCategory: OperationToolbarUiState.Category,
    onCategoryChange: (OperationToolbarUiState.Category, Boolean) -> Unit,
    onReset: (OperationToolbarUiState.ResetOption?) -> Unit,
    resetOption: OperationToolbarUiState.ResetOption,
    @DrawableRes icon: Int,
    tooltipText: String
) {
    var showOptions by remember { mutableStateOf(false) }
    val isSelected = category == targetCategory
    val iconColors = if (isSelected) {
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

    ToolbarItem(
        modifier = modifier,
        onClick = { onCategoryChange(targetCategory, true) },
        onLongClick = { showOptions = true }
    ) { itemModifier ->
        Box {
            Image(
                modifier = itemModifier.fillMaxSize(),
                colorFilter = ColorFilter.tint(iconColors.fillColor),
                painter = painterResource(icon),
                contentDescription = null
            )

            DropdownMenu(
                modifier = Modifier.wrapContentWidth(),
                expanded = showOptions,
                onDismissRequest = { showOptions = false },
                containerColor = LocalPalette.current.surfaceContainerHighest
            ) {
                ResetDropdownMenuItem(
                    tooltipText = tooltipText,
                    iconColors = IconVectorColors.defaults(fillColor = LocalPalette.current.onSurface),
                    subImage = icon,
                    onClick = {
                        onReset(resetOption)
                        showOptions = false
                    }
                )
            }
        }
    }
}

@Composable
internal fun BpmButton(
    modifier: Modifier = Modifier,
    category: OperationToolbarUiState.Category,
    onCategoryChange: (OperationToolbarUiState.Category, Boolean) -> Unit,
    onReset: (OperationToolbarUiState.ResetOption?) -> Unit
) {
    ToolbarCategoryButton(
        modifier = modifier,
        category = category,
        targetCategory = OperationToolbarUiState.Category.TOOL_FOOTSTEP,
        onCategoryChange = onCategoryChange,
        onReset = onReset,
        resetOption = OperationToolbarUiState.ResetOption.TOOL_FOOTSTEP,
        icon = { modifier, colors -> FootprintsIcon(modifier, colors) },
        tooltipText = stringResource(OperationToolbarUiState.ResetOption.TOOL_FOOTSTEP.title)
    )
}

@Composable
internal fun StopwatchButton(
    modifier: Modifier = Modifier,
    category: OperationToolbarUiState.Category,
    onCategoryChange: (OperationToolbarUiState.Category, Boolean) -> Unit,
    onReset: (OperationToolbarUiState.ResetOption?) -> Unit
) {
    ToolbarCategoryButton(
        modifier = modifier,
        category = category,
        targetCategory = OperationToolbarUiState.Category.TOOL_TIMERS,
        onCategoryChange = onCategoryChange,
        onReset = onReset,
        resetOption = OperationToolbarUiState.ResetOption.TOOL_TIMERS,
        icon = R.drawable.ic_stopwatch,
        tooltipText = stringResource(OperationToolbarUiState.ResetOption.TOOL_TIMERS.title)
    )
}

@Composable
internal fun AnalyticsButton(
    modifier: Modifier = Modifier,
    category: OperationToolbarUiState.Category,
    onCategoryChange: (OperationToolbarUiState.Category, Boolean) -> Unit,
    onReset: (OperationToolbarUiState.ResetOption?) -> Unit
) {
    ToolbarCategoryButton(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxSize(),
        category = category,
        targetCategory = OperationToolbarUiState.Category.TOOL_ANALYZER,
        onCategoryChange = onCategoryChange,
        onReset = onReset,
        resetOption = OperationToolbarUiState.ResetOption.TOOL_ANALYZER,
        icon = { modifier, colors -> AnalyticsIcon(modifier, colors) },
        tooltipText = stringResource(OperationToolbarUiState.ResetOption.TOOL_ANALYZER.title)
    )
}

@Composable
internal fun ConfigButton(
    modifier: Modifier = Modifier,
    category: OperationToolbarUiState.Category,
    onCategoryChange: (OperationToolbarUiState.Category, Boolean) -> Unit,
    onReset: (OperationToolbarUiState.ResetOption?) -> Unit
) {
    ToolbarCategoryButton(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxSize(),
        category = category,
        targetCategory = OperationToolbarUiState.Category.TOOL_CONFIG,
        onCategoryChange = onCategoryChange,
        onReset = onReset,
        resetOption = OperationToolbarUiState.ResetOption.TOOL_CONFIG,
        icon = { modifier, colors -> ConfigIcon(modifier, colors) },
        tooltipText = stringResource(OperationToolbarUiState.ResetOption.TOOL_CONFIG.title)
    )
}

@Composable
internal fun TraitsButton(
    modifier: Modifier = Modifier,
    category: OperationToolbarUiState.Category,
    onCategoryChange: (OperationToolbarUiState.Category, Boolean) -> Unit,
    onReset: (OperationToolbarUiState.ResetOption?) -> Unit
) {
    ToolbarCategoryButton(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxSize(),
        category = category,
        targetCategory = OperationToolbarUiState.Category.TOOL_TRAITS,
        onCategoryChange = onCategoryChange,
        onReset = onReset,
        resetOption = OperationToolbarUiState.ResetOption.TOOL_TRAITS,
        icon = { modifier, colors -> GeneticsIcon(modifier, colors) },
        tooltipText = stringResource(OperationToolbarUiState.ResetOption.TOOL_TRAITS.title)
    )
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

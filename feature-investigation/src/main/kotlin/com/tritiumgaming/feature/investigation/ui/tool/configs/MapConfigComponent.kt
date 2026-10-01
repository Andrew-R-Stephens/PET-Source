package com.tritiumgaming.feature.investigation.ui.tool.configs

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.LocalTypography
import com.tritiumgaming.core.ui.widgets.tooltip.CommonTooltip
import com.tritiumgaming.data.map.simple.mappers.SimpleMapResources.MapTitle
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.OperationConfigUiColors
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.dropdown.OperationConfigDropdown

@Composable
internal fun MapConfigControl(
    modifier: Modifier = Modifier,
    dropdownOptions: List<Int>,
    isDropdownEnabled: Boolean,
    dropdownLabel: Int,
    colors: OperationConfigUiColors,
    onDropdownSelect: (Int) -> Unit
) {
    val textStyle = LocalTypography.current.quaternary.regular

    val icon: @Composable (Modifier) -> Unit = { modifier ->

        CommonTooltip(
            modifier = Modifier,
            tooltipText = stringResource(R.string.investigation_timer_maplabel)
        ) {
            Image(
                modifier = modifier,
                contentScale = ContentScale.Inside,
                alignment = Alignment.Center,
                painter = painterResource(R.drawable.icon_nav_mapmenu2),
                colorFilter = ColorFilter.tint(colors.onColor),
                contentDescription = ""
            )
        }
    }

    OperationConfigDropdown(
        modifier = modifier,
        icon = { icon(it) },
        options = dropdownOptions,
        enabled = isDropdownEnabled,
        label = dropdownLabel,
        onSelect = onDropdownSelect,
        textStyle = textStyle,
        onColor = colors.onColor,
        expandedColor = colors.color,
    )
}

internal data class MapConfigUiState(
    internal val name: MapTitle = MapTitle.BLEASDALE_FARMHOUSE,
    internal val enabled: Boolean = true,
    internal val allMaps: List<MapTitle> = emptyList()
)
package com.tritiumgaming.feature.investigation.ui.tool.configs

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.LocalPalette
import com.tritiumgaming.core.ui.theme.LocalTypography
import com.tritiumgaming.core.ui.widgets.tooltip.CommonTooltip
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.dropdown.OperationConfigDropdown
import com.tritiumgaming.shared.data.difficultysetting.mapper.DifficultySettingResources.Weather

@Composable
internal fun WeatherConfigComponent(
    modifier: Modifier = Modifier,
    @DrawableRes icon: Int,
    dropdownOptions: List<Int>,
    isDropdownEnabled: Boolean,
    dropdownLabel: Int,
    onDropdownSelect: (Int) -> Unit
) {
    val textStyle = LocalTypography.current.quaternary.regular
    val color = LocalPalette.current.surfaceContainer
    val onColor = LocalPalette.current.onSurface

    val icon: @Composable (Modifier) -> Unit = { modifier ->
        CommonTooltip(
            modifier = Modifier,
            tooltipText = stringResource(R.string.difficulty_setting_title_weather)
        ) {
            Image(
                modifier = modifier,
                contentScale = ContentScale.Inside,
                alignment = Alignment.Center,
                painter = painterResource(icon),
                colorFilter = ColorFilter.tint(onColor),
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
        onColor = onColor,
        expandedColor = color,
    )
}

internal data class WeatherUiState(
    val weather: Weather = Weather.RANDOM,
    val enabled: Boolean = true
)

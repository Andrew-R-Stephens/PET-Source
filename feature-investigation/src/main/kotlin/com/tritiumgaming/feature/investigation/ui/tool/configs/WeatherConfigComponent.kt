package com.tritiumgaming.feature.investigation.ui.tool.configs

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.tritiumgaming.core.ui.theme.LocalUiConfiguration
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.ConfigActionsBundle
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.ConfigStateBundle
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.carousel.OperationConfigCarousel
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.dropdown.OperationConfigDropdown
import com.tritiumgaming.shared.data.preferences.properties.DensityType
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
        Image(
            modifier = modifier,
            contentScale = ContentScale.Inside,
            alignment = Alignment.Center,
            painter = painterResource(icon),
            colorFilter = ColorFilter.tint(onColor),
            contentDescription = ""
        )
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

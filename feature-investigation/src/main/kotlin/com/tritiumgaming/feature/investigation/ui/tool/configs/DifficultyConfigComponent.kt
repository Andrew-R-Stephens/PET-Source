package com.tritiumgaming.feature.investigation.ui.tool.configs

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.LocalUiConfiguration
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.ConfigActionsBundle
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.ConfigStateBundle
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.carousel.OperationConfigCarousel
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.dropdown.OperationConfigDropdown
import com.tritiumgaming.shared.data.preferences.properties.DensityType
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyTitle
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyTitle.AMATEUR


@Composable
internal fun DifficultyConfigControl(
    modifier: Modifier = Modifier,
    carouselLabel: Int,
    isCarouselEnabled: Boolean,
    onCarouselLeftClick: () -> Unit,
    onCarouselRightClick: () -> Unit,
    dropdownOptions: List<Int>,
    isDropdownEnabled: Boolean,
    dropdownLabel: Int,
    onDropdownSelect: (Int) -> Unit
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
                options = dropdownOptions,
                enabled = isDropdownEnabled,
                label = dropdownLabel,
                onSelect = onDropdownSelect,
                icon = { icon(it) },
                textStyle = textStyle,
                expandedColor = color,
                onColor = onColor,
            )
        }
        else -> {
            OperationConfigCarousel(
                modifier = modifier,
                label = carouselLabel,
                enabled = isCarouselEnabled,
                onLeftClick = onCarouselLeftClick,
                onRightClick = onCarouselRightClick,
                leadingIcon = { icon(it) },
                textStyle = textStyle,
                onColor = onColor,
            )
        }
    }
}

internal data class DifficultyConfigUiState(
    internal val name: DifficultyTitle = AMATEUR,
    val allDifficulties: List<DifficultyTitle> = emptyList()
)
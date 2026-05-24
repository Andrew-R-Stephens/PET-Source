package com.tritiumgaming.feature.investigation.ui.tool.configs

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.OperationConfigUiColors
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.dropdown.OperationConfigDropdown
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyTitle
import com.tritiumgaming.shared.data.difficulty.mapper.DifficultyResources.DifficultyType


@Composable
internal fun DifficultyConfigControl(
    modifier: Modifier = Modifier,
    dropdownOptions: List<Int>,
    isDropdownEnabled: Boolean,
    dropdownLabel: Int,
    colors: OperationConfigUiColors,
    onDropdownSelect: (Int) -> Unit
) {
    val textStyle = LocalTypography.current.quaternary.regular

    val icon: @Composable (Modifier) -> Unit = { modifier ->
        Image(
            modifier = modifier,
            contentScale = ContentScale.Inside,
            alignment = Alignment.Center,
            painter = painterResource(R.drawable.ic_puzzle),
            colorFilter = ColorFilter.tint(colors.onColor),
            contentDescription = ""
        )
    }

    OperationConfigDropdown(
        modifier = modifier,
        options = dropdownOptions,
        enabled = isDropdownEnabled,
        label = dropdownLabel,
        onSelect = onDropdownSelect,
        icon = { icon(it) },
        textStyle = textStyle,
        expandedColor = colors.color,
        onColor = colors.onColor,
    )
}

internal data class DifficultyConfigUiState(
    internal val type: DifficultyType = DifficultyType.AMATEUR,
    internal val name: DifficultyTitle = DifficultyTitle.AMATEUR,
    val allDifficulties: List<DifficultyTitle> = emptyList()
)
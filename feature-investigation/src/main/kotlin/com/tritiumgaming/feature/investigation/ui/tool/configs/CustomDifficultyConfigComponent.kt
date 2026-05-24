package com.tritiumgaming.feature.investigation.ui.tool.configs

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.core.ui.widgets.dropdownlist.DropdownList
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.OperationConfigUiColors

@Composable
internal fun CustomDifficultyConfigControl(
    modifier: Modifier = Modifier,
    dropdownOptions: List<Int>,
    isDropdownEnabled: Boolean,
    dropdownLabel: Int,
    colors: OperationConfigUiColors,
    onDropdownSelect: (Int) -> Unit,
    editButtonComponent: @Composable (Modifier) -> Unit
) {
    val textStyle = LocalTypography.current.quaternary.regular

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        DropdownList(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
                .align(Alignment.CenterVertically),
            options = dropdownOptions,
            enabled = isDropdownEnabled,
            label = dropdownLabel,
            onSelect = onDropdownSelect,
            textStyle = textStyle,
            color = colors.color,
            onColor = colors.onColor
        )

        editButtonComponent(
            Modifier
                .size(36.dp)
                .padding(8.dp)
        )

    }
}

@Composable
internal fun CustomDifficultyConfigControl(
    modifier: Modifier = Modifier,
    dropdownOptions: List<String>,
    isDropdownEnabled: Boolean,
    dropdownLabel: String,
    colors: OperationConfigUiColors,
    onDropdownSelect: (Int) -> Unit,
    editButtonComponent: @Composable (Modifier) -> Unit
) {
    val textStyle = LocalTypography.current.quaternary.regular

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        DropdownList(
            modifier = Modifier
                .weight(1f)
                .wrapContentHeight()
                .align(Alignment.CenterVertically),
            options = dropdownOptions,
            enabled = isDropdownEnabled,
            label = dropdownLabel,
            onSelect = onDropdownSelect,
            textStyle = textStyle,
            color = colors.color,
            onColor = colors.onColor,
            selectionFontSize = 18.sp,
            optionsFontSize = 14.sp
        )

        editButtonComponent(
            Modifier
                .size(36.dp)
                .padding(8.dp)
        )

    }
}

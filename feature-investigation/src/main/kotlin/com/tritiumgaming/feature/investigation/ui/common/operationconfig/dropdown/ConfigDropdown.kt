package com.tritiumgaming.feature.investigation.ui.common.operationconfig.dropdown

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.widgets.dropdownlist.DropdownList

@Composable
fun OperationConfigDropdown(
    modifier: Modifier = Modifier,
    icon: @Composable (Modifier) -> Unit = {},
    options: List<Int>,
    enabled: Boolean = true,
    label: Int,
    textStyle: TextStyle = TextStyle.Default,
    onColor: Color = Color.Unspecified,
    containerColor: Color = Color.Unspecified,
    expandedColor: Color = Color.Unspecified,
    onSelect: (Int) -> Unit,
) {

    Surface(
        modifier = modifier,
        color = containerColor
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {

            icon(
                Modifier
                    .size(36.dp)
                    .padding(8.dp)
            )

            DropdownList(
                modifier = Modifier
                    .weight(1f)
                    //.fillMaxWidth()
                    .wrapContentHeight()
                    .align(Alignment.CenterVertically),
                options = options,
                enabled = enabled,
                label = label,
                onSelect = onSelect,
                textStyle = textStyle,
                color = expandedColor,
                onColor = onColor
            )

        }
    }
}

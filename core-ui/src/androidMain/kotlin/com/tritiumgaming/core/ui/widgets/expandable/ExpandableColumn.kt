package com.tritiumgaming.core.ui.widgets.expandable


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.widgets.collapsebutton.CollapseButton


@Composable
fun ExpandableColumn(
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Unspecified,
    expanded: Boolean = false,
    defaultContent: @Composable (modifier: Modifier, expanded: Boolean) -> Unit = { _, _ -> },
    expandedContent: @Composable (modifier: Modifier) -> Unit = {},
) {
    var rememberExpanded by remember { mutableStateOf(expanded) }

    Column(
        modifier = modifier
            .background(
                containerColor,
                RoundedCornerShape(8.dp)
            )
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Row(
            modifier = Modifier
                .clickable(onClick = {
                    rememberExpanded = !rememberExpanded
                })
        ) {
            defaultContent(Modifier, rememberExpanded)
        }

        if(rememberExpanded) {
            expandedContent(Modifier)
        }
    }
}

@Composable
fun ExpandableRow(
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Unspecified,
    isExpanded: Boolean = false,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.SpaceBetween,
    verticalArrangement: Alignment.Vertical = Alignment.CenterVertically,
    content: @Composable RowScope.(modifier: Modifier) -> Unit = {}
) {
    Row(
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = verticalArrangement,
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = containerColor,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        content(
            Modifier
                .weight(1f)
        )

        CollapseButton(
            modifier = Modifier
                .size(18.dp),
            isCollapsed = isExpanded,
            icon = R.drawable.ic_arrow_chevron_right,
            disabledRotationVertical = -90,
            disabledRotationHorizontal = -90,
            enabledRotationAddition = 180
        )
    }
}

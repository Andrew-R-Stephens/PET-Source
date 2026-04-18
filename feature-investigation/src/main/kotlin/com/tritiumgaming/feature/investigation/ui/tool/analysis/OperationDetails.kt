package com.tritiumgaming.feature.investigation.ui.tool.analysis

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.core.ui.theme.type.LocalTypography
import com.tritiumgaming.core.ui.widgets.collapsebutton.CollapseButton
import com.tritiumgaming.feature.investigation.ui.tool.analysis.sections.ActiveGhostModifierDetails
import com.tritiumgaming.feature.investigation.ui.tool.analysis.sections.DifficultyModifierDetails
import com.tritiumgaming.feature.investigation.ui.tool.analysis.sections.MapModifierDetails
import com.tritiumgaming.feature.investigation.ui.tool.analysis.sections.PhaseModifierDetails

@Composable
internal fun OperationDetails(
    modifier: Modifier = Modifier,
    operationDetailsUiState: OperationDetailsUiState
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.Top),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically) {

            HorizontalDivider(
                modifier = Modifier
                    .weight(1f),
                color = LocalPalette.current.onSurfaceVariant,
                thickness = Dp.Hairline
            )

            Text(
                modifier = Modifier
                    .wrapContentWidth(),
                text = "Operation Details".uppercase(),
                color = LocalPalette.current.onSurfaceVariant,
                style = LocalTypography.current.quaternary.bold.copy(
                    textAlign = TextAlign.Start
                ),
                fontSize = 18.sp,
                maxLines = 1
            )
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f),
                color = LocalPalette.current.onSurfaceVariant,
                thickness = Dp.Hairline
            )
        }


        PhaseModifierDetails(
            state = operationDetailsUiState.phaseDetails
        )
        MapModifierDetails(
            state = operationDetailsUiState.mapDetails
        )
        DifficultyModifierDetails(
            operationDetails = operationDetailsUiState
        )
        ActiveGhostModifierDetails(
            state = operationDetailsUiState.ghostDetails
        )
    }
}

@Composable
internal fun CategoryColumn(
    containerColor: Color = Color.Unspecified,
    content: @Composable () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                containerColor,
                RoundedCornerShape(8.dp)
            )
            .padding(4.dp)
    ) {
        content()
    }
}

@Composable
internal fun ExpandableCategoryColumn(
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Unspecified,
    expanded: Boolean = false,
    defaultContent: @Composable (modifier: Modifier, expanded: Boolean) -> Unit = { _, _ -> },
    expandedContent: @Composable (modifier: Modifier) -> Unit = {},
) {
    var rememberExpanded by remember { mutableStateOf(expanded) }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .background(
                containerColor,
                RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
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
internal fun CategoryRow(
    containerColor: Color = Color.Unspecified,
    content: @Composable RowScope.() -> Unit = {},
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                containerColor,
                RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
    ) {
        content()
    }
}

@Composable
internal fun ExpandableCategoryRow(
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

@Composable
internal fun SubRow(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    Row(
        modifier = modifier
            .wrapContentWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        content()
    }
}

@Composable
internal fun TextCategoryTitle(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified
) {
    Text(
        modifier = modifier,
        text = text,
        color = color
    )
}

@Composable
internal fun TextSubTitle(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = Color.Unspecified
) {
    Text(
        modifier = modifier,
        text = text,
        color = color
    )
}
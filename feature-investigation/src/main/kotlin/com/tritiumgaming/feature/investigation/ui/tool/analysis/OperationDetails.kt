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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.resources.R
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
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
            .verticalScroll(rememberScrollState())
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        PhaseModifierDetails(
            state = operationDetailsUiState.phaseDetails
        )
        MapModifierDetails(
            state = operationDetailsUiState.mapDetails
        )
       DifficultyModifierDetails(
            state = operationDetailsUiState.difficultyDetails
        )
        ActiveGhostModifierDetails(
            state = operationDetailsUiState.ghostDetails
        )
    }

}

@Composable
internal fun CategoryColumn(
    content: @Composable () -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                LocalPalette.current.surfaceContainer,
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
    expanded: Boolean = false,
    defaultContent: @Composable (modifier: Modifier, expanded: Boolean) -> Unit = { _, _ -> },
    expandedContent: @Composable (modifier: Modifier) -> Unit = {}
) {
    var rememberExpanded by remember { mutableStateOf(expanded) }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
            .background(
                LocalPalette.current.surfaceContainer,
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
    content: @Composable RowScope.() -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                LocalPalette.current.surfaceContainer,
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
    isExpanded: Boolean = false,
    content: @Composable RowScope.(modifier: Modifier) -> Unit = {}
) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .background(
                LocalPalette.current.surfaceContainer,
                RoundedCornerShape(8.dp)
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
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        color = LocalPalette.current.onSurface
    )
}

@Composable
internal fun TextSubTitle(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        color = LocalPalette.current.onSurface
    )
}
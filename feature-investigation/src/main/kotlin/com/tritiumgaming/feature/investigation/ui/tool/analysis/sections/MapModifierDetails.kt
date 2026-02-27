package com.tritiumgaming.feature.investigation.ui.tool.analysis.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tritiumgaming.core.ui.theme.palette.provider.LocalPalette
import com.tritiumgaming.feature.investigation.app.mappers.map.toStringResource
import com.tritiumgaming.feature.investigation.ui.tool.analysis.OperationDetailsUiState
import com.tritiumgaming.feature.investigation.ui.tool.analysis.ExpandableCategoryColumn
import com.tritiumgaming.feature.investigation.ui.tool.analysis.ExpandableCategoryRow
import com.tritiumgaming.feature.investigation.ui.tool.analysis.SubRow
import com.tritiumgaming.feature.investigation.ui.tool.analysis.TextCategoryTitle
import com.tritiumgaming.feature.investigation.ui.tool.analysis.TextSubTitle
import java.util.Locale

@Composable
internal fun MapModifierDetails(
    state: OperationDetailsUiState.MapDetails
) {

    ExpandableCategoryColumn(
        modifier = Modifier
            .fillMaxWidth(),
        containerColor = LocalPalette.current.surfaceContainer,
        expanded = false,
        defaultContent = { modifier, expanded ->
            ExpandableCategoryRow(
                modifier = modifier,
                isExpanded = expanded
            ) {
                Row(
                    modifier = modifier,
                ) {
                    TextCategoryTitle(
                        color = LocalPalette.current.onSurface,
                        text = "Map: ")
                    TextSubTitle(
                        color = LocalPalette.current.onSurface,
                        text = stringResource(state.name.toStringResource()))
                }
            }
        }
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SubRow {
                TextSubTitle(
                    color = LocalPalette.current.onSurface,
                    text = "Size: ")
                TextSubTitle(
                    color = LocalPalette.current.onSurface,
                    text = stringResource(state.size.toStringResource()))
            }
            SubRow {
                TextSubTitle(
                    color = LocalPalette.current.onSurface,
                    text = "Setup Modifier:")
                TextSubTitle(
                    color = LocalPalette.current.onSurface,
                    text = String.format(
                        Locale.getDefault(), "%.2f",
                        state.modifiers.setup
                    )
                )
            }
            SubRow {
                TextSubTitle(
                    color = LocalPalette.current.onSurface,
                    text = "Action Modifier:")
                TextSubTitle(
                    color = LocalPalette.current.onSurface,
                    text = String.format(
                        Locale.getDefault(), "%.2f",
                        state.modifiers.normal
                    )
                )
            }
        }
    }

}

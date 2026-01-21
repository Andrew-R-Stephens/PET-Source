package com.tritiumgaming.feature.investigation.ui.common.analysis.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.tritiumgaming.feature.investigation.app.mappers.map.toStringResource
import com.tritiumgaming.feature.investigation.ui.common.analysis.ExpandableCategoryColumn
import com.tritiumgaming.feature.investigation.ui.common.analysis.ExpandableCategoryRow
import com.tritiumgaming.feature.investigation.ui.common.analysis.SubRow
import com.tritiumgaming.feature.investigation.ui.common.analysis.TextCategoryTitle
import com.tritiumgaming.feature.investigation.ui.common.analysis.TextSubTitle
import com.tritiumgaming.feature.investigation.ui.common.operationconfig.map.MapUiState
import java.util.Locale

@Composable
fun MapModifierDetails(
    mapUiState: MapUiState
) {

    ExpandableCategoryColumn(
        modifier = Modifier
            .fillMaxWidth(),
        expanded = false,
        defaultContent = { modifier, expanded ->
            ExpandableCategoryRow(
                modifier = modifier,
                isExpanded = expanded
            ) {
                Row(
                    modifier = modifier,
                ) {
                    TextCategoryTitle(text = "Map: ")
                    TextSubTitle(text = stringResource(mapUiState.name.toStringResource()))
                }
            }
        }
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            SubRow {
                TextSubTitle(text = "Size: ")
                TextSubTitle(text = stringResource(mapUiState.size.toStringResource()))
            }
            SubRow {
                TextSubTitle(text = "Setup Modifier:")
                TextSubTitle(
                    text = String.format(
                        Locale.getDefault(), "%.2f",
                        mapUiState.setupModifier
                    )
                )
            }
            SubRow {
                TextSubTitle(text = "Action Modifier:")
                TextSubTitle(
                    text = String.format(
                        Locale.getDefault(), "%.2f",
                        mapUiState.normalModifier
                    )
                )
            }
        }
    }

}

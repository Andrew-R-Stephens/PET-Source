package com.tritiumgaming.feature.investigation.ui.toolbar.common.analysis.sections

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tritiumgaming.feature.investigation.ui.toolbar.common.analysis.ExpandableCategoryColumn
import com.tritiumgaming.feature.investigation.ui.toolbar.common.analysis.ExpandableCategoryRow
import com.tritiumgaming.feature.investigation.ui.toolbar.common.analysis.SubRow
import com.tritiumgaming.feature.investigation.ui.toolbar.common.analysis.TextCategoryTitle
import com.tritiumgaming.feature.investigation.ui.toolbar.common.analysis.TextSubTitle
import java.util.Locale

@Composable
fun MapModifierDetails(
    mapName: String,
    mapSize: String,
    setupMapModifier: Float,
    normalMapModifier: Float
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
                    TextSubTitle(text = mapName)
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
                TextSubTitle(text = mapSize)
            }
            SubRow {
                TextSubTitle(text = "Setup Modifier:")
                TextSubTitle(
                    text = String.format(
                        Locale.getDefault(), "%.2f",
                        setupMapModifier
                    )
                )
            }
            SubRow {
                TextSubTitle(text = "Action Modifier:")
                TextSubTitle(
                    text = String.format(
                        Locale.getDefault(), "%.2f",
                        normalMapModifier
                    )
                )
            }
        }
    }

}
